
import {chargerApiUrl} from "./utils.js";

async function setScores() {

    let API_BASE_URL = await chargerApiUrl();
    const API_ENDPOINT = "/rank";

    console.log("Fonction de SetRAnk appelée !");
    let endpoint = `${API_BASE_URL}${API_ENDPOINT}`;

    try {
        const response = await fetch(endpoint)
        if (!response.ok) {
            throw new Error('La requête a échoué');
        }

        const players = await response.json(); // Attendre la résolution de la promesse JSON

        const table = document.getElementById('scoresTable');
        let i = 1;
        players.forEach(player => {
            const tr = document.createElement('tr');
            tr.innerHTML = `<td>#${i}</td><td>${player.name}</td><td>${player.points}</td>
                            <td>${player.harvested}</td><td>${player.wallet}</td>`;
            table.appendChild(tr);
            ++i;
        });
    } catch (error) {
        console.error('Une erreur s\'est produite :', error);
    }
}




// Appel de la fonction pour afficher les scores
setScores();
