package JavaBall;


/** Defines an object representing a single referee
 *
 *Comparable used to compare two referees in order to assign them to matches
 */

public class Referee implements Comparable<Referee> 
{
	//Instance variables

	private String refID; //Unique ID for class
	private String fName; //non-unique first name
	private String sName; //non-unique second name
	private String qualification; //Qualification Level
	private int qualLevel;
	private int numMatches; //Number of matches assigned to a referee
	private String homeArea; //Area where the Referee lives
	private String visitAreas; //Areas in YYN format of where a Referee will visit.

	//Default constructor to clear all the variables
	public Referee ()
	{
		//Add 
		refID = ""; 
		fName = "";
		sName = "";
		qualification = "";
		numMatches = 0;
		homeArea ="";
		visitAreas="";
	}

	//This constructor is initialised on complete Referee data passed as a String

	public Referee (String refereeDetails)
	{	
		//Split the String into words
		String [] data = refereeDetails.split("[ ]+");

		/*
		 * Assuming there is always 6 pieces of data in the correct format 
		 * we can set the instance variables from the string tokens
		 */
		refID = data[0].trim();
		fName = data[1].trim();
		sName = data[2].trim();
		qualification = data[3].trim();
		numMatches = Integer.parseInt(data[4].trim());
		homeArea = data[5].trim();
		visitAreas = data[6].trim();
		qualLevel=Integer.parseInt(qualification.substring(3));

	}

	//A constructor for simply adding a new referee using the Referee's name only.	
	public Referee (String firstName, String surName)
	{
		refID =""; 
		fName = firstName;
		sName = surName;
		qualification = "";
		numMatches = 0;
		homeArea ="";
		visitAreas="";
		qualLevel = 0;
	}

	/*
	 * Methods to individually set the instance variables.
	 * Not sure we use these in program but generally useful.
	 */
	public void setRefID(String ID)
	{
		refID = ID;
	}	
	public void setRefFName(String name)
	{
		fName = name;
	}
	public void setRefSName(String name)
	{
		sName = name;
	}	
	public void setQualLevel(String qLevel)
	{
		qualification = qLevel;
	}
	public void setNumMatches(int number)
	{
		numMatches = number;
	}
	public void setHomeArea(String area)
	{
		homeArea = area;
	}
	public void setVisitAreas(String XXX)
	{
		visitAreas = XXX;
	}

	/*
	 * 	These methods simply return the values of the instance variables	
	 */

	public String getRefID()
	{
		return refID;
	}	
	public String getRefFName()
	{
		return fName;
	}
	public String getRefSName()
	{
		return sName;
	}	
	public String getQualification()
	{
		return qualification;
	}
	public int getQualLevel()
	{
		return qualLevel;
	}

	public int getNumMatches()
	{
		return numMatches;
	}
	public String getHomeArea()
	{
		return homeArea;
	}
	public String getVisitAreas()
	{
		return visitAreas;
	}

	public String getFullName()
	{
		return fName+" "+sName;
	}

	// A method to return the all of the details as a Referee as a String
	public String getRefDetails () 
	{
		StringBuilder refDetails = new StringBuilder();

		refDetails.append(String.format("%-12s", this.getRefID()));
		refDetails.append(String.format("%-22s", this.getFullName()));
		refDetails.append(String.format("%-17s", this.getQualification()));
		refDetails.append(String.format("%9s %-10s", this.getNumMatches(),""));
		refDetails.append(String.format("%-13s", this.getHomeArea()));
		refDetails.append(String.format("%-13s", this.getVisitAreas()));

		return refDetails.toString(); 
	}

	//A method to compare Referees based on their IDs so that they can be ordered lexographically

	public int compareTo(Referee other) 
	{				
		return this.getRefID().compareTo(other.getRefID());

	}

	/*
	 * Methods to return booleans of whether a referee will be 
	 * willing to travel to different areas.
	 */

	public boolean canGoNorth(){
		if(this.visitAreas.substring(0, 1).equalsIgnoreCase("Y"))
		{
			return true;
		}else
		{
			return false;
		}

	}
	public boolean canGoCentral()
	{
		if(this.visitAreas.substring(1, 2).equalsIgnoreCase("Y"))
		{
			return true;
		}
		else
		{
			return false;
		}

	}   
	public boolean canGoSouth(){

		if(this.visitAreas.substring(2, 3).equalsIgnoreCase("Y"))
		{
			return true;
		}else
		{
			return false;
		}
	}
}
