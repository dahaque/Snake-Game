package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    //dimensions of our window screen.
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT= 700;
    //one unit of the screen
    static final int UNIT_SIZE = 25;
    //total number of objects we can fit in our screen.
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    //higher the delay slower the game is.
    static final int DELAY = 75;

    //X and Y co-ordinates of our snake.
    final int[] x = new int [GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction ='R'; // 'R' for right | 'L' for left | 'U' for up | 'D' for down.
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw (Graphics g) {
        if (running) {
            //to construct a grid.
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//                }
            //creating a new apple.
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //drawing the snake
            for (int i = 0; i < bodyParts; i++) {
                //head
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    //body
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink free",Font.BOLD,30));
            FontMetrics m = getFontMetrics(g.getFont());
            //to display score on top of the screen.
            g.drawString("Score : "+applesEaten,(SCREEN_WIDTH - m.stringWidth("Score : "+applesEaten))/2,g.getFont().getSize());
        }else {
            gameOver(g);
        }
    }
    //to move the snake.
    public void move(){
        for (int i = bodyParts ; i>0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        //checking the head of snake and the co-ordinates of apple.
        if((x[0] == appleX) && (y[0]== appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        //checking if head collided with body
        for (int i = bodyParts; i > 0 ; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //checking if head touched the left border
        if(x[0] < 0){
            running = false;
        }
        //checking if head touched the right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //checking if head touched the top border
        if(y[0] < 0){
            running = false;
        }
        //checking if head touched the bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //Game Over text
        g.setColor(Color.RED);
        g.setFont(new Font("Ink free",Font.BOLD,75));
        FontMetrics m1 = getFontMetrics(g.getFont());
        //to display game over in centre of the screen.
        g.drawString("Game Over",(SCREEN_WIDTH - m1.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

        //Score after game over
        g.setColor(Color.RED);
        g.setFont(new Font("Ink free",Font.BOLD,30));
        FontMetrics m2 = getFontMetrics(g.getFont());
        //to display score on top of the screen.
        g.drawString("Score : "+applesEaten,(SCREEN_WIDTH - m2.stringWidth("Score : "+applesEaten))/2,g.getFont().getSize());
    }
    public void newApple(){
        //x and y-axis of the apple.
        appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT :
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
