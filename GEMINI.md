# Gestión de Películas - Servicio en Memoria

Este proyecto es una API REST desarrollada con **Spring Boot** para la gestión de películas, utilizando almacenamiento en memoria. 

## Tecnologías Utilizadas

- **Java**: 17
- **Spring Boot**: 4.0.5
  - `spring-boot-starter-web`
  - `spring-boot-starter-validation`
- **Lombok**: Reducción de código boilerplate (Getters, Setters, Constructors).
- **Apache Commons Lang**: Utilidades adicionales.
- **Maven**: Gestor de dependencias y construcción del proyecto (`pom.xml` y `mvnw`).

## Arquitectura

El proyecto sigue una arquitectura clásica de aplicaciones web Java dividida en capas:

- **Entities**: Representan los objetos del modelo de negocio (`Pelicula`, `Comentario`).
- **Controllers**: Gestionan las peticiones HTTP y las respuestas (`PeliculasController`).
- **Services**: Contienen la lógica de negocio, sirviendo como puente entre los controladores y los repositorios (`PeliculasService`, `PeliculasServiceImpl`).
- **Repositories**: Se encargan de la persistencia (en este caso en memoria) y la recuperación de datos (`PeliculasRepository`, `PeliculasRepositoryImpl`).

## Entidades Principales

### `Pelicula`
- `id` (String) - Identificador único de la película.
- `titulo` (String) - Título de la película.
- `director` (String) - Director de la película.
- `tokenDescarga` (String) - Token para descargas.
- `comentarios` (Array de `Comentario`) - Arreglo de comentarios asociados.

### `Comentario`
(Detalles definidos en `Comentario.java`).

## Endpoints de la API

La API expone los siguientes endpoints para interactuar con las películas:

| Método | Endpoint | Descripción |
| --- | --- | --- |
| `GET` | `/peliculas` | Obtiene la lista de todas las películas. Soporta un parámetro `q` para filtrar (ej. `/peliculas?q=terminator`). |
| `POST` | `/peliculas` | Crea una nueva película. Valida los campos obligatorios como `id`, `titulo` y `director`. |
| `GET` | `/peliculas/{id}` | Obtiene los detalles de una película específica por su `id`. |
| `GET` | `/peliculas/{id}/comentarios` | Obtiene la lista de comentarios asociados a una película por su `id`. |

## Cómo Ejecutar el Proyecto

Dado que el proyecto utiliza Maven Wrapper, puedes ejecutarlo sin necesidad de tener Maven instalado globalmente en tu sistema.

Desde la raíz del proyecto (donde se encuentra `pom.xml`), ejecuta:

**En Windows:**
```cmd
mvnw.cmd spring-boot:run
```

**En Linux/Mac:**
```bash
./mvnw spring-boot:run
```

La aplicación se iniciará por defecto en el puerto `8080` (a menos que se especifique lo contrario en `application.properties`).

## Tests

Para ejecutar las pruebas del proyecto (utilizando `spring-boot-starter-test`):

```cmd
mvnw.cmd test
```
