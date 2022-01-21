<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="statistics">
    <h2>Statistics con jugador: <c:out value="${statistics.player.user.username}"/></h2>

    <table id="statisticsTable1" class="table table-striped">
        <thead>
        <tr>
        	<th>Número de partidas jugadas</th>
        	<th>Número de partidas ganadas</th>
            <th>Puntuación media por partida</th>
            <th>Tiempo medio por partida (en minutos)</th>
            
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <c:out value="${statistics.gamesPlayed}"/>
                </td>
                <td>
                    <c:out value="${statistics.gamesWon}"/>
                </td>
                <td>
                    <c:out value="${statistics.averageScore}"/>
                </td>
                <td>
                    <c:out value="${statistics.averageTime}"/>
                </td>                              
            </tr>
        </tbody>
    </table>
    
    <table id="statisticsTable2" class="table table-striped text-center">
       <thead>
       <tr>
           <th>Total de doblones</th>
           <th>Total de pistolas</th>
           <th>Total de espadas</th>
           <th>Total de rones</th>
           <th>Total de coronas</th>
           <th>Total de mapas</th>
           <th>Total de collares</th>
           <th>Total de cálices</th>
           <th>Total de diamantes</th>
           <th>Total de rubíes</th>
       </tr>
       </thead>
       <tbody>
           <tr>
               <td>
                   <c:out value="${statistics.doubloonCount}   "/>
                   <img width=50px src="<spring:url value="/resources/images/doblon.jpg" htmlEscape="true" />"/>
               </td>   
           	<td>
                   <c:out value="${statistics.gunCount}   "/>
                   <img width=50px src="<spring:url value="/resources/images/pistola.jpg" htmlEscape="true" />"/>
               </td>
               <td>
                   <c:out value="${statistics.swordCount}   "/>
                   <img width=50px src="<spring:url value="/resources/images/espada.jpg" htmlEscape="true" />"/>
               </td>
               <td>
                   <c:out value="${statistics.rumCount}"/>
                   <img width=50px src="<spring:url value="/resources/images/ron.jpg" htmlEscape="true" />"/>
               </td>
               <td>
                   <c:out value="${statistics.crownCount}"/>
                   <img width=50px src="<spring:url value="/resources/images/corona.jpg" htmlEscape="true" />"/>
               </td>
               <td>
                   <c:out value="${statistics.mapCount}"/>
                   <img width=50px src="<spring:url value="/resources/images/mapa.jpg" htmlEscape="true" />"/>
               </td>
               <td>
                   <c:out value="${statistics.necklaceCount}"/>
                   <img width=50px src="<spring:url value="/resources/images/collar.jpg" htmlEscape="true" />"/>
               </td> 
                <td>
                   <c:out value="${statistics.chaliceCount}"/>
                   <img width=50px src="<spring:url value="/resources/images/caliz.jpg" htmlEscape="true" />"/>
               </td>
               <td>
                   <c:out value="${statistics.diamondCount}"/>
                   <img width=50px src="<spring:url value="/resources/images/diamante.jpg" htmlEscape="true" />"/>
               </td>
               <td>
                   <c:out value="${statistics.rubyCount}"/>
                   <img width=50px src="<spring:url value="/resources/images/rubi.jpg" htmlEscape="true" />"/>
               </td>                                   
           </tr>
       </tbody>
    </table>
    
    <table id="Achievements" class="table table-striped">
        <thead>
        <tr>
            <th>Logro</th>
            <th>Conseguido</th>
            
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${achievements}" var="achievement">
            <tr>
            	<td>
                    <c:out value="${achievement.achievement.achievementType.description}"/>
                </td>
                <td>
                	<c:if test="${achievement.achieved}">
                		Sí
                   	 </c:if>
                   	 <c:if test="${!achievement.achieved}">
                   	 	No
                   	 </c:if>
                </td> 
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>