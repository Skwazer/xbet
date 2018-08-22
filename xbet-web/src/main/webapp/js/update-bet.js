function checkUserId() {
    var id = $("#userId").val();
    if (id === "") {
        $('#userIdDiv').css("display", "none");
        alert("<c:out value="${userIdRequired}"/>");
    }else if (id < 1) {
        $('#userIdDiv').css("display", "block");
        idMessage("NEGATIVE");
    } else {
        $('#userIdDiv').css("display", "block");
        idMessage("SUCCESS");
    }
}

function idMessage(result) {
    if (result === 'SUCCESS') {
        $('#userIdDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
    } else if (result === 'NEGATIVE') {
        $('#userIdDiv').html("<p style='color: red'><c:out value="${userIdNegative}"/></p>");
    }
}

function checkMatchId() {
    var matchId = $("#matchId").val();
    if (matchId === "") {
        $('#matchIdDiv').css("display", "none");
        alert("<c:out value="${matchIdRequired}"/>");
    } else if (matchId < 1) {
        matchIdMessage("NEGATIVE");
    } else {
        matchIdMessage("SUCCESS");
    }
}

function matchIdMessage(result) {
    if (result === 'SUCCESS') {
        $('#matchIdDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#matchIdDiv').css("display", "block")
    } else if (result === 'NEGATIVE') {
        $('#matchIdDiv').html("<p style='color: red'><c:out value="${negative}"/></p>");
        $('#matchIdDiv').css("display", "block")
    }
}

function checkBet() {
    var bet = $("#bet").val();
    if (bet === "") {
        $('#betDiv').css("display", "none");
        alert("<c:out value="${betRequired}"/>");
    } else if (bet < 1) {
        betMessage("NEGATIVE");
    } else if (bet > 5) {
        betMessage("EXCESS");
    } else {
        betMessage("SUCCESS");
    }
}

function betMessage(result) {
    if (result === 'SUCCESS') {
        $('#betDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#betDiv').css("display", "block")
    } else if (result === 'NEGATIVE') {
        $('#betDiv').html("<p style='color: red'><c:out value="${negative}"/></p>");
        $('#betDiv').css("display", "block")
    } else if (result === 'EXCESS') {
        $('#betDiv').html("<p style='color: red'><c:out value="${betExcess}"/></p>");
        $('#betDiv').css("display", "block")
    }
}

function checkMoney() {
    var money = $("#money").val();
    if (money === "") {
        $('#moneyDiv').css("display", "none");
        alert("<c:out value="${moneyRequired}"/>");
    } else if (money <= 0) {
        moneyMessage("NEGATIVE");
    } else {
        moneyMessage("SUCCESS");
    }
}

function moneyMessage(result) {
    if (result === 'SUCCESS') {
        $('#moneyDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#moneyDiv').css("display", "block")
    } else if (result === 'NEGATIVE') {
        $('#moneyDiv').html("<p style='color: red'><c:out value="${moneyNegative}"/></p>");
        $('#moneyDiv').css("display", "block")
    }
}

function checkStatus() {
    var status = $("#status").val();
    if (status === "") {
        $('#statusDiv').css("display", "none");
        alert("<c:out value="${statusRequired}"/>");
    } else if (status.trim() === "") {
        statusMessage("INCORRECT");
    } else {
        statusMessage("SUCCESS");
    }
}

function statusMessage(result) {
    if (result === 'SUCCESS') {
        $('#statusDiv').html("<p style='color: #4cae4c'><c:out value="${emailAccepted}"/></p>");
        $('#statusDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#statusDiv').html("<p style='color: red'><c:out value="${whitespace}"/></p>");
        $('#statusDiv').css("display", "block")
    }
}