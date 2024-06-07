# Choice-task

### Modules

* `spring-boot-soap-ws` - spring-boot 2.x
* `spring-mvc-rest` - Apache Tomcat 8 and Spring 5
*  `database` - MariaDB 

### Assumptions

* the amenities is just a `catalog` in the system, so we don't have operations to interact with this catalog, we only used it
* the parameter `name` is unique for the Hotel model.
* we only have 2 required fields for the Hotel model (`name` and `address`).
* the parameter `rating` is a positive integer between 1 and 10 and is optional.
* for any internal and possible error we return `SERVICE_UNAVAILABLE` HTTP-503
* for the `delete` operation we apply `soft` delete instead of `hard` delete
