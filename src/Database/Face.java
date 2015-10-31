package Database;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Face {
	

	public Face() {
		//if connected start interface
		JFrame frame = new JFrame("Main Interface");
		JButton connect_db = new JButton("Connect to database");
		connect_db.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new db_auth(frame);
			}
		});

		frame.setLayout(new GridLayout(1, 1));
		frame.add(connect_db);
		frame.setSize(new Dimension(200, 100));
		frame.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
	}

}

class db_auth_dialogue{
	public static String Connection_name;
	public static String username;
	public static String password;
	public static String hostname;
	public static String port;
	public static String sid;
	
	public db_auth_dialogue(JFrame main)
	{
		JFrame frame = new JFrame("Select Database");
		JLabel area = new JLabel("lets see what happens");
		JFrame.setDefaultLookAndFeelDecorated(true);

		JLabel un = new JLabel("Username");
		JTextField unn = new JTextField();
		
		JLabel p = new JLabel("Password");
		JTextField pp = new JTextField();
		
		JLabel hn = new JLabel("Hostname");
		JTextField hnn = new JTextField();
		
		JLabel pr = new JLabel("Port");
		JTextField prr = new JTextField();
		
		JLabel s = new JLabel("SID");
		JTextField ss = new JTextField();
		
		JButton Connect_button = new JButton("Connect");
		Connect_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				username = unn.getText();
				password = pp.getText();
				hostname = hnn.getText();
				port = prr.getText();
				sid = ss.getText();
				new db_auth(1).gotIt(area,frame,main);
				//return;
			}
		});
		frame.setSize(500, 200);
		frame.setLayout(new GridLayout(6, 2));
		frame.add(un);
		frame.add(unn);
		frame.add(p);
		frame.add(pp);
		frame.add(hn);
		frame.add(hnn);
		frame.add(pr);
		frame.add(prr);
		frame.add(s);
		frame.add(ss);
		frame.add(Connect_button);
		frame.add(area);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		frame.setVisible(true);
		
	}
	
	
}

class db_auth{
	public static Connection conn = null;
	public db_auth(JFrame main)
	{
		new db_auth_dialogue(main);
		
		System.out.println("here");
		
	}
	public db_auth(int one)
	{
		System.out.println("2nd constructor");
	}
	public void gotIt(JLabel text, JFrame frame, JFrame main)
	{
		
//		String URL = "jdbc:oracle:thin:@"+db_auth_dialogue.hostname+":"+db_auth_dialogue.port+":"+db_auth_dialogue.sid;
		
//		String URL = "jdbc:oracle:thin:@localhost:1521/xe";
//		final String user = db_auth_dialogue.username;
//		final String password = db_auth_dialogue.password;
		final String user = "system";
		final String password = "payam";
		String URL = "jdbc:oracle:thin:payam/payam@localhost:1521:XE";
		
		
		try {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");
			   System.exit(1);
			}
		
		
		try {
			   Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			}
			catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");
//			   System.exit(1);
			}
			catch(IllegalAccessException ex) {
			   System.out.println("Error: access problem while loading!");
//			   System.exit(2);
			}
			catch(InstantiationException ex) {
			   System.out.println("Error: unable to instantiate driver!");
//			   System.exit(3);
			}
		text.setText("Driver has loaded successfully! Authenticating now...");
		try {
			conn = DriverManager.getConnection(URL, user, password);
		} catch (SQLException e) {
//			new db_auth_dialogue();
//			new db_auth();
			e.printStackTrace();
		}
		if(conn != null)
		{
			text.setText("Driver has loaded successfully! Authenticating now..."+
							"Successfully Authenticated!");
			frame.dispose();
			System.out.println("yayyy");
			main.dispose();
			new dummy();
			try {
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery("select count(1) from products");
				while (rset.next())
					Main.ID = rset.getInt(1);
				Main.ID++;
				System.out.println("total products in shop : "+(Main.ID-1));
				rset.close();
				stmt.close();
//				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		else {
			text.setText("Problem with authentication.. try again");
			frame.dispose();
			
			//new db_auth_dialogue();
			new db_auth(main);
			
//			System.out.println("oops");
			//new db_auth(frame, text);
		}
	}
}
