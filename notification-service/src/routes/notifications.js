const express = require('express');
const router = express.Router();
const notificationService = require('../services/notificationService');
const Joi = require('joi');
const logger = require('../utils/logger');

const schema = Joi.object({
  type: Joi.string().valid('EMAIL', 'SMS').required(),
  recipient: Joi.string().required(),
  subject: Joi.string().when('type', { is: 'EMAIL', then: Joi.required() }),
  message: Joi.string().required(),
  metadata: Joi.object().optional()
});

router.post('/send', async (req, res) => {
  const { error, value } = schema.validate(req.body);
  if (error) return res.status(400).json({ error: error.details[0].message });

  try {
    const result = await notificationService.send(value);
    res.json({ success: true, messageId: result.messageId });
  } catch (err) {
    logger.error('Failed to send notification', { error: err.message });
    res.status(500).json({ error: 'Failed to send notification' });
  }
});

router.post('/qc-alert', async (req, res) => {
  const { batchNumber, result, testedBy } = req.body;
  try {
    await notificationService.send({
      type: 'EMAIL',
      recipient: process.env.QC_ALERT_EMAIL || 'qc@pharma.com',
      subject: `QC Alert: Batch ${batchNumber}`,
      message: `Quality control test result for batch ${batchNumber}: ${result}. Tested by: ${testedBy}`
    });
    res.json({ success: true });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.post('/order-alert', async (req, res) => {
  const { orderNumber, supplier, status } = req.body;
  try {
    await notificationService.send({
      type: 'EMAIL',
      recipient: process.env.ORDER_ALERT_EMAIL || 'procurement@pharma.com',
      subject: `Purchase Order Update: ${orderNumber}`,
      message: `Order ${orderNumber} from supplier ${supplier} status: ${status}`
    });
    res.json({ success: true });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.get('/health', (req, res) => {
  res.json({ status: 'UP', service: 'notification-service' });
});

module.exports = router;
