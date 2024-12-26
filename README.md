# Atours Backend 🚀

**Atours Backend** es una aplicación desarrollada con **Spring Boot**, diseñada para gestionar servicios relacionados
con rutas turísticas. Actúa como un monolito backend, integrado con tecnologías modernas para ofrecer una
solución completa y eficiente para crear y consultar rutas y puntes de interes.

---

## 📋 Índice

1. [Características principales](#-características-principales)
2. [Estructura del proyecto](#-estructura-del-proyecto)
3. [Requisitos previos](#-requisitos-previos)
4. [Instalación](#-instalación)
5. [Configuración](#-configuración)
6. [Tecnologías utilizadas](#-tecnologías-utilizadas)
7. [Contribución](#-contribución)

---

## 🌟 Características principales

- **API RESTful**: Gestión eficiente de recursos turísticos.
- **Persistencia robusta**: Uso de **Spring Data JPA** para la manipulación de datos relacionales.
- **Base de datos PostgreSQL**: Compatibilidad completa como fuente de datos principal.
- **Seguridad avanzada**: Protege las rutas de la API con **Spring Security** y autenticación basada en **JWT**.
- **Auditoría integrada**:
    - Registro automático de fechas y usuarios en cada operación.
    - Historial detallado de cambios con **Hibernate Envers**.
- **Pruebas integradas**: Cobertura de pruebas con **JUnit 5**.
- **Optimización del entorno de desarrollo**: Incluye **Spring Boot DevTools** para recarga automática.

---

## 📂 Estructura del proyecto

El proyecto sigue la convención estándar de Spring Boot:

```
atours-backend
│── pom.xml
│── mvnw / mvnw.cmd
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   └── test
```

---

## 🔧 Requisitos previos

Antes de ejecutar el proyecto, asegúrate de contar con los siguientes elementos instalados:

- **Java 17** o superior.
- **Maven 3.8** o superior.
- **PostgreSQL** configurado y en ejecución.

---

## 🚀 Instalación

Sigue estos pasos para instalar y ejecutar el proyecto en tu entorno local:

### 1. Clona el repositorio

```bash
git clone https://github.com/usuario/atours-backend.git
cd atours-backend
```

### 2. Construye el proyecto con Maven

```bash
./mvnw clean install
```

### 3. Ejecuta la aplicación

```bash
./mvnw spring-boot:run
```

---

## 🗂 Configuración

### 1. Configuración de la base de datos

El proyecto utiliza **PostgreSQL** como base de datos principal. Asegúrate de actualizar las credenciales en el
archivo `application.properties` o `application.yml` dentro de `src/main/resources`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

### 2. Seguridad con JWT

El backend implementa **Spring Security** con autenticación basada en **JSON Web Tokens (JWT)**. Los usuarios deben
autenticarse para acceder a recursos protegidos.

### 3. Auditoría automática

El proyecto incluye auditorías automáticas con las siguientes capacidades:

- **Registro de fechas**: Creación y última modificación.
- **Registro de usuarios**: Quién creó o modificó los registros.

Tablas de auditoría configuradas con Hibernate Envers:

```properties
spring.jpa.properties.hibernate.envers.audit_table_suffix=_AUD
spring.jpa.properties.hibernate.envers.store_data_at_delete=true
```

---

## 🛠 Tecnologías utilizadas

El proyecto utiliza un stack moderno y optimizado:

- **Spring Boot 3.3.4**
- **Spring Data JPA**
- **Spring Security** y **JWT**
- **Hibernate Envers** para auditorías históricas
- **PostgreSQL** como base de datos
- **JUnit 5** para pruebas unitarias
- **Spring Boot DevTools** para facilitar el desarrollo

---

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Para contribuir a **Atours Backend**, sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. Realiza tus cambios y haz commit:
   ```bash
   git commit -m "Añadida nueva funcionalidad"
   ```
4. Sube tus cambios:
   ```bash
   git push origin feature/nueva-funcionalidad
   ```
5. Abre un pull request en GitHub.

---

¡Espero que este README sea útil y atractivo! 😊
