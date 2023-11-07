import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.logging.Logger;

class CarWorld extends JPanel {

    private static final Logger Log = Logger.getLogger(CarWorld.class.getSimpleName());

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int LEFT_CAR_IMAGE_ID = 0;
    private static final int RIGHT_CAR_IMAGE_ID = 1;
    private static final int BRIDGE_IMAGE_ID = 2;

    private final Image bridgeImage;
    private final Image leftCarImage;
    private final Image rightCarImage;

    private final TrafficController controller;

    private final ArrayList<Car> rightCars = new ArrayList<>(5);
    private final ArrayList<Car> leftCars = new ArrayList<>(5);

    CarWorld(TrafficController controller) {
        this.controller = controller;

        MediaTracker mt = new MediaTracker(this);
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        leftCarImage = toolkit.getImage(ClassLoader.getSystemResource("image/redcar.gif"));
        rightCarImage = toolkit.getImage(ClassLoader.getSystemResource("image/bluecar.gif"));
        bridgeImage = toolkit.getImage(ClassLoader.getSystemResource("image/bridge.gif"));

        mt.addImage(leftCarImage, LEFT_CAR_IMAGE_ID);
        mt.addImage(rightCarImage, RIGHT_CAR_IMAGE_ID);
        mt.addImage(bridgeImage, BRIDGE_IMAGE_ID);

        try {
            mt.waitForID(LEFT_CAR_IMAGE_ID);
            mt.waitForID(RIGHT_CAR_IMAGE_ID);
            mt.waitForID(BRIDGE_IMAGE_ID);
        } catch (java.lang.InterruptedException e) {
            Log.info("Couldn't load one of the images");
        }

        leftCars.add(new Car(0, Car.LEFT_CAR, null, leftCarImage, null));
        rightCars.add(new Car(0, Car.RIGHT_CAR, null, rightCarImage, null));
    }

    private static Car getLastCar(ArrayList<? extends Car> carList) {
        return carList.get(carList.size() - 1);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bridgeImage, 0, 0, this);
        for (Car leftCar : leftCars) leftCar.draw(g);
        for (Car rightCar : rightCars) rightCar.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(bridgeImage.getWidth(null), bridgeImage.getHeight(null));
    }

    void addCar(int cartype) {
        SwingUtilities.invokeLater(() -> {
            switch (cartype) {
                case Car.LEFT_CAR -> {
                    Car inFront = getLastCar(leftCars);
                    leftCars.add(new Car(inFront.getId() + 1, Car.LEFT_CAR, inFront, leftCarImage, controller));

                    new Thread(getLastCar(leftCars)).start();
                }
                case Car.RIGHT_CAR -> {
                    Car inFront = getLastCar(rightCars);
                    rightCars.add(new Car(inFront.getId() + 1, cartype, inFront, rightCarImage, controller));

                    new Thread(getLastCar(rightCars)).start();
                }
            }
        });
    }

}
