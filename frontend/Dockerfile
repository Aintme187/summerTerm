FROM node:22-bullseye-slim

WORKDIR /app/frontend

COPY package*.json ./

RUN npm install

COPY ./ ./

RUN npm run build

FROM nginx:1.27.0

COPY --from=0 /app/frontend/dist /usr/share/nginx/html

EXPOSE 80

ENTRYPOINT [ "nginx","-g","daemon off" ]