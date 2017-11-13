

$(document).ready(function(){
    navListener();


});

function navListener(){
    $("#menu-button").hover(function(){
        $("#menudrop").addClass("show");
        $("#menu-button").css({
            "outline":"none",
            "background-color":"transparent",
            "-webkit-filter":"drop-shadow(5px 5px 5px #222)",
            "filter":"drop-shadow(5px 5px 5px #222)"
        })
    });


    $(".dropdown-menu").mouseleave(function () {
        $("#menudrop").removeClass("show");
        $("#menu-button").css({
            "-webkit-filter":"drop-shadow(0px 0px 0px #222)",
            "filter":"drop-shadow(0px 0px 0px #222)"
        })
    });
}


