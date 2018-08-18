<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="header.jsp"%>


<!-- HOME -->
<div id="home">
    <!-- container -->
    <div class="container">
        <!-- home wrap -->
        <div class="home-wrap">
            <!-- home slick -->
            <div id="home-slick">
                <!-- banner -->
                <div class="banner banner-1">
                    <img src="<c:url value="/img/football1.jpg"/>" alt="football">
                    <div class="banner-caption text-center">
                        <h1 class="primary-color">X-BET</h1>
                        <h2 class="white-color font-weak"><c:out value="${xbet}"/></h2>
                        <br/>
                        <button class="primary-btn">
                            <a href="<c:url value="/main/matches"/>"><c:out value="${matches}"/></a></button>
                    </div>
                </div>
                <!-- /banner -->

                <!-- banner -->
                <div class="banner banner-1">
                    <img src="<c:url value="/img/football2.jpg"/>" alt="football">
                    <div class="banner-caption text-center">
                        <h1 class="primary-color">X-BET</h1>
                        <h2 class="white-color font-weak"><c:out value="${xbet}"/></h2>
                        <br/>
                        <button class="primary-btn">
                            <a href="<c:url value="/main/matches"/>"><c:out value="${matches}"/></a></button>
                    </div>
                </div>
                <!-- /banner -->

                <!-- banner -->
                <div class="banner banner-1">
                    <img src="<c:url value="/img/football3.jpg"/>" alt="football">
                    <div class="banner-caption text-center">
                        <h1 class="primary-color">X-BET</h1>
                        <h2 class="white-color font-weak"><c:out value="${xbet}"/></h2>
                        <br/>
                        <button class="primary-btn">
                            <a href="<c:url value="/main/matches"/>"><c:out value="${matches}"/></a></button>
                    </div>
                </div>
                <!-- /banner -->

                <!-- banner -->
                <div class="banner banner-1">
                    <img src="<c:url value="/img/football4.jpg"/>" alt="football">
                    <div class="banner-caption text-center">
                        <h1 class="primary-color">X-BET</h1>
                        <h2 class="white-color font-weak"><c:out value="${xbet}"/></h2>
                        <br/>
                        <button class="primary-btn">
                            <a href="<c:url value="/main/matches"/>"><c:out value="${matches}"/></a></button>
                    </div>
                </div>
                <!-- /banner -->
            </div>
            <!-- /home slick -->
        </div>
        <!-- /home wrap -->
    </div>
    <!-- /container -->
</div>
<!-- /HOME -->


<%@include file="footer.jsp"%>
