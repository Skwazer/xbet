var isNameCorrect = false;

function nameCheck() {
    var name = $("#name").val();
    var regExp = new RegExp("<c:out value='${teamRegexp}'/>");
    if (name === "") {
        $('#nameDiv').css("display", "none");
        alert("<c:out value='${nameRequired}'/>");
        isNameCorrect = false;
    } else if (!regExp.test(name)) {
        isNameCorrect = false;
        $('#nameDiv').html("<p style='color: red'><c:out value='${regexError}'/></p>");
        $('#nameDiv').css("display", "block");
    }  else {
        isNameCorrect = true;
        $('#nameDiv').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#nameDiv').css("display", "block");
    }
}

function changeFormAction() {
    if (isNameCorrect) {
        var form = document.getElementById('createForm');
        form.action = "<c:url value='/main/create/team'/>";
        form.submit();
    } else {
        alert("<c:out value='${createData}'/>");
    }
}