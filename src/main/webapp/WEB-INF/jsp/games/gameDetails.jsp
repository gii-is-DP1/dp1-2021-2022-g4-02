<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Game</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
         	<th>Code</th>
            <th>Hora de comienzo</th>
            <th>Hora de fin</th>
            <th>Jugadores</th>
        </tr>
        </thead>
        <tbody>
            <tr>
				<td>
					<c:out value="${game.code}" />
				</td>
				<td>
					<c:out value="${game.startHour}" />
				</td>
				<td>
					<c:out value="${game.endHour}" />
				</td>
				<td>
					<c:forEach items="${game.players}" var="player">
						<c:out value="${player.user.username}" />
					</c:forEach>
				</td>

			</tr>
        </tbody>
    </table>
</petclinic:layout>