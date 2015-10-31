package Database;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class GuestPanel {
	JFrame frame;
	static Reciept reciept;
	public static Servo master;
	VideoCapture capture;
	public static MyFrame camFrame;
	public GuestPanel()
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		capture = new VideoCapture(0);
//		try {
//			master = new Servo();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		JFrame frame = new JFrame("clickk");
		JButton button = new JButton("CLICKK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switched(0);
			}
		});
		frame.add(button);
		frame.setSize(100, 100);
		frame.setLocation(700,0);
		frame.setVisible(true);
		 reciept = new Reciept();
		 camFrame = new MyFrame();
		 camFrame.start();
		 //new waitForKey().start();
//		 Scanner scan = new Scanner(System.in);
//		 while(true)
//		 {
//			 System.out.println("here");
//			 if(scan.next()!=null)
//			 {
//				 switched(0);
//			 }
//		 }
		// if got signal from 2nd laser stop servo and send to serial<-- microcontroller programming
		//receive serial signal and take snap
		//store image to temp file (name should be current.jpg in root directory)
		//run loop
		
	}
	
	public static void switched(int on)
	{
		if(on == 1)
		{
			System.out.println("");
		}
		if(on == 0)
		{
			System.out.println("item on the line");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ImShow a = new ImShow("preview");
//			a.showImage(VideoCap.getOneFrameInMat());
			Highgui.imwrite("current.jpg",VideoCap.getOneFrameInMat());
			//take pick of scene
			//save it
			System.out.println("main  "+Main.ID);
			for(int i =1;i<Main.ID;i++)
			{
				new CVScan(Main.ID-1).start();
			}
		}
	}
	
	public static void found(int id)
	{
		
		try {
			Statement stmt = db_auth.conn.createStatement();
			ResultSet rset = stmt.executeQuery("Select name,price from products where id="+id);
			rset.next();
			if(!rset.getString(1).equals("creditcard"))
				reciept.add(rset);
			else reciept.creditCard();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
