package project.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.core.Response;

/**
 * Class responsible for HTTP requests to the server.
 */
public class HttpService
{
	private static HttpService instance;

	/**
	 * @return The unique instance of this service
	 */
	public static HttpService getInstance()
	{
		if(instance == null)
		{
			instance = new HttpService();
		}
		return instance;			
	}

	protected HttpService()
	{}
	
	/**
	 * Executes a GET request to the API with the given URL.
	 *
	 * @param URL The URL to execute the GET operation
	 * @return The JSON response
	 */
	public String doGet(String URL)
	{
		String httpResponse = "";

		try
		{
			URL url = new URL(URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);
			
			if(connection.getResponseCode() != Response.Status.OK.getStatusCode())
			{
				throw new RuntimeException("HTTP request returned " + connection.getResponseCode()
					+ ".\n URL: " + connection.getURL());
			}

			httpResponse = this.getHttpResponse(connection);
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException("Error while building the URL.", e);
		}
		catch (IOException e)
		{
			throw new RuntimeException("An error ocurred with the connection.", e);
		}

		return httpResponse;
	}

	/**
	 * Returns the HTTP response as a JSON for the given connection.
	 *
	 * @param connection The connection to get the response from
	 * @return The response JSON
	 */
	private String getHttpResponse(HttpURLConnection connection)
	{
		String responseJSON = "";

		try
		{
			InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(inputStream);
			StringBuffer response = new StringBuffer();
			String responseLine;

			while ((responseLine = reader.readLine()) != null)
			{
				response.append(responseLine);
			}

			reader.close();
			responseJSON = response.toString();
		} 
		catch (IOException e)
		{
			throw new RuntimeException("Error while trying to read the response from the server.", e);
		}

		return responseJSON;
	}

}
