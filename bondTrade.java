
public class bondTrade {
	//3 attributes: yield, days to maturity and amount
	private double yield;
	private int days;
	private int amount;
	
	//constructor
	public bondTrade(double y,int d,int a) {
		yield = y;
		days = d;
		amount = a;
	}
	
	//3 getter method that can get the value of the attribute
	public double getYield() {
		return yield;
	}
	
	public int getDays() {
		return days;
	}
	
	public int getAmount() {
		return amount;
	}
}
