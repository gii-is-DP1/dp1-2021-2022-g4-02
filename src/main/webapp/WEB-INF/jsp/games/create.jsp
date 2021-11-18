<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <jsp:body>
        <form:form modelAttribute="game"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${game.id}"/>
            <input type="hidden" name="code" value="${game.code}"/>
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            
                <button class="btn btn-default" type="submit">Crear Partida</button>
            </div>
        </div>
        </form:form>
    </jsp:body>
</petclinic:layout>