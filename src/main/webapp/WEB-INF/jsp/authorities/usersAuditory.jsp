<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>Lista de usuarios</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
        	
            <th>Nombre de usuario</th>
            <th>Creador</th>
            <th> Modificador </th>
            <th> Fecha de creación </th>
            <th> Fecha última modificación </th>
        </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${users}" var="user">
            <tr> 
                <td>
                    <c:out value="${user.username}"/>
                </td>
                <td>
                    <c:out value="${user.creator}"/>
                </td>
                
                <td>
                    <c:out value="${user.modifier}"/>
                </td>
              		
              	<td>
                    <c:out value="${user.createdDate}"/>
                </td>
                <td>
                    <c:out value="${user.lastModifiedDate}"/>
                </td>
            </tr>
        	</c:forEach>

        </tbody>
    </table>
    	<div class="col-md-2">
    	</div>
    
    </div>
</petclinic:layout>



