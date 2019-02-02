package project.service;

import project.object.Data;

/**
 * Service responsible for providing customer data.
 */
public class CustomerDataService
{
	private static CustomerDataService instance;

	/**
	 * @return The unique instance of this service
	 */
	public static CustomerDataService getInstance()
	{
		if(instance == null)
		{
			instance = new CustomerDataService();
		}
		return instance;			
	}

	protected CustomerDataService()
	{}

	/**
	 * Returns the data from the given customer.
	 *
	 * @param customer The customer to get the data from
	 * @return The data
	 */
	public double getData(String customer)
	{
		Data data = BusinessService.getInstance().getData(customer);
		return this.calculateData(data.param2, data.param3);
	}

	/**
	 * Calculates data from param2 and param3.
	 *
	 * @param param2 The param2
	 * @param param3 The param3
	 * @return The data
	 */
	protected double calculateData(double param2, long param3)
	{
		return param2 * param3;
	}
}
