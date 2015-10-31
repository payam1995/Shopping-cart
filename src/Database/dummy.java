package Database;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class dummy {
	public static String rightsdb;
	boolean authorized=false;
	public dummy() {
		JFrame frame = new JFrame("Authentication!");
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(250, 100);
//		frame.setUndecorated(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		JLabel username = new JLabel("UserName : ");
		JLabel password = new JLabel("Password : ");
		JTextField user = new JTextField();
		JTextField pass = new JTextField();
		frame.setResizable(false);
		frame.setLayout(new GridLayout(3, 4));
		frame.add(username);
		frame.add(user);
		frame.add(password);
		frame.add(pass);
		JButton start = new JButton("Login");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Statement stmt = db_auth.conn.createStatement();
					ResultSet rset = stmt.executeQuery("select * from users");
					while (rset.next()){
//						System.out.println(rset.getString(1));
//						System.out.println(rset.getString(2));
						String userdb = rset.getString(1);
						String passdb = rset.getString(2);
						rightsdb = rset.getString(3);
						if((user.getText().equals(userdb)&&pass.getText().equals(passdb)))
						{
							authorized = true;
							frame.dispose();
							System.out.println("Successfully authenticated as "+rightsdb+"!");
							break;
						}
					}
					if(authorized)
						new dialogue().correct(frame);
					else new dialogue().wrong();
					rset.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(rightsdb.equals("guest"))
					new GuestPanel();//do something
				if(rightsdb.equals("admin"))
					new AdminPanel();//do something
			}
		});
		JButton stop = new JButton("Exit");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		frame.add(start);
		frame.add(stop);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

class dialogue{
	
	public void wrong()
	{
		JFrame frame = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(350, 200);
		JLabel field = new JLabel("Wrong USERNAME/PASSWORD! TRY AGAIN");
		frame.setLayout(new GridLayout(3, 2));
		frame.add(field);
		JButton button = new JButton("OK");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();	
			}
		});
		frame.add(button);
		frame.setVisible(true);
	}
	
	public void correct(JFrame fr)
	{
		JFrame frame = new JFrame();
		frame.setSize(250, 100);
		JLabel field = new JLabel("Welcome!");
		frame.setLayout(new GridLayout(3, 2));
		frame.add(field);
		JButton button = new JButton("Awesome! you've logged in!");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setAlwaysOnTop(true);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				fr.dispose();
			}
		});
		frame.add(button);
		frame.setVisible(true);
	}
	
}
