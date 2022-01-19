<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="statistics">
    <h2>Ranking mejores jugadores</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Posición</th>
        	<th>Jugador</th>
            <th>Puntuación total</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${statistics}" var="statistic" varStatus="loop">
            <tr>
            	<td >
                    <c:out value="${loop.index+1}º"/>
                </td>
                <td >
					 <c:out value="${statistic.player.user.username}"/>
					
                </td>
                <td>
                    <c:out value="${statistic.totalScore}"/>
                </td>
                
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>