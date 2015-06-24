package Qu3;

/**
 * Composite Pattern - this class represents the component
 * an interface which allows composition of a 'resource' 
 * to include leafs and also other composites.
 * 
 * @author 2160196S
 *
 */


public interface FilingComponent {
	// This is the top-level interface for the 
	// composite patterns.Including 'shared' methods.
	public Integer totSize();
	public Integer totNumber();
	public String display();
}