 Pizza Creed 🍕
Pizza Creed is a pizza bakery in Galle Fort that prepares pizza only for orders.

#Table of Contents
Introduction
Setup
Prerequisites
Database Configuration
Running the Application
Endpoints
Postman Collection
Documentation
Admin Panel

#Introduction
Pizza Creed is a Spring Boot app providing RESTful APIs for managing pizza orders.

#Setup
Prerequisites
>JDK
>Maven Wrapper (included)
>MySQL

#Database Configuration
Create a MySQL database named pizzacreed.
Duplicate application.properties.template in src/main/resources and rename to application.properties.
Update application.properties with your database details.

#Running the Application
1.In the terminal, navigate to the project directory.
2.Run:
bash
Copy code
./mvnw clean install
./mvnw spring-boot:run
(On Windows, use mvnw instead of ./mvnw)
3.The app will run at http://localhost:8181.

#Endpoints
See the Postman Collection for detailed API endpoints.

#Postman Collection
Test the API using the Postman Collection.

#Documentation
View the detailed API documentation on Postman Documentation.

#Admin Panel
Access the admin panel at http://localhost:8181/login with:

Username: admin
Password: admin
