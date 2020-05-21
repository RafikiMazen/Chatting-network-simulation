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

public class MultiServer2 implements Runnable {

	Socket soc;
	static int clientNumber= 1;
	static Hashtable<Integer,Socket> mem = new Hashtable<Integer,Socket>();
	static Socket soc1;
	public MultiServer2(Socket soc){
		this.soc = soc;
		System.out.println("New client #" + clientNumber + " connected at " + soc);
	}

	public void run(){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			PrintWriter out = null;
			String userin;
			String msg = "";
			Set<Integer>set =mem.keySet();
			Object[] x = set.toArray();


			while((!(soc.isClosed()))&&(userin  = br.readLine())!=null){
				String [] MsgArray  = userin.split(" ");

				if (MsgArray[0].equalsIgnoreCase("GetMemberList()")) {

					out = new PrintWriter(soc.getOutputStream(),true);
					out.println(set);
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
						if(cn%2==1) {
							out = new PrintWriter(soc.getOutputStream(),true);
							out.println("User " + cn + " Unavailable");
							out.flush();
						}
						else {
							if(soc1==null)
								soc1 =new Socket("localhost",6789);
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
							out = new PrintWriter(soc1.getOutputStream(),true);
							out.println("FromServer " +sender+ " "+ cn+ " "+ msg);
							mem.put(-1, soc);
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
				else if (MsgArray[0].equalsIgnoreCase("list")) {
					if(soc1==null)
						soc1 = new Socket("localhost", 6789);
					out = new PrintWriter(soc1.getOutputStream(),true);
					out.println("list2 "+MsgArray[1]+" "+set);
					out.flush();

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
						if(soc1==null)
							soc1 =new Socket("localhost",6789);
						//Socket sentsoc = MultiServer2.mem.get(-1);
						out = new PrintWriter(soc1.getOutputStream(),true);
						out.println("User "+ MsgArray[1]+ " "+ cn + " Unavailable");
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
			ServerSocket serverSocket = new ServerSocket(3636);
			//System.out.println(serverSocket.getLocalPort());
			//Socket soc2 = new Socket("localhost", 6789);
			System.out.println("The capitalization server2 is running.");
			while(true){
				Socket socket = serverSocket.accept();

				MultiServer2 chat = new MultiServer2(socket);
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
