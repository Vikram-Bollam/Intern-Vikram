package full.aw.helper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class Conversion {
	//Converting MilliSeconds To Date 
	public static String milliToDate(long milli, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milli);
		return formatter.format(cal.getTime());
	}//method close
     
	
	//Converting Date to MilliSeconds
	public static Long dateToMilli(String date, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try {
			Date requriedDate = formatter.parse(date);
			long milli = requriedDate.getTime();
			return milli;
		} catch (ParseException e) {
			return null;
		}
	}//dateToMilliClose
	
	//method to get current date 
	public static String getCurrentDate(String dateFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	    Date date = new Date();  
		return formatter.format(date);
	}//method close
}//class close
