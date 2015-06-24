package JavaBall;


import java.util.*;

import javax.swing.JOptionPane;

public class RefereeBank 
{
	//Instance variables	
	private final int MAX_REF_NO = 12; //max no of referees allowed
	private int numRefs; //keeps track of the number of referees
	private Referee [] refBank; //array of referees 

	//Constructs a RefreeBank object with no referees
	public RefereeBank()
	{
		refBank = new Referee [MAX_REF_NO];
		numRefs = 0;	//keeps track of the number of refs in the array	
	}

	/*
	 * This method adds referees to the RefereeBank (array) by passing in an ordered string 
	 */
	public void addRef (String refDetails)
	{
		Referee addRef = null; //A new Referee

		addRef = new Referee(refDetails);	//Pass details to new Referee
		refBank[numRefs]=addRef; //Put into array at next free position

		//Add one to the number of referees stored in the array
		numRefs++;
	}

	/*
	 * This method takes a Referee name and generates and ID and uses
	 * this to construct a new Referee and store it the array (other details 
	 * are blank). Only adds if there is space.
	 */
	public void addNewRef (String fName, String sName)
	{	
		Referee addRef = null;
		if (numRefs < MAX_REF_NO) //if there is space
		{
			addRef = new Referee(fName, sName); //pass the name to a new Referee object
			String refID = generateRefID(fName, sName);
			addRef.setRefID(refID); //set the new Referee's ID.

			refBank[numRefs]=addRef; //Add to the array.
			numRefs++; //increase the number of Refs



		}
		else //a space in the referee bank is not available
		{
			JOptionPane.showMessageDialog(null, "Our refree fulfillment is currently full and "
					+ "no new refrees can currently be entered.", 
					"Confirmation", JOptionPane.ERROR_MESSAGE);
			return;  //Return from adding the class.	
		}
	}

	/*This method takes all the details of a Referee from the RefereeGUI and overwrites the
	 * corresponding position in the refBank...	
	 */
	public void updateRefDetails (String refDetails, String refID)
	{
		//Get the position of the referee in the Array
		int arrayPos = this.getRefPosition(refID);

		//Create a new Referee with the details
		Referee updateRef = new Referee(refDetails);

		//overwrite the found position with the new Referee
		refBank[arrayPos] = updateRef;
	}

	//A method to generate the ID of a referee from a string 

	public String generateRefID(String forename, String surname) 
	{	
		//Make sure the first and second letters are represented by uppercase letters

		String forenameFirstLetter = forename.substring(0, 1).toUpperCase();
		String surnameFirstLetter = surname.substring(0, 1).toUpperCase();

		// Initial characters generated from the forename and surname of a Ref
		String initChars = forenameFirstLetter + surnameFirstLetter;			

		int index = 1;	// the first index for an ID
		String refID = initChars+index; //ID = Chars + index
		int i=0; //i counts through the array looking for matching IDs

		while (i < numRefs)  //check through all Referees in the array
		{
			if (refBank[i].getRefID().substring(0, 3).equals(refID)) //if the ID exists
			{
				index++; //increase the index
				i = 0; //reset i to look again - NB do we need this??
			}		
			refID = initChars+index; //change the refID
			i++; //keep looking 
		}	
		return refID;	//return the ref ID		
	}

	//Returns the number of referees that are currently in the bank
	public int getNumRefs()
	{	
		return numRefs;
	}	

	//A method to return whether a name exists or not...
	public boolean currentName (String fName, String sName)
	{
		boolean isCurrent = false; //it doesn't exist

		for (int i = 0; i<numRefs; i++)
		{
			if (fName.equalsIgnoreCase(refBank[i].getRefFName()) && sName.equalsIgnoreCase(refBank[i].getRefSName()))
			{	

				isCurrent= true; //checking if the ID is currently in use.
			}
		}
		return isCurrent;
	}

	/*
	 * A method to return a referee given the name of that referee.
	 */
	public Referee getRefWithName(String fName, String sName)
	{
		Referee refFind = null; //initialise returning referee
		int i=0;
		boolean found = false; //boolean to look for matching referee

		while ((i < numRefs) && (!found))
		{
			if (fName.equalsIgnoreCase(refBank[i].getRefFName())) //FName matches
			{
				if (sName.equalsIgnoreCase(refBank[i].getRefSName())) //SName matches
				{
					refFind =refBank[i];
					found = true; 
				}			
			}
			i++; //Keep looking for a match
		}
		return refFind;
	}

	/*
	 * A method that sorts the referees array in order of ID (lexographically).	
	 */
	public Referee [] sortReferees()
	{			
		Referee [] sortedReferees =new Referee [MAX_REF_NO];

		int i=0;//index for in (refBank)
		int j=0;//index for out (sorted referees)

		while ((i < MAX_REF_NO) && (j < numRefs))
		{
			if (refBank[i]==null)
			{
				i++; //only i moves on by one, j remains the same (unfilled)
			}
			else 
			{
				sortedReferees[j]=refBank[i];
				i++;
				j++; //move i and j forward by one.
			}		
		}
		//Sort the classes on ID
		Arrays.sort(sortedReferees, 0, numRefs);
		return sortedReferees;	//return sorted classes
	}	

	/*A method to return a referee at particular position in the array*/

	public Referee getRefAtPosition (int pos)
	{	
		Referee ref = refBank[pos];	
		return ref;
	}

	/*A method to search for and output the position of the Referee 
	 * in the array given the referee ID 
	 */
	private int getRefPosition (String ID)
	{
		int listPos=0; //possible bad choice of initialization,

		int i=0;
		boolean isFound = false; //boolean to look for matching referee

		//While not at end of Referee array && ID not found
		while ((i < numRefs) && (!isFound))
		{
			if (ID.equalsIgnoreCase(refBank[i].getRefID())) //ID matches
			{

				listPos=i;			
				isFound = true; 
			}
			i++; //Keep looking for a match
		}
		//This would only be called if an internal error happened and ID not found
		if (!isFound)
		{
			System.out.print("This ID is not in the RefereeBank!");			
		}

		return listPos;	
	}

	/*
	 * A method to delete a referee given the name of the referee
	 * 
	 */
	public void deleteRefWithName(String fName, String sName)
	{
		//Find Referee for deletion
		Referee forDeletion = this.getRefWithName(fName, sName);

		//Find the position of the refree in the array
		int listPosition = getRefPosition(forDeletion.getRefID());

		refBank[listPosition]=null; //clear the position in the array
		forDeletion=null; //Delete the Referee
		numRefs--; //subtract one from the number of classes	

		refBank = this.sortReferees(); //reorganise and sort the array
	}

	/*
	 * 1. refBank sorted by number of matches (sortRefBankByNumMatches method called). 
	 * 2. Qualification level is next check 
	 * 3. sorted into three arrayLists ; home ref, adjacent ref and away ref to describe relationship 
	      between referee home and match location.
	 * 4. The three arraylists are then added in order of priority home:adjacent:away, providing 
	 * 	  the list of suitable refs. Note that the end size is not numRefs as those non-suited refs
	 * 	  are not included.
	 */
	public  ArrayList<Referee> processRefAllocation(String matchLevel, String matchLocation, int weekNo)
	{

		ArrayList<Referee> suitHomeRef = new ArrayList<Referee>();//refs with match at home
		ArrayList<Referee> suitAdjRef = new ArrayList<Referee>();//refs with match in adjacent area
		ArrayList<Referee> suitAwayRef = new ArrayList<Referee>();//refs willing to travel north/south
		ArrayList<Referee> suitRef = new ArrayList<Referee>();
		int i=0;//index for in (refBank)


		Referee [] sortRefs = sortRefBankByNumMatches(refBank);//refs sorted on ascending order of matches

		while (i < numRefs) 
		{
			if (sortRefs[i]==null)
			{
				i++; //only i moves on by one
			}
			else 
			{
				for (i = 0; i < numRefs; i++)
				{
					//ref qualified at level 1 can only supervise junior games, qualified > 1 can supervise any
					//this disqualifies refs at level 1 from supervising senior matches.
					while (i < numRefs &&((sortRefs[i].getQualLevel() == 1) && (matchLevel.equals("Junior"))||
							(sortRefs[i].getQualLevel() >=2 )))
					{								
						if (i < numRefs && matchLocation.equals(sortRefs[i].getHomeArea()))	//ref home same as match
						{
							Referee suitableRef = sortRefs[i];
							suitHomeRef.add(suitableRef);

							i++;

						}
						//is the referee's home adjacent to matchLocation and are they willing to referee that match?
						else if (i < numRefs &&(((matchLocation.equals("North"))&& sortRefs[i].canGoNorth()&& sortRefs[i].getHomeArea().equals("Central"))
								||((matchLocation.equals("Central"))&& sortRefs[i].canGoCentral())//both North & South are adjacent
								||((matchLocation.equals("South"))&& sortRefs[i].canGoSouth()&& sortRefs[i].getHomeArea().equals("Central"))))
						{
							Referee suitableRef = sortRefs[i];
							suitAdjRef.add(suitableRef);
							i++;
						}
						//is ref willing to travel from North to South or vice versa.
						else if (i < numRefs &&(((matchLocation.equals("North"))&& sortRefs[i].canGoNorth())
								||((matchLocation.equals("South"))&& sortRefs[i].canGoSouth())))
						{
							Referee suitableRef = sortRefs[i];
							suitAwayRef.add(suitableRef);
							i++;
						}
						else
						{
							i++;
						}
					}
				}
			} 
		}
		suitRef.addAll(suitHomeRef);//first choice in ascending order of matches added
		suitRef.addAll(suitAdjRef);//second choice in ascending order of matches added to end
		suitRef.addAll(suitAwayRef);//third choice in ascending order of matches added to end
		return suitRef;//create an ArrayList in the order HomeRefs:AdjacentRefs:AwayRefs.
	}

	/*
	 * use of comparator to sort by number of matches in ascending order.
	 */
	public Referee [ ] sortRefBankByNumMatches(Referee[] refBank2)
	{
		//Comparator from Horstmann p659 & p896; Stack Overflow & docs.oracle.com
		Referee sortedOnNum [ ] = new Referee [numRefs];
		Comparator<Referee> comparator = new Comparator<Referee>() {
			//"The method has a signature that is override-
			//equivalent to that of any public method declared in Object"
			//- deals with use of sort. Using an inner class (docs.oracle.com)

			public int compare(Referee a, Referee b) {
				int oi = a.getNumMatches();
				int oo = b.getNumMatches();
				if (oi < oo) return -1;
				if (oi > oo) return 1;
				return 0;
			}
		};
		//create a copy of the refBank array to be sorted.
		int i=0;//index for in (refBank)
		int j=0;//index for out (sorted referees)

		while ((i < MAX_REF_NO) && (j < numRefs))
		{
			if (refBank[i]==null)
			{
				i++; //only i moves on by one, j remains the same (unfilled)
			}
			else 
			{
				sortedOnNum[j]=refBank[i];
				i++;
				j++; //move i and j forward by one.
			}		
		}
		//Sort the copy of the array on Number of Matches using comparator 
		Arrays.sort(sortedOnNum, comparator);
		return sortedOnNum;
	}

	public boolean isRefBankFull()
	{
		if (numRefs < MAX_REF_NO)
		{
			return false;
		}
		else
		{
			return true;
		}

	}
}


