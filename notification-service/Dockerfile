FROM node:22-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci --omit=dev
FROM node:22-alpine
WORKDIR /app
RUN addgroup -S pharma && adduser -S pharma -G pharma
COPY --from=builder /app/node_modules ./node_modules
COPY src ./src
COPY package.json .
USER pharma
EXPOSE 3000
CMD ["node", "src/index.js"]