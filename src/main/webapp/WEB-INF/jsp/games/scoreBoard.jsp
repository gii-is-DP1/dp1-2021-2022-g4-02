<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Ranking de la partida</h2>
    
    <p>El ganador es: <c:forEach items="${winners}" var="status">
    	 	<c:out value="${player.username}"></c:out>
    	 </c:forEach></p>

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
</petclinic:layout>