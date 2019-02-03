import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AE3Model {
	//attributes
	private static ArrayList<bondTrade> trades;
	private static boolean s=false;

	//a method that reads the content of the file and store the data into a ArrayList
	public static void FileContent(File csv) throws FileNotFoundException,IOException{
		trades = new ArrayList<bondTrade>();
		
		try {
			 BufferedReader br = new BufferedReader(new FileReader(csv));  
	         String line = null; 
	         br.readLine();
			
			while((line = br.readLine())!=null) {
				String y = line.split(",")[0];
				String d = line.split(",")[1];
				String a = line.split(",")[2];
                double y2 = Double.parseDouble(y);
				int d2 = Integer.parseInt(d);
				int a2 = Integer.parseInt(a);
				
				trades.add(new bondTrade(y2,d2,a2));
			}
			s=true;	
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * a switch that helps to draw the dots and tick marks
	 * draw the dots and tick marks after opening and reading a file
	 */
	public static boolean getSwitch() {
		return s;
	}
	
	//a method that gets the data of trades
	public static ArrayList<bondTrade> getTrade(){
		return trades;
	}
}
