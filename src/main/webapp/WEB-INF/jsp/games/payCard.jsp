<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="payCard">
    <h1 align="center">Partida en curso</h1>

    <br></br>
    <div align="right">
        <p>Cógido de Partida: <c:out value="${game.code}"></c:out></p>
    </div>
    <br></br>
    <p>Turno Nº <c:out value="${game.currentTurn}"></c:out></p>
	<br></br>
	<c:if test="${diff==1}"> <p>Te queda por pagar <c:out value="${diff}"> </c:out> carta.</p></c:if>
    <c:if test="${diff>1}"> <p>Te quedan por pagar <c:out value="${diff}"> </c:out> cartas.</p></c:if>
    	 
    <c:if test="${loggedUserId==playerUserId}">
    		<p>Elige la carta con la que quieres pagar:</p>
    		<div align="center">   	
    			<form>
        			<select name="cardId">
          				<c:forEach var="card" items="${status.cards}">
            				<option value="${card.id}">Carta ${card.id}</option>
          				</c:forEach>
       				 </select>
        			<button type="button" class="btn btn-primary" onclick="window.location.href='/games/${code}/robIsland/${islandId}/payCard/${difference}/'+cardId.value">Pagar con esta carta</button>
    			</form>
    		</div>
    </c:if>	
    
    <table id="GameInventory" class="table table-striped">
        <thead>
        <tr>
            <th>Jugador</th>
            <th>Cartas en el inventario</th>
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
                   	 	<p><c:out value="${card.id}"/></p></c:forEach>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    

</petclinic:layout>