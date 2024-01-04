package de.lojaw;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyboardSimulator {
    private Robot robot;

    public KeyboardSimulator() {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }
}
