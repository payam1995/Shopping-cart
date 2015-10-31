package Database;

import java.io.IOException;

public class Servo {
	
	public Servo() throws Exception
	{
		Serial main = new Serial();
		main.initialize();
		Thread t=new Thread() {
			public void run() {
				try {Thread.sleep(2000000);} catch (InterruptedException ie) {}
			}
		};
		
		t.start();
		
		System.out.println("Started");
	}
	
	
}
