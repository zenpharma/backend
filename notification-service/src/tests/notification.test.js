const request = require('supertest');
const app = require('../index');

describe('Notification Service', () => {
  test('GET /actuator/health returns UP', async () => {
    const res = await request(app).get('/actuator/health');
    expect(res.statusCode).toBe(200);
    expect(res.body.status).toBe('UP');
  });

  test('POST /api/notifications/send validates input', async () => {
    const res = await request(app)
      .post('/api/notifications/send')
      .send({ type: 'INVALID' });
    expect(res.statusCode).toBe(400);
  });
});
