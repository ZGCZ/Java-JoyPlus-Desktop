package joyplus;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class VirtualKeyboard {
    private VirtualKeyboard() {
        
    }
    
    private static VirtualKeyboard instance = null; 
    
    public static VirtualKeyboard getInstance() {
        if (instance == null) instance = new VirtualKeyboard();
        return instance;
    }
    
    void test() {
        try { 

            Robot robot = new Robot(); 
            // Creates the delay of 5 sec so that you can open notepad before 
            // Robot start writting 
            robot.delay(5000); 
            System.out.println("Start printing");
            robot.keyPress(KeyEvent.VK_H); 
            robot.keyPress(KeyEvent.VK_I); 
            robot.keyPress(KeyEvent.VK_SPACE); 
            robot.keyPress(KeyEvent.VK_B); 
            robot.keyPress(KeyEvent.VK_U); 
            robot.keyPress(KeyEvent.VK_D); 
            robot.keyPress(KeyEvent.VK_Y); 
            robot.keyRelease(KeyEvent.VK_Y); 
            System.out.println("End printing");

        } catch (AWTException e) { 
            e.printStackTrace(); 
        } 
    }
}
