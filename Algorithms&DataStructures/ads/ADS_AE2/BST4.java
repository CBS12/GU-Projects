package ADS_AE2;



/**
 * This class implements a binary search tree with Nodes containg a pair of objects
 * X, Y implementing the Relation interface. Object X implements Comparable to 
 * facilitate traversal through the tree. It is assumed that Object X will be 
 * compatible with casting to comparable. Efficiency is calculated on the basis of the tree
 * being balanced. i.e. O(log(n)) vs O(n) for unbalanced. Complexities for initialization
 * and simple methods have not been included.
 * @author 2160196S
 *ERRORS resulting from structure
 *example: methods being duplicated in inner class and BST class. To resolve this in future I 
 *would probably try a structure where the interface has methods applied to the pairs & nodes and perhaps
 *another class(abstract) or interface to deal with the methods on the BST.
 *
 *Also resulted in issues printing out to a text file. To be revisited!
 *
 *Unfortunately the listing of the tree at present is all to the console, all other
 *tests have been printed out to "out.txt"
 *
 *The methods for deletion follow the methods in the course notes closely, in addition I referred to 
 *'Big Java: Late Objects' by Cay Horstmann.
 *
 *
 * @param <X>
 * @param <Y>
 */



public class BST4<X,Y> implements Relation <X,Y>{

	//Each BST Pair is  binary search tree header.

	private BST4.Node <X,Y> root;//start point
	protected Node<X,Y> left, right;//children of node
	//public <X,Y> element;
	protected X elemX;//Object X instance variable
	protected Y elemY;//Object Y instance variable
	public BST4(){
		//construct an empty BST
		root = null;
	}

	//======================================================================
/*
 * inner class to implement methods on the Node elements
 * 
 */

	// ///////Inner Class ///////
	public static class Node<X,Y> {
		
		private String message;
		public Node<X,Y> left, right, root;
		//public <X,Y> element;
		public X elemX;
		public Y elemY;
		//constructor
		public Node(X elemX, Y elemY){
			this.elemX = elemX;
			this.elemY = elemY;
			left = null;//new node, so no children
			right = null;//new node, so no children
		}			
		//-----------------------------------------------------------------
		/*
		 * Methods from notes,
		 * If node to be deleted is a parent node 
		 * complexity at best O(log(n)) for deletion
		 */
		
		public Node<X,Y> deleteParent() {
			if (this.left == null)//no child on left
				return this.right;//go to child on right
			else if (this.right == null)//no child on right
				return this.left;//go to child on left
			else { // this node has two children
				this.elemX = this.right.getLeftX();//get X from child
				this.elemY = this.right.getLeftY();//get Y from child
				this.right = this.right.deleteLeft();//delete old child node
				return this;	
			}
		}
		//-----------------------------------------------------------------
		/*
		 * Node superseded by left child as priority. If left child is null,
		 * then right child becomes parent.
		 * Removing an element O(log(n)) from a BST
		 * @return Node<X,Y>
		 */
		public Node<X,Y> deleteLeft() {
			if (this.left == null) 
				return this.right;//no left child, use right
			else {
				Node<X,Y> parent = this; 
				Node<X,Y> curr = this.left;
				while (curr.left != null) {
					parent = curr;
					curr = curr.left; //replaces parent node with left child node
				}
				parent.left = curr.right;//or replaces parent node with right child node
				return this;
			}
		}
		//-----------------------------------------------------------------
		/*
		 * uses recursion to go through branches to move subsequent children up
		 * @return X
		 * Deleting an element in a BST O(log(n))
		 */
		private X getLeftX() {
			Node<X,Y> curr = this;
			while (curr.left != null){ //if this node exists
				curr = curr.left;//replace with left child
			}
			return curr.getX();
		}
		//-----------------------------------------------------------------
		/*
		 * Duplicate of X function, not an efficient approach
		 */
		private Y getLeftY() {
			Node<X,Y> curr = this;
			while (curr.left != null){
				curr = curr.left;
			}
			return curr.getY();
		}

		//-----------------------------------------------------------------
		//Accessor methods to get and set X and Y			
		
		public X getX(){
			return elemX;
		}
		
		public void setX(X elemX){
			this.elemX=elemX;			
		}
		
		public Y getY(){
			return elemY;
		}
		public void setY(Y elemY){
			this.elemY=elemY;	
		}
		//method to clear a node of X and Y objects
		public Node<X,Y> clear(X elemX, Y elemY) {
			Node<X,Y> curr=this;	
			curr.setX(null);
			curr.setY(null);
			return curr;
		}
		//-----------------------------------------------------------------
		
		/*
		 * Return true if and only if the Relation has
		 * the same value Y.
		 * compares current node Y  with
		 * target X  to establish equality.
		 * comparison tests O(n^2)		 
		 */
		
		public boolean equalsY(Y thaty) {
			Node<X,Y> curr = this;				
			if (!curr.getY().equals(thaty)){
				message = "false";
				return false;
			}
			message = "true";
			return true;						
			//-----------------------------------------------------------------
		}
		/*
		 *compares current node X & Y  with
		 *target X and Y  to establish equality.
		 *Return true if and only if the Relation has
		 *the same value X and Y.
		 *comparison tests O(n^2)
		 */
		
		
		public boolean equalsXY(X thatx, Y thaty) {
			Node<X,Y> curr = this;
			if((curr.getX().equals(thatx))
					&&(curr.getY().equals(thaty))){
				message = "true";
				return true;
			}
			else{
				
				message = "false";
				return false;				
			}
		}
			//-----------------------------------------------------------------
			/*
			 *compares current node X  with
			 *target X  to establish equality.
			 *Return true if and only if the Relation has
			 *the same value X.
			 */
			public boolean equalsX(X x) {
				Node<X,Y> curr = this;
				if(curr.getX().equals(x)){
					message = "true";
					return true;
				}
				message = "false";
				return false;			
			}
			//-----------------------------------------------------------------
			

		
		//-----------------------------------------------------------------
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 * overwriting toString method to ouput paired objects
		 */
		public String display() {
			return ("" + elemX + ", " +  elemY);			
		}				
	}
	// /////End of Inner Class //////////

	//implemented methods from interface
	public X getX(){
		return elemX;
	}

	//implemented methods from interface
	public Y getY(){
		return elemY;
	}
	
	
			//-----------------------------------------------------------------
	@SuppressWarnings("unchecked")
	
	// Finding an element O(log(n))
	//Binary search, best Complexity O(log(n))
	
	public	void findXY (X targetX, Y targetY){//X used to create BST
		int direction = 0;//direction based on size comparison between X objects
		Node<X,Y> curr = root;//start at top
		boolean allDone = false;

		while(curr != null && !allDone){
			//compare X values
			direction = ((Comparable<X>) curr.getX()).compareTo(targetX);
			//check for both X  (direction ==0)and Y  being the same as the Node element
			if (direction == 0 && curr.getY().equals(targetY)){

				System.out.println("Values ("+curr.getX()+","+curr.getY()+ ") found.");
				allDone = true;//match found
				return;	
			}
			//match not found yet
			else if (direction > 0){
				curr = curr.left; //check left child node
			}//match not found yet
			else if (direction < 0 ){
				curr = curr.right;//check right child node
			}
			//reach the end of the tree	, no child nodes
			else if (curr.left==null && curr.right == null){
				allDone = true;//finished without finding a match
				System.out.println("(" + targetX + "," + targetY+ ")  Not found.");
				return;
			}
		}				
		return;				
	}

	//-----------------------------------------------------------------
@SuppressWarnings("unchecked")
	
	// Finding an element O(log(n))
	//Binary search, best Complexity O(log(n))
	
	public	void clearXY (X targetX, Y targetY){//X used to create BST
		int direction = 0;//direction based on size comparison between X objects
		Node<X,Y> curr = root;//start at top
		boolean allDone = false;

		while(curr != null && !allDone){
			//compare X values
			direction = ((Comparable<X>) curr.getX()).compareTo(targetX);
			//check for both X  (direction ==0)and Y  being the same as the Node element
			if (direction == 0 && curr.getY().equals(targetY)){

				curr.clear(targetX, targetY);
				System.out.println("Values ("+curr.getX()+","+curr.getY()+ ") found.");
				allDone = true;//match found
				return;	
			}
			//match not found yet
			else if (direction > 0){
				curr = curr.left; //check left child node
			}//match not found yet
			else if (direction < 0 ){
				curr = curr.right;//check right child node
			}
			//reach the end of the tree	, no child nodes
			else if (curr.left==null && curr.right == null){
				allDone = true;//finished without finding a match
				System.out.println("(" + targetX + "," + targetY+ ")  Not found.");
				return;
			}
		}				
		return;				
	}

	//-----------------------------------------------------------------
	// Adding an element O(log(n)), similar to Binary search
	// where the element is inserted at the last null link.
	
	@SuppressWarnings("unchecked")
	public void add(X x, Y y) {
		int direction = 0;
		Node<X,Y> parent = null;//empty
		Node<X,Y> curr = root;//start at root
		for (;;) {
			if (curr == null) {//no node so insert in first position
				Node<X,Y> newNode = new Node<X,Y> (x, y);
				if (root == null)
					root = newNode; 
				
				//comparing X values, direction is normally left 
				//for < 0, my compares are wrong way round but consistently so.
				else if (direction > 0) //alphabetically/numerically smaller go left
					parent.left = newNode;
				else
					////alphabetically/numerically larger go right
					parent.right = newNode;
				return;
			}
			//if curr not null, check for a match
			direction = ((Comparable<X>) curr.getX()).compareTo(x);//check X
			if (direction == 0 && curr.getY().equals(y)){//check for X and Y both matching
				return;//no duplicates allowed
			}
			parent = curr;
			//check for matching X & non matching Y, matching X set to go left
			if (direction > 0 ||(direction == 0 && !curr.getY().equals(y))){
				curr = curr.left;
			}
			else{//else go right
				curr = curr.right;
			}
		}
	}
	//-----------------------------------------------------------------
	//Deleting an element O(log(n)), same as finding, 
	//in addition 'promoting' children to parent nodes
	//Binary search, best Complexity O(log(n))
	
	@SuppressWarnings("unchecked")//for cast to Comparable
	public void delete(X x, Y y) {
		int direction = 0;
		Node<X,Y> parent = null, curr = root;//start at top
		for (;;) {
			if (curr == null)//empty node
				return;//nothing to delete
			
			//compare X values to establish route through tree
			direction = ((Comparable<X>) curr.getX()).compareTo(x);
			//check for both X  (direction ==0)and Y  being the same as the Node element
			if (direction == 0 && curr.getY().equals(y)) {
				//when found identify as parent to delete
				Node<X,Y> del = curr.deleteParent();
				if (curr == root)//no children
					root = del;//no child nodes to consider
				else if (curr == parent.left)//node is parent to left child
					parent.left = del;//identify node as parent of left
				else
					parent.right = del;//else identify as parent of right
				return;
			}
			parent = curr;
			if (direction > 0)
				curr = parent.left;
			else

				curr = parent.right;
		}
	}

	//-----------------------------------------------------------------

	@SuppressWarnings("unchecked")
	//Deleting an element O(log(n)), same as finding, 
	//in addition 'promoting' children to parent nodes
	//Binary search, best Complexity O(log(n))
	//similar to delete process but while loop used to continue 
	//through to search for more instances.
	
	public	void removeAllXYatX (X targetX){//X used to create BST
		int direction = 0;
		Node<X,Y> curr = root;
		boolean allDone = false;

		while(curr != null && !allDone){

			if (curr.getX().equals(targetX)){
				Y anyY = curr.getY();
				delete(targetX, anyY);
				allDone = false;
			}
			direction = ((Comparable<X>) curr.getX()).compareTo(targetX);

			if (direction > 0 || direction == 0){
				curr = curr.left;
			}
			else if (direction < 0 || direction == 0){
				curr = curr.right;
			}
			allDone = false;

		}
		allDone = true;
		return;

	}

//	//-----------------------------------------------------------------
	//Finding an element O(log(n)), same as finding, 
	//in addition 'promoting' children to parent nodes
	//Binary search, best Complexity O(log(n))
	//same commenting as above but return the node rather than delete
	
	public	void findAllXYatX (X targetX){//X used to create BST

		findX(root, targetX);//sets process off from the root
	}

	private void findX (Node<X,Y> parent,X targetX){
		// Print, in ascending order, all the elements in the BST 
		// subtree whose topmost node is top.
		if (parent != null) {

			findX(parent.left, targetX);			
			if (parent.left != null && parent.left.getX().equals(targetX)){
				System.out.println(parent.left.display());
			}

			//write.println(parent.display());

			findX(parent.right,targetX);
			if (parent.right != null && parent.right.getX().equals(targetX)){
				System.out.println(parent.right.display());
			}
		}
	}

	//-----------------------------------------------------------------

	/*
	 * need to visit all nodes to check for value Y
	 * Not a binary search so using a post order traversal
	 * O(n) complexity
	 * (non-Javadoc)
	 * @see Relation#removeAllXYatY(java.lang.Object)
	 * 
	 */
	
	public void removeAllXYatY(Y that) {
		int direction = 0;
		Node<X,Y> curr = root;
		boolean allDone = false;
		if(curr!= null){
			if (curr.getY().equals(that)){//finds appropriate node
				X elemX = curr.getX();//returns X
				delete (elemX, that);//deletes Node
			}
			else if (curr.left == null){
				curr = curr.right;
			}
			else {
				curr = curr.left;
			}
			allDone = false;
		}
		return;	

	}
	
	//-----------------------------------------------------------------	
	
	/*
	 * (non-Javadoc)
	 * @see Relation#findAllXYatY(java.lang.Object)
	 * uses recursive methods to traverse the tree
	 */
	public	void findAllXYatY (Y targetY){//X used to create BST		

		findY(root, targetY);//sets process off from the root
	}

	private void findY (Node<X,Y> parent,Y targetY){
		// Print, in ascending order, all the elements in the BST 
		// subtree whose topmost node is top.
		if (parent != null) {

			findY(parent.left, targetY);			
			if (parent.left != null && parent.left.getY().equals(targetY)){
				System.out.println(parent.left.getX());
			}

			//write.println(parent.display());

			findY(parent.right,targetY);
			if (parent.right != null && parent.right.getY().equals(targetY)){
				System.out.println(parent.right.getX());
			}
		}
	}

	//-----------------------------------------------------------------
	/*
	 * methods to render BST as a list, in ascending order
	 */
	public void printTree () {
		print(root);
	}			

	private void print(Node<X, Y> parent) {
		// Print, in ascending order, all the elements in the BST 
		// subtree whose topmost node is top.

		if (parent != null) {
			print(parent.left);
			System.out.println(parent.display());		
			print(parent.right);
		}
	
	}


	public void displayTree(){
		displayT(root);
	}			

	private String displayT(Node<X, Y> parent) {
		// Print, in ascending order, all the elements in the BST 
		// subtree whose topmost node is top.		
		if (parent != null) {
			displayT(parent.left);
			
			displayT(parent.right);
		}
	
		return parent.display();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setX(X that) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(Y that) {
		// TODO Auto-generated method stub
		
	}
}
//-----------------------------------------------------------------	


