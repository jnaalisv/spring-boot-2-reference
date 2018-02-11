stack:
	aws cloudformation create-stack --stack-name AlphaBuckets --capabilities "CAPABILITY_NAMED_IAM" --template-body file://alpha-buckets.yaml

clean:
	aws cloudformation delete-stack --stack-name AlphaBuckets
