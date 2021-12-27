<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">
	<div class="text-center">
		<br><br><br><br><br>
		<spring:url value="/resources/images/pirata-error.png"
			var="pirateImage" />
		<img src="${pirateImage}" width=250 />

		<h1><br><br>
			Aaargg!! ${message}
		</h1>

		<form>
		<input type="button" class="btn btn-primary" value="Volver" onclick="history.back()">
		</form>
	</div>
</petclinic:layout>