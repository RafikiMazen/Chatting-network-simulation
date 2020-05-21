package networks1;
import java.io.*;
import java.net.*;

public class client2 extends Thread {

	static Socket clientSocket;
	static DataInputStream inFromServer;
	static DataInputStream inFromGUI;
	static Socket GUISocket;
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
		inFromUser = new BufferedReader(new InputStreamReader(System.in));

		try {
			clientSocket = new Socket("localhost", 3636);
			//GUISocket = new Socket("localhost", 1234);
			//inFromGUI= new DataInputStream(GUISocket.getInputStream());
			//OuttoGUI= new PrintStream(GUISocket.getOutputStream());
			outToServer = new PrintStream(clientSocket.getOutputStream());
			inFromServer = new DataInputStream(clientSocket.getInputStream());
			new client2().start();
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

