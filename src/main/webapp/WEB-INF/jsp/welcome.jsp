<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->
<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    
    <div class="row">
        <h2> Project ${title}</h2>
        <h2> Group ${group}</h2>
        <ul>
          <c:forEach items="${persons}" var="person">
              <li> ${person.firstName}    ${person.lastName}  </li>
          </c:forEach>
        </ul>
      
    </div>
        
        <form:form modelAttribute="game" class="form-horizontal">
           	<div style="text-align: center">
           	      <button class="btn btn-default" type="submit" onclick=" location.href='/games/create'">Crear Partida</button>
           	</div>
        </form:form>

</petclinic:layout>
