# 🚗 FleetMan - Système de Gestion de Flotte Automobile

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![License](https://img.shields.io/badge/License-Polytechnique_yaounde-yellow)

FleetMan est le backend d'une application pour la gestion de flotte automobile, offrant le suivi des véhicules, la gestion des conducteurs, le suivi GPS, et bien plus encore.

##  Fonctionnalités

### 🎯 Modules Principaux
- **Gestion des Véhicules** - Suivi complet du parc automobile
- **Gestion des Conducteurs** - Informations et contacts des chauffeurs
- **Tracking GPS** - Géolocalisation en temps réel des véhicules
- **Gestion du Carburant** - Suivi des recharges et consommations
- **Maintenance** - Historique et planning des entretiens
- **Système de Notifications** - Alertes et communications
- **Gestion des Utilisateurs** - Comptes et authentification

### 🛠️ Technologies Utilisées

- **Backend** : Spring Boot 3.5.7, Java 21
- **Base de données** : PostgreSQL avec PostGIS
- **Documentation API** : SpringDoc OpenAPI 3
- **Géolocalisation** : JTS Topology Suite
- **Build** : Maven
- **Validation** : Bean Validation API
- **Sécurité** : Spring Security

## 🚀 Démarrage Rapide

### Prérequis
- Java 17 ou supérieur
- Maven 3.6+
- PostgreSQL 15+ avec PostGIS
- Git

### Installation

1. **Cloner le projet**
   ```bash
   git clone https://github.com/Djusse/Fleetman.git
   cd fleetman

2. **Configuration de la bibliothèque externe GeoJSON**

Ce projet utilise la bibliothèque ultimate-geojson pour le support avancé des formats GeoJSON.
**Source** : https://github.com/mokszr/ultimate-geojson.git

3. **Télécharger les dépendances Maven**
   ```bash
   # Télécharger toutes les dépendances
    mvn clean install

    # Ou pour forcer le téléchargement
    mvn dependency:resolve

4. **Créer la base de données avec postgis**
    ```bash
   # installer postgis 3
    sudo apt install postgresql-17-postgis-3
   # Créer la base de donnée et l'utilisateur : nom et mot de passe respectivement admin admin
   sudo -u postgres psql -f - <<SQL
    CREATE DATABASE "fleetmanBD";
    CREATE USER admin PASSWORD 'admin';
    ALTER DATABASE "fleetmanBD" OWNER TO admin;
    \c fleetmanBD
    GRANT ALL ON SCHEMA public TO admin;
    GRANT CREATE ON DATABASE "fleetmanBD" TO admin;
    CREATE EXTENSION IF NOT EXISTS postgis;
    SQL
  
### Lancer l'application 
1.
    ```bash
    cd Fleetman
    ./mvnw spring-boot:run

Une fois l'application démarrée, accédez aux différentes interfaces :

- 🌐 Application Spring Boot : http://localhost:9080
- 📖 Documentation API Swagger : http://localhost:9080/api/swagger-ui.html
- 📋 API Docs JSON : http://localhost:9080/api/v3/api-docs
