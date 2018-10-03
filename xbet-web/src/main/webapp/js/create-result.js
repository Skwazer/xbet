var isTeam1GoalsCorrect = false;
var isTeam2GoalsCorrect = false;

function checkTeam1Goals() {
    var team1Goals = $("#team1Goals").val();
    if (team1Goals === "") {
        $('#team1GoalsDiv').css("display", "none");
        alert("<c:out value="${team1GoalsRequired}"/>");
        isTeam1GoalsCorrect = false;
    } else if (team1Goals < 0 || team1Goals > 6) {
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
    } else if (team2Goals < 0 || team2Goals > 6) {
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
    var result = isTeam1GoalsCorrect && isTeam2GoalsCorrect;
    if (result) {
        var form = document.getElementById('createForm');
        form.action = "<c:url value="/main/create/result"/>";
        form.submit();
    } else {
        alert("<c:out value="${createData}"/>");
    }
}