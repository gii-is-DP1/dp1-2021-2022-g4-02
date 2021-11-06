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
            <c:if test="${authorities['new']}">New </c:if> Authority
        </h2>
    
        <form:form modelAttribute="authorities"
                   class="form-horizontal">
 		<input type="hidden" name="id" value="${authorities.id}"/>
            <h2> Choose an new user's authority </h2>
                <div class="control-group">
            		<petclinic:inputField label="Authority" name="authority" />	
                </div>
                <c:if test="${authorities['new']}"><h2> Choose the user </h2>
                <div class="control-group">
            		<petclinic:inputField label="User_ID" name="user" />	
                </div></c:if>                
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                        <c:when test="${authorities['new']}">
                            <button class="btn btn-default" type="submit">Add Authority</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Authority</button>
                        </c:otherwise>
                    </c:choose>
            </div>
        </div>
        </form:form>
        <c:if test="${!authorities['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
