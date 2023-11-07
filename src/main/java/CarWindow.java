import javax.swing.*;
import java.awt.*;
import java.io.Serial;

final class CarWindow extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    CarWindow(TrafficController controller) {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        CarWorld carWorldPanel = new CarWorld(controller);

        contentPane.add("Center", carWorldPanel);

        JButton addLeft = new JButton("Add Left");
        JButton addRight = new JButton("Add Right");
        addLeft.addActionListener(e -> carWorldPanel.addCar(Car.LEFT_CAR));
        addRight.addActionListener(e -> carWorldPanel.addCar(Car.RIGHT_CAR));

        JPanel carWorldControlPanel = new JPanel();
        carWorldControlPanel.setLayout(new FlowLayout());
        carWorldControlPanel.add(addLeft);
        carWorldControlPanel.add(addRight);
        contentPane.add("South", carWorldControlPanel);

        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
