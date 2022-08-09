

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.paypal.base.rest.PayPalRESTException;

/**
 * Servlet implementation class AuthorizePaymentServle
 */
public class AuthorizePaymentServle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizePaymentServle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 HttpSession ss=request.getSession();
		
	
		String qr=String.valueOf(ss.getAttribute("pqt"));
		String pid=(String)ss.getAttribute("pid");
		String guid=(String)ss.getAttribute("currentuser");
		
		
		
		
		
		
		
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		
		String pname="";
		int price=0;
//		String query1="select P.pid,O.sid,P.price from inventory o,product p where p.pid=? and p.pid=o.pid";
		String query1="select p.pname,p.price from product p where p.pid=?";

		try{
			Class.forName("com.mysql.jdbc.Driver");
					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/drugdatabase","ashu","ashu14mysql");
			ps=conn.prepareStatement(query1);
			ps.setString(1,pid);
			rs=ps.executeQuery();
			if(rs.next())
			{
				pname=rs.getString("pname");
				price=rs.getInt("price");
				
//			 response.sendRedirect("authorize_payment");
			}
		}
		catch(Exception E)
		{
			System.out.println(E);
		}
		finally {
			  	try { if (rs != null) rs.close(); } catch (Exception e) {};
			  	try { if (ps != null) ps.close(); } catch (Exception e) {};
				try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		
		
		
		
		
		  
		   String pNames=pname ;
           int pTotal= price*(Integer.parseInt(qr));
            String subtotal =(Integer.toString(pTotal));
       String shipping =      "50";//                    request.getParameter("shipping");
       String tax =           "4";//                       request.getParameter("tax");

     
     String total= Double.toString(  Double.parseDouble(subtotal) + 54);
     
     
       OrderDetail orderDetail = new OrderDetail(pNames, subtotal, shipping, tax, total);
  
		
		
 
        try {
            PaymentServices paymentServices = new PaymentServices();
            String approvalLink = paymentServices.authorizePayment(orderDetail);
 
            response.sendRedirect(approvalLink);
             
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
