package cloud.earth;

import cloud.earth.app.ConsoleFrame;

public class Main {
    public static void main(String[] args) {
        ConsoleFrame consoleFrame=new ConsoleFrame();
        consoleFrame.init();
        consoleFrame.accessData();
        consoleFrame.close();
    }
}
