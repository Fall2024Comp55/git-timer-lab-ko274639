import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DodgeBall extends GraphicsProgram implements ActionListener {
	private ArrayList<GOval> balls;
	private ArrayList<GRect> enemies;
	private GLabel text;
	private Timer movement;
	private RandomGenerator rgen;
	private int numTimes = -1 ;
	
	public static final int SIZE = 25;
	public static final int SPEED = 2;
	public static final int MS = 50;
	public static final int MAX_ENEMIES = 10;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 300;
	
	public void run() {
		rgen = RandomGenerator.getInstance();
		balls = new ArrayList<GOval>();
		enemies = new ArrayList<GRect>();
		
		text = new GLabel(""+enemies.size(), 0, WINDOW_HEIGHT);
		add(text);
		
		movement = new Timer(MS, this);
		movement.start();
		addMouseListeners();
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		numTimes++;
		moveAllBallsOnce();
		moveAllEnemiesOnce();
		if(numTimes % 40 == 0) {
		    addAnEnemy();
		}
		
		if (numTimes % 40 == 0) {
	        addAnEnemy();
	    }
	}
	
	public void mousePressed(MouseEvent e) {
		for(GOval b:balls) {
			if(b.getX() < SIZE * 2.5) {
				return;
			}
		}
		addABall(e.getY());     
	}
	
	private void addABall(double y) {
		GOval ball = makeBall(SIZE/2, y);
		add(ball);
		balls.add(ball);
	}
	
	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		return temp;
	}
	
	private void addAnEnemy() {
		GRect e = makeEnemy(rgen.nextInt(0, WINDOW_HEIGHT-SIZE/2));
		enemies.add(e);
		text.setLabel("" + enemies.size());
		rgen.nextInt();
		add(e);
	}
	
	public GRect makeEnemy(double y) {
		GRect temp = new GRect(WINDOW_WIDTH-SIZE, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.GREEN);
		temp.setFilled(true);
		return temp;
	}
	private void moveAllEnemiesOnce( ) {
		for(GRect enemy : enemies) {
			int randomY = rgen.nextInt(-SPEED, SPEED); // Random movement between -SPEED and SPEED
	        enemy.move(0, randomY);
		}
	}

	private void moveAllBallsOnce() {
	    ArrayList<GRect> toRemove = new ArrayList<>(); // Store enemies to remove

		for(GOval ball:balls) {
			//ball.move(SPEED, 0);
			 double nextX = ball.getX() + ball.getWidth() + 1; // The red point in your image
		        double midY = ball.getY() + ball.getHeight() / 2;

		        GObject obj = getElementAt(nextX, midY);
		        
		        if (obj instanceof GRect) { // If it's an enemy
		            remove(obj); // Remove from screen
		            enemies.remove(obj); // Remove from the list
		        } else {
		            ball.move(SPEED, 0); // Move ball forward if no collision
		        }
		    
		}
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public static void main(String args[]) {
		new DodgeBall().start();
	}
}
