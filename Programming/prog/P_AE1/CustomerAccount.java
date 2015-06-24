package P_AE1;

/** Methods for processing transactions Sale or Return are 
 * in this Class to be passed to the GUI. The Customer name and intial balance 
 * are called to this Class from the Main method.
 * @author Stewart home
 *
 */
public class CustomerAccount {

	private double currBalance;
	private String custName;//input from the JOptionPane
	public static double initBalance;//input from the JOptioPane
	//represents 20% charge on return transactions
	public final static double returnCharge = 0.20;
	double bal;
	//constructor for getting data from JOptionPane
	public CustomerAccount(String c, double b)	{
		custName = c;
		currBalance = b;
	}
	/*method for preparing the initial balance taking Credit balances
	into account, passed to LWMGUI and also to helper methods in 
	CustomerAccount for processing*/
	public String getInitBalance(){
		if (currBalance>=0){
			return (String.format("%.2f", currBalance));
		}
		else{
			bal = -1*currBalance;
			return (String.format("%.2f CR", bal));
		}
	}
	//method for return Transaction with data provided by the Wine object.
	public double returnTransaction(Wine wineModel){
		double wineTransRet = (wineModel.getNumBottles()
				*wineModel.getCostBottle())*(1-returnCharge);//20% charge applied
		return wineTransRet;
	}
	/*method for returns updated Current balance with 
	 * data provided by the Wine object.*/
	public String returnBalance(Wine wineModel){
		currBalance = currBalance - (wineModel.getNumBottles()
				*wineModel.getCostBottle()*(1-returnCharge));
		if (currBalance>=0){
			return (String.format("%.2f", currBalance));
		}
		else{
			bal = -1*currBalance;//negative values presented as CR balances in LWMGUI
			return (String.format("%.2f CR", bal));
		}
	}
	//method for sale Transaction with data provided by the Wine object.
	public double saleTransaction(Wine wineModel){
		double wineTransSale = (wineModel.getNumBottles()*wineModel.getCostBottle());
		return wineTransSale;
	}
	//method for sale updated Current balance with data provided by the Wine object.	
	public String saleBalance(Wine wineModel){
		currBalance = currBalance + (wineModel.getNumBottles()*wineModel.getCostBottle());
		if (currBalance>=0){
			return (String.format("%.2f", currBalance));
		}
		else{
			bal = -1*currBalance;//negative values presented as CR balances in LWMGUI
			return (String.format("%.2f CR", bal));
		}
	}
	//Accessor method to input Customer name to GUI 
	public String getCustName(){
		return custName;
	}
}



