FROM nginx:1.17

COPY ./dist /usr/share/nginx/html

COPY ./default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

ENTRYPOINT [ "nginx","-g","daemon off;" ]