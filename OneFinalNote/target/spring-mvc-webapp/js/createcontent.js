$(document).ready(function () {

    if ($("#redirect").val() === "true") {
        window.setTimeout(function () {
            window.location.href = "./index";
        }, 3000);
    }
    launchEditor();


    $('input[type=radio]').change(function () {
        console.log("sx");
        switch ($(this).val()) {
            case 'blog':
                $(".form-hide").show();
                break;
            case 'page':
                $(".form-hide").hide();
                break;
        }
    });


});


function launchEditor() {
    $('#newBlogPost').froalaEditor({

        // Set custom buttons with separator between them.
        toolbarButtons: ['undo', 'redo', '|', 'bold', 'italic', 'underline', 'strikeThrough',
            'subscript', 'superscript', 'outdent', 'indent',
            'clearFormatting', 'insertTable', 'html', 'insertLink',
            'insertImage', 'insertVideo', 'emoticons', 'specialCharacters',
            'fontFamily', 'fontSize', 'color', 'align', 'formatOL', 'formatUL'],


        heightMin: 300,
        charCounterCount: false,
        theme: 'dark',
        zIndex: 2003
    });

}