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
            <th>Autoridad</th>
            <th> Acciones de usuario </th>
            <th> Acciones de autoridad </th>
        </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${users}" var="user">
            <tr> 
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
                <td>
                   <c:choose>
                        <c:when test="${user.authorities!=null}">
                        <p>
                            <spring:url value="/admin/authorities/{userId}/edit" var="autheditUrl">
                        <spring:param name="userId" value="${user.id}"/>
                    	</spring:url>
                    <a href="${fn:escapeXml(autheditUrl)}">Editar autoridad</a></p>
                    <p>
                    <spring:url value="/admin/authorities/{userId}/delete" var="authUrl">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(authUrl)}">Borrar autoridad</a></p>
                        </c:when>
                        <c:otherwise><p>
                            <spring:url value="/admin/authorities/{userId}/new" var="autheditUrl">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(autheditUrl)}">Crear autoridad</a></p>
                        </c:otherwise>
                    </c:choose>
                    
                </td>
              
            </tr>
        	</c:forEach>

        </tbody>
    </table>
    <spring:url value="/admin/users/new" var="usernewUrl"></spring:url>
    <a href="${fn:escapeXml(usernewUrl)}"><button> Crear usuario</button></a>
    <div class="row">
    	<div class="col-md-2">
    	</div>
    	<div class="col-md-8 text-center">
    		<nav aria-label="Pagination">
  				<ul class="pagination">
    				<c:forEach items="${paginas}" var="page">
    					<li class="page-item"> 
    						<a class="page-link" href="?page=${page}">
    						<c:out value="${page}"> </c:out>
    						</a>
    					</li>
    				
    				</c:forEach>
    	
    			</ul>
    		</nav>	
    	</div>
    	<div class="col-md-2">
    	</div>
    
    </div>
</petclinic:layout>



