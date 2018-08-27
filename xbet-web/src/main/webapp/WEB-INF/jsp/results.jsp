<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="header.jsp" %>

<fmt:bundle basename="i18n">
    <fmt:message key="match.date" var="matchDateTitle"/>
    <fmt:message key="result" var="resultTitle"/>
    <fmt:message key="match.team1" var="team1Title"/>
    <fmt:message key="match.team2" var="team2Title"/>
    <fmt:message key="match.score" var="matchScore"/>
    <fmt:message key="match.1" var="match1"/>
    <fmt:message key="match.X" var="matchX"/>
    <fmt:message key="match.2" var="match2"/>

</fmt:bundle>


<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="billing-details">
                    <div class="section-title">
                        <h3 class="title"><c:out value="${resultsTitle}"/></h3>
                    </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th><c:out value="${matchDateTitle}"/></th>
                                <th><c:out value="${team1Title}"/></th>
                                <th><c:out value="${matchScore}"/></th>
                                <th><c:out value="${team2Title}"/></th>
                                <th><c:out value="${resultTitle}"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${resultsList}" var="result">
                                <tr>
                                    <td><c:out value="${result.match.date}"/></td>
                                    <td><c:out value="${result.match.team1.name}"/></td>
                                    <td><c:out value="${result.team1_goals} : ${result.team2_goals}"/></td>
                                    <td><c:out value="${result.match.team2.name}"/></td>
                                    <td><c:out value="${result.result}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                </div>
                <div class="small text-center">
                    <p id="explanations">
                        <strong>1</strong> - <c:out value="${match1}"/>
                        <strong>X</strong> - <c:out value="${matchX}"/>
                        <strong>2</strong> - <c:out value="${match2}"/>
                    </p>
                </div>
                <!-- pagination -->
                <div class="text-center">
                    <ul class="store-pages">
                        <c:if test="${currentPage > 1}">
                            <li><a href=<c:url value="/main/results?page=${currentPage-1}"/>>
                                <i class="fa fa-caret-left"></i></a></li>
                        </c:if>
                        <c:if test="${pages > 1}">
                            <c:forEach begin="1" end="${pages}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage == i}">
                                        <li class="active">${i}</li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href=<c:url value="/main/results?page=${i}"/>>${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                        <c:if test="${currentPage < pages}">
                            <li><a href=<c:url value="/main/results?page=${currentPage+1}"/>>
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

<%@include file="footer.jsp" %>