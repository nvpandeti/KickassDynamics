import java.awt.Color;
import java.awt.Graphics2D;


public class Ball {
	public static double coefficient_of_drag = 2;
	private Vector2D position, velocity;
	private double mass;
	private int radius;
	private Color color;
	
	public Ball(Vector2D pos, Vector2D vel, double m, int r, Color c)
	{
		position = pos;
		velocity = vel;
		mass = m;
		radius = r;
		color = c;
	}
	
	public Vector2D getPosition()
	{
		return position;
	}
	
	public Vector2D getVelocity()
	{
		return velocity;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public void render(Graphics2D g2){
		g2.setColor(color);
		g2.fillOval((int)position.getX(),(int)position.getY(), radius*2, radius*2);
	}
	
	public void applyAcceleration(Vector2D f, double t)
	{
		/*
		velocity.x += f.x*t;
		velocity.y += f.y*t;
		*/
		velocity = Vector2D.addVectors(velocity, f.multiplyRet(1.0/60));
	}
	
	public void applyForce(Vector2D f, double t)
	{
		/*
		velocity.x += f.x*t;
		velocity.y += f.y*t;
		*/
		velocity = Vector2D.addVectors(velocity, f.multiplyRet((1.0/60)*(1/mass)));
	}
	
	public void applyVelocity(double t)
	{
		position = Vector2D.addVectors(position, velocity.multiplyRet(1.0/60));
		/*
		position.x += velocity.x*t;
		position.y += velocity.y*t;
		*/
	}
}
