package Database;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class recordToFile {
	static Mat mat;
	static List<Integer> ROIX;
	static List<Integer> ROIY;
	public recordToFile(){}
	public recordToFile(Mat mat, List<Integer> ROIX,List<Integer> ROIY)
	{
		new confirmPicture();
		recordToFile.mat = mat;
		recordToFile.ROIX = ROIX;
		recordToFile.ROIY = ROIY;
	}
	public static void doIt()
	{
//		String xml = "<?xml version=\"1.0\"?><opencv_storage><bounding_box>"+" "+(ROIX.get(0)-5)+" "+(ROIY.get(0)-25)+" "+(ROIX.get(1)-5)+" "+(ROIY.get(1)-25)+" "+(ROIX.get(2)-5)+" "+(ROIY.get(2)-25)+" "+(ROIX.get(3)-5)+" "+(ROIY.get(3)-25)+" </bounding_box></opencv_storage>\"";
		
		String xml = (ROIX.get(0)-5)+" "+(ROIY.get(0)-25)+" "+(ROIX.get(1)-5)+" "+(ROIY.get(1)-25)+" "+(ROIX.get(2)-5)+" "+(ROIY.get(2)-25)+" "+(ROIX.get(3)-5)+" "+(ROIY.get(3)-25);
		System.out.println(xml);
//		new ImShow("cool!").showImage(recordToFile.mat); //save this file with Main.ID.jpg name
		Highgui.imwrite("pictureDB/"+Main.ID+".jpg",recordToFile.mat);
		try {
			Files.write(Paths.get("xmlDB/"+Main.ID+".txt"), xml.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		MyFrame.videoCap.release();
	}
}
class confirmPicture extends JFrame{
	private static final long serialVersionUID = 1L;

	public confirmPicture()
	{
		setSize(200, 100);
		setLayout(new GridLayout(2,2));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
		JButton yes = new JButton("YES");
		JButton no = new JButton("NO");
		yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				recordToFile.doIt();
				MyFrame.a.Window.dispose();
				MyFrame.frame.dispose();
				MyFrame.frame.dispose();
				MyFrame.jframe.dispose();
				
			}
		});
		no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		add(new JLabel("Confirm image?"));
		add(new JLabel());
		add(yes);
		add(no);
		setVisible(true);
		
	}
}
