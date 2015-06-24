package Qu2;

/**
 * @author 2160196S
 * Client sends a question to the server, a sum to be
 * processed and answer returned to the client. Confirmation given
 * to server and request for next question, initiates Client's next question.
 */


import java.io.*;
import java.net.*;

public class MyClient {
	private static String SERVER = "127.0.0.1";
	private static Integer PORT = 8765;
	public static void main(String[] args) throws IOException {
		// Connect to the server and create the writer and reader
		Socket socket = new Socket(SERVER,PORT);
		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		out.println("First question is: 5,6,+");//first question sent to the Server	
		// Loop forever until a "Finished" request received or issued
		while(true) {

			String line = in.readLine().trim();//reads input from the server
			 
			//allows for empty response or server signing off
			if(line==null || line.startsWith("Server Finished")) {
				System.out.println(line);//message to console from server
				out.println("Bye-Bye from Client!");//message to server from client
				break;//end comms
			}
			//client receives first answer from server
			//sends message to confirm result
			else if (line.startsWith("My first answer is: ")){
				System.out.println(line);//message to console from server
				out.println("First Result: correct!");//message to server
			}
			//client receives a request for the next question from the server
			//client sends next question
			else if (line.startsWith("Next question please!")){
				System.out.println(line);//message to console from server
				out.println("Second question is: 12,3,-");//second question to server
			}	
			//client receives second answer from server
			//sends message to confirm result
			else if (line.startsWith("the second answer is: ")){
				System.out.println(line);//message from server to console
				out.println("Latest Result: correct!");//response to server
			}	
		}
		// Close the in and out and socket
		out.close();
		in.close();
		socket.close();
	}
}

