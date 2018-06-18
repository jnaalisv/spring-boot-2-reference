### Spring Boot 2 sample project
- gradlew 4.6 with kotlin
- Hibernate 5.2
- JUnit Jupiter

`
$ ./gradlew bootRun
`

`
$ http POST localhost:8080/products name=ShinyThingy
`

`
$ http GET localhost:8080/products
`

aws cloudformation validate-template --template-body file://product-service.yaml 

aws cloudformation create-stack \
    --stack-name product-service \
    --template-body file://product-service.yaml

aws cloudformation update-stack \
    --stack-name product-service \
    --template-body file://product-service.yaml


aws cloudformation describe-stacks --stack-name product-service