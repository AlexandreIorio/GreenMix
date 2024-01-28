document.addEventListener('DOMContentLoaded', (event) => {
    document.querySelector('#btn-connect').addEventListener('click', connect);
    document.querySelector('#btn-signup').addEventListener('click', signup);
});

function connect() {
    window.location.href = `pages/login.html`;
}

function signup() {
    window.location.href = `pages/signup.html`;
}