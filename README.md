# Instrucciones para iniciar el proyecto

Antes de ejecutar el proyecto, asegúrate de seguir estos pasos en orden:

## 1. Levantar XAMPP
- Abre XAMPP y activa los servicios:
  - **Apache**
  - **MySQL**

## 2. Crear la base de datos en phpMyAdmin
1. Ingresa a [http://localhost/phpmyadmin](http://localhost/phpmyadmin)
2. Crea una nueva base de datos con el nombre:
   ```
   desafio
   ```

## 3. Crear usuario de la base de datos
1. En phpMyAdmin, ve a la pestaña **Usuarios**.
2. Crea un nuevo usuario con los siguientes datos:
   - **Usuario:** `desafio_user`
   - **Contraseña:** `12346`
3. Asigna todos los privilegios a la base de datos `desafio` para este usuario.

## 4. Ejecutar el script de la base de datos
- Localiza el archivo:
  ```
  database script
  ```
- Ejecútalo en phpMyAdmin para crear las tablas y relaciones necesarias.

## 5. Configurar el pool de conexión en Payara
1. Abre la consola de administración de Payara: [http://localhost:4848](http://localhost:4848)
2. Crea un nuevo **JDBC Connection Pool** con los datos del usuario `desafio_user` y la base de datos `desafio`.
3. Crea un **JDBC Resource** vinculado al pool recién creado.

---

✅ Después de seguir estos pasos, el proyecto estará listo para ejecutarse.
