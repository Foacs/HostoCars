# HostoCars

##### _Logiciel de gestion et de facturation de réparations de véhicules._

## `Backend Foundation`

_Version:_ `SNAPSHOT`

###### Description

Le but de cette branche est de préparer les fondations du server backend, à savoir:

* ~~Configuration Maven + Spring~~
* ~~Démarrage du serveur~~
* ~~Accès à la base de données~~
* ~~Accès aux web services REST~~
* ~~Accès aux propriétés~~
* ~~Gestion des messages~~
* ~~Système de logging~~

###### Mises à jours

* ##### 31/05/2019:
  * Enrichissement du modèle de `Contact`
    * Champ `String` facultatif ajouté
    * Champ `Integer` facultatif ajouté
    * Champ `Boolean` avec valeur par défaut ajouté
    * Champ `Blob` facultatif ajouté
    * `Converter` adapté
  * Controller REST pour les `Contact` DTOs enrichi
    * Méthodes pour insérer, modifier et supprimmer des `Contact`
    * Méthode pour modifier l'image d'un `Contact`
    * Interface `RequestBody` créée et implémentée pour les `Contact`
    * Classes `QueryArgument` et `QueryArgumentType` créées
  * Classe utilitaire `ObjectUtils` créée
  * Classe utilitaire `FileUtils` créée
    * Méthode pour extraire un `Blob` d'un fichier
  * Classe utilitaire `SQLUtils` créée
    * Méthode utilitaire pour générer une requête SQL avec une clause `WHERE` (pour la recherche)
    * Méthode utilitaire pour générer une requête SQL avec une clause `INSERT`
    * Méthode utilitaire pour générer une requête SQL avec une clause `UPDATE` (+1 pour les `Blob`)
  * Gestion des erreurs corrigée
    * Laisser le serveur gérer les erreurs IO et SQL
  * Fichiers de logging automatiquement gérés par Spring
    * Fichier de logging renommé
    * Limite de fichiers de logging fixée à 1 an

* ##### 26/05/2019:
  * Configuration Maven
    * Configuration Spring Boot par défaut
    * Tomcat remplacé par Undertow
    * SQLite JDBC
  * L'application démarre correctement (sans argument)
    * Mise en place de l'injection Spring
    * Mise en place des propriétés (`application.properties`)
  * Mise en place de la base de données SQLite
    * Fichier `data.db` généré dans `/data`
    * Table de configuration (`DatabaseInfo`) avec numéro de version
    * Initialisation de la base de données au démarrage si inexistante
    * Mise en place d'un controller pour la configuration de la base de données (`DatabaseController`)
    * Mise à jour de la base de données au démarrage si version antérieure
    * Connection à la base de données instanciée au démarrage et partagée (`DatabaseConnection`)
    * Mis en place d'un éxécuteur de scripts SQL injectable (`DatabaseScriptExecutor`)
    * Création des scripts de mis à jour pour les versions `0.0.1` et `0.0.2`
  * Mise en place du système de logging
    * Logs disponibles dans `/log/log.log`
  * Modèle temporaire de contacts mis en place
    * `Contact` DTO
  * Mise en place de l'interface `Converter` pour les DTO converters
    * `ContactConverter` converter pour les `Contact` DTOs
  * Mise en place d'une exception technique `TechnicalException`
  * Mise en place d'un controller REST pour les `Contact` DTOs
    * `GET` méthode sans argument créée
    * `GET` méthode avec argument créée
    * `PUT` méthode
  * Mise en place du repository Git `HostoCars`
    * Création des branches `develop` et `feature/backend_foundation`
