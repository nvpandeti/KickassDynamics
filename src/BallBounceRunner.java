
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BallBounceRunner extends JPanel implements Runnable{
	
	public static final double AIR_DENSITY = .0000012041;
	
	private boolean running = true;
	private long firstTick = 0;
	
	private int x, y;
	private double mass, force;
	
	private Vector2D position, velocity, acceleration;
	
	private ArrayList<Shape> path;
	
	private Ball ball;
	
	public BallBounceRunner(){
		
		setSize(1920/2, 1080/2);
		x = 100;
		y = 100;
		mass = 1;
		force = mass * 9.81;
		
		position = new Vector2D(x, y);
		velocity = new Vector2D(100, 0);
		acceleration = new Vector2D(0, 21);
		
		ball = new Ball(position, velocity, 1, 25, Color.BLACK);
		
		setIgnoreRepaint(true);
		
		path = new ArrayList<Shape>();
	}
	
	public void run() {
		final int TICKS_PER_SECOND = 120;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMESKIP = 5;
		final int RENDERS_PER_SECOND = 200;
		final int SKIP_RENDERS = 1000 / RENDERS_PER_SECOND;
		long nextGameTick = getTickCount();
		long nextRender = getTickCount();
		int loops;
		int count = 0;
		int count2 = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			loops = 0;
			while(getTickCount() > nextGameTick && loops<5){
				tick();
				nextGameTick += SKIP_TICKS;
				loops++;
				count++;
			}
			while(getTickCount() > nextRender){
				render();
				nextRender += SKIP_RENDERS;
				count2++;
			}
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + count);
				System.out.println(count2);
				count = 0;
				count2 = 0;
			}
		}
	}
	
	public void tick(){
		/*
		velocity = Vector2D.addVectors(velocity, acceleration.multiplyRet(1.0/60));
		position = Vector2D.addVectors(position, velocity.multiplyRet(1.0/60));
		*/
		/*
		 * Lets make each pixel = 1 cm, each weight in kg., etc... Basically just use SI units
		 */
		ball.applyAcceleration(acceleration, 1.0/60);
		ball.applyForce(new Vector2D(-.5 * AIR_DENSITY * ball.getVelocity().x * Math.abs(ball.getVelocity().x) * Ball.coefficient_of_drag * Math.PI * ball.getRadius() * ball.getRadius(),
									 -.5 * AIR_DENSITY * ball.getVelocity().y * Math.abs(ball.getVelocity().y) * Ball.coefficient_of_drag * Math.PI * ball.getRadius() * ball.getRadius()),
						1.0/60);    // F drag = .5 * p * v^2 * Cd * A
		ball.applyVelocity(1.0/60);

		if(ball.getPosition().getX() > getWidth()-ball.getRadius()*2){
			ball.getPosition().setX(getWidth()-ball.getRadius()*2);
			ball.getVelocity().setX(ball.getVelocity().getX() * -.9);
		}
			
		if(ball.getPosition().getX() < 0){
			ball.getPosition().setX(0);
			ball.getVelocity().setX(ball.getVelocity().getX() * -.9);
		}
		if(ball.getPosition().getY() > getHeight()-ball.getRadius()*2){
			ball.getPosition().setY(getHeight()-ball.getRadius()*2);
			ball.getVelocity().setY(ball.getVelocity().getY() * -.9);
		}
			
		//*
		System.out.println("Velocity: "+ball.getVelocity());
		System.out.println("Position: "+ball.getPosition());
		/*
		*/
	}
	
	public void render(){
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 1920/2, 1080/2);
		ball.render(g2);
		g2.setColor(Color.RED);
		Ellipse2D.Double o = new Ellipse2D.Double((int)ball.getPosition().getX() + ball.getRadius(), (int)ball.getPosition().getY() + ball.getRadius(), 3, 3);
		path.add(o);
		for(Shape s: path)
			g2.fill(s);
		g2.dispose();
	}
	private long getTickCount(){
		if(firstTick == 0)
			firstTick = System.currentTimeMillis();
		return System.currentTimeMillis() - firstTick;
	}
	
}
