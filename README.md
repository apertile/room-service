# room-service
REST API to obtain optimal amount of cleaning resources for a given number of rooms

# Overview
This is a REST API with only one endponint that allows you to calculate the optimal amount of junior and senior cleaning staff you will need for a given amount of rooms.
The problem was solved by building an objective function to minimize so that you could obtain the optimal values with two restrictions: 

   f(senior,junior)= seniorscore * senior + juniorscore * junior - rooms
   Restrictions: 
   - senior, junior € N
   - senior>=1
   - junior>=0
   - senior>junior
   
The API was built with Spring Boot and provides Swagger to facilitate its use. Other technologies include Maven, Java 8, JUnit and Mockito for unit tests.

To test it you can clone the repository and run the app as a Spring Boot app or with Maven through 'mvn spring:run'. You can access the Swagger page at http://localhost:8080/swagger-ui.html

# Heroku
The application is also deployed in Heroku so that you don't have to run it locally. You can find the Swagger page at 

