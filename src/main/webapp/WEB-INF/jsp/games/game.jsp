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
    <table id="Game" class="table table-striped">
        <thead>
        <tr>
            <th>Islas</th>
            <th>Número de isla</th>
            <th>Carta asociada</th>
            <!-- <th>Acción</th>-->
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${game.island}" var="island">
            <tr>
            	<td>
                    <c:out value="${island.id}"/>
                </td>
                <td>
                    <c:out value="${island.card}"/>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div align="center">
        <button type="button" align="center">Lanzar dado</button>
        <p>Resultado de la tirada: <c:out value="${game.diceNumber}"></c:out></p>
    </div>
    
    <p>Introduzca la isla que quiere saquear:</p>
    
    <div align="center">
    <form>
        <input type="text" align="center" placeholder="Número de isla"></input>
        <button type="submit" align="center">Saquear isla</button>
    </form>
    </div>
    
    <table id="GameInventory" class="table table-striped">
        <thead>
        <tr>
            <th>Jugador</th>
            <th>Cartas en el inventario</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${game.players}" var="player">
            <tr>
            	<td>
                    <c:out value="${player.username}"/>
                </td>
                <td>
                    <c:out value="${player.cards}"/>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    

</petclinic:layout>