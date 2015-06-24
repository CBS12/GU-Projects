package ADS_AE1;

/**
 * A program to check spelling of words from a file (doc.txt) using
 * a "dictionary" file (dict.txt). The contents of the two files with
 * a list of mis-spelt words are saved to an outputfile ("out.txt").
 * ArrayList and Set from the Collections Class have been used.
 * @author Carol Stewart 2160196S.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class SpellCheck {


	public static void main(String[] args)
	{
		String inputFile = "doc.txt";
		String dictionaryFile = "dict.txt";
		String outputFile = "out.txt";


		//Using a set for the collection of words to provide a collection
		//without duplicates. Not necessary for the dictionary words.
		//call helper method wordSet to process reading files, and
		//creating a Set or ArrayList of words for each document.
		Set<String> docWords = wordSet(inputFile);
		ArrayList<String>dictWords = arrayWord(dictionaryFile);


		//-------------------------------------------------------------------------
		//Print out the dictionary words on individual lines rather than
		//a string of words in a Set array. Iterate through the set
		//printing each element individually.

		PrintWriter write = getPrintWriter(outputFile);

		write.println("Dictionary Words:");//heading
		for (String word:dictWords)
		{
			write.println(word);
		}
		write.println();//space between sections


		//-------------------------------------------------------------------------
		//Print out a copy of the text in doc.txt.
		write.println("Document text: ");//title
		FileReader fr = getFileReader(inputFile);//reads the file
		Scanner text = new Scanner(fr);//instantiate Scanner
		while(text.hasNextLine())//reads and scans each line to the file
		{
			write.println(text.nextLine());
		}
		text.close();
		write.println();//line space



		//-------------------------------------------------------------------------
		//print out misspelled words:
		//words from the document are checked as words 
		//NOT contained in the dictionary! These are printed out to the file.

		write.println("Misspelled Words: ");//title
		for(String wordLower:docWords)//iterate through docWords set
		{
			if (!dictWords.contains(wordLower))
				//Cay Horstmann p682.
				//'contains' returns true if the element, wordLower is in the ArrayList
				//of dictionary words so ! sets false resulting in the
				//return of words not contained in the dictionary i.e. misspelled.
			{
				write.print(wordLower + " ");
			}
		}
		write.close();
	}
		
		
//=================================================================================	
	/*
	 * A helper method to read a document, remove spaces, punctuation and
	 * change to lower case before assigning as an element in a Set.
	 * The Set approach removes duplicates from the comparison process.
	 * Processes such as the delimiter and toLowerCase are 
	 * not necessary for processing the dictionary document
	 * @param f ,which represents a String filename.
	 * @return Set<String>
	 */

	public static Set<String> wordSet(String f)
	{
		Set<String>words = new TreeSet<String>();
		FileReader fr = getFileReader(f);
		Scanner in = new Scanner (fr);
		in.useDelimiter("[^A-Z^a-z]+");//remove all except letters

		while (in.hasNext())
		{
			String word = in.next();
			//ensure all words converted to lower case.
			String wordLower = word.toLowerCase();
			words.add(wordLower);//add to Set<>
		}
		in.close();	

		return words;
	}				

	public static ArrayList <String> arrayWord(String f)
	{
		ArrayList<String>dictWords = new ArrayList<String>();
		FileReader fr = getFileReader(f);
		Scanner in = new Scanner (fr);
		in.useDelimiter("[^A-Z^a-z]+");//remove all except letters

		while (in.hasNext())
		{
			String dictWord = in.next();
			//ensure all words converted to lower case.
			String dictWordLower = dictWord.toLowerCase();
			dictWords.add(dictWordLower);//add to Set<>
		}
		in.close();	

		return dictWords;

	}
	//--------------------------------------------------------------------------------
	/**
	 * FileReader helper
	 * @param f
	 * @return
	 */
	public static FileReader getFileReader(String f) 
	{
		File fileName = new File(f);//convert String toFile
		try 
		{
			return new FileReader(fileName);
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("File Not Found");
			return null;
		}
	}

	//---------------------------------------------------------------------------------  
	/**
	 * at save and exit writing to a file
	 */
	public static PrintWriter getPrintWriter(String f) {
		File fileName = new File(f);//convert String toFile
		try {
			return new PrintWriter(fileName);
		} catch (IOException e) {
			System.err.println("Error writing to file");
			return null;
		}

	}


	//---------------------------------------------------------------------------------  

}