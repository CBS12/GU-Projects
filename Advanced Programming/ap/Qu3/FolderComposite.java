package Qu3;

/**
 * Composite Pattern - this represents the composite
 * A composite is a collection of resources(a folder)
 * such as files or a mix of files and folders resulting in 
 * subfolders in some cases
 * Shared methods with the FilingComponent interface
 */
import java.util.ArrayList;

public class FolderComposite implements FilingComponent {
	// The composite class
	private String name;
	// The array list can include anything that implements
	// FilingComponent: leaves, composites,  
	private ArrayList<FilingComponent> children;//a collection of resources
	//constructor
	public FolderComposite(String name) {
		this.name = name;
		children = new ArrayList<FilingComponent>();
	}
	//lists out documents and folders
	public String display() {
		String s = "Folder: " + name + " containing: "+ "\n";
		//iterate through composites and prints out names of contents
		for(FilingComponent c : children) {
			s += "\t"+ c.display() + "\n";
		}		
		return s;
	}
	//file/folder/resource size
	public Integer totSize() {
		Integer size = 0;
		//aggregates the sizes of each of the resources
		for(FilingComponent c: children) {
			size += c.totSize();
		}
		return size;
	}	
	//calculates the number of files within the composite.
	public Integer totNumber(){
		Integer number = 0;
	//	Integer number = children.size();
		for(FilingComponent c: children) {
			number += children.size();
		}
		return number;
	}
	// Need add and remove methods to add and 
	// remove objects to and from the composite
	public void add(FilingComponent c) {
		children.add(c);
	}
	public void remove(FilingComponent c) {
		children.remove(c);
	}
}