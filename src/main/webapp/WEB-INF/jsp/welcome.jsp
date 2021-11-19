<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->
<petclinic:layout pageName="home">

	<h1 style="text-align: center">7Islas</h1>



	<form:form modelAttribute="game" class="form-horizontal">
		<div class="container"
			style="display: flex; justify-content: space-around">
			<button class="btn btn-default" type="submit"
				onclick=" location.href='/games/create'">Crear partida</button>

			<button class="btn btn-default" type="submit"
				onclick=" location.href='/games/searchGame'">Buscar partidas</button>

			<button class="btn btn-default" type="submit"
				onclick=" location.href='/games'">Ver partidas disponibles</button>
		</div>
	</form:form>


</petclinic:layout>
