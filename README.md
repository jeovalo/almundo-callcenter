## Acerca

Este proyecto presenta la solución para publicar una API RESTful Almundo Callcenter utilizando Spring Boot, Spring MVC.



La aplicación está construida con:

```
Java version 1.8.0_60-b27
Apache Maven 3.2.3 
Spring Tool Suite Version: 3.9.2.RELEASE

```
## Descripcin de la Solución

Para implementar la solución se utilizó PriorityBlockingQueue para almacenar los empleados que son añadidos al centro de llamadas. Ésta es una cola con bloqueo y con prioridades, no una cola de tipo "primero en entrar/primero en salir". Los elementos de dicha cola(Empleados en este caso) se van eliminando por orden de prioridades, seguiendo el siguiente orden: **OPERADOR > SUPERVISOR > DIRECTOR**. 
PriorityBlockingQueue tiene una capacidad ilimitada, por esta razón(nunca se llena) al añadir un empleado a la cola nunca se bloquea(**put**). Caso contrario ocurre con la operación recuperar un empleado(**take**), la cual se bloquea si la cola está vacía.

### Extras/Plus
- Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.
  - Se definió un atributo en la Call de tipo Boolean llamado **wait**, la cual indica si el cliente que llama
    está dispuesto a esperar que se desocupe un agente del centro de llamada(empleado).
  - Cuando no hay ningún empleado libre, si **wait** es **true** el sistema espera que se desocupe un empleado para que
    atienda la llamada. Si **wait** es **false** el sistema **rechaza** inmediatamente la llamada.
    
- Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.
  - Se definió un atributo en la Call de tipo Boolean llamado **wait**, la cual indica si el cliente que llama
    está dispuesto a esperar que se desocupe un agente del centro de llamada(empleado).
  - Se utilizó una cola **PriorityBlockingQueue**, la cual permite que si entran más de 10 llamadas concurrentes, 
    las Calls(llamadas) que tengas la propiedad **wait** en **true** esperarán en ser atendidas(**se bloquea el hilo**)
    y las que tengan el valor de **false** serán rechazadas inmediatamente.

- Agregar los tests unitarios que se crean convenientes.
  - Se agregan test para las funcionalidades básicas.
  - Se agregan test para las funcionalidades que implican concurrencia y competencia de hilos.

- Agregar documentación de código
Para documentar la API RESTful Almundo Callcenter se utilizó un estandar el la especificación y documentación de APIS llamado [Swagger](https://swagger.io/).  

## Configuración de Hilos
Los hilos se pueden configurar en el archivo application.properties. En dicho archivo se pueden configurar las siguientes propiedades:
- Número máximo de llamadas procesadas al mismo tiempo(de modo concurrente)
- Tiempo en segundos de la duración mínima que puede tener una llamada.
- Tiempo en segundos de la duración máxima que puede tener una llamada.

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

Dispatcher       => Procesar las llamadas con un ConcurrentTaskExecutor <= ThreadPoolTaskExecutor 

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

{  
  "id": 1,
  "nombre": "Juan Pérez",
  "sala":"sala de operadores",
  "ocupado":false,
  "tipoPrioridad":"1"
}

RESPONSE: 
{
    "codigo": 4,
    "tipo": "ok",
    "mensaje": "Se ha agregado un Agente de tipo Operador al Centro de llamadas"
}
HTTP 201 (Created)

```

- Insertar un nuevo empleado de Tipo Supervisor

```
POST /almundo/v1/callcenter/empleado/supervisor
Accept: application/json
Content-Type: application/json

{  
  "id": 2,
  "nombre": "María Polo",
  "sala":"sala de supervisores",
  "ocupado":false,
  "tipoPrioridad":"2"
}

RESPONSE: 
{
    "codigo": 4,
    "tipo": "ok",
    "mensaje": "Se ha agregado un Agente de tipo Supervisor al Centro de llamadas"
}
HTTP 201 (Created)

```

- Insertar un nuevo empleado de Tipo Director

```
POST /almundo/v1/callcenter/empleado/director
Accept: application/json
Content-Type: application/json

{  
  "id": 3,
  "nombre": "Jeovany López Director",
  "sala":"sala de directores",
  "ocupado":false,
  "tipoPrioridad":"3"
}

RESPONSE: 
{
    "codigo": 4,
    "tipo": "ok",
    "mensaje": "Se ha agregado un Agente de tipo Director al Centro de llamadas"
}
HTTP 201 (Created)

```

- Simular una nueva Llamada(Call) de un cliente

```
POST /almundo/v1/callcenter/call
Content-Type: application/json

{  
  "id": 123,
  "cliente": "Jeovany Lopez",
  "mensaje":"Reserva Billete de Avión",
  "telefono":"555555",
  "wait":true
}

RESPONSE: 
{
    "codigo": 4,
    "tipo": "ok",
    "mensaje": "Se ha agregado la Call Correctamente, está pendiente por atenderse"
}
HTTP 200 (OK)

```

- Limpia el Pool de empleados que están atendiendo las llamadas. Esto solo lo puede hacer un usuario que ha iniciado sesión

```
DELETE /almundo/v1/callcenter/empleado/empleados

RESPONSE: 
{
    "codigo": 4,
    "tipo": "ok",
    "mensaje": "Se han Eliminados todos los empleados que estaban atendiendo las llamadas"
}
HTTP 200 (OK)

```


## Build
Los pasos son para construir y desplegar la Aplicación Almundo Callcenter son los siguientes:
- Instalar Java 8 y Maven.

- Clonar la rama desde github  

```
git clone https://github.com/jeovalo/almundo-callcenter.git

```

- Empaquetar la Aplicación

```
mvn clean package

```

- Ejecutar La Aplicación

```
java -jar target/callcenter-1.0.0.jar

```


## Run

Para ejecutar la aplicación, ejecutar el siguiente comando maven:

```
mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"

```

## Documentación de la Api Almundo Callcenter
Para ver la [documentación](http://localhost:8080/almundo/v1/callcenter/swagger-ui.html) de la Api con Swagger, ejecute la aplicación y visite una de las siguientes urls locales:


```
http://localhost:8080/almundo/v1/callcenter
```

```
http://localhost:8080/almundo/v1/callcenter/swagger-ui.html
```

En la [documentación](http://localhost:8080/almundo/v1/callcenter/swagger-ui.html) de la api se puede ver en formato html entre otras cosas:
- Todos los servicios(verbos) de la API RESTful Almundo Callcenter.
- Todos los formatos (json/xml) que soporta la Api.
- Listado de todas las operaciones que forman cada servicio Rest.
- Ejemplos de request y response de la Api.
- Códigos de HTTP Status Code para cada recurso.

## Testing
Para ejecutar los test de la Aplicación CallCenter de Almundo se puede usar maven o a través de un terminal utilizando el comando curl

### Ejecutar los Test con Maven
Se pueden ejecutar los Test de la aplicación de forma automática con los siguientes comandos de maven

- Ejecutar todos los test
```
mvn test

```
- Ejecutar todos los test en modo DEBUG habilitado
```
mvn test -Dlogging.level.com.almundo=DEBUG  (para activar el modo DEBUG)

```


### Ejecutar los Test de forma manual
Se pueden ejecutar los Test de la aplicación de forma manual utilizando una terminal y el comando curl.
- Ejecute la aplicación con el siguiente comando maven:

```
mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"

```

-  Copie y pegue en el terminal los siguientes comandos dependiendo la funcionalidad que se quiera probar

#### Crear un nuevo empleado de Tipo Operador
Crear un agente del Callcenter de tipo Operador:
- Petición:
```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/almundo/v1/callcenter/empleado/operador -d '{  
  "id": 1,
  "nombre": "Juan Pérez",
  "sala":"sala de operadores",
  "ocupado":false,
  "tipoPrioridad":"1"
}' 
```
- Respuesta:
```
{"codigo":4,"tipo":"ok","mensaje":"Se ha agregado un Agente de tipo Operador al Centro de llamadas"}
```

#### Crear un nuevo empleado de Tipo Supervisor
Crear un agente del Callcenter de tipo Supervisor:
- Petición:
```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/almundo/v1/callcenter/empleado/supervisor -d '{  
  "id": 2,
  "nombre": "María Polo",
  "sala":"sala de supervisores",
  "ocupado":false,
  "tipoPrioridad":"2"
}' 
```
- Respuesta:
```
{"codigo":4,"tipo":"ok","mensaje":"Se ha agregado un Agente de tipo Supervisor al Centro de llamadas"}
```

#### Crear un nuevo empleado de Tipo Director
Crear un agente del Callcenter de tipo Director:
- Petición:
```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/almundo/v1/callcenter/empleado/director -d '{  
  "id": 3,
  "nombre": "Jeovany López Director",
  "sala":"sala de directores",
  "ocupado":false,
  "tipoPrioridad":"3"
}' 
```
- Respuesta:
```
{"codigo":4,"tipo":"ok","mensaje":"Se ha agregado un Agente de tipo Director al Centro de llamadas"}
```

#### Crear una nueva Llamada(Call)
Simular que entra una llamada de un cliente. La llamada es atendida por un empleado del Callcenter:
- Petición:
```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/almundo/v1/callcenter/call -d '{  
  "id": 123,
  "cliente": "Jeovany Lopez",
  "mensaje":"Reserva Billete de Avión",
  "telefono":"555555",
  "wait":true
}' 
```
- Respuesta:
```
{"codigo":4,"tipo":"ok","mensaje":"Se ha agregado la Call Correctamente, está pendiente por atenderse"}
```

#### Eliminar todos los empleados del Callcenter
Limpia el Pool de empleados que están atendiendo las llamadas:
- Petición:
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/almundo/v1/callcenter/empleado/empleados

```
- Respuesta:
```
{"codigo":4,"tipo":"ok","mensaje":"Se han Eliminados todos los empleados que estaban atendiendo las llamadas"}
```
## LICENSE

Este código es liberado bajo licencia Apache License 2.0. Ver la LICENCIA para más detalles.
