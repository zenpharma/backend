const nodemailer = require('nodemailer');
const logger = require('../utils/logger');

const transporter = nodemailer.createTransport({
  host: process.env.SMTP_HOST || 'localhost',
  port: parseInt(process.env.SMTP_PORT || '1025'),
  secure: process.env.SMTP_SECURE === 'true',
  auth: process.env.SMTP_USER ? {
    user: process.env.SMTP_USER,
    pass: process.env.SMTP_PASSWORD
  } : undefined
});

async function send(notification) {
  if (notification.type === 'EMAIL') {
    return sendEmail(notification);
  }
  logger.warn('SMS not implemented, logging instead', notification);
  return { messageId: `log-${Date.now()}` };
}

async function sendEmail({ recipient, subject, message }) {
  const info = await transporter.sendMail({
    from: process.env.SMTP_FROM || 'noreply@pharma.com',
    to: recipient,
    subject,
    text: message,
    html: `<div style="font-family: Arial, sans-serif;"><p>${message}</p></div>`
  });
  logger.info('Email sent', { messageId: info.messageId, recipient });
  return { messageId: info.messageId };
}

module.exports = { send };
