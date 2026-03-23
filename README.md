# WhatsApp SaaS Platform

A comprehensive SaaS automation platform for WhatsApp with AI integration, built with Java 17, Spring Boot, and microservices architecture.

## Features

- **WhatsApp Integration**: Automated messaging and webhook handling
- **AI-Powered**: OpenAI integration for intelligent responses
- **State Machine Workflow**: Advanced workflow management
- **Multi-tenant Database**: PostgreSQL with vector support
- **Message Queue**: Kafka for reliable event processing
- **Caching**: Redis for high-performance data access
- **Billing System**: Subscription and payment management
- **CRM**: Customer relationship management
- **Webhook API**: External integrations

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.2.4
- **Database**: PostgreSQL with pgvector
- **Cache**: Redis
- **Message Queue**: Kafka
- **AI**: OpenAI API
- **Build**: Maven (multi-module)
- **Container**: Docker
- **Orchestration**: Kubernetes

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   saas-webhook  │    │ saas-workflow   │    │   saas-ai       │
│                 │    │                 │    │                 │
│   Webhook       │───▶│   State Machine │───▶│   OpenAI        │
│   Processing    │    │   Engine        │    │   Integration   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ saas-application│    │ saas-database   │    │ saas-core       │
│                 │    │                 │    │                 │
│   REST API      │    │   Data Access   │    │   Shared Libs   │
│   Gateway       │    │   Layer         │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   PostgreSQL    │    │     Redis       │    │     Kafka       │
│   (pgvector)    │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Quick Start

### Prerequisites

- Java 17+
- Maven 3.9+
- Docker & Docker Compose
- Git

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/VamshiKrishnaBandari07/whatsapp-SaaS-platform.git
   cd whatsapp-saas-platform
   ```

2. **Start infrastructure**
   ```bash
   docker-compose up -d
   ```

3. **Build and run**
   ```bash
   mvn clean install
   cd saas-application
   mvn spring-boot:run
   ```

4. **Access the application**
   - API: http://localhost:8080
   - Health check: http://localhost:8080/actuator/health

### Environment Variables

Create a `.env` file or set environment variables:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/whatsapp_saas?sslmode=disable
SPRING_DATASOURCE_USERNAME=saas_admin
SPRING_DATASOURCE_PASSWORD=saas_password
SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:29092
OPENAI_API_KEY=your_openai_api_key
OPENAI_API_BASE_URL=https://api.openai.com
```

## Deployment

### Docker

```bash
# Build image
docker build -f saas-application/Dockerfile -t whatsapp-saas-platform .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=... \
  -e SPRING_DATASOURCE_USERNAME=... \
  -e SPRING_DATASOURCE_PASSWORD=... \
  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=... \
  -e OPENAI_API_KEY=... \
  whatsapp-saas-platform
```

### Kubernetes

```bash
# Apply manifests
kubectl apply -f k8s/

# Get service URL
kubectl get svc whatsapp-saas-service
```

### Cloud Platforms

#### Railway (Recommended)
1. Connect GitHub repo
2. Add PostgreSQL and Redis services
3. Set environment variables
4. Deploy automatically

#### Fly.io
```bash
flyctl launch
flyctl secrets set SPRING_DATASOURCE_URL=...
flyctl deploy
```

#### Google Cloud Run
```bash
gcloud run deploy --source . --platform managed
```

## API Documentation

Once running, access Swagger UI at: http://localhost:8080/swagger-ui.html

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For questions or support, please open an issue on GitHub.

---

Built with ❤️ for scalable WhatsApp automation