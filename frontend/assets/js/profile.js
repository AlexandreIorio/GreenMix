import { chargerApiUrl } from './utils.js';

document.addEventListener('DOMContentLoaded', SetValues);

async function SetValues() {

    let API_BASE_URL = await chargerApiUrl();
    const API_ENDPOINT = "/profile";

    console.log("Fonction de login appelée !");
    let endpoint = `${API_BASE_URL}${API_ENDPOINT}`;

    try {
        const response = await fetch(endpoint, {
            credentials: 'include' // Inclure les cookies dans la demande
        });

        if (!response.ok) {
            throw new Error('La requête a échoué');
        }

        const data = await response.json(); // Attendre la résolution de la promesse JSON

        document.getElementById("username").innerHTML = data.username;
        document.getElementById("wallet-amount").innerHTML = data.wallet;
    } catch (error) {
        console.error('Une erreur s\'est produite :', error);
    }
}
