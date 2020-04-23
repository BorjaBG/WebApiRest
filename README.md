# WebApiRest
Proyecto de repaso que contiene dos proyectos, restApi y web.

![img1](https://github.com/BorjaBG/WebApiRest/blob/master/Screenshot%20from%202020-04-23%2010-49-26.png)

## AppCliente
Aqui podemos encontrar todo lo relacionado con la parte de frontend de la aplicacion.

- Tecnologias:
    - HTML5
    - CSS3
    - JavaScript

Para cambiar el endpoint, las APIs de donde coge la informacion, tendriamos que acceder al main.js y cambiar la URI de las constantes "endpoint" y "endpoint2".



`const endpoint = 'http://localhost:8080/apprest/api/personas/'`



`const endpoint2 = 'http://localhost:8080/apprest/api/cursos/'`

## AppRest
Aqui podemos encontrar todo lo relacionado con la API-REST de la aplicacion

- Tecnologias:
    - Java

Para cambiar la base de datos tendremos que entrar en el archivo "context.xml".



`url="jdbc:mysql://127.0.0.1:3306/alumnos"`



`username="root"`



`password="admin"`

- API-REST:



    `http://localhost:8080/apprest/api/personas/`



    - Alumnos
        - GET: Hacemos la llamada y recibimos todos los alumnos.
            - Codigos:
                - 200: OK.
                - 404: Not Found.
        - POST: Hacemos la llamada y mandamos un alumno (parametros: nombre, avatar y sexo). La ID se la asignara la base de datos automaticamente.
            - Codigos:
                - 201: Created.
        - PUT:  Hacemos la llamada y mandamos un alumno, en este caso con la ID para identificar el alumno que se desea modificar.
            - Codigos:
                - 200: OK.
                - 404: Not Found.
        - DELETE: Hacemos la llamada y mandamos el ID del alumno que se desea eliminar. `http://localhost:8080/apprest/api/personas/1`
            - Codigos:
                - 200: OK.
                - 404: Not Found.

## Tags
Versiones de la aplicacion

- Version 1.0: CRUD de alumnos funcional.
- Actividad 15: Añadida tabla curso y la que relaciona los cursos con los alumnos. Listado de todos los alumnos y cursos.
