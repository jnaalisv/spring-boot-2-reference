include eb.env
include aws.env

.PHONY: build publishJar publishTemplate deploy clean undeploy events describe

check-bucket:
	test -n "$(DEPLOYMENT_BUCKET_NAME)" || (echo "DEPLOYMENT_BUCKET_NAME must be defined in eb.env"; exit 1)

build:
	./gradlew build

publishJar: check-bucket build
	aws --region $(AWS_REGION) s3 cp build/libs/spring-boot-2-reference-0.0.1-SNAPSHOT.jar s3://$(DEPLOYMENT_BUCKET_NAME)/$(APP_JAR_NAME)

publishTemplate: check-bucket
	aws --region $(AWS_REGION) s3 cp alpha-eb.yml s3://$(DEPLOYMENT_BUCKET_NAME)/alpha-eb.yml

deploy: publishJar publishTemplate
	aws --region $(AWS_REGION) cloudformation create-stack --stack-name $(STACK_NAME) --template-url https://s3.amazonaws.com/$(DEPLOYMENT_BUCKET_NAME)/alpha-eb.yml --parameters ParameterKey=AppJarName,ParameterValue=$(APP_JAR_NAME) ParameterKey=SourceBucket,ParameterValue=$(DEPLOYMENT_BUCKET_NAME)

undeploy:
	aws --region $(AWS_REGION) cloudformation delete-stack --stack-name $(STACK_NAME)

clean: build
	find -d build | xargs rm -fd

events:
	aws --region $(AWS_REGION) cloudformation describe-stack-events --stack-name $(STACK_NAME)

describe:
	aws --region $(AWS_REGION) cloudformation describe-stacks --stack-name $(STACK_NAME)