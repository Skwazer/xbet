<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="match.id" var="matchIdTitle"/>
    <fmt:message key="result" var="resultTitle"/>
    <fmt:message key="team1.goals" var="team1GoalsTitle"/>
    <fmt:message key="team2.goals" var="team2GoalsTitle"/>
    <fmt:message key="negative" var="negative"/>
    <fmt:message key="goals.incorrect" var="goalsIncorrect"/>
    <fmt:message key="match.id.required" var="matchIDRequired"/>
    <fmt:message key="team1.goals.required" var="team1GoalsRequired"/>
    <fmt:message key="team2.goals.required" var="team2GoalsRequired"/>
    <fmt:message key="match.1" var="match1"/>
    <fmt:message key="match.X" var="matchX"/>
    <fmt:message key="match.2" var="match2"/>

</fmt:bundle>


<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="billing-details">
                    <div class="section-title">
                        <h3 class="title"><c:out value="${createData}"/></h3>
                    </div>
                    <form id="createForm" class="clearfix" method="post" action="#">
                        <div class="form-group">
                            <label for="select1" class="normal"><c:out value="${matchIdTitle}"/></label>
                            <select id="select1" name="matchID" class="form-control no-radius" form="createForm">
                                <c:forEach var="matchId" items="${matchesIds}">
                                    <option value="${matchId}"><c:out value="${matchId}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <input id="team1Goals" class="input" type="number" name="team1Goals" min="0" max="6" step="1"
                                   onchange="checkTeam1Goals()" placeholder="<c:out value="${team1GoalsTitle}"/>">
                            <div id="team1GoalsDiv" style="display:none;"></div>
                        </div>
                        <div class="form-group">
                            <input id="team2Goals" class="input" type="number" name="team2Goals" min="0" max="6" step="1"
                                   onchange="checkTeam2Goals()" placeholder="<c:out value="${team2GoalsTitle}"/>">
                            <div id="team2GoalsDiv" style="display:none;"></div>
                        </div>
                        <div class="form-group">
                            <select id="result" name="result" class="form-control no-radius" form="createForm">
                                <option value="1"><c:out value="${match1}"/></option>
                                <option value="X" selected><c:out value="${matchX}"/></option>
                                <option value="2"><c:out value="${match2}"/></option>
                            </select>
                        </div>
                    </form>
                    <button id="createButton" class="primary-btn pull-left"
                            onclick="changeFormAction()"><c:out value="${create}"/></button>
                </div>
            </div>
        </div>
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<script type="text/javascript" charset="UTF-8">
    <%@include file="../../../js/create-result.js" %>
</script>

<%@include file="../footer.jsp" %>