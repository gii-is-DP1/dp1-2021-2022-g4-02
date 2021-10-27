<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cards">
    <jsp:body>
        <form:form modelAttribute="card"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${card.id}"/>
            <div class="form-group has-feedback">
                <div class="control-group">
                    <petclinic:selectField name="cardType" label="Type " names="${types}" size="5"/>
                </div>
            </div>
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Update Card</button>
            </div>
        </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
