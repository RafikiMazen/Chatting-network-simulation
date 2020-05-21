package networks1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class GUI extends Thread implements ActionListener{
    DataInputStream in;
    static PrintWriter out;
    static BufferedReader br;
    static Socket socket;
    String res;
    static Hashtable<Integer,Socket> mem = new Hashtable<Integer,Socket>();
    
	private JFrame frame;
	private JTextField message;
	private JTextField textField;
	static int v =0;
	private JTextArea textarea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
				ServerSocket ss = new ServerSocket(1234);
			while(true){
				socket = ss.accept();
				GUI window = new GUI(socket);
				window.frame.setVisible(true);
				
				//GUI  g = new GUI();
				new Thread(window).start();
				//soc = new Socket("localhost",1234);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(),true);
			
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
					
				
				
				
				
				
				
				
				
				
				
				
				
				
				//GUI window = new GUI(socket);
					//window.frame.setVisible(true);
//					try {
////						soc = new Socket("localhost",1234);
////						BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
////						out = new PrintWriter(soc.getOutputStream(),true);
//					} catch (UnknownHostException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(Socket s) {
		this.socket = s;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 711, 541);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		message = new JTextField();
		message.setBounds(12, 390, 463, 91);
		frame.getContentPane().add(message);
		message.setColumns(10);
		
		JButton btnNewButton = new JButton("send");
		btnNewButton.setBounds(558, 418, 97, 44);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("login");
		btnNewButton_1.setBounds(584, 13, 97, 25);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("logout");
		btnNewButton_2.setBounds(475, 13, 97, 25);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("checkout who's online!");
		lblNewLabel.setBounds(543, 70, 138, 31);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(533, 97, 148, 262);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textarea = new JTextArea();
		textarea.setBounds(12, 44, 463, 316);
		frame.getContentPane().add(textarea);
	}
	/*public void run() {
		//BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		//
		//soc = new Socket("localhost",1234);
		while((!(soc.isClosed()))&&(message.getText()!=null)){
		
		res = br.readLine();
		textarea.append(res);
		}
		out = new PrintWriter(soc.getOutputStream(),true);
		out.println(set);
		out.flush();*/
	/*
	 * 
		try {
			soc = new Socket("localhost",6789);
			while(true) {
			res = br.readLine();
			textarea.append(res);
			
		} }catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn.getText() == "login") {
			if (v==0) {
				new client();
				v=1;
			}
			else {
				new client2();
				v=1;
			}
		}
		else if(btn.getText() == "send"){
			String msg = message.getText();
			out.println(msg);
			System.out.println(msg);
		}
		
	}
}
