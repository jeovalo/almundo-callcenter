## Acerca

Este proyecto presenta la solución para publicar una API RESTful Almundo Callcenter utilizando Spring Boot, Spring MVC.



La aplicación está construida con:

```
Java version "1.8.0_121"
Apache Maven 3.2.3 
Spring Tool Suite Version: 3.9.2.RELEASE

```



## Configuración de Hilos
Los hilos se pueden configurar en un archivo .properties application.properties

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
  - String name
  - TipoPrioridad tipoPrioridad
  
```

## Clases Principales

```
CallApiController => Main App Controller with the following methods:

  - (POST)    /empleado/operador    <== Crear un nuevo Operador en Sistema (alta prioridad - 1)
  - (POST)    /empleado/supervisor  <== Crear un nuevo Supervisor en Sistema (alta prioridad - 2)
  - (POST)    /empleado/director    <== Crear un nuevo Director en Sistema (alta prioridad - 3)
  - (POST)    /call                 <== Simular atender una llamada
  - (DELETE)  /empleado/empleados   <== Eliminar todos los empleados del sistema
 

EmpleadoServicio => PriorityBlockingQueue Manager (Empleado implementa la interface Comparable)

Dispatcher      => Procesar las llamadas con un ConcurrentTaskExecutor <= ThreadPoolTaskExecutor 

ThreadPoolTaskExecutor => callcenter.numThreads = 10 (Max Pool Size and Core Size)

CallCenterApplication     =>  Configuración de la Aplicación Boot

CallControllerTest  =>  Prueba el correcto funcionamiento de la funcionalidad requerida.
EmpleadoControllerTest  =>  Prueba el correcto funcionamiento de la funcionalidad requerida.

```

## API Rest Examples

- Insert a new Operador

```
POST /almundo/v1/callcenter/empleado/operador
Accept: application/json
Content-Type: application/json

{"name":"OPERADOR-1"}

RESPONSE: HTTP 201 (Created)
```

- Insert a new Supervisor

```
POST /almundo/v1/callcenter/empleado/supervisor
Accept: application/json
Content-Type: application/json

{"name":"SUPERVISOR-1"}

RESPONSE: HTTP 201 (Created)
```

- Insert a new Director

```
POST /almundo/v1/callcenter/empleado/director
Accept: application/json
Content-Type: application/json

{"name":"DIRECTOR-1"}

RESPONSE: HTTP 201 (Created)
```

- Simulate a Call 

```
POST /almundo/v1/callcenter/call
Content-Type: application/x-www-form-urlencoded

message=LLamando a Almundo CallCenter

RESPONSE: HTTP 200 (OK)
```

- Delete all Empleados

```
DELETE /almundo/v1/callcenter/empleados

RESPONSE: HTTP 200 (OK)
```


## Build
Los pasos son para desplegar la API son los siguientes:
1. Instalar Java 8 y Maven.
2. Clonar la rama desde bitbucket  
```
git clone https://github.com/jeovalo/almundo-callcenter.git
```

3. Ejecutar Maven Install

```
mvn package && java -jar target/spring-boot-call-center-0.5.0.jar
```

Para contstruir callcenter-almundo como un archivo war, ejecutar:

```
mvn clean package;

java -jar target/spring-boot-call-center-0.5.0.war (To run spring-boot App)
```

## Run

Para ejecutar la aplicación, run:

```
mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"

```

### Verificar la documentación con Swagger

Para ver la documentación de la Api Swagger, ejecute la aplicación y visite:

```
http://localhost:8080/swagger-ui.html
```

## Testing

Para ejecutar los test de la Aplicación CallCenter de Almundo 

```
mvn test

mvn test -Dlogging.level.com.almundo=DEBUG  (para activar el modo DEBUG)
```


## LICENSE

Este código es liberado bajo licencia Apache License 2.0. Ver la LICENCIA para más detalles.
