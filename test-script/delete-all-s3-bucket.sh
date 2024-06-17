#!/bin/bash

allS3BucketName=$(aws s3api list-buckets --query 'Buckets[].Name' --output text)

for BUCKET_NAME in ${allS3BucketName}; do
  if [ ${BUCKET_NAME} != "kevin5603" ]
  then
    echo ${BUCKET_NAME}
    aws s3api delete-objects --bucket ${BUCKET_NAME} \
    --delete "$(aws s3api list-object-versions --bucket ${BUCKET_NAME} --query='{Objects: Versions[].{Key:Key,VersionId:VersionId}}')"
    aws s3api delete-objects --bucket ${BUCKET_NAME} \
    --delete "$(aws s3api list-object-versions --bucket ${BUCKET_NAME} --query='{Objects: DeleteMarkers[].{Key:Key,VersionId:VersionId}}')"
    aws s3api delete-bucket --bucket ${BUCKET_NAME}
  fi
done

