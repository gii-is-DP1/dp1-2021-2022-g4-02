<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->
<petclinic:layout pageName="home">

	<p style="text-align: center;font-size:350%; font-weight:bold; margin-top:2%;">ˇBienvenido a 7Islas!</p>
	
	<!--<spring:url value="/resources/images/barco-pantalla-principal.png"
			var="pirateShipImage" />
	<img src="${pirateShipImage}" style="width:100%; border-radius: 10px;"/>-->
	
	<div class="row">
	<div class="col-md-6">
	<a href="/games/create">
		<spring:url value="/resources/images/crear-partida.png"
				var="crearPartidaImg" />
		<img src="${crearPartidaImg}" style="width:100%; border-radius: 20%; margin-right:5%; margin-top:10%"/>
	</a>
	</div>
	<div class="col-md-6">
	<a href="/games/searchGame">
		<spring:url value="/resources/images/buscar-partida.png"
			var="buscarPartidaImg" />
		<img src="${buscarPartidaImg}" style="width:100%; border-radius: 20%; margin-left: 5%; margin-top:10%"/>
	</a>
	</div>
	</div>




</petclinic:layout>
