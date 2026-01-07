# builder stage (example for a Node app)
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY . .
RUN npm run build

# runtime stage
FROM node:18-alpine
# create a non-root user and use a minimal base
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app

# copy only production artifacts
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/dist ./dist
COPY package.json .

# ensure files are not writable by others if possible
RUN chown -R appuser:appgroup /app && chmod -R 755 /app

USER appuser
ENV NODE_ENV=production
# Drop capabilities at runtime via container runtime / k8s, and set a default no-new-privileges in container spec
EXPOSE 3000
CMD ["node", "dist/index.js"]
