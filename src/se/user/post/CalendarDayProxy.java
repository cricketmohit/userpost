package se.user.post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

public class CalendarDayProxy {

	public CalendarDay getCalendar(String date) {
		// Call for calendar
		HttpClient client = new DefaultHttpClient();
		// String buffer for future updates
		StringBuffer dateURL = new StringBuffer(
		"https://www.atg.se/services/v1/calendar/day/");
		dateURL.append(date);
		HttpGet request = new HttpGet(dateURL.toString());
		HttpResponse httpResponse;
		CalendarDay calendarDay = null;
		try {
			httpResponse = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			String response = rd.readLine();
			Gson gson = new Gson();
			System.out.println(response);
			calendarDay = gson.fromJson(response, CalendarDay.class);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendarDay;
	}

}
