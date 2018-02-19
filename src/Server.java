
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.awt.EventQueue;
import javax.swing.JFileChooser;

import java.net.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;

public class Server {

	private JFrame frame;
    String path;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream ois = null;
    private InputStream dis;
    private FileOutputStream fos = null;
    private BufferedOutputStream bos = null;
    private fileevent fileEvent;
    private File dstFile = null;
    private FileOutputStream fileOutputStream = null;
    private JLabel lblNewLabel_3;
    private String destination=System.getProperty("user.home")+"\\Desktop\\"; // To set default location of destination as dekstop
    JLabel lblNewLabel_1;
    private File file;
    private JButton btnNewButton;
    private JLabel lblNewLabel_5;
    JLabel filename;
    private String outputFile;
    JProgressBar progressBar;
    private JLabel lblNewLabel_6;
	/**
	 * Launch the application.
	 */
	public static void Yo() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
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
	public Server() {
		initialize();
	}
	//For opening the socket for client to connect
	public void Connect()
	{
		  Runnable serverTask = new Runnable() {
	            @Override
	            public void run() {
        try {
			serverSocket = new ServerSocket(4445);
	        socket = serverSocket.accept();
			lblNewLabel_3.setText("Connected.");
			lblNewLabel_5.setText("");
			ois = new ObjectInputStream(socket.getInputStream());
			downloadFileDetail();
			} catch (IOException e) {
			e.printStackTrace();
			}
	          }
	     };
	     lblNewLabel_5.setText("Waiting for Connections....");
		 lblNewLabel_5.setBounds(164, 101, 150, 14);
	        Thread serverThread = new Thread(serverTask);
	        serverThread.start();
	}
	//Downloading the file details as an object as creating a file
	public void downloadFileDetail()
	{
		
		try {
			fileEvent = (fileevent) ois.readObject();
			int len=(int)fileEvent.getFileSize();
			 double ts=(double) ((len/1024.0)/1024.0);
			    ts=Double.parseDouble(new DecimalFormat("##.##").format(ts));
			if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
				JOptionPane.showMessageDialog(null,  "Error occurred ..So exiting");
				System.exit(0);
			}
			outputFile = destination +fileEvent.getFilename();
			file=new File(outputFile);
			 boolean fvar = file.createNewFile();
		     if (fvar){
		          System.out.println("File has been created successfully");
		          filename.setText(fileEvent.getFilename());
		          lblNewLabel_6.setText(ts+" MB");
		          downloadFileData();
		     }
		     else{
		          System.out.println("File already present at the specified location");
		     }
			
		    } 
		catch (IOException e) {
			e.printStackTrace();
			}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			} 
}
//Downloading the file data as the byte buffer
	 public void downloadFileData()
	 {
		
		 progressBar = new JProgressBar(0,100);
         progressBar.setStringPainted(true);
         progressBar.setValue(0);
         progressBar.setBounds(37, 159, 366, 23);
         frame.getContentPane().add(progressBar);
		 	try{
		 		dis = socket.getInputStream();
		 		byte [] buffer  = new byte [16000];
		 		fos = new FileOutputStream(outputFile);
		 		bos=new BufferedOutputStream(fos);
		 	        double filesize = fileEvent.getFileSize();
				int read = 0;
				int x=0;
				int remaining =(int)fileEvent.getFileSize();
				//Reading the file buffer one by one in each loop and writing the file data is stored location
				while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) 
				{
					remaining -= read;
					bos.write(buffer,0,read);
					x=x+read;
					int per=(int) ((x/filesize)*100);
					progressBar.setValue(per);
					progressBar.setString(per+"%");
				}
				
				
		 		JOptionPane.showMessageDialog(null,"Output file : " + outputFile + " is successfully saved ");
			    Thread.sleep(3000);
			    System.exit(0);
		 	}	
			catch (IOException e) {
				e.printStackTrace();
				}
			catch (InterruptedException e) {
				e.printStackTrace();
				}
		 	
	 }


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	    btnNewButton = new JButton("Recieve");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connect();
				
				}
		});
		btnNewButton.setBounds(136, 55, 157, 35);	
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Enter IP: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 191, 65, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Status:");
		lblNewLabel_2.setBounds(277, 11, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
	    lblNewLabel_3 = new JLabel("Disconnect");
		lblNewLabel_3.setBounds(333, 11, 70, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(79, 193, 46, 14);
		frame.getContentPane().add(lblNewLabel_4);
		 try {
	            InetAddress ipAddr = InetAddress.getLocalHost();
	           lblNewLabel_4.setText(ipAddr.getHostAddress());
	           lblNewLabel_4.setBounds(79, 193, 100, 14);
	           
	           JLabel lblDefaultLcatione = new JLabel("Default Location: ");
	           lblDefaultLcatione.setBounds(10, 224, 85, 14);
	           frame.getContentPane().add(lblDefaultLcatione);
	           
	           JButton btnClickToChange = new JButton("Click to Change");
	           btnClickToChange.addActionListener(new ActionListener() {
	           	public void actionPerformed(ActionEvent arg0) {
	                JFileChooser f = new JFileChooser();
	                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
	                f.showSaveDialog(null);
	                destination=f.getSelectedFile()+"\\";
	                lblNewLabel_1.setText(destination);
	                lblNewLabel_1.setBounds(105,224,250,14);
	                
	           	}
	           });
	           btnClickToChange.setBounds(317, 220, 107, 23);
	           frame.getContentPane().add(btnClickToChange);
	           
	           lblNewLabel_1 = new JLabel(destination);
	           lblNewLabel_1.setBounds(105, 224, 172, 14);
	           frame.getContentPane().add(lblNewLabel_1);
	           
	           lblNewLabel_5 = new JLabel("");
	           lblNewLabel_5.setBounds(164, 101, 113, 14);
	           frame.getContentPane().add(lblNewLabel_5);
	           
	           
	           filename = new JLabel("");
	           filename.setBounds(10, 126, 240, 14);
	           frame.getContentPane().add(filename);
	           
	           lblNewLabel_6 = new JLabel("");
	           lblNewLabel_6.setBounds(345, 126, 79, 14);
	           frame.getContentPane().add(lblNewLabel_6);
	        } catch (UnknownHostException ex) {
	            ex.printStackTrace();
	        }


	}
}
