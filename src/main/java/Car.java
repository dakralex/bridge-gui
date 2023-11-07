import java.awt.*;

/**
 * Car is an implementation of a vehicle, which runs with its own engine (as a thread) in the traffic.
 */
public class Car implements Vehicle, Runnable {

    static final int LEFT_CAR = 0;
    static final int RIGHT_CAR = 1;

    private static final int CAR_WORLD_WIDTH = 900;
    private static final int CAR_WORLD_LEFT_END = -200;
    private static final int CAR_WORLD_RIGHT_END = CAR_WORLD_WIDTH + 200;

    private static final int LEFT_CAR_X_INIT = -80;
    private static final int RIGHT_CAR_X_INIT = CAR_WORLD_WIDTH;

    private static final int CAR_X_SPEED = 4;
    private static final int CAR_Y_SPEED = 2;
    private static final int CAR_WAIT_TIME = 30;
    private static final int CAR_SEPARATION_DISTANCE = 90;

    private static final int LANE_Y_TOP = 55;
    private static final int LANE_Y_MID = 95;
    private static final int LANE_Y_BOTTOM = 135;

    private static final int BRIDGE_X_LEFT_BARRIER = 210;
    private static final int BRIDGE_X_LEFT_CROSS_POINT = 290;
    private static final int BRIDGE_X_MID_POINT = 410;
    private static final int BRIDGE_X_RIGHT_CROSS_POINT = 530;
    private static final int BRIDGE_X_RIGHT_BARRIER = 610;

    private final int id;
    private final int type;
    private final Car next;
    private final Image image;
    private final TrafficController controller;
    private int x, y;

    Car(int id, int type, Car next, Image image, TrafficController controller) {
        this.id = id;
        this.type = type;
        this.next = next;
        this.image = image;
        this.controller = controller;

        y = type == LEFT_CAR ? LANE_Y_BOTTOM : LANE_Y_TOP;
        x = switch (type) {
            case LEFT_CAR ->
                    next == null ? CAR_WORLD_RIGHT_END : Math.min(LEFT_CAR_X_INIT, next.getX() - CAR_SEPARATION_DISTANCE);
            case RIGHT_CAR ->
                    next == null ? CAR_WORLD_LEFT_END : Math.max(RIGHT_CAR_X_INIT, next.getX() + CAR_SEPARATION_DISTANCE);
            default -> throw new IllegalStateException(String.format("Invalid car type: %d", type));
        };
    }

    @Override
    public int getId() {
        return id;
    }

    private void move() {
        int xBeforeUpdate = x;
        boolean isNextCarTooClose = Math.abs(x - next.getX()) < (CAR_SEPARATION_DISTANCE + 10);

        if (isNextCarTooClose) return;
        if (type == LEFT_CAR) {
            moveLeft(xBeforeUpdate);
        } else {
            moveRight(xBeforeUpdate);
        }

    }

    private void moveLeft(int xBeforeUpdate) {
        x += CAR_X_SPEED;

        if (x >= BRIDGE_X_LEFT_BARRIER && xBeforeUpdate < BRIDGE_X_LEFT_BARRIER) {
            controller.enterLeft(this);
        } else if (x > BRIDGE_X_LEFT_BARRIER && x < BRIDGE_X_MID_POINT) {
            if (y > LANE_Y_MID) y -= CAR_Y_SPEED;
        } else if (x >= BRIDGE_X_RIGHT_CROSS_POINT && x < BRIDGE_X_RIGHT_BARRIER) {
            if (y < LANE_Y_BOTTOM) y += CAR_Y_SPEED;
        } else if (x >= BRIDGE_X_RIGHT_BARRIER && xBeforeUpdate < BRIDGE_X_RIGHT_BARRIER) {
            controller.leaveRight(this);
        }
    }

    private void moveRight(int xBeforeUpdate) {
        x -= CAR_X_SPEED;

        if (x <= BRIDGE_X_RIGHT_BARRIER && xBeforeUpdate > BRIDGE_X_RIGHT_BARRIER) {
            controller.enterRight(this);
        } else if (x < BRIDGE_X_RIGHT_BARRIER && x > BRIDGE_X_MID_POINT) {
            if (y < LANE_Y_MID) y += CAR_Y_SPEED;
        } else if (x <= BRIDGE_X_LEFT_CROSS_POINT && x > BRIDGE_X_LEFT_BARRIER) {
            if (y > LANE_Y_TOP) y -= CAR_Y_SPEED;
        } else if (x <= BRIDGE_X_LEFT_BARRIER && xBeforeUpdate > BRIDGE_X_LEFT_BARRIER) {
            controller.leaveLeft(this);
        }
    }

    public void run() {
        while (type == LEFT_CAR ? x <= CAR_WORLD_WIDTH : x >= -image.getWidth(null)) {
            move();
            try {
                Thread.sleep(CAR_WAIT_TIME);
            } catch (InterruptedException ignored) {
            }
        }

        x = type == LEFT_CAR ? CAR_WORLD_RIGHT_END : CAR_WORLD_LEFT_END;
    }

    private int getX() {
        return x;
    }

    void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

}
