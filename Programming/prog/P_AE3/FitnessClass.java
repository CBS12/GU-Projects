package P_AE3;


/**
 * Defines an object for a single fitness class incorporating
 * String classId,string classname, string tutorname and Start time from one
 * file and 5 weeks of attendance data linked to classID from another file.
 * 
 */


public class FitnessClass implements Comparable<FitnessClass> {

	private final int NUM_WEEKS = 5;
	private String classID, className, tutorName, classTimeSpan;
	private int index;
	private int startTime;
	private double averageAtt;
	private boolean isEmpty = false;
	private int [] weekAtt= new int[NUM_WEEKS];
	

	
	/**
	 * default constructor for FitnessClass
	 */ 
	public FitnessClass() {
	}

	//------------------------------------------------------------------------------------------
	/**
	 * Constructor for fitness class based on classesIn file
	 * and used for the GUI
	 */
	public FitnessClass(int d, String i, String c, String t, int s) {

		index = d;
		classID = i;
		className = c;
		tutorName = t;
		startTime = s;
		classTimeSpan = String.format("%2d - %2d",startTime, startTime + 1);
		isEmpty = false;
	}
	//------------------------------------------------------------------------------------------
	/**
	 * constructor also including attendance, used for attendance view.
	 * @param d
	 * @param i
	 * @param c
	 * @param t
	 * @param s
	 * @param w
	 */
	public FitnessClass(int d, String i,String c, String t,int s, int[] w) {

		index = d;
		classID = i;
		className = c;
		tutorName = t;
		startTime = s;
		classTimeSpan = String.format("%2d - %2d",startTime, startTime + 1);
		weekAtt = w;
		isEmpty = false;
		
	}
	//------------------------------------------------------------------------------------------
	/**
	 * constructor for use in empty classes and in deleted classes
	 * @param d
	 * @param s
	 */
	public FitnessClass(int d, int s) {
		index =d;
		startTime = s;
		classTimeSpan = String.format("%2d - %2d",startTime, startTime + 1);
		className = "Available";

	}
	//------------------------------------------------------------------------------------------
	/*
	 *  set methods  to enable instance variables to be given values	
	 */
	
	public void setIndex(int index) {
		this.index = index;
	}

	public void setClassId(String classID){
		this.classID = classID;
	}

	public void setClassName(String className){
		this.className = className;
	}

	public void setTutorName(String tutorName){
		this.tutorName =  tutorName;
	}

	public void setStartTime(int startTime){
		this.startTime = startTime;
	}

	//played with this to try to find empty classes
	public void setIsEmpty(boolean status) {//status only meaning true or false return
		this.isEmpty = status;
	}

	public void setTimeSpan(String t) {
		this.classTimeSpan = t;
	}
	
	public void setAttendance(int weekNumber, int n) {
		weekAtt[weekNumber] = n;	
	}
	
	public void setWeekAtt(int i, int n){
		weekAtt[i] = n;		
	}
	//------------------------------------------------------------------------------------------					
	public String getClassID(){
		return classID;
	}

	public String getClassName(){
		return className;
	}

	public String getTutorName(){
		return tutorName;
	}

	public int getStartTime(){
		return startTime;
	}

	public int getIndex(){
		return index;
	}

	public String getClassTimeSpan() {
		String ClassTimeSpan = String.format("%2d - %2d",startTime, startTime + 1);
		return classTimeSpan;
	}
	
	public int getWeekAtt(int i){
		return weekAtt[i];
	}
	
	//-----------------------------------------------------------------------------
	//set up of the boolean to check for empty files
	public boolean isEmpty() {
		return this.isEmpty;
	}
	//-----------------------------------------------------------------------------
	
	public double getAverage(){
		double totAtt = 0;
		for (int i = 0; i < NUM_WEEKS; i++){
		//iterate through adding, to get total attendance over 5 weeks
		totAtt = totAtt + weekAtt[i];//i represents week number
		}
		averageAtt = totAtt/weekAtt.length;
		return averageAtt;
	}
	
//------------------------------------------------------------------------------------------
	//compareTo for comparison of average attendances between fitness classes	
	public int compareTo(FitnessClass other) {

		double thisAverage = this.getAverage();
		double otherAverage = other.getAverage();

		if (thisAverage < otherAverage)
			return -1;
		else if (thisAverage == otherAverage)
			return 0;
		else
			return 1;
	}
	
	//-----------------------------------------------------------------------------
	// string output for the output to the Attendance Viewer
	public String toString(){


		return String.format("%-6s   %-12s    %-10s   %5d   %5d   %5d   "
		+ "%5d   %5d     %8.2f", classID, className,tutorName,weekAtt[0],
		weekAtt[1],weekAtt[2],weekAtt[3],weekAtt[4],getAverage());
	}

//-------------------------------------------------------------------------
}
