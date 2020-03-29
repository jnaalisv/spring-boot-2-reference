### Spring Boot 2 sample project
- gradlew 6.x with Gradle Script Kotlin and JaCoCo
- Spring Boot 2.x
- Hibernate 5.4
- JUnit Jupiter 5.6
- Java14

#### Todo
- real DB, migrations
- more entities and business logic

`
$ ./gradlew bootRun
`

`
$ http POST localhost:8080/products name=ShinyThingy
`

`
$ http GET localhost:8080/products
`

`
http PUT localhost:8080/products/1 id=1 version=1 name=ComputerMkII
`

`
$ http DELETE localhost:8080/products/1
`
