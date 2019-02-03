import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.scene.control.ComboBox;

public class PlotFrame extends JFrame implements ActionListener,ItemListener,MouseListener{
	//attributes
	private JButton openbt = new JButton("Open");
	private JButton quitbt = new JButton("Quit");
	private JTextField filename = new JTextField(20);
	private JTextArea tradedetail = new JTextArea(5,25);
	private JComboBox xcb = new JComboBox();
	private JComboBox ycb = new JComboBox();
	private JFileChooser fc = new JFileChooser();
	private AE3Model model;
	private PlotContent plotContent;
	
	//constructor
	public PlotFrame(AE3Model model){
		this.model = model;
		plotContent =  new PlotContent(0,0);
		this.setSize(700, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
		this.setVisible(true);
		plotContent.addMouseListener(this);

	}

	//a method that initializes the panel
	public void initialize() {
		/*
		 * one Jpanel in the north which contains two buttons and one textfield:
		 * open, quit buttons and textfield that shows the name of the file
		 */
		JPanel myPanel1 = new JPanel();
		this.add(myPanel1,BorderLayout.NORTH);
		
		myPanel1.add(openbt);
		openbt.addActionListener(this);
		filename.setEditable(false);
		myPanel1.add(filename);
		myPanel1.add(quitbt);
		quitbt.addActionListener(this);

		//one Jcomponent in the centre for the drawing area
		this.add(plotContent,BorderLayout.CENTER);
		
		/*
		 * one Jpanel in the south that has two combobox and one textarea
		 * the comboboxes can change the column used for the x axis and y axis
		 * the text area shows the corresponding trade that has be clicked
		 */
		JPanel myPanel2 = new JPanel();
		this.add(myPanel2,BorderLayout.SOUTH);
		String[] options1 = { "YIELD", "DAYS_TO_MATURITY", "AMOUNT_CHF(000)"};
		String[] options2 = { "DAYS_TO_MATURITY", "YIELD", "AMOUNT_CHF(000)"};
		xcb = new JComboBox(options1);
		ycb = new JComboBox(options2);
		myPanel2.add(xcb);
		myPanel2.add(ycb);
		xcb.addItemListener(this);
		ycb.addItemListener(this);
		tradedetail.setEditable(false);
		myPanel2.add(tradedetail);	
	}
	
	//a method that can use JFileChooser to obtain the name of the input file
	public void openFile() {
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
	        filename.setText(file.getName());
	        try {
				model.FileContent(file);	//read the content of the input file
				plotContent.repaint();	//repaint the JComponent
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/*
	 * a method that listens to the action:
	 * if the action is clicking the open button, then the button will trigger a JFileChooser to choose the file;
	 * if the action is clicking the quit button, the program will exit.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== openbt) {
			openFile();
		}
		else if(e.getSource()== quitbt) {
			System.exit(0);	
		}			
	}	
	
	//the user clicks the JComboBox to change the column used for the axes
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			/*
			 * the user can change the column used for x-axis
			 * the initial value for x-axis is yield
			 */
			if(xcb.getSelectedItem().equals("YIELD")) {
				plotContent.setI(0);
				plotContent.repaint();
			}else if(xcb.getSelectedItem().equals("DAYS_TO_MATURITY")) {
				plotContent.setI(1);
				plotContent.repaint();
			}else if(xcb.getSelectedItem().equals("AMOUNT_CHF(000)")) {
				plotContent.setI(2);
				plotContent.repaint();
			}
			
			/*
			 * the user can change the column used for y-axis
			 * the initial value for y-axis is days to maturity
			 */
			if(ycb.getSelectedItem().equals("DAYS_TO_MATURITY")) {
				plotContent.setJ(0);
				plotContent.repaint();
			}else if(ycb.getSelectedItem().equals("YIELD")) {
				plotContent.setJ(1);
				plotContent.repaint();
			}else if(ycb.getSelectedItem().equals("AMOUNT_CHF(000)")) {
				plotContent.setJ(2);
				plotContent.repaint();
			}
		}
	}

	/*
	 * a method that listens to the mouse:
	 * get the x and y positions of the mouse
	 * pass the positions to another method to checks if the dot clicked by the mouse matches the position of the trade dot
	 * if yes, print out the details of the trade in the text area
	 */
	
	public void mouseClicked(MouseEvent e) {
		double[] pointLocation = new double[3];
		double x = e.getX();
		double y = e.getY();
		
		plotContent.Click(x, y, pointLocation);
		if(pointLocation[0]==0.0&&pointLocation[1]==0.0&&pointLocation[2]==0.0) {
			
		}else {
			tradedetail.setText("\n"+"Yield = "+pointLocation[0]+"\n"+"Days_To_Maturity = "+pointLocation[1]+"\n"+"Amount_CHF = "+pointLocation[2]);
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {		
	}

	public void mouseEntered(MouseEvent e) {		
	}

	public void mouseExited(MouseEvent e) {		
	}


}
