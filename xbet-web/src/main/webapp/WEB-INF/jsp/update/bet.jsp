<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="enter.user.id" var="enterUserId"/>
    <fmt:message key="enter.match.id" var="enterMatchId"/>
    <fmt:message key="enter.bet.result" var="enterBetResult"/>
    <fmt:message key="bet" var="enterBet"/>
    <fmt:message key="enter.money" var="enterMoney"/>
    <fmt:message key="status" var="enterStatus"/>
    <fmt:message key="update" var="update"/>
    <fmt:message key="delete" var="delete"/>
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
                            <form id="createForm" class="clearfix" method="post"
                                  action="<c:url value="/main/change/bet"/> ">
                                <div class="form-group">
                                    <label for="id">ID</label>
                                    <input id="id" class="input" name="id"
                                           value="<c:out value="${updateBet.id}"/>" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="userId"><c:out value="${enterUserId}"/></label>
                                    <input id="userId" class="input" type="number" name="userId"
                                           onchange="checkUserId()" value="<c:out value="${updateBet.user_id}"/>">
                                    <div id="userIdDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="matchId"><c:out value="${enterMatchId}"/></label>
                                    <input id="matchId" class="input" type="number" name="matchId"
                                           onchange="checkMatchId()" value="<c:out value="${updateBet.match_id}"/>">
                                    <div id="matchIdDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="betResult"><c:out value="${updateBet.betResult}"/></label>
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
                                    <label for="bet"><c:out value="${enterBet}"/></label>
                                    <input id="bet" class="input" type="number" name="bet" onchange="checkBet()"
                                           step="0.001" value="<c:out value="${updateBet.bet}"/>">
                                    <div id="betDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="money"><c:out value="${enterMoney}"/></label>
                                    <input id="money" class="input" type="number" name="money" onchange="checkMoney()"
                                           value="${updateBet.money}">
                                    <div id="moneyDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="status"><c:out value="${enterStatus}"/></label>
                                    <input id="status" class="input" name="status" onchange="checkStatus()"
                                           value="<c:out value="${updateBet.status}"/>">
                                    <div id="statusDiv" style="display:none;"></div>
                                </div>
                                <button class="primary-btn pull-left"><c:out value="${update}"/></button>
                                <button form="deleteForm" class="primary-btn pull-right">
                                    <c:out value="${delete}"/></button>
                            </form>
                            <form id="deleteForm" method="post" action="<c:url value="/main/delete/bet"/>">
                                <input type="hidden" name="key" value="${updateBet.id}">
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
    <%@include file="../../../js/update-bet.js" %>
</script>



<%@include file="../footer.jsp" %>