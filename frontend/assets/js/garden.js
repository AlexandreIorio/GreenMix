import {chargerApiUrl} from './utils.js';
import {getProfile} from './utils.js';

document.addEventListener('DOMContentLoaded', async function () {
    await populateGarden(await getProfile());
});


window.update = async function () {
    await populateGarden(await getProfile());
};

async function populateGarden(profile) {
    let API_BASE_URL = await chargerApiUrl();
    const API_ENDPOINT = "/profile";

    console.log("Fonction de populateGarden appelée !");
    let endpoint = `${API_BASE_URL}${API_ENDPOINT}`;

    const gardenContainer = document.getElementById('garden');

    profile.plants.forEach((plant) => {
        if (plant) {
            if (document.getElementById(`plant-${plant.id}`)) {
                return;
            }

            let plantElement = document.createElement('div');
            plantElement.setAttribute('id', `plant-${plant.id}`);
            plantElement.classList.add('plant');

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', '../assets/images/' + plant.plantType.name.toLowerCase() + '.png');
            imageElement.classList.add('plant-image');
            imageElement.setAttribute('alt', plant.plantType.name);
            plantElement.appendChild(imageElement);

            let nameElement = document.createElement('div');
            nameElement.textContent = plant.plantType.name;
            nameElement.classList.add('plant-name');
            nameElement.classList.add('text');
            plantElement.appendChild(nameElement);

            let timeElement = document.createElement('div');
            let timeId = `time-${plant.id}`;
            timeElement.setAttribute('id', timeId);
            timeElement.classList.add('plant-time');
            timeElement.classList.add('text');
            plantElement.appendChild(timeElement);

            let remainingTime = reminingTime(plant);
            let timer = setInterval(function () {
                updateRemainingTime(plant, timeElement, timer);
            }, 1000);

            gardenContainer.appendChild(plantElement);
        }
    });
}

function reminingTime(plant) {
    let now = Math.round(new Date().getTime() / 1000);
    let endTime = plant.plantingTime + plant.plantType.duration;
    let remainingTime = endTime - now;
    return (remainingTime);
}

function updateRemainingTime(plant, timeElement, timer) {
    let remainingTimeSeconds = reminingTime(plant);
    if (remainingTimeSeconds <= 0) {
        clearInterval(timer);
        let card = document.getElementById(`plant-${plant.id}`);
        card.classList.add('plant-harvest');
        // card.style.boxShadow = "#4caf50 0px 0px 5px 5px;";
        card.onclick = function () {
            harvestPlant(plant.id);
        };
        timeElement.textContent = "Récolter";

    } else {
        let hours = Math.floor((remainingTimeSeconds % (24 * 60 * 60)) / (60 * 60));
        let minutes = Math.floor((remainingTimeSeconds % (60 * 60)) / 60);
        let seconds = Math.floor(remainingTimeSeconds % 60);
        timeElement.textContent = `${hours}h ${minutes}m ${seconds}s`;
    }
}

async function harvestPlant(plantIndex) {
    let API_BASE_URL = await chargerApiUrl();
    fetch(`${API_BASE_URL}/harvest/${plantIndex}`, {credentials: 'include'})
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            // Supprime l'élément de plante du DOM
            document.getElementById(`plant-${plantIndex}`).remove();
            console.log(`Plante à l'index ${plantIndex} récoltée`);
            parent.window.update();
        })
        .catch(error => {
            console.error('Error fetching the plants data:', error);
        });
}
