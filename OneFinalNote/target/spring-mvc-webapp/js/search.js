$(document).ready(function () {

    var cat = getParameterByName("cat");
    var sub = getParameterByName("method");
    var pub = getParameterByName("state");
    var terms = getParameterByName("terms");

    if (cat === null || cat === undefined){
        clickHandlers()
    }

    else {
        qfrMin(cat, sub, pub, terms);
        clickHandlers();
    }



});

function clickHandlers() {

    var category = $("#category");
    var subcategory = $("#subcategory");

    $("#searchbutton").click(function () {
        queryforresults();
    });

    category.change(function () {
        if (category.val() === "page") {
            $(".postopt").hide();
        }
        else {
            $(".postopt").show()
        }
        subcategory.val("general").prop('selected', true);
    });

    subcategory.change(function () {
        var elSub = subcategory.val();

        if (elSub === "date") {
            $("#searchterms").hide();
            $("#cat-select").hide();
            $("#searchdate").show();
        } else if (elSub === "category") {
            $("#searchterms").hide();
            $("#cat-select").show();
            $("#searchdate").hide();
        } else {
            $("#searchterms").show();
            $("#searchdate").hide();
            $("#cat-select").hide();
        }
    });

}

function queryforresults() {

    var terms;
    var category = $("#category").val();
    var method = $("#subcategory").val();

    if (method === "date") {
        terms = $("#searchdate").val();
    }
    else if (method === "category") {
        terms = $("#cat-select").val();
    }
    else {
        terms = $("#searchterms").val()
    }
    var state = $("#state").val();

    if (state === undefined) {
        state = "published"
    }

        qfrMin(category, method, state, terms);
}

function qfrMin(category, method, state, terms) {

    var call = "/onefinalnote/search/" + category
        + "s?method=" + method + "&state=" + state + "&terms=" + terms;





    $.ajax({
        type: "GET",
        url: call,
        success: function (results) {
            printResults(results, category);
        },
        error: function () {
            alert("fail")
        }
    });
}


function printResults(results, category) {

    var id = "blogPostId";
    var time = "updateTime";
    var type = "blog";

    if (category === "page") {
        id = "pageId";
        time = "updatedTime";
        type = "page";
    }
    var sec = $("#sec").val();
    var curruser = $("#userid").val();

    var resultstable =
        "<table id='resultstable'>" +
        "<thead> <tr> <th class='header-th-id'>ID</th> <th>Title</th> <th>Date</th> " +
        "<th>Preview</th>";

    if (type === "blog") {
        resultstable += "<th>Tags</th>";
    }


    if (sec === "owner" || sec === "admin") {
        resultstable += " <th>Published</th> <th>Admin Functions</th> ";
    }

    resultstable += "</tr> </thead> <tbody>";

    $.each(results, function (index, res) {

        console.log(res.published);

        var d = moment(res[time]);
        var body = res.body;


        resultstable +=
            "<tr><td class='id-col'><a class='hlink2' target='_blank' href='./show?contentType=" + type + "&contentID="
            + res[id] + "'>" + res[id] + "</a></td>" +
            "<td><a class='hlink2' target='_blank' href='./show?contentType=" + type + "&contentID=" + res[id] + "'>"
            + res.title + "</a></td>" +
            "<td>" + d.format("MMM Do YY, h:mm a") + "</td>" +
            "<td>" + getPreview(body, 4) + "</td>";

        if (type === "blog") {
            try {
                resultstable += "<td>";
                $.each(res.tagList, function (index, tag) {
                    if (index === res.tagList.length - 1) {
                        resultstable += tag.tagText;
                    } else {
                        resultstable += tag.tagText + ", ";
                    }
                });
                resultstable += "</td>";
            } catch (err) {}
        }

        resultstable += "</td>";

        if (sec === "admin" || sec === "owner") {
            resultstable += "<td>" + res.published + "</td>";
            if (sec === "owner" && curruser === "owner") {
                resultstable += "<td><a class='hlink2' target='_blank' href='./editcontent?contentType=" + type + "&contentID=" + res[id] + "'>Edit</a>&nbsp; | &nbsp;" +
                    "<a class='hlink2' target='_blank' href='deleteBlogPost?blogId=" + res[id] + "'>Delete</a></td>";
            } else if (sec === "owner" && curruser === res.user.userName){
                resultstable += "<td><a class='hlink2' target='_blank' href='./editcontent?contentType=" + type + "&contentID=" + res[id] + "'>Edit</a>&nbsp; | &nbsp;" +
                    "<a class='hlink2' target='_blank' href='deleteBlogPost?blogId=" + res[id] + "'>Delete</a></td>";
            } else if (sec === "admin" && curruser === res.user.userName && res.published === false) {
                resultstable += "<td><a class='hlink2' target='_blank' href='./editcontent?contentType=" + type + "&contentID=" + res[id] + "'>Edit</a>&nbsp; | &nbsp;" +
                    "<a class='hlink2' target='_blank' href='deleteBlogPost?blogId=" + res[id] + "'>Delete</a></td>";
            } else {
                resultstable += "<td>No functions Available</td>";
            }
        }
        resultstable += "</tr>";
    });

    resultstable += "</tbody> </table>";
    $("#resultsdiv").html(resultstable);
}


function strip(html) {
    var tmp = document.createElement("DIV");
    tmp.innerHTML = html;
    return tmp.textContent || tmp.innerText || "";
}


function getPreview(fulltext, wlim) {
    fulltext = strip(fulltext);
    var words = fulltext.split(" ");
    var preview = "";

    for (var i = 0; i < wlim; i++) {
        if (i === wlim - 1) {
            preview += words[i] + "...";
        } else {
            preview += words[i] + " ";
        }
    }
    return preview;

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