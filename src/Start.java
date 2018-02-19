

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Start {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Start() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnServer = new JButton("Recieve"); //To execute the class Server.java
		btnServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Server s=new Server();
				s.Yo();// To call the initiating function of Server.java
			}
		});
		btnServer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnServer.setBounds(111, 23, 189, 84);
		frame.getContentPane().add(btnServer);
		
		JButton btnStartAsClient = new JButton("Send");//To execute the class Client.java
		btnStartAsClient.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStartAsClient.setBounds(111, 142, 189, 84);
		btnStartAsClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client h=new Client();
				h.Yo();// To call the initiating function of Client.java
			}
		});
		frame.getContentPane().add(btnStartAsClient);
	}

}
