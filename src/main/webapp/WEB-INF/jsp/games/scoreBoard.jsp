<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h1 align='center'>Ranking de la partida</h1>
    <c:choose>
    <c:when test="${number>1}">
    <h1>Los ganadores son: <c:forEach items="${winners}" var="status" varStatus="loop">
    	 	<c:out value="${status.player.user.username}"></c:out>
    	 	<c:if test="${!loop.last }">, </c:if>
    	 </c:forEach></h1>
    	 </c:when>
    <c:otherwise><h1>El ganador es: <c:forEach items="${winners}" var="status">
    	 	<c:out value="${status.player.user.username}"></c:out>
    	 </c:forEach></h1>
	</c:otherwise>
	</c:choose>
    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Jugador</th>
            <th>Puntos</th>
        </tr>
        </thead>
        <tbody>
            <tr>
				<td>
					<c:forEach items="${ranking}" var="status">
						<p><c:out value="${status.player.user.username}" /></p>
					</c:forEach>
				</td>
				<td>
					<c:forEach items="${ranking}" var="status">
						<p><c:out value="${status.score}" /></p>					
					</c:forEach>
				</td>

			</tr>
        </tbody>
    </table>
    
    <table id="GameInventory" class="table table-striped">
        <thead>
        <tr>
            <th>Jugador</th>
            <th>Cartas finales</th>
            
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${ranking}" var="status">
            <tr>
            	<td>
                    <c:out value="${status.player.user.username}"/>
                </td>
                <td>
                	<c:forEach items="${status.cards}" var="card">
                		<img width=4% src="<spring:url value="${card.urlCardImg}" htmlEscape="true" />" />
                   	 </c:forEach>
                </td> 
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>