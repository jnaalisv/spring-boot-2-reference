include eb.env

.PHONY: build publishJar publishTemplate deploy clean undeploy

check-bucket:
	test -n "$(BUCKET)" || (echo "BUCKET must be defined in eb.env"; exit 1)

build:
	./gradlew build

publishJar: check-bucket build
	aws s3 cp build/libs/spring-boot-2-reference-0.0.1-SNAPSHOT.jar s3://$(BUCKET)/alpha.jar

publishTemplate: check-bucket
	aws s3 cp alpha-eb.template s3://$(BUCKET)/alpha-eb.template

deploy: publishJar publishTemplate
	aws cloudformation create-stack --stack-name AlphaElasticBeanStalk --template-url https://s3.amazonaws.com/$(BUCKET)/alpha-eb.template

undeploy:
	aws cloudformation delete-stack --stack-name AlphaElasticBeanStalk

clean: build
	find -d build | xargs rm -fd