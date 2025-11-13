FROM node:25-alpine3.21 AS build

WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

FROM nginx:1.29-alpine AS production

COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80

CMD [ "nginx", "-g", "daemon off;" ]