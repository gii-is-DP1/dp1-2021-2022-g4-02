<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h1 align="center">Partida en curso</h1>

    <br></br></br>
    <div class="col-md-12">
	    <div class="col-md-6" align="left">
	    	<h2>Ronda Nº <c:out value="${game.currentRound}"></c:out><br/> Turno de: <c:out value="${currentPlayerStatus.player.user.username}"></c:out></h2>
	    </div>
	    <div class="col-md-6" align="right"><br>
	    	<h2>Cógido de Partida: <c:out value="${game.code}"></c:out></h2>
	    </div>
    </div>
    <br></br>

	<table id="Game" class="table table-striped">
		<thead>
			<tr>
				<th>Número de isla</th>
				<th>Carta asociada</th>
				<th>ID Carta</th>
				<!-- <th>Acción</th>-->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${game.islandStatus}" var="islandstatus">
				<tr>
					<td><c:out value="${islandstatus.island.id}" /></td>
					<td><c:out value="${islandstatus.card.cardType}" /></td>
					<td><c:out value="${islandstatus.card.id}" /></td>

				</tr>
			</c:forEach>
		</tbody>
		
	</table>

	<div align="center" style="margin-bottom:1.25em;">
    	<c:if test="${game.finishedTurn==0}">
    		<c:if test="${loggedUserId==playerUserId}">
    			<c:if test="${currentPlayerStatus.diceNumber == null}"><button type="button" align="center" class="btn btn-primary" onclick="window.location.href='/games/${game.code}/dice'"> Lanzar dado</button>
    			</c:if>       
    		</c:if>
    		<c:if test="${currentPlayerStatus.diceNumber != null}"> <h3>Resultado de la tirada: <c:out value="${currentPlayerStatus.diceNumber}"></c:out></h3>
    		</c:if>
    	</c:if>
    	<c:if test="${game.finishedTurn==1}"><p><button type="button" align="center" class="btn btn-primary" onclick="window.location.href='/games/${game.code}/turn'"> Pasar turno</button> </p></c:if>
    	 		
    </div>
    	 
    <c:if test="${loggedUserId==playerUserId && game.finishedTurn==0}">
    	<c:if test="${currentPlayerStatus.diceNumber != null}">
    		<h3 align="center">Introduzca la isla que quiere saquear:</h3>
    		<div align="center" style="margin-bottom:1.25em;">   	
    			<form class="form-inline">
        			<select name="islandId" class="form-control" width=100>
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
            <th>ID Carta</th>
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
                		<p><c:out value="${card.cardType}"/></p>
                   	 </c:forEach>
                </td> 
                <td>
                	<c:forEach items="${status.cards}" var="card">
                		<p><c:out value="${card.id}"/></p>
                   	 </c:forEach>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    

</petclinic:layout>