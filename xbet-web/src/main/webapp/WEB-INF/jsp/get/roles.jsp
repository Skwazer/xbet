<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
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
                    <div class="col-md-6 col-md-offset-3">
                        <div class="billing-details">
                            <div class="section-title">
                                <c:choose>
                                    <c:when test="${not empty roleMessage}">
                                        <h3 class="message-title">
                                            <fmt:bundle basename="i18n">
                                                <fmt:message key="${roleMessage}" var="message"/>
                                            </fmt:bundle>
                                            <c:out value="${message}!"/>
                                            <c:remove var="roleMessage" scope="session"/>
                                        </h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3 class="title"><c:out value="${rolesTitle}"/></h3>
                                    </c:otherwise>
                                </c:choose>
                                <button class="primary-btn pull-right"><a href="<c:url value="/main/create/role"/>">
                                    <c:out value="${create}"/></a></button>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 col-md-offset-4">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th><c:out value="ID"/></th>
                                    <th><c:out value="${enterRole}"/></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${roles}" var="role">
                                    <tr>
                                        <td><c:out value="${role.id}"/></td>
                                        <td><c:out value="${role.role}"/></td>
                                        <td>
                                            <c:if test="${role.id ne 1}">
                                                <form class="clearfix" method="POST"
                                                      action="<c:url value="/main/update/role"/>">
                                                    <input type="hidden" name="key" value="${role.id}">
                                                    <button class="main-btn pull-right"><c:out value="${change}"/></button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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


<%@include file="../footer.jsp" %>