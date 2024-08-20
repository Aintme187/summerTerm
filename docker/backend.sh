#!/bin/bash
cd ../backend
#backend
docker build .
docker run -p 8888:8888 -d backend