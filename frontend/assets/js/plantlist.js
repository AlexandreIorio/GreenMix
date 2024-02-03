import {chargerApiUrl} from './utils.js';

async function fillTableWithData() {
    let API_BASE_URL = await chargerApiUrl();
    const API_ENDPOINT = "/plantlist";


    fetch(`${API_BASE_URL}${API_ENDPOINT}`)
        .then(response => response.json())
        .then(plantsData => {
            const tableBody = document.getElementById('plantsTable').getElementsByTagName('tbody')[0];
            plantsData.forEach(plant => {
                let row = tableBody.insertRow();
                let cellName = row.insertCell();
                    cellName.textContent = plant.name;
                    cellName.className = "col-left";
                    let cellDuration = row.insertCell();
                    cellDuration.textContent = plant.duration + "s";
                    cellDuration.className = "col-center";
                    let cellHavrvest = row.insertCell();
                    cellHavrvest.textContent = plant.harvest;
                    cellHavrvest.className = "col-center";
                    let cellBuy = row.insertCell();
                    cellBuy.textContent = plant.purchasePrice+ "\t$";
                    cellBuy.className = "col-center" ;
                    let cellSell = row.insertCell();
                    cellSell.textContent = plant.sellingPrice+ "\t$";
                    cellSell.className = "col-center";

                    let cellButton = row.insertCell();
                    let button = document.createElement("button");
                    button.setAttribute('data-name', plant.name);
                    button.textContent = "planter";
                    button.className = "col-right";
                    button.addEventListener("click", grow);
                    cellButton.appendChild(button);


            });
        })
        .catch(error => {
            console.error('Error fetching the plants data:', error);
        });
}

async function grow() {
    let API_BASE_URL = await chargerApiUrl();

    let plantName = this.getAttribute('data-name');
    // Utilisez des backticks pour intégrer la variable plantName dans l'URL
    const API_ENDPOINT = `/grow/${plantName}`;

    fetch(`${API_BASE_URL}${API_ENDPOINT}`, {
        method: 'GET', // Spécifier que c'est une requête GET
        headers: {
            'Accept': 'application/json' // Assurez-vous que le serveur sait que le client attend du JSON
        },
        credentials: 'include' // Inclure les cookies dans la demande
    })
        .then(async response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            await parent.window.update();
        })
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}


// Exécutez la fonction pour remplir le tableau une fois que le DOM est chargé
document.addEventListener('DOMContentLoaded', fillTableWithData);