package P_AE2;

/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};
	/** Percentage of character frequency in text*/
	private double [] percent;
	/** Frequency of character that occurs most frequently */
	private int maxChFreq;
	/**Max percentage in the percent array*/
	private double maxPCcountSofar;
	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	private int index;
	/**Sets index position and tlly of character count to zero
	 * Instantiates a new letterFrequencies object.
	 */
//---------------------------------------------------------------------------------
	public LetterFrequencies(char d)
	{
		alphabet = new char [SIZE];
		alphaCounts = new int [SIZE];
		percent = new double [SIZE];
		totChars = 0;
		index = 0;
		addAlphabet();
	}
//---------------------------------------------------------------------------------			
	/** Creates the alphabet array
	 * using a loop to go though from 
	 * 0 to 25 where A+0 = A in integer form.
	 * 
	 */
	public void addAlphabet ()
	{
		
		for (int i = 0; i < SIZE; i++){
			alphabet[i] = (char)('A' + i);//cast to char
			
		}
	}
//---------------------------------------------------------------------------------		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read and increments the
	 * frequency for each letter.
	 * totChars increments for all letters read
	 */
	public void addChar(char d)
	{
		index = d - 'A';
		alphaCounts[index]++;//loops through input text
		totChars++;
		
		}
	
//---------------------------------------------------------------------------------	 
	/**
	 * @param alphaCounts
	 * @param SIZE
	 * array used to find maxPC
	 */
	private void getPercent()
	{
		percent = new double [SIZE];
		for (int i = 0; i < SIZE; i++){
			percent[i] = (100.0* alphaCounts[i]/SIZE);
			
		}
	}

//---------------------------------------------------------------------------------
	/**
	 * Gets the maximum percentage
	 * @param percent[] to get the %frequency for each letter
	 * @return the maximum %frequency
	 */
	private double getMaxPC()
	{
		for (int index = 0; index < SIZE; index++){
			if (percent[index]> maxPCcountSofar){
				maxPCcountSofar = percent[index];
			}
		}
		return maxPCcountSofar;
	}

//---------------------------------------------------------------------------------
	/**
	 * Gets the maximum frequency
	 * @param alphaCounts[] to get the frequency for each letter
	 * @return the letter at the same index as the max freq.
	 */
	private char getModalLetter()
	{
		maxChFreq = alphaCounts[1];
		for (int index = 0; index < SIZE; index++){
			maxChFreq = alphaCounts[1];
			if (alphaCounts[index]> maxChFreq) {
				//reads freq in array and compares to get max
				maxChFreq = alphaCounts[index];
			}
		
		}
		return alphabet[index];
	}

//---------------------------------------------------------------------------------
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{// create header
		
		String report = "LETTER ANALYSIS";
		report += '\n';
		String headings = ("Letter  Freq  Freq%  AvgFreq% Diff ");
		report += headings;
		report += '\n';
		for (int i= 0; i< SIZE; i++){
			
			
		
		report += String.format ("   %s    %2d    %4.1f    %4.1f  %6.1f",
				alphabet[i],alphaCounts[i],(100.0*alphaCounts[i]/totChars),
				avgCounts[i],(100.0*alphaCounts[i]/totChars)-avgCounts[i]);
		report += '\n';}
			
		String footer = ("The most frequent letter is "
		+ getModalLetter()+" at " + getMaxPC()+"%.");
		report += footer;
		report += '\n';
		report += '\n';
		System.out.print(report);
		return report;
	}
	
//---------------------------------------------------------------------------------	
	}		