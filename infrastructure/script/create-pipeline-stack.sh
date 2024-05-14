#!/usr/bin/env bash
if [ $# -eq 0 ]; then
  echo "缺少stack-name參數"
  exit
fi

if [ $# -eq 1 ]; then
  echo "缺少region參數"
  exit
fi

stackName=$1
region=$2
echo
echo stack-name: "$stackName"
echo region: "$region"
echo "開始部署pipeline..."
aws cloudformation create-stack --stack-name "$stackName" --template-body file://infrastructure/codepipeline.yml --capabilities CAPABILITY_IAM --region "$region"
echo "結束部署pipeline..."
