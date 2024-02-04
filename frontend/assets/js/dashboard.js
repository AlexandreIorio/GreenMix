import {chargerApiUrl} from './utils.js';
import {chargerSiteUrl} from './utils.js';
import {getProfile} from './utils.js';



document.addEventListener('DOMContentLoaded', function() {
    window.update();

    let btnGarden = document.getElementById('btn-garden');
    btnGarden.addEventListener('click', function() {
        var pageFrame = document.getElementById('page-frame');
        pageFrame.src = 'farmfield.html';
    });

    let varbtnRanking = document.getElementById('btn-rank');
    varbtnRanking.addEventListener('click', function() {
        var pageFrame = document.getElementById('page-frame');
        pageFrame.src = 'rank.html';
    });

});

window.update = async function() {
    document.getElementById('menu-profil-frame').contentWindow.update();
    let btnGardenText = document.getElementById('btn-garden-text');
    let profile = await getProfile()
    btnGardenText.textContent = "Jardin "+ profile.plants.length +"/"+ profile.gardenSize;
};
