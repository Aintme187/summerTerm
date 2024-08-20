cd ../frontend
#frontend
docker build . -t frontend
docker run -p 5173:80 -d frontend