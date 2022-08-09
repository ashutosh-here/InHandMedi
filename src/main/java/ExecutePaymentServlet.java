

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Servlet implementation class ExecutePaymentServlet
 */
public class ExecutePaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecutePaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		  
        String paymentId = request.getParameter("paymentId");
   String payerId = request.getParameter("PayerID");

   try {
       PaymentServices paymentServices = new PaymentServices();
       Payment payment = paymentServices.executePayment(paymentId, payerId);
        
       PayerInfo payerInfo = payment.getPayer().getPayerInfo();
       Transaction transaction = (Transaction) payment.getTransactions().get(0);
        
       request.setAttribute("payer", payerInfo);
       request.setAttribute("transaction", transaction);          

       request.getRequestDispatcher("receipt.jsp").forward(request, response);
        
   } catch (PayPalRESTException ex) {
       request.setAttribute("errorMessage", ex.getMessage());
       ex.printStackTrace();
       request.getRequestDispatcher("error.jsp").forward(request, response);
   }
       
       
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
