package project.service;

import com.google.gson.Gson;

import project.object.Data;

/**
 * Class that contains business methods that calls
 * the API via {@link HttpService}.
 */
public class BusinessService
{
	private static final String HOST = "mockHost";
	private static final String SPECIFIC_URL = "http://%s/specificApi/%s";

	private static BusinessService instance;

	/**
	 * @return The unique instance of this service
	 */
	public static BusinessService getInstance()
	{
		if(instance == null)
		{
			instance = new BusinessService();
		}
		return instance;			
	}

	protected BusinessService()
	{}

	/**
	 * Gets data from specific API for the given customer.
	 *
	 * @param customer The customer to get the data from
	 * @return The JSON response
	 */
	public Data getData(String customer)
	{
		Gson gson = new Gson();
		String url = String.format(SPECIFIC_URL, HOST, customer);
		return gson.fromJson(HttpService.getInstance().doGet(url), Data.class);
	}
}
