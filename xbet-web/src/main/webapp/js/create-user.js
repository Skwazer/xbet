var isLoginCorrect = false;
var isPasswordCorrect = false;
var isFirstNameCorrect = false;
var isLastNameCorrect = false;
var isEmailCorrect = false;

function checkLogin() {
    var login = $("#login").val();
    var regExp = new RegExp("<c:out value="${regexp}"/>");
    if (login === "") {
        $('#loginDiv').css("display", "none");
        alert("<c:out value="${loginRequired}"/>");
        isLoginCorrect = false;
    } else if (login.trim() === '') {
        $('#loginDiv').css("display", "block");
        loginMessage("INCORRECT");
        isLoginCorrect = false;
    } else if (!regExp.test(login)) {
        $('#loginDiv').css("display", "block");
        loginMessage("REGEXP");
    } else {
        $.ajax({
            url: "<c:url value="/main/registration"/>",
            type: "POST",
            data: {
                key: login
            },
            success: function (data, textStatus, request) {
                var result = request.getResponseHeader('result');
                if (result !== null && result !== "") {
                    loginMessage(result);
                    $('#loginDiv').css("display", "block");
                } else {
                    $('#loginDiv').css("display", "none");
                    $('#loginDiv').html("");
                    alert("<c:out value="${loginError}"/>");
                    isLoginCorrect = false;
                }
            }
        });
    }
}

function loginMessage(result) {
    if (result === 'SUCCESS') {
        $('#loginDiv').html("<p style='color: #4cae4c'><c:out value="${loginAccepted}"/></p>");
        isLoginCorrect = true
    } else if (result === 'FAILURE') {
        $('#loginDiv').html("<p style='color: red'><c:out value="${loginExists}"/></p>");
        isLoginCorrect = false;
    } else if (result === 'INCORRECT') {
        $('#loginDiv').html("<p style='color: red'><c:out value="${loginWhitespace}"/></p>");
        isLoginCorrect = false;
    } else if (result === 'REGEXP') {
        $('#loginDiv').html("<p style='color: red'><c:out value="${loginRegexp}"/></p>");
        isLoginCorrect = false;
    }
}

function checkPassword() {
    var password = $("#password").val();
    var login = $("#login").val();
    var regExp = new RegExp("^[A-Z](?=.*[a-z])(?=.*[0-9])(?=.{7,})");
    if (password === "") {
        $('#passwordDiv').css("display", "none");
        alert("<c:out value="${passwordRequired}"/>");
        isPasswordCorrect = false;
    } else if (password.trim() === "") {
        passwordMessage("INCORRECT");
        isPasswordCorrect = false;
    } else if (login === "") {
        $('#passwordDiv').css("display", "none");
        alert("<c:out value="${loginRequired}"/>");
        isPasswordCorrect = false;
    } else if (!regExp.test(password)) {
        isPasswordCorrect = false;
        passwordMessage("REGEXP");
    } else {
        isPasswordCorrect = true;
        passwordMessage("SUCCESS");
    }
}

function passwordMessage(result) {
    if (result === 'SUCCESS') {
        $('#passwordDiv').html("<p style='color: #4cae4c'><c:out value="${passwordAccepted}"/></p>");
        $('#passwordDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#passwordDiv').html("<p style='color: red'><c:out value="${passwordWhitespace}"/></p>");
        $('#passwordDiv').css("display", "block")
    } else if (result === 'REGEXP') {
        $('#passwordDiv').html("<p style='color: red'><c:out value="${passwordRegexp}"/></p>");
        $('#passwordDiv').css("display", "block")
    }
}

function firstNameCheck() {
    var regExp = new RegExp("<c:out value="${regexp}"/>");
    var firstName = $("#firstName").val();
    if (firstName === "") {
        $('#firstNameDiv').css("display", "none");
        alert("<c:out value="${firstNameRequired}"/>");
        isFirstNameCorrect = false;
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
        $('#firstNameDiv').html("<p style='color: #4cae4c'><c:out value="${firstNameAccepted}"/></p>");
        $('#firstNameDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#firstNameDiv').html("<p style='color: red'><c:out value="${firstNameWhitespace}"/></p>");
        $('#firstNameDiv').css("display", "block")
    } else if (result === 'REGEXP') {
        $('#firstNameDiv').html("<p style='color: red'><c:out value="${firstNameRegexp}"/></p>");
        $('#firstNameDiv').css("display", "block")
    }
}

function lastNameCheck() {
    var regExp = new RegExp("<c:out value="${regexp}"/>");
    var lastName = $("#lastName").val();
    if (lastName === "") {
        $('#lastNameDiv').css("display", "none");
        alert("<c:out value="${lastNameRequired}"/>");
        isLastNameCorrect = false;
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
        $('#lastNameDiv').html("<p style='color: #4cae4c'><c:out value="${lastNameAccepted}"/></p>");
        $('#lastNameDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#lastNameDiv').html("<p style='color: red'><c:out value="${lastNameWhitespace}"/></p>");
        $('#lastNameDiv').css("display", "block")
    } else if (result === 'REGEXP') {
        $('#lastNameDiv').html("<p style='color: red'><c:out value="${lastNameRegexp}"/></p>");
        $('#lastNameDiv').css("display", "block")
    }
}

function emailCheck() {
    var regExp = new RegExp("<c:out value="${regexpEmail}"/>");
    var email = $("#email").val();
    if (email === "") {
        $('#emailDiv').css("display", "none");
        alert("<c:out value="${emailRequired}"/>");
        isEmailCorrect = false;
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
        $('#emailDiv').html("<p style='color: #4cae4c'><c:out value="${emailAccepted}"/></p>");
        $('#emailDiv').css("display", "block")
    } else if (result === 'INCORRECT') {
        $('#emailDiv').html("<p style='color: red'><c:out value="${emailWhitespace}"/></p>");
        $('#emailDiv').css("display", "block")
    } else if (result === 'REGEXP') {
        $('#emailDiv').html("<p style='color: red'><c:out value="${emailRegexp}"/></p>");
        $('#emailDiv').css("display", "block")
    }
}

function balanceCheck() {
    var amount = $("#balance").val();
    if (amount === "") {
        $('#balanceDiv').css("display", "none");
        alert("<c:out value="${balanceRequired}"/>");
        isBalanceCorrect = false;
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
        $('#balanceDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#balanceDiv').css("display", "block")
    } else if (result === 'NEGATIVE') {
        $('#balanceDiv').html("<p style='color: red'><c:out value="${balanceNegative}"/></p>");
        $('#balanceDiv').css("display", "block")
    }
}


function changeFormAction() {
    var result = isEmailCorrect && isLoginCorrect && isPasswordCorrect && isFirstNameCorrect && isLastNameCorrect
        && isBalanceCorrect;
    if (result) {
        var form = document.getElementById('createForm');
        form.action = "<c:url value="/main/create/user"/>";
        form.submit();
    } else {
        alert("<c:out value="${createData}"/>");
    }
}