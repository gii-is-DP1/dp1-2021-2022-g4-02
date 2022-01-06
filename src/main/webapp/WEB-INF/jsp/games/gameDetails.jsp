<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Detalles de la partida</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
         	<th>C�digo</th>
         	<th>Modo de Juego</th>
            <th>Hora de comienzo</th>
            <th>Hora de fin</th>
            <th>Jugadores</th>
            <th>Puntos</th>
        </tr>
        </thead>
        <tbody>
            <tr>
				<td>
					<c:out value="${game.code}" />
				</td>
				 <td >
					<c:if test="${game.gameMode==0}">Normal</c:if>
					<c:if test="${game.gameMode==1}">Secundario</c:if>
                </td>
				<td>
					<c:out value="${game.startHour}" />
				</td>
				<td>
					<c:out value="${game.endHour}" />
				</td>
				<td>
					<c:forEach items="${game.status}" var="status">
						<p><c:out value="${status.player.user.username}" /></p>		
					</c:forEach>
				</td>
				<td>
					<c:forEach items="${game.status}" var="status">
						<p><c:out value="${status.score}" /></p>	
					</c:forEach>
				</td>
			</tr>
        </tbody>
    </table>
    <c:if test="${game.startHour==null}">
    	<spring:url value="/games/${game.code}/start" var="startgameUrl"></spring:url>
    	<a href="${fn:escapeXml(startgameUrl)}"><button> Empezar Partida</button></a>
    
    </c:if>
</petclinic:layout>