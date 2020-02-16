FROM node:12.10.0

WORKDIR /usr/app

COPY package*.json ./

RUN npm ci -qy

COPY . .

EXPOSE 8181

CMD ["npm", "install"]
CMD ["npm", "start"]