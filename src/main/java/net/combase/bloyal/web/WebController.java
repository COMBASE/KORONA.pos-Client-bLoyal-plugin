/**
 * 
 */
package net.combase.bloyal.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;

import net.combase.bloyal.BLoyalUtil;
import net.combase.bloyal.KoronaScriptReponseUtil;
import net.combase.bloyal.settings.DataStoreManager;
import net.combase.bloyal.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bloyal.schema._3.CardBalanceRequest;
import com.bloyal.schema._3.CardCreditRequest;
import com.bloyal.schema._3.CardRedeemRequest;
import com.bloyal.schema._3.CardRequestStatus;
import com.bloyal.schema._3.CardResponse;
import com.bloyal.schema._3.ObjectFactory;
import com.bloyal.service._3_5.PaymentEngine;

/**
 * @author "Till Freier"
 *
 */
@Controller
@RequestMapping(value = "/")
public class WebController
{
	private final PaymentEngine service;
	private final Settings settings;
	private final ObjectFactory objFactory;

	@Autowired
	public WebController(DataStoreManager store) throws MalformedURLException
	{
		super();
		settings = store.getSettings();
		objFactory = new ObjectFactory();
		String loyaltyEngineServiceUrl = settings.getLoyaltyEngineServiceUrl();
		System.out.println("create WS for " + loyaltyEngineServiceUrl);
		service = BLoyalUtil.createWebService(loyaltyEngineServiceUrl);

	}

	@RequestMapping(value = "/load", method = RequestMethod.GET, produces = "text/html")
	@ResponseBody
	public String loadCard(@RequestParam(value = "account", required = false) String account,
		@RequestParam(value = "amount", required = false) BigDecimal amount)
	{
		System.out.println("load account " + account + " $" + amount);
		CardCreditRequest req = objFactory.createCardCreditRequest();
		req.setAllowDuplicate(Boolean.TRUE);
		req.setAmount(amount);
		req.setCardNumber(objFactory.createCardCreditRequestCardNumber(account));
		try
		{
			CardResponse cardCredit = service.cardCredit(settings.getDeviceKey(),
				settings.getStoreCode(), settings.getDeviceCode(), req);

			System.out.println("" + cardCredit.getStatus() + " - " +
				cardCredit.getMessage().getValue());

			if (cardCredit.getStatus() == CardRequestStatus.APPROVED)
			{
				return KoronaScriptReponseUtil.addAccountTransaction(
					settings.getKoronaGiftCardAccountNumber(), amount) +
					KoronaScriptReponseUtil.showMessage("New Balance",
						cardCredit.getAvailableBalance()
							.setScale(2, RoundingMode.HALF_UP)
							.toString()) +
					KoronaScriptReponseUtil.setInputLineValue("") +
					KoronaScriptReponseUtil.exit();
			}

			return KoronaScriptReponseUtil.showWarning("Declined", cardCredit.getMessage()
				.getValue()) +
				KoronaScriptReponseUtil.exit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return KoronaScriptReponseUtil.showWarning("Unknown Error", e.getMessage()) +
				KoronaScriptReponseUtil.exit();
		}


	}


	@RequestMapping(value = "/charge", method = RequestMethod.GET, produces = "text/html")
	@ResponseBody
	public String chargeCard(@RequestParam(value = "account", required = false) String account,
		@RequestParam(value = "amount", required = false) BigDecimal amount)
	{
		System.out.println("charge account " + account + " $" + amount);
		CardRedeemRequest req = objFactory.createCardRedeemRequest();
		req.setAllowDuplicate(Boolean.TRUE);
		req.setAmount(amount);
		req.setCardNumber(objFactory.createCardCreditRequestCardNumber(account));
		try
		{
			CardResponse cardCredit = service.cardRedeem(settings.getDeviceKey(),
				settings.getStoreCode(), settings.getDeviceCode(), req);

			System.out.println("" + cardCredit.getStatus() + " - " +
				cardCredit.getMessage().getValue());

			if (cardCredit.getStatus() == CardRequestStatus.APPROVED)
			{
				return KoronaScriptReponseUtil.addPayment(
					settings.getKoronaGiftCardPaymentMethodNumber(), amount) +
					KoronaScriptReponseUtil.showMessage("New Balance",
						cardCredit.getAvailableBalance()
							.setScale(2, RoundingMode.HALF_UP)
							.toString()) +
					KoronaScriptReponseUtil.setInputLineValue("") +
					KoronaScriptReponseUtil.exit();
			}

			return KoronaScriptReponseUtil.showWarning("Declined", cardCredit.getMessage()
				.getValue()) +
				KoronaScriptReponseUtil.exit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return KoronaScriptReponseUtil.showWarning("Unknown Error", e.getMessage()) +
				KoronaScriptReponseUtil.exit();
		}
	}

	@RequestMapping(value = "/check", method = RequestMethod.GET, produces = "text/html")
	@ResponseBody
	public String checkCard(@RequestParam(value = "account", required = false) String account)
	{
		System.out.println("check account " + account);
		CardBalanceRequest req = objFactory.createCardBalanceRequest();
		req.setCardNumber(objFactory.createCardCreditRequestCardNumber(account));
		try
		{
			CardResponse cardCredit = service.getCardBalance(settings.getDeviceKey(),
				settings.getStoreCode(), settings.getDeviceCode(), req);

			System.out.println("" + cardCredit.getStatus() + " - " +
				cardCredit.getMessage().getValue());

			return KoronaScriptReponseUtil.showMessage("Balance", cardCredit.getAvailableBalance()
				.setScale(2, RoundingMode.HALF_UP)
				.toString()) +
				KoronaScriptReponseUtil.exit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return KoronaScriptReponseUtil.showWarning("Unknown Error", e.getMessage()) +
				KoronaScriptReponseUtil.exit();
		}
	}
}
