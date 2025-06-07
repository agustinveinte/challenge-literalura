# 📚 challenge-literalura en Spring Boot para Gestión de Libros

Esta es una aplicación de consola desarrollada con **Spring Boot 3.5.0** y **Java 17** que se conecta a una API externa (`https://gutendex.com/books`) para obtener información de libros y realiza operaciones sobre una base de datos **PostgreSQL**.

---

## 🚀 Características principales

La aplicación ofrece un menú de consola interactivo con las siguientes opciones:

```
MENU PRINCIPAL
1 - Buscar libros por título.
2 - Listar libros registrados.
3 - Listar autores registrados.
4 - Listar autores vivos en un determinado año.
5 - Listar libros por idioma.
0 - Salir.
```

---

## ⚙️ Tecnologías utilizadas

* Java 17
* Spring Boot 3.5.0
* Spring Data JPA
* PostgreSQL
* OpenAPI/REST (consumo de `https://gutendex.com/books`)
* Maven o Gradle (según tu configuración)

---

## 🧩 Configuración de entorno

La aplicación utiliza variables de entorno para la configuración de la base de datos PostgreSQL. Asegúrate de definir las siguientes variables:

```properties
# application.properties

spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Variables de entorno requeridas

| Variable      | Descripción                      |
| ------------- | -------------------------------- |
| `DB_HOST`     | Hostname del servidor PostgreSQL |
| `DB_NAME`     | Nombre de la base de datos       |
| `DB_USER`     | Usuario de la base de datos      |
| `DB_PASSWORD` | Contraseña del usuario de la BD  |

---

## 📊 Base de datos

Los datos obtenidos de la API se almacenan localmente en PostgreSQL.


