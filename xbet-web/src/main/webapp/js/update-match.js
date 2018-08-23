function checkDate() {
    var date = $("#date").val();
    if (date === "") {
        $('#dateDiv').css("display", "none");
        alert("<c:out value="${dateRequired}"/>");
    }
}

function checkTeam1ID() {
    var team1ID = $("#team1ID").val();
    if (team1ID === "") {
        $('#team1IDDiv').css("display", "none");
        alert("<c:out value="${team1Required}"/>");
    } else if (team1ID < 1 || team1ID > 50) {
        team1IDMessage("INCORRECT");
    } else {
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
    } else if (team2ID < 1 || team2ID > 50) {
        team2IDMessage("INCORRECT");
    } else {
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
    } else if (v1 < 1 || v1 > 4) {
        v1Message("INCORRECT");
    } else {
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
    } else if (X < 1 || X > 4) {
        XMessage("INCORRECT");
    } else {
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
    } else if (v2 < 1 || v2 > 4) {
        v2Message("INCORRECT");
    } else {
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
    } else if (X1 < 1 || X1 > 4) {
        X1Message("INCORRECT");
    } else {
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
    } else if (v12 < 1 || v12 > 4) {
        v12Message("INCORRECT");
    } else {
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
    } else if (X2 < 1 || X2 > 4) {
        X2Message("INCORRECT");
    } else {
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