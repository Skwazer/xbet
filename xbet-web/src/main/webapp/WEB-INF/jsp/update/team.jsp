<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="update" var="update"/>
    <fmt:message key="delete" var="delete"/>
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
                            <form id="updateForm" class="clearfix" method="post"
                                  action="<c:url value="/main/change/team"/> ">
                                <div class="form-group">
                                    <label for="id">ID</label>
                                    <input id="id" class="input" name="id"
                                           value="<c:out value="${updateTeam.id}"/>" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="name"><c:out value="${enterName}"/></label>
                                    <input id="name" class="input" name="name" onchange="nameCheck()"
                                           value="${updateTeam.name}">
                                    <div id="nameDiv" style="display:none;"></div>
                                </div>
                                <button class="primary-btn pull-left"><c:out value="${update}"/></button>
                                <button form="deleteForm" class="primary-btn pull-right">
                                    <c:out value="${delete}"/></button>
                            </form>
                            <form id="deleteForm" method="post" action="<c:url value="/main/delete/team"/>">
                                <input type="hidden" name="key" value="${updateTeam.id}">
                            </form>
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
    <%@include file="../../../js/update-team.js" %>
</script>



<%@include file="../footer.jsp" %>