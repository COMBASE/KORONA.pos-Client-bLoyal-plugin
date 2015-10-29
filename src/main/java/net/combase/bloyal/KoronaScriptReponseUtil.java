/**
 * 
 */
package net.combase.bloyal;

import java.math.BigDecimal;

/**
 * @author "Till Freier"
 *
 */
public final class KoronaScriptReponseUtil
{

	private KoronaScriptReponseUtil()
	{
	}


	private static String showMessage(String title, String text, String level)
	{
		StringBuilder sb = new StringBuilder("korona_plugin_api.response.displayMessage({").append('\n');
		sb.append("text: \"").append(text).append("\",").append('\n');
		sb.append("title: \"").append(title).append("\",").append('\n');
		sb.append("level: \"").append(level).append("\"").append('\n');
		sb.append("});");

		return sb.toString();
	}

	public static String showMessage(String title, String text)
	{
		return showMessage(title, text, "INFO");
	}

	public static String showWarning(String title, String text)
	{
		return showMessage(title, text, "WARN");
	}

	public static String addAccountTransaction(String account, BigDecimal value)
	{
		StringBuilder sb = new StringBuilder("korona_plugin_api.response.addAccountTransaction({").append('\n');
		sb.append("accountNumber: ").append(account).append(',').append('\n');
		sb.append("amount: ").append(value).append(',').append('\n');
		sb.append("fixedAmount: true").append('\n');
		sb.append("});");

		return sb.toString();
	}

	public static String addPayment(String paymentMethodNo, BigDecimal value)
	{
		StringBuilder sb = new StringBuilder("korona_plugin_api.response.addPayment({").append('\n');
		sb.append("paymentMethodNumber: ").append(paymentMethodNo).append(',').append('\n');
		sb.append("inputAmount: ").append(value).append(',').append('\n');
		sb.append("});");

		return sb.toString();
	}

	public static String setInputLineValue(String value)
	{
		StringBuilder sb = new StringBuilder("korona_plugin_api.response.setInputLine({").append('\n');
		sb.append("value: \"").append(value).append('"').append('\n');
		sb.append("});");

		return sb.toString();
	}

	public static String exit()
	{
		return "korona_plugin_api.backToKorona();";
	}
}
