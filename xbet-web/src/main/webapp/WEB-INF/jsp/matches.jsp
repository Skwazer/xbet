<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="matches" var="matchesTitle"/>
    <fmt:message key="match.date" var="matchDate"/>
    <fmt:message key="match.team1" var="matchTeam1"/>
    <fmt:message key="match.team2" var="matchTeam2"/>
    <fmt:message key="match.1" var="match1"/>
    <fmt:message key="match.X" var="matchX"/>
    <fmt:message key="match.2" var="match2"/>
    <fmt:message key="match.1X" var="match1X"/>
    <fmt:message key="match.12" var="match12"/>
    <fmt:message key="match.2X" var="match2X"/>
    <fmt:message key="bet" var="bet"/>
    <fmt:message key="matches.log.in" var="matchesLogIn"/>
    <fmt:message key="login.error" var="loginError"/>
    <fmt:message key="match.finish" var="finishMatch"/>
    <fmt:message key="matches.list.empty" var="matchesListEmpty"/>

</fmt:bundle>


<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <c:choose>
            <c:when test="${not empty matchesMessage}">
                <div class="text-center">
                    <h2><c:out value="${matchesListEmpty}"/></h2>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                        <div class="billing-details">
                            <div class="section-title">
                                <h3 class="title"><c:out value="${matchesTitle}"/></h3>
                            </div>
                            <div>
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <c:if test="${not empty user}">
                                            <c:if test="${user.role eq 1}">
                                                <td></td>
                                            </c:if>
                                        </c:if>
                                        <th><c:out value="${matchDate}"/></th>
                                        <th><c:out value="${matchTeam1}"/></th>
                                        <th><c:out value="${matchTeam2}"/></th>
                                        <th title="<c:out value="${match1}"/>">1</th>
                                        <th title="<c:out value="${matchX}"/>">X</th>
                                        <th title="<c:out value="${match2}"/>">2</th>
                                        <th title="<c:out value="${match1X}"/>">1X</th>
                                        <th title="<c:out value="${match12}"/>">12</th>
                                        <th title="<c:out value="${match2X}"/>">2X</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${matchesList}" var="match">
                                        <tr>
                                            <c:if test="${user.role eq 1}">
                                                <td>
                                                    <form class="clearfix" method="post"
                                                          action="<c:url value="/main/matches"/>">
                                                        <input type="hidden" name="matchId" value="${match.id}">
                                                        <button class="btn main-btn">
                                                            <c:out value="${finishMatch}"/></button>
                                                    </form>
                                                </td>
                                            </c:if>
                                            <td><c:out value="${match.date}"/></td>
                                            <td><c:out value="${match.team1.name}"/></td>
                                            <td><c:out value="${match.team2.name}"/></td>
                                            <td><c:out value="${match.victory1}"/></td>
                                            <td><c:out value="${match.draw}"/></td>
                                            <td><c:out value="${match.victory2}"/></td>
                                            <td><c:out value="${match.victory1OrDraw}"/></td>
                                            <td><c:out value="${match.victory1Or2}"/></td>
                                            <td><c:out value="${match.victory2OrDraw}"/></td>
                                            <td>
                                                <button id="btn" class="btn primary-btn"
                                                        onclick="isUserLoggedIn('betForm${match.id}')">
                                                    <c:out value="${bet}"/>
                                                </button>
                                                <form id="betForm${match.id}" class="clearfix" method="POST">
                                                    <input type="hidden" name="matchId" value="${match.id}">
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- pagination -->
                        <div class="text-center">
                            <ul class="store-pages">
                                <c:if test="${currentPage > 1}">
                                    <li><a href=<c:url value="/main/matches?page=${currentPage-1}"/>>
                                        <i class="fa fa-caret-left"></i></a></li>
                                </c:if>
                                <c:if test="${pages > 1}">
                                    <c:forEach begin="1" end="${pages}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage == i}">
                                                <li class="active">${i}</li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href=<c:url value="/main/matches?page=${i}"/>>${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${currentPage < pages}">
                                    <li><a href=<c:url value="/main/matches?page=${currentPage+1}"/>>
                                        <i class="fa fa-caret-right"></i></a></li>
                                </c:if>
                            </ul>
                        </div>
                        <!-- /pagination -->
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<script>
    <%@include file="../../js/matches.js"%>
</script>

<%@include file="footer.jsp" %>