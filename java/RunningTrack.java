import java.util.Random;

public class RunningTrack implements Obstacle {

    private final int LENGTH;
    static Random rnd = new Random();

    public RunningTrack() {
        LENGTH = rnd.nextInt(200);
    }

    public RunningTrack(int length) {
        LENGTH = length;
    }

    public int getLength() {
        return LENGTH;
    }

}
