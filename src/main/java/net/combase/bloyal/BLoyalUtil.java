package net.combase.bloyal;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import com.bloyal.service._3_5.PaymentEngine;

/**
 * 
 * @author "Till Freier"
 *
 */
public final class BLoyalUtil
{

	private BLoyalUtil()
	{
	}


	public static PaymentEngine createWebService(String url)
	{
		try
		{
			final URL localWsdlUrl = BLoyalUtil.class.getResource("/PaymentEngine.wsdl");
			final Service service = Service.create(localWsdlUrl, new QName(
				"http://service.bloyal.com/3.5", "PaymentEngineService"));
			final PaymentEngine port = service.getPort(PaymentEngine.class);
			final Map<String, Object> requestContext = ((BindingProvider)port).getRequestContext();
			requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

			final Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
			httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);

			return port;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
