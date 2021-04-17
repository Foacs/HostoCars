[English version](https://github.com/Foacs/HostoCars/blob/develop/README.md)

# HostoCars

![Logo](https://github.com/Foacs/HostoCars/blob/develop/src/main/javascript/public/logo.png?raw=true "Logo")

Application web de gestion et de réparation de véhicules.

![GitHub top language](https://img.shields.io/github/languages/top/Foacs/HostoCars)
[![Build Status](https://travis-ci.com/Foacs/HostoCars.svg?branch=develop)](https://travis-ci.com/Foacs/HostoCars)
![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/foacs/HostoCars?include_prereleases)
![License](https://img.shields.io/badge/license-CeCILL-blueviolet)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=fr.foacs%3Ahostocars&metric=ncloc)](https://sonarcloud.io/dashboard?id=fr.foacs%3Ahostocars)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fr.foacs%3Ahostocars&metric=alert_status)](https://sonarcloud.io/dashboard?id=fr.foacs%3Ahostocars)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fr.foacs%3Ahostocars&metric=coverage)](https://sonarcloud.io/dashboard?id=fr.foacs%3Ahostocars)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=fr.foacs%3Ahostocars&metric=bugs)](https://sonarcloud.io/dashboard?id=fr.foacs%3Ahostocars)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=fr.foacs%3Ahostocars&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=fr.foacs%3Ahostocars)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fr.foacs%3Ahostocars&metric=security_rating)](https://sonarcloud.io/dashboard?id=fr.foacs%3Ahostocars)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=fr.foacs%3Ahostocars&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=fr.foacs%3Ahostocars)

## Table des matières

- [Informations générales](#informations-générales)
- [Technologies](#technologies)
- [Installation](#installation)
- [Licence](#licence)

## Informations générales

Cette application web est destiné à aider à la gestion de réparation de véhicules, du diagnostic à la facturation. Son usage est destiné à être intuitive et simple d'utilisation.

> Le seul langage disponible est le Français et le projet n'est pas destiné à être adapté pour d'autres langages, compte tenu du panel d'utilisateurs visé.

> Cette application est destinée à tourner correctement sur les 2 dernières versions majeures des navigateurs Google Chrome et Mozilla Firefox. Tout autre navigateur web ne sera
> pas supporté. De plus, elle est destinée à un usage sur ordinateur, donc à ce jour, aucune compatibilité n'est prévue pour mobile.

## Technologies

### Backend

- Java: `>11` [_(openJDK-11)_](https://openjdk.java.net/projects/jdk/11/)
- Spring: `2.2.4.RELEASE`
- Maven: `4.0.0`

### Frontend

- NodeJS: `14.16.0`
- ReactJS: `16.13.1`
- npm: `7.8.0`

## Installation

1. Pour lancer le projet en local, vous aurez besoin d'installer Java, maven, node et npm.

> Pour plus d'informations, vous pouvez consulter les guides suivants :
> - Le [site d'Oracle](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html) pour Java
> - Le [site d'Apache](https://maven.apache.org/install.html) pour maven
> - Le [site de NodeJS](https://nodejs.org/en/download/package-manager) pour NodeJS
> - Le [site d'npm](https://docs.npmjs.com/cli/v7/configuring-npm) pour npm

2. Puis buildez le projet avec maven avec la commande suivante à la racine du projet :

```shell script
./mvn clean install
```

3. Enfin, pour lancer l'application utilisez la commande suivante à la racine du projet :

```shell script
java -jar ./target/HostoCars.jar fr.foacs.hostocars.Application
```

4. À présent l'application devrait être accessible à [localhost:8080](http://localhost:8080/).

### Développement du backend

Pour accélérer le build du backend, vous pouvez spécifier l'option `-Dskip-frontend_build` à maven dans le but d'ignorer le build du frontend :

```shell script
./mvn clean install -Dskip-frontend-build
```

### Développement du frontend

Pour vous aider à développer l'interface graphique, le serveur node peut être démarré en parallèle avec la commande suivante :

```shell script
./src/main/javascript/npm run start
```

À présent l'implémentation locale de l'interface devrait être automatique exécutée et rafraîchie à [localhost:3000](http://localhost:3000/).

De plus, afin de builder le frontend sans builder le backend, vous pouvez la commande npm suivante :

```shell script
./src/main/javascript/npm run build
```

> Pour packager la dernière version de l'interface graphique, le projet doit être buildé à nouveau avec maven avec la commande `mvn clean install`.

### Mails

Afin d'activer la fonctionnalité d'envoi de mails, vous devez ajouter un fichier `mail.properties` dans le dossier `./src/main/resources` (au niveau du fichier
`application.properties`), avec une propriété `mail.bearer.token`.

> Vous pouvez prendre example sur le fichier `example.mail.properties`.

Afin d'acquérir un jeton pour votre besoin, vous devrez vous inscrire sur le [site de l'organisation FOACS](https://foacs.ovh/), puis créer un jeton sur la page de votre profil
avec le type "_(API) Mail d'erreur_". Attention cependant, la valeur du jeton n'est affichée qu'à la création de celui-ci est ne sera plus accessible ensuite.

## Licence

Ce projet est sous les termes de la licence __CeCILL__.

> Vous pouvez accéder aux détails de la licence [ici](https://github.com/Foacs/HostoCars/blob/develop/LICENSE.md).
