<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://ofn.com/functions" prefix="cf" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="random" class="java.util.Random" scope="application"/>
<!DOCTYPE html>
<html>
<head>
    <title>One Final Note</title>
    <!-- Bootstrap core CSS -->
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="./css/jquery-ui.min.css">
    <link rel="stylesheet" href="./css/jquery-ui.structure.min.css">
    <link rel="stylesheet" href="./css/jquery-ui.theme.min.css">
    <link href="./css/main.css" rel="stylesheet">
    <link href="./css/forms.css" rel="stylesheet">
    <%--<link href="./css/ofn-custom-style.css" rel="stylesheet">--%>
</head>
<body>
<div class="container" id="page">

    <hr/>

    <input type="hidden" id="cShowType" name="cShowType" value="${cShowType}"/>
    <input type="hidden" id="cShowID" name="cShowID" value="${cShowID}"/>
    <input type="hidden" id="cShow" name="cShow" value="${cShow}"/>
    <input type="hidden" id="blogid" name="blogid" value="${blogid}"/>

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
                <div class="col-12 text-left">
                    <div id="linktitle">Content</div>
                </div>
            </div>

            <div id="staticpagelinkdiv">
                <ul id="links-ul">
                    <li><a class="hlink" href="./">Blog</a></li>
                    <c:forEach var="link" items="${pageLinks}">
                        <li class="staticlnk" id="staticpage${link.key}"><span class="staticpages">${link.value}</span>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col-sm-10 text-center center-offset-editor">
            <div id="staticdiv" style="display: none">
            </div>
            <c:set var="isAdminLoggedIn" value="False"/>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <c:set var="isAdminLoggedIn" value="True"/>
            </sec:authorize>
            <c:set var="isOwnerLoggedIn" value="False"/>
            <sec:authorize access="hasRole('ROLE_OWNER')">
                <c:set var="isOwnerLoggedIn" value="True"/>
            </sec:authorize>
            <form>
                <input type="hidden" name="ownerLoggedIn" id="ownerLoggedIn" value="${isOwnerLoggedIn}"/>
            </form>
            <div id="mainblogdiv">
                <c:forEach var="blogRow" items="${allBlogs}">
                    <c:set var="blog" value="${blogRow.value}"/>
                    <c:if test="${isOwnerLoggedIn || isAdminLoggedIn || blog.published}">


                        <div style="display:inline" class="blogposts" id="bp${blog.blogPostId}">
                            <div class="blogDiv">
                                <span class="content-title"><c:out value="${blog.title}"/></span>
                                <br>

                                <c:if test="${!blog.published}">
                                    <span class="unpub">Unpublished</span><br/>
                                </c:if>


                                Posted by: <span class="postedBy">${blog.user.userName}</span><br/>
                                Last updated: ${cf:formatLocalDateTime(blog.updateTime, 'dd.MM.yyyy hh:mm')}
                                <br/><br>

                                <div class="blog-body">
                                    <c:out value="${blog.body}" escapeXml="false"/>
                                </div>
                                <br>

                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <c:if test="${pageContext.request.userPrincipal.name == 'owner' ||
                                        (isOwnerLoggedIn
                                            && (pageContext.request.userPrincipal.name == blog.user.userName
                                                                || blog.user.authorities[0] == 'ROLE_ADMIN'))
                                            || !blog.published}">
                                    <a class="hlink" href="editcontent?contentType=blog&contentID=${blog.blogPostId}">Edit</a>&nbsp | &nbsp<a
                                    class="hlink" href="deleteBlogPost?blogId=${blog.blogPostId}">Delete</a>
                                    </c:if>
                                </sec:authorize>

                                <div class="tagDiv">
                                    <div class="tagTextDiv">
                                        <c:forEach var="tag" items="${blog.tagList}">
                                        <c:set var="r" value="${random.nextInt(9)}"/>
                                        <c:set var="g" value="${random.nextInt(9)}"/>
                                        <c:set var="b" value="${random.nextInt(9)}"/>


                                        <a style="color: #${r}${g}${b}" class="bodytag"
                                           href="./search?cat=blog&method=tag&state=published&terms=${tag.tagText}">
                                        #${tag.tagText}
                                        <a>&nbsp;
                                            </c:forEach>
                                    </div>
                                    <div class="commentbuttondiv">
                                        <button
                                                id="blogPost${blog.blogPostId}"
                                                class="showCommentsButton">View Comments
                                        </button>
                                    </div>
                                    <div style="clear: both"></div>
                                </div>
                            </div>

                        </div>


                        <div style="display:none;" class="blogcomments" id="blogPostComments${blog.blogPostId}">
                            <c:forEach var="comm" items="${blog.commentList}">
                                <c:if test="${isOwnerLoggedIn || isAdminLoggedIn || comm.published}">
                                    <div class="single-comment">

                                        <span class="commentTitle">Comment from ${comm.user.userName}
                                            at ${cf:formatLocalDateTime(comm.commentTime, 'dd.MM.yyyy hh:mm' )}</span>
                                        <br>${comm.body}<br/>
                                        <c:set var="canChange" value="true"/>

                                        <c:if test="${pageContext.request.userPrincipal.name !=
                                         comm.user.userName && pageContext.request.userPrincipal.name != 'owner' }">
                                            <c:forEach var="auth" items="${comm.user.authorities}">
                                                <c:if test="${auth == 'ROLE_OWNER'}">
                                                    <c:set var="canChange" value="false"/>
                                                </c:if>
                                                <c:if test="${auth == 'ROLE_ADMIN' && isAdminLoggedIn
                                                && !isOwnerLoggedIn}">
                                                    <c:set var="canChange" value="false"/>
                                                </c:if>

                                            </c:forEach>
                                        </c:if>

                                        <sec:authorize access="hasRole('ROLE_ADMIN')">

                                        <c:if test="${canChange == 'true'}">

                                            <a class="hlink2" href="editComment?commId=${comm.commentId}&blogid=${blog.blogPostId}">
                                                <c:choose>
                                                    <c:when test="${comm.published}">Hide</c:when>
                                                    <c:otherwise>Show</c:otherwise>
                                                </c:choose>
                                            </a>&nbsp| &nbsp<a
                                            class="hlink2" href="deleteComment?commId=${comm.commentId}&blogid=${blog.blogPostId}">Delete</a>&nbsp-&nbsp
                                        </c:if>

                                        <span class="smalltext">Currently
                                        <c:choose>
                                            <c:when test="${comm.published}"><span
                                                    class="pub smalltext"> visible</span></c:when>
                                            <c:otherwise><span class="unpub smalltext"> hidden</span></c:otherwise>
                                        </c:choose>


                                    </sec:authorize>
                                    </div>
                                </c:if>

                                <br>
                            </c:forEach>
                            <br/>
                            <form>
                                <input type="hidden" id="hiddenBlogPostID" name="hiddenBlogPostID" value="-1">
                            </form>
                            <sec:authorize access="isAuthenticated()">
                                <form action="addComment" method="POST">
                                    <c:set var="isApprovalNeeded" value="False"/>
                                    <c:set var="isPublishing" value="True"/>
                                    <sec:authorize access="hasRole('ROLE_OWNER') || hasRole('ROLE_ADMIN')">
                                        <c:set var="isApprovalNeeded" value="False"/>
                                        <c:set var="isPublishing" value="True"/>
                                    </sec:authorize>

                                    <div class="add-comment">
                                        <table class="cb-table">
                                            <tbody>
                                            <tr>
                                                <td>
                                                    <textarea style="background-color: white" name="commentBody" id="commentBody"
                                                              placeholder="Comment..."
                                                              class="commentBody"></textarea>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="text-align: right">
                                                    <button type="submit" class="add-comment-button">Add Comment
                                                    </button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>

                                    <c:choose>
                                        <c:when test="${isApprovalNeeded}">
                                            <br><c:out value="Your comment must be approved before posting"/>
                                        </c:when>
                                    </c:choose>
                                    <input type="hidden" id="blogIdNumber" name="blogIdNumber"
                                           value="${blog.blogPostId}"/>
                                    <input type="hidden" name="isPublishing" value="${isPublishing}"/>
                                    <input type="hidden" name="userLoggedIn"
                                           value="${pageContext.request.userPrincipal.name}"/>

                                </form>
                            </sec:authorize>
                        </div>
                        <br/><br/>
                    </c:if>
                </c:forEach>
            </div>
            <div id="singleblogdiv" style="display: none">
            </div>
            <form>

            </form>
            <div id="commentbuttondiv" style="display: none">

            </div>

        </div>
    </div>


</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/tether.min.js"></script>
<script src="./js/moment.min.js"></script>
<script src="./js/jquery-3.2.1.min.js"></script>
<script src="./js/external/jquery/jquery.js"></script>
<script src="./js/jquery-ui.min.js"></script>
<script src="./js/jquery-ui.triggeredAutocomplete.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/ofn.js"></script>
<script src="./js/index.js"></script>

</body>
</html>