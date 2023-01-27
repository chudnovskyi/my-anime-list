# MyAnimeList
> A WebApp using Spring Boot and MVC architectural pattern to surf [_anime base_](https://myanimelist.net/) via [_Jikan API_](https://docs.api.jikan.moe/).
> The application allows the user to manage a personal list of favorite anime and add them to different categories.
> With a third party API, the database will never be out of date.


## Table of Contents
* [Demo](#demo)
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [SQL Schema](#sql-schema)
* [Contact](#contact)


## Demo
Here is a working live demo: (soon...)


## General Information
> A training web project to reinforce the newly acquired knowledge after completing the [_Spring Boot course_](https://www.udemy.com/course/spring-hibernate-tutorial/).


## Technologies Used
- Java 17

- Spring Boot 2.7.7
- Spring MVC
- Spring Security
- Spring Validation
- Spring Mail

- Hibernate Core
- MySQL database
- Thymeleaf


## Features
- Login/Registration (including validation and required email verification).
- Filtered anime search by title and genres. (pagination)
- Top anime by it's rating. (pagination)
- Random anime generation.
- Access to detailed information about each anime, including its trailer (if available).
- Write and delete comments under any anime with validation for at least of 5 characters length.
- Ability to add anime to sections: watching, planning, completed, on hold, dropped.
- Mark anime as favourite, rate it in score of 1 to 10.
- View a list of selected anime in any section, sorted by user personal rating. (pagination)
- Profile page where users can upload a profile photo and see available roles.
- Page only available to users with admin role (at the moment without much functionality).
- Encrypted user passwords with the Bcrypt algorithm


# SQL Schema
![](https://github.com/lwantPizza/my-anime-list/blob/main/sql-script/schema.png?raw=true)


## Contact
Created by [@lwantPizza](https://t.me/lwantPizza) - feel free to contact me!
