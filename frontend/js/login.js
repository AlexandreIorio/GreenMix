
// import { API_BASE_URL} from "config.js";

const API_ENDPOINT = "/connect";

function login() {

    tbUserName = document.getElementById("tb-username")
    tbPassword = document.getElementById("tb-password")
    btnSubmit = document.getElementById("btn-submit");

    console.log("Fonction de login appelée !");
    userName = tbUserName.value;
    passHash = CryptoJS.MD5(tbPassword.value);

    fetch(`http://127.0.0.1:8080${API_ENDPOINT}/${userName}/${passHash}`, {
        method: "GET",
    })
    .then(response => {
        if (response.ok) {
            // La requête a réussi
            console.log("Réponse reçue ! 200 OK");
            window.location.href = `http://127.0.0.1:8080/profile`;
        } else if (response.status === 401) {
            console.log("Réponse reçue ! 401 Unauthorized");
            error = document.getElementById("error-message").innerHTML = "Erreur d'authentification";
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

