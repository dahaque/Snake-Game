package Snake;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel()); //passing an instance of GamePanel in GameFrame.
        this.setTitle("SnakeGame by Sadiq");  //title of the game
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to close the game
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // ensures that the window appears in the middle of the screen.
    }
}
