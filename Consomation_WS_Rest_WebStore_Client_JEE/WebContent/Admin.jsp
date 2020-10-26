<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<link rel='stylesheet' type='text/css' href='FichierCss/panie.css'>
<link rel="stylesheet" type="text/css" href="static/css/mystyle.css" />
<link rel="stylesheet" type="text/css" href="static/css/bootstrap.min.css" />

<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.foter {
	background-color: #282828;
	background-position: center center;
	text-shadow: rgb(31, 23, 22) 20px 35px 50px;
	height: 150px;
	}
	
	header1 {
	/* margin-top: 0.3cm; */
	border: 1px solid rgba(8, 8, 8, 0.493);
	background-color: #282828;
	/*position: fixed;*/
	border-radius: 15px;
	height: 60px;
	margin-bottom: 20px;
/*	top: 0;*/
	box-shadow: 2px 2px 2px rgb(27, 26, 26);
	width: 100%;
}

</style>
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


<nav class="navbar">
		<header>
			<ul class="ulheader">
				<li><a href="#welcomme"> Welcome </a></li>
				<li><a href="#books"> Books </a></li>
				<li><a href="buy.jsp"> Buy </a></li>
				<li><a href="#contact"> Contact </a></li>
			</ul>
		</header>
	</nav>

	<div
		style="background-color: DarkCyan; width: 92%; margin-left: 60px; border-radius: 40px; margin-top:2cm; margin-bottom:2cm">
		<p style="text-align: center; font-size: 30px;">Welcome Admin</p>
	</div>


  
   	
		
	
  
	
	
	
	<div
		style="background-color: #D8DCFF; width: 92%; margin-left: 60px; border-radius: 40px;">
		<p style="text-align: center; font-size: 30px;">Clients</p>
	</div>
	
	<div align='center'>
		<table class="table table-bordered table-Primary" style="margin-top:1cm; width: 60%;text-align:center;">
		 <thead>
    		<tr>
    			<th scope="col">First_name</th>
      			<th scope="col">Last_Name</th>
      			<th scope="col">Phone</th>
      			<th scope="col">Email</th>
      			<th scope="col">Supprimer</th>
    		</tr>
  		</thead>
  		
  		<c:forEach items="${listeClient}" var="Client" varStatus="vs">
			<tr>
				<td>	<c:out value="${Client.nom }"> </c:out></td>
				<td>	<c:out value="${Client.prenom }"> </c:out></td>
				<td>	<c:out value="${Client.phone }"> </c:out></td>
				<td>	<c:out value="${Client.email }"></c:out> </td>
				<td>	<a class="link-info" href="/Consommation_Web_Store/ServletDeleteAll?id=${Client.id}&type=Client">Supprimer</a>	 </td>
			</tr>
		</c:forEach>
  		
  		
  	</table>
	</div>
	
	
	<div
		style="background-color: #D8DCFF; width: 92%; margin-left: 60px; border-radius: 40px;">
		<p style="text-align: center; font-size: 30px;">Livres</p>
	</div>
	
	<div align='center'>
		<table class="table table-bordered table-Primary" style="margin-top:1cm; width: 60%;text-align:center;">
		 <thead>
    		<tr>
    			<th scope="col">Name</th>
      			<th scope="col">NPage</th>
      			<th scope="col">Langue</th>
      			<th scope="col">Prix</th>
      			<th scope="col">Annee</th>
      			<th scope="col">Type</th>
      			<th scope="col">auteur</th>
      			<th scope="col">Supprimer</th>
    		</tr>
  		</thead>
  		
  		<c:forEach items="${listeLivre}" var="livre" varStatus="vs">
			<tr>
				<td>	<c:out value="${livre.nom }"> </c:out></td>
				<td>	<c:out value="${livre.nbrpage }"> </c:out></td>
				<td>	<c:out value="${livre.langue }"> </c:out></td>
				<td>	<c:out value="${livre.prix }"></c:out> </td>
				<td>	<c:out value="${livre.annee }"> </c:out></td>
				<td>	<c:out value="${livre.nomcatalogue }"> </c:out></td>
				<td>	<c:out value="${livre.autheur }"></c:out> </td>
				<td>	<a class="link-info" href="/Consommation_Web_Store/ServletDeleteAll?id=${livre.id}&type=livre">Supprimer</a>	 </td>
			</tr>
		</c:forEach>
  		
  		
  	</table>
	</div>
	
		
	
	
	<div style="background-color: #D8DCFF; width: 92%; margin-left: 60px; border-radius: 40px;">
		<p style="text-align: center; font-size: 30px;">Ajouter Livre</p>

	</div>
	<div style="text-align: center;">
		<form action="catalouelivre" method="post">
			<input type="text" name="nom" placeholder="Nom"  required="required"/><br>
			<input type="text" name="nbrpage" placeholder="Nbrpage" required="required"/><br>
			<input type="text" name="langue" placeholder="Langue" required="required"/><br>
			<input type="text" name="prix" placeholder="Prix" required="required"/><br>
			<input type="text" name="auteur" placeholder="Auteur" required="required"/><br>
			<input type="text" name="annee" placeholder="Annee" required="required"/><br>
			<input type="file" name="path" accept="image/png, image/jpeg" required="required"/><br>
		 <select name="Choixcatalogue" required="required">
			  <option value="Scince" > 		Science</option> 
			  <option value="Anime">      	Anime</option>
			  <option value="Programming">	Programming</option>
			  <option value="Economie">		Economie</option>
			  <option value="History ">		History </option>
			  <option value="Programming ">	Programming </option>
			  <option value="art">			art</option>
			  <option value="Novels">		Novels</option>
			  <option value="Religion">		Religion </option>
		</select> <br>
			<input type="submit" value="send" /><br>
		</form>
		
	</div>
	





<footer>
		<div class="foter">
			<p style="color: white; padding-top: 0.3cm;">Admin: ${connectedUser.nom}
				${connectedUser.prenom}</p>
			<p style="color: white;">Email: ${connectedUser.email}</p>
			<!-- <p style="color: white;">
				Panier: <a href="/Consommation_Web_Store/les_livrespanier(1).jsp"
					style="color: aqua">Panier</a>
			</p> -->
			<p style="color: white;">
				Deconnection: <a href="/Consommation_Web_Store/Deconnexion"
					style="color: aqua">Deconnecxion</a>
			</p>
		</div>
</footer>

</body>
</html>