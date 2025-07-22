
FROM node:16-alpine
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

RUN npm run build

EXPOSE 80

CMD ["npx", "http-server", "dist/frontend", "-p", "80"]
