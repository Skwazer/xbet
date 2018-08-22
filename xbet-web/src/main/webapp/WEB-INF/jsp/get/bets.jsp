<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="matches.log.in" var="matchesLogIn"/>
    <fmt:message key="match.date" var="matchDate"/>
    <fmt:message key="match.team1" var="matchTeam1"/>
    <fmt:message key="match.team2" var="matchTeam2"/>
    <fmt:message key="coefficient" var="coefficient"/>
    <fmt:message key="bet" var="betTitle"/>
    <fmt:message key="bets.empty" var="betsEmpty"/>
    <fmt:message key="status" var="status"/>
    <fmt:message key="bet.result" var="betResult"/>
    <fmt:message key="change" var="change"/>
    <fmt:message key="create" var="create"/>

</fmt:bundle>

<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <c:choose>
            <c:when test="${user.role eq 1}">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <div class="billing-details">
                            <div class="section-title">
                                <c:choose>
                                    <c:when test="${not empty betsMessage}">
                                        <h3 class="message-title">
                                            <fmt:bundle basename="i18n">
                                                <fmt:message key="${betsMessage}" var="message"/>
                                            </fmt:bundle>
                                            <c:out value="${message}!"/>
                                            <c:remove var="betsMessage" scope="session"/>
                                        </h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3 class="title"><c:out value="${betsTitle}"/></h3>
                                    </c:otherwise>
                                </c:choose>
                                <button class="primary-btn pull-right"><a href="<c:url value="/main/create/bet"/>">
                                    <c:out value="${create}"/></a></button>
                            </div>
                            <c:choose>
                                <c:when test="${not empty allBets}">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th><c:out value="${matchDate}"/></th>
                                            <th><c:out value="${matchTeam1}"/></th>
                                            <th><c:out value="${matchTeam2}"/></th>
                                            <th><c:out value="${betResult}"/></th>
                                            <th><c:out value="${coefficient}"/></th>
                                            <th><c:out value="${betTitle}"/></th>
                                            <th><c:out value="${status}"/></th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${allBets}" var="bet">
                                            <tr>
                                                <td><c:out value="${bet.id}"/></td>
                                                <td><c:out value="${bet.match.date}"/></td>
                                                <td><c:out value="${bet.match.team1.name}"/></td>
                                                <td><c:out value="${bet.match.team2.name}"/></td>
                                                <td><c:out value="${bet.betResult}"/></td>
                                                <td><c:out value="${bet.bet}"/></td>
                                                <td><c:out value="${bet.money}"/></td>
                                                <td><c:out value="${bet.status}"/></td>
                                                <td>
                                                    <form class="clearfix" method="POST"
                                                          action="<c:url value="/main/update/bet"/>">
                                                        <input type="hidden" name="key" value="${bet.id}">
                                                        <button class="main-btn pull-right"><c:out value="${change}"/></button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center">
                                        <h2><c:out value="${betsEmpty}"/></h2>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <!-- pagination -->
                        <div class="text-center">
                            <ul class="store-pages">
                                <c:if test="${currentPage > 1}">
                                    <li><a href=<c:url value="/main/get/bets?page=${currentPage-1}"/>>
                                        <i class="fa fa-caret-left"></i></a></li>
                                </c:if>
                                <c:if test="${pages > 1}">
                                    <c:forEach begin="1" end="${pages}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage == i}">
                                                <li class="active">${i}</li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href=<c:url value="/main/get/bets?page=${i}"/>>${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${currentPage < pages}">
                                    <li><a href=<c:url value="/main/get/bets?page=${currentPage+1}"/>>
                                        <i class="fa fa-caret-right"></i></a></li>
                                </c:if>
                            </ul>
                        </div>
                        <!-- /pagination -->
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="text-center">
                    <h2><c:out value="${matchesLogIn}"/></h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <!-- /container -->
</div>
<!-- /section -->


<%@include file="../footer.jsp" %>