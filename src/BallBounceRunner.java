
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
	
	private ArrayList<Shape> path;
	private ArrayList<Color> pathColors;
	
	private Handler handler;
	
	RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
	
	public BallBounceRunner(){
		
		setSize(1920/2, 1080/2);
		
		setIgnoreRepaint(true);
		
		path = new ArrayList<Shape>();
		pathColors = new ArrayList<Color>();
		
		handler = new Handler();
		handler.add(new Ball(new Vector2D(100,100), new Vector2D(100, 0), 1, 2, 25, Color.BLACK, handler));
		handler.add(new Ball(new Vector2D(200,100), new Vector2D(100,0), 2, 2, 25, Color.GREEN, handler));
		handler.add(new Ball(new Vector2D(300,100), new Vector2D(100,0), 5, 2, 25, Color.BLUE, handler));
		handler.add(new Ball(new Vector2D(400,100), new Vector2D(100,0), 10, 2, 25, Color.RED, handler));
	}
	
	public void run() {
		final int TICKS_PER_SECOND = 120;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
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
		handler.tick(getWidth(), getHeight());
	}
	
	public void render(){
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(rh);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		//*
		for (int i = 0; i < path.size(); i++) {
			g2.setColor(pathColors.get(i));
			g2.fill(path.get(i));
		}
		/*
		*/
		handler.render(g2);
		for(PhysicsObject p: handler.getObjects())
		{
			Ball ball = (Ball) p;
			Ellipse2D.Double o = new Ellipse2D.Double((int)ball.getPosition().getX() + ball.getRadius(), (int)ball.getPosition().getY() + ball.getRadius(), 3, 3);
			path.add(o);
			pathColors.add(ball.getColor());
		}
		
		g2.dispose();
	}
	private long getTickCount(){
		if(firstTick == 0)
			firstTick = System.currentTimeMillis();
		return System.currentTimeMillis() - firstTick;
	}
	
}
