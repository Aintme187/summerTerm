FROM nginx:1.17

COPY ./dist /usr/share/nginx/html

EXPOSE 80

ENTRYPOINT [ "nginx","-g","daemon off;" ]