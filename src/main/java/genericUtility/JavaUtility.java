package genericUtility;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {
	//1. method to calculate current date
	public String getCurrentDate() {
		 Date date=new Date();
		 SimpleDateFormat sim=new SimpleDateFormat("dd-MMyyyy");
		 String currentDate = sim.format(date);
		 return currentDate;
	}
	//2. method to calculate date after n days from present date
	public String togetReqDate(int days)
	{
		Date date = new Date();
		//SimpleDateFormat is a class
		SimpleDateFormat sim = new SimpleDateFormat("dd-MM-yyyy");//MM should always be capital
		sim.format(date);
		Calendar cal = sim.getCalendar();//returns calendar interface and storing it
		//				current date , add 30 days	
		cal.add(Calendar.DAY_OF_MONTH, days);
		String datereq = sim.format(cal.getTime());//format for exact date after current date
		return datereq;
	}
	//3. Method to generate some random numbers, can't be used in Campaign name as doesnot accept numbers
	public int togetRandomCount() 
	{
		Random rand = new Random(500); //500 times new random data will be generated
		int randcount = rand.nextInt();
		return randcount;
	}
	//4. Method to generate random alphabets
	public char randomAlphabets()
	{
		char letter;
		Random rand = new Random();
		if(rand.nextBoolean()) { letter = (char)('a'+ rand.nextInt(26)); }
		else { letter = (char)('A'+ rand.nextInt(26)); }
		return letter;	
	}
}







