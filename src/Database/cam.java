package Database;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class cam extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	public cam(){
		super();
	}
	private BufferedImage getimage(){
		return image;
	}
	private void setimage(BufferedImage newimage){
		image=newimage;
		return;
	}
	public static BufferedImage matToBufferedImage(Mat matrix) {
		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int)matrix.elemSize();
		byte[] data = new byte[cols * rows * elemSize];
		int type;
		matrix.get(0, 0, data);
		switch (matrix.channels()) {
			case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
			case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			// bgr to rgb
			byte b;
			for(int i=0; i<data.length; i=i+3) {
				b = data[i];
				data[i] = data[i+2];
				data[i+2] = b;
			}
			break;
			default:
			return null;
		}
		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0, 0, cols, rows, data);
		return image2;
	}
	public void paintComponent(Graphics g){
		BufferedImage temp=getimage();
//		if(temp != null)
			g.drawImage(temp,10,10,temp.getWidth(),temp.getHeight(), this);
	}
	JFrame frame;// = new JFrame("image");
	cam panel;// = new cam();
	Mat webcam_image;//=new Mat();
	BufferedImage temp;
	VideoCapture capture;
	
	public void getReady()
	{
		capture =new VideoCapture(0);
		webcam_image=new Mat();
		panel = new cam();
		frame = new JFrame("image");
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
	public boolean display(){
//		frame.setContentPane(panel);
//		frame.setVisible(true);
		if( capture.isOpened())
		{
			
//			while( true )
//			{
				capture.read(webcam_image);
				if( !webcam_image.empty() )
				{
					frame.setSize(webcam_image.width()+40,webcam_image.height()+100);
					temp=matToBufferedImage(webcam_image);
					panel.setimage(temp);
					panel.repaint();
				}
				else
				{
					System.out.println(" --(!) No captured frame -- Break!");
//					break;
				}
//			}
		}
		System.out.println("out of if");
		return true;
	}
}