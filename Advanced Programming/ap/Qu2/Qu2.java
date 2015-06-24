package Qu2;

/**
 * Interaction between Client and Server. The Client sends the first
 * message "First question is....." and server responds. 
 * The question has to be in the form
 * integer comma integer comma operator eg, 5,3,+  OR 5,3,-
 * The server has a helper method to interpret and calculate the 'sum'. 
 * Server returns response to client in String form.
 * @author 2160196S
 */

import java.io.*;
import java.net.*;

public class Qu2 {

	private static int PORT =8765;
	private static ServerSocket server;
	private static String line;
	private static BufferedReader in;
	private static PrintWriter out;
	private static Socket client;

	public static void main(String[] args) throws IOException {
		//try catch to handle exceptions with server
		try{
			server = new ServerSocket(PORT);
			client =server.accept();
			in = new  BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(),true);
			//loop through question/answer
			while(true) {
				line = in.readLine().trim();//reads a line from the client
				//no response or Client types "Finished" then interaction ends.
				if(line==null || line.startsWith("Finished")) {
					break;
				}
				//receives first question from client
				//server sends first answer to client
				else if(line.startsWith("First question is:")){//question from client
					System.out.println(line);//print question to console
					String answer = processInLine(line);//process question
					out.println("My first answer is: " + answer);	//sends answer to client	
				}
				//receives confirmation of receipt from client
				//server requests next question.
				else if(line.startsWith("First Result:")){//message from client
					System.out.println(line);	//print message to console
					out.println("Next question please!");//send request for next question to client
				}
				//receives next question from client
				// server returns answer
				else if(line.startsWith("Second question is:")){
					System.out.println(line);//print question to console
					String answer = processInLine(line);//process question
					out.println("the second answer is: " + answer);//send answer to client
				}	
				//receives confirmation of receipt from client
				//server says farewell and quits
				else if(line.startsWith("Latest Result:")){
					System.out.println(line);	//print result to console
					out.println("Server Finished!");// server tells Client to finish
				
				}
				else if(line.startsWith("Bye-Bye from Client!")){
					System.out.println(line);	//print result to console
					break;//end comms
				}
			}//close
			out.close();
			in.close();
			client.close();
		}
		catch(IOException e) {}
	}
	/**
	 * method to process the string question line, gets rid of letters
	 * converts to integer, does the calculation and returns the answer
	 * as a String.
	 * @param line
	 * @return
	 */
	private static String processInLine(String line) {
		//remove letters and extract values fro the 'sum'
		String [] values = line.split("[ :,.^a-zA-Z]+");//remove letters and
		int sum = 0;
		int first = Integer.parseInt(values[1]);//cast String to integer
		int second = Integer.parseInt(values[2]);//cast String to integer
		String operator = values[3];//operator 
		//do calculations based on + or -		
		if (operator.contains("+"))
		{
			sum = first + second;
		}
		else if (operator.contains("-"))
		{
			sum = first - second;
		}
		String answer = String.format("%d",sum);//convert back to String
		return answer;
	}
}
