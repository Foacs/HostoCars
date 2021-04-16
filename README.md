[Version française](https://github.com/Foacs/HostoCars/blob/develop/LISEZMOI.md)

# HostoCars

![Logo](https://github.com/Foacs/HostoCars/blob/develop/src/main/javascript/public/logo.png?raw=true "Logo")

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

### Frontend development

To help with frontend development, the node server can be started in parallel with the following command:

```shell script
./src/main/javascript/npm run start
```

Then the local frontend implementation will be automatically compiled and executed at [localhost:3000](http://localhost:3000/).

> In order for the latest frontend changes to be packaged, the project needs to be built again with maven with the previous command.

### Mails

To enable the mail support, you have to add a `mail.properties` file in the `./src/main/resources` folder (alongside the `application.properties` file), with a `mail.bearer.token`
property.

> As an example, you can have a look at the `example.mail.properties` file.

In order to get a token for your use, you have to register an account on the [FOACS organisation website](https://foacs.ovh/), then create a token on your profile with the
"_(API) Mail d'erreur_" token type. Be careful, as the token will only be displayed right after the creation and won't be accessible afterwards.

## License

This project is licensed under the terms of the __CeCILL__ license.

> You can check out the full license [here](https://github.com/Foacs/HostoCars/blob/develop/LICENSE.md).
