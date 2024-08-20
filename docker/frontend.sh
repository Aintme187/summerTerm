cd ../frontend
#frontend
docker build .
docker run -p 5173:80 -d frontend