<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="enter.role" var="enterRole"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="role.required" var="roleRequired"/>
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
                    <form id="createForm" class="clearfix" method="post" action="#">
                        <div class="form-group">
                            <input id="role" class="input" name="role" onchange="roleCheck()"
                                   placeholder="<c:out value="${enterRole}"/>">
                            <div id="roleDiv" style="display:none;"></div>
                        </div>
                    </form>
                    <button id="createButton" class="primary-btn pull-left"
                            onclick="changeFormAction()"><c:out value="${create}"/></button>
                </div>
            </div>
        </div>
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<script type="text/javascript" charset="UTF-8">
    <%@include file="../../../js/create-role.js" %>
</script>

<%@include file="../footer.jsp" %>