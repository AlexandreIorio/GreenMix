# Définition des données des plantes
plantes = [
    ("TURBOFEUILLASSE", 1, 2, 1.0, 1),
    ("BITBUD", 5, 5, 5, 2),
    ("DEBUGDREAM", 15, 1, 13, 30),
    ("HASHHACK", 10, 3, 10.0, 7),
    ("BYTEBLOOM", 20, 1, 30.0, 35),
    ("KERNELHAZE", 30, 1, 50, 50),
    ("PIXELPOT", 40, 8, 20.0, 8),
    ("JAVAJOINT", 50, 5, 25.0, 20),
    ("CYBERCUSH", 60, 3, 30.0, 42.0),
    ("DATAWEED", 70, 5, 35.0, 32),
    ("CLOUDKUSH", 400, 8, 180, 30),
    ("QUANTUMSKUNK", 3600, 10, 800, 150)
]

# Calcul de la rentabilité pour chaque plante
rentabilites = {}
for plante in plantes:
    nom, duree_pousse, nb_fruits, prix_graine, prix_vente_fruit = plante
    rentabilite = (prix_vente_fruit * nb_fruits) - prix_graine
    rent_par_sec = round((rentabilite / duree_pousse), 2)
    xp_par_seconde = round(prix_graine / duree_pousse, 2)
    rentabilites[nom] = (rentabilite, rent_par_sec, prix_graine, xp_par_seconde)

# Affichage des résultats sous forme de tableau
print("{:<15} {:<15} {:<15} {:<15} {:<15}".format("Plante", "Rentabilité", "Prix Graine", "Rentabilité/Seconde", "XP/Seconde"))
print("-" * 75)
for nom, (rentabilite, rent_par_sec, prix_graine, xp_par_seconde) in rentabilites.items():
    print("{:<15} {:<15} {:<15} {:<20} {:<15}".format(nom, rentabilite, prix_graine, rent_par_sec, xp_par_seconde))
