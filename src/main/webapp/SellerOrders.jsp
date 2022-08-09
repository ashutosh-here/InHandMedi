<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Orders</title>
<link rel="stylesheet" href="css/Orders.css">
</head>
<body>
<div class="main">
	<div class="topbar1"></div>
	<div class="topbar2">
		<div class="container1">
			<div class="logout-btn">
				<a href="Logout.jsp">Logout</a>
			</div>
		</div>
	</div>
	<div class="header">
		<div class="container2">
			<div class="navbar">
				<a href="SellerHomepage.jsp">HOME</a>
				<a href="AddProduct.html">ADD</a>
				<a href="AddInventory.jsp">RESTOCK</a>
				<a href="SellerOrders.jsp">ORDERS</a>
			</div>
		</div>
	</div>
	</div>
	<div class="active">
	<%@ page import="java.sql.*" %>
	<%@ page import="javax.sql.*" %>
	<%
	HttpSession httpSession = request.getSession();
    String guid=(String)httpSession.getAttribute("currentuser");
    %>
    <div class="filler"></div>
    <%
    int flag=0;
	ResultSet rs=null;  ResultSet rs1=null;PreparedStatement ps=null;
	CallableStatement cs=null;
	java.sql.Connection conn=null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
				conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/drugdatabase","ashu","ashu14mysql");
		cs = conn.prepareCall("call getsellerorders(?)");
		cs.setString(1, guid);
		rs = cs.executeQuery();
		
		
		String query="select fname,phno from customer where uid=?";
		
		%><div class="filler2"></div>
		<table class="tables">
			<tr>
    			<th>Order ID</th>
    			<th>Product ID</th>
    			<th>Price</th>
    			<th>Quantity</th>
    			<th>CUSTOMER Name & Contact</th>
    			<th>Order Date and Time</th>
  			</tr>
		<%while(rs.next()) 
		{
		%>
  		
  			<tr>
    			<td><%=rs.getInt("oid") %></td>
    			<td><%=rs.getString("pid") %></td>
    			<td><%=rs.getInt("price") %></td>
    			<td><%=rs.getInt("quantity") %></td>
    	<% 		
    	        ps=conn.prepareStatement(query);

    			ps.setString(1,rs.getString("uid"));
		        rs1=ps.executeQuery();
		     if(rs1.next())
		    {
		    	 %>
		    	 <td><%=rs1.getString("fname") %> , <%=rs1.getString("phno") %></td>
		     
		        <%  }   %>
		               
    			
    		
    			<td><%=rs.getTimestamp("orderdatetime") %>
  			</tr>
  			
		<%
	}
		%>
		</table>
		</div>
		<% 
	}
	catch(Exception e)
	{
		out.println("error: "+e);
	}
	finally {
  	  	try { if (rs != null) rs.close(); } catch (Exception e) {};
    	try { if (cs != null) cs.close(); } catch (Exception e) {};
   		try { if (conn != null) conn.close(); } catch (Exception e) {};
}
	%>
</body>
</html>
