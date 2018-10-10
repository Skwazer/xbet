<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="change" var="change"/>
    <fmt:message key="matches" var="matchesTitle"/>
    <fmt:message key="match.date" var="matchDate"/>
    <fmt:message key="team1.id" var="team1ID"/>
    <fmt:message key="team2.id" var="team2ID"/>
    <fmt:message key="match.1" var="match1"/>
    <fmt:message key="match.X" var="matchX"/>
    <fmt:message key="match.2" var="match2"/>
    <fmt:message key="match.1X" var="match1X"/>
    <fmt:message key="match.12" var="match12"/>
    <fmt:message key="match.2X" var="match2X"/>

</fmt:bundle>


<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="billing-details">
                    <div class="section-title">
                        <c:choose>
                            <c:when test="${not empty matchesMessage}">
                                <h3 class="message-title">
                                    <fmt:bundle basename="i18n">
                                        <fmt:message key="${matchesMessage}" var="message"/>
                                    </fmt:bundle>
                                    <c:out value="${message}!"/>
                                    <c:remove var="matchesMessage" scope="session"/>
                                </h3>
                            </c:when>
                            <c:otherwise>
                                <h3 class="title"><c:out value="${matchesTitle}"/></h3>
                            </c:otherwise>
                        </c:choose>
                        <button class="primary-btn pull-right"><a href="<c:url value="/main/create/match"/>">
                            <c:out value="${create}"/></a></button>
                    </div>
                    <div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th><c:out value="${matchDate}"/></th>
                                <th><c:out value="${team1ID}"/></th>
                                <th><c:out value="${team2ID}"/></th>
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
                                    <td><c:out value="${match.id}"/></td>
                                    <td><c:out value="${match.date}"/></td>
                                    <td><c:out value="${match.team1_id}"/></td>
                                    <td><c:out value="${match.team2_id}"/></td>
                                    <td><c:out value="${match.victory1}"/></td>
                                    <td><c:out value="${match.draw}"/></td>
                                    <td><c:out value="${match.victory2}"/></td>
                                    <td><c:out value="${match.victory1OrDraw}"/></td>
                                    <td><c:out value="${match.victory1Or2}"/></td>
                                    <td><c:out value="${match.victory2OrDraw}"/></td>
                                    <td>
                                        <form class="clearfix" method="POST" action="<c:url value="/main/update/match"/>">
                                            <input type="hidden" name="key" value="${match.id}">
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
                            <li><a href=<c:url value="/main/get/matches?page=${currentPage-1}"/>>
                                <i class="fa fa-caret-left"></i></a></li>
                        </c:if>
                        <c:if test="${pages > 1}">
                            <c:forEach begin="1" end="${pages}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage == i}">
                                        <li class="active">${i}</li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href=<c:url value="/main/get/matches?page=${i}"/>>${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                        <c:if test="${currentPage < pages}">
                            <li><a href=<c:url value="/main/get/matches?page=${currentPage+1}"/>>
                                <i class="fa fa-caret-right"></i></a></li>
                        </c:if>
                    </ul>
                </div>
                <!-- /pagination -->
            </div>
        </div>
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<%@include file="../footer.jsp" %>