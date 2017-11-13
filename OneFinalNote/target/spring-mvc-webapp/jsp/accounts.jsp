<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Accounts</title>
    <!-- Bootstrap core CSS -->
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link href="./css/main.css" rel="stylesheet">
    <link href="./css/forms.css" rel="stylesheet">
</head>
<body>
<div class="container" id="page">


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


    <div class="row">
        <%--<div class="col-sm-2 text-left" id="links-bar">--%>
            <%--<div class="row">--%>
                <%--<div class="col-12 text-center">--%>
                    <%--<div id="linktitle">Content</div>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<div id="staticpagelinkdiv">--%>
                <%--<ul id="links-ul">--%>
                    <%--<li class="staticpages">View Users</li>--%>
                    <%--<li class="staticpages">Search Users</li>--%>
                <%--</ul>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="col-sm-12 text-center">
            <div class="row">
                <div class="col-12">
                    <h2>Manage Users</h2>
                </div>
            </div>
            <br/>


            <div style="display: inline-block; width: 90%">

                <c:forEach var="user" items="${users}">
                    <c:if test="${user.userName != pageContext.request.userPrincipal.name}">
                        <c:set var="isAdminLoggedIn" value="False"/>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <c:set var="isAdminLoggedIn" value="True"/>
                        </sec:authorize>
                        <c:set var="isOwnerLoggedIn" value="False"/>
                        <sec:authorize access="hasRole('ROLE_OWNER')">
                            <c:set var="isOwnerLoggedIn" value="True"/>
                            <c:set var="isAdminLoggedIn" value="True"/>
                        </sec:authorize>
                        <c:set var="userLoggedIn" value="${pageContext.request.userPrincipal.name}"/>
                        <c:if test="${(userLoggedIn == 'owner') || (isOwnerLoggedIn && user.userName != 'owner'
                                                                        && user.authorities[0] != 'ROLE_OWNER')
                         || (isAdminLoggedIn && user.authorities[0] != 'ROLE_OWNER')}">
                            <div class="user-role-div text-left">
                                <form action="./manageuser?userid=${user.userId}" method="post">
                                    <input type="hidden" name="isOwnerLoggedIn" value="${isOwnerLoggedIn}"/>
                                    <input type="hidden" name="isAdminLoggedIn" value="${isAdminLoggedIn}"/>
                                    <input type="hidden" name="userLoggedIn" value="${userLoggedIn}"/>
                                    <div class="row">
                                        <div class="col-sm-7">
                                            Username: <span class="edit-username-font"><c:out
                                                value="${user.userName}"/></span><br/>
                                            Authorities:
                                            <c:forEach var="auth" items="${user.authorities}" varStatus="status">
                                                ${auth}<c:if test="${not status.last}">, </c:if>
                                            </c:forEach>
                                        </div>
                                        <div class="col-sm-5 text-right" style="margin-top: -7px">
                                            <input name="enabledbox" class="ebox" value="true" type="checkbox"
                                                   <c:if test="${user.isEnabled}">checked="checked"</c:if>
                                                   style="margin-bottom: 2px"/>
                                            Enabled<br/>
                                            <sec:authorize access="hasRole('ROLE_OWNER')">
                                                <select name="roleselect" class="roleselect">
                                                    <option value="false">Change Role</option>
                                                    <option value="owner">Owner</option>
                                                    <option value="admin">Admin</option>
                                                    <option value="user">User</option>
                                                </select>
                                                <button class="editbutton" type="submit" name="editbutton"
                                                        value="delete">
                                                    Delete
                                                </button>
                                            </sec:authorize>
                                            <button class="editbutton" type="submit" name="editbutton" value="update">
                                                Update
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <br/>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>


    </div>
</div>
<script src="./js/tether.min.js"></script>
<script src="./js/moment.min.js"></script>
<script src="./js/jquery-3.2.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/ofn.js"></script>
</body>
</html>

