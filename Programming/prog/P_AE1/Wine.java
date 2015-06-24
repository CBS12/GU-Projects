package P_AE1;

/**Used to collect data from the GUI, passed to the 
 * Customer class for processing and returning information
 * to the GUI on the transactions taking place
 * @author Stewart home
 *
 */
public class Wine {

	//instance variables
	private int numBottles;
	private double costBottle;
	private String wineName;


	//prepare the constructor for the Wine object	
	public Wine (int n, double c, String t){
		numBottles = n;
		costBottle = c;
		wineName = t;
	}

	//accessor methods
	public String getWineName() {
		return wineName;
	}
	public double getCostBottle(){
		return costBottle;
	}
	public int getNumBottles(){
		return numBottles;
	}

}
