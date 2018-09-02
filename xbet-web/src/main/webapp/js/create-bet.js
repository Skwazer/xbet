var isUserIdCorrect = false;
var isMatchIdCorrect = false;
var isBetCorrect = false;
var isMoneyCorrect = false;
var isStatusCorrect = false;

function checkUserId() {
    var id = $("#userId").val();
    if (id === "") {
        $('#userIdDiv').css("display", "none");
        alert("<c:out value="${userIdRequired}"/>");
        isUserIdCorrect = false;
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
        isUserIdCorrect = true;
    } else if (result === 'NEGATIVE') {
        $('#userIdDiv').html("<p style='color: red'><c:out value="${userIdNegative}"/></p>");
        isUserIdCorrect = false;
    }
}

function checkMatchId() {
    var matchId = $("#matchId").val();
    if (matchId === "") {
        $('#matchIdDiv').css("display", "none");
        alert("<c:out value="${matchIdRequired}"/>");
        isMatchIdCorrect = false;
    } else if (matchId < 1) {
        isMatchIdCorrect = false;
        matchIdMessage("NEGATIVE");
    } else {
        isMatchIdCorrect = true;
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
        isBetCorrect = false;
    } else if (bet < 1) {
        isBetCorrect = false;
        betMessage("NEGATIVE");
    } else if (bet > 5) {
        isBetCorrect = false;
        betMessage("EXCESS");
    } else {
        isBetCorrect = true;
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
        isMoneyCorrect = false;
    } else if (money <= 0) {
        isMoneyCorrect = false;
        moneyMessage("NEGATIVE");
    } else {
        isMoneyCorrect = true;
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
        isStatusCorrect = false;
    } else if (status.trim() === "") {
        isStatusCorrect = false;
        statusMessage("INCORRECT");
    } else {
        isStatusCorrect = true;
        statusMessage("SUCCESS");
    }
}

function statusMessage(result) {
    if (result === 'SUCCESS') {
        $('#statusDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#statusDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#statusDiv').html("<p style='color: red'><c:out value="${whitespace}"/></p>");
        $('#statusDiv').css("display", "block")
    }
}

function changeFormAction() {
    var result = isUserIdCorrect && isMatchIdCorrect && isBetCorrect && isMoneyCorrect && isStatusCorrect;
    if (result) {
        var form = document.getElementById('createForm');
        form.action = "<c:url value="/main/create/bet"/>";
        form.submit();
    } else {
        alert("<c:out value="${createData}"/>");
    }
}