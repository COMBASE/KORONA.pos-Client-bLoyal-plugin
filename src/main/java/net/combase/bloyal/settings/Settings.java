/**
 * 
 */
package net.combase.bloyal.settings;

import java.io.Serializable;

/**
 * @author Till Freier
 *
 */
public class Settings implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2756760963012353808L;
	private String loyaltyEngineServiceUrl;
	private String deviceCode;
	private String storeCode;
	private String deviceKey;

	private String koronaGiftCardAccountNumber;
	private String koronaGiftCardPaymentMethodNumber;

	private int port = 8080;

	private boolean autostart = false;


	public boolean isAutostart()
	{
		return autostart;
	}

	public void setAutostart(boolean autostart)
	{
		this.autostart = autostart;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getDeviceKey()
	{
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey)
	{
		this.deviceKey = deviceKey;
	}

	public String getLoyaltyEngineServiceUrl()
	{
		return loyaltyEngineServiceUrl;
	}

	public void setLoyaltyEngineServiceUrl(String loyaltyEngineServiceUrl)
	{
		this.loyaltyEngineServiceUrl = loyaltyEngineServiceUrl;
	}

	public String getDeviceCode()
	{
		return deviceCode;
	}

	public void setDeviceCode(String user)
	{
		this.deviceCode = user;
	}

	public String getStoreCode()
	{
		return storeCode;
	}

	public void setStoreCode(String company)
	{
		this.storeCode = company;
	}

	public String getKoronaGiftCardAccountNumber()
	{
		return koronaGiftCardAccountNumber;
	}

	public void setKoronaGiftCardAccountNumber(String koronaGiftCardAccountNumber)
	{
		this.koronaGiftCardAccountNumber = koronaGiftCardAccountNumber;
	}

	public String getKoronaGiftCardPaymentMethodNumber()
	{
		return koronaGiftCardPaymentMethodNumber;
	}

	public void setKoronaGiftCardPaymentMethodNumber(String koronaGiftCardPaymentMethodNumber)
	{
		this.koronaGiftCardPaymentMethodNumber = koronaGiftCardPaymentMethodNumber;
	}


}
