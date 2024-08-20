#!/bin/bash
# bashend.sh
# shut down backend
ps aux|grep 'backend-exec'|awk '{print $2}'|xargs kill -9

cd ../backend
bash ./mvnw clean package
cd -
nohup java -jar ../backend/blog-api/target/backend-exec.jar&