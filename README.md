# Currency Fetcher

This service fetches currency exchange rates from Binance API and stores them in a PostgreSQL database.

## Features

### Scheduled rate fetching

- The service fetches rates every second from Binance API using a Spring Scheduler.
- To prevent duplicate execution in multi-pod deployments, ShedLock is used.

### Long polling API

- The service provides a long polling endpoint `GET /api/v1/rates` to wait for rate updates.
- More details are available via Swagger UI: `/swagger-ui/index.html`

### Monitoring & Logs

- The service is integrated with the ELK stack (Elasticsearch, Logstash, and Kibana) for comprehensive monitoring and log aggregation.
- Prometheus and Grafana are configured to query historical metrics, monitor trends over time, and set up alerts based on custom rules.