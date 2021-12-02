<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h1 align="center">Partida en curso</h1>

    <br></br>
    <div align="right">
        <p>Cógido de Partida: <c:out value="${game.code}"></c:out></p>
    </div>
    <br></br>
    <p>Turno Nº <c:out value="${game.currentTurn}"></c:out>. Turno de: <c:out value="${currentPlayerStatus.player.user.username}"></c:out></p>
    <table id="Game" class="table table-striped">
        <thead>Islas
        <tr>
            
            <th>Número de isla</th>
            <th>Carta asociada</th>
            <!-- <th>Acción</th>-->
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${game.islandStatus}" var="islandstatus">
            <tr>
            	<td>
                    <c:out value="${islandstatus.island.id}"/>
                </td>
                <td>
                    <c:out value="${islandstatus.card.id}"/>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
	
    <div align="center">
    	<c:if test="${game.finishedTurn==0}">
    		<c:if test="${loggedUserId==playerUserId}">
    			<c:if test="${currentPlayerStatus.diceNumber == null}"><button type="button" align="center" class="btn btn-primary" onclick="window.location.href='/games/${game.code}/dice'"> Lanzar dado</button>
    			</c:if>       
    		</c:if>
    		<c:if test="${currentPlayerStatus.diceNumber != null}"> <p>Resultado de la tirada: <c:out value="${currentPlayerStatus.diceNumber}"></c:out></p>
    		</c:if>
    	</c:if>
    	<c:if test="${game.finishedTurn==1}"><p><button type="button" align="center" class="btn btn-primary" onclick="window.location.href='/games/${game.code}/turn'"> Pasar turno</button> </p></c:if>
    	 		
    </div>
    	 
    <c:if test="${loggedUserId==playerUserId && game.finishedTurn==0}">
    	<c:if test="${currentPlayerStatus.diceNumber != null}">
    		<p>Introduzca la isla que quiere saquear:</p>
    		<div align="center">   	
    			<form>
        			<select name="islandId">
          				<c:forEach var="islandStatus" items="${game.islandStatus}">
            				<option value="${islandStatus.island.id}">Isla ${islandStatus.island.id}</option>
          				</c:forEach>
       				 </select>
        			<button type="button" class="btn btn-primary" onclick="window.location.href='/games/${game.code}/robIsland/'+islandId.value"> Saquear Isla</button>
    			</form>
    		</div>
    	</c:if>	
    </c:if>
    
    <table id="GameInventory" class="table table-striped">
        <thead>
        <tr>
            <th>Jugador</th>
            <th>Cartas en el inventario</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${game.status}" var="status">
            <tr>
            	<td>
                    <c:out value="${status.player.user.username}"/>
                </td>
                <td>
                	<c:forEach items="${status.cards}" var="card">
                   	 	<p><c:out value="${card.id}"/></p></c:forEach>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    

</petclinic:layout>