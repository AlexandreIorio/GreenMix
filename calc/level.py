import matplotlib.pyplot as plt
import numpy as np

# Définir la constante
xpConstante = 2
progress_factore = 3
placePriceConstante = 0.02

# Générer les niveaux de 1 à 100
unit = np.arange(1, 101)

# Calculer les valeurs de la fonction pour chaque niveau
#xp = np.round((unit / xpConstante) ** (1 + np.log((unit)**xpConstante)))
xp = (unit ** progress_factore) * xpConstante
placePrice = np.round(((unit + (1 / placePriceConstante)) * (unit / placePriceConstante)) ** np.log((unit**(1/2)) + 1))

for level, value in zip(unit, xp):
    print(f"Niveau: {level}\txp: {int(value)}")

# for nb, value in zip(unit, placePrice):
#     print(f"nb: {nb}\tprix: {int(value)}")
# Créer le graphique
plt.scatter(unit, xp, s=1)
#plt.scatter(unit, placePrice, s=1)

plt.xlabel('unité')
plt.ylabel('Valeur de la fonction')
plt.title('Graphique de la fonction (level/xpConstante)*(level/xpConstante)*(1+log(1+1*xpConstante))')
plt.grid(True)

# Afficher le graphique
plt.show()