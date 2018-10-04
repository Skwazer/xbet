var isDateCorrect = false;
var isTeamsCorrect = false;
var is1Correct = false;
var isXCorrect = false;
var is2Correct = false;
var is1XCorrect = false;
var is12Correct = false;
var is2XCorrect = false;

function checkDate() {
    var date = $("#date").val();
    var current = new Date();
    current.setHours(3, 0, 0, 0);
    var jsDate = new Date(date);
    if (date === "") {
        alert("<c:out value='${dateRequired}'/>");
        isDateCorrect = false;
        $('#dateDiv').css("display", "none");
    } else if (jsDate < current) {
        alert("<c:out value='${dateIncorrect}'/>");
        isDateCorrect = false;
        $('#dateDiv').html("<p style='color: red'><c:out value='${incorrectValue}'/></p>");
        $('#dateDiv').css("display", "block")
    } else {
        isDateCorrect = true;
        $('#dateDiv').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#dateDiv').css("display", "block")
    }
}

function checkTeams() {
    var team1 = $("#select1").val();
    var team2 = $("#select2").val();
    if (team1 === team2) {
        isTeamsCorrect = false;
    } else {
        isTeamsCorrect = true;
    }
}

function check1() {
    var v1 = $("#v1").val();
    if (v1 === "") {
        $('#v1Div').css("display", "none");
        alert("<c:out value='${v1Required}'/>");
        is1Correct = false;
    } else if (v1 < 1 || v1 > 4) {
        is1Correct = false;
        v1Message("INCORRECT");
    } else {
        is1Correct = true;
        v1Message("SUCCESS");
    }
}

function v1Message(result) {
    if (result === 'SUCCESS') {
        $('#v1Div').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#v1Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#v1Div').html("<p style='color: red'><c:out value='${incorrectCoefficient}'/></p>");
        $('#v1Div').css("display", "block")
    }
}

function checkX() {
    var X = $("#X").val();
    if (X === "") {
        $('#XDiv').css("display", "none");
        alert("<c:out value='${XRequired}'/>");
        isXCorrect = false;
    } else if (X < 1 || X > 4) {
        isXCorrect = false;
        XMessage("INCORRECT");
    } else {
        isXCorrect = true;
        XMessage("SUCCESS");
    }
}

function XMessage(result) {
    if (result === 'SUCCESS') {
        $('#XDiv').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#XDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#XDiv').html("<p style='color: red'><c:out value='${incorrectCoefficient}'/></p>");
        $('#XDiv').css("display", "block")
    }
}

function check2() {
    var v2 = $("#v2").val();
    if (v2 === "") {
        $('#v2Div').css("display", "none");
        alert("<c:out value='${v2Required}'/>");
        is2Correct = false;
    } else if (v2 < 1 || v2 > 4) {
        is2Correct = false;
        v2Message("INCORRECT");
    } else {
        is2Correct = true;
        v2Message("SUCCESS");
    }
}

function v2Message(result) {
    if (result === 'SUCCESS') {
        $('#v2Div').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#v2Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#v2Div').html("<p style='color: red'><c:out value='${incorrectCoefficient}'/></p>");
        $('#v2Div').css("display", "block")
    }
}

function check1X() {
    var X1 = $("#X1").val();
    if (X1 === "") {
        $('#X1Div').css("display", "none");
        alert("<c:out value='${X1Required}'/>");
        is1XCorrect = false;
    } else if (X1 < 1 || X1 > 4) {
        is1XCorrect = false;
        X1Message("INCORRECT");
    } else {
        is1XCorrect = true;
        X1Message("SUCCESS");
    }
}

function X1Message(result) {
    if (result === 'SUCCESS') {
        $('#X1Div').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#X1Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#X1Div').html("<p style='color: red'><c:out value='${incorrectCoefficient}'/></p>");
        $('#X1Div').css("display", "block")
    }
}

function check12() {
    var v12 = $("#v12").val();
    if (v12 === "") {
        $('#v12Div').css("display", "none");
        alert("<c:out value='${v12Required}'/>");
        is12Correct = false;
    } else if (v12 < 1 || v12 > 4) {
        is12Correct = false;
        v12Message("INCORRECT");
    } else {
        is12Correct = true;
        v12Message("SUCCESS");
    }
}

function v12Message(result) {
    if (result === 'SUCCESS') {
        $('#v12Div').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#v12Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#v12Div').html("<p style='color: red'><c:out value='${incorrectCoefficient}'/></p>");
        $('#v12Div').css("display", "block")
    }
}

function check2X() {
    var X2 = $("#X2").val();
    if (X2 === "") {
        $('#X2Div').css("display", "none");
        alert("<c:out value='${X2Required}'/>");
        is2XCorrect = false;
    } else if (X2 < 1 || X2 > 4) {
        is2XCorrect = false;
        X2Message("INCORRECT");
    } else {
        is2XCorrect = true;
        X2Message("SUCCESS");
    }
}

function X2Message(result) {
    if (result === 'SUCCESS') {
        $('#X2Div').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#X2Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#X2Div').html("<p style='color: red'><c:out value='${incorrectCoefficient}'/></p>");
        $('#X2Div').css("display", "block")
    }
}

function changeFormAction() {
    checkTeams();
    var result = is1Correct && isXCorrect && is2Correct && is1XCorrect && is12Correct && is2XCorrect
        && isDateCorrect;
    if (!result) {
        alert("<c:out value='${createData}'/>");
    } else if (!isTeamsCorrect) {
        alert("<c:out value='${teamsEqual}'/>");
    } else {
        var form = document.getElementById('createForm');
        form.action = "<c:url value='/main/create/match'/>";
        form.submit();
    }
}