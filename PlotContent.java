import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JComponent;

public class PlotContent extends JComponent {
	//attributes
	private int i,j;
	private double[] arrayXpoint;
	private double[] arrayYpoint;
	
	//constructor
	public PlotContent(int i,int j) {
		this.i=i;
		this.j=j;
	}

	//a setter method that set the value of attribute i
	public void setI(int i) {
		this.i=i;
	}
	
	//a setter method that set the value of attribute j
	public void setJ(int j) {
		this.j=j;
	}
	
	//a JComponent that draws the scatter plot
	public void paintComponent(Graphics g) {
		//draw the box for the scatter plot. the size is 500*500 and the color is white.
		Graphics2D g2 = (Graphics2D)g;
		Rectangle box = new Rectangle(100,28,500,500);
		g2.draw(box);
		g2.setColor(Color.WHITE);
		g2.fill(box);
		
		//a loop that draws dots for each bond trade and the color of the dot is blue
		if(AE3Model.getSwitch()) {
			arrayXpoint=new double[AE3Model.getTrade().size()];
			arrayYpoint=new double[AE3Model.getTrade().size()];
			for(int u=0;u<AE3Model.getTrade().size();u++) {
				g2.setColor(Color.BLUE);
				Ellipse2D.Double e = new Ellipse2D.Double(drawPointX(this.i,u),drawPointY(this.j,u),5,5);
				
				//get the actual position in x axis and y axis of the dot
				arrayXpoint[u]=drawPointX(this.i,u);
				arrayYpoint[u]=drawPointY(this.j,u);
				g2.fill(e);
				g2.draw(e);
				//draw the tick marks and labels for these marks
				tickMarks(g2,axisX(this.i),axisY(this.j));
			}
		}	
	}
	

	//a method that draws the tick marks and labels for these marks
	public void tickMarks(Graphics2D g2,String[]n,String[] m) {
		//draw 10 tick marks for x axis. the size of the mark is 5 and the gap between marks is 50
		int x = 100;
		for (int u = 0; u < 10; u++) {
			g2.setColor(Color.BLACK);
			g2.drawLine(x, 528, x, 528+5);
			x = x + 50;
		}
		
		//draw 10 tick marks for y axis. the size of the mark is 5 and the gap between marks is 50
		int y = 28+50;
		for (int u = 0; u < 10; u++) {
			g2.setColor(Color.BLACK);
			g2.drawLine(100-5, y, 100, y);
			y = y + 50;
		}

		/*
		 * draw the labels for tick marks
		 * get the value of the label by calling a method "axisX"
		 * set the location of the label according to the tick mark
		 */
		Font drawFont = new Font("Arial",Font.PLAIN,10);
		g2.setFont(drawFont);
		x = 100;
		for (int u = 0; u < 10; u++) {
			g2.drawString(axisX(this.i)[u].toString(), x-20, 528+20);
			x = x + 50;
		}
		
		/*
		 * draw the labels for tick marks
		 * get the value of the label by calling a method "axisY"
		 * set the location of the label according to the tick mark
		 */
		y = 28+50;
		for (int u = 9; u >=0; u--) { 
			g2.drawString(axisY(this.j)[u].toString(), 100-50, y+5);
			y = y + 50;
		}
	}
	
	/*
	 * a method that calculates the labels for x axis.
	 * the method would get values of the column that is chosen by the user
	 * then pass these values to another method to get the labels.
	 * if the user chooses "yield" for x axis, then the labels for x axis
	 * will change according to the data in the "yield" column.
	 */
	public String[] axisX(int i) {
		double[] column = new double[AE3Model.getTrade().size()];
		if(i==0) {
			for(int u=0;u<column.length;u++) {
				column[u] = AE3Model.getTrade().get(u).getYield();
			}
			return makeArray(column);
		}else if(i==1) {
			for(int u=0;u<column.length;u++) {
				column[u] = AE3Model.getTrade().get(u).getDays();
			}
			return makeArray(column);
		}
		else if(i==2) {
			for(int u=0;u<column.length;u++) {
				column[u] = AE3Model.getTrade().get(u).getAmount();
			}
			return makeArray(column);
		}
		return null;
	}
	
	/*
	 * a method that calculates the labels for y axis.
	 * the method would get values of the column that is chosen by the user
	 * then pass these values to another method to get the labels.
	 * if the user chooses "days_of_maturity" for y axis, then the labels for y axis
	 * will change according to the data in the "days_of_maturity" column.
	 */
	public String[] axisY(int j) {
		double[] column = new double[AE3Model.getTrade().size()];

		if(j==0) {
			for(int u=0;u<column.length;u++) {
				column[u] = AE3Model.getTrade().get(u).getDays();
			}
			return makeArray(column);
		}else if(j==1) {
			for(int u=0;u<column.length;u++) {
				column[u] = AE3Model.getTrade().get(u).getYield();
			}
			return makeArray(column);
		}
		else if(j==2) {
			for(int u=0;u<column.length;u++) {
				column[u] = AE3Model.getTrade().get(u).getAmount();
			}
			return makeArray(column);
		}
		return null;
	}

	/*
	 * a method that gets the data of the column that is chosen by the user
	 * calculate the maximum and minimum of the values
	 * calculate the gap according to the number of tick marks
	 * calculate the label for each tick mark
	 * store the values for 10 tick marks into a new string array
	 * and set the format of the string
	 */
	public String[] makeArray(double[] a) {
		double max = 0.0;
		double min = 0.0;
		
		String[] axis = new String[10];
		Arrays.sort(a);
		max = a[a.length-1];
		min = a[0];
		double gap = (max-min)/9.0;

		for(int p=0;p<10;p++) {
				axis[p]=Double.toString(min+p*gap).format("%.2f", min+p*gap);
		}
		return axis;
	}

	
	/*
	 * a method that draws the dot for each bond trade
	 * the position of the dot shifts according to the column chosen for the x axis
	 */
	public double drawPointX(int i,int u) {
		if (i==0) {
			double x=(AE3Model.getTrade().get(u).getYield()*22.8)+120;
			return x;
		}else if (i==1) {
			double x=(AE3Model.getTrade().get(u).getDays()/9.4)+93;
			return x;
		}else if (i==2) {
			double x=(AE3Model.getTrade().get(u).getAmount()/44.5)+100;
			return x;
		}else return 0.0;
	}
	
	/*
	 * a method that draws the dot for each bond trade
	 * the position of the dot shifts according to the column chosen for the y axis
	 */
	public double drawPointY(int j,int u) {
		if (j==0) {
			double y=30+500-(AE3Model.getTrade().get(u).getDays()/9.4);
			return y;
		}else if (j==1) {
			double y=500-(AE3Model.getTrade().get(u).getYield()*22.5);
			return y;
		}else if (j==2) {
			double y=23+500-(AE3Model.getTrade().get(u).getAmount()/45);
			return y;
		}else return 0.0;
	}

	
	/*
	 * a method that checks if the dot clicked by the mouse matches the position of the trade dot
	 * if yes, store the value of the trade into a new array
	 */
	
	public void Click(double x,double y,double[] pl) {
		for(int u=0;u<AE3Model.getTrade().size();u++) {
			if(((x<arrayXpoint[u]+7.00 && x>arrayXpoint[u]-7.00)&&(y<arrayYpoint[u]+7.00 && y>arrayYpoint[u]-7.00))){
				pl[0] = AE3Model.getTrade().get(u).getYield();
				pl[1] = AE3Model.getTrade().get(u).getDays();
				pl[2] = AE3Model.getTrade().get(u).getAmount();

			}
		}
	}
	
}