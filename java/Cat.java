import java.util.Random;

public class Cat implements Participant {
    static Random rnd = new Random();
    private final String NAME;
    private final int maxRun;
    private final float maxJump;
    private boolean isDroppedOut = false;


    public Cat() {
        NAME = nameGen();
        this.maxRun = rnd.nextInt(200) * 4;
        this.maxJump = (float) (Math.random() + 1);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getParticipantType() {
        return "кот";
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
    public void run(RunningTrack track) {
        if (maxRun >= track.getLength()) {
            System.out.println("Котейка " + NAME + " пробежал беговую дорожку диной " + track.getLength() + "м.");
        } else {
            System.out.println("Котейка " + NAME + " не смог пробежать дорожку длиной " + track.getLength() + " и поэтому выбывает :(");
            isDroppedOut = true;
        }
    }

    @Override
    public void jump() {
        System.out.println(NAME + " подпрыгнул");
    }

    @Override
    public void jump(Wall wall) {
        if (maxJump >= wall.getHeight()) {
            System.out.println("Котейка " + NAME + " перепрыгнул стену высотой " + wall.getHeightRounded() + "м.");
        } else {
            System.out.println("Котейка " + NAME + " не смог перепрыгнуть стену высотой " + wall.getHeightRounded() + "м и поэтому выбывает :(");
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
        String[] slog = new String[]{"ба", "ва", "га", "да", "та", "ра", "ма", "ла", "зе", "ле", "ме", "ше", "ве", "бе", "ле", "ку", "зя", "пе", "ся", "то", "ли", "зю", "на", "ти", "ля", "фи", "ка", "фри", "бон", "до", "жир"};
        String[] suf = new String[]{"зик", "лик", "тик", "рик", "чик", "дик", "вик", "бик", "тя", "ня", "шик", "ник", "мик"};
        StringBuilder sb = new StringBuilder();
        sb.append(slog[rnd.nextInt(slog.length - 1)]);
        sb.append(suf[rnd.nextInt(suf.length - 1)]);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }
}
