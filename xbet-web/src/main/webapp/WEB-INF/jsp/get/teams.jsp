<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="change" var="change"/>
    <fmt:message key="name" var="teamName"/>

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
                                <c:choose>
                                    <c:when test="${not empty teamsMessage}">
                                        <h3 class="message-title">
                                            <fmt:bundle basename="i18n">
                                                <fmt:message key="${teamsMessage}" var="message"/>
                                            </fmt:bundle>
                                            <c:out value="${message}!"/>
                                            <c:remove var="teamsMessage" scope="session"/>
                                        </h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3 class="title"><c:out value="${teamsTitle}"/></h3>
                                    </c:otherwise>
                                </c:choose>
                                <button class="primary-btn pull-right"><a href="<c:url value="/main/create/team"/>">
                                    <c:out value="${create}"/></a></button>
                            </div>
                            <div>
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th><c:out value="${teamName}"/></th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${teamsList}" var="team">
                                        <tr>
                                            <td><c:out value="${team.id}"/></td>
                                            <td><c:out value="${team.name}"/></td>
                                            <td>
                                                <form class="clearfix" method="POST" action="<c:url value="/main/update/team"/>">
                                                    <input type="hidden" name="key" value="${team.id}">
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
                                    <li><a href=<c:url value="/main/get/teams?page=${currentPage-1}"/>>
                                        <i class="fa fa-caret-left"></i></a></li>
                                </c:if>
                                <c:if test="${pages > 1}">
                                    <c:forEach begin="1" end="${pages}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage == i}">
                                                <li class="active">${i}</li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href=<c:url value="/main/get/teams?page=${i}"/>>${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${currentPage < pages}">
                                    <li><a href=<c:url value="/main/get/teams?page=${currentPage+1}"/>>
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