final class BridgeGUI {

    private static final int REPAINT_EVERY_MS = 25;

    private BridgeGUI() {
    }

    public static void main(String[] args) {
        TrafficRegistrar registrar = new TrafficRegistrarLogger();

        String controllerCommand = args.length > 0 ? args[0] : "";

        TrafficController controller = switch (controllerCommand) {
            case "fair" -> new TrafficControllerFair(registrar);
            case "twoRightCars" -> new TrafficController2RightCars(registrar);
            default -> new TrafficControllerSimple(registrar);
        };

        CarWindow win = new CarWindow(controller);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(REPAINT_EVERY_MS);
                } catch (InterruptedException ignored) {
                }
                win.repaint();
            }
        }).start();
    }

}
