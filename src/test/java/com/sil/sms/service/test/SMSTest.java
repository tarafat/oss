/**
 * 
 */
package com.sil.sms.service.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
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
	private static final String END_POINT = "oss.endpoint";
	private static final String API_KEY = "oss.api.key";
	private static SendSmsSoap port;

	@BeforeClass
	public static void beforeClass() throws Exception {
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999");

		port = OnnorkomObjectBuilder.getSMSPort(System.getProperty(END_POINT));
	}

	@Test
	public void testNumberSms() {
		String message = "Hi Bhaiya, This is a test message from SMS API of Onnorokom SMS Service";
		List<String> numbers = new ArrayList<>();
		numbers.add("01515634889");
		String value = port.numberSms(System.getProperty(API_KEY), message, numbers.toString(), "TEXT", "", "");
		logger.info(value);
	}

	@Test
	public void testListSMS() {
		String message = "Hi Bhaiya, This is a test message from SMS API of Onnorokom SMS Service";

		List<WsSms> wsSmses = new ArrayList<>();
		WsSms wsSms = new WsSms();
		wsSms.setMobileNumber("01515634889");
		wsSms.setSmsText(message);
		wsSms.setType("TEXT");
		wsSmses.add(wsSms);

		WsSms wsSms2 = new WsSms();
		wsSms2.setMobileNumber("01748562164");
		wsSms2.setSmsText("This is message 2");
		wsSms2.setType("TEXT");
		wsSmses.add(wsSms2);

		ArrayOfWsSms arrayOfWsSms = new ArrayOfWsSms();
		arrayOfWsSms.getWsSms().add(wsSms);
//		arrayOfWsSms.getWsSms().add(wsSms2);
		port.listSms(System.getProperty(API_KEY), arrayOfWsSms, "", "");
	}

	//@Ignore("It will work after two hour of send sms")
	@Test
	public void testSMSDeliveryStatus() {
		String value = port.smsDeliveryStatus(System.getProperty(API_KEY), "90769061");
		logger.info(value);
	}

	/*
	 * Some ids:
	 * 90767271
	 * 90767035
	 */
	@Test
	public void testCheckBalance() {
		String response = port.getCurrentBalance(System.getProperty(API_KEY));
		logger.info(response);
	}

	@Test
	public void parseReturnValue() {
		String value = "1900||01515634889||91006356/1900||01748562164||91006357/";
		String[] fullSMS = value.split("/");
		for(int i = 0; i < fullSMS.length; i++) {
			logger.info(fullSMS[i]);
			String[] values = fullSMS[i].split("\\|\\|");
			for(int j = 0; j < values.length; j++) {
				logger.info(values[j]);
			}
		}
	}
}
