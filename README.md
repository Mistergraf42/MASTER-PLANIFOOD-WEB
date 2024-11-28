# DevWebAvancée

## Sujet du projet
Le projet consiste à créer une plateforme permettant la mise en relation entre restaurateurs et clients.

Les **restaurateurs** pourront :
- Créer un profil pour leur restaurant, offrant une vitrine virtuelle.
- Activer ou désactiver la réservation de tables.
- Permettre les commandes en ligne.
- Publier des actualités et des nouveautés.
- Créer et gérer des offres d'emploi, facilitant le recrutement de personnel, surtout pendant la haute saison.

Ils pourront personnaliser tous les contenus liés à leur restaurant, notamment :
- Le nombre de tables disponibles.
- Les jours et heures d'ouverture, avec un ou deux services par jour.
- La création d'une carte en ligne interactive.
- La réception des commandes et réservations par e-mail, avec la possibilité de les recevoir via une requête API, permettant une intégration directe dans leur système.

Les **clients** auront la possibilité de :
- Réserver une table et voir leurs différentes réservations.
- Visualiser les restaurants disponibles sur une carte (fonctionnalité similaire à celle de Booking pour les hôtels).
- Interagir avec tous les contenus et fonctionnalités proposés par les restaurateurs.

## Démarrer le projet

Le projet utilise le framework **Spring Boot** sous **JDK 23** et une base de données **MySQL** qui doit être créée au préalable avec les informations suivantes :

- **Nom de la base** : `devWebAvancee`
- **Utilisateur** : `userWeb`
- **Mot de passe** : `multipass`

L'utilisateur doit avoir tous les droits sur la base de données.

Avant de démarrer le projet, il est recommandé de se placer sur la branche `save`, qui stocke les versions stables du projet. Vous pouvez également utiliser la branche `develop` pour accéder aux derniers ajouts validés.

De plus, assurez-vous de charger les dépendances Maven (elles sont présentes dans le fichier `pom.xml`).

Une fois tout cela en place, vous pouvez démarrer le projet dans **VS Code**, **IntelliJ**, ou tout autre IDE compatible (des manipulations supplémentaires peuvent être nécessaires pour certains IDE).

## Accéder à la page d'accueil

Pour accéder à la page d'accueil une fois le projet démarré, il suffit de taper `http://localhost:8080` dans votre navigateur.

Vous pourrez accéder au contenu de base du site avec certaines fonctionnalités. Pour le moment, aucune donnée de test n'est chargée, mais c'est prévu.

Vous pouvez créer un compte en cliquant sur le bouton de profil (en haut à droite). Après la création de votre compte, il est également possible de créer un compte professionnel et de créer votre propre restaurant, qui apparaîtra dans la barre de recherche dès sa création.
