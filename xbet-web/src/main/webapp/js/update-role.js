function roleCheck() {
    var role = $("#role").val();
    var regExp = new RegExp("<c:out value="${roleRegexp}"/>");
    if (role === "") {
        $('#roleDiv').css("display", "none");
        alert("<c:out value="${roleRequired}"/>");
    } else if (!regExp.test(role)) {
        $('#roleDiv').html("<p style='color: red'><c:out value="${regexpError}"/></p>");
        $('#roleDiv').css("display", "block");
    }  else {
        $('#roleDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#roleDiv').css("display", "block");
    }
}