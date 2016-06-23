import java.awt.Graphics2D;
import java.util.ArrayList;

public class Handler {
	private ArrayList<PhysicsObject> objects;
	
	public Handler(){
		objects = new ArrayList<PhysicsObject>();
	}
	
	public void render(Graphics2D g2){
		for(PhysicsObject p:objects)
			p.render(g2);
	}
	
	public void tick(int w, int h){
		for(PhysicsObject p:objects)
			p.tick(w, h);
	}
	
	public void add(PhysicsObject p){
		objects.add(p);
	}
	
	public ArrayList<PhysicsObject> getObjects(){
		return objects;
	}
}	
