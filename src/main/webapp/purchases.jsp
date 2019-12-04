<%@ page session ="true"%>
<%@ page import="java.util.*" %>
<%@ page import="it.distributedsystems.model.dao.*" %>


<html>

	<head>
		<title>HOMEPAGE DISTRIBUTED SYSTEM EJB</title>
	
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="Mon, 01 Jan 1996 23:59:59 GMT"/>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<meta name="Author" content="you">

		<link rel="StyleSheet" href="styles/default.css" type="text/css" media="all" />
	
	</head>
	
	<body>

	
	
	<%
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory( application.getInitParameter("dao") );
		PurchaseDAO purchaseDAO = daoFactory.getPurchaseDAO();
        CustomerDAO customerDAO = daoFactory.getCustomerDAO();
        String customerName=(String) session.getAttribute("customerName");
        Customer customer=new Customer();
        List<Purchase> purchases=new ArrayList<Purchase>();
        if (customerName != null){
            customer=customerDAO.findCustomerByName(customerName);
            purchases=purchaseDAO.findAllPurchasesByCustomer(customer);
        }       

	%>


	<h1>Elenco ordini effettuati</h1>

    <%
        for (Purchase purchase : purchases){
    %>
    <div id="left" style="float: left; width: 100%; border-right: 1px solid grey; border-right: 1px solid grey;  border-top: 1px solid grey;  border-left: 1px solid grey;  border-bottom: 1px solid grey ">
			
			<p>Ordine <%=purchase.getId()%></p>
			<table class="formdata">
					<tr>
						<th style="width: 25%">Descrizione</th>
						<th style="width: 25%">Codice</th>
                        <th style="width: 25%">Prezzo</th>
                        <th style="width: 25%"></th>
					</tr>
					<% 
					for( Product product : purchase.getProducts() ){  
												
					%> 
						
							<tr>
								<td style="width: 25%;text-align : center"><%= product.getName() %></td>
                                <td style="width: 25%;text-align : center"><%= product.getProductNumber() %></td>
								<td style="width: 25%;text-align : center"><%= product.getPrice() %> </td>
								<td style="width: 25%;text-align : center"></td>
							</tr>
						
					<% 
							
						}
					  %>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
		    </table>			
	</div>
	<%
        }
    %>
	 
	</body>

</html>