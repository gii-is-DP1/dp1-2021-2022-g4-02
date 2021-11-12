<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h1 align="center">Creaci�n de Partida</h1>

    <br></br>
    <div align="right">
        <p> C�gido de Partida: <c:out value="${game.code}">  </c:out> </p>
    </div>
    <br></br>
    <table id="GameLobby" class="table table-striped">
        <thead>
        <tr>
            <th>Jugadores</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${game.players}" var="user">
            <tr>
            	<td>
                    <c:out value="${user.username}"/>
                </td>
                          
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div align="center">
        <button type="button" align="center">Iniciar Partida</button>
    </div>

</petclinic:layout>