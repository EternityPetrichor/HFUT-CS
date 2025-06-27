$(document).ready((function() {
    $(".case-sensitive").tagging({
        "case-sensitive": !0
    }), $(".close-char").tagging({
        "close-char": ""
    }), $(".deleted").tagging({
        "deleted": !1
    }), $(".duplicated").tagging({
        "duplicated": !1
    }), $(".no-enter").tagging({
        "no-enter": !0
    }), $(".no-comma").tagging({
        "no-comma": !0
    }), $(".type-zone-class").tagging({
        "type-zone-class": "tagging-area"
    });
    var a = $(".reset-box").tagging();
    a = a[0], $(".reset-tagging").on("click", (function() {
        a.tagging("reset")
    }));
}));