<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>

<fmt:bundle basename="i18n">
    <fmt:message key="error" var="error"/>
    <%--<fmt:message key="no.command" var="noCommand"/>
    <fmt:message key="login.failure" var="loginFailure"/>
    <fmt:message key="login.exception" var="loginException"/>
    <fmt:message key="registration.error" var="registrationError"/>
    <fmt:message key="matches.list.empty" var="matchesListEmpty"/>
    <fmt:message key="match.id.error" var="matchIdError"/>
    <fmt:message key="bet.param.error" var="betParamError"/>
    <fmt:message key="bet.error" var="betError"/>
    <fmt:message key="balance.error" var="balanceError"/>
    <fmt:message key="amount.error" var="amountError"/>
    <fmt:message key="top.up.error" var="topupError"/>
    <fmt:message key="bets.error" var="betsError"/>
    <fmt:message key="finish.error" var="finishError"/>
    <fmt:message key="number.error" var="numberError"/>
    <fmt:message key="no.users.error" var="noUsersError"/>
    <fmt:message key="users.error" var="usersError"/>
    <fmt:message key="create.user.error" var="createUserError"/>
    <fmt:message key="show.updateBalance.user.error" var="showUpdateUserError"/>--%>

</fmt:bundle>

<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <div class="centered text-center">
            <h2><c:out value="${error}"/></h2>
            <br>
            <h3>
                <c:if test="${not empty errorMessage}">
                    <fmt:bundle basename="i18n">
                        <fmt:message key="${errorMessage}" var="errorToShow"/>
                    </fmt:bundle>
                    <c:out value="${errorToShow}"/>
                    <c:remove var="errorMessage" scope="session"/>
                </c:if>
            </h3>
        </div>
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<jsp:include page="footer.jsp"/>