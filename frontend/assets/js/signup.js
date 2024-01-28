const API_ENDPOINT = "/signin";

function signin() {

    let tbUserName = document.getElementById("tb-username");
    let tbPassword = document.getElementById("tb-password");
    let btnSubmit = document.getElementById("btn-submit");

    console.log("Fonction de login appelée !");
    let userName = tbUserName.value;
    let passHash = CryptoJS.MD5(tbPassword.value);



}