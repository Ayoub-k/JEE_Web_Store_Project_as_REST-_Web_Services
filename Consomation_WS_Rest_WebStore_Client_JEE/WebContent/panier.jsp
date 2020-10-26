<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.Beans.*"%>
<%
	Client client = (Client) session.getAttribute("connectedUser");
	request.setAttribute("client", client);
	String id = request.getParameter("id");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accueil</title>
<link rel='stylesheet' type='text/css' href='FichierCss/panie.css'>
<style type="text/css">
a {
	text-decoration: none;
}

.foter {
	background-color: #282828;
	background-position: center center;
	text-shadow: rgb(31, 23, 22) 20px 35px 50px;
	height: 150px;
}

div ul {
	display: inline-block;
}
</style>
<script src="scripts/javascript.js"></script>
</head>
<body>
	<div>
		<img src="static/images/background/6.jpg" alt="" id="slide"
			style="width: 100%; height: 550px; border-radius: 10px; background-position: center center; background-size: cover; position: static;">
	</div>
	<script>
		var imgs = [ "static/images/background/1.jpg",
				"static/images/background/2.jpg",
				"static/images/background/3.jpg",
				"static/images/background/4.jpg",
				"static/images/background/5.jpg",
				"static/images/background/6.jpg" ]
		var k = 0
		function slides() {
			document.getElementById("slide").src = imgs[k];
			k++;
			if (k > 5)
				k = 0
		}
		setInterval(slides, 2000)
	</script>
	<h1 style="text-align: center;" style="color:brouwn;">votre panier
		:</h1>
		
		




<c:if test="${!empty listeslignePanier }">


<table border="1" align="center" width="100%"
		style="background-color: #C0C0C0;">
	<tr>
			<td>Id</td>
			<td>Nom</td>
			<td>Prix</td>
	</tr>
<c:forEach items="${listeslignePanier}" var="i">	
	<tr>
	<th>${i.id}</th>
	<th>${i.nom_livre}</th>
	<th>${i.prix_livre}</th>
	</tr>
</c:forEach>
</table>

<p>Prix_totale: ${Prix_totale}</p>



	<div class="divbtn">
		<form method='get' action="ServletCommande">
			<input type="submit" name="btne" value="Confirmer" class="btn" />
		</form>
	</div>

</c:if>

<h1>${!empty listeslignePanier?" ":"votre panier est vide " }</h1>





	<footer>
		<div class="foter">
			<p style="color: white; padding-top: 0.3cm;">User: ${client.nom}
				${client.prenom}</p>
			<p style="color: white;">Email: ${client.email}</p>
			<p style="color: white;">
				Panier: <a href="/Con_WS_Rest_WebStore/ServletAfficherAllLignePanier"
					style="color: aqua">Panier</a>
			</p>
			<p style="color: white;">
				Deconnection: <a href="/Con_WS_Rest_WebStore/Deconnexion"
					style="color: aqua">Deconnecxion</a>
			</p>
		</div>
	</footer>

</body>
</html>