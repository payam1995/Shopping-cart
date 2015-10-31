package Database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CVScan extends Thread{
	private int num;
	
	public CVScan(int i)
	{
		num = i;
	}
	public void run()
	{
		String everything = "";
		System.out.println(num);
		//read from file
		BufferedReader br = null;
	    try {
	    	br =  new BufferedReader(new FileReader("xmlDB/"+num+".txt"));
	    	
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        everything = sb.toString();
	        
	    } catch (Exception e) {
			e.printStackTrace();
		} finally {
	        try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	    }
	    System.out.println(everything);
	    String[] a = everything.split("\\s+");
	    
		Process process = null;
		try {
			process = new ProcessBuilder("C:\\opencv3\\my_build\\bin\\Release\\planar.exe","pictureDB/"+num+".jpg","current.jpg",a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7]).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader brr = new BufferedReader(isr);
		String line;

		//System.out.printf("Output of running %s is:", Arrays.toString());
		String output = null;
		try {
			while ((line = brr.readLine()) != null) {
			  System.out.println("output from scan: "+line);
			  output = line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(output!=null)
		{
			if(Integer.parseInt(output)==1)
				GuestPanel.found(num);//call a method found!
		}
		else System.out.println(":(");
	}
	public int call(String output) {
		/*Process process = null;
		try {
			process = new ProcessBuilder("C:\\opencv3\\my_build\\bin\\Release\\planar.exe","pictureDB/"+num+".jpg","current.jpg","xmlDB/"+num+".xml").start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;

		//System.out.printf("Output of running %s is:", Arrays.toString());
		String output = null;
		try {
			while ((line = br.readLine()) != null) {
			  System.out.println(line);
			  output = line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		return new Integer(Integer.parseInt(output)); 
	}

}
