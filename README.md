# Control de Productos

Aplicación web desarrollada con Spring Boot que permite gestionar negocios con sus respecitvos productos y usuarios con manejo de roles.

## Tecnologías
- Java
- Spring Boot
- Spring Security
- Spring data JPA
- Thymeleaf
- Bootstrap 5
- MySQL

## Funcionalidades

- Registro de usuarios
- Login con autenticación segura
- Gestión de roles (ADMIN / USER)
- Protección para evitar eliminar o modificar el último administrador
- CRUD de productos y negocios
- Validaciones de formulario
- Alertas dinámicas con Thymeleaf
- Internacionalizacion(Cambio de idioma)

## Seguridad

- Autenticación con Spring Security
- Contraseñas encriptadas con BCrypt
- Control de acceso por roles
- Protección contra eliminación o edición del último administrador
- Creacion automática de un administrador al iniciar la aplicación por primera vez


## Decisiones técnicas

- Se implementó validación para evitar que el sistema quede sin administradores.
- Se refresca la sesión al modificar datos sensibles del usuario.
- Se utiliza fragmentación con Thymeleaf para reutilizar componentes.

# Capturas

## Login
<img width="1916" height="775" alt="image" src="https://github.com/user-attachments/assets/f1b56b52-9b73-413a-b2c4-c2af58ba6eb4" />

## Página principal(con negocios)
<img width="1915" height="779" alt="image" src="https://github.com/user-attachments/assets/07f67326-fa6f-472b-8dae-fda871bdf36f" />

## Gestion de usuarios
<img width="1914" height="771" alt="image" src="https://github.com/user-attachments/assets/15a6b243-7239-4f98-a5ab-9c6d79370ac4" />

## Gestión de productos
<img width="1898" height="876" alt="image" src="https://github.com/user-attachments/assets/fe35beab-79ca-48d7-8b84-ad81a40c7c04" />

## Manejo de edición o eliminación de último admin
<img width="1913" height="774" alt="image" src="https://github.com/user-attachments/assets/8f5066eb-84c8-49f9-b947-5f2307035420" />


## Confirmacion de eliminación
<img width="1916" height="773" alt="image" src="https://github.com/user-attachments/assets/7fb8b209-fe75-43e3-ab6b-615012c5b887" />

# Cómo ejecutar

1. Clonar el repositorio:
   git clone https://github.com/tuusuario/control-productos.git

2. Configurar base de datos en application.properties

3. Ejecutar:
   Ejecutar la aplicacion Spring en su IDE de preferencia
