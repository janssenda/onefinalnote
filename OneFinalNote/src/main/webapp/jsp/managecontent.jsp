<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://ofn.com/functions" prefix="cf" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>OFN Search</title>
    <!-- Bootstrap core CSS -->
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link href="./css/main.css" rel="stylesheet">
    <link href="./css/forms.css" rel="stylesheet">
</head>
<body>
<div class="container" id="page">

    <sec:authorize access="hasRole('ROLE_OWNER')">
        <input type="hidden" value="owner" id="sec"/>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <input type="hidden" value="admin" id="sec"/>
    </sec:authorize>
        <input type="hidden" value="${pageContext.request.userPrincipal.name}" id="userid"/>

    <hr/>
    <div class="row" id="title-row">
        <div class="col-sm-6 text-left" id="title-col-left">

            <ul id="m" class="nav nav-pills" style="display: inline-block; width:50px">
                <li id="menudrop" class="dropdown">
                    <a data-toggle="dropdown" href="#"><img id="menu-button" src="./images/ofn-menu.png"/></a>
                    <ul class="dropdown-menu" id="linksdropdown">
                        <li><a class="hlink" href="./">Home</a></li>
                        <li class="dropdown-divider"></li>
                        <sec:authorize access="isAnonymous()">
                            <li><a class="hlink" href="./signup">Sign Up</a></li>
                        </sec:authorize>
                        <li><a class="hlink" href="./search">Search</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li class="dropdown-divider"></li>
                            <li><a class="hlink" href="./createcontent">New Post</a></li>
                            <li><a class="hlink" href="./accounts">Accounts</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </ul>
            <span id="title"><img src="./images/logo.png" alt="One Final Note"></span>&nbsp;

        </div>
        <div class="col-sm-6 text-right" id="title-col">
            <br/>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <c:if test="${param.login_error == 1}">
                    <span class="errmsg"> Wrong id or password!</span><br/>
                </c:if>
                <form role="form" action="j_spring_security_check" style="display: inline" method="post">
                    <input class="login-form" style="width:20%" name="j_username" placeholder="Username"/>&nbsp;
                    <input class="login-form" style="width:20%" type="password" placeholder="Password "
                           name="j_password"/>&nbsp;
                    <button type="submit">Login</button>
                </form>
                <form role="form" style="display: inline" action="signup" method="get">
                    <button type="submit">Sign up</button>
                </form>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <div id="welcomediv">

                    <span id="hello">${pageContext.request.userPrincipal.name}</span>
                    | <a class="hlink" href="<c:url value="/j_spring_security_logout" />">Logout</a>

                </div>
            </c:if>
        </div>
    </div>
    <hr/>
    <div class="col-lg-12 text-center">
        <form>
            &nbsp;
            <button type="button" id="searchbutton">Search</button>&nbsp;
            <input type="text" id="searchterms" placeholder="Search for..">
            <input type="date" id="searchdate" style="display: none">
            <select class="category-select" style="display: none" id="cat-select">
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.categoryID}">${cat.categoryName}</option>
                </c:forEach>
            </select>
            &nbsp;
            <select class="search-select" id="category" >
                <option value="blog" selected>Blog Posts</option>
                <option value="page">Pages</option>
            </select>
            &nbsp;<div class="spc">by</div>&nbsp;
            <select class="search-select" id="subcategory">
                <option value="general" selected>General</option>
                <option class = "postopt" value="category">Category</option>
                <option class = "postopt" value="title">Title</option>
                <option class = "postopt" value="date">Date</option>
                <option class = "postopt" value="tag">Tags</option>
                <option class = "postopt" value="id">Content ID</option>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <option class = "postopt" value="userid">User ID</option>
                </sec:authorize>
            </select>
            &nbsp;&nbsp;
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <select class="search-select" id="state">
                <option value="published" selected>Published</option>
                <option value="unpublished">Unpublished</option>
                <option value="all">All</option>
            </select>
            </sec:authorize>
        </form>
    </div>
<br/><br/>
    <div class="row">
        <div class="col-lg-12 text-center" id="resultsdiv">
        </div>
    </div>

</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/tether.min.js"></script>
<script src="./js/moment.min.js"></script>
<script src="./js/jquery-3.2.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/ofn.js"></script>
<script src="./js/search.js"></script>


</body>
</html>

