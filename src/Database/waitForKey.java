package Database;

import java.util.Scanner;

public class waitForKey extends Thread {
	public waitForKey(){}
	public void run()
	{
		Scanner scan = new Scanner(System.in);
		while(true)
		 {
			 System.out.println("here");
			 if(scan.next()!=null)
			 {
				 GuestPanel.switched(0);
			 }
		 }
	}
}
