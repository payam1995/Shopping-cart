package Database;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument.Content;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class AdminPanel {
	JFrame frame;
	
	public AdminPanel()
	{
		
		frame = new JFrame("Admin Panel");
		
		JButton add = new JButton("ADD ITEM");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Add();
			}
		});
		JButton edit = new JButton("EDIT ITEM");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Edit();
			}
		});
		JButton remove = new JButton("REMOVE ITEM");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Remove();
			}
		});
		JButton view = new JButton("VIEW");
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new View();
			}
		});
		JButton logout = new JButton("LOGOUT");
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("logout button pressed");
				new logoutConfirm(frame).setVisible(true);
			}
		});
		
		frame.add(view);
		frame.add(add);
		frame.add(edit);
		frame.add(remove);
		frame.add(logout);
		frame.setLayout(new GridLayout(5, 1));
		frame.setSize(new Dimension(200, 500));
		frame.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class logoutConfirm extends JFrame {
	private static final long serialVersionUID = 1L;
	public logoutConfirm(JFrame frame)
	{
		setSize(400, 80);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
		JButton yes = new JButton("YES");
		JButton no = new JButton("NO");
		yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				frame.dispose();
				MyFrame.videoCap.release();
				new dummy();
			}
		});
		no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		setLayout(new GridLayout(2,2));
		add(new JLabel("Are you sure you wanna logout?"));
		add(new JLabel());
		add(yes);
		add(no);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}

class View {
	public View()
	{
		JFrame frame = new JFrame("Choose");

		JPanel topPanel = new JPanel();
		JCheckBox ID = new JCheckBox("ID"), name = new JCheckBox("Name"), stock = new JCheckBox("Stocks"), price = new JCheckBox("Price");
		topPanel.setLayout(new FlowLayout());
		topPanel.add(ID);
		topPanel.add(name);
		topPanel.add(stock);
		topPanel.add(price);

		JPanel midPanel = new JPanel();
		midPanel.setLayout(new FlowLayout());
		JRadioButton nameRadio = new JRadioButton("Name"), IDRadio = new JRadioButton("ID");
		ButtonGroup group = new ButtonGroup();
		group.add(IDRadio);
		group.add(nameRadio);
		JTextField field = new JTextField(18);
		midPanel.add(nameRadio);
		midPanel.add(IDRadio);
		midPanel.add(field);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		JButton submit = new JButton("Search");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String searchqry = "select ";
				List<String> colNames = new ArrayList<String>();
				if(ID.isSelected()) {searchqry += "id "; colNames.add("ID of product");}
				if(name.isSelected()&&ID.isSelected()) {searchqry += ",name ";colNames.add("Name of product");}
				if(name.isSelected()&&!ID.isSelected()) {searchqry += "name ";colNames.add("ID of product");}
				if(stock.isSelected()&&(name.isSelected()||ID.isSelected())) {searchqry += ",stock  ";colNames.add("Current Stock");}
				if(stock.isSelected()&&!(name.isSelected()||ID.isSelected())) {searchqry += "stock  ";colNames.add("Current stock");}
				if(price.isSelected()&&(name.isSelected()||ID.isSelected()||stock.isSelected())) {searchqry += ",price  ";colNames.add("Price of product");}
				if(price.isSelected()&&!(name.isSelected()||ID.isSelected()||stock.isSelected())) {searchqry += "price  ";colNames.add("Price of product");}
				searchqry+="from products ";
				if(!field.getText().equalsIgnoreCase(""))
				{
					searchqry += "where ";
					if(IDRadio.isSelected()) searchqry += "id= "+field.getText().toString();
					if(nameRadio.isSelected()) searchqry += "name= '"+field.getText().toString()+"'";
				}
				System.out.println(searchqry);
				if(colNames.size()>0)
					new searchQ(searchqry,"view",colNames);
			}
		});
		bottomPanel.add(submit);
		
		frame.setLayout(new GridLayout(5, 1));
		frame.setSize(350, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.add(new JLabel("Display : "));
		frame.add(topPanel);
		frame.add(new JLabel("Search by : (leave blank to display everything)"));
		frame.add(midPanel);
		frame.add(bottomPanel);
		frame.setVisible(true);
	}
}

class Remove{
	public Remove()
	{
		JFrame frame = new JFrame("Remove");
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new FlowLayout());
		JRadioButton nameRadio = new JRadioButton("Name"), IDRadio = new JRadioButton("ID");
		ButtonGroup group = new ButtonGroup();
		group.add(IDRadio);
		group.add(nameRadio);
		JTextField field = new JTextField(18);
		midPanel.add(nameRadio);
		midPanel.add(IDRadio);
		midPanel.add(field);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		JButton submit = new JButton("Search");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String searchqry = "select * ";
				List<String> colNames = new ArrayList<String>();
				searchqry+="from products ";
				if(!field.getText().equalsIgnoreCase(""))
				{
					searchqry += "where ";
					if(IDRadio.isSelected()) searchqry += "id= "+field.getText().toString();
					if(nameRadio.isSelected()) searchqry += "name= '"+field.getText().toString()+"'";
					System.out.println(searchqry);
					new searchQ(searchqry,"remove",colNames);
				}
				
				
			}
		});
		bottomPanel.add(submit);
		
		frame.setLayout(new GridLayout(3, 1));
		frame.setSize(350, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.add(new JLabel("Search by : "));
		frame.add(midPanel);
		frame.add(bottomPanel);
		frame.setVisible(true);
	}
}

class Edit{
	public Edit()
	{
		JFrame frame = new JFrame("Edit");
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new FlowLayout());
		JRadioButton nameRadio = new JRadioButton("Name"), IDRadio = new JRadioButton("ID");
		ButtonGroup group = new ButtonGroup();
		group.add(IDRadio);
		group.add(nameRadio);
		JTextField field = new JTextField(18);
		midPanel.add(nameRadio);
		midPanel.add(IDRadio);
		midPanel.add(field);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		JButton submit = new JButton("Search");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String searchqry = "select * ";
				List<String> colNames = new ArrayList<String>();
				searchqry+="from products ";
				if(!field.getText().equalsIgnoreCase(""))
				{
					searchqry += "where ";
					if(IDRadio.isSelected()) searchqry += "id= "+field.getText().toString();
					if(nameRadio.isSelected()) searchqry += "name= '"+field.getText().toString()+"'";
					System.out.println(searchqry);
					new searchQ(searchqry,"edit",colNames);
				}
			}
		});
		bottomPanel.add(submit);
		
		frame.setLayout(new GridLayout(3, 1));
		frame.setSize(350, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.add(new JLabel("Search by : "));
		frame.add(midPanel);
		frame.add(bottomPanel);
		frame.setVisible(true);
	}
}

class Add{
	public Add()
	{
		new Form("","add",new JFrame(),new Object[0][0]);
	}
}

class searchQ {
	public searchQ(){}
	public searchQ(String qry,String options,List<String> colNames)
	{
		try {
			Statement stmt = db_auth.conn.createStatement();
			ResultSet rset = stmt.executeQuery(qry);
//			while (rset.next())
//			  System.out.println(rset.getString(1));
//			rset.close();
//			stmt.close();
			int x = count(qry);
			new Table(stmt,rset,x,options,qry,colNames);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static int count(String qry) throws SQLException
	{
		Statement stmt = db_auth.conn.createStatement();
		ResultSet rset = stmt.executeQuery(qry);
		int x=0;
		while (rset.next())
			  x++;
		System.out.println(x+" record found!");
		stmt.close();
		rset.close();
		return x;
	}
}

class Table {
	JFrame frame = new JFrame();
	public Table(Statement stmt, ResultSet rset,int rows,String options,String qry, List<String> colNames)
	{
		if(rows>0)
			try {
				
				if(options.equals("remove")||options.equals("edit"))
				{
					colNames.add("ID of product");
					colNames.add("Name of product");
					colNames.add("Stock");
					colNames.add("Price of product");
				}
				Object rowData[][] = new Object[rows][colNames.size()];
				Object columnNames[] = new Object[colNames.size()];
				for(int i=0;i<rows;i++)
				{
					rset.next();
					for(int j=0;j<colNames.size();j++)
					{
						if(i==0) columnNames[j] = colNames.get(j);
						rowData[i][j] = rset.getString(j+1);
					}
				}
				
				JTable table = new JTable(rowData, columnNames);
				table.setEnabled(false);
				JScrollPane scrollPane = new JScrollPane(table);
			    frame.add(scrollPane, BorderLayout.CENTER);
			    frame.setSize(140*colNames.size(), 50+25*rows);
			    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
				frame.setVisible(true);
				
				if(options.equals("remove"))
				{
					new RemovedialogueBox(qry,frame);
				}
				if(options.equals("edit"))
				{
					new Form(qry,"edit",frame,rowData);
				}
				
				rset.close();
				stmt.close();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		else {
			JButton ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.dispose();
				}
			});
			frame.setSize(200, 100);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
			frame.setLayout(new GridLayout(2,1));
			frame.add(new JLabel("no records found"));
			frame.add(ok);
			frame.setVisible(true);
		}
	}
}

class RemovedialogueBox{
	public RemovedialogueBox(String qry,JFrame main)
	{
		JFrame frame = new JFrame("dialogue");
		frame.setSize(300, 100);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2-200);
		frame.setLayout(new GridLayout(2,2));
		JButton yes = new JButton("YES");
		JButton no = new JButton("NO");
		yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				delete(qry);	//keep track of Main.ID or something
				frame.dispose();
				main.dispose();
			}
		});
		no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		frame.add(new JLabel("Are you sure you wanna delete this record?"));
		frame.add(new JLabel());
		frame.add(yes);
		frame.add(no);
		frame.setVisible(true);
	}
	private static void delete(String qry)
	{
		try {
			qry = qry.substring(8, qry.length());
			qry = "delete "+qry;
			System.out.println(qry);
			Statement stmt = db_auth.conn.createStatement();
			ResultSet rset = stmt.executeQuery(qry);
			Main.ID--;
			stmt.close();
			rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
}

class Form{
	public Form(String qry,String option,JFrame main,Object rowData[][])
	{
		JFrame frame = new JFrame(option);
		frame.setSize(400, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setLayout(new GridLayout(5,2));
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		namePanel.add(new JLabel("Enter name"));
		JTextField name = new JTextField(20);
		if(option.equals("edit")) 
			name.setText(rowData[0][1].toString());
		namePanel.add(name);
		
		JPanel stockPanel = new JPanel();
		stockPanel.setLayout(new FlowLayout());
		stockPanel.add(new JLabel("Enter stock"));
		JTextField stock = new JTextField(20);
		if(option.equals("edit")) 
			stock.setText(rowData[0][2].toString());
		stockPanel.add(stock);
		
		JPanel pricePanel = new JPanel();
		pricePanel.setLayout(new FlowLayout());
		pricePanel.add(new JLabel("Enter price"));
		JTextField price = new JTextField(20);
		if(option.equals("edit")) 
			price.setText(rowData[0][3].toString());
		pricePanel.add(price);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(option.equals("edit"))
					update(rowData[0][0].toString(),name,stock,price);
				if(option.equals("add")&&!(name.getText()==""&&stock.getText()==""&&price.getText()==""))
					add(name,stock,price);
			}
		});
		
		JButton addPicture = new JButton("Add picture");
		addPicture.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MyFrame a = new MyFrame();
				a.start();
				//access camera
				//show in separate frame
				//start servo (on user call)
				//stop servo (on user call)
				//capture button
				//select RIO (four corners)
				//add "ID"+".jpg" in a directory & the xml file
				//display success
			}
		});
		
		frame.add(namePanel);
		frame.add(stockPanel);
		frame.add(pricePanel);
		frame.add(addPicture);
		frame.add(submit);
		frame.setVisible(true);
	}
	
	private static void update(String ID, JTextField name, JTextField stock, JTextField price)
	{
		try {
			Statement stmt = db_auth.conn.createStatement();
			ResultSet rset = stmt.executeQuery("update products set ID="+ID+", name = '"+name.getText()+"', stock ="+stock.getText()+", price = "+price.getText()+" where id="+ID);
			System.out.println("update products set ID="+ID+", name = '"+name.getText()+"', stock ="+stock.getText()+", price = "+price.getText()+" where id="+ID);
			edited();
			stmt.close();
			rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void add(JTextField name, JTextField stock, JTextField price)
	{
		try {
			Statement stmt = db_auth.conn.createStatement();
			ResultSet rset = stmt.executeQuery("INSERT INTO products VALUES ("+Main.ID+",'"+name.getText()+"',"+price.getText()+","+stock.getText()+")");
			System.out.println("INSERT INTO products VALUES ("+Main.ID+",'"+name.getText()+"',"+price.getText()+","+stock.getText()+")");
			Main.ID++;
			added(name, stock, price);
			stmt.close();
			rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void added(JTextField name, JTextField stock, JTextField price)
	{
		JFrame frame = new JFrame("OK");
		JButton OK = new JButton("OK");
		OK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				name.setText("");
				stock.setText("");
				price.setText("");
			}
		});
		frame.setSize(200, 100);
		frame.add(OK);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	private static void edited()
	{
		JFrame frame = new JFrame("OK");
		JButton OK = new JButton("OK");
		OK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		frame.setSize(200, 100);
		frame.add(OK);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
}

class Mat2Image {
    static Mat mat = new Mat();
    BufferedImage img;
    byte[] dat;
    public Mat2Image() {
    }
    public Mat2Image(Mat mat) {
        getSpace(mat);
    }
    public void getSpace(Mat mat) {
        Mat2Image.mat = mat;
        int w = mat.cols(), h = mat.rows();
        if (dat == null || dat.length != w * h * 3)
            dat = new byte[w * h * 3];
        if (img == null || img.getWidth() != w || img.getHeight() != h || img.getType() != BufferedImage.TYPE_3BYTE_BGR)
                img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        }
        
    BufferedImage getImage(Mat mat){
        getSpace(mat);
        mat.get(0, 0, dat);
        byte b;  
        for(int i=0; i<dat.length; i=i+3) {  
		    b = dat[i];  
		    dat[i] = dat[i+2];  
		    dat[i+2] = b;  
        } 
        img.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), dat);
        return img;
    }
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}

class VideoCap {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public VideoCapture cap;
    static Mat2Image mat2Img = new Mat2Image();

    VideoCap(){
        cap = new VideoCapture();
        cap.open(1);
    } 
 
    BufferedImage getOneFrame() {
        cap.read(Mat2Image.mat);
        return mat2Img.getImage(Mat2Image.mat);
    }
    static Mat getOneFrameInMat() {
    	return Mat2Image.mat;
    }
    public void release()
    {
    	cap.release();
    }
}

class MyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane;
	public static List<Integer> ROIX = new ArrayList<Integer>();
	public static List<Integer> ROIY = new ArrayList<Integer>();
	static MyFrame jframe;
    public void start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    jframe = new MyFrame();
                    if(dummy.rightsdb.equals("admin"))
                    	jframe.buttonPanel();
                    jframe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MyFrame() {
    	setBounds(0, 0, 650, 490);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        new MyThread().start();
    }
    static int i=0;
    static boolean captured = false;
    static ImShow a;
    static Mat capFrame;
    static JFrame frame;
    
    public void buttonPanel() {
    	
    	frame = new JFrame("buttons");
    	frame.setUndecorated(true);
    	frame.setSize(300, 100);
    	frame.setLocation(200, 490);
    	frame.setLayout(new GridLayout(2,2));
    	JButton capture = new JButton("Capture");
    	capture.addActionListener(new ActionListener() {

    		@Override
			public void actionPerformed(ActionEvent arg0) {
				captured = true;
				a = new ImShow("preview");
				a.setLocation(650, 0);
				capFrame = (VideoCap.getOneFrameInMat());
				a.showImage(capFrame);
				//save image to temp location
			}
		});
    	
    	JButton selectROI = new JButton("Select ROI");
    	selectROI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(captured)
				{
					i=0;
					System.out.println("Captured!");
					
					a.Window.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseReleased(MouseEvent arg0) {
							
						}
						
						@Override
						public void mousePressed(MouseEvent arg0) {
							
						}
						
						@Override
						public void mouseExited(MouseEvent arg0) {
							
						}
						
						@Override
						public void mouseEntered(MouseEvent arg0) {
							
						}
						
						@Override
						public void mouseClicked(MouseEvent arg0) {
							
							if(i<4)
							{
								ROIX.add(arg0.getX());
								ROIY.add(arg0.getY());
								System.out.println(arg0.getX()+" "+arg0.getY());
								
							}
//							if(i>=4) captured =false;
							i++;
							if(i==1) {Core.line(capFrame, new Point(ROIX.get(0)-5,ROIY.get(0)-25), new Point(ROIX.get(0)-5,ROIY.get(0)-25), new Scalar(0,255,0),5);}
							if(i==2) Core.line(capFrame, new Point(ROIX.get(0)-5,ROIY.get(0)-25), new Point(ROIX.get(1)-5,ROIY.get(1)-25), new Scalar(0,255,0),5);
							if(i==3) {
								Core.line(capFrame, new Point(ROIX.get(0)-5,ROIY.get(0)-25), new Point(ROIX.get(1)-5,ROIY.get(1)-25), new Scalar(0,255,0),5);
								Core.line(capFrame, new Point(ROIX.get(1)-5,ROIY.get(1)-25), new Point(ROIX.get(2)-5,ROIY.get(2)-25), new Scalar(0,255,0),5);
							}
							if(i==4) {
								Core.line(capFrame, new Point(ROIX.get(0)-5,ROIY.get(0)-25), new Point(ROIX.get(1)-5,ROIY.get(1)-25), new Scalar(0,255,0),5);
								Core.line(capFrame, new Point(ROIX.get(1)-5,ROIY.get(1)-25), new Point(ROIX.get(2)-5,ROIY.get(2)-25), new Scalar(0,255,0),5);
								Core.line(capFrame, new Point(ROIX.get(2)-5,ROIY.get(2)-25), new Point(ROIX.get(3)-5,ROIY.get(3)-25), new Scalar(0,255,0),5);
								Core.line(capFrame, new Point(ROIX.get(3)-5,ROIY.get(3)-25), new Point(ROIX.get(0)-5,ROIY.get(0)-25), new Scalar(0,255,0),5);
							}
							
							if(i<=4) a.showImage(capFrame);
							System.out.println(i);
							if(i>4)
								new recordToFile(capFrame,ROIX,ROIY);
						}
					});
					
				}
			}
		});
    	frame.add(capture);
    	frame.add(selectROI);
    	frame.setVisible(true);
    	
//    	return toReturn;
    }
    public static VideoCap videoCap = new VideoCap();
 
    public void paint(Graphics g){
        g = contentPane.getGraphics();
        g.drawImage(videoCap.getOneFrame(), 0, 0, this);
    }
    
    class MyThread extends Thread{
        @Override
        public void run() {
            for (;;){
                repaint();
//                if(captured) try { Thread.sleep(100000);
//                } catch (InterruptedException e) {    };
                try { Thread.sleep(5);
                } catch (InterruptedException e) {    }
            }  
        } 
    }
    
}