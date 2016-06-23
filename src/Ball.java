import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


public class Ball extends PhysicsObject {
	
	private int radius;
	
	public static final double AIR_DENSITY = .0000012041;
	
	public Ball(Vector2D pos, Vector2D vel, double m, double cD, int r, Color c, Handler h)
	{
		super(pos, vel, m, cD, c, ID.Ball, h);
		radius = r;	
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public void render(Graphics2D g2){
		g2.setColor(super.getColor());
		g2.fillOval((int)super.getPosition().getX(),(int)super.getPosition().getY(), radius*2, radius*2);
	}

	public void tick(int w, int h) {
		super.applyForce(new Vector2D(-.5 * AIR_DENSITY * super.getVelocity().x * Math.abs(super.getVelocity().x) * super.getCoefDrag() * Math.PI * radius * radius,
				 -.5 * AIR_DENSITY * super.getVelocity().y * Math.abs(super.getVelocity().y) * super.getCoefDrag() * Math.PI * radius * radius));    // F drag = .5 * p * v^2 * Cd * A
		super.applyForce(new Vector2D(0, 98*super.getMass()));
		
		for(PhysicsObject p: super.getHandler().getObjects()){
			if(p.getId() == ID.Ball){
				Ball b = (Ball) p;
				if(!b.getColor().equals(getColor())){
					if(Math.abs(getDistance(b)) < b.getRadius() + getRadius()){
						double theta = Math.atan2(getPosition().y - b.getPosition().y, getPosition().x - b.getPosition().x);
						double offset = Math.abs((b.getRadius() + getRadius()) - getDistance(b));
						double ratio = b.getRadius()/(b.getRadius() + getRadius());
						setPosition(Vector2D.addVectors(getPosition(), new Vector2D(ratio*offset*Math.cos(theta), ratio*offset*Math.sin(theta))));
						ratio = getRadius()/(b.getRadius() + getRadius()) + .01;
						theta += Math.PI;
						b.setPosition(Vector2D.addVectors(b.getPosition(), new Vector2D(ratio*offset*Math.cos(theta), ratio*offset*Math.sin(theta))));
						Vector2D thisVel = super.getVelocity();
						Vector2D otherVel = b.getVelocity();
						Vector2D newOtherVel = Vector2D.addVectors(otherVel.multiplyRet(b.getMass() - getMass()), thisVel.multiplyRet(2 * getMass())).divideRet(getMass() + b.getMass());
						Vector2D newThisVel = Vector2D.addVectors(thisVel.multiplyRet(getMass() - b.getMass()), otherVel.multiplyRet(2 * b.getMass())).divideRet(getMass() + b.getMass());
						//super.setPosition(Vector2D.addVectors(super.getPosition(), thisVel.multiplyRet(1.0/20)));
						//b.setPosition(Vector2D.addVectors(b.getPosition(), otherVel.multiplyRet(1.0/20)));
						super.setVelocity(newThisVel);
						b.setVelocity(newOtherVel);
					}
				}
			}
		}
		
		super.applyVelocity();
		
		if(super.getPosition().getX() > w-radius*2){
			super.getPosition().setX(w-radius*2);
			super.getVelocity().setX(super.getVelocity().getX() * -.9);
		}
		
		if(super.getPosition().getX() < 0){
			super.getPosition().setX(0);
			super.getVelocity().setX(super.getVelocity().getX() * -.9);
		}
		if(super.getPosition().getY() > h-radius*2){
			super.getPosition().setY(h-radius*2);
			super.getVelocity().setY(super.getVelocity().getY() * -.9);
		}
	}

	public Shape getBounds() {
		//going clockwise
		int xTop = (int) super.getPosition().getX() + radius;
		int yTop = (int) super.getPosition().getY();
		double xOne = super.getPosition().getX() + radius + radius*Math.cos(Math.PI/3);
		int xInt = (int) xOne;
		//int xpoints[] = {super.getPosition().getX() + radius, super.getPosition().getX() + radius + radius*Math.cos(Math.PI/3), super.getPosition().getX() + radius + radius*Math.cos(Math.PI/4), super.getPosition().getX() + radius + radius*Math.cos(Math.PI/6), 
		return new Ellipse2D.Double(super.getPosition().getX(), super.getPosition().getY(), radius*2, radius*2);
	}
	
	public double getDistance(Ball b){
		return Math.sqrt(Math.pow(b.getPosition().getX()+b.getRadius() - (getPosition().getX()+getRadius()), 2) + Math.pow(b.getPosition().getY()+b.getRadius() - (getPosition().getY() + getRadius()), 2));
	}
	
}
