include ../eb.env
include ../aws.env

.PHONY: stack clean events describe

stack:
	aws --region $(AWS_REGION) cloudformation create-stack --stack-name $(INFRA_STACK_NAME) --capabilities "CAPABILITY_NAMED_IAM" --template-body file://cf-infra.yaml --parameters ParameterKey=DeploymentBucketNameParam,ParameterValue=$(DEPLOYMENT_BUCKET_NAME)

clean:
	aws --region $(AWS_REGION) cloudformation delete-stack --stack-name $(INFRA_STACK_NAME)

events:
	aws --region $(AWS_REGION) cloudformation describe-stack-events --stack-name $(INFRA_STACK_NAME)

describe:
	aws --region $(AWS_REGION) cloudformation describe-stacks --stack-name $(INFRA_STACK_NAME)