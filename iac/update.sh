#!/bin/bash
aws cloudformation update-stack --stack-name 'serverlesspizza-order-service-pipeline' \
	--template-body file://cfn_codepipeline.yml --region eu-west-1 \
  --parameters ParameterKey=GitHubToken,ParameterValue=$AWS_GITHUB_TOKEN \
	--capabilities CAPABILITY_NAMED_IAM CAPABILITY_AUTO_EXPAND
