<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="update" var="update"/>
    <fmt:message key="delete" var="delete"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="match.id" var="matchIdTitle"/>
    <fmt:message key="result" var="resultTitle"/>
    <fmt:message key="team1.goals" var="team1GoalsTitle"/>
    <fmt:message key="team2.goals" var="team2GoalsTitle"/>
    <fmt:message key="negative" var="negative"/>
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
        <c:choose>
            <c:when test="${user.role eq 1}">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="billing-details">
                            <div class="section-title">
                                <h3 class="title"><c:out value="${createData}"/></h3>
                            </div>
                            <form id="createForm" class="clearfix" method="post"
                                  action="<c:url value="/main/change/result"/> ">
                                <div class="form-group">
                                    <label for="id">ID</label>
                                    <input id="id" class="input" name="id"
                                           value="<c:out value="${updateResult.id}"/>" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="select1"><c:out value="${matchIdTitle}"/></label>
                                    <select id="select1" name="matchID" class="form-control no-radius" form="createForm">
                                        <c:forEach var="matchId" items="${matchesIds}">
                                            <option <c:if test="${matchId eq updateResult.matchId}">selected</c:if>
                                                    value="${matchId}">
                                                <c:out value="${matchId}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="team1Goals"><c:out value="${team1GoalsTitle}"/></label>
                                    <input id="team1Goals" class="input" type="number" name="team1Goals" min="1" max="6"
                                           onchange="checkTeam1Goals()" value="${updateResult.team1_goals}">
                                    <div id="team1GoalsDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="team2Goals"><c:out value="${team2GoalsTitle}"/></label>
                                    <input id="team2Goals" class="input" type="number" name="team2Goals" min="1" max="6"
                                           onchange="checkTeam2Goals()" value="${updateResult.team2_goals}">
                                    <div id="team2GoalsDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="result"><c:out value="${updateResult.result}"/></label>
                                    <select id="result" name="result" class="form-control no-radius" form="createForm">
                                        <option value="1"><c:out value="${match1}"/></option>
                                        <option value="X" selected><c:out value="${matchX}"/></option>
                                        <option value="2"><c:out value="${match2}"/></option>
                                    </select>
                                </div>
                                <button class="primary-btn pull-left"><c:out value="${update}"/></button>
                                <button form="deleteForm" class="primary-btn pull-right">
                                    <c:out value="${delete}"/></button>
                            </form>
                            <form id="deleteForm" method="post" action="<c:url value="/main/delete/result"/>">
                                <input type="hidden" name="key" value="${updateResult.id}">
                            </form>
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
    <%@include file="../../../js/update-result.js" %>
</script>



<%@include file="../footer.jsp" %>