<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="regex.error" var="regexError"/>
    <fmt:message key="name" var="enterName"/>
    <fmt:message key="team.regexp" var="teamRegexp"/>
    <fmt:message key="name.required" var="nameRequired"/>

</fmt:bundle>


<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <c:choose>
            <c:when test="${user.role eq 1}">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="billing-details">
                            <div class="section-title">
                                <h3 class="title"><c:out value="${createData}"/></h3>
                            </div>
                            <form id="createForm" class="clearfix" method="post" action="#">
                                <div class="form-group">
                                    <input id="name" class="input" name="name" onchange="nameCheck()"
                                           placeholder="<c:out value="${enterName}"/>">
                                    <div id="nameDiv" style="display:none;"></div>
                                </div>
                            </form>
                            <button id="createButton" class="primary-btn pull-left"
                                    onclick="changeFormAction()"><c:out value="${create}"/></button>
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

<script type="text/javascript" charset="UTF-8">
    <%@include file="../../../js/create-team.js" %>
</script>

<%@include file="../footer.jsp" %>