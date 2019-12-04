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
		
		Set<Product> cart=(Set<Product>)request.getAttribute("cart");
		DAOFactory daoFactory = DAOFactory.getDAOFactory( application.getInitParameter("dao") );
		CustomerDAO customerDAO = daoFactory.getCustomerDAO();
		PurchaseDAO purchaseDAO = daoFactory.getPurchaseDAO();
		ProductDAO productDAO = daoFactory.getProductDAO();
		ProducerDAO producerDAO = daoFactory.getProducerDAO();
		
        List<Product> products= productDAO.getAllProducts();

	%>


	<h1>Acquista</h1>

    <div id="left" style="float: left; width: 100%; border-right: 1px solid grey; border-right: 1px solid grey;  border-top: 1px solid grey;  border-left: 1px solid grey;  border-bottom: 1px solid grey ">
			
			<p>Seleziona un prodotto dal catalogo:</p>
			<table class="formdata">
					<tr>
						<th style="width: 25%">Descrizione</th>
						<th style="width: 25%">Codice</th>
                        <th style="width: 25%">Produttore</th>
                        <th style="width: 25%"></th>
					</tr>
					<% 
					for( Product product : products ){  
						if (product.getPurchase()==null && (cart == null || !cart.contains(product))){							
					%> 
						<form action="cartServlet" method="post">
							<tr>
								<td style="width: 25%;text-align : center"><%= product.getName() %></td>
                                <td style="width: 25%;text-align : center"><%= product.getProductNumber() %></td>
								<td style="width: 25%;text-align : center"><%= product.getProducer().getName() %> </td>
								<td style="width: 25%;text-align : center">
									<input type="hidden" name="operation" value="insertProductToCart"/>
									<input type="hidden" name="productId" value="<%= product.getId() %>"/>
									<input type="submit" name="aggiungi" value="add to cart"/>
								</td>
							</tr>
						</form>
					<% 
							}
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
	
	 <div id="left" style="float: left; width: 100%; border-right: 1px solid grey; border-right: 1px solid grey;  border-top: 1px solid grey;  border-left: 1px solid grey;  border-bottom: 1px solid grey ">
		
			<p>Carello:</p>
			<table class="formdata" style="width: 100%">
					<tr>
						<th style="width: 25%">Descrizione</th>
						<th style="width: 25%">Codice</th>
                        <th style="width: 25%">Produttore</th>
                        <th style="width: 25%">Prezzo</th>
					</tr>
					<% 
					if (cart != null){
						for( Product p : cart ){  
					%> 
						<tr>
							<td style="width: 25%;text-align : center"><%= p.getName() %></td>
							<td style="width: 25%;text-align : center"><%= p.getProductNumber() %> </td>
							<td style="width: 25%;text-align : center"><%= p.getProducer().getName() %></td>
							<td style="width: 25%;text-align : center"><%= 12 %></td>
						</tr>
					<% 
						} 
					}
					%>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>			
				<br/>

					<div>
						<p>Checkout</p>
						<form action="cartServlet" method="post">
							Inserisci il tuo nome: <input type="text" name="customerName" />
							<input type="hidden" name="operation" value="persistCart"/>
							<input type="submit" name="ordina" value="submit"/>
						</form>
					</div>
		</div>

		<a href="/demo/purchases.jsp">I tuoi ordini</a>

	</body>

</html>