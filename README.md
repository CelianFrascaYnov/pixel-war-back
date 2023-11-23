# pixel-war-back

Projet partie Back de réalisation d'un projet de type pixel war en communication WebSocket. Le projet est équipé d'une base de données [MongoDBCloud](https://cloud.mongodb.com/v2/6527ab72855c952e21f17537#/overview).
Voir le fichier de dépôt des rendus pour avoir les identifiants.

## Installation

Utiliser le package manager [Maven](https://maven.apache.org/install.html) pour l'installer.

### Cloner le Répertoire

Clonez le dépôt Git :

```bash
git clone https://github.com/CelianFrascaYnov/pixel-war-back.git
cd pixel-war-back
mvn clean install
```

Construisez l'image Docker et lancer votre application avec :
```bash
docker-compose up --build
```

Votre application devrait maintenant être accessible à l'adresse `http://localhost:8080`.