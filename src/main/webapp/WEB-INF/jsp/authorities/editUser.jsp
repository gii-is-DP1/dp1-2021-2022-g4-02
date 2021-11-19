<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <jsp:body>
    	<h2>
            <c:if test="${user['new']}">New </c:if> User
        </h2>
    
        <form:form modelAttribute="user"
                   class="form-horizontal">
 
            <h2> Choose an new user's username </h2>
                <div class="control-group">
            		<petclinic:inputField label="Username" name="username" />	
                </div>                
                 <h2> Choose an new user's password </h2>
                <div class="control-group">
            		<petclinic:inputField label="Password" name="password" />  		
                </div>
                <h2> Choose an new user's first name </h2>
                <div class="control-group">
            		<petclinic:inputField label="First Name" name="firstName" />	
                </div>                
                 <h2> Choose an new user's last name </h2>
                <div class="control-group">
            		<petclinic:inputField label="Last Name" name="lastName" />  		
                </div>
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                        <c:when test="${user['new']}">
                            <button class="btn btn-default" type="submit">Add User</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update User</button>
                        </c:otherwise>
                    </c:choose>
            </div>
        </div>
        </form:form>
        <c:if test="${!user['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
