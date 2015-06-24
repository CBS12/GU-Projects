package ADS_AE2;

/**
 * An interface to provide methods for actions
 * on a Pair of objects.
 * @author2160196S
 *
 * @param <X>
 * @param <Y>
 */
public interface Relation <X, Y>{

	//Each Relation <X,Y> is a homogenous pair
	//whose elements are of type X and Y.
	

	///////Accessors/////////

	
	public X getX();
	//Return the first element of the Relation
	
	public Y getY();
	//Return the second element of the Relation
	
	public void setX(X that);
	//Set the first element of the Relation
	
	public void setY(Y that);
	//Set the second element of the Relation
	
	public void findXY (X x, Y y);
	//Return true if and only if the Relation has
	//the same value X AND same value Y.
	
	
	///////Transformers/////////
	public void clear();
	//Make this Relation empty;

	public void add (X x,Y y);
	//Add Relation (x, y) after the last element

	public void delete(X x, Y y);
	//Delete a Relation with the values
	//x and y.

	public void findAllXYatX (X x);
	//Find all Relations with the value
	//X x.
	
	public void removeAllXYatX (X x);
	//Delete all Relations with the value
	//X x.

	public void findAllXYatY (Y that);
	//Find all Relations with the value
	//Y y.
	
	public void removeAllXYatY (Y that);
	//Delete all Relations with the value
	//Y y.

	public String toString();
	//Return the Relation in String format.
	
}
