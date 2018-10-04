<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="update" var="update"/>
    <fmt:message key="delete" var="delete"/>
    <fmt:message key="match.date" var="matchDate"/>
    <fmt:message key="date.incorrect" var="dateIncorrect"/>
    <fmt:message key="incorrect.value" var="incorrectValue"/>
    <fmt:message key="team1.id" var="team1ID"/>
    <fmt:message key="team2.id" var="team2ID"/>
    <fmt:message key="match.1" var="match1"/>
    <fmt:message key="match.X" var="matchX"/>
    <fmt:message key="match.2" var="match2"/>
    <fmt:message key="match.1X" var="match1X"/>
    <fmt:message key="match.12" var="match12"/>
    <fmt:message key="match.2X" var="match2X"/>
    <fmt:message key="date.required" var="dateRequired"/>
    <fmt:message key="team1.required" var="team1Required"/>
    <fmt:message key="team2.required" var="team2Required"/>
    <fmt:message key="teams.equal" var="teamsEqual"/>
    <fmt:message key="negative" var="negative"/>
    <fmt:message key="victory1.required" var="v1Required"/>
    <fmt:message key="victory2.required" var="v2Required"/>
    <fmt:message key="incorrect.coefficient" var="incorrectCoefficient"/>
    <fmt:message key="draw.required" var="XRequired"/>
    <fmt:message key="x1.required" var="X1Required"/>
    <fmt:message key="v12.required" var="v12Required"/>
    <fmt:message key="x2.required" var="X2Required"/>

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
                                    <label for="id">ID</label>
                                    <input id="id" class="input" name="id"
                                           value="<c:out value="${updateMatch.id}"/>" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="date"><c:out value="${matchDate}"/></label>
                                    <input id="date" class="input" type="date" name="date" onchange="checkDate()"
                                           value="${updateMatch.date}">
                                    <div id="dateDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="select1"><c:out value="${team1ID}"/></label>
                                    <select id="select1" name="team1ID" class="form-control no-radius" form="createForm">
                                        <c:forEach var="teamId" items="${teamsIds}">
                                            <option <c:if test="${teamId eq updateMatch.team1_id}">selected</c:if>
                                                    value="${teamId}">
                                                <c:out value="${teamId}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="select2"><c:out value="${team2ID}"/></label>
                                    <select id="select2" name="team2ID" class="form-control no-radius" form="createForm">
                                        <c:forEach var="teamId" items="${teamsIds}">
                                            <option <c:if test="${teamId eq updateMatch.team2_id}">selected</c:if>
                                                    value="${teamId}">
                                                <c:out value="${teamId}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="v1"><c:out value="${match1}"/></label>
                                    <input id="v1" class="input" type="number" name="v1" min="1" max="4" step="0.01"
                                           onchange="check1()" value="<c:out value="${updateMatch.victory1}"/>">
                                    <div id="v1Div" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="X"><c:out value="${matchX}"/></label>
                                    <input id="X" class="input" type="number" name="X" min="1" max="4" step="0.01"
                                           onchange="checkX()" value="<c:out value="${updateMatch.draw}"/>">
                                    <div id="XDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="v2"><c:out value="${match2}"/></label>
                                    <input id="v2" class="input" type="number" name="v2" min="1" max="4" step="0.01"
                                           onchange="check2()" value="<c:out value="${updateMatch.victory2}"/>">
                                    <div id="v2Div" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="X1"><c:out value="${match1X}"/></label>
                                    <input id="X1" class="input" type="number" name="X1" min="1" max="4" step="0.01"
                                           onchange="check1X()" value="<c:out value="${updateMatch.victory1OrDraw}"/>">
                                    <div id="X1Div" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="v12"><c:out value="${match12}"/></label>
                                    <input id="v12" class="input" type="number" name="v12" min="1" max="4" step="0.01"
                                           onchange="check12()" value="<c:out value="${updateMatch.victory1Or2}"/>">
                                    <div id="v12Div" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="X2"><c:out value="${match2X}"/></label>
                                    <input id="X2" class="input" type="number" name="X2" min="1" max="4" step="0.01"
                                           onchange="check2X()" value="<c:out value="${updateMatch.victory2OrDraw}"/>">
                                    <div id="X2Div" style="display:none;"></div>
                                </div>
                            </form>
                            <button class="primary-btn pull-left" onclick="changeFormAction()">
                                <c:out value="${update}"/></button>
                            <button form="deleteForm" class="primary-btn pull-right">
                                <c:out value="${delete}"/></button>
                            <form id="deleteForm" method="post" action="<c:url value="/main/delete/match"/>">
                                <input type="hidden" name="key" value="${updateMatch.id}">
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
    <%@include file="../../../js/update-match.js" %>
</script>



<%@include file="../footer.jsp" %>