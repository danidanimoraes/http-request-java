package project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import project.object.Data;

public class BusinessServiceTest
{
	@Test
    public void shouldHaveOnlyOneInstance()
    {
		BusinessService instance1 = BusinessService.getInstance();
		BusinessService instance2 = BusinessService.getInstance();
    	assertEquals(instance1, instance2);
    }

	@Test
    public void shouldReturnQuoteDataCorrectly()
    {
		BusinessService businessService = BusinessService.getInstance();
		Data result = businessService.getData("Mary");
		assertNotNull(result);
		assertNotNull(result.name);
		assertNotNull(result.param2);
		assertNotNull(result.param3);
    }
}
