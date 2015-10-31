package Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class testcv {
	public static void main(String[] args)
	{
		int num=1;
		String full = "153 73 384 32 444 416 198 451";
		String[] a = full.split("\\s+");
		for(int i=0;i<a.length;i++)
		{
			System.out.println(a[i]);
		}
		
		Process process = null;
		try {
//			process = new ProcessBuilder("C:\\opencv3\\my_build\\bin\\Release\\planar.exe","\"C:/Users/payam/workspace/DataBaseSemesterProject/pictureDB/"+num+".jpg\"","\"C:/Users/payam/workspace/DataBaseSemesterProject/current.jpg\"","153 73 384 32 444 416 198 451").start();
//			process = new ProcessBuilder("C:/opencv3/my_build/bin/Release/planar.exe","C:/Users/payam/workspace/DataBaseSemesterProject/pictureDB/"+num+".jpg","C:/Users/payam/workspace/DataBaseSemesterProject/current.jpg","153 73 384 32 444 416 198 451").start();
			process = new ProcessBuilder("C:/opencv3/my_build/bin/Release/planar.exe","C:/Users/payam/workspace/DataBaseSemesterProject/pictureDB/"+num+".jpg","C:/Users/payam/workspace/DataBaseSemesterProject/current.jpg",a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7]).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("executed");
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		System.out.println("executed");
		//System.out.printf("Output of running %s is:", Arrays.toString());
		String output = null;
		try {
			while ((line = br.readLine()) != null) {
			  System.out.println("output from scan: "+line);
			  output = line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(output!=null)
		{
			if(Integer.parseInt(output)==1)System.out.println("found");
//				GuestPanel.found(num);//call a method found!
		}
		else System.out.println(":(");
	}
}
