package networks1;
import java.io.*;
import java.net.*;

public class client extends Thread {

	static Socket clientSocket;
	static Socket GUISocket;
	static DataInputStream inFromServer;
	static DataInputStream inFromGUI;
	static PrintStream OuttoGUI;
	static PrintStream outToServer;
	static BufferedReader inFromUser;

	public void run() {
		String reply;

		try {
			while((reply = inFromServer.readLine()) !=null){
				System.out.println(reply);
				//OuttoGUI.println(reply);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String argv[]) 
	{
		

		try {
			
			clientSocket = new Socket("localhost", 6789);
			//GUISocket = new Socket("localhost", 1234);
			//
			inFromUser = new BufferedReader(new InputStreamReader(System.in));
			outToServer = new PrintStream(clientSocket.getOutputStream());
			//OuttoGUI = new PrintStream(GUISocket.getOutputStream());
			inFromServer = new DataInputStream(clientSocket.getInputStream());
			//inFromGUI = new DataInputStream(GUISocket.getInputStream());
			new client().start();
			while(true) {
				if(clientSocket.isClosed())
					{break;}
				outToServer.println(inFromUser.readLine());
			}
			

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}}

