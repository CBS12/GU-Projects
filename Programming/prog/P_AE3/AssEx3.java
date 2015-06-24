package P_AE3;



/**
 * The main class
 */
public class AssEx3 {
	/**
	 * The main method
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		FitnessProgram fp = new FitnessProgram();
		
		
		SportsCentreGUI display = new SportsCentreGUI(fp);
		display.setVisible(true);
		
	}
	
}
