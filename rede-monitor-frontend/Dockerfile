FROM node:25-alpine3.21

WORKDIR /app
COPY . .
RUN npm install

EXPOSE 5173

CMD [ "npm", "run", "dev" ]