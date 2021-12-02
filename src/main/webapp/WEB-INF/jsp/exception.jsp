<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="error">
	<div class="text-center">
		<br><br><br><br><br>
		<spring:url value="/resources/images/pirata-error.png"
			var="pirateImage" />
		<img src="${pirateImage}" width=300 />

		<h1><br><br>
			Aaargg!! Ha ocurrido un error...
		</h1>

		<p>${exception.message}</p>
	</div>
</petclinic:layout>
