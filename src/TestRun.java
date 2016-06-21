import javax.swing.JFrame;

public class TestRun {
	public static void main(String[] args){
		JFrame f = new JFrame();
		BallBounceRunner bb = new BallBounceRunner();
		
		f.setSize(1920/2, 1080/2);
		f.setLocationRelativeTo(null);
		f.add(bb);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Thread t = new Thread(bb);
		t.start();
		
		f.setVisible(true);
	}
}
