package P_AE3;


import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {

	private final int BASE_TIME = 9; 			//classes start at 9am.
	private final int LAST_CLASS_TIME = 15; 	//classes start at 3pm.
	private final int MAX_NUM_CLASSES = LAST_CLASS_TIME - BASE_TIME + 1;
	private FitnessClass [] FC = new FitnessClass [MAX_NUM_CLASSES];
	private File classFile;
	private double totAverage, overallAverage;
	private FitnessClass fc;
	private int numClasses;


	//---------------------------------------------------------------------------------  

	/**
	 * Constructs a default program with no classes
	 */
	public FitnessProgram() {
	}


	//---------------------------------------------------------------------------------  
	public void initData() {
		initData(null, null);//default
	}
	//---------------------------------------------------------------------------------  
	/**
	 * Imports data from the input files GUI to be processed and returned
	 * @param classesInFile
	 * @param attendancesFile
	 */
	public void initData(String classesInFile, String attendancesFile) {
		//methods to import from the GUI
		readClasses(classesInFile);
		readAttendances(attendancesFile);
	} 

	//---------------------------------------------------------------------------------  
	/**
	 * reads the classesIn file and generates the Fitness class objects
	 * @param classesInFile
	 */
	private void readClasses(String classesInFile) {

		classFile = new File(classesInFile);
		FileReader fileReader = getFileReader(classFile);//method holds the try/catch staements
		//create a Scanner object from the reader
		Scanner reader = new Scanner(fileReader);
		boolean done = false;
		while (reader.hasNextLine()) {
			//more lines to read, read next line from the input file
			String lineClass = reader.nextLine();
			done = true;
			String [] tokens = lineClass.split("[ ]+");//string line from file broken into elements
			int startTime = Integer.parseInt(tokens[3]);//convert from string to integer
			int index = startTime - BASE_TIME;//avoiding "magic" numbers to get an index.
			//generate a FitnessClass object by inputting parameters
			FitnessClass fc = new FitnessClass(index, tokens[0], tokens[1], tokens[2], startTime);
			FC[index] = fc;

			done = true;

			//sortFitnessClass(fc);//method to put fitnessclasses in index order
		}
		setEmptyClasses();//method to deal with empty classes	
		reader.close();
	}	
	//---------------------------------------------------------------------------------	
	/**
	 * Searches for an empty class and enters the details for the class at the same index
	 * this is part of the initial setting up process
	 */
	private void setEmptyClasses() {
		for (int i = 0; i < FC.length; i++) {//check through the array for empty classes
			if (FC[i] == null) {//when empty class found, substitute required display details
				FitnessClass fc = new FitnessClass(i,"","Available","", BASE_TIME + i);
				fc.setIsEmpty(true);
				FC[i] = fc;
			}
		}
	}
	//---------------------------------------------------------------------------------  
	/**
	 * gets input from the GUI and sets it into a FitnessClass object using the shorter constructor
	 * @param idNew
	 * @param nameNew
	 * @param tutorNew
	 */
	public void setNewClasses(int i,String idNew,String nameNew, String tutorNew) {
		FitnessClass fc = new FitnessClass (i,idNew.toLowerCase(), nameNew.toLowerCase(), tutorNew.toLowerCase(), BASE_TIME + i);
		fc.setIsEmpty(true);
		FC[i] = fc;
		return;
	}
	//---------------------------------------------------------------------------------  
	/**
	 * gets file from GUI and opens to read, the ClassId is used to match to the class data
	 * FitnessClass object.
	 * @param attendancesFile
	 */
	private void readAttendances(String attendancesFile) {
		if (attendancesFile != null) {
			File attFile = new File (attendancesFile);
			FileReader attfilereader = getFileReader(attFile);
			Scanner attreader = new Scanner(attfilereader);

			boolean done = false;


			while (attreader.hasNextLine() && !done) {
				//more lines to read
				String lineClass = attreader.nextLine();
				String [] tokens = lineClass.split("[ ]+");//split elements
				String id = tokens[0];//class id 
				//uses a method to find the matching class data from FitnessClass array
				FitnessClass fc = findByClassID(id);
				//iterates through to collect each week attendance read from the file
				for (int i = 1; i < tokens.length; i++) {
					int n = Integer.parseInt(tokens[i]);
					int weekNumber = i-1;
					fc.setAttendance(weekNumber, n);//adds attendance data to the FitnessClass object
				}	
				done = false; //possibly still more lines to read
			}
			done = true;//no more lines to read	
			attreader.close();
		}
	}
	//---------------------------------------------------------------------------------  
	/**
	 * this method is used when the user types a classid into a textfield providing the parameter
	 * this could serve two purposes when 
	 * 'Add' button pressed - to identify an empty timeslot.
	 * 'Delete button - to find the location and replace the class with an empty Fitness Class.
	 */
	public FitnessClass findByClassID(String classID) {
		if (classID != null) {
			for (FitnessClass fc : FC) {
				if (fc != null && fc.getClassID() != null && fc.getClassID().equals(classID)) {
					return fc;
				}
			}
		}
		return null;
	}

	//---------------------------------------------------------------------------------  
	/**
	 * This returns the total number of available time slots.
	 */
	public int getNumberofClasses() {

		int numClasses = 0;
		boolean found = false;
		while (numClasses < FC.length && !found){
			for (FitnessClass fc : FC){
				if (fc.getClassName().equals("Available")){
					found = false;
				}
				else {
					numClasses++;
				}		
			}
			found = true;
		}
		return numClasses;
	}
	//---------------------------------------------------------------------------------  
	/**
	 * Adds a class to the program and interacts with GUI
	 * uses findClassAtID method
	 */
	public void addClass(String idNew,String nameNew, String tutorNew){//input from the GUI
		if(idNew.charAt(0)!= tutorNew.charAt(0)|| idNew.charAt(1) != nameNew.charAt(0)) {
			JOptionPane.showMessageDialog(null,"Incorrect format for ID. "
					+ "Use first letter of tutor name followed by first letter of the class with instance of the class, eg '1' or '2'.","Error", JOptionPane.ERROR_MESSAGE );	
		}
		else if(numClasses==FC.length){
			JOptionPane.showMessageDialog(null,"Programme full. "
					+ "No new classes can be added.","Error", JOptionPane.ERROR_MESSAGE );	
		}
		else if (findByClassID(idNew)!=null) {
			JOptionPane.showMessageDialog(null,"Class " + idNew + 
					"is already on the timetable. Try increasing the class number by 1.","Error", JOptionPane.ERROR_MESSAGE );
		}		
		else{
			for (int i =0; i< FC.length; i++){
				if (FC[i].isEmpty()){
					setNewClasses(i,idNew,nameNew,tutorNew);
					FC[i].setIsEmpty(false);
					return;
				}				
			}
		}
	}

	//---------------------------------------------------------------------------------  	
	/**
	 * Finds a class using classID and replaces it with an empty class
	 * modification of findClassAtID used here
	 */
	public void delete(String targetID) {

		for (int i = 0; i < FC.length; i++) {
			FitnessClass fc = FC[i];
			if (fc.getClassID().equals(targetID)) {//searches for a matching id
				int index = fc.getIndex();//uses index to insert empty class details
				FC[index] = new FitnessClass(i,"","Available","", BASE_TIME + i);//inserts empty class details
				FC[index].setIsEmpty(true);
				return;
			}
		}
		JOptionPane.showMessageDialog(null,"This class does not exist","Error", JOptionPane.ERROR_MESSAGE );
		System.exit(0);
		//fitness class does not exist

	}
	//---------------------------------------------------------------------------------  	
	/**
	 * sets fitness classes at their place/in order
	 * @param fc
	 */
	public void sortFitnessClass(FitnessClass fc) {
		FC[fc.getIndex()] = fc;
	}
	//---------------------------------------------------------------------------------  
	/**
	 * FileReader helper
	 * @param filename
	 * @return
	 */
	private FileReader getFileReader(File filename) {
		try {
			return new FileReader(filename);
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
			return null;
		}
	}
	//---------------------------------------------------------------------------------  
	/**
	 * at save and exit writing to a file
	 */
	private FileWriter getFileWriter(File file) {
		try {
			return new FileWriter(file);
		} catch (IOException e) {
			System.err.println("Error writing to file");
			return null;
		}
	}
	//---------------------------------------------------------------------------------  
	/**
	 * writes the data out to the file.
	 * @param classesOutFile
	 */
	public void writeData(String classesOutFile) {
		try {
			File outFile = new File("ClassesOut.txt");
			FileWriter writer = getFileWriter(outFile);//method contains the try/catch
			writer.write(getFileReport());//get StringBuilder report to add to the file.
			writer.close();
		} 
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	//--------------------------------------------------------------------------------- 
	/**
	 * method for returning the report to the ClassesOut file.
	 * @return
	 */
	public String getFileReport() {

		StringBuilder sb = new StringBuilder();

		for (FitnessClass fc : FC) {//iterates over the array
			if (!fc.isEmpty()){//if a class exists
				sb.append(fc);//add the FitnessClass object's toString to the report for printing
				sb.append("\n");//next line
			}
		}

		return sb.toString();
	}

	//-------------------------------------------------------------------------------  
	/**
	 * Finds the overall weekly average of all classes
	 * @return
	 */
	public  double getTotAverage(){
		int count = 0;//number of classes with recorded attendances
		totAverage = 0.0;
		for (FitnessClass fc : FC) {//iterate over the array
			if (fc.getAverage() > 0){//if the average attendance is greater than zero, increase the number of classes.
				count++;
			}
			totAverage = totAverage + fc.getAverage();//accumulate the averages

		}
		overallAverage = totAverage/count;//calculate the average of the average
		return overallAverage;
	}
	//-------------------------------------------------------------------------------  
	/**
	 * Finds the index of an object based on its classID
	 * used for deletion process
	 */
	private int findIndex(String classID) {
		FitnessClass fc = findByClassID(classID);
		if (fc == null) {
			return -1;
		} 
		else {
			return fc.getIndex();
		}
	}

	//--------------------------------------------------------------------------------- 

	/**
	 * Prepares the report for the main GUI display
	 * @return
	 */

	public String getInitReport() {
		StringBuilder sb = new StringBuilder();
		for (FitnessClass fc : FC) {
			sb.append(String.format(" %-10s", fc.getClassTimeSpan()));
		}
		sb.append("\n");
		for (FitnessClass fc : FC) {
			sb.append(String.format("  %-9s", fc.getClassName()));
		}
		sb.append("\n");
		for (FitnessClass fc : FC) {
			sb.append(String.format("  %-9s", fc.getTutorName()));
		}
		sb.append("\n");
		return sb.toString();
	}
	//--------------------------------------------------------------------------------- 

	/**
	 * will be using compareTo method to sort attendance report by average
	 */
	public FitnessClass [] sortByAverage() {

		FitnessClass [] aveSortedFC = new FitnessClass[MAX_NUM_CLASSES];

		int i = 0; //index for in (fitnessClasses)
		int j = 0; //index for out (sorted FitnessClasses)
		while ((i < MAX_NUM_CLASSES) && (j < MAX_NUM_CLASSES)){
			if (FC[i] == null)
			{
				i++;
			}
			else
			{
				aveSortedFC[j] = FC[i];
				i++;
				j++;
			}
		}

		Arrays.sort(aveSortedFC,0, MAX_NUM_CLASSES);
		return aveSortedFC;

	}
	//---------------------------------------------------------------------------------
}


