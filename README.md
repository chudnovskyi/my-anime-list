# My Anime List
> A web application built using Spring Boot to provide users with a platform to manage their favorite anime.
> The app uses the [_Jikan API_](https://docs.api.jikan.moe/) to provide up-to-date information about anime and includes features such as login/registration, anime search, top anime ratings, and more.


## Demo
Here is a working live demo deployed on Heroku cloud service: [_My Anime List_](https://my-anime-listt.herokuapp.com/)


## Table of Contents
* [Overview](#overview)
* [Technology Stack](#technology-stack)
* [Additional Tools](#additional-tools)
* [Features](#features)
* [Local Deployment](#local-deployment)
* [SQL Schema](#sql-schema)
* [MVC Endpoints](#mvc-endpoints)
* [Contact](#contact)


## Overview
> My Anime List is my first web development project, designed to reinforce my newly acquired knowledge after completing a Spring Boot [_course_](https://www.udemy.com/certificate/UC-f22b858d-784d-436d-be89-1c1ae1fc5835/).
> This project allowed me to apply my skills and gain hands-on experience working with Java, Spring Boot, and database development.


## Technology Stack
- Java 17

- Spring Boot 2.7.7
- Spring MVC
- Spring Data JPA
- Spring Security

- Hibernate
- MySQL database
- Thymeleaf

- Javax Validation API
- Spring Mail

## Additional Tools
- Lombok: A library used to reduce boilerplate code in Java.
- Swagger: An API documentation and testing tool.


## Features
- Login/Registration: Users can create an account and log in, with email verification required.
- Filtered anime search: Users can search for anime by title and genres, with pagination support.
- Top anime: A list of the top rated anime, with pagination support.
- Random anime generation: Users can generate a random anime to watch.
- Detailed anime information: Users can access information about each anime, including its trailer (if available).
- Reviews: Users can write and delete reviews about any anime, with validation for at least 5 characters.
- Anime sections: Users can add anime to sections (watching, planning, completed, on-hold, dropped) and view a list of selected anime in each section, sorted by user personal rating.
- Favourites: Users can mark anime as favourites and rate them on a scale of 1 to 10.
- Profile page: Users can upload a profile photo and view available roles.
- Admin page: A page only available to users with the admin role (currently with limited functionality).
- Encrypted passwords: User passwords are encrypted using the Bcrypt algorithm for enhanced security.


## Local Deployment
1. Clone the repository using the following command in your terminal:

```git clone https://github.com/lwantPizza/my-anime-list```

2. Set up the MySQL database locally, SQL script provided:

```https://github.com/lwantPizza/my-anime-list/blob/main/sql-script/script.sql```

3. Set the db properties in project's `application.properties` file:
```
spring.datasource.url=jdbc:mysql://localhost:3306/my_anime_list?useSSL=false&serverTimezone=UTC
spring.datasource.username="your-username"
spring.datasource.password="your-password"
```
4. Set up Gmail SMTP Server for email sender. Here's a quick tutorial on [how to do that](https://www.youtube.com/watch?v=1YXVdyVuFGA&ab_channel=Sombex).
5. Finally, insert the generated password and email username in project's `application.properties` file:
```
spring.mail.username="generated-username"
spring.mail.password="password"
```
> That's it! You should now be able to run your GitHub project locally with the correct database and email configurations.


# SQL Schema
![](https://github.com/lwantPizza/my-anime-list/blob/main/images/sql-schema1.png?raw=true)


# MVC Endpoints
![](https://github.com/lwantPizza/my-anime-list/blob/main/images/mvc-endpoints.png?raw=true)


## Contact
Created by [@lwantPizza](https://t.me/lwantPizza) - feel free to contact me!
