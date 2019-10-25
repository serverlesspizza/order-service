#!/bin/bash

aws cloudformation delete-stack 
    --stack-name 'serverlesspizza-order-service-pipeline' 
    --region eu-west-1
    --profile aws-serverlesspizza-devops
