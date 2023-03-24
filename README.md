# Optimal Delivery Routes
### Introduction
Optimal Delivery Routes is a platform that enable users add their organisation delivery locations, remove those locations, edit the locations and also generate the most optimal route to achieve fast delivery for their clients with its respective delivery cost.
### Optimal Delivery Route Features
* Users can signup and login to their accounts
* Authenticated users can add locations, remove locations, update locations and generate the most optimal route for delivery with the cost.
### Installation Guide
* Clone this repository [here](https://github.com/liomotolani/optimal-route.git).
* The main branch is the most stable branch at any given time, ensure you're working from it.
* Run mvn clean install to install all dependencies
* You can use your locally installed MongoDB for database, You can also use other kind of database do configure to your choice in the application.properties file.
* Check the application.properties file in the resources folder and add your variables.
### Usage
* Run the application by locating the LogisticApplication class and then click on run.
* Connect to the API using Postman on port 8080.
### API Endpoints
| HTTP Verbs | Endpoints | Action |
| --- | --- | --- |
| POST | /v1/auth/register | To sign up a new user account |
| POST | /v1/auth/login | To login an existing user account |
| POST | /v1/location/add | To add a location |
| PUT | /v1/location/:locationId/update | To edit details of a location |
| GET | /v1/location/:causeId | To retrieve details of a single location |
| GET | /v1/location | To retrieve the details of a all locations |
| DELETE | /v1/location/:causeId | To delete a single location |
| POST | /v1/delivery/generate_details | To generate the optimal route for delivery and delivery cost |
### Technologies Used
* [Springboot](https://www.spring.io/) This is a popular Java-based framework that is used for building stand-alone, production-ready web applications and microservices
* [MongoDB](https://www.mongodb.com/) This is a free open source NOSQL document database with scalability and flexibility. Data are stored in flexible JSON-like documents.
* [HERE API](https://here.com/) This is a free-tier external API to calculate route between two points.
### Swaagger Documentation
* [Optimal Delivery Route API](http://localhost:8080/swagger-ui/index.html)
### Authors
* [Omotolani Ligali](https://github.com/liomotolani)
### License
This project is available for use under the MIT License.
