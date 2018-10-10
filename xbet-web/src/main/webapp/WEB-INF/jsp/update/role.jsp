<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="enter.role" var="enterRole"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="role.required" var="roleRequired"/>
    <fmt:message key="update" var="update"/>
    <fmt:message key="delete" var="delete"/>
    <fmt:message key="role.regexp" var="roleRegexp"/>
    <fmt:message key="regexp.error" var="regexpError"/>
</fmt:bundle>


<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="billing-details">
                    <div class="section-title">
                        <h3 class="title"><c:out value="${createData}"/></h3>
                    </div>
                    <form id="updateForm" class="clearfix" method="post" action="#">
                        <div class="form-group">
                            <label for="id">ID</label>
                            <input id="id" class="input" name="id"
                                   value="<c:out value="${updateRole.id}"/>" readonly>
                        </div>
                        <div class="form-group">
                            <label for="role"><c:out value="${enterRole}"/></label>
                            <input id="role" class="input" name="role" onchange="roleCheck()"
                                   value="<c:out value="${updateRole.role}"/>">
                            <div id="roleDiv" style="display:none;"></div>
                        </div>
                    </form>
                    <button class="primary-btn pull-left" onclick="changeFormAction()">
                        <c:out value="${update}"/></button>
                    <button form="deleteForm" class="primary-btn pull-right">
                        <c:out value="${delete}"/></button>
                    <form id="deleteForm" method="post" action="<c:url value="/main/delete/role"/>">
                        <input type="hidden" name="key" value="${updateRole.id}">
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- /container -->
</div>
<!-- /section -->



<script type="text/javascript" charset="UTF-8">
    <%@include file="../../../js/update-role.js" %>
</script>



<%@include file="../footer.jsp" %>