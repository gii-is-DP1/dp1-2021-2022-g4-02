<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <jsp:body>
    	<div class=text-center>
    	    <h1>Reglas del juego</h1><br>
    		<img width=85% src="<spring:url value="/resources/images/rules.png" htmlEscape="true" />" />
    	</div>
    </jsp:body>
</petclinic:layout>