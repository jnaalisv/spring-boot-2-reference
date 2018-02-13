.PHONY: stack clean events describe

stack:
	aws cloudformation create-stack --stack-name AlphaBuckets --capabilities "CAPABILITY_NAMED_IAM" --template-body file://alpha-buckets.yaml

clean:
	aws cloudformation delete-stack --stack-name AlphaBuckets

events:
	aws cloudformation describe-stack-events --stack-name AlphaBuckets

describe:
	aws cloudformation describe-stacks --stack-name AlphaBuckets
