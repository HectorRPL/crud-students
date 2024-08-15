# Student CRUD Application

## Descripción del Proyecto

Esta aplicación es un sistema de gestión de estudiantes donde los usuarios pueden crear, listar, actualizar y eliminar estudiantes. La aplicación está construida utilizando **Spring Boot 3.3.0** y **Java 21**, aprovechando las nuevas características de Java como Streams API y Pattern Matching.

## Tecnologías Utilizadas

- **Java 21**: Con mejoras en el Stream API y patrones de coincidencia (Pattern Matching).
- **Spring Boot 3.3.0**: Para construir una API REST robusta.
- **JPA**: Para la gestión de las entidades.
- **PostgreSQL**: Base de datos utilizada para almacenar los datos de los estudiantes.
- **JUnit y Mockito**: Para escribir pruebas unitarias.
- **SLF4J con Logback**: Para la implementación de logs.
- **Postman**: Para la colección de pruebas de la API.

## Funcionalidades

- **CRUD de Estudiantes**:
    - Crear, listar, actualizar y eliminar estudiantes.
    - Los estudiantes tienen atributos como nombre, apellido, email, etc.

- **Validación de Datos**:
    - Validación de los datos de entrada en los endpoints utilizando anotaciones de validación de Spring.

- **Gestión de Errores**:
    - Excepciones personalizadas para manejar casos como la no existencia de un estudiante al intentar actualizarlo o eliminarlo.
    - Controlador global de excepciones para capturar y manejar errores en toda la aplicación.

- **Logs**:
    - Implementación de logs con SLF4J para monitorear las operaciones críticas dentro de la aplicación.

## Instalación y Configuración

1. **Clona el repositorio**:
```bash
   git clone https://github.com/HectorRPL/crud-students.git
   cd student-crud
```

## Instalación y Configuración


2. **Configura la Base de Datos:**:
   - Asegúrate de tener PostgreSQL instalado y configura la base de datos en application.properties:

```bash     
    spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
    spring.datasource.username=tuusuario
    spring.datasource.password=tupassword
    spring.jpa.hibernate.ddl-auto=update
```

3. **Construcción y Ejecución**:
   - Para construir y ejecutar la aplicación:
```bash
  mvn clean install
  mvn spring-boot:run
```

4. **Colección de Postman**:
  - La colección de Postman para probar la API está disponible en la carpeta postman del repositorio. Importa la colección en Postman para realizar las pruebas de los endpoints de la aplicación.
  - Ubicación:

```bash
  docs/student-crud.postman_collection.json
```

4. **Pruebas Unitarias**:
  - Las pruebas unitarias están ubicadas en el directorio src/test/java. Estas pruebas cubren tanto los controladores como los servicios de la aplicación.
  - Para ejecutar las pruebas:

```bash
  mvn test
```