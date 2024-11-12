#  Matinsa S.A de S.V

## Contenido

Back-End controlador de ordenes de producción de Matinsa S.A de S.V.
El proyecto esta realizado en:

- Spring Boot 3+
- PostgreSQL

## Programas necesarios

Para poder utilizar el proyecto en localhost en necesario clonarlo y tener algunos programas necesarios:

- [PostMan](https://www.postman.com/downloads/) para puebas de APIS. (Opcional)
- [Git](https://git-scm.com/downloads) para poder gestionar las versiones.

- **Java Development Kit 19 (JDK)**: para descargar la versión de JDK 19 desde [el sitio web de Oracle](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html).

- **IDE de desarrollo**:IDE recomendado [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

- Gestor de dependencias: [Maven](https://maven.apache.org/download.cgi).

## 🔍 Como Clonar

Comando para clonar:

```bash
git clone https://github.com/mishelrodri/matinsa-backend.git
```

## 🚀  Run en localhost:

- **Configurar las credenciales en el archivo** `application.properties`:
  - Crea una base de datos en PostgreSQL con el nombre `db_matinsa`.
  - Encuentra el archivo `application.properties-template` en la carpeta `src/main/resources/`.
  - Cambia el nombre del archivo a `application.properties`, eliminando el sufijo "-template".
  - Configura las credenciales necesarias dentro del archivo `application.properties` , como usuario de base de datos y contraseña.
  - 
- **Configurar la clave de JWT:**
  - Ejecuta el archivo `JWTSecretGenerator.java` ubicado en `src/main/java/com/matinsa/backend/`.
  - Copia la respuesta generada por el programa.
  - Pega la respuesta en el campo `jwt.secret` del archivo `application.properties`.

- **Ejecutar la API:**
  - Ejecuta la clase principal `MatinsaBackendApplication.java` ubicada en `src/main/java/com/matinsa/backend/`.
  - 
Nota: Al ejecutar la aplicación por primera vez, se generarán automáticamente algunos datos, como los usuarios, en la base de datos.

## 🔐 Credentials
- username: administrador -> password: 1234
- username: bodega -> password: 1234
- username: produccion -> password: 1234

