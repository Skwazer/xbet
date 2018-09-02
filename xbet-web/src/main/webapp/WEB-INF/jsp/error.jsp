<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"/>

<fmt:bundle basename="i18n">
    <fmt:message key="error" var="error"/>
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