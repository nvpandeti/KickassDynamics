import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BallBounceRunner extends JPanel implements Runnable{
	
	private boolean running = true;
	private long firstTick = 0;
	
	private int x, y;
	private double mass, force;
	
	private Ball ball;
	
	private Vector2D position, velocity, acceleration;
	
	public BallBounceRunner(){
		setSize(1920/2, 1080/2);
		x = 200;
		y = 200;
		mass = 1;
		force = mass * 9.81;
		
		//what THE FUCK
		
		position = new Vector2D(x, y);
		velocity = new Vector2D(50, 0);
		acceleration = new Vector2D(0, 21);
		
		ball = new Ball(position, velocity, mass, 25, Color.black);
	}
	
	public void run() {
		final int TICKS_PER_SECOND = 60;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMESKIP = 5;
		long nextGameTick = getTickCount();
		int loops;
		int count = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			loops = 0;
			while(getTickCount() > nextGameTick){
				tick();
				nextGameTick += SKIP_TICKS;
				loops++;
				count++;
			}
			render();
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + count);
				count = 0;
			}	
		}
	}
	
	public void tick(){
		//velocity = Vector2D.addVectors(velocity, acceleration.multiplyRet(1.0/60));
		//position = Vector2D.addVectors(position, velocity.multiplyRet(1.0/60));
		ball.applyAcceleration(acceleration, 1.0/60);
		ball.applyVelocity(1.0/60);
		
		System.out.println(velocity);
		System.out.println(position);
		
	}
	
	public void render(){
		repaint();
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 1920/2, 1080/2);
		g2.setColor(ball.getColor());
		g2.fillOval((int)ball.getPosition().getX(),(int)ball.getPosition().getY(), (int)(ball.getRadius()), (int)(ball.getRadius()));
		
		
		g2.dispose();
	}
	
	private long getTickCount(){
		if(firstTick == 0)
			firstTick = System.currentTimeMillis();
		return System.currentTimeMillis() - firstTick;
	}
	
}
