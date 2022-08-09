<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
        <%@ page import = "com.paypal.api.payments.PayerInfo" %>
    <%@ page import = "com.paypal.api.payments.Payment" %>
     <%@ page import = "com.paypal.api.payments.ShippingAddress" %>
      <%@ page import = "com.paypal.api.payments.Transaction" %>
   <%@ page import = "com.paypal.api.payments.CartBase" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment Receipt</title>
<style type="text/css">
    table { border: 0; }
    table td { padding: 5px; }
</style>
</head>
<body>

<%

String paymentId = request.getParameter("paymentId");
String payerId = request.getParameter("PayerID");

HttpSession ss= request.getSession();


PayerInfo payer = (PayerInfo)request.getAttribute("payer") ;
Transaction transaction = (Transaction)request.getAttribute("transaction");
ShippingAddress shippingAddress = (ShippingAddress)request.getAttribute("shippingAddress");


%>


<div align="center">
    <h1>Payment Done. Thank you for purchasing our products</h1>
    <br/>
    <h2>Receipt Details:</h2>
    <table>
        <tr>
            <td><b>Merchant:</b></td>
            <td>InHandMedi.</td>
        </tr>
        <tr>
            <td><b>Payer:</b></td>
            <td><%=payer.getFirstName() %>  <%=payer.getLastName() %>  </td>      
        </tr>
        <tr>
            <td><b>Description:</b></td>
            <td><%=transaction.getDescription()%></td>
        </tr>
        <tr>
            <td><b>Subtotal:</b></td>
            <td><%=transaction.getAmount().getDetails().getSubtotal() %> USD</td>
        </tr>
        <tr>
            <td><b>Shipping:</b></td>
            <td>$<%=transaction.getAmount().getDetails().getShipping() %> USD</td>
        </tr>
        <tr>
            <td><b>Tax:</b></td>
            <td><%=transaction.getAmount().getDetails().getTax()  %>USD</td>
        </tr>
        <tr>
            <td><b>Total:</b></td>
            <td><%=transaction.getAmount().getTotal() %>  USD</td>
        </tr>                    
    </table>
</div>
</body>
</html>