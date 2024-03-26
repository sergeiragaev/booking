# Rest API for Hotel Booking Application

## Description:

This is a spring boot application for an online hotel booking system management, developed using core java and the spring framework.
The application utilizes a PostgreSQL database for data storage, MongoDB to collect statistic and Apache Kafka event streaming platform. 
Almost all API calls authenticated, so you need to register user first.  

## API Details

### Managing users
* (POST) user/register - Register user using next fields in body: 
  * username, email, password 
  * and roleType as a request parameter (ROLE_ADMIN, ROLE_USER).
* (GET) user/{id} - Get requested user details.
* (DELETE) user/{id} - Delete user by it's id.

### Managing hotels
* (POST) hotel - Add hotel using next fields in body: 
  * name, title, city, address, distance.
* (GET) hotel - This will return the list of available hotels using pagination and filter by adding next requested fields:
  * pageSize, pageNumber
  * id, name, title, city, address, distanceFrom, distanceTo, ratingFrom, ratingTo, numberOfRatingFrom, numberOfRatingTo.
* (GET) hotel/{id} - This will return information about hotel with requested id.
* (PUT) hotel/{id} - Update hotel information by it's id.
* (DELETE) hotel/{id} - Delete requested hotel.
* (PUT) hotel/rating?newMark=mark&id=id - Update hotel's rating with requested id.

### Managing rooms
* (POST) room - Add room using next fields in body: 
  * hotelId, name, description, number, price, maxCapacity.
* (GET) room - This will return the list of available rooms using pagination and filter by adding next requested fields:
  * pageSize, pageNumber
  * roomId, name, minPrice, maxPrice, capacity, dateIn, dateOut, hotelId.
* (PUT) room/{id} - Update requested room information.
* (DELETE) room/{id} - Delete requested room.

### Download files with statistics
* (GET) stat/register - This will download register statistic information file stored in MongoDB.
* (GET) stat/booking - This will download booking statistic information file stored in MongoDB.


### How to launch the application using docker:
* build .jar file from Gradle tab going by running the Tasks\build\buildNeeded menu item
* build docker image by running command "docker build -f docker/Dockerfile -t booking ." into application location terminal
* run docker-start.sh shell command from the docker subfolder

### API Root Endpoint
http://localhost:8080/api/v1/