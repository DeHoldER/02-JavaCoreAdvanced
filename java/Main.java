import java.util.Random;

public class Main {

    static Random rnd = new Random();
    private static final int PARTICIPANTS_NUM = 7;
    private static final int OBSTACLES_NUM = 3;

    public static void main(String[] args) {

        Participant[] participants = new Participant[PARTICIPANTS_NUM];
        Obstacle[] obstacles = new Obstacle[OBSTACLES_NUM];


        for (int i = 0; i < PARTICIPANTS_NUM; i++) {
            int x = rnd.nextInt(3);
            if (x == 0) {
                participants[i] = new Human();
            }
            if (x == 1) {
                participants[i] = new Cat();
            } else participants[i] = new Robot();
        }
        for (int i = 0; i < OBSTACLES_NUM; i++) {
            if (rnd.nextInt(2) == 0) {
                obstacles[i] = new RunningTrack();
            } else obstacles[i] = new Wall();
        }


        for (int i = 0; i < PARTICIPANTS_NUM; i++) {
            for (int j = 0; j < OBSTACLES_NUM; j++) {
                if (!participants[i].getDroppedOut()) {
                    participants[i].passObstacle(obstacles[j]);
                }
            }
        }

        System.out.println();
        StringBuilder runnerWinners = new StringBuilder();
        for (int i = 0; i < PARTICIPANTS_NUM; i++) {
            if (!participants[i].getDroppedOut()) {
                runnerWinners.append(participants[i].getParticipantType()).append(" ").append(participants[i].getName()).append(", ");
            }
        }

        if (runnerWinners.length() > 2) {
            System.out.println("Соревнования осилили: " + runnerWinners.deleteCharAt(runnerWinners.length() - 2));
        } else System.out.println("Все участники выбыли :(");
    }
}
