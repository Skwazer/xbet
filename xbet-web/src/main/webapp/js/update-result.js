function checkMatchID() {
    var matchID = $("#matchID").val();
    if (matchID === "") {
        $('#matchIDDiv').css("display", "none");
        alert("<c:out value="${matchIDRequired}"/>");
    }  else if (matchID < 1 || matchID > 100) {
        matchIDMessage("INCORRECT");
    } else {
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

function checkTeam1Goals() {
    var team1Goals = $("#team1Goals").val();
    if (team1Goals === "") {
        $('#team1GoalsDiv').css("display", "none");
        alert("<c:out value="${team1GoalsRequired}"/>");
    } else if (team1Goals < 1 || team1Goals > 6) {
        team1GoalsMessage("INCORRECT");
    } else {
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
    } else if (team2Goals < 1 || team2Goals > 6) {
        team2GoalsMessage("INCORRECT");
    } else {
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