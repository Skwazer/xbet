<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="matches.log.in" var="matchesLogIn"/>
    <fmt:message key="enter.user.id" var="userIDTitle"/>
    <fmt:message key="enter.match.id" var="matchIDTitle"/>
    <fmt:message key="coefficient" var="coefficient"/>
    <fmt:message key="bet" var="betTitle"/>
    <fmt:message key="bets.empty" var="betsEmpty"/>
    <fmt:message key="status" var="status"/>
    <fmt:message key="bet.result" var="betResult"/>
    <fmt:message key="change" var="change"/>

</fmt:bundle>

<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <c:choose>
            <c:when test="${user.role eq 1}">
                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <div class="billing-details">
                            <div class="section-title">
                                <h3 class="title"><c:out value="${betsTitle}"/></h3>
                            </div>
                            <c:choose>
                                <c:when test="${not empty allBets}">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th><c:out value="${userIDTitle}"/></th>
                                            <th><c:out value="${matchIDTitle}"/></th>
                                            <th><c:out value="${betResult}"/></th>
                                            <th><c:out value="${coefficient}"/></th>
                                            <th><c:out value="${betTitle}"/></th>
                                            <th><c:out value="${status}"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${allBets}" var="bet">
                                            <tr>
                                                <td><c:out value="${bet.id}"/></td>
                                                <td><c:out value="${bet.user_id}"/></td>
                                                <td><c:out value="${bet.match_id}"/></td>
                                                <td><c:out value="${bet.betResult}"/></td>
                                                <td><c:out value="${bet.bet}"/></td>
                                                <td><c:out value="${bet.money}"/></td>
                                                <td><c:out value="${bet.status}"/></td>
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