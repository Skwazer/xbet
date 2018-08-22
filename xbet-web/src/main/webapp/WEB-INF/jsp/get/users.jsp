<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="enter.login" var="enterLogin"/>
    <fmt:message key="enter.password" var="enterPassword"/>
    <fmt:message key="enter.firstname" var="enterFirstname"/>
    <fmt:message key="enter.lastname" var="enterLastname"/>
    <fmt:message key="enter.email" var="enterEmail"/>
    <fmt:message key="enter.balance" var="enterBalance"/>
    <fmt:message key="enter.role" var="enterRole"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="change" var="change"/>
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
                                    <c:when test="${not empty userMessage}">
                                            <h3 class="message-title">
                                                <fmt:bundle basename="i18n">
                                                    <fmt:message key="${userMessage}" var="message"/>
                                                </fmt:bundle>
                                                <c:out value="${message}!"/>
                                                <c:remove var="userMessage" scope="session"/>
                                            </h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3 class="title"><c:out value="${usersTitle}"/></h3>
                                    </c:otherwise>
                                </c:choose>

                                <button class="primary-btn pull-right"><a href="<c:url value="/main/create/user"/>">
                                    <c:out value="${create}"/></a></button>
                            </div>
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th><c:out value="ID"/></th>
                                    <th><c:out value="${enterLogin}"/></th>
                                    <th><c:out value="${enterPassword}"/></th>
                                    <th><c:out value="${enterFirstname}"/></th>
                                    <th><c:out value="${enterLastname}"/></th>
                                    <th><c:out value="${enterEmail}"/></th>
                                    <th><c:out value="${enterBalance}"/></th>
                                    <th><c:out value="${enterRole}"/></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${users}" var="user">
                                    <tr>
                                        <td><c:out value="${user.id}"/></td>
                                        <td><c:out value="${user.login}"/></td>
                                        <td><c:out value="${user.password}"/></td>
                                        <td><c:out value="${user.firstName}"/></td>
                                        <td><c:out value="${user.lastName}"/></td>
                                        <td><c:out value="${user.email}"/></td>
                                        <td>
                                            <fmt:formatNumber value="${user.balance}" var="balance" maxFractionDigits="2"/>
                                            <c:out value="${balance}"/>
                                        </td>
                                        <td><c:out value="${user.role}"/></td>
                                        <td>
                                            <form class="clearfix" method="POST" action="<c:url value="/main/update/user"/>">
                                                <input type="hidden" name="key" value="${user.id}">
                                                <button class="main-btn"><c:out value="${change}"/></button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- pagination -->
                        <div class="text-center">
                            <ul class="store-pages">
                                <c:if test="${currentPage > 1}">
                                    <li><a href=<c:url value="/main/get/users?page=${currentPage-1}"/>>
                                        <i class="fa fa-caret-left"></i></a></li>
                                </c:if>
                                <c:if test="${pages > 1}">
                                    <c:forEach begin="1" end="${pages}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage == i}">
                                                <li class="active">${i}</li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href=<c:url value="/main/get/users?page=${i}"/>>${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${currentPage < pages}">
                                    <li><a href=<c:url value="/main/get/users?page=${currentPage+1}"/>>
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