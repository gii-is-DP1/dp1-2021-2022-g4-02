<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="payCard">

    <h1 align="center">�Qu� modo de juego quieres?</h1>
    
	<form:form modelAttribute="game" class="form">
		<input type="hidden" name="id" value="${game.id}"/>
	    <input id="gameMode" type="radio" name="gameMode" value="0">Modo Normal</input>
	    <input id="gameMode" type="radio" name="gameMode" value="1">Modo Secundario</input>
    	<button type="submit" class="btn btn-primary">Continuar</button>
	</form:form>
	<br><br><br><br>
	<h2 >El Modo de Juego Normal calcula las puntuaciones como viene en las reglas: fij�ndose en el n�mero de sets de tesoros distintos que se tiene.<br></br>
	El Modo de Juego Secundario calcula calcula las puntuaciones fij�ndose solo en el set con mayor n�mero de tesoros distintos.<br></br>
	En ambos casos, se desmpata con el n�mero de doblones.</h2>
   
    
    

</petclinic:layout>