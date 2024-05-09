#!/usr/bin/env bash
aws s3 cp cloud-formation/api.yml s3://todo-cloudformation-lambda-source/api.yml --region us-west-2
