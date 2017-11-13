<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://ofn.com/functions" prefix="cf" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Content</title>
    <!-- Bootstrap core CSS -->
    <link href="./css/bootstrap.min.css" rel="stylesheet">

    <!-- Include external CSS. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">
    <!-- Include Editor style. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_editor.pkgd.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_style.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="./css/main.css" rel="stylesheet" type="text/css">
    <link href="./css/forms.css" rel="stylesheet" type="text/css">
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


    <c:set var="isApprovalNeeded" value="true"/>
    <sec:authorize access="hasRole('ROLE_OWNER')">
        <c:set var="isApprovalNeeded" value="false"/>
    </sec:authorize>
    <c:choose>
    <c:when test="${newcontent == 'true' || pageContext.request.userPrincipal.name == 'owner' ||
        (pageContext.request.userPrincipal.name == author)}">

        <%--<c:out value="Last updated: ${cf:formatLocalDateTime(blog.updateTime, 'dd.MM.yyyy hh:mm')}"/>--%>

        <form action="save" method="POST">
            <input type="hidden" name="contentID" value="${contentID}"/>
            <input type="hidden" name="isApprovalNeeded" value="${isApprovalNeeded}"/>
            <input type="hidden" name="userLoggedIn"
                   value="${pageContext.request.userPrincipal.name}"/>
            <div class="row">
                <div class="col-sm-3" id="links-bar">
                    <div class="row">
                        <div class="col-12 text-center">
                            <div id="linktitle">Properties</div>
                        </div>
                    </div>
                    <div id="contentformlinks">
                        <table id="contentform-table">
                            <tbody>
                            <tr>
                                <td class="form-left">Title:</td>
                                <td class="form-right">
                                    <input value="${title}" class="contentform" type="text" placeholder="Title"
                                           name="newBlogPostTitle" id="newBlogPostTitle" required/></td>
                            </tr>


                            <tr class="form-hide">
                                <td class="form-left">Category:</td>
                                <td class="form-right">
                                    <select class="contentform" name="categorySelector" id="categorySelector" required>
                                        <c:forEach var="cat" items="${categories}">
                                            <option value="${cat.categoryID}"
                                                    <c:if test="${catID == 1}">
                                                        selected</c:if>
                                            >${cat.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr class="form-hide">
                                <td class="form-left">Start Date:</td>
                                <td class="form-right">
                                    <input value="${startDate}" class="contentform" type="datetime-local"
                                           name="startDateSelector" id="startDateSelector"/></td>
                            </tr>
                            <tr class="form-hide">
                                <td class="form-left">End Date:</td>
                                <td class="form-right">
                                    <input value="${endDate}" class="contentform" type="datetime-local"
                                           name="endDateSelector" id="endDateSelector"/></td>
                            </tr>
                            <tr>


                                <td class="form-left">Post Type:</td>
                                <td class="form-right">
                                    Blog <input class="ebox" type="radio" value="blog" name="typeRadio" checked>&nbsp;&nbsp;
                                    <sec:authorize access="hasRole('ROLE_OWNER')">
                                        Page <input class="ebox" type="radio" value="page" name="typeRadio">
                                    </sec:authorize>

                                </td>
                            </tr>
                            <tr>
                                <td class="form-left">Publish:</td>
                                <td class="form-right">

                                    <c:choose>
                                        <c:when test="${isApprovalNeeded == false}">
                                            <input type="checkbox"
                                                    <c:if test="${published == true}"> checked </c:if>
                                                   name="publishedSelector" value="true" class="ebox"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="publishedSelector" disabled value="true"
                                                   class="ebox"/>
                                        </c:otherwise>
                                    </c:choose>


                                </td>
                            </tr>
                            <tr>
                                <td id="cf-buttonrow" colspan="2">
                                    <button type="submit" value="save" name="button">Save</button>&nbsp;&nbsp;
                                    <button type="submit" value="delete" name="button">Delete</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-sm-9 text-center">
                <textarea name="newBlogPost" id="newBlogPost">
                    <c:choose>
                        <c:when test="${not empty body}">
                            ${body}
                        </c:when>
                        <c:otherwise>
                            Say something...
                        </c:otherwise>
                    </c:choose>


                </textarea><br>
                </div>
            </div>
        </form>
    </c:when>
        <c:otherwise>

            <div  class="alert alert-danger" role="alert">You are not authorized to view this content...</div>
            <input type="hidden" value="true" id="redirect"/>



        </c:otherwise>
    </c:choose>
</div>

<script src="./js/tether.min.js"></script>
<script src="./js/moment.min.js"></script>
<script src="./js/jquery-3.2.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/ofn.js"></script>
<script src="./js/createcontent.js"></script>

<script src="./js/codemirror.min.js"></script>
<script src="./js/xml.min.js"></script>
<script src="./js/froala_editor.pkgd.min.js"></script>
</body>
</html>

<%--<div id="blogOptions">--%>
<%--</div>--%>
<%--<label for="publishedSelector">Publish new post?</label>--%>
<%--<br>--%>
<%--<label for="blogPostRadio">Type of post:</label><br>--%>
<%--<input type="radio" id="blogPostRadio" checked>--%>
<%--<label for="blogPostRadio">Blog post</label><br>--%>
<%--<sec:authorize access="hasRole('ROLE_OWNER')">--%>
<%--<input type="radio" id="staticPageRadio">--%>
<%--<label for="staticPageRadio">Static page</label><br>--%>
<%--</sec:authorize>--%>
<%--<c:choose>--%>
<%--<c:when test="${isApprovalNeeded}">--%>
<%--<c:out value="The owner must approve your post before it is published"/>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<c:out value="When you're ready, press SUBMIT"/>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--<br>--%>
<%--<button>SUBMIT</button>--%>