package co.aquario.mvp;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

public class Config {

	// PayPal app configuration
	public static final String PAYPAL_CLIENT_ID = "AT9HOtDlyH-6E8l81KBCfMFUi7r4tLnmhsTpZsFp7oOKtC5mZo-_abyLC-UWF7co4DP4OReFZ9Jp8LBh";
	public static final String PAYPAL_CLIENT_SECRET = "EJJ8q1_cqBbRJdpZrMNyl7K0sqlYwz3y1Md0mQ4IAnZRqByiGWxNn_a7WrxKuEtQH4DnRcuHiftbF1aW";

	public static final String PAYPAL_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
	public static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
	public static final String DEFAULT_CURRENCY = "USD";



}
