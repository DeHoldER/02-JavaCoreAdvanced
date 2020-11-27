import java.util.Random;

public class Human implements Participant {

    static Random rnd = new Random();
    private final String NAME;
    private final int maxRun;
    private final float maxJump;
    private boolean isDroppedOut = false;

    static void doSome() {
        System.out.println();
    }

    public Human() {
        NAME = nameGen();
        this.maxRun = rnd.nextInt(200) * 2;
        this.maxJump = (float) (Math.random() + 1);
    }

    // "на случай важных переговоров" :))
    public Human(String name, int maxRun, float maxJump) {
        NAME = name;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getParticipantType() {
        return "человек";
    }

    @Override
    public void setDroppedOut(boolean value) {
        isDroppedOut = value;
    }

    @Override
    public boolean getDroppedOut() {
        return isDroppedOut;
    }

    @Override
    public void run() {
        System.out.println(NAME + " побежал");
    }

    @Override
    public void jump() {
        System.out.println(NAME + " подпрыгнул");
    }

    @Override
    public void run(RunningTrack track) {
        if (maxRun >= track.getLength()) {
            System.out.println("Человек " + NAME + " пробежал беговую дорожку диной " + track.getLength() + "м.");
        } else {
            System.out.println("Человек " + NAME + " не смог пробежать дорожку длиной " + track.getLength() + " и поэтому выбывает :(");
            isDroppedOut = true;
        }
    }

    @Override
    public void jump(Wall wall) {
        if (maxJump >= wall.getHeight()) {
            System.out.println("Человек " + NAME + " перепрыгнул стену высотой " + wall.getHeightRounded() + "м.");
        } else {
            System.out.println("Человек " + NAME + " не смог перепрыгнуть стену высотой " + wall.getHeightRounded() + "м и поэтому выбывает :(");
            isDroppedOut = true;
        }
    }

    @Override
    public void passObstacle(Obstacle obstacle) {
        if (obstacle instanceof RunningTrack) {
            run((RunningTrack) obstacle);
        }
        if (obstacle instanceof Wall) {
            jump((Wall) obstacle);
        }
    }

    public static String nameGen() {
        String[] namesArr = new String[]{"Коля", "Максим", "Артур", "Дима", "Вася", "Федя", "Семён", "Саша", "Олег", "Антон", "Женя", "Афанасий", "Иннокентий"};
        return namesArr[rnd.nextInt(namesArr.length - 1)];
    }

}
