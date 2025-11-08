# Biblioteca

Proyecto Spring Boot generado automáticamente desde diagrama UML.

## Descripción

Este proyecto contiene 5 clases principales:

- **Usuario** (entity): Sin descripción
- **Miembro** (entity): Sin descripción
- **Libro** (entity): Sin descripción
- **Prestamo** (entity): Sin descripción
- **AccesoBiblioteca** (entity): Sin descripción


## 🔐 Autenticación

Este proyecto incluye un **sistema de autenticación JWT completo** generado automáticamente porque se detectó una entidad **Usuario** con campos de email y contraseña.

### Endpoints de Autenticación

- **POST** `/api/auth/register` - Registrar nuevo usuario
- **POST** `/api/auth/login` - Iniciar sesión
- **GET** `/api/auth/validate` - Validar token (requiere autenticación)

### Ejemplo de Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@email.com",
    "password": "123456"
  }'
```

### Ejemplo de Uso con Token

```bash
curl -X GET http://localhost:8080/api/usuario \
  -H "Authorization: Bearer tu_jwt_token_aqui"
```

### Características de Seguridad

- ✅ **JWT (JSON Web Tokens)** para autenticación stateless
- ✅ **BCrypt** para cifrado de contraseñas
- ✅ **Spring Security** configurado automáticamente
- ✅ **CORS** habilitado para frontend
- ✅ **Manejo de errores** personalizado
- ✅ **Validaciones** de entrada en DTOs

## Estructura del Proyecto

```
src/main/java/com/biblioteca/
├── domain/
│   ├── model/          # Entidades JPA
│   ├── repository/     # Repositorios
│   └── service/        # Servicios de negocio
├── web/
│   ├── controller/     # Controladores REST
│   └── dto/           # DTOs Request/Response
├── auth/
│   ├── controller/     # AuthController
│   ├── service/        # AuthService, UserDetailsService
│   ├── dto/           # DTOs de autenticación
│   └── filter/        # Filtros JWT
├── config/            # SecurityConfig, JwtUtil
├── exception/         # Manejo global de errores
└── util/              # Clases utilitarias
```

## Tecnologías

- **Spring Boot 3.2.0**
- **Java 17**
- **Spring Data JPA**
- **MySQL 8.0**
- **Lombok**
- **Bean Validation**

## Configuración

### Opción 1: Docker (Recomendado)

1. **Ejecutar MySQL con Docker:**
   ```bash
   docker run --name biblioteca-mysql \
     -e MYSQL_ROOT_PASSWORD=root \
     -e MYSQL_DATABASE=biblioteca_db \
     -p 3306:3306 -d mysql:8.0
   ```

2. **Ejecutar aplicación:**
   ```bash
   mvn spring-boot:run
   ```

3. **Comandos útiles:**
   ```bash
   # Ver el contenedor
   docker ps

   # Detener MySQL
   docker stop biblioteca-mysql

   # Iniciar MySQL existente
   docker start biblioteca-mysql

   # Eliminar contenedor
   docker rm -f biblioteca-mysql
   ```

### Opción 2: MySQL Local

1. Crear base de datos: `CREATE DATABASE biblioteca_db;`
2. Configurar credenciales en `application.properties`
3. Ejecutar: `mvn spring-boot:run`

## Inicio Rápido

```bash
# 1. Crear base de datos MySQL
docker run --name biblioteca-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=biblioteca_db -p 3306:3306 -d mysql:8.0

# 2. Esperar 30 segundos para que MySQL inicie
sleep 30

# 3. Ejecutar aplicación Spring Boot
mvn spring-boot:run

# 4. Probar API
curl http://localhost:8080/api/usuario
```

## Endpoints Generados

### Usuario
- GET    `/api/usuario`     - Listar todos
- GET    `/api/usuario/{id}` - Obtener por ID
- POST   `/api/usuario`     - Crear nuevo
- PUT    `/api/usuario/{id}` - Actualizar
- DELETE `/api/usuario/{id}` - Eliminar

### Miembro
- GET    `/api/miembro`     - Listar todos
- GET    `/api/miembro/{id}` - Obtener por ID
- POST   `/api/miembro`     - Crear nuevo
- PUT    `/api/miembro/{id}` - Actualizar
- DELETE `/api/miembro/{id}` - Eliminar

### Libro
- GET    `/api/libro`     - Listar todos
- GET    `/api/libro/{id}` - Obtener por ID
- POST   `/api/libro`     - Crear nuevo
- PUT    `/api/libro/{id}` - Actualizar
- DELETE `/api/libro/{id}` - Eliminar

### Prestamo
- GET    `/api/prestamo`     - Listar todos
- GET    `/api/prestamo/{id}` - Obtener por ID
- POST   `/api/prestamo`     - Crear nuevo
- PUT    `/api/prestamo/{id}` - Actualizar
- DELETE `/api/prestamo/{id}` - Eliminar

### AccesoBiblioteca
- GET    `/api/acceso-biblioteca`     - Listar todos
- GET    `/api/acceso-biblioteca/{id}` - Obtener por ID
- POST   `/api/acceso-biblioteca`     - Crear nuevo
- PUT    `/api/acceso-biblioteca/{id}` - Actualizar
- DELETE `/api/acceso-biblioteca/{id}` - Eliminar

## Notas

- Los DTOs y validaciones deben completarse según reglas de negocio
- Los métodos de mapeo en Services requieren implementación específica
- Se recomienda agregar tests unitarios e integración
- Configurar profiles para diferentes ambientes

---
*Generado automáticamente por UML Diagrammer*