
$(document).ready(function () {

    indexListener();
    navListener();
    preload();



});

function preload(){

    if ($("#cShow").val() === "2"){

        var commDivID = "blogPostComments"+$("#blogid").val();
        $("#"+commDivID).show();

        document.getElementById(commDivID).scrollIntoView();
    }

    if ($("#cShow").val() === "1"){

        var id = $("#cShowID").val();
        var type = $("#cShowType").val();

        if (type === "blog"){
           document.getElementById("bp"+id).scrollIntoView();
        }
        else {
            hideall();
            fetchPage(id);
            $("#staticdiv").show();
        }
    }

    startAutoComplete();

}

function startAutoComplete() {



    var call = "/onefinalnote/getUserNames";
    $.ajax({
        type: "GET",
        url: call,
        success: function (results) {
            autoListener(results);
        },
        error: function () {
            alert("fail")
        }
    });
}


function autoListener(list){

    $('#commentBody').triggeredAutocomplete({
        position: { my : "top", at: "bottom" },
        source: list,
        trigger: "@",
        open: function() {
            $('.ui-menu').width(0);
            $('.ui-menu').height(0);
            $('.ui-menu').css({
                "margin-top":"0px",
                "margin-left":"0px",
                "background-color":"transparent",
                "border":"0"});


        }
    });
}




function hideall(){
    $("#mainblogdiv").hide();
    $("#singleblogdiv").hide();
    $("#commentbuttondiv").hide();
    $("#staticdiv").hide();
}

// Listens for clicks on elements and implements resulting functions
function indexListener() {

    var elements = document.getElementsByClassName('bodytag');

    $.each(elements, function(index, el){
        el.style.color = '#'+(0x1000000+(Math.random())*0xffffff).toString(16).substr(1,6);
    });

    $(".bodytag").on({
        mouseenter: function () {
            $(this).addClass("taghover");
        },
        mouseleave: function () {
            $(this).removeClass("taghover");
        }
    });

    // $(document).on("hover", ".bodytag", function () {
    //     console.log("x");
    //     $(this).addClass("test");
    // });


    $(document).on("click", "#staticpagelinkdiv .staticlnk", function () {
         hideall();
        $("#staticdiv").show();
        var thisItem = $(this).attr("id");
        var itemNum = thisItem.substr(10, thisItem.length - 1);
        fetchPage(itemNum);
    });

    $(document).on("click", "#mainblogdiv .blogposts .showCommentsButton", function () {
        var blogPostID = $(this).attr("id");
        blogPostID = blogPostID.substr(8, blogPostID.length - 1);

        var commDivID = "#blogPostComments"+blogPostID;

        if ($(commDivID).is(":visible")){
            $(commDivID).hide();
        } else {
        $(commDivID).show();
        }

    });

    $('input[type=radio]').click(function () {
        var radioClicked = this.id;
        if (radioClicked === "staticPageRadio") {
            $("#blogPostRadio").attr("checked", false);
            $("#blogOptions").hide();}
        else {
            $("#staticPageRadio").attr("checked", false);
            $("#blogOptions").show();}
    });
}

// Fetches a page by ID and displays it
function fetchPage(pageID) {

    $.ajax({
        type: "GET",
        url: "/onefinalnote/displayStaticPage/" + pageID,
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (data) {
            var post = "<div class='page-container'>" + "<h3 style='text-align:center;color:green'>" + data.title + "</h3><br>" + data.body + "<br>Posted by: " + data.user.userName + "</div>";
            // var secstr = "<sec:authorize access='hasRole(" + "'ROLE_OWNER')><a href='deleteStaticPage?pageId=" + pageID + "'>Delete</a></sec:authorize></div>";
            // post += secstr;
            $("#staticdiv").html(post);
        },
        error: function () {
            alert("fail"); }
    });
}

// Fetches a post by ID and displays it.  On successful retrieval,
// comments are also queried for and added.
function fetchBlogPost(blogPostID, direct) {

    var singleblogdiv = $("#singleblogdiv");
    hideall();
    singleblogdiv.empty();
    singleblogdiv.show();

    if (direct !== true){
        blogPostID = blogPostID.substr(8, blogPostID.length - 1);
    }

    $.ajax({
        type: "GET",
        url: "/onefinalnote/displayBlogPost/" + blogPostID,
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (data) {
            // Note the addition of moment.js.  Using the backend JSON
            // serializer for date, this greatly simplifies the date-time
            // format exchange from front to back.

            // var d = new moment(data.updateTime); parses the serialized date object into a moment.js type date
            // d.format("MMM Do YY, h:mm a") provides a simple method to format the date for output
            var d = new moment(data.updateTime);

            var post = "<div class='page-container'>" + data.title + "<br/>" +
                "Last updated on " + d.format("MMM Do YYYY, h:mm a")+
                "<br/>" + data.body + "<br/></div>";

            singleblogdiv.append(post);
            fetchComments(blogPostID);
            document.getElementById("hiddenBlogPostID").value = blogPostID;

        },
        error: function () {
            alert("fail");
        }
    });
}


// Grabs comments for a single blog post and attaches them
function fetchComments(blogPostID){
    var isOwner = document.getElementById("ownerLoggedIn").value;
    $.ajax({
        type: "get",
        url: "/onefinalnote/getCommentsForBlogPost/" + blogPostID + "/" + isOwner,
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (commdata) {
            $.each(commdata, function (i) {
                var d = new moment(commdata[i]["commentTime"]);
                var commstr = "<strong>Comment from " + commdata[i]["user"]["userName"] + " on " + d.format("MMM Do YYYY, h:mm a") + "</strong><br>";
                commstr += commdata[i]["body"] + " <a href='deleteComment?commId=" + (i + 1) + "'>Delete</a><br>";
                $("#singleblogdiv").append(commstr);
            });},
        error: function () {
            alert("fail"); }
    });
    $("#commentbuttondiv").show();
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));

}