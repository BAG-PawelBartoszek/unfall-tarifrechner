# syntax=docker/dockerfile:1

# 1) Build-Stage: Angular Production Build
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
# erzeugt /app/dist/frontend (Standardprojektname "frontend" laut angular.json)
RUN npm run build

# 2) Runtime-Stage: statisches Hosting via Nginx
FROM nginx:1.27-alpine
# SPA-Fallback: alle Routen auf index.html
RUN rm -f /etc/nginx/conf.d/default.conf
COPY <<'NGINX_CONF' /etc/nginx/conf.d/default.conf
server {
  listen 80;
  server_name _;
  root /usr/share/nginx/html;
  index index.html;

  location / {
    try_files $uri $uri/ /index.html;
  }
}
NGINX_CONF

# Kopiert den Browser-Build
COPY --from=build /app/dist/frontend/browser/ /usr/share/nginx/html/
EXPOSE 80
HEALTHCHECK --interval=30s --timeout=3s --retries=3 CMD wget -qO- http://localhost/ >/dev/null 2>&1 || exit 1
