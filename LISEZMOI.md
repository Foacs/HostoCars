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
- [Liens](#liens)

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

> Pour packager la dernière version de l'interface graphique, le projet doit être buildé à nouveau avec la commande maven `mvn clean install`.

### Mails

Afin d'activer la fonctionnalité d'envoi de mails, vous devez ajouter un fichier `mail.properties` dans le dossier `./src/main/resources` (au niveau du fichier
`application.properties`), avec une propriété `mail.bearer.token`.

> Vous pouvez prendre example sur le fichier `example.mail.properties`.

Afin d'acquérir un jeton pour votre besoin, vous devrez vous inscrire sur le [site de l'organisation FOACS](https://foacs.ovh/), puis créer un jeton sur la page de votre profil
avec le type "_(API) Mail d'erreur_". Attention cependant, la valeur du jeton n'est affichée qu'à la création de celui-ci est ne sera plus accessible ensuite.

## Licence

Ce projet est sous les termes de la licence __CeCILL__.

> Vous pouvez accéder aux détails de la licence [ici](https://github.com/Foacs/HostoCars/blob/develop/LICENSE.md).

## Liens

[![Foacs](https://img.shields.io/badge/Foacs-%23ffffff.svg?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNTEycHgiIGhlaWdodD0iNTEycHgiIHZpZXdCb3g9IjAgMCA1MTIgNTEyIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxwYXRoIGZpbGw9IiMwMDAiIGQ9Ik0xNDQuNzM0IDIyLjA0Yy01LjYxLS4wMzUtMTEuMTYzLjEyLTE2LjYzNC40NTYtNDMuNzcgMi43LTgyLjkwNCAxNy4wMDMtMTAzLjg2MiA0NC45OTYtNy41MDYgMTAuMDI3LTUuNjgyIDIzLjkxIDIuOTUgMzEuNDIgOC42MzUgNy41MSAyMy4wMDQgOC4wNTMgMzYuMjM0LS41MiAyMi44NC0xNC44MDUgNDcuOTMzLTkuNTcyIDY0LjI3IDYuMTcyIDE2LjM0IDE1Ljc0NSAyMy43MzYgNDEuNDUzIDcuNTQgNzEuMTQ1LTE5LjE3IDM1LjE0My0zMi43MTYgOTYuMTUzLTIwLjE0NiAxNTYuNTI2IDEyLjU3IDYwLjM3NCA1Mi45NjggMTE5Ljc2IDEzOS43MjggMTQ1Ljc3MiAzMy40NzYgMTAuMDM2IDc4LjgyNSAxNi43NSAxMjEuNjQ1IDcuNjY2IDQ0LjUwNy04Ljc4OCA5NS44NS0zNC43NTggMTA2Ljg5Mi02My4xMS05LjI1IDguODg1LTE5LjQ0IDE1LjE0LTMwLjIwMiAxOS43OSAxOC4zMDYtMjAuOTIgMzEuNzM1LTQ5LjczMiAzNi43OS04OC4xNzRsMi41My0xOS4yNC0xNi4zMjIgMTAuNDk2Yy0xMC41MDMgNi43NTUtMjAuNTg1IDEzLjQwMy0zMC4wOTMgMTguMzk2IDIuNjM4LTUuODcyIDUuMDM4LTEzLjIyIDcuNzMtMjIuNzc3LTExLjA5NyAxNS4xOS0yMy43MyAyNS4zNTUtMzguNTk4IDMxLjQ3Mi05LjIzNC0uNTAzLTE4LjM1My00Ljg2Ny0yOS4yMS0xNi4wOTctMTEuMzU4LTExLjc0Ny0xOC4xMi0zMi4wOTUtMjIuNDYzLTU3LjY2Ni00LjM0NC0yNS41NzItNi40Ni01NS45MjctMTAuNjY4LTg2Ljg3Ny04LjQyLTYxLjkwMi0yNS45MTItMTI3Ljg3My04OS43NC0xNjEuMDM1LTM2Ljk1NS0xOS4yLTc5LjA5Mi0yOC41NzctMTE4LjM3Mi0yOC44MTN6bS0uMTIzIDE4LjAxYzM2LjQ2Mi4yNTUgNzYuMTEgOS4wNjUgMTEwLjE5NyAyNi43NzQgNTYuMzkzIDI5LjMgNzEuOTk0IDg3LjE0IDgwLjIwMyAxNDcuNDg4IDQuMTA0IDMwLjE3NSA2LjE4NiA2MC41NTQgMTAuNzU4IDg3LjQ2NSAxLjMxNiA3Ljc1MyAyLjgzNSAxNS4yNDIgNC42OTMgMjIuMzg1LTE1LjQ0OC4wNC0yNy4yNTQtOC4zMDctNDEuNzA0LTI0LjcxNyA3LjM4NSAzMC40MSAxMS45OSAzNi41MzQgMjUuNzA1IDU1LjU1LTI4LjIyLTguMjM1LTYwLjY0LTM0Ljc0LTgwLjk1LTY0LjA2My0zLjI3NCA0MC4wNDcgMjAuMjIzIDcxLjU3NCAzMy4yNzUgODMuOTMtMjUuMTc2LTE0LjE5Ni02MC43MTMtNDEuNTM2LTg0LjYyMy04OC42NTUtMS4wMTYgNDEuNDI2IDExLjkzIDg3LjczMiAzNi40NSAxMTYuNDY1LTM0LjUxNS0xMS41MzYtNjQuOTctOTkuNDcyLTg1LjQyLTEyNy42MzMtMTMuMDQgMzMuMjE3LTIuOTQ4IDg5LjA4NSAxNi4wNzIgMTMwLjEyMi0xOS42MjgtMjIuODM4LTMwLjg4Ny00OS4zNzUtMzYuNTU1LTc2LjU5Ni0xMS41MjQtNTUuMzQyIDEuNzUtMTEzLjg0NyAxOC4zMjUtMTQ0LjIzOCAxOS41NS0zNS44NDIgMTAuOTE1LTcxLjc1LTEwLjg1LTkyLjcyNi0yMS43NjgtMjAuOTc2LTU2Ljg1NC0yNy41NjQtODYuNTU0LTguMzE1LTguNTYgNS41NS0xMi42ODggMy43MzItMTQuNjI2IDIuMDQ1LTEuOTQtMS42ODctMi43Ni0zLjg0LS4zNTYtNy4wNTMgMTYuMTA2LTIxLjUxNCA1MC4xMzUtMzUuMzI0IDkwLjU2LTM3LjgxNyA1LjA1Mi0uMzEyIDEwLjE5NS0uNDUgMTUuNDAzLS40MTR6Ii8+PC9zdmc+)](https://foacs.ovh/)
[![Discord](https://img.shields.io/badge/Discord-%237289DA.svg?&style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/VWX9pybWvT)
[![Travis CI](https://img.shields.io/badge/Travis%20CI-%232B2F33.svg?&style=for-the-badge&logo=travis)](https://travis-ci.com/github/Foacs)
[![SonarCloud](https://img.shields.io/badge/SonarCloud-%23F3702A.svg?&style=for-the-badge&logo=sonarcloud&logoColor=white)](https://sonarcloud.io/organizations/foacs)
