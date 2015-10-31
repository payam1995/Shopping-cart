package Database;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Reciept {
	JFrame frame;
	public static int total;
	public Reciept()
	{
		total = 0;
		frame = new JFrame("Reciept");
		frame.setSize(200, 400);
		frame.setLayout(new GridLayout(20,1));
		JLabel label = new JLabel("THE RECIEPT!");
		label.setFont(new Font(label.getFont().getName(),Font.BOLD,20));
		frame.add(label);
		frame.setLocation(1000,0);
		frame.setVisible(true);
		
	}
	public void add(ResultSet rset)
	{
		try {
//			rset.next();
			JLabel label = new JLabel(rset.getString(1)+"   "+rset.getInt(2));
			frame.add(label);
			frame.setVisible(true);
			total += rset.getInt(2);
		} catch(SQLException e){}
	}
	public void creditCard()
	{
		System.out.println("Credit card match found : Payam Mashrequi");
		frame.add(new JLabel());
		frame.add(new JLabel("total : "+total));
		frame.add(new JLabel("Rs."+total + " Amount has been deduced from your account"));
		frame.setVisible(true);
	}
}
