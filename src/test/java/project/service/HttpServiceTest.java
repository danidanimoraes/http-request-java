package project.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HttpServiceTest
{
	@Test
    public void shouldHaveOnlyOneInstance()
    {
    	HttpService instance1 = HttpService.getInstance();
    	HttpService instance2 = HttpService.getInstance();
    	assertEquals(instance1, instance2);
    }
}
