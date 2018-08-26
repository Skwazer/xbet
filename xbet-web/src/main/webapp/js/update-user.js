function checkLogin() {
    var login = $("#login").val();
    var regExp = new RegExp("<c:out value="${regexp}"/>");
    if (login === "") {
        $('#loginDiv').css("display", "none");
        alert("<c:out value="${loginRequired}"/>");
    } else if (login.trim() === '') {
        $('#loginDiv').css("display", "block");
        loginMessage("INCORRECT");
    } else if (!regExp.test(login)) {
        $('#loginDiv').css("display", "block");
        loginMessage("REGEXP");
    } else {
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
}

function firstNameCheck() {
    var regExp = new RegExp("<c:out value="${regexp}"/>");
    var firstName = $("#firstName").val();
    if (firstName === "") {
        $('#firstNameDiv').css("display", "none");
        alert("<c:out value="${firstNameRequired}"/>");
    } else if (firstName.trim() === "") {
        firstNameMessage("INCORRECT");
    } else if (!regExp.test(firstName)) {
        firstNameMessage("REGEXP");
    } else {
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
    } else if (lastName.trim() === "") {
        lastNameMessage("INCORRECT");
    } else if (!regExp.test(lastName)) {
        lastNameMessage("REGEXP");
    } else {
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
    } else if (email.trim() === "") {
        emailMessage("INCORRECT");
    } else if (!regExp.test(email)) {
        emailMessage("REGEXP");
    } else {
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
    } else if (amount < 0) {
        balanceMessage('NEGATIVE');
    } else {
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

function roleCheck() {
    var role = $("#role").val();
    if (role === "") {
        $('#roleDiv').css("display", "none");
        alert("<c:out value="${roleRequired}"/>");
    } else if (role <= 0) {
        roleMessage('NEGATIVE');
    } else if (role > 10) {
        roleMessage('EXCESS');
    } else {
        roleMessage('SUCCESS');
    }
}

function roleMessage(result) {
    if (result === 'SUCCESS') {
        $('#roleDiv').html("<p style='color: #4cae4c'><c:out value="${accepted}"/></p>");
        $('#roleDiv').css("display", "block")
    } else if (result === 'NEGATIVE') {
        $('#roleDiv').html("<p style='color: red'><c:out value="${roleNegative}"/></p>");
        $('#roleDiv').css("display", "block")
    } else if (result === 'EXCESS') {
        $('#roleDiv').html("<p style='color: red'><c:out value="${roleExcess}"/></p>");
        $('#roleDiv').css("display", "block")
    }
}
