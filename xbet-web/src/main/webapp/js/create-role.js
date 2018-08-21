var isRoleCorrect = false;

function roleCheck() {
    var role = $("#role").val();
    var regExp = new RegExp("<c:out value="${roleRegexp}"/>");
    if (role === "") {
        $('#roleDiv').css("display", "none");
        alert("<c:out value="${roleRequired}"/>");
        isRoleCorrect = false;
    } else if (!regExp.test(role)) {
        isRoleCorrect = false;
        $('#roleDiv').html("<p style='color: red'><c:out value="${regexpError}"/></p>");
        $('#roleDiv').css("display", "block");
    }  else {
        isRoleCorrect = true;
        $('#roleDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#roleDiv').css("display", "block");
    }
}

function changeFormAction() {
    if (isRoleCorrect) {
        var form = document.getElementById('createForm');
        form.action = "<c:url value="/main/create-role"/>";
        form.submit();
    } else {
        alert("<c:out value="${createData}"/>");
    }
}