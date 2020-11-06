package backend.realestate.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import backend.realestate.paypal.config.PaypalPaymentIntent;
import backend.realestate.paypal.config.PaypalPaymentMethod;
import backend.realestate.paypal.service.PaypalService;
import backend.realestate.paypal.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PaymentController {
	
	public static final String URL_PAYPAL_SUCCESS = "pay/success";
	public static final String URL_PAYPAL_CANCEL = "pay/cancel";
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PaypalService paypalService;

	@PostMapping("/pay")
	 public ResponseEntity<String> pay( @RequestBody  double price){
//		String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
//		String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
		try {
			Payment payment = paypalService.createPayment(
					price, 
					"USD", 
					PaypalPaymentMethod.paypal, 
					PaypalPaymentIntent.sale,
					"payment description",
					"localhost8080/pay/cancel",
					"localhost8080/pay/success");
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					return new ResponseEntity<>("redirect:" + links.getHref(), HttpStatus.OK);
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}

		return new ResponseEntity<>("redirect:/", HttpStatus.OK);
	}

	@GetMapping(URL_PAYPAL_CANCEL)
	public String cancelPay(){
		return "cancel";
	}

	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){
				return "success";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}
	
}
