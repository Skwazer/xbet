<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="change" var="change"/>
    <fmt:message key="match.id" var="matchIdTitle"/>
    <fmt:message key="result" var="resultTitle"/>
    <fmt:message key="team1.id" var="team1IdTitle"/>
    <fmt:message key="team2.id" var="team2IdTitle"/>
    <fmt:message key="team1.goals" var="team1GoalsTitle"/>
    <fmt:message key="team2.goals" var="team2GoalsTitle"/>

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
                                    <c:when test="${not empty resultsMessage}">
                                        <h3 class="message-title">
                                            <fmt:bundle basename="i18n">
                                                <fmt:message key="${resultsMessage}" var="message"/>
                                            </fmt:bundle>
                                            <c:out value="${message}!"/>
                                            <c:remove var="resultsMessage" scope="session"/>
                                        </h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3 class="title"><c:out value="${resultsTitle}"/></h3>
                                    </c:otherwise>
                                </c:choose>
                                <button class="primary-btn pull-right"><a href="<c:url value="/main/create/result"/>">
                                    <c:out value="${create}"/></a></button>
                            </div>
                            <div>
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th><c:out value="${matchIdTitle}"/></th>
                                        <th><c:out value="${resultTitle}"/></th>
                                        <th><c:out value="${team1IdTitle}"/></th>
                                        <th><c:out value="${team1GoalsTitle}"/></th>
                                        <th><c:out value="${team2IdTitle}"/></th>
                                        <th><c:out value="${team2GoalsTitle}"/></th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${resultsList}" var="result">
                                        <tr>
                                            <td><c:out value="${result.id}"/></td>
                                            <td><c:out value="${result.matchId}"/></td>
                                            <td><c:out value="${result.result}"/></td>
                                            <td><c:out value="${result.team1_id}"/></td>
                                            <td><c:out value="${result.team1_goals}"/></td>
                                            <td><c:out value="${result.team2_id}"/></td>
                                            <td><c:out value="${result.team2_goals}"/></td>
                                            <td>
                                                <form class="clearfix" method="POST" action="<c:url value="/main/update/result"/>">
                                                    <input type="hidden" name="key" value="${result.id}">
                                                    <button class="main-btn"><c:out value="${change}"/></button>
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
                                    <li><a href=<c:url value="/main/get/results?page=${currentPage-1}"/>>
                                        <i class="fa fa-caret-left"></i></a></li>
                                </c:if>
                                <c:if test="${pages > 1}">
                                    <c:forEach begin="1" end="${pages}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage == i}">
                                                <li class="active">${i}</li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href=<c:url value="/main/get/results?page=${i}"/>>${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${currentPage < pages}">
                                    <li><a href=<c:url value="/main/get/results?page=${currentPage+1}"/>>
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
                    <h2><c:out value="${adminError}"/></h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<%@include file="../footer.jsp" %>