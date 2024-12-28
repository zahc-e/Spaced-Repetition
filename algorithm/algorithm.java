import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

class SRS {
    public Parameters p;
    public SRS() {
        p = new Parameters();
    }

    /* 
    private Map<String, Float> topicPerformance = new HashMap<>();
    private ArrayList<Card> cards = new ArrayList<>();

    private void updateDifficultyByTopic(String topic, float userPerformance) {
        float currentPerformance = topicPerformance.getOrDefault(topic, 0.0f);
        int numResponses = 1; //for simplicity
        float updatedPerformance = (currentPerformance * numResponses + userPerformance) / (numResponses + 1);
        topicPerformance.put(topic, updatedPerformance);
    }


    private void assignDifficultyToRelatedFlashcards(String topic, float difficulty) {
        for (Card card : cards) {
            if (card.getTopic().equals(topic)) {
                card.setDifficulty(difficulty);
            }
        }
    }
    */

    public Map<Integer, SchedulingInfo> repeat(Card card, LocalDateTime now) {
        Card newCard = new Card(card);
        if (newCard.state == State.New) {
            newCard.elapsedDays = 0;
        } else {
            //Duration duration = Duration.between(now, card.lastReview);
            //newCard.elapsedDays = (int) duration.toDays();
            newCard.elapsedDays = newCard.scheduledDays;
        }
        newCard.lastReview = now;
        newCard.reps += 1;

        SchedulingCards s = new SchedulingCards(newCard);
        s.updateState(newCard.state);

        if (newCard.state == State.New) {
            initDS(s);

            s.again.due = now.plusMinutes(1);
            s.hard.due = now.plusMinutes(5);
            s.good.due = now.plusMinutes(10);
            int easyInterval = nextInterval(s.easy.stability);
            s.easy.scheduledDays = (int) easyInterval;
            s.easy.due = now.plusDays((int) easyInterval);
        } else if (newCard.state == State.Learning || newCard.state == State.Relearning) {
            int hardInterval = 0;
            int goodInterval = nextInterval(s.good.stability);
            int easyInterval = Math.max(nextInterval(s.easy.stability), goodInterval + 1);

            s.schedule(now, hardInterval, goodInterval, easyInterval);
        } else if (newCard.state == State.Review) {
            int interval = newCard.elapsedDays;
            float lastD = newCard.difficulty;
            float lastS = newCard.stability;
            float retrievability = (float) Math.pow(1 + interval / (9 * lastS), -1);
            nextDS(s, lastD, lastS, retrievability);

            int hardInterval = nextInterval(s.hard.stability);
            int goodInterval = nextInterval(s.good.stability);
            hardInterval = Math.min(hardInterval, goodInterval);
            goodInterval = Math.max(goodInterval, hardInterval + 1);
            int easyInterval = Math.max(nextInterval(s.easy.stability), goodInterval + 1);
            s.schedule(now, hardInterval, goodInterval, easyInterval);
        }
        
        return s.recordLog(newCard, now);
    }

    private void initDS(SchedulingCards s) {
        Rating rating = new Rating();
        s.again.difficulty = initDifficulty(rating.Again);
        s.again.stability = initStability(rating.Again);
        s.hard.difficulty = initDifficulty(rating.Hard);
        s.hard.stability = initStability(rating.Hard);
        s.good.difficulty = initDifficulty(rating.Good);
        s.good.stability = initStability(rating.Good);
        s.easy.difficulty = initDifficulty(rating.Easy);
        s.easy.stability = initStability(rating.Easy);
    }

    private void nextDS(SchedulingCards s, float lastD, float lastS, float retrievability) {
        Rating rating = new Rating();
        s.again.difficulty = nextDifficulty(lastD, rating.Again);
        s.again.stability = nextRecallStability(s.again.difficulty, lastS, retrievability, rating.Again);
        s.hard.difficulty = nextDifficulty(lastD, rating.Hard);
        s.hard.stability = nextRecallStability(s.hard.difficulty, lastS, retrievability, rating.Hard);
        s.good.difficulty = nextDifficulty(lastD, rating.Good);
        s.good.stability = nextRecallStability(s.good.difficulty, lastS, retrievability, rating.Good);
        s.easy.difficulty = nextDifficulty(lastD, rating.Easy);
        s.easy.stability = nextRecallStability(s.easy.difficulty, lastS, retrievability, rating.Easy);
    }

    private float initStability(int r) {
        return Math.max(p.w[r - 1], 0.1f);
    }

    private float initDifficulty(int r) {
        return Math.min(Math.max(p.w[4] - p.w[5] * (r - 3), 1f), 10f);
    }

    private int nextInterval(float s) {
        float newInterval = s * 9 * (1 / p.requestRetention - 1);
        return Math.min(Math.max(Math.round(newInterval), 1), p.maximumInterval);
    }

    private float nextDifficulty(float d, int r) {
        float nextD = d - p.w[6] * (r - 3);
        return Math.min(Math.max(meanReversion(p.w[4], nextD), 1f), 10f);
    }

    private float meanReversion(float init, float current) {
        return p.w[7] * init + (1 - p.w[7]) * current;
    } 

    private float nextRecallStability(float d, float s, float r, int rating) {
        Rating Rating = new Rating();
        float hardPenalty = (rating == Rating.Hard) ? p.w[15] : 1;
        float easyBonus = (rating == Rating.Easy) ? p.w[16] : 1;
        return (float) (s * (1 + (float) Math.exp(p.w[8]) * (11 - d) *
                Math.pow(s, -p.w[9]) * (Math.exp((1 - r) * p.w[10]) - 1) *
                hardPenalty * easyBonus));
    }

    private float nextForgetStability(float d, float s, float r) {
        return p.w[11] * (float) Math.pow(d, -p.w[12]) * (float) (Math.pow(s + 1, p.w[13]) - 1) *
                (float) Math.exp((1 - r) * p.w[14]);
    }
}