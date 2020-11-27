public interface Participant {

    void passObstacle(Obstacle obstacle);

    void run();

    void run(RunningTrack track);

    void jump();

    void jump(Wall wall);

    String getName();

    String getParticipantType();

    void setDroppedOut(boolean value);

    boolean getDroppedOut();

}
