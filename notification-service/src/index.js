require('dotenv').config();
const express = require('express');
const { register, collectDefaultMetrics } = require('prom-client');
const notificationRouter = require('./routes/notifications');
const logger = require('./utils/logger');

const app = express();
const PORT = process.env.PORT || 3000;

collectDefaultMetrics({ prefix: 'notification_service_' });

app.use(express.json());

app.use('/api/notifications', notificationRouter);

app.get('/metrics', async (req, res) => {
  res.set('Content-Type', register.contentType);
  res.end(await register.metrics());
});

app.get('/actuator/health', (req, res) => {
  res.json({ status: 'UP', service: 'notification-service', version: process.env.npm_package_version || '1.0.0' });
});

app.get('/actuator/health/readiness', (req, res) => {
  res.json({ status: 'UP', timestamp: new Date().toISOString() });
});

app.get('/actuator/health/liveness', (req, res) => {
  res.json({ status: 'UP', uptime: process.uptime() });
});

app.use((err, req, res, _next) => {
  logger.error(err.message);
  res.status(500).json({ error: 'Internal server error' });
});

app.listen(PORT, () => {
  logger.info(`Notification service running on port ${PORT}`);
});

module.exports = app;