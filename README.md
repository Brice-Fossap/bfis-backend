# bfis-backend

B.F.I.S. Backend (Brice Fossap Integrated Banking System)
Backend Jakarta EE pour une application bancaire sécurisée et modulaire

## 📌 Présentation
Ce backend est développé avec Jakarta EE 10 pour offrir une API REST robuste, sécurisée et maintenable, servant une application bancaire avec gestion des clients, employés, comptes, opérations et sécurité avancée (JWT + Jakarta Security).

## 🛠 Configuration principale
* persistence.xml
Configuration JPA, connexion à PostgreSQL, dialecte, pool de connexion.

* application.yaml
Configuration des propriétés spécifiques (JWT secret, expiration, etc.).

* Serveur d’applications : GlassFish

## 🔐 Sécurité
* Authentification JWT : via JWTAuthenticationMechanism et JWTIdentityStore.
* Gestion des rôles et permissions.
* Utilisation de Jakarta Security pour sécuriser les endpoints REST.
* Injection CDI pour services sécurisés.

## ⚙️ Fonctionnalités backend principales
* CRUD complet sur Clients, Employés, Comptes et Opérations.
* Validation des règles métier.
* Transactions atomiques gérées via @Transactional.
* Pagination, filtrage et recherche via JPA Specifications.
* Gestion des erreurs et exceptions personnalisées.
* Journalisation et audit.

## 📦 Commandes Maven utiles
* Compiler et packager :
mvn clean package

* Lancer les tests unitaires :
mvn test

## 🚀 Démarrage local
* Assure-toi que PostgreSQL tourne et que la base de données est accessible.
* Configure la source de données JTA dans GlassFish (ex: jdbc/postgres).
* Déploie le WAR généré dans GlassFish.
* Démarre le serveur d’application.
* L’API est accessible sur http://localhost:8080/.

## 📄 Documentation API
Utilise Swagger/OpenAPI pour générer la doc de l’API REST. URL : http://localhost:8080/api/swagger-ui

Tests d’intégration : RestAssured pour valider les endpoints REST.

Tests de sécurité : valider les accès selon rôle et permissions.
