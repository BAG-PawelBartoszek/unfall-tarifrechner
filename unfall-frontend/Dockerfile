
# syntax=docker/dockerfile:1

# Build-Stage: Node baut Angular App
FROM node:22-alpine AS build
WORKDIR /app

# Dependencies installieren
COPY package*.json ./
RUN npm ci

# App bauen
COPY .. .
RUN npm run build

# Runtime-Stage: Nginx für statisches Hosting
FROM nginx:1.27-alpine
WORKDIR /usr/share/nginx/html

# Nginx Konfiguration
COPY <<'NGINX_CONF' /etc/nginx/conf.d/default.conf
server {
    listen 80;
    server_name _;
    root /usr/share/nginx/html;
    index index.html;

    # SPA Fallback
    location / {
        try_files $uri $uri/ /index.html;
    }

    # API Proxy zum Backend
    location /api/ {
        proxy_pass http://backend:8080/api/;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_cache_bypass $http_upgrade;
    }
}
NGINX_CONF

# Angular Build kopieren
COPY --from=build /app/dist/unfall-frontend/browser/ .

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD wget -qO- http://localhost/ >/dev/null 2>&1 || exit 1
