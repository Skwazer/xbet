// var isLoginCorrect = true;
var isFirstNameCorrect = true;
var isLastNameCorrect = true;
var isEmailCorrect = true;
var isBalanceCorrect = true;

/*function checkLogin() {
    var login = $("#login").val();
    var regExp = new RegExp("<c:out value="${regexp}"/>");
    if (login === "") {
        isLoginCorrect = false;
        $('#loginDiv').css("display", "none");
        alert("<c:out value="${loginRequired}"/>");
    } else if (login.trim() === '') {
        isLoginCorrect = false;
        $('#loginDiv').css("display", "block");
        loginMessage("INCORRECT");
    } else if (!regExp.test(login)) {
        isLoginCorrect = false;
        $('#loginDiv').css("display", "block");
        loginMessage("REGEXP");
    } else {
        isLoginCorrect = true;
        $('#loginDiv').css("display", "block");
        loginMessage("SUCCESS");
    }
}

function loginMessage(result) {
    if (result === 'SUCCESS') {
        $('#loginDiv').html("<p style='color: #4cae4c'><c:out value="${loginAccepted}"/></p>");
    } else if (result === 'INCORRECT') {
        $('#loginDiv').html("<p style='color: red'><c:out value="${loginWhitespace}"/></p>");
    } else if (result === 'REGEXP') {
        $('#loginDiv').html("<p style='color: red'><c:out value="${loginRegexp}"/></p>");
    }
}*/

function firstNameCheck() {
    var regExp = new RegExp("<c:out value='${regexp}'/>");
    var firstName = $("#firstName").val();
    if (firstName === "") {
        isFirstNameCorrect = false;
        $('#firstNameDiv').css("display", "none");
        alert("<c:out value='${firstNameRequired}'/>");
    } else if (firstName.trim() === "") {
        isFirstNameCorrect = false;
        firstNameMessage("INCORRECT");
    } else if (!regExp.test(firstName)) {
        isFirstNameCorrect = false;
        firstNameMessage("REGEXP");
    } else {
        isFirstNameCorrect = true;
        firstNameMessage("SUCCESS");
    }
}

function firstNameMessage(result) {
    if (result === 'SUCCESS') {
        $('#firstNameDiv').html("<p style='color: #4cae4c'><c:out value='${firstNameAccepted}'/></p>");
        $('#firstNameDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#firstNameDiv').html("<p style='color: red'><c:out value='${firstNameWhitespace}'/></p>");
        $('#firstNameDiv').css("display", "block")
    } else if (result === 'REGEXP') {
        $('#firstNameDiv').html("<p style='color: red'><c:out value='${firstNameRegexp}'/></p>");
        $('#firstNameDiv').css("display", "block")
    }
}

function lastNameCheck() {
    var regExp = new RegExp("<c:out value='${regexp}'/>");
    var lastName = $("#lastName").val();
    if (lastName === "") {
        isLastNameCorrect = false;
        $('#lastNameDiv').css("display", "none");
        alert("<c:out value='${lastNameRequired}'/>");
    } else if (lastName.trim() === "") {
        isLastNameCorrect = false;
        lastNameMessage("INCORRECT");
    } else if (!regExp.test(lastName)) {
        isLastNameCorrect = false;
        lastNameMessage("REGEXP");
    } else {
        isLastNameCorrect = true;
        lastNameMessage("SUCCESS");
    }
}

function lastNameMessage(result) {
    if (result === 'SUCCESS') {
        $('#lastNameDiv').html("<p style='color: #4cae4c'><c:out value='${lastNameAccepted}'/></p>");
        $('#lastNameDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#lastNameDiv').html("<p style='color: red'><c:out value='${lastNameWhitespace}'/></p>");
        $('#lastNameDiv').css("display", "block")
    } else if (result === 'REGEXP') {
        $('#lastNameDiv').html("<p style='color: red'><c:out value='${lastNameRegexp}'/></p>");
        $('#lastNameDiv').css("display", "block")
    }
}

function emailCheck() {
    var regExp = new RegExp("<c:out value='${regexpEmail}'/>");
    var email = $("#email").val();
    if (email === "") {
        isEmailCorrect = false;
        $('#emailDiv').css("display", "none");
        alert("<c:out value='${emailRequired}'/>");
    } else if (email.trim() === "") {
        isEmailCorrect = false;
        emailMessage("INCORRECT");
    } else if (!regExp.test(email)) {
        isEmailCorrect = false;
        emailMessage("REGEXP");
    } else {
        isEmailCorrect = true;
        emailMessage("SUCCESS");
    }
}

function emailMessage(result) {
    if (result === 'SUCCESS') {
        $('#emailDiv').html("<p style='color: #4cae4c'><c:out value='${emailAccepted}'/></p>");
        $('#emailDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#emailDiv').html("<p style='color: red'><c:out value='${emailWhitespace}'/></p>");
        $('#emailDiv').css("display", "block")
    } else if (result === 'REGEXP') {
        $('#emailDiv').html("<p style='color: red'><c:out value='${emailRegexp}'/></p>");
        $('#emailDiv').css("display", "block")
    }
}

function balanceCheck() {
    var amount = $("#balance").val();
    if (amount === "") {
        isBalanceCorrect = false;
        $('#balanceDiv').css("display", "none");
        alert("<c:out value='${balanceRequired}'/>");
    } else if (amount < 0) {
        isBalanceCorrect = false;
        balanceMessage('NEGATIVE');
    } else {
        isBalanceCorrect = true;
        balanceMessage('SUCCESS');
    }
}

function balanceMessage(result) {
    if (result === 'SUCCESS') {
        $('#balanceDiv').html("<p style='color: #4cae4c'><c:out value='${accepted}'/></p>");
        $('#balanceDiv').css("display", "block")
    } else if (result === 'NEGATIVE') {
        $('#balanceDiv').html("<p style='color: red'><c:out value='${balanceNegative}'/></p>");
        $('#balanceDiv').css("display", "block")
    }
}


function changeFormAction() {
    var result = isEmailCorrect && isFirstNameCorrect && isLastNameCorrect && isBalanceCorrect;
    if (result) {
        var form = document.getElementById('createForm');
        form.action = "<c:url value='/main/change/user'/>";
        form.submit();
    } else {
        alert("<c:out value='${createData}'/>");
    }
}