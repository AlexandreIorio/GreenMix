document.addEventListener('DOMContentLoaded', (event) => {
    const loginDiv = document.getElementById('btn-login');
    const signupDiv = document.getElementById('btn-signup');

    loginDiv.addEventListener('click', login);
    signupDiv.addEventListener('click', signup);
});

async function login() {
    window.location.href = "login.html";
}

async function signup() {
    window.location.href = "signup.html";
}
