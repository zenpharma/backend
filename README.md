# zen-pharma-backend

Spring Boot microservices monorepo for the Zen Pharma platform. Contains 7 backend services built with Java 17 and deployed to AWS EKS via GitOps (ArgoCD).

> **Companion repos:**
> - [`zen-infra`](https://github.com/your-github-username/zen-infra) — Terraform for AWS infrastructure (EKS, RDS, ECR, IAM)
> - [`zen-pharma-frontend`](https://github.com/your-github-username/zen-pharma-frontend) — React frontend
> - [`zen-gitops`](https://github.com/your-github-username/zen-gitops) — ArgoCD apps + Helm values

---

## Services

| Service | Description | Port | DB |
|---|---|---|---|
| `api-gateway` | Spring Cloud Gateway — routes all external traffic | 8080 | No |
| `auth-service` | JWT authentication and user management | 8081 | PostgreSQL |
| `drug-catalog-service` | Drug catalogue — search, categories, formulary | 8082 | PostgreSQL |
| `inventory-service` | Stock levels, replenishment, batch tracking | 8083 | PostgreSQL |
| `manufacturing-service` | Production orders and batch manufacturing | 8084 | PostgreSQL |
| `supplier-service` | Supplier management and purchase orders | 8085 | PostgreSQL |
| `notification-service` | Email/SMS notifications (Node.js 20 / Express) | 8086 | No |

---

## Repository Structure

```
zen-pharma-backend/
├── api-gateway/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── auth-service/
│   └── ...
├── drug-catalog-service/
│   └── ...
├── inventory-service/
│   └── ...
├── manufacturing-service/
│   └── ...
├── notification-service/          ← Node.js (not Java)
│   └── ...
├── supplier-service/
│   └── ...
└── .github/
    └── workflows/
        ├── _java-build.yml        ← Reusable: full Java CI pipeline
        ├── _java-pr-check.yml     ← Reusable: lightweight PR check
        ├── _node-build.yml        ← Reusable: full Node.js CI pipeline
        ├── _node-pr-check.yml     ← Reusable: lightweight Node PR check
        ├── ci-<service>.yml       ← Full build + DEV deploy + QA PR (7 files)
        ├── ci-pr-<service>.yml    ← Feature branch check (7 files)
        └── promote-prod.yml       ← Manual PROD promotion trigger
```

---

## CI Pipeline Overview

Every push to `develop` or `release/**` runs the full pipeline for the changed service:

```
1. Gitleaks (secret scan)
2. Maven verify + JaCoCo coverage (≥ 80%)  — real PostgreSQL sidecar for DB services
3. CodeQL SAST (security-extended queries)
4. Semgrep SAST (p/java, p/spring-boot, p/owasp-top-ten)
5. OWASP Dependency Check (CVSS ≥ 7.0)
6. Docker build (multi-stage, non-root UID 1000)
7. Trivy image scan (HIGH/CRITICAL, ignore-unfixed)
8. ECR push → tag: sha-<7chars>
9. Cosign keyless sign (GitHub OIDC → Fulcio → Rekor)
10. Update envs/dev/values-<service>.yaml in zen-gitops → ArgoCD auto-syncs dev
11. Open QA promotion PR in zen-gitops
```

Feature branch pushes run only steps 1–5 (~5 min, no Docker/ECR).

**Authentication to AWS:** GitHub OIDC (no `AWS_ACCESS_KEY_ID` stored as a secret).

See [`zen-infra/docs/CICD-IMPLEMENTATION.md`](https://github.com/your-github-username/zen-infra/blob/main/docs/CICD-IMPLEMENTATION.md) for full architecture details.

---

## Branching Strategy

| Branch | Purpose | CI |
|---|---|---|
| `feat/*`, `fix/*`, `chore/*` | Feature development | Lightweight: test + SAST only |
| `develop` | Integration branch | Full pipeline + DEV deploy |
| `release/**` | Sprint release / hotfix | Full pipeline + DEV deploy |
| `main` | Stable / matches production | PR check only |

PROD is promoted manually via `promote-prod.yml` (workflow_dispatch with service dropdown).

---

## Local Development

### Prerequisites
- Java 17 (`sdk install java 17-tem`)
- Maven 3.9+
- Docker Desktop
- PostgreSQL 15 (for DB services)

### Run a service locally

```bash
# Auth service example
cd auth-service

# Start PostgreSQL (Docker)
docker run -d --name pharma-db \
  -e POSTGRES_DB=pharma \
  -e POSTGRES_USER=pharma \
  -e POSTGRES_PASSWORD=pharma \
  -p 5432:5432 postgres:15-alpine

# Set environment variables
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/pharma
export SPRING_DATASOURCE_USERNAME=pharma
export SPRING_DATASOURCE_PASSWORD=pharma
export JWT_SECRET=local-dev-secret

# Run
mvn spring-boot:run
```

### Run tests

```bash
cd auth-service
mvn verify                        # unit + integration tests + JaCoCo coverage
mvn verify -Pintegration-tests    # integration tests only
```

### Build Docker image locally

```bash
cd auth-service
docker build -t auth-service:local .
docker run -p 8081:8081 auth-service:local
```

---

## Required GitHub Secrets

Set in **Settings → Secrets and variables → Actions**:

| Secret | Description |
|---|---|
| `AWS_ACCOUNT_ID` | 12-digit AWS account ID |
| `GITOPS_TOKEN` | GitHub PAT with `contents: write` on `your-github-username/zen-gitops` |
| `SEMGREP_APP_TOKEN` | Semgrep Cloud token (optional) |
| `NVD_API_KEY` | NIST NVD API key for OWASP Dep Check (optional, faster) |

| Variable | Value |
|---|---|
| `GITOPS_REPO` | `your-github-username/zen-gitops` |

---

## Full Deployment Guide

See [`zen-infra/docs/FULL-DEPLOYMENT-GUIDE.md`](https://github.com/your-github-username/zen-infra/blob/main/docs/FULL-DEPLOYMENT-GUIDE.md) for the complete 4-stage deployment:
1. Provision infrastructure (Terraform via GitHub Actions in zen-infra)
2. Install K8s prerequisites (scripts in zen-infra)
3. CI pipeline (this repo — auto-triggered on push to develop)
4. ArgoCD CD (zen-gitops — ArgoCD watches this after step 2 setup)
