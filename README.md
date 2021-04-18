[Version française](./LISEZMOI.md)

# HostoCars

![Logo](./src/main/javascript/public/logo.png?raw=true "Logo")

Vehicle repair management and invoicing web application.

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

## Table of contents

- [General info](#general-info)
- [Technologies](#technologies)
- [Setup](#setup)
- [License](#license)
- [Links](#links)

## General info

This web application is intended to help with the management of vehicles repairs, from the diagnostic to the billing. Its usage is expected to be intuitive and user-friendly.

> The only available language is French and the project is not intended to be adapted for other languages, given the user panel it is designed for.

> This application is expected to run correctly on the 2 last major versions of Google Chrome and Mozilla Firefox web browsers. Any other web browser or version won't be supported.
> Also, it is intended to be used on a computer, so as of today, no compatibility is planned for mobile.

## Technologies

### Backend

- Java: `>11` [_(openJDK-11)_](https://openjdk.java.net/projects/jdk/11/)
- Spring: `2.2.4.RELEASE`
- Maven: `4.0.0`

### Frontend

- NodeJS: `14.16.0`
- ReactJS: `16.13.1`
- npm: `7.8.0`

## Setup

1. To run this project locally, you'll first need to set up Java, maven, node and npm.

> For more info, you can check out the following guides:
> - The [Oracle's website](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html) for Java
> - The [Apache's website](https://maven.apache.org/install.html) for maven
> - The [NodeJS website](https://nodejs.org/en/download/package-manager) for NodeJS
> - The [npm website](https://docs.npmjs.com/cli/v7/configuring-npm) for npm

2. Then use maven to build the project using the following command at the project root:

```shell script
./mvn clean install
```

3. Finally, to launch the application use the following command at the project root:

```shell script
java -jar ./target/HostoCars.jar fr.foacs.hostocars.Application
```

4. Now the application should be accessible at [localhost:8080](http://localhost:8080/).

### Backend development

To fasten the backend build, you can specify the `-Dskip-frontend_build` option to maven in order to skip the frontend build:

```shell script
./mvn clean install -Dskip-frontend-build
```

### Frontend development

To help with frontend development, the node server can be started in parallel with the following command:

```shell script
./src/main/javascript/npm run start
```

Then the local frontend implementation will be automatically compiled and executed at [localhost:3000](http://localhost:3000/).

Also, to build the frontend without building the backend, you can use npm as follows:

```shell script
./src/main/javascript/npm run build
```

> In order for the latest frontend changes to be packaged, the project needs to be built again with the maven `mvn clean install` command.

### Mails

To enable the mail support, you have to add a `mail.properties` file in the `./src/main/resources` folder (alongside the `application.properties` file), with a `mail.bearer.token`
property.

> As an example, you can have a look at the `example.mail.properties` file.

In order to get a token for your use, you have to register an account on the [FOACS organisation website](https://foacs.ovh/), then create a token on your profile with the
"_(API) Mail d'erreur_" token type. Be careful, as the token will only be displayed right after the creation and won't be accessible afterwards.

## License

This project is licensed under the terms of the __CeCILL__ license.

> You can check out the full license [here](./LICENSE.md).

## Links

[![Foacs](https://img.shields.io/badge/Foacs-%23ffffff.svg?&style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNTEycHgiIGhlaWdodD0iNTEycHgiIHZpZXdCb3g9IjAgMCA1MTIgNTEyIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxwYXRoIGZpbGw9IiMwMDAiIGQ9Ik0xNDQuNzM0IDIyLjA0Yy01LjYxLS4wMzUtMTEuMTYzLjEyLTE2LjYzNC40NTYtNDMuNzcgMi43LTgyLjkwNCAxNy4wMDMtMTAzLjg2MiA0NC45OTYtNy41MDYgMTAuMDI3LTUuNjgyIDIzLjkxIDIuOTUgMzEuNDIgOC42MzUgNy41MSAyMy4wMDQgOC4wNTMgMzYuMjM0LS41MiAyMi44NC0xNC44MDUgNDcuOTMzLTkuNTcyIDY0LjI3IDYuMTcyIDE2LjM0IDE1Ljc0NSAyMy43MzYgNDEuNDUzIDcuNTQgNzEuMTQ1LTE5LjE3IDM1LjE0My0zMi43MTYgOTYuMTUzLTIwLjE0NiAxNTYuNTI2IDEyLjU3IDYwLjM3NCA1Mi45NjggMTE5Ljc2IDEzOS43MjggMTQ1Ljc3MiAzMy40NzYgMTAuMDM2IDc4LjgyNSAxNi43NSAxMjEuNjQ1IDcuNjY2IDQ0LjUwNy04Ljc4OCA5NS44NS0zNC43NTggMTA2Ljg5Mi02My4xMS05LjI1IDguODg1LTE5LjQ0IDE1LjE0LTMwLjIwMiAxOS43OSAxOC4zMDYtMjAuOTIgMzEuNzM1LTQ5LjczMiAzNi43OS04OC4xNzRsMi41My0xOS4yNC0xNi4zMjIgMTAuNDk2Yy0xMC41MDMgNi43NTUtMjAuNTg1IDEzLjQwMy0zMC4wOTMgMTguMzk2IDIuNjM4LTUuODcyIDUuMDM4LTEzLjIyIDcuNzMtMjIuNzc3LTExLjA5NyAxNS4xOS0yMy43MyAyNS4zNTUtMzguNTk4IDMxLjQ3Mi05LjIzNC0uNTAzLTE4LjM1My00Ljg2Ny0yOS4yMS0xNi4wOTctMTEuMzU4LTExLjc0Ny0xOC4xMi0zMi4wOTUtMjIuNDYzLTU3LjY2Ni00LjM0NC0yNS41NzItNi40Ni01NS45MjctMTAuNjY4LTg2Ljg3Ny04LjQyLTYxLjkwMi0yNS45MTItMTI3Ljg3My04OS43NC0xNjEuMDM1LTM2Ljk1NS0xOS4yLTc5LjA5Mi0yOC41NzctMTE4LjM3Mi0yOC44MTN6bS0uMTIzIDE4LjAxYzM2LjQ2Mi4yNTUgNzYuMTEgOS4wNjUgMTEwLjE5NyAyNi43NzQgNTYuMzkzIDI5LjMgNzEuOTk0IDg3LjE0IDgwLjIwMyAxNDcuNDg4IDQuMTA0IDMwLjE3NSA2LjE4NiA2MC41NTQgMTAuNzU4IDg3LjQ2NSAxLjMxNiA3Ljc1MyAyLjgzNSAxNS4yNDIgNC42OTMgMjIuMzg1LTE1LjQ0OC4wNC0yNy4yNTQtOC4zMDctNDEuNzA0LTI0LjcxNyA3LjM4NSAzMC40MSAxMS45OSAzNi41MzQgMjUuNzA1IDU1LjU1LTI4LjIyLTguMjM1LTYwLjY0LTM0Ljc0LTgwLjk1LTY0LjA2My0zLjI3NCA0MC4wNDcgMjAuMjIzIDcxLjU3NCAzMy4yNzUgODMuOTMtMjUuMTc2LTE0LjE5Ni02MC43MTMtNDEuNTM2LTg0LjYyMy04OC42NTUtMS4wMTYgNDEuNDI2IDExLjkzIDg3LjczMiAzNi40NSAxMTYuNDY1LTM0LjUxNS0xMS41MzYtNjQuOTctOTkuNDcyLTg1LjQyLTEyNy42MzMtMTMuMDQgMzMuMjE3LTIuOTQ4IDg5LjA4NSAxNi4wNzIgMTMwLjEyMi0xOS42MjgtMjIuODM4LTMwLjg4Ny00OS4zNzUtMzYuNTU1LTc2LjU5Ni0xMS41MjQtNTUuMzQyIDEuNzUtMTEzLjg0NyAxOC4zMjUtMTQ0LjIzOCAxOS41NS0zNS44NDIgMTAuOTE1LTcxLjc1LTEwLjg1LTkyLjcyNi0yMS43NjgtMjAuOTc2LTU2Ljg1NC0yNy41NjQtODYuNTU0LTguMzE1LTguNTYgNS41NS0xMi42ODggMy43MzItMTQuNjI2IDIuMDQ1LTEuOTQtMS42ODctMi43Ni0zLjg0LS4zNTYtNy4wNTMgMTYuMTA2LTIxLjUxNCA1MC4xMzUtMzUuMzI0IDkwLjU2LTM3LjgxNyA1LjA1Mi0uMzEyIDEwLjE5NS0uNDUgMTUuNDAzLS40MTR6Ii8+PC9zdmc+)](https://foacs.ovh/)
[![Discord](https://img.shields.io/badge/Discord-%237289DA.svg?&style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/VWX9pybWvT)
[![Travis CI](https://img.shields.io/badge/Travis%20CI-%232B2F33.svg?&style=for-the-badge&logo=travis)](https://travis-ci.com/github/Foacs)
[![SonarCloud](https://img.shields.io/badge/SonarCloud-%23F3702A.svg?&style=for-the-badge&logo=sonarcloud&logoColor=white)](https://sonarcloud.io/organizations/foacs)
