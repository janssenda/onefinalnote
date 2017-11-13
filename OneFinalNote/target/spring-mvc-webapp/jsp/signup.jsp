<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign up for OFN</title>
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
        <div class="col-sm-2 text-left" id="links-bar">
            <div class="row">
                <div class="col-12 text-center">
                    <div id="linktitle">Content</div>
                </div>
            </div>

            <div id="staticpagelinkdiv">
                <ul id="links-ul">
                    <c:forEach var="link" items="${pageLinks}">
                        <li class="staticpages" id="staticpage${link.key}">${link.value}</li>
                    </c:forEach>
                    <li class="staticpages" >Music</li>
                    <li class="staticpages" >About Me</li>
                </ul>
            </div>
        </div>
        <div class="col-sm-10 text-center center-offset">
            <h3> Sign up </h3>


            <div style="display: inline-block">
                <form method="POST" action="newuser">

                    <div class="row">
                        <div class="col-12">
                            <table>
                                <tbody>
                                    <tr><td class="text-right">Username:</td><td><input class="signform" required style="width: 90%" type="text" value="${username}" name="username"/></td></tr>
                                    <tr><td class="text-right">Password:</td><td><input class="signform" required style="width: 90%" type="password" name="password"/></td></tr>
                                    <tr><td class="text-right">Re-Enter Password:</td><td><input class="signform" required style="width: 90%" type="password" name="password-check"/></td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12 text-center"><br/>
                            <c:if test="${pwerr == 1}">
                                <span class="errmsg">${pwmismatcherror}</span><br/>
                            </c:if>
                            <c:if test="${userr == 1}">
                                <span class="errmsg">${userexistserror}</span><br/>
                            </c:if>
                            <button type="submit" value="create-account-button">Create Account</button>
                        </div>
                    </div>
                </form>


            </div>
        </div>

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

