package Qu3;

/**
 * Composite Pattern - this is the leaf
 * Each leaf represents a file
 * @author 2160196S
 *
 */



public class DocLeaf implements FilingComponent {
	//implements the Component interface
	
	//instance variables
	private String name;
	private Integer size;
	private Integer number;
	//constructor for file
	public DocLeaf(String name,Integer size) {
		this.name = name;
		this.size = size;
	}
	// Implements the methods defined in FilingComponent
	public String display() {
		String s = "\t"+name;//indents name of document
		return s;
	}
	
	//method to find total file size
	public Integer totSize() {
		return size;//size of file in bytes
	}
	//method to find total number of files/resources

	public Integer totNumber() {
		number = 1;//1 item for each file
		return number;
	}
} 