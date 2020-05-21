package clientsChat;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import networks1.client;
import networks1.client2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class chat extends Composite implements ActionListener {
	private Text text;
	private Text text_1;
	private Text text_2;
	static int v = 0;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public chat(Composite parent, int style) {
		super(parent, style);
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(10, 441, 414, 108);
		
		Button btnSend = new Button(this, SWT.NONE);
		btnSend.setBounds(430, 441, 160, 109);
		btnSend.setText("SEND");
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setEditable(false);
		text_1.setBounds(430, 100, 160, 337);
		
		Button btnSignIn = new Button(this, SWT.NONE);
		btnSignIn.setBounds(514, 3, 76, 55);
		btnSignIn.setText("sign in");
		
		Button btnLogout = new Button(this, SWT.NONE);
		btnLogout.setBounds(430, 3, 78, 55);
		btnLogout.setText("logout");
		
		Label lblCheckoutWhosOnline = new Label(this, SWT.NONE);
		lblCheckoutWhosOnline.setBounds(430, 74, 160, 20);
		lblCheckoutWhosOnline.setText("checkout who's online!");
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setEditable(false);
		text_2.setBounds(10, 10, 414, 412);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		if (btn.getText() == "sign in") {
			if (v==0) {
				new client();
				v=1;
			}
			else {
				new client2();
				v=1;
			}
		}
		else if(btn.getText() == "SEND"){
			
		}
		
	}
	
	public static void main(String args[]) {
		new chat(null,SWT.NONE);
	}
}
