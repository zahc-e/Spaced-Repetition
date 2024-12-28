import java.time.LocalDateTime;
import java.util.Map;

public class simulation {
    public static void main(String[] args) {
        //initialize card
        //Rating rating = new Rating();
        SRS f = new SRS();
        Card card = new Card("topic");
        System.out.println("new scheduled days: " + card.scheduledDays);
        System.out.println("new due date: " + card.due);
        System.out.println("new stability: " + card.stability);
        System.out.println("new difficulty: " + card.difficulty);
        System.out.println("new state: " + card.state);
        System.out.println("new reps: " + card.reps);
        System.out.println();
        //first scheduling
        LocalDateTime now = LocalDateTime.now();
        Map<Integer, SchedulingInfo> scheduling_cards = f.repeat(card, now);
        

        int[] testrun = {2,3,3,3,2,3};
        for (int i = 0; i < testrun.length; i++ ) {
            scheduling_cards = f.repeat(card, card.due);
            card = scheduling_cards.get(testrun[i]).card;
            System.out.println("Round#: " + card.reps);
            System.out.println("scheduled days: " + card.scheduledDays);
            System.out.println("due date: " + card.due);
            System.out.println("stability: " + card.stability);
            System.out.println("difficulty: " + card.difficulty);
            System.out.println("state: " + card.state);
            System.out.println();
        }
    }
}