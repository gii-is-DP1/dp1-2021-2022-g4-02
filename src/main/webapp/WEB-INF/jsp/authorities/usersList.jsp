<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>Users' List</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
        	<th>ID</th>
            <th>UserName</th>
            <th>Authority</th>
            <th> Actions </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
            	<td>
                    <c:out value="${user.id}"/>
                </td> 
                <td>
                    <c:out value="${user.username}"/>
                </td>
                <td>
                    <c:out value="${user.authorities}"/>
                </td>
                
                 <td>
                 <p>
                    <spring:url value="/admin/users/{userId}/delete" var="userUrl">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(userUrl)}">Borrar usuario</a>
                   </p>
                   
                   <p>
                     <spring:url value="/admin/{userId}/edit" var="usereditUrl">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(usereditUrl)}">Editar usuario</a>
                    </p>
                </td>
              
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <spring:url value="/admin/users/new" var="usernewUrl"> </spring:url>
    <a href="${fn:escapeXml(usernewUrl)}">Crear usuario</a>
    
</petclinic:layout>