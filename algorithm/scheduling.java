import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


enum State {
    New(0),
    Learning(1),
    Review(2),
    Relearning(3);

    public final int value;

    State(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class Rating {
    int Again = 1;
    int Hard = 2;
    int Good = 3;
    int Easy = 4;

    /*
    Again(1),
    Hard(2),
    Good(3),
    Easy(4);

    public final int value;

    Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    } */
}

class ReviewLog {
    public int rating;
    public int scheduledDays;
    public int elapsedDays;
    public LocalDateTime review;
    public State state;

    public ReviewLog(int rating, int scheduledDays, int elapsedDays, LocalDateTime review, State state) {
        this.rating = rating;
        this.scheduledDays = scheduledDays;
        this.elapsedDays = elapsedDays;
        this.review = review;
        this.state = state;
    }
}

class Card {
    public LocalDateTime due;
    public float stability;
    public float difficulty;
    public int elapsedDays;
    public int scheduledDays;
    public int reps;
    public int lapses;
    public State state;
    public LocalDateTime lastReview;
    public String topic;

    public Card(String topic) {
        this.due = LocalDateTime.now();
        this.stability = 0;
        this.difficulty = 0;
        this.elapsedDays = 0;
        this.scheduledDays = 0;
        this.reps = 0;
        this.lapses = 0;
        this.state = State.New;
        this.topic = topic;
    }

    public Card(Card card) {
        this.due = card.due;
        this.stability = card.stability;
        this.difficulty = card.difficulty;
        this.elapsedDays = card.elapsedDays;
        this.scheduledDays = card.scheduledDays;
        this.reps = card.reps;
        this.lapses = card.lapses;
        this.state = card.state;
        this.topic = card.topic;
    }

    public String getTopic() {
        return this.topic;
    }
    
    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
    }
}

class SchedulingInfo {
    public Card card;
    public ReviewLog reviewLog;

    public SchedulingInfo(Card card, ReviewLog reviewLog) {
        this.card = card;
        this.reviewLog = reviewLog;
    }
}

class SchedulingCards {
    public Card again;
    public Card hard;
    public Card good;
    public Card easy;

    public SchedulingCards(Card card) {
        this.again = new Card(card);
        this.hard = new Card(card);
        this.good = new Card(card);
        this.easy = new Card(card);
    }

    public void updateState(State state) {
        if (state == State.New) {
            again.state = State.Learning;
            hard.state = State.Learning;
            good.state = State.Learning;
            easy.state = State.Review;
            again.lapses += 1;
        } else if (state == State.Learning || state == State.Relearning) {
            again.state = state;
            hard.state = state;
            good.state = State.Review;
            easy.state = State.Review;
        } else if (state == State.Review) {
            again.state = State.Relearning;
            hard.state = State.Review;
            good.state = State.Review;
            easy.state = State.Review;
            again.lapses += 1;
        }
    }

    public void schedule(LocalDateTime now, int hardInterval, int goodInterval, int easyInterval) {
        again.scheduledDays = 0;
        hard.scheduledDays = hardInterval;
        good.scheduledDays = goodInterval;
        easy.scheduledDays = easyInterval;
        again.due = now.plusMinutes(5);
        if (hardInterval > 0) {
            hard.due = now.plusDays((long) hardInterval);
        } else {
            hard.due = now.plusMinutes(10);
        }
        good.due = now.plusDays((long) goodInterval);
        easy.due = now.plusDays((long) easyInterval);
    }

    public Map<Integer, SchedulingInfo> recordLog(Card card, LocalDateTime now) {
        Rating Rating = new Rating();
        HashMap<Integer, SchedulingInfo> log = new HashMap<>();
        log.put(Rating.Again, new SchedulingInfo(again, new ReviewLog(Rating.Again, again.scheduledDays, card.elapsedDays, now, card.state)));
        log.put(Rating.Hard, new SchedulingInfo(hard, new ReviewLog(Rating.Hard, hard.scheduledDays, card.elapsedDays, now, card.state)));
        log.put(Rating.Good, new SchedulingInfo(good, new ReviewLog(Rating.Good, good.scheduledDays, card.elapsedDays, now, card.state)));
        log.put(Rating.Easy, new SchedulingInfo(easy, new ReviewLog(Rating.Easy, easy.scheduledDays, card.elapsedDays, now, card.state)));
        return log;
    }
    
}

class Parameters {
    public float requestRetention;
    public int maximumInterval;
    public float[] w;

    public Parameters() {
        requestRetention = 0.9f;
        maximumInterval = 36500;
        w = new float[]{0.4f, 0.6f, 2.4f, 4.0f, 4.93f, 0.94f, 0.86f, 0.01f, 1.49f, 0.14f, 0.94f, 2.18f, 0.05f, 0.34f, 1.26f, 0.29f, 1.30f};
    }
}