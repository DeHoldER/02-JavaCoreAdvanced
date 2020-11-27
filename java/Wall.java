import java.util.Random;

public class Wall implements Obstacle {

    private final float HEIGHT;
    static Random rnd = new Random();

    public Wall() {
        HEIGHT = (float) (Math.random() + 1);
    }

    public Wall(int height) {
        HEIGHT = height;
    }

    public float getHeight() {
        return HEIGHT;
    }

    public String getHeightRounded() {
        String str;
        str = String.format("%.2f", HEIGHT);
        return str;
    }

}
