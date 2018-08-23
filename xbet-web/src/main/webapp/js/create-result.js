var isMatchIDCorrect = false;
var isTeam1IDCorrect = false;
var isTeam2IDCorrect = false;
var isTeam1GoalsCorrect = false;
var isTeam2GoalsCorrect = false;

function checkMatchID() {
    var matchID = $("#matchID").val();
    if (matchID === "") {
        $('#matchIDDiv').css("display", "none");
        alert("<c:out value="${matchIDRequired}"/>");
        isMatchIDCorrect = false;
    }  else if (matchID < 1 || matchID > 100) {
        isMatchIDCorrect = false;
        matchIDMessage("INCORRECT");
    } else {
        isMatchIDCorrect = true;
        matchIDMessage("SUCCESS");
    }
}

function matchIDMessage(result) {
    if (result === 'SUCCESS') {
        $('#matchIDDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#matchIDDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#matchIDDiv').html("<p style='color: red'><c:out value="${negative}"/></p>");
        $('#matchIDDiv').css("display", "block")
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

function checkTeam1Goals() {
    var team1Goals = $("#team1Goals").val();
    if (team1Goals === "") {
        $('#team1GoalsDiv').css("display", "none");
        alert("<c:out value="${team1GoalsRequired}"/>");
        isTeam1GoalsCorrect = false;
    } else if (team1Goals < 1 || team1Goals > 6) {
        isTeam1GoalsCorrect = false;
        team1GoalsMessage("INCORRECT");
    } else {
        isTeam1GoalsCorrect = true;
        team1GoalsMessage("SUCCESS");
    }
}

function team1GoalsMessage(result) {
    if (result === 'SUCCESS') {
        $('#team1GoalsDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#team1GoalsDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#team1GoalsDiv').html("<p style='color: red'><c:out value="${negative}"/></p>");
        $('#team1GoalsDiv').css("display", "block")
    }
}

function checkTeam2Goals() {
    var team2Goals = $("#team2Goals").val();
    if (team2Goals === "") {
        $('#team2GoalsDiv').css("display", "none");
        alert("<c:out value="${team2GoalsRequired}"/>");
        isTeam2GoalsCorrect = false;
    } else if (team2Goals < 1 || team2Goals > 6) {
        isTeam2GoalsCorrect = false;
        team2GoalsMessage("INCORRECT");
    } else {
        isTeam2GoalsCorrect = true;
        team2GoalsMessage("SUCCESS");
    }
}

function team2GoalsMessage(result) {
    if (result === 'SUCCESS') {
        $('#team2GoalsDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#team2GoalsDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#team2GoalsDiv').html("<p style='color: red'><c:out value="${negative}"/></p>");
        $('#team2GoalsDiv').css("display", "block")
    }
}

function changeFormAction() {
    var result = isMatchIDCorrect && isTeam1IDCorrect && isTeam2IDCorrect && isTeam1GoalsCorrect && isTeam2GoalsCorrect;
    if (result) {
        var form = document.getElementById('createForm');
        form.action = "<c:url value="/main/create/result"/>";
        form.submit();
    } else {
        alert("<c:out value="${createData}"/>");
    }
}