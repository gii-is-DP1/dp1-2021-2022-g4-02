<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="auths">
    <jsp:body>
    	<h2>
            <c:if test="${authorities['new']}">Nuevos </c:if> Permisos
        </h2>
    
        <form:form modelAttribute="authorities"
                   class="form-horizontal">
            <h2> Elige los nuevos permisos </h2>
                <div class="control-group">
            		<select name="authority">
						<option value="admin">Admin</option>
    					<option value="player">Jugador</option>
					</select>	
                </div>              
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                        <c:when test="${authorities['new']}">
                            <button class="btn btn-default" type="submit">Añadir permisos</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Editar permisos</button>
                        </c:otherwise>
                    </c:choose>
            </div>
        </div>
        </form:form>
        <c:if test="${!authorities['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
