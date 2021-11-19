<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->
<petclinic:layout pageName="home">
    
    <h1 style="text-align: center">7Islas</h1>
    
    
        
        <form:form modelAttribute="game" class="form-horizontal">
           	<div style="text-align: left">
           	      <button class="btn btn-default" type="submit" onclick=" location.href='/games/create'">Crear Partida</button>
           	</div>
           	<div style="text-align: center">
           	      <button class="btn btn-default" type="submit" onclick=" location.href='/games/create'">Buscar partidas</button>
           	</div>
           	<div style="text-align: right">
           	      <button class="btn btn-default" type="submit" onclick=" location.href='/games'">Ver Partidas disponibles</button>
           	</div>
        </form:form>
        

</petclinic:layout>
