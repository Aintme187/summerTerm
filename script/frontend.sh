#!/bin/bash
# frontend.sh
cd ../frontend
npm install
npm run build
cp -r ./dist/* /usr/share/nginx/html
nginx -s reload