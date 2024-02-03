import {chargerApiUrl} from './utils.js';
import {chargerSiteUrl} from './utils.js';

document.addEventListener('DOMContentLoaded', (event) => {
    document.querySelector('button#btn-signup').addEventListener('click', signup);
});



async function signup() {

    const API_BASE_URL = await chargerApiUrl();
    const WEB_BASE_URL = await chargerSiteUrl();
    const API_ENDPOINT = "/signup";
    const WEB_ENDPOINT = "/pages/login.html";

    let tbUserName = document.getElementById("tb-username");
    let tbPassword = document.getElementById("tb-password");
    let tbPasswordConfirm = document.getElementById("tb-password-confirm");

    let userName = tbUserName.value;
    let passHash = CryptoJS.MD5(tbPassword.value);
    let endpoint = `${API_BASE_URL}${API_ENDPOINT}/${userName}/${passHash}`;

    if (userName === "" || passHash === "") {
        let error = document.getElementById("error-message").innerHTML = "Veuillez remplir tous les champs";
        return;
    }
    if (userName.length < 3 || userName.length > 20) {
        let error = document.getElementById("error-message").innerHTML = "Le nom d'utilisateur doit être compris entre 3 et 20 caractères";
        return;
    }
    if (passHash.length < 6 || passHash.length > 20) {
        let error = document.getElementById("error-message").innerHTML = "Le mot de passe doit être compris entre 6 et 20 caractères";
        return;
    }
    if (tbPassword.value !== tbPasswordConfirm.value) {
        let error = document.getElementById("error-message").innerHTML = "Les mots de passe ne correspondent pas";
        return;
    }

    fetch(endpoint, {
        credentials: 'include' // Inclure les cookies dans la demande
    })
        .then(response => {
            if (response.ok) {
                // La requête a réussi
                console.log("Réponse reçue ! 200 OK");
                let redirection = `${WEB_BASE_URL}${WEB_ENDPOINT}`;
                window.location.href = redirection;
            } else if (response.status === 401) {
                console.log("Réponse reçue ! 401 Unauthorized");
                let error = document.getElementById("error-message").innerHTML = "Erreur d'authentification";
            }
        })
        .then(data => {
            // Traitez la réponse du serveur ici
            console.log(data);
        })
        .catch(error => {
            console.error("Erreur lors de la requête GET : ", error);
        });





}