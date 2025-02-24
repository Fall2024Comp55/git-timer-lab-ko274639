import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BallLauncher extends GraphicsProgram{
	public static final int PROGRAM_HEIGHT = 600;
	public static final int PROGRAM_WIDTH = 800;
	public static final int SIZE = 25;
	public static final int ms =50;
	public static final int speed = 2;
    public static final int LIMIT_X = 100; // Cooldown limit for ball spawning
	private ArrayList<GOval> balls;
	private Timer timer;
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}
	
	public void run() {
		balls = new ArrayList<>();
		addMouseListeners();
		
		timer = new Timer(ms,e -> actionPerformed());
        timer.start();
		
	}
	
	public void mousePressed(MouseEvent e) {
		
		
		for (GOval ball : balls) {
            if (ball.getX() < LIMIT_X) {
                return; // Ignore this mouse press
            }
		}
		
		GOval ball = makeBall(SIZE/2, e.getY());
		add(ball);
		balls.add(ball);
	}
	
	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		return temp;
	}
	private void actionPerformed() {
        for (GOval ball : balls) {
            ball.move(speed, 0); // Move each ball to the right
        }
    }
	public static void main(String[] args) {
		new BallLauncher().start();
	}

}
