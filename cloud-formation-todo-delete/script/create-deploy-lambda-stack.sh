#!/usr/bin/env bash
aws cloudformation create-stack --stack-name ptt-crawler --template-body file://cloud-formation/create-lambda-cfn.yml --capabilities CAPABILITY_IAM --region us-west-2
