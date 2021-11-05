<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cards">
    <h2>Cards</h2>

    <table id="cardsTable" class="table table-striped">
        <thead>
        <tr>
        	<th>ID</th>
            <th>Card Type</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cards}" var="card">
            <tr>
            	<td>
                    <c:out value="${card.id}"/>
                </td>
                <td>
                    <c:out value="${card.cardType}"/>
                </td>
              
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>