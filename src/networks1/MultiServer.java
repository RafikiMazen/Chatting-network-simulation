package networks1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class MultiServer implements Runnable {

	Socket soc;
	static int clientNumber= 0;
	static Hashtable<Integer,Socket> mem = new Hashtable<Integer,Socket>();
	static Socket soc2;
	//public MultiServer(){}
	public MultiServer(Socket soc){
		this.soc = soc;
		System.out.println("New client #" + clientNumber + " connected at " + soc);
	}

	public void run(){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			PrintWriter out = null;
			PrintWriter out2 = null;
			String userin;
			String msg = "";
			Set<Integer>set =mem.keySet();
			Object[] x = set.toArray();


			while((!(soc.isClosed()))&&(userin  = br.readLine())!=null){
				String [] MsgArray  = userin.split(" ");

				if (MsgArray[0].equalsIgnoreCase("GetMemberList()")) {
					if(soc2==null) {
						soc2 = new Socket("localhost", 3636);}
					int sender = 0;
					for (int i = 0; i < mem.size(); i++) {
						if (mem.get(x[i]).equals(soc)) {
							sender = (Integer) x[i];
							break;}
					}

					out2 = new PrintWriter(soc2.getOutputStream(),true);
					out2.println("list " + sender );
					out2.flush();
					out = new PrintWriter(soc.getOutputStream(),true);
					out.println(set);
					out.flush();

				}
				else if (MsgArray[0].equalsIgnoreCase("list2")) {
					msg="";
					for(int i =2; i<MsgArray.length;i++){
						msg = msg + " " + MsgArray[i].toString();
					}
					//System.out.println(MsgArray[1]);
System.out.println("xx");
					Socket s = ((Socket)mem.get(Integer.parseInt(MsgArray[1])));
					System.out.println(s);
					out = new PrintWriter(s.getOutputStream(),true);
					out.println(msg);
					out.flush();

				}
				else if(MsgArray[0].equalsIgnoreCase("Quit")) {

					int sender = 0;
					for (int i = 0; i < mem.size(); i++) {
						if (mem.get(x[i]).equals(soc)) {
							sender = (Integer) x[i];
							break;}
					}

					mem.remove(sender);
					out = new PrintWriter(soc.getOutputStream(),true);
					out.println("User " + sender + " has logged out");
					soc.close();
					out.flush();

				}
				else if(MsgArray[0].equals("*")) {
					int cn = Integer.parseInt(MsgArray[1]);

					if(!(set.contains(cn))){
						if(cn%2==0) {
							out = new PrintWriter(soc.getOutputStream(),true);
							out.println("User " + cn + " Unavailable");
							out.flush();
						}
						else {
							if(soc2==null)
								soc2 =new Socket("localhost",3636);
							int sender = 0;
							for (int i = 0; i < mem.size(); i++) {
								if (mem.get(x[i]).equals(soc)) {
									sender = (Integer) x[i];
									break;}
							}
							msg="";
							for(int i =2; i<MsgArray.length;i++){
								msg = msg + " " + MsgArray[i].toString();
							}
							out = new PrintWriter(soc2.getOutputStream(),true);
							out.println("FromServer " +sender+ " "+ cn+ " "+ msg);
							//mem.put(-1, soc);
							out.flush();
						}
					}

					else{


						Socket s =mem.get(cn);

						out = new PrintWriter(s.getOutputStream(),true);
						msg="";
						for(int i =2; i<MsgArray.length;i++){
							msg = msg + " " + MsgArray[i].toString();
						}
						int sender = 0;
						for (int i = 0; i < mem.size(); i++) {
							if (mem.get(x[i]).equals(soc)) {
								sender = (Integer) x[i];
								break;}
						}

						out.println("Client " + sender +": " + msg.toString());
						out.flush();
					} 



				}
				else if(MsgArray[0].equals("User")) {
					int sender = Integer.parseInt(MsgArray[1]);
					int receiver = Integer.parseInt(MsgArray[2]);
					//mem.get(sender);
					out = new PrintWriter(mem.get(sender).getOutputStream(),true);
					out.println("User "+ receiver + " Unavailable");
				}
				else if(MsgArray[0].equals("FromServer")) {
					int cn = Integer.parseInt(MsgArray[2]);

					if(!(set.contains(cn))) {
						if(soc2==null)
							soc2 =new Socket("localhost",3636);
						//Socket sentsoc = MultiServer2.mem.get(-1);
						out = new PrintWriter(soc2.getOutputStream(),true);
						out.println("User "+ MsgArray[1]+" "+ cn + " Unavailable");
						out.flush();
					}
					else {
						Socket s =mem.get(cn);
						msg="";
						for(int i =3; i<MsgArray.length;i++){
							msg = msg + " " + MsgArray[i].toString();
						}
						out = new PrintWriter(s.getOutputStream(),true);
						out.println("Client " + MsgArray[1] +": " + msg.toString());
						out.flush();
					}
				}
				else {

					out = new PrintWriter(soc.getOutputStream(),true);
					out.println("Incorrect message format");
					out.flush();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();

		}
	}
	public static void main(String[]args){
		try {
			ServerSocket serverSocket = new ServerSocket(6789);
			//ServerSocket serverSocket1 = new ServerSocket(1111);
			//Socket soc2 = new Socket("localhost", 1111);
			System.out.println("The capitalization server is running.");

			while(true){
				Socket socket = serverSocket.accept();
				//	Socket conn = serverSocket1.accept();
				MultiServer chat = new MultiServer(socket);

				new Thread(chat).start();

				mem.put(clientNumber, socket);
				clientNumber+=2;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}