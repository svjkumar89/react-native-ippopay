package com.axebelk.ippopay;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import android.widget.Toast;
import com.ippopay.core.IppoPayPay;
import com.ippopay.core.IppoPayListener;
import com.ippopay.models.OrderData;
import com.ippopay.models.Customer;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import javax.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;

public class IppopayModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    public IppopayModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Ippopay";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }

    @ReactMethod
    public void init(String publicKey) {
        IppoPayPay.init(getReactApplicationContext(), publicKey);
        // "pk_test_sHIs7WDKKitk"
    }


    @ReactMethod
    public void makePayment(ReadableMap options) {
        
        OrderData orderData = new OrderData(); // Ippopay Order Data Model Class Instance
        //orderData.setCustomColor("#780991"); // make payment page loading color as app color.
        //orderData.setFont(ResourcesCompat.getFont(this,R.font.poppins_medium)); // make payment page text font as app font.
        orderData.setOrderDescription(options.getString("DESCRIPTION"));  // any description.
        orderData.setOrderId(options.getString("ORDER_ID")); // unique order id.
        Customer customer = new Customer();
        customer.setName(options.getString("CUSTOMER_NAME"));
        customer.setEmail(options.getString("EMAIL"));
        customer.setMobile(options.getString("MOBILE_NO"));
        orderData.setCustomer(customer);
        orderData.setOrderAmount(options.getDouble("TXN_AMOUNT"));
        orderData.setCurrencyCode("INR");
        IppoPayPay.makePayment(orderData);

        IppoPayPay.setPaymentListener(new IppoPayListener() {
            @Override
            public void onTransactionSuccess(String transactionId) {
                WritableMap params = new WritableNativeMap();
                params.putString("status", "SUCCESS");
                params.putString("transactionId", transactionId);
                sendEvent( "IppoPayResponse", params);
            }

            @Override
            public void onTransactionFailure(String error, String transactionId) {
                WritableMap params = new WritableNativeMap();
                params.putString("status", "FAILURE");
                params.putString("transactionId", transactionId);
                params.putString("error", error);
                sendEvent( "IppoPayResponse", params);
            }

            @Override
            public void onTransactionCancelled() {
                WritableMap params = new WritableNativeMap();
                params.putString("status", "CANCELLED");
                sendEvent( "IppoPayResponse", params);
            }
        });

    }

   private void sendEvent(String eventName, @Nullable WritableMap params) {
    reactContext
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit(eventName, params);
  }

}
