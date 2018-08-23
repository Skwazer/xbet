function nameCheck() {
    var name = $("#name").val();
    var regExp = new RegExp("<c:out value="${teamRegexp}"/>");
    if (name === "") {
        $('#nameDiv').css("display", "none");
        alert("<c:out value="${nameRequired}"/>");
    } else if (!regExp.test(name)) {
        $('#nameDiv').html("<p style='color: red'><c:out value="${regexError}"/></p>");
        $('#nameDiv').css("display", "block");
    }  else {
        $('#nameDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#nameDiv').css("display", "block");
    }
}