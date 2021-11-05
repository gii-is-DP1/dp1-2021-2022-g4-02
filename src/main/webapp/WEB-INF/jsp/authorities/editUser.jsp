<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <jsp:body>
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
            </div>
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Create User</button>
            </div>
        </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
