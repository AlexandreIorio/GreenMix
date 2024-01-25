import { API_BASE_URL} from "config.js";

const API_ENDPOINT = "/signin";

function signin() {

    tbUserName = document.getElementById("tb-username");
    tbPassword = document.getElementById("tb-password");
    btnSubmit = document.getElementById("btn-submit");

    console.log("Fonction de login appelée !");
    userName = tbUserName.value;
    passHash = CryptoJS.MD5(tbPassword.value);

    
}