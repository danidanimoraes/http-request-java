package project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import project.object.Data;

public class CustomerDataServiceTest extends CustomerDataService
{
	@Test
    public void shouldHaveOnlyOneInstance()
    {
		CustomerDataService instance1 = CustomerDataService.getInstance();
		CustomerDataService instance2 = CustomerDataService.getInstance();
    	assertEquals(instance1, instance2);
    }

	@Test
    public void shouldReturnData()
    {
		CustomerDataService financialDataService = CustomerDataService.getInstance();
		double result = financialDataService.getData("Mary");
		assertNotNull(result);
    }

	@Test
    public void shouldCalculateMarketCapitalizationCorrectly()
    {
		CustomerDataService financialDataService = CustomerDataService.getInstance();
		BusinessService businessService = new BusinessService();
		Data resultBusinessService = businessService.getData("Mary");
		double result = financialDataService.calculateData(
			resultBusinessService.param2,
			resultBusinessService.param3
		);
		double expectedResult = resultBusinessService.param2 * resultBusinessService.param3;
		assertEquals(result, expectedResult, 0);
    }
}
