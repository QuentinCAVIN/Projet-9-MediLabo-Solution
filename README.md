# MediLabo-Solution

Outil déstiné aux médecin, pour aider à détecter les patients à risque sur le diabète de type 2.  
Application basée sur une architecture microservices.  
Pour exécuter l'application avec docker, suivez les étapes ci-dessous.

## Prérequis

Avant de commencer, assurez-vous d'avoir installé Docker et Docker Compose sur votre machine. Si ce n'est pas le cas, vous pouvez les télécharger depuis le site officiel de Docker :
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Clonage du Projet

Utilisez la commande suivante pour cloner le projet sur votre machine :  
git clone https://github.com/QuentinCAVIN/Projet-9-MediLabo-Solution.git

## Configuration des Ports
Assurez-vous que les ports nécessaires sur votre machine sont disponibles. Les services Docker utilisent les ports suivants :

8080: clientui -> interface utilisateur

9101: gateway -> redirige les url vers les différents microservices

9001: patient-service -> gère les opération du CRUD sur les patients 

9002: note-service -> gère les opération du CRUD sur les notes

9003: diabetes-assessment-service -> permet l'évaluation du niveau de risque des patients à partir du contenu des notes écrites par le médecin

3307: mysql-db -> persiste les données utiles à patient-service

27018: mongodb -> persiste les données utiles à note-service

## Démarrage de l'Application
Accédez au répertoire du projet et placez vous dans le répertoire config:  
`cd Projet-9-MediLabo-Solution/config`

Utilisez la commande suivante pour démarrer l'application en mode détaché:  
`docker-compose up -d`

## Connexion à l'application
Après le démarrage des dockers, vous pouvez accéder à l'application via l'interface utilisateur à l'adresse http://localhost:8080.  
Utilisez les informations de connexion par défaut pour vous connecter :

Nom d'utilisateur: `username`

Mot de passe: `password`


## Recommandations d'amélioration Green du projet

### Optimisation des Dépendances
Évaluer l'utilité des dépendances, qui peuvent parfois générer du code inutilisé (ex : lombock). Supprimer ou réduire l'utilisation de ces dépendances peu alléger l'empreinte du projet.
<br>
Privilégier l'utilisation du **driver Java de MongoDB** pour les interactions avec la base de données plutôt que le framework Spring Data MongoDB, plus technique à utiliser, mais favorise la performance en optimisant le temps de traitement des requêtes, contribuant ainsi à une utilisation plus efficace des ressources.
### Utilisation Efficace de Docker
Intégrer le fichier .dockerignore pour exclure les dossiers inutiles à la construction de l'image (ex : dossier target), réduisant ainsi la taille des images générées.
Lors de la reconstruction des images Docker à l'aide de `docker-compose up`, veiller à supprimer uniquement les images nécessaires pour éviter de re-télécharger une image plusieurs fois et contribuer à réduire la demande en bande passante (particulièrement concernant les images des bases de données).
### Évaluation et Optimisation des Algorithmes
Évaluer et optimiser les algorithmes de boucle dans le code en recherchant des structures plus efficaces pour contribuer à une exécution plus rapide et à une consommation moindre de ressources.
Utiliser la JVM (Java Virtual Machine) pour repérer le code non performant et gourmand en ressources. 