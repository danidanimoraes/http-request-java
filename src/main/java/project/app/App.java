package project.app;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import project.service.CustomerDataService;
import project.service.FinancialDataService;

/**
 * This application gets data every 5 seconds for 5 minutes.
 * Then, it returns to std out the total diff in data
 * and the maximum change in datan for a specific input
 * in this period.
 */
public class App 
{
	public static volatile String input = "John Doe";
	public static volatile double maxChangeInData = 0;
	public static volatile String timestampForMaxChangeInData = "";
	public static volatile double totalChangeInData = 0;
	public static volatile double currentData = 0;

	/**
	 * Callback function that:
	 * - Determines the maximum change in data for the current run
	 * - Determines the total change data until the current run
	 */
	static Consumer<Double> callback = (data) -> 
	{
		// Change in data since the last run
		double currentChange = Math.abs(data - currentData);

		// Determines maximum change until now
		if(currentChange > maxChangeInData)
		{
			String timestamp = DateTimeFormatter.ofPattern("MM/dd/yyyy-hh:mm:ss VV").format(ZonedDateTime.now());
			maxChangeInData = currentChange;
			timestampForMaxChangeInData = timestamp;
		}

		// Determines total change until now
		totalChangeInData = Math.abs(totalChangeInData - data);
	};

	/**
	 * This {@link TimerTask} class gets the data for
	 * a specific customer and executes the callback.
	 */
	static class StatsService extends TimerTask {	
	    public void run() {
	    	CustomerDataService customerDataService = CustomerDataService.getInstance();
	    	double data = customerDataService.getData(input);
	    	callback.accept(data);
	     }
	}

	/**
	 * Gets data from the server every 5 minutes,
	 * and after 5 minutes returns the following statistics
	 * for the period:
	 * - Total change in data
	 * - Maximum change in data, along with its timestamp
	 */
    public static void main( String[] args )
    {
    	System.out.println("Executing application...");
    
    	Timer timer5seconds = new Timer();
    	timer5seconds.schedule(new StatsService(), 0, 5000);

    	// Waits for 5 minutes to stop the execution
    	try
		{
			Thread.sleep(300000);
		}
    	catch (InterruptedException e)
		{
			throw new RuntimeException("Thread was interrupted while sleeping.", e);
		}

    	timer5seconds.cancel();

    	DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");
    	System.out.println("\nDATA FOR CUSTOMER " + input + "\n");
    	System.out.println("Max change in data:\n" 
    		+ decimalFormat.format(maxChangeInData)
    		+ " (at " + timestampForMaxChangeInData + ")\n"
    	);
    	System.out.println("Total change in data:\n" + decimalFormat.format(totalChangeInData));
    }
}
