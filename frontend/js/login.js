
import { API_BASE_URL} from "config.js";

const API_ENDPOINT = "/connect";

function login() {

    tbUserName = document.getElementById("tb-username");
    tbPassword = document.getElementById("tb-password");
    btnSubmit = document.getElementById("btn-submit");

    console.log("Fonction de login appelée !");
    userName = tbUserName.value;
    passHash = CryptoJS.MD5(tbPassword.value);


    fetch("`${API_BASE_URL}${API_ENDPOINT}/${userName}/${passHash}`;", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (response.ok) {
            // La requête a réussi
            window.location.href = "${API_BASE_URL}/profil.html";
        } else {
            throw new Error("Échec de la requête");
        }
    })
    .then(data => {
        // Traitez la réponse du serveur ici
        console.log(data);
    })
    .catch(error => {
        console.error("Erreur lors de la requête POST : ", error);
    });

}

