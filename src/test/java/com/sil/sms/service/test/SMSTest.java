/**
 * 
 */
package com.sil.sms.service.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.onnorokom.sms.v1.ArrayOfWsSms;
import com.onnorokom.sms.v1.SendSmsSoap;
import com.onnorokom.sms.v1.WsSms;
import com.sil.onnorokomsms.service.OnnorkomObjectBuilder;

/**
 * @author zubayer
 *
 */
public class SMSTest {

	private static final Logger logger = Logger.getLogger(SMSTest.class);
	private static final String END_POINT = "https://api2.onnorokomsms.com/sendsms.asmx";
	private static final String API_KEY = "92134";

	@BeforeClass
	public static void beforeClass() throws Exception {
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
	}

	@Test
	public void testNumberSms() {
		SendSmsSoap port = OnnorkomObjectBuilder.getSMSPort(END_POINT);
		String message = "Hi Bhaiya, This is a test message from SMS API of Onnorokom SMS Service";
		List<String> numbers = new ArrayList<>();
		numbers.add("01758444444");
		String value = port.numberSms(API_KEY, message, numbers.toString(), "TEXT", "", "");
		logger.info(value);
	}

	@Test
	public void testListSMS() {
		SendSmsSoap port = OnnorkomObjectBuilder.getSMSPort(END_POINT);
		String message = "Hi Bhaiya, This is a test message from SMS API of Onnorokom SMS Service";
		
		List<WsSms> wsSmses = new ArrayList<>();
		WsSms wsSms = new WsSms();
		wsSms.setMobileNumber("01515634889");
		wsSms.setSmsText(message);
		wsSms.setType("TEXT");
		wsSmses.add(wsSms);
		ArrayOfWsSms arrayOfWsSms = new ArrayOfWsSms();
		arrayOfWsSms.setWsSms(wsSmses);
		port.listSms(API_KEY, arrayOfWsSms, "", "");
	}

	@Ignore("It will work after two hour of send sms")
	@Test
	public void testSMSDeliveryStatus() {
		SendSmsSoap port = OnnorkomObjectBuilder.getSMSPort(END_POINT);
		String value = port.smsDeliveryStatus(API_KEY, "90769061");
		logger.info(value);
	}

	/*
	 * Some ids:
	 * 90767271
	 * 90767035
	 */
	@Test
	public void testCheckBalance() {
		SendSmsSoap port = OnnorkomObjectBuilder.getSMSPort(END_POINT);
		String response = port.getCurrentBalance(API_KEY);
		logger.info(response);
	}
}
