<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="payCard">
    <h1 align="center">Partida en curso</h1>

    <br><br><br><br>
    <div class="col-md-12">
	    <div class="col-md-6" align="left">
	    	<h2>Ronda Nº <c:out value="${game.currentRound}"></c:out><br> Turno de: <c:out value="${currentPlayerStatus.player.user.username}"></c:out></h2>
	    </div>
	    <div class="col-md-6" align="right"><br>
	    	<h2>Cógido de Partida: <c:out value="${game.code}"></c:out></h2>
	    </div>
    </div>
    <br><br><br><br><br><br>
    
    <div class="text-center">
		<c:if test="${status.numberOfCardsToPay==1}"> <h2>Te queda por pagar <c:out value="${status.numberOfCardsToPay}"> </c:out> carta.</h2></c:if>
	    <c:if test="${status.numberOfCardsToPay>1}"> <h2>Te quedan por pagar <c:out value="${status.numberOfCardsToPay}"> </c:out> cartas.</h2></c:if>
	    	 
	    <c:if test="${loggedUserId==playerUserId}">
	    		<h3>Elige la carta con la que quieres pagar:</h3>
	    		<div align="center" style="margin-bottom:3rem"><br> 	
	    			<form class="form-inline">
	        			
          				<c:forEach var="card" items="${status.cards}">
            				<input id="cardId" type="radio" name="cardToPay" value="${card.id}"><img width=4% src="<spring:url value="${card.urlCardImg}" htmlEscape="true" />" /></input>
          				</c:forEach>
	       				 
	        			<button type="button" class="btn btn-primary" onclick="window.location.href='/games/${code}/robIsland/${islandId}/payCard/'+cardId.value">Pagar con esta carta</button>
	    			</form>
	    		</div>
	    </c:if>	
    </div><br><br><br><br>
    <table id="GameInventory" class="table table-striped">
        <thead>
        <tr>
            <th>Jugador</th>
            <th>Cartas en el inventario</th>
            <th>ID Carta</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${game.status}" var="status2">
            <tr>
            	<td>
                    <c:out value="${status2.player.user.username}"/>
                </td>
                <td>
                	<c:forEach items="${status2.cards}" var="card">
                   	 	<img width=4% src="<spring:url value="${card.urlCardImg}" htmlEscape="true" />" />
                   	</c:forEach>
                </td> 
                                <td>
                	<c:forEach items="${status2.cards}" var="card">
                   	 	<p><c:out value="${card.id}"/></p></c:forEach>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    

</petclinic:layout>