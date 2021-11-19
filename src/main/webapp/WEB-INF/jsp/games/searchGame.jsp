<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <jsp:body>
        <form:form modelAttribute="game" class="form-horizontal">
          <p> Introduzca el código de la partida a la que quiera unirse: </p>
         	<input type="text" name="code"/>
          	<div>
          		<button class="btn btn-default" type="submit">Buscar Partida</button>
           	</div>
        </form:form>
    </jsp:body>
</petclinic:layout>
