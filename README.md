# Tareas API — Spring Boot

![CI](https://github.com/TU_USUARIO/tareas-api-spring-boot/actions/workflows/ci.yml/badge.svg)

API REST para gestionar tareas (CRUD), construida con Spring Boot como evolución de mi proyecto **ToDoWeb** (PHP MVC).

## Tecnologías
Java 17 · Spring Boot 3 · Spring Data JPA (Hibernate) · MySQL · Bean Validation · Swagger/OpenAPI · JUnit 5 · Mockito · Docker · GitHub Actions

## Arquitectura
Arquitectura por capas:

```
Controller  ->  Service  ->  Repository  ->  MySQL
 (HTTP)       (lógica)      (acceso a datos)
```

- **DTOs** (`TareaRequest` / `TareaResponse`) para no exponer las entidades directamente.
- **Validación** de entrada con `@Valid`.
- **Manejo global de errores** con `@RestControllerAdvice` (respuestas JSON coherentes).

## Ejecutar con Docker (recomendado)
```bash
docker compose up --build
```
- API: http://localhost:8080/api/tareas
- Swagger UI: http://localhost:8080/swagger-ui.html

## Ejecutar en local (sin Docker para la app)
Requisitos: Java 17, Maven y un MySQL en `localhost:3306` (usuario `root`, contraseña `root`; se puede cambiar con las variables `DB_URL`, `DB_USER`, `DB_PASSWORD`).
```bash
mvn spring-boot:run
```

## Endpoints
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/tareas` | Lista todas las tareas (filtro opcional `?estado=PENDIENTE`) |
| GET | `/api/tareas/{id}` | Obtiene una tarea |
| POST | `/api/tareas` | Crea una tarea (201) |
| PUT | `/api/tareas/{id}` | Actualiza una tarea |
| DELETE | `/api/tareas/{id}` | Elimina una tarea (204) |

Estados posibles: `PENDIENTE`, `EN_PROGRESO`, `COMPLETADA`.

### Ejemplo
```bash
curl -X POST http://localhost:8080/api/tareas \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Estudiar Spring","descripcion":"Capitulo 1"}'
```

## Tests
```bash
mvn test
```
Los tests usan H2 en memoria (no necesitan MySQL). Hay cuatro niveles:

| Tipo | Clase | Qué comprueba |
|------|-------|---------------|
| Unitario (Mockito) | `TareaServiceTest` | Lógica del servicio, con el repositorio simulado |
| Repositorio (`@DataJpaTest`) | `TareaRepositoryTest` | Consultas JPA contra una BD real en memoria |
| Controlador (`@WebMvcTest`) | `TareaControllerTest` | Códigos HTTP, JSON y validaciones |
| Integración (`@SpringBootTest`) | `TareaApiIntegrationTest` | Flujo completo de la API |

La integración continua (GitHub Actions) ejecuta los tests en cada push.

## Autora
Fátima Lesme Ayala — [LinkedIn](https://linkedin.com/in/fatima-lesme) · [GitHub](https://github.com/fatimalesme)
