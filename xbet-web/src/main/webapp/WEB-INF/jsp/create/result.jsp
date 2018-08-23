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
    <fmt:message key="team1.id" var="team1IdTitle"/>
    <fmt:message key="team2.id" var="team2IdTitle"/>
    <fmt:message key="team1.goals" var="team1GoalsTitle"/>
    <fmt:message key="team2.goals" var="team2GoalsTitle"/>
    <fmt:message key="team1.required" var="team1Required"/>
    <fmt:message key="team2.required" var="team2Required"/>
    <fmt:message key="negative" var="negative"/>
    <fmt:message key="match.id.required" var="matchIDRequired"/>
    <fmt:message key="team1.goals.required" var="team1GoalsRequired"/>
    <fmt:message key="team2.goals.required" var="team2GoalsRequired"/>

</fmt:bundle>


<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <c:choose>
            <c:when test="${user.role eq 1}">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="billing-details">
                            <div class="section-title">
                                <h3 class="title"><c:out value="${createData}"/></h3>
                            </div>
                            <form id="createForm" class="clearfix" method="post" action="#">
                                <div class="form-group">
                                    <input id="matchID" class="input" type="number" name="matchID" min="1" max="100"
                                           onchange="checkMatchID()" placeholder="<c:out value="${matchIdTitle}"/>">
                                    <div id="matchIDDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="team1ID" class="input" type="number" name="team1ID" min="1" max="50"
                                           onchange="checkTeam1ID()" placeholder="<c:out value="${team1IdTitle}"/>">
                                    <div id="team1IDDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="team2ID" class="input" type="number" name="team2ID" min="1" max="50"
                                           onchange="checkTeam2ID()" placeholder="<c:out value="${team2IdTitle}"/>">
                                    <div id="team2IDDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="team1Goals" class="input" type="number" name="team1Goals" min="1" max="6"
                                           onchange="checkTeam1Goals()" placeholder="<c:out value="${team1GoalsTitle}"/>">
                                    <div id="team1GoalsDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="team2Goals" class="input" type="number" name="team2Goals" min="1" max="6"
                                           onchange="checkTeam2Goals()" placeholder="<c:out value="${team2GoalsTitle}"/>">
                                    <div id="team2GoalsDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <select id="result" name="result" class="form-control" form="createForm">
                                        <option value="1">1</option>
                                        <option value="X" selected>X</option>
                                        <option value="2">2</option>
                                    </select>
                                </div>
                            </form>
                            <button id="createButton" class="primary-btn pull-left"
                                    onclick="changeFormAction()"><c:out value="${create}"/></button>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="text-center">
                    <h2><c:out value="${adminError}"/></h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<script type="text/javascript" charset="UTF-8">
    <%@include file="../../../js/create-result.js" %>
</script>

<%@include file="../footer.jsp" %>