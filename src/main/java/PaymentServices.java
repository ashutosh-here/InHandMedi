
 
import java.util.*;
 
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;
 
public class PaymentServices {
    private static final String CLIENT_ID = "Ab4EVKRcyXzFLKFfDFMh0kwqHp6hlZ-PNM47VKa0bT6_BchGGfGZgBvcexTGEnUNms3LW_Q0ysBaHyt5";
    private static final String CLIENT_SECRET = "EJ6C11w-7uzsoW4eHQHqCPfHfHeIJMymuiFYLe0Ly3mIwKYJjJRcyth0v7f36KbYaKWTuEBcvEZ5ayI0";
    private static final String MODE = "sandbox";
 
    public String authorizePayment(OrderDetail orderDetail)        
            throws PayPalRESTException {       
 
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(orderDetail);
         
        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");
 
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
 
        Payment approvedPayment = requestPayment.create(apiContext);
 
        return getApprovalLink(approvedPayment);
 
    }
     
   private Payer getPayerInformation() {
    Payer payer = new Payer();
    payer.setPaymentMethod("paypal");
     
    PayerInfo payerInfo = new PayerInfo();
    payerInfo.setFirstName("Ashutosh")
             .setLastName("Tripathi")
             .setEmail("ashutoshtripathi6937@gmail.com");
     
    payer.setPayerInfo(payerInfo);
     
    return payer;
}
     
   private RedirectUrls getRedirectURLs() {
    RedirectUrls redirectUrls = new RedirectUrls();
    redirectUrls.setCancelUrl("http://localhost:9494/Major/cancel.html");
    redirectUrls.setReturnUrl("http://localhost:9494/Major/review_payment");
     
    return redirectUrls;
}
     
   private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
    Details details = new Details();
    details.setShipping(orderDetail.getShipping());
    details.setSubtotal(orderDetail.getSubtotal());
    details.setTax(orderDetail.getTax());
 
    Amount amount = new Amount();
                                                          amount.setCurrency("USD");
    amount.setTotal(orderDetail.getTotal());
    amount.setDetails(details);
 
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription(orderDetail.getProductName());
     
    ItemList itemList = new ItemList();
    List<Item> items = new ArrayList();
     
    Item item = new Item();
                                                                                            item.setCurrency("USD");
    item.setName(orderDetail.getProductName());
    item.setPrice(orderDetail.getSubtotal());
    item.setTax(orderDetail.getTax());
    item.setQuantity("1");
     
    items.add(item);
    itemList.setItems(items);
    transaction.setItemList(itemList);
 
    List<Transaction> listTransaction = new ArrayList();
    listTransaction.add(transaction);  
     
    return listTransaction;
}
     
   private String getApprovalLink(Payment approvedPayment) {
    List<Links> links = approvedPayment.getLinks();
    String approvalLink = null;
     
    for (Links link : links) {
        if (link.getRel().equalsIgnoreCase("approval_url")) {
            approvalLink = link.getHref();
            break;
        }
    }      
     
    return approvalLink;
}
   public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
    APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
    return Payment.get(apiContext, paymentId);
}
   
   
   
   
   
   
   public Payment executePayment(String paymentId, String payerId)
        throws PayPalRESTException {
    PaymentExecution paymentExecution = new PaymentExecution();
    paymentExecution.setPayerId(payerId);
 
    Payment payment = new Payment().setId(paymentId);
 
    APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
 
    return payment.execute(apiContext, paymentExecution);
}
   
   
   
   
   
   
   
   
   
}