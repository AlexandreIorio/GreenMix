// Fonction asynchrone pour charger l'URL de l'API à partir du fichier de configuration
export async function chargerApiUrl() {
    try {
        const response = await fetch('../config.json');
        const data = await response.json();
        const apiUrl = data.config.apiUrl;
        console.log(apiUrl); // Utiliser l'URL de l'API dans votre application
        return apiUrl; // Retourner l'URL de l'API comme une chaîne
    } catch (error) {
        console.error("Erreur lors du chargement du fichier de configuration", error);
        return ""; // Retourner une chaîne vide en cas d'erreur
    }
}