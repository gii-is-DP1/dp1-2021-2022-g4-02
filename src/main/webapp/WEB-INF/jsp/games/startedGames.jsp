<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Partidas comenzadas</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
          	<th>C�digo</th>
            <th>Hora de comienzo</th>
            <th>Jugadores</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
            	<td >
                    <c:out value="${game.code}"/>
                </td>
                <td>
                    <c:out value="${game.startHour}"/>
                </td>
                <td>
                	<c:forEach items="${game.status}" var="status">
                    	<p><c:out value="${status.player.user.username}" /></p>
                    </c:forEach>
                </td>
				<td>
					<div style="text-align: center">
						<button class="btn btn-default" type="submit">Espectar</button>
					</div>
				</td>
				</tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>