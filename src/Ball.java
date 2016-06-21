import java.awt.Color;


public class Ball {
	private Vector2D position, velocity;
	private double mass, radius;
	private Color color;
	
	public Ball(Vector2D pos, Vector2D vel, double m, double r, Color c)
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
	
	public void applyAcceleration(Vector2D f, double t)
	{
		velocity.x += f.x*t;
		velocity.y += f.y*t;
	}
	
	public void applyVelocity(double t)
	{
		position.x += velocity.x*t;
		position.y += velocity.y*t;
	}
}
