# Spaceship CRUD Application

Este proyecto es una API CRUD para gestionar naves espaciales de series y películas. Está construido con **Spring Boot**.

## URLs importantes

### Swagger UI

La documentación de la API está disponible a través de **Swagger**:

- **Swagger UI**: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

## Ejecutar con Docker

Sigue estos pasos para ejecutar la aplicación utilizando Docker.

### 1. Construir la imagen Docker

Primero, asegúrate de tener Docker instalado y en ejecución. Luego, ejecuta el siguiente comando para construir la imagen Docker:

```bash
docker build -t spaceship-app .
```

### 2. Arrancar la imagen Docker

Una vez se ha construido la imagen, ejecutar el siguiente comando:

```bash
docker run -p 8080:8080 spaceship-app
```
