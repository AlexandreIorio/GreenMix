import { chargerApiUrl } from './utils.js';
import { getProfile } from './utils.js';

document.addEventListener('DOMContentLoaded', async function () {
    await SetValues(getProfile());
});

document.getElementById("btn-add-space").addEventListener("click", addSpace);

window.update = async function() {
    await SetValues();
};

async function SetValues() {

    let API_BASE_URL = await chargerApiUrl();
    const API_ENDPOINT = "/profile";

    console.log("Fonction de SetValue appelée !");
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
        document.getElementById("wallet-amount").innerHTML = new Intl.NumberFormat().format(data.wallet);
        document.getElementById("xp-amount").innerHTML = `${new Intl.NumberFormat().format(data.xp)} XP`;
        document.getElementById("level-amount").innerHTML = `Level ${data.level}`;

        let progressXp = Math.round(((data.xp - data.currentLevelXp) / (data.nextLevelXp - data.currentLevelXp))*100);
        document.getElementById("progress-bar-value").style.width = `${progressXp}%`;
        let currentLevelXp = new Intl.NumberFormat().format(data.xp - data.currentLevelXp);
        let nextLevelXp = new Intl.NumberFormat().format(data.nextLevelXp - data.currentLevelXp);
        document.getElementById("progress-bar-text").innerHTML = `${currentLevelXp} / ${nextLevelXp} XP : ${progressXp}%`;
        document.getElementById("btn-add-space").innerHTML = "Ajouter un espace pour " + data.spacePrice + "$";

    } catch (error) {
        console.error('Une erreur s\'est produite :', error);
    }
}

async function addSpace() {
    let API_BASE_URL = await chargerApiUrl();
    const API_ENDPOINT = "/addspace";

    console.log("Fonction de addSpace appelée !");
    let endpoint = `${API_BASE_URL}${API_ENDPOINT}`;

    try {
        const response = await fetch(endpoint, {
            credentials: 'include' // Inclure les cookies dans la demande
        });

        if (!response.ok) {
            throw new Error('La requête a échoué');
        }
        update();
    } catch (error) {
        console.error('Une erreur s\'est produite :', error);
    }
}


