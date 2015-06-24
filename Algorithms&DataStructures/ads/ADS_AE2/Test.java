package ADS_AE2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Main method to run a test of BST4 (implements Relation)
 * @author Stewart home
 *
 */


public class Test {

	public static void main(String[] args) throws IOException {
		BST4<String,Integer> animals = new BST4<String,Integer>();
		BST4<String,String> names = new BST4<String,String>();

		PrintWriter out = new PrintWriter("out2.txt");
		try {
			out = new PrintWriter("out2.txt");
		} catch (FileNotFoundException e) {
			System.err.println("File error");
			e.printStackTrace();
		}


		animals.add("lion", 5);//first node is fairly central,leads to a more balanced tree
		animals.add("fox", 6);
		animals.add("rat", 7);
		animals.add("cat", 3);
		animals.add("pig",4);
		animals.add("dog", 5);
		animals.add("tiger",3);
		animals.add("rat", 3);
		animals.add("rat", 1);
		animals.add("horse",2);
		animals.add("goat", 9);
		animals.add("chicken",10);
		animals.add("fox", 8);
		//it would have been better to design the BST to allow user to select
		//criteria for adding nodes, either X or Y
		names.add("Mary","Smith");
		names.add("Bob","Smith");
		names.add("Joe","Bloggs");
		names.add("Jim","Harris");
		names.add("Mary","Andrews");
		names.add("Arthur","Smith");
		names.add("Larry","Carson");


		System.out.println("Print out of BST Node data:  'names.printTree();'");
		out.println("Print out of BST Node data:  'names.printTree();'");
		names.printTree();


		names.delete("Mary", "Smith");
		System.out.println("\nPrint out of BST Node data: \n'names.delete(Mary, Smith)' \n'names.printTree();'");
		out.println("\nPrint out of BST Node data: \n'names.delete(Mary, Smith)' \n'names.printTree();'");
		names.printTree();

		names.clearXY("Bob", "Smith");
		System.out.println("\nPrint out of BST Node data: \n'names.clearXY(Bob, Smith)' \n'names.printTree();'");
		out.println("\nPrint out of BST Node data: \n'names.clearXY(Bob, Smith)' \n'names.printTree();'");
		names.printTree();


		System.out.println("\nPrint out of BST Node data:  'animals.printTree();'");
		out.println("\nPrint out of BST Node data:  'animals.printTree();'");
		animals.printTree();
		//writer.displayTree();


		System.out.println("\nChecks BST and asserts (goat,9) present: 'findXY(X x, Y y);'");
		out.println("\nChecks BST and asserts (goat,9) present: 'findXY(X x, Y y);'");
		animals.findXY("goat",9);


		System.out.println("\nChecks BST and asserts (goat,12) not present: 'findXY(X x, Y y);'");
		out.println("\nChecks BST and asserts (goat,12) not present: 'findXY(X x, Y y);'");
		animals.findXY("goat",12);


		System.out.println("\nSearches and prints out all (X,Y) with target X: findAllXYatX ('rat');");
		out.println("\nSearches and prints out all (X,Y) with target X: findAllXYatX ('rat');");
		animals.findAllXYatX ("rat");

		System.out.println("\nSearches and prints out all (X,Y) with target Y: findAllXYatY (3);");
		out.println("\nSearches and prints out all (X,Y) with target Y: findAllXYatY (3);");
		animals.findAllXYatY(3);


		System.out.println("\nDeletes target (X,Y) : delete (goat,9);");
		out.println("\nDeletes target (X,Y) : delete (goat,9);");
		animals.delete("goat",9);//chosen to test removal of a node
		animals.printTree();// ("goat",9) removed.


		System.out.println("\nDeletes target (X,Y) : delete (goat,9);");
		out.println("\nDeletes target (X,Y) : delete (horse, 2);");
		animals.delete("horse",2);//chosen to test left side & parent node
		animals.printTree();

		System.out.println("\nDeletes target (X) : delete (fox);");
		out.println("\nDeletes target (X) : delete (fox);");
		animals.removeAllXYatX("fox");//chosen to test removal of repeat
		animals.printTree();// ("fox",6) ("fox",8)removed.


		System.out.println("\nDeletes target (X) : delete (rat);");
		out.println("\nDeletes target (X) : delete (rat);");
		animals.removeAllXYatX("rat");//chosen because it has some in sequence ---causing problems
		animals.printTree();// ("rat", 7)  (rat,1) (rat,3)removed.# Not quite right
		System.out.println("ERROR: It is only deleting two of the three!!!!!");
		out.println("ERROR: It is only deleting two of the three!!!!!");

		System.out.println("\nSearches and deletes all (X,Y) with target Y: removeAllXYatY (3);");
		out.println("\nSearches and deletes all (X,Y) with target Y: removeAllXYatY (3);");
		animals.removeAllXYatY(3);
		animals.printTree();
		System.out.println("ERROR: Not working!!!!!");
		out.println("ERROR: Not working!!!!!");



		out.close();



	}
}

