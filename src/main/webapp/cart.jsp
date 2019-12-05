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
		CustomerDAO customerDAO = daoFactory.getCustomerDAO();
		PurchaseDAO purchaseDAO = daoFactory.getPurchaseDAO();
		ProductDAO productDAO = daoFactory.getProductDAO();
		ProducerDAO producerDAO = daoFactory.getProducerDAO();
		Set<Product> cart=(Set<Product>)request.getAttribute("cart");
        List<Product> products= productDAO.getAllProducts();
		String customerName=(String) session.getAttribute("customerName");

	%>


	<h1>Acquista</h1>

    <div id="left" style="float: left;width: 100%; border-right: 1px solid grey; border-right: 1px solid grey;  border-top: 1px solid grey;  border-left: 1px solid grey;  border-bottom: 1px solid grey ">
			
			<p>Seleziona un prodotto dal catalogo:</p>
			<table class="formdata" style="width: 100%">
					<tr>
						<th style="width: 18%">Descrizione</th>
						<th style="width: 18%">Codice</th>
                        <th style="width: 18%">Produttore</th>
                        <th style="width: 18%">Prezzo</th>
						<th style="width: 18%">Quantita</th>
						<th style="width: 10%"></th>
					</tr>
					<% 
					for( Product product : products ){  
						
							if (cart == null || (!cart.contains(product) && product.getQuantity() !=0)){				
					%> 
						<form action="cartServlet" method="post">
							<tr>
								<td style="width: 18%;text-align : center"><%= product.getName() %></td>
                                <td style="width: 18%;text-align : center"><%= product.getProductNumber() %></td>
								<td style="width: 18%;text-align : center"><%= product.getProducer().getName() %> </td>
								<td style="width: 18%;text-align : center"><%= product.getPrice() %></td>
								<td style="width: 18%;text-align : center"><input type="text" name="quantity" placeholder="<%= product.getQuantity() %>" value="<%= product.getQuantity() %>" /></td>
								<td style="width: 10%;text-align : center">
									<input type="hidden" name="operation" value="insertProductToCart"/>
									<input type="hidden" name="productId" value="<%= product.getId() %>"/>
									<input type="submit" name="submit" value="aggiungi"/>
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
	
	 <div id="left" style="float: left;width: 100%; border-right: 1px solid grey; border-right: 1px solid grey;  border-top: 1px solid grey;  border-left: 1px solid grey;  border-bottom: 1px solid grey ">
		
			<p>Carello:</p>
			<table class="formdata" style="width: 100%">
					<tr>
						<th style="width: 20%">Descrizione</th>
						<th style="width: 20%">Codice</th>
                        <th style="width: 20%">Produttore</th>
                        <th style="width: 20%">Prezzo</th>
						<th style="width: 20%"></th>
					</tr>
					<% 
					if (cart != null){
						for( Product product : cart ){  
					%> 
						<form action="cartServlet" method="post">
						<tr>
							<td style="width: 20%;text-align : center"><%= product.getName() %></td>
                                <td style="width: 20%;text-align : center"><%= product.getProductNumber() %></td>
								<td style="width: 20%;text-align : center"><%= product.getProducer().getName() %> </td>
								<td style="width: 20%;text-align : center"><%= product.getPrice() %></td>
								<td style="width: 20%;text-align : center">
									<input type="hidden" name="operation" value="deleteProductFromCart"/>
									<input type="hidden" name="productId" value="<%= product.getId() %>"/>
									<input type="submit" name="submit" value="elimina"/>
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
					</tr>
				</table>			
				<br/>

					<div>
						<p>Checkout</p>
						<form action="cartServlet" method="post">
							Inserisci il tuo nome:
							<%
								if (customerName != null){
							%> 
							<input type="text" name="customerName" placeholder="<%= customerName %>" value="<%= customerName %>" />
							<%
								}else{
							%>
							<input type="text" name="customerName"/>
							<%}%>
							<input type="hidden" name="operation" value="persistCart"/>
							<input type="submit" name="submit" value="ordina"/>
						</form>
					</div>
		</div>

		<a href="/demo/purchases.jsp">I tuoi ordini</a>

	</body>

</html>