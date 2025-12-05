//nazrawit tefera
// the use of this class is that it handels moving the paddels and the ball, keeping the score etc
package com.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PongGame extends JPanel implements MouseMotionListener {
    static int width = 640; // this is the amount of pixels to the right side of the screen
    static int height = 480; // this is the amount of pixels to the top of the screen.
    private int userMouseY;
    private Paddle aiPaddle;
    private int playerScore;
    private int aiScore;
    private Ball ball;
    private Paddle userPaddle;
    private Rectangle speedUpZone;
    private Rectangle speedDownZone;
    private Rectangle middleWall;
    //private Speedup speedup;
    //private SlowDown slowDown;// step 1 add any other private variables you may need to play the game.

    public PongGame() {

        aiPaddle = new Paddle(610, 240, 50, 9, Color.WHITE);
        JLabel pScore = new JLabel("0");
        JLabel aiScore = new JLabel("0");
        pScore.setBounds(280, 440, 20, 20);
        aiScore.setBounds(360, 440, 20, 20);
        pScore.setVisible(true);
        aiScore.setVisible(true);
        userMouseY = 0;
        addMouseMotionListener(this);
        ball = new Ball(100, 200, 10, 3, Color.RED, 10);
        speedUpZone = new Rectangle(300, 0, 40, 100);
        middleWall = new Rectangle(300, 150, 40, 100);
        speedDownZone = new Rectangle(300, 300, 40, 100);

        userPaddle=new Paddle(20,100,50,9,Color.RED);//create any other objects necessary to play the game.

    }
    //pre-none
    //post-it makes sure that the ball starts from the center and restarts the game
    public void resetBall() {
    ball.setX(width / 2);
    ball.sety(height / 2);
    ball.reverseY();
}

    // precondition: None
    // postcondition: returns playerScore
    public int getPlayerScore() {
        return playerScore;
    }

    // precondition: None
    // postcondition: returns aiScore
    public int getAiScore() {
        return aiScore;
    }

    //precondition: All visual components are initialized, non-null, objects 
    //postcondition: A frame of the game is drawn onto the screen.
    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.drawString("The Score is User:" + playerScore + " vs Ai:" + aiScore, 240, 20);
        ball.draw(g);
        aiPaddle.draw(g);
        
        userPaddle.draw(g);
        g.fillRect(
           middleWall.x,
           middleWall.y,
           middleWall.width,
           middleWall.height);   
    }

    // precondition: all required visual components are intialized to non-null
    // values
    // postcondition: one frame of the game is "played"
    public void gameLogic() {
        ball.moveBall();
        if (ball.getY() < 0) {
    ball.sety(0);
    ball.reverseY();
}
if (ball.getY() + 10 > height) {  
    ball.sety(height - 10);
    ball.reverseY();
}
        checkSpeedUpZone();
        checkSpeedDownZone();
        checkCollusion();
        pointScored();
        userPaddle.moveY(userMouseY);//add commands here to make the game play propperly
        
    int targetY = ball.getY();

// keep the target inside the screen so paddle never goes outside
if (targetY < 0) {
    targetY = 0;
}
if (targetY > height - 50) {
    targetY = height - 50;
}

aiPaddle.moveY(targetY);
    }
    // precondition: ball and middleWall exist and have valid Rectangle shapes
// postcondition: 
//    - if ball hits the middle wall, ball's vertical direction reverses
//    - otherwise, ball movement is unchanged
    public void checkMiddleWall() {
    if (ball.getRectangle().intersects(middleWall)) {
        ball.setChangeY(ball.getChangeY() * -1); // bounce vertically
    }
}
       
// precondition: ball, userPaddle, and aiPaddle exist
// postcondition: 
//    - if ball touches either paddle, its horizontal direction reverses
//    - otherwise, ball direction is unchanged
        public void checkCollusion(){
        if(userPaddle.isTouching(ball)){
            ball.reverseX();
        }
        if(aiPaddle.isTouching(ball)){
            ball.reverseX();
        }
        }
        // precondition: None
// postcondition: No effect (method currently empty)
    public void bounceOffWalls(){
           
        }
       // precondition: ball exists and can return its x-coordinate
// postcondition:
//    - if ball leaves left boundary: AI score increases; ball resets
//    - if ball leaves right boundary: player score increases; ball resets
//    - otherwise: no score change
        public void pointScored(){
            if(ball.getX()<0){
                aiScore++;
                resetBall();
            }
            if(ball.getX()>width){
                playerScore++;
                resetBall();
            }
        }
       // precondition: ball and speedUpZone are initialized
// postcondition: 
//    - if the ball gets to the speed up zone, its X speed increases by 20%
//    - if not the speed is unchanged
        public void checkSpeedUpZone(){
            if (ball.getRectangle().intersects(speedUpZone)) {
                ball.setChangeX(ball.getChangeX() * 1.2);
    }
        }
        // precondition: ball and speedDownZone are initialized
// postcondition: 
//    - if the ball gets to the slow down zone, its X speed decreases by 20%
//    - if not the speed is unchanged
        public void checkSpeedDownZone(){
            if (ball.getRectangle().intersects(speedDownZone)) {
                ball.setChangeX(ball.getChangeX() * 0.8);
    }

        }

        

    

    // precondition: ball is a non-null object that exists in the world
    // postcondition: determines if either ai or the player score needs to be
    // updated and re-sets the ball
    // the player scores if the ball moves off the right edge of the screen (640
    // pixels) and the ai scores
    // if the ball goes off the left edge (0)
    //public void pointScored() {

    //}

    // you do not need to edit the below methods, but please do not remove them as
    // they are required for the program to run.
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        userMouseY = e.getY();
    }

}
