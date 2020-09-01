function validateEmail(emailValue) {
    //const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const emailRegex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
    return emailRegex.test(emailValue);
}

function checkForWhitespace(firstValue, secondValue) {
    // const whiteSpaceRegex = /s+/;
    // return true;
    return !(firstValue==="" || secondValue==="" || !(validateEmail(firstValue)));
}

function alertTheUser(email) {
    if ((!validateEmail(email))) {
        alert("Invalid email format.");
    } else {
        alert("Log in successful");
    }
}

function changeButtonStatus(){
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    if(checkForWhitespace(email, password)) {
        enableButton()
    } else {
        disableButton()
    }
}

function enableButton(){
    document.getElementById('login-btn').disabled = false;
}

function disableButton(){
    document.getElementById('login-btn').disabled = true;
}