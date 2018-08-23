var isDateCorrect = false;
var isTeam1IDCorrect = false;
var isTeam2IDCorrect = false;
var is1Correct = false;
var isXCorrect = false;
var is2Correct = false;
var is1XCorrect = false;
var is12Correct = false;
var is2XCorrect = false;

function checkDate() {
    var date = $("#date").val();
    if (date === "") {
        $('#dateDiv').css("display", "none");
        alert("<c:out value="${dateRequired}"/>");
        isDateCorrect = false;
    } else {
        isDateCorrect = true;
    }
}

function checkTeam1ID() {
    var team1ID = $("#team1ID").val();
    if (team1ID === "") {
        $('#team1IDDiv').css("display", "none");
        alert("<c:out value="${team1Required}"/>");
        isTeam1IDCorrect = false;
    } else if (team1ID < 1 || team1ID > 50) {
        isTeam1IDCorrect = false;
        team1IDMessage("INCORRECT");
    } else {
        isTeam1IDCorrect = true;
        team1IDMessage("SUCCESS");
    }
}

function team1IDMessage(result) {
    if (result === 'SUCCESS') {
        $('#team1IDDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#team1IDDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#team1IDDiv').html("<p style='color: red'><c:out value="${negative}"/></p>");
        $('#team1IDDiv').css("display", "block")
    }
}

function checkTeam2ID() {
    var team2ID = $("#team2ID").val();
    if (team2ID === "") {
        $('#team2IDDiv').css("display", "none");
        alert("<c:out value="${team2Required}"/>");
        isTeam2IDCorrect = false;
    } else if (team2ID < 1 || team2ID > 50) {
        isTeam2IDCorrect = false;
        team2IDMessage("INCORRECT");
    } else {
        isTeam2IDCorrect = true;
        team2IDMessage("SUCCESS");
    }
}

function team2IDMessage(result) {
    if (result === 'SUCCESS') {
        $('#team2IDDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#team2IDDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#team2IDDiv').html("<p style='color: red'><c:out value="${negative}"/></p>");
        $('#team2IDDiv').css("display", "block")
    }
}

function check1() {
    var v1 = $("#v1").val();
    if (v1 === "") {
        $('#v1Div').css("display", "none");
        alert("<c:out value="${v1Required}"/>");
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
        $('#v1Div').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#v1Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#v1Div').html("<p style='color: red'><c:out value="${incorrectCoefficient}"/></p>");
        $('#v1Div').css("display", "block")
    }
}

function checkX() {
    var X = $("#X").val();
    if (X === "") {
        $('#XDiv').css("display", "none");
        alert("<c:out value="${XRequired}"/>");
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
        $('#XDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#XDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#XDiv').html("<p style='color: red'><c:out value="${incorrectCoefficient}"/></p>");
        $('#XDiv').css("display", "block")
    }
}

function check2() {
    var v2 = $("#v2").val();
    if (v2 === "") {
        $('#v2Div').css("display", "none");
        alert("<c:out value="${v2Required}"/>");
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
        $('#v2Div').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#v2Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#v2Div').html("<p style='color: red'><c:out value="${incorrectCoefficient}"/></p>");
        $('#v2Div').css("display", "block")
    }
}

function check1X() {
    var X1 = $("#X1").val();
    if (X1 === "") {
        $('#X1Div').css("display", "none");
        alert("<c:out value="${X1Required}"/>");
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
        $('#X1Div').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#X1Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#X1Div').html("<p style='color: red'><c:out value="${incorrectCoefficient}"/></p>");
        $('#X1Div').css("display", "block")
    }
}

function check12() {
    var v12 = $("#v12").val();
    if (v12 === "") {
        $('#v12Div').css("display", "none");
        alert("<c:out value="${v12Required}"/>");
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
        $('#v12Div').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#v12Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#v12Div').html("<p style='color: red'><c:out value="${incorrectCoefficient}"/></p>");
        $('#v12Div').css("display", "block")
    }
}

function check2X() {
    var X2 = $("#X2").val();
    if (X2 === "") {
        $('#X2Div').css("display", "none");
        alert("<c:out value="${X2Required}"/>");
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
        $('#X2Div').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#X2Div').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#X2Div').html("<p style='color: red'><c:out value="${incorrectCoefficient}"/></p>");
        $('#X2Div').css("display", "block")
    }
}

function changeFormAction() {
    var result = isTeam1IDCorrect && isTeam2IDCorrect && is1Correct && isXCorrect && is2Correct
    && is1XCorrect && is12Correct && is2XCorrect && isDateCorrect;
    if (result) {
        var form = document.getElementById('createForm');
        form.action = "<c:url value="/main/create/match"/>";
        form.submit();
    } else {
        alert("<c:out value="${createData}"/>");
    }
}