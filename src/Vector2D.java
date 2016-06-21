
public class Vector2D {
	public double x, y;
	
	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public String toString(){
		return "X: " + x + "Y: " + y;
	}
	
	public Vector2D multiplyRet(double m){
		return new Vector2D(x * m, y * m);
	}
	
	public void multiply(double m){
		
	}
	
	public static Vector2D addVectors(Vector2D a, Vector2D b){
		return new Vector2D(a.x + b.x, a.y + b.y);
	}
	
	public static Vector2D subtractVectors(Vector2D a, Vector2D b){
		return new Vector2D(a.x - b.x, a.y - b.y);
	}
	
	public static Vector2D dotProduct(Vector2D a, Vector2D b){
		return new Vector2D(a.x * b.x, a.y * b.y);
	}
}
