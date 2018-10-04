<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="create.data" var="createData"/>
    <fmt:message key="admin.error" var="adminError"/>
    <fmt:message key="enter.login" var="enterLogin"/>
    <fmt:message key="enter.password" var="enterPassword"/>
    <fmt:message key="enter.firstname" var="enterFirstname"/>
    <fmt:message key="enter.lastname" var="enterLastname"/>
    <fmt:message key="enter.email" var="enterEmail"/>
    <fmt:message key="enter.balance" var="enterBalance"/>
    <fmt:message key="enter.role" var="enterRole"/>
    <fmt:message key="create" var="create"/>
    <fmt:message key="regexp" var="regexp"/>
    <fmt:message key="login.required" var="loginRequired"/>
    <fmt:message key="login.accepted" var="loginAccepted"/>
    <fmt:message key="login.exists" var="loginExists"/>
    <fmt:message key="login.whitespace" var="loginWhitespace"/>
    <fmt:message key="login.error" var="loginError"/>
    <fmt:message key="login.regexp" var="loginRegexp"/>
    <fmt:message key="firstname.required" var="firstNameRequired"/>
    <fmt:message key="firstname.whitespace" var="firstNameWhitespace"/>
    <fmt:message key="firstname.regexp" var="firstNameRegexp"/>
    <fmt:message key="firstname.accepted" var="firstNameAccepted"/>
    <fmt:message key="lastname.required" var="lastNameRequired"/>
    <fmt:message key="lastname.accepted" var="lastNameAccepted"/>
    <fmt:message key="lastname.regexp" var="lastNameRegexp"/>
    <fmt:message key="lastname.whitespace" var="lastNameWhitespace"/>
    <fmt:message key="email.accepted" var="emailAccepted"/>
    <fmt:message key="email.required" var="emailRequired"/>
    <fmt:message key="email.whitespace" var="emailWhitespace"/>
    <fmt:message key="email.regexp" var="emailRegexp"/>
    <fmt:message key="regexp.email" var="regexpEmail"/>
    <fmt:message key="balance.required" var="balanceRequired"/>
    <fmt:message key="balance.negative" var="balanceNegative"/>
    <fmt:message key="accepted" var="accepted"/>
    <fmt:message key="role.negative" var="roleNegative"/>
    <fmt:message key="role.required" var="roleRequired"/>
    <fmt:message key="role.excess" var="roleExcess"/>
    <fmt:message key="update" var="update"/>
    <fmt:message key="delete" var="delete"/>

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
                                    <label for="id">ID</label>
                                    <input id="id" class="input" name="id"
                                          value="<c:out value="${updateUser.id}"/>" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="login"><c:out value="${enterLogin}"/></label>
                                    <input id="login" class="input" name="login" onchange="checkLogin()"
                                          value="<c:out value="${updateUser.login}"/>" readonly>
                                    <div id="loginDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="firstName"><c:out value="${enterFirstname}"/></label>
                                    <input id="firstName" class="input" name="firstName"
                                           onchange="firstNameCheck()"
                                           value="<c:out value="${updateUser.firstName}"/>">
                                    <div id="firstNameDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="lastName"><c:out value="${enterLastname}"/></label>
                                    <input id="lastName" class="input" name="lastName"
                                           onchange="lastNameCheck()"
                                           value="<c:out value="${updateUser.lastName}"/>">
                                    <div id="lastNameDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="email"><c:out value="${enterEmail}"/></label>
                                    <input id="email" class="input" name="email" onchange="emailCheck()"
                                           value="<c:out value="${updateUser.email}"/>">
                                    <div id="emailDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="balance"><c:out value="${enterBalance}"/></label>
                                    <input id="balance" class="input" type="number" name="balance"  step="0.001"
                                           onchange="balanceCheck()"
                                           value="<c:out value="${updateUser.balance}"/>" readonly>
                                    <div id="balanceDiv" style="display:none;"></div>
                                </div>
                                <div class="form-group">
                                    <label for="select"><c:out value="${enterRole}"/></label>
                                    <select id="select" name="role" class="form-control no-radius" form="createForm">
                                        <c:choose>
                                            <c:when test="${updateUser.role eq 1}">
                                                <option value="1"><c:out value="1"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="roleId" items="${rolesIds}">
                                                    <option <c:if test="${roleId eq updateUser.role}">selected</c:if>
                                                            value="${roleId}">
                                                        <c:out value="${roleId}"/></option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </form>
                            <button class="primary-btn pull-left" onclick="changeFormAction()">
                                <c:out value="${update}"/></button>
                            <button form="deleteForm" class="primary-btn pull-right">
                                <c:out value="${delete}"/></button>
                            <form id="deleteForm" method="post" action="<c:url value="/main/delete/user"/>">
                                <input type="hidden" name="key" value="${updateUser.id}">
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
    <%@include file="../../../js/update-user.js" %>
</script>



<%@include file="../footer.jsp" %>