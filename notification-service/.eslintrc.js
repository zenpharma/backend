// ESLint config for notification-service (Node.js / Express)
module.exports = {
  env: {
    node: true,
    es2021: true,
    jest: true,
  },
  extends: ['eslint:recommended'],
  parserOptions: {
    ecmaVersion: 2021,
    sourceType: 'commonjs',
  },
  rules: {
    // ── Code quality ──────────────────────────────────────────────────────
    'no-console': ['warn', { allow: ['warn', 'error', 'info'] }],
    'no-unused-vars': ['warn', { argsIgnorePattern: '^_', caughtErrorsIgnorePattern: '^_' }],
    'prefer-const': 'error',
    'no-var': 'error',

    // ── Error handling ────────────────────────────────────────────────────
    'no-throw-literal': 'error',         // always throw Error objects, not strings
    'handle-callback-err': 'warn',

    // ── Security-relevant ────────────────────────────────────────────────
    'no-eval': 'error',
    'no-implied-eval': 'error',
    'no-new-func': 'error',
  },
};
