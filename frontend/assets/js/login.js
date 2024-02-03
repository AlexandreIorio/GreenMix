import {chargerApiUrl} from './utils.js';
import {chargerSiteUrl} from './utils.js';

document.addEventListener('DOMContentLoaded', (event) => {
    document.querySelector('button#btn-login').addEventListener('click', login);
});

let API_BASE_URL = await chargerApiUrl();
let WEB_BASE_URL = await chargerSiteUrl();
const API_ENDPOINT = "/connect";
const WEB_ENDPOINT = "/pages/profile.html";

async function login() {
    let tbUserName = document.getElementById("tb-username")
    let tbPassword = document.getElementById("tb-password")
    let btnSubmit = document.getElementById("btn-submit");

    console.log("Fonction de login appelée !");
    let userName = tbUserName.value;
    let passHash = CryptoJS.MD5(tbPassword.value);
    let endpoint = `${API_BASE_URL}${API_ENDPOINT}/${userName}/${passHash}`;

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


