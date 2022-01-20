<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
	<jsp:body>
        <form:form modelAttribute="game"
			class="form-horizontal text-center">
			<br><br><br>
		  <h1 style="color: #0769C6">Buscar partida</h1>
          <br><br><br><br><br><br>
          <h2 style="color: #0769C6"> Introduzca el código de la partida a la que quiera unirse: </h2><br><br>
          <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-4">
         	<input type="text" placeholder="Código" class="form-control" name="code" /><br><br>
          	<div>
          		<button class="btn btn-primary" type="submit">Buscar Partida</button>
           	</div>
          </div>
          </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
