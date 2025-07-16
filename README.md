Intrucciones para ejecutar el proyecto:

Se debe realizar el pull a la rama dev, la cual es la rama estable del proyecto, en ella se debe crear el archivo application-dev.properties y en ella incluír el dataSource para permitir la conexión a la base de datos, cabe aclarar que el proyecto se encuentra configurado con las dependencias para usar la base de datos MySQL.

Se debe agregar una propiedad dentro del application-dev.properties, con el siguiente nombre: security.api-key, esta se reutilizará para enviarse como header en cada solicitud que se realice al microservicio de productos.
Se debe agregar otra propiedad con nombre: product.api.base la cual contendrá la ruta del microservicio de productos. Si el proyecto se corre de manera local, se debe agregar la ruta local con el endpoint correspondiente.
Por último si se tiene otro servicio corriendo con el puerto 8080, se debe crear otra propiedad con nombre: server.port y proceder a cambiarlo para que se pueda levantar el contexto de la aplicación sin problemas.
