#!/bin/bash
cd ../backend
#backend
docker build . -t backend
docker run -p 8888:8888 -d backend