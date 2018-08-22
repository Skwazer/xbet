<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="enter.user.id" var="enterUserId"/>
    <fmt:message key="enter.match.id" var="enterMatchId"/>
    <fmt:message key="enter.bet.result" var="enterBetResult"/>
    <fmt:message key="bet" var="enterBet"/>
    <fmt:message key="enter.money" var="enterMoney"/>
    <fmt:message key="status" var="enterStatus"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="user.id.required" var="userIdRequired"/>
    <fmt:message key="user.id.negative" var="userIdNegative"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="match.id.required" var="matchIdRequired"/>
    <fmt:message key="negative" var="negative"/>
    <fmt:message key="bet.required" var="betRequired"/>
    <fmt:message key="bet.excess" var="betExcess"/>
    <fmt:message key="money.required" var="moneyRequired"/>
    <fmt:message key="money.negative" var="moneyNegative"/>
    <fmt:message key="status.required" var="statusRequired"/>
    <fmt:message key="whitespace" var="whitespace"/>

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
                                    <input id="userId" class="input" type="number" name="userId" onchange="checkUserId()"
                                           placeholder="<c:out value="${enterUserId}"/>">
                                    <div id="userIdDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="matchId" class="input" type="number" name="matchId"
                                           onchange="checkMatchId()"
                                           placeholder="<c:out value="${enterMatchId}"/>">
                                    <div id="matchIdDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <select id="betResult" name="betResult" class="form-control" form="createForm">
                                        <option value="1">1</option>
                                        <option value="X">X</option>
                                        <option value="2">2</option>
                                        <option value="1X">1X</option>
                                        <option value="12">12</option>
                                        <option value="2X">2X</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <input id="bet" class="input" type="number" name="bet" onchange="checkBet()"
                                           step="0.001" placeholder="<c:out value="${enterBet}"/>">
                                    <div id="betDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="money" class="input" type="number" name="money" onchange="checkMoney()"
                                           placeholder="<c:out value="${enterMoney}"/>">
                                    <div id="moneyDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <input id="status" class="input" name="status" onchange="checkStatus()"
                                           placeholder="<c:out value="${enterStatus}"/>">
                                    <div id="statusDiv" style="display:none;"></div>
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
    <%@include file="../../../js/create-bet.js" %>
</script>

<%@include file="../footer.jsp" %>