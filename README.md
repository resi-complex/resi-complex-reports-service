# resi-complex-reports-service

Microservicio de Analítica y Reportes Gerenciales para **resi-complex**.

## Configuración
- **Puerto:** `8089`
- **Base de datos MySQL:** `reports_db`
- **Migraciones Flyway:** `src/main/resources/db/migration/V1__init_reports.sql`
- **Swagger UI:** `http://localhost:8089/swagger-ui/index.html`

## Endpoints principales (`/api/v1/reports`)
- `GET /api/v1/reports/fee-stats` — Estadísticas de cartera y unidades con más de 2 cuotas en mora
- `POST /api/v1/reports/fee-stats` — Actualizar proyección de cartera de una unidad
- `GET /api/v1/reports/maintenance-stats` — Solicitudes de mantenimiento por estado (`PENDING`, `IN_PROGRESS`, `RESOLVED`)
- `POST /api/v1/reports/maintenance-stats` — Actualizar conteo proyectado por estado de mantenimiento
