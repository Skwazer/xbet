<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="match.date" var="matchDate"/>
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
                                    <input id="date" class="input" type="date" name="date" onchange="checkDate()">
                                    <div id="dateDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="team1ID" class="input" type="number" name="team1ID" min="1" max="50"
                                           onchange="checkTeam1ID()" placeholder="<c:out value="${team1ID}"/>">
                                    <div id="team1IDDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="team2ID" class="input" type="number" name="team2ID" min="1" max="50"
                                           onchange="checkTeam2ID()"
                                           placeholder="<c:out value="${team2ID}"/>">
                                    <div id="team2IDDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="v1" class="input" type="number" name="v1" min="1" max="4" step="0.01"
                                           onchange="check1()"
                                           placeholder="<c:out value="${match1}"/>">
                                    <div id="v1Div" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="X" class="input" type="number" name="X" min="1" max="4" step="0.01"
                                           onchange="checkX()"
                                           placeholder="<c:out value="${matchX}"/>">
                                    <div id="XDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="v2" class="input" type="number" name="v2" min="1" max="4" step="0.01"
                                           onchange="check2()"
                                           placeholder="<c:out value="${match2}"/>">
                                    <div id="v2Div" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="X1" class="input" type="number" name="X1" min="1" max="4" step="0.01"
                                           onchange="check1X()"
                                           placeholder="<c:out value="${match1X}"/>">
                                    <div id="X1Div" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="v12" class="input" type="number" name="v12" min="1" max="4" step="0.01"
                                           onchange="check12()"
                                           placeholder="<c:out value="${match12}"/>">
                                    <div id="v12Div" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="X2" class="input" type="number" name="X2" min="1" max="4" step="0.01"
                                           onchange="check2X()"
                                           placeholder="<c:out value="${match2X}"/>">
                                    <div id="X2Div" style="display:none;"></div>
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
    <%@include file="../../../js/create-match.js" %>
</script>

<%@include file="../footer.jsp" %>