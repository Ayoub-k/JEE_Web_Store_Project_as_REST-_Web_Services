

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.Beans.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.images {
	/* margin-top:80px; */
	width: 80%;
	height: 80%;
	border-radius: 10px;
	position: static;
	/* transform: scale(1,1); */
}

table {
	margin: auto;
}

.ulheader {
	text-align: center;
	padding-top: 3px;
}

ul a {
	color: white;
	padding-left: 30px;
	padding-right: 30px;
	border: 1px solid gray;
	border-radius: 10px;
	background-color: gray;
	text-decoration: none;
	font-weight: bold;
}

li {
	display: inline-block;
}

header {
	/* margin-top: 0.3cm; */
	border: 1px solid rgba(8, 8, 8, 0.493);
	background-color: #282828;
	position: fixed;
	border-radius: 15px;
	height: 60px;
	margin-bottom: 20px;
	top: 0;
	box-shadow: 2px 2px 2px rgb(27, 26, 26);
	width: 100%;
}

.foter {
	background-color: #282828;
	background-position: center center;
	text-shadow: rgb(31, 23, 22) 20px 35px 50px;
	height: 150px;
}
</style>
</head>
<body>

	<%
	
	Client client=(Client)session.getAttribute("connectedUser");
	
    request.setAttribute("client", client);
    
    String id=request.getParameter("id");
    
    request.setAttribute("id",id);
    
   %>
<nav class="navbar">
		<header>
			<ul class="ulheader">
				<li><a href="/Con_WS_Rest_WebStore/Servletlivres?idcatalogue=Science">Science </a></li>
				<li><a href="Servletlivres?idcatalogue=History"> History </a></li>
				<li><a href="/Con_WS_Rest_WebStore/Servletlivres?idcatalogue=Programming">Programming </a></li>
				<li><a href="Servletlivres?idcatalogue=art"> Art </a></li>
				<li><a href="Servletlivres?idcatalogue=Anime"> Anime </a></li>
				<li><a href="Servletlivres?idcatalogue=Novels"> Novels </a></li>
				<li><a href="Servletlivres?idcatalogue=Economie"> Economie
				</a></li>
				<li><a href="Servletlivres?idcatalogue=Various"> Various </a></li>
				<li><a href="Servletlivres?idcatalogue=religion ">Religion </a></li>
			</ul>
		</header>
	</nav>

	<div>
		<img src="static/images/background/6.jpg" alt="" id="slide"
			style="width: 100%; height: 530px; border-radius: 10px; background-position: center center; background-size: cover; position: static;">
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
		setInterval(slides, 3000)
	</script>


	<div id="welcomme"
		style="background-color: DarkCyan; width: 92%; margin-left: 60px; border-radius: 40px; margin-top: 1cm;">
		<p style="text-align: center; font-size: 30px;">${param.idcatalogue}</p>
	</div>
   
		
<!--  -->





<table>

<c:forEach items="${listes_livre}" var="line" varStatus="vs">	
	<tr>
	<th>
    <a href="ServletAfficherlivre?id=${line.id}"><img src="static/images/science/${line.path}"/></a>	<c:if test="${vs.count==4}"><c:out value="${vs.count}"/></c:if> 
	</th>
	</tr>
</c:forEach>

</table>





	<footer>
		<div class="foter">
			<p style="color: white; padding-top: 0.3cm;">User: ${!empty client.nom?client.nom:"Nom"}
				${!empty client.prenom?client.nom:"Prenom"}</p>
			<p style="color: white;">Email: ${!empty client.email?client.email: "example@ETU.AR" }</p>
			<p style="color: white;">
				Panier: <a href="/Con_WS_Rest_WebStore/ServletAfficherAllLignePanier" style="color: aqua">Panier</a>
			</p>
			<p style="color: white;">
				Deconnection: <a href="/Con_WS_Rest_WebStore/Deconnexion"
					style="color: aqua">Deconnecxion</a>
			</p>
		</div>
	</footer>
</body>
</html>