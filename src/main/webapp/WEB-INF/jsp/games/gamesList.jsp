<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Games</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
        	<th>C�digo</th>
            <th>Hora de comienzo</th>
            <th>Hora de fin</th>
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
                    <c:out value="${game.endHour}"/>
                </td>
                <td>
                	<c:forEach items="${game.players}" var="player">
                    	<p><c:out value="${player.user.username}" /></p>
                    </c:forEach>
                </td>
                <td>
                    <div style="text-align: center">
           	      		<button class="btn btn-default" type="submit" onclick=" location.href='/games/${game.code}/enter'">Unirse</button>
           			</div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>