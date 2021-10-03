# regdevac

Esta aplicación fué generada utilizando JHipster 7.2.0, más documentación y ayuda sobre JHipster se puede encontrar en [https://www.jhipster.tech/documentation-archive/v7.2.0](https://www.jhipster.tech/documentation-archive/v7.2.0).

## Desarrollo

Antes de ejecutar el proyecto debes tener instalado las siguientes dependencias:

1. [Node.js][]: Es utilizado para correr un servidor web de desarrollo y compilar código Typescript en el cual está soportado el desarrollo React.

Después de la instalación de Node, debe ejecutarse el comando siguiente para que instale las herramientas de desarrollo.

```
npm install
```

2. [Java 11][]: Es utilizado para compilar el backend, crear el Uber jar y correr la aplicación

3. [Maven][]: Es utilizado para gestionar las dependencias del proyecto

4. [Postgresql][]: Es utilizado como sistema de gestión de Bases de Datos. En el mismo Postgres, el aplicativo espera conectarse a través del usuario `rdv`
con password `rdv` a una base de datos con nombre `rdv`

Una vez que se tengan los requerimientos, clonar este proyecto y desde la carpeta raíz del proyecto ejecutar el comando `./mvnw`
el mismo descargará las dependencias e iniciará la aplicación. Una vez iniciada la aplicación, abrir un navegador y acceder a http://localhost:8080
Se puede entrar con el usuario `admin` y password `admin`
En el menú superior se podrá acceder a las diferentes opciones de la aplicación. 

Adicionalmente en http://localhost:9000/admin/docs se puede acceder al API REST.

