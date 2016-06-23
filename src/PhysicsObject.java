import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class PhysicsObject {
	private Vector2D position, velocity;
	private double mass;
	private double coefOfDrag;
	private ID id;
	private Color color;
	private Handler handler;
	
	public PhysicsObject(Vector2D p, Vector2D v, double m, double cD, Color c, ID id, Handler h){
		position = p;
		velocity = v;
		mass = m;
		coefOfDrag = cD;
		color = c;
		handler = h;
		this.id = id;
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
	
	public double getCoefDrag(){
		return coefOfDrag;
	}
	
	public Handler getHandler(){
		return handler;
	}
	
	public ID getId(){
		return id;
	}
	
	public void applyForce(Vector2D f)
	{
		velocity = Vector2D.addVectors(velocity, f.multiplyRet((1.0/60)*(1/mass)));
	}
	
	public void applyVelocity()
	{
		position = Vector2D.addVectors(position, velocity.multiplyRet(1.0/60));
	}
	
	public void setVelocity(Vector2D f){
		velocity = f;
	}
	
	public void setPosition(Vector2D f){
		position = f;
	}
	
	public abstract void render(Graphics2D g2);
	
	public abstract void tick(int width, int height);
	
	public abstract Shape getBounds();
}
