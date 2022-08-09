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
<title>Review</title>
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
    <h1>Please Review Before Paying</h1>
    <form action="execute_payment" method="post">
    <table>
        <tr>
            <td colspan="2"><b>Transaction Details:</b></td>
            <td>
                <input type="hidden" name="paymentId" value=<%= paymentId%> />
                <input type="hidden" name="PayerID" value=<%=payerId %> />
            </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><%=transaction.getDescription()%></td>
        </tr>
        <tr>
            <td>Subtotal:</td>
            <td><%=transaction.getAmount().getDetails().getSubtotal() %> USD</td>
        </tr>
        <tr>
            <td>Shipping:</td>
            <td><%=transaction.getAmount().getDetails().getShipping() %>  USD</td>
        </tr>
        <tr>
            <td>Tax:</td>
            <td><%=transaction.getAmount().getDetails().getTax()  %>  USD</td>
        </tr>
        <tr>
            <td>Total:</td>
            <td><%=transaction.getAmount().getTotal() %>  USD</td>
        </tr>
        <tr><td><br/></td></tr>
        <tr>
            <td colspan="2"><b>Payer Information:</b></td>
        </tr>
        <tr>
            <td>First Name:</td>
            <td><%=payer.getFirstName() %></td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td><%=payer.getLastName() %></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><%=payer.getEmail() %></td>
        </tr>
        <tr><td><br/></td></tr>
        <tr>
            <td colspan="2"><b>Shipping Address:</b></td>
        </tr>
        <tr>
            <td>Recipient Name:</td>
            <td><%=shippingAddress.getRecipientName() %></td>
        </tr>
        <tr>
            <td>Line 1:</td>
            <td><%=shippingAddress.getLine1()%></td>
        </tr>
        <tr>
            <td>City:</td>
            <td><%=shippingAddress.getCity() %></td>
        </tr>
        <tr>
            <td>State:</td>
            <td><%=shippingAddress.getState() %></td>
        </tr>
        <tr>
            <td>Country Code:</td>
            <td><%=shippingAddress.getCountryCode() %></td>
        </tr>
        <tr>
            <td>Postal Code:</td>
            <td><%=shippingAddress.getPostalCode()%></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Pay Now" />
            </td>
        </tr>    
    </table>
    </form>
</div>
</body>
</html>