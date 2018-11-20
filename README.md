## Acerca

Este proyecto presenta la solución para publicar una API RESTful Almundo Callcenter utilizando Spring Boot, Spring MVC.



La aplicación está construida con:

```
Java version "1.8.0_121"
Apache Maven 3.2.3 
Spring Tool Suite Version: 3.9.2.RELEASE

```



## Configuración de Hilos
Los hilos se pueden configurar en el archivo application.properties. De dicho archivo se pueden configurar las siguientes propiedades:
Número máximo de llamadas procesadas al mismo tiempo(de modo concurrente)
Tiempo en segundos de la duración mínima que puede tener una llamada.
Tiempo en segundos de la duración máxima que puede tener una llamada.

```
callcenter.numThreads = 10
callcenter.minCallTime = 5 
callcenter.maxCallTime = 10
```

## Modelo Básico de UML

```
Operador   <extends>  Empleado 
Supervisor <extends>  Empleado
Director   <extends>  EmplEmpleado 

TipoPrioridad(Integer priority) => OPERADOR(1), SUPERVISOR(2), DIRECTOR(3)

Empleado attributes:
  - Long id
  - String nombre
  - TipoPrioridad tipoPrioridad

Call attributes:
  - Long id
  - String  cliente
  - String  telefono
  - Boolean wait
  - String  mensaje

```

## Clases Principales

```
CallApiController => Controlador que procesa las llamadas, contiene los siguientes verbos:

  - (POST)    /call                 <== Simular atender una llamada. Una Llamada es atendida por un Empleado del Callcenter

EmpleadoApiController => Controlador principal para manejar empleados, contiene los siguientes métodos:

  - (POST)    /empleado/operador    <== Crear un nuevo  Empleado de tipo Operador en el Sistema (alta prioridad - 1)
  - (POST)    /empleado/supervisor  <== Crear un nuevo  Empleado de tipo Supervisor en el Sistema (alta prioridad - 2)
  - (POST)    /empleado/director    <== Crear un nuevo Empleado de tipo Director en el Sistema (alta prioridad - 3)
  - (DELETE)  /empleado/empleados   <== Limpia el Pool de empleados que están atendiendo las llamadas
  - (GET)     /empleado/login       <== Autenticacion del empleado en el sistema
  - (GET)     /empleado/logout      <== Cierra la sesión del empleado actualmente autenticado


EmpleadoServicio => Manejador de la cola PriorityBlockingQueue (Empleado implementa la interface Comparable)

Dispatcher      => Procesar las llamadas con un ConcurrentTaskExecutor <= ThreadPoolTaskExecutor 

ThreadPoolTaskExecutor => callcenter.numThreads = 10 (Max Pool Size and Core Size)

CallCenterApplication     =>  Configuración de la Aplicación Boot

CallControllerTest  =>  Prueba el correcto funcionamiento del controlador CallApiController.
EmpleadoControllerTest  =>  Prueba el correcto funcionamiento del controlador EmpleadoApiController.

```

## Ejemplos de la API Rest

- Insertar un nuevo empleado de Tipo Operador

```
POST /almundo/v1/callcenter/empleado/operador
Accept: application/json
Content-Type: application/json

{"name":"OPERADOR-1"}

RESPONSE: HTTP 201 (Created)
```

- Insertar un nuevo empleado de Tipo Supervisor

```
POST /almundo/v1/callcenter/empleado/supervisor
Accept: application/json
Content-Type: application/json

{"name":"SUPERVISOR-1"}

RESPONSE: HTTP 201 (Created)
```

- Insertar un nuevo empleado de Tipo Director

```
POST /almundo/v1/callcenter/empleado/director
Accept: application/json
Content-Type: application/json

{"name":"DIRECTOR-1"}

RESPONSE: HTTP 201 (Created)
```

- Simular una nueva Llamada(Call) de un cliente

```
POST /almundo/v1/callcenter/call
Content-Type: application/x-www-form-urlencoded

message=LLamando a Almundo CallCenter

RESPONSE: HTTP 200 (OK)
```

- Limpia el Pool de empleados que están atendiendo las llamadas. Esto solo lo puede hacer un usuario que ha iniciado sesión

```
DELETE /almundo/v1/callcenter/empleados

RESPONSE: HTTP 200 (OK)
```


## Build
Los pasos son para construir y desplegar la API son los siguientes:
1. Instalar Java 8 y Maven.

2. Clonar la rama desde github  

```
git clone https://github.com/jeovalo/almundo-callcenter.git
```

3. Empaquetar la Aplicación

```
mvn clean package
```

4. Ejecutar La Aplicación

```
java -jar target/callcenter-1.0.0.jar
``


## Run

Para ejecutar la aplicación, run:

```
mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"

```

### Verificar la documentación con Swagger

Para ver la documentación de la Api Swagger, ejecute la aplicación y visite:

```
http://localhost:8080/almundo/v1/callcenter/swagger-ui.html
```

## Testing
Para ejecutar los test de la Aplicación CallCenter de Almundo se puede usar maven o a traves de un terminal utilizando el comando curl

###
Se pueden ejecutar los Test de la aplicación de 


```
mvn test

mvn test -Dlogging.level.com.almundo=DEBUG  (para activar el modo DEBUG)
```


## LICENSE

Este código es liberado bajo licencia Apache License 2.0. Ver la LICENCIA para más detalles.
