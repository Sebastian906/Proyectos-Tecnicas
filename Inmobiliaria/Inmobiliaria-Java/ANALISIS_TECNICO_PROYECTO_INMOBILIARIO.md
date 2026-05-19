# PROYECTO ACADÉMICO: SISTEMA DE GESTIÓN Y RESERVA DE PROPIEDADES INMOBILIARIAS
## Análisis Técnico, Arquitectónico y Académico

---

## ÍNDICE
1. [Contexto General y Justificación](#contexto-general)
2. [Análisis del Dominio Inmobiliario](#análisis-dominio)
3. [Arquitectura MVC Detallada](#arquitectura-mvc)
4. [Mapeo de Técnicas de Programación](#mapeo-técnicas)
5. [Estructura de Paquetes](#estructura-paquetes)
6. [Especificación de Entidades](#especificación-entidades)
7. [Requisitos Funcionales](#requisitos-funcionales)
8. [Requisitos No Funcionales](#requisitos-no-funcionales)
9. [Casos de Uso](#casos-de-uso)
10. [Flujo del Sistema](#flujo-del-sistema)

---

## CONTEXTO GENERAL Y JUSTIFICACIÓN {#contexto-general}

### 1.1 ¿Qué es una Inmobiliaria? (Contexto Real)

Una inmobiliaria es una empresa que actúa como intermediaria en operaciones de compra, venta o alquiler de propiedades inmuebles. En el contexto académico simplificado de este proyecto:

**Actores principales:**
- **Propietarios**: Registran sus propiedades en el sistema
- **Clientes/Usuarios**: Buscan, visualizan y reservan propiedades
- **Sistema**: Gestiona el inventario, búsquedas, reservas e historial

**Operaciones clave:**
1. **Gestión de inventario**: Registrar, actualizar y eliminar propiedades
2. **Búsqueda y filtrado**: Encontrar propiedades según criterios específicos
3. **Visualización de detalles**: Consultar características completas de una propiedad
4. **Sistema de reservas**: Permitir que usuarios reserven propiedades disponibles
5. **Historial y trazabilidad**: Mantener registro de acciones realizadas

### 1.2 ¿Por qué este contexto es académicamente válido?

Este dominio permite demostrar técnicas de programación de manera **natural y práctica**:

| Técnica | Justificación en Inmobiliaria |
|---------|-------------------------------|
| **Listas dinámicas** | Gestionar cantidad variable de propiedades sin conocer el tamaño previo |
| **Búsqueda y ordenamiento** | Presentar propiedades filtradas y ordenadas por precio, tamaño, ubicación |
| **Recursión** | Navegar menús jerárquicos, procesamiento recursivo de filtros |
| **Pilas (LIFO)** | Historial de búsquedas recientes, deshacer última acción |
| **Colas (FIFO)** | Procesar solicitudes de reserva en orden de llegada |
| **Archivos** | Persistencia de propiedades y reservas entre ejecuciones |
| **Operaciones con cadenas** | Búsquedas por nombre, validación de datos textuales |
| **Backtracking** | Encontrar combinaciones de propiedades que cumplan múltiples criterios |
| **Fuerza bruta** | Comparar todas las propiedades contra criterios de búsqueda |

---

## ANÁLISIS DEL DOMINIO INMOBILIARIO {#análisis-dominio}

### 2.1 Entidades del Sistema

El sistema operará con **5 entidades principales**:

#### 2.1.1 Casa (Property)
**Responsabilidad**: Representar una propiedad inmobiliaria individual

**Atributos clave:**
- `id`: Identificador único de la propiedad
- `direccion`: Ubicación física
- `ciudad`: Localidad
- `precio`: Valor mensual o de venta
- `area`: Superficie en metros cuadrados
- `dormitorios`: Cantidad de habitaciones
- `banos`: Cantidad de baños
- `disponible`: Estado booleano (disponible/reservada)
- `descripcion`: Detalles adicionales
- `propietario`: Nombre del dueño

**Justificación académica:**
- Permite practicar encapsulación y propiedades de objetos
- Base para operaciones de búsqueda y ordenamiento

#### 2.1.2 Usuario (User)
**Responsabilidad**: Representar un cliente que interactúa con el sistema

**Atributos clave:**
- `id`: Identificador único
- `nombre`: Nombre completo
- `email`: Correo electrónico
- `telefono`: Contacto telefónico
- `presupuesto`: Capacidad de pago máxima
- `preferencias`: Lista de características buscadas
- `activo`: Si el usuario está registrado

**Justificación académica:**
- Muestra agregación de objetos (usuario tiene lista de preferencias)
- Base para validaciones y filtros personalizados

#### 2.1.3 Reserva (Reservation)
**Responsabilidad**: Registrar la intención de un usuario de alquilar una propiedad

**Atributos clave:**
- `id`: Identificador único
- `idUsuario`: Referencia al usuario
- `idCasa`: Referencia a la propiedad
- `fechaReserva`: Cuándo se realizó
- `fechaInicio`: Inicio del contrato
- `duracion`: Duración de la reserva (meses)
- `estado`: (Pendiente, Confirmada, Cancelada)
- `observaciones`: Notas adicionales

**Justificación académica:**
- Demuestra relaciones entre entidades (Usuario ↔ Reserva ↔ Casa)
- Base para colas de procesamiento

#### 2.1.4 Historial (History)
**Responsabilidad**: Registrar acciones del usuario para auditoría y análisis

**Atributos clave:**
- `id`: Identificador
- `idUsuario`: Usuario que ejecutó la acción
- `tipo`: (BÚSQUEDA, VISUALIZACIÓN, RESERVA, etc.)
- `descripcion`: Detalle de la acción
- `fecha`: Cuándo ocurrió
- `resultado`: Resultado de la acción

**Justificación académica:**
- Base para implementar pilas (últimas búsquedas)
- Demuestra persistencia en archivos

#### 2.1.5 SistemaInmobiliario (RealEstateSystem)
**Responsabilidad**: Orquestador general del sistema (podría considerarse como entidad de control)

**Responsabilidades:**
- Mantener colecciones de casas, usuarios, reservas, historial
- Coordinar operaciones entre entidades
- Ejecutar algoritmos de búsqueda y ordenamiento
- Gestionar persistencia en archivos

---

## ARQUITECTURA MVC DETALLADA {#arquitectura-mvc}

### 3.1 Separación de Responsabilidades

```
┌─────────────────────────────────────────────────────┐
│             VISTA (View - Consola)                  │
│  - Menús interactivos                               │
│  - Captura de entrada del usuario                   │
│  - Presentación de resultados                       │
│  - Validación de formato de entrada                 │
└─────────────┬───────────────────────────┬───────────┘
              │                           │
     ┌────────▼──────────┐       ┌────────▼──────────┐
     │  Entrada usuario  │       │ Salida formateada│
     └─────────┬─────────┘       └────────▲──────────┘
               │                         │
               │    ┌──────────────┐     │
               └───►│  Controlador  ├────┘
                    │  (Controller) │
                    └──┬───────┬────┘
                       │       │
        ┌──────────────┘       └──────────────┐
        │                                     │
   ┌────▼──────────┐               ┌─────────▼─────┐
   │  Lógica de    │               │ Acceso a      │
   │  negocio      │               │ Almacenamiento│
   │  Algoritmos   │               │ (Archivos)    │
   └────┬──────────┘               └─────────┬─────┘
        │                                    │
        └────────┬──────────────┬────────────┘
                 │              │
         ┌───────▼────┐  ┌──────▼────────┐
         │  MODELO    │  │ Persistencia  │
         │  (Model)   │  │  (Archivos)   │
         │ - Casa     │  │ - Lectura     │
         │ - Usuario  │  │ - Escritura   │
         │ - Reserva  │  │ - CSV/TXT     │
         │ - Historial│  │               │
         └────────────┘  └───────────────┘
```

### 3.2 Capas y sus Responsabilidades

#### 3.2.1 MODELO (Model)

**Ubicación**: `com.inmobiliaria.model`

**Clases principales:**

| Clase | Responsabilidad |
|-------|-----------------|
| `Casa.java` | Encapsular atributos y comportamiento de una propiedad |
| `Usuario.java` | Encapsular datos y comportamiento del cliente |
| `Reserva.java` | Encapsular información de reservas |
| `Historial.java` | Registrar acciones del sistema |
| `SistemaInmobiliario.java` | Orquestación central de datos |

**Características del Modelo:**
- **Encapsulación total**: Atributos privados, acceso mediante getters/setters
- **Validación en construcción**: Verificar estados válidos al crear objetos
- **Métodos de utilidad**: `toString()`, `equals()`, `compareTo()` para comparaciones
- **Sin lógica de presentación**: Completamente independiente de la vista
- **Sin lógica de entrada/salida**: No accede archivos directamente

#### 3.2.2 VISTA (View)

**Ubicación**: `com.inmobiliaria.view`

**Clases principales:**

| Clase | Responsabilidad |
|-------|-----------------|
| `MenuPrincipal.java` | Menú raíz del sistema |
| `MenuBusqueda.java` | Interfaz de búsqueda de propiedades |
| `MenuReserva.java` | Interfaz para realizar reservas |
| `MenuVisualizacion.java` | Presentación de detalles de propiedades |
| `MenuHistorial.java` | Visualización de historial |
| `ConsolaHelper.java` | Utilidades de entrada/salida por consola |

**Características de la Vista:**
- **Métodos para captura de datos**: `pedirString()`, `pedirEntero()`, `pedirOpcion()`
- **Métodos para presentación**: `mostrarPropiedad()`, `mostrarMenu()`, `mostrarResultados()`
- **Validación de formato**: Verificar que entrada sea del tipo esperado
- **Formateo visual**: Espacios, líneas separadoras, alineación
- **Mensajes de usuario**: Mensajes claros y descriptivos
- **Sin acceso a datos directamente**: No accede listas del modelo

#### 3.2.3 CONTROLADOR (Controller)

**Ubicación**: `com.inmobiliaria.controller`

**Clases principales:**

| Clase | Responsabilidad |
|-------|-----------------|
| `ControladorPrincipal.java` | Orquesta toda la lógica y flujo del sistema |
| `ControladorBusqueda.java` | Ejecuta búsquedas y ordenamientos |
| `ControladorReserva.java` | Gestiona el proceso de reserva |
| `ControladorPersistencia.java` | Lectura/escritura de archivos |
| `ControladorHistorial.java` | Gestión del historial de acciones |

**Características del Controlador:**
- **Mediador entre vista y modelo**: Recibe datos de vista, los procesa con modelo, devuelve resultados
- **Implementación de algoritmos**: Búsqueda lineal, binaria, ordenamientos
- **Validaciones de negocio**: Verificar disponibilidad, presupuesto, etc.
- **Manejo de pilas y colas**: Implementación de estas estructuras
- **Recursión**: Implementación de funciones recursivas según requisitos
- **Persistencia**: Coordina lectura/escritura de archivos

### 3.3 Flujo de Datos en MVC

```
USUARIO INGRESA DATOS (Teclado)
         │
         ▼
    ┌─────────────────────────┐
    │  VISTA (View)           │
    │ - Captura entrada       │
    │ - Valida formato        │
    │ - Crea comando          │
    └────────┬────────────────┘
             │
    ┌────────▼────────────────┐
    │ CONTROLADOR (Controller)│
    │ - Procesa comando       │
    │ - Aplica lógica         │
    │ - Llama al modelo       │
    │ - Ejecuta algoritmos    │
    └────────┬────────────────┘
             │
    ┌────────▼────────────────┐
    │ MODELO (Model)          │
    │ - Accede estructuras    │
    │ - Modifica datos        │
    │ - Retorna resultado     │
    └────────┬────────────────┘
             │
    ┌────────▼────────────────┐
    │ CONTROLADOR (retorna)   │
    │ - Formatea resultado    │
    └────────┬────────────────┘
             │
    ┌────────▼────────────────┐
    │ VISTA (View)            │
    │ - Presenta resultado    │
    │ - Muestra al usuario    │
    └────────┬────────────────┘
             │
    USUARIO VE RESULTADO (Pantalla)
```

---

## MAPEO DE TÉCNICAS DE PROGRAMACIÓN {#mapeo-técnicas}

### 4.1 UNIDAD 1: GENERALIDADES

#### 4.1.1 TÉCNICAS DE ADQUISICIÓN DE DATOS

**A) Lectura y Escritura por Consola**

| Técnica | Implementación | Ubicación | Justificación |
|---------|---|---|---|
| **Menús interactivos** | Estructura switch/case que presenta opciones al usuario | `MenuPrincipal.java`, `MenuBusqueda.java` | El usuario navega por opciones usando números. Cada menú tiene un loop que persiste hasta que elige salir |
| **Captura de datos** | Métodos como `scanner.nextLine()`, `scanner.nextInt()` | `ConsolaHelper.java` | Fundamental para obtener búsquedas, filtros, datos de reserva del usuario |
| **Navegación** | Sistema jerárquico de menús con submúltiplos | `ControladorPrincipal.java` | Usuario puede navegar hacia adelante (seleccionar opción) y hacia atrás (volver) |
| **Validación de entradas** | Verificar tipo de dato, rango válido, valores permitidos | `ConsolaHelper.java` | Evitar excepciones, garantizar datos consistentes |

**Ejemplos de implementación:**
- Usuario selecciona "Buscar propiedad" → Sistema solicita ciudad → Valida que no esté vacío → Busca coincidencias
- Usuario intenta reservar → Sistema valida presupuesto → Verifica disponibilidad → Crea reserva

---

**B) Lectura y Escritura mediante Archivos**

| Operación | Método | Ubicación | Justificación |
|-----------|--------|-----------|---|
| **Guardar propiedades** | `guardarCasasEnArchivo()` | `ControladorPersistencia.java` | Al terminar sesión, todas las propiedades se escriben en archivo CSV. En siguiente ejecución se cargan automáticamente |
| **Guardar reservas** | `guardarReservasEnArchivo()` | `ControladorPersistencia.java` | Cada reserva se registra permanentemente |
| **Cargar datos** | `cargarCasasDesdeArchivo()` | `ControladorPersistencia.java` | Al iniciar, el sistema lee archivos y reconstruye estado anterior |
| **Formato de archivo** | CSV (valores separados por comas) | `datos/` | Formato simple, legible, sin dependencias de BD |

**Estructura de archivos:**

```
/datos/
├── casas.csv
│   Formato: id,direccion,ciudad,precio,area,dormitorios,banos,disponible,descripcion,propietario
│   Ejemplo: 1,Cra 10 #25-45,Bogota,1500000,80,2,1,true,Apto cerca estación,Juan
│
├── usuarios.csv
│   Formato: id,nombre,email,telefono,presupuesto,activo
│
├── reservas.csv
│   Formato: id,idUsuario,idCasa,fechaReserva,fechaInicio,duracion,estado,observaciones
│
└── historial.csv
    Formato: id,idUsuario,tipo,descripcion,fecha,resultado
```

**Justificación de persistencia en archivos:**
- Alternativa académicamente válida a bases de datos
- Demuestra I/O en Java
- Facilita pruebas (archivos son editables manualmente)
- Mantiene datos entre ejecuciones

---

**C) Operaciones con Cadenas (String)**

| Operación | Aplicación en Inmobiliaria | Ubicación | Técnica |
|-----------|---|---|---|
| **Búsqueda por coincidencia** | Usuario busca "apartamento" en descripción | `ControladorBusqueda.java` | `contains()`, `equalsIgnoreCase()` |
| **Comparación de cadenas** | Validar que email sea válido (contiene @) | `ConsolaHelper.java` | `indexOf()`, expresiones regulares |
| **Formateo** | Mostrar precio con formato de moneda | `ConsolaHelper.java` | `String.format()`, `printf()` |
| **Validación de texto** | Verificar que nombre no esté vacío | `ConsolaHelper.java` | `trim()`, `isEmpty()`, `length()` |
| **Conversión y manipulación** | Convertir entrada a mayúsculas para comparación | `ControladorBusqueda.java` | `toUpperCase()`, `toLowerCase()`, `substring()` |

**Ejemplos específicos:**
```
Búsqueda por ciudad: usuario ingresa "BOGOTA" 
  → Sistema convierte a "Bogota" (normalización)
  → Compara con cada propiedad usando equalsIgnoreCase()
  → Retorna coincidencias

Validación de email:
  → Verificar que contiene "@"
  → Verificar que tiene dominio (.com, .co, etc.)
  → Aceptar o rechazar
```

---

**D) Documentación de Código Fuente**

| Elemento | Estándar | Ubicación | Ejemplo |
|----------|----------|-----------|---------|
| **JavaDoc de clases** | `/** ... */` sobre declaración | Toda clase del modelo | Descripción de responsabilidad, parámetros, valores retorno |
| **JavaDoc de métodos** | `/** @param ... @return ... */` | Métodos públicos | Explica qué hace, qué recibe, qué retorna |
| **Comentarios de línea** | `// comentario` | Lógica compleja | Explica "por qué" no "qué" |
| **Comentarios de bloque** | `/* ... */` | Algoritmos | Detalla pasos del algoritmo |
| **Nomenclatura significativa** | CamelCase, nombres descriptivos | Todo código | Variable `usuariosActivos` en lugar de `ua` |

---

**E) Listas (ArrayList)**

| Estructura | Uso en Inmobiliaria | Justificación | Ubicación |
|-----------|---|---|---|
| **Lista de casas** | `ArrayList<Casa> casas` | Cantidad variable de propiedades, operaciones dinámicas | `SistemaInmobiliario.java` |
| **Lista de usuarios** | `ArrayList<Usuario> usuarios` | Gestión de clientes | `SistemaInmobiliario.java` |
| **Lista de reservas** | `ArrayList<Reserva> reservas` | Acumular todas las reservas | `SistemaInmobiliario.java` |
| **Historial de búsquedas** | `ArrayList<String> busquedasRecientes` | Mantener últimas búsquedas (junto con pila) | `ControladorHistorial.java` |
| **Preferencias de usuario** | `ArrayList<String> preferencias` | Usuario selecciona múltiples características | `Usuario.java` |

**Operaciones clave:**
```
Agregar: casas.add(nuevaCasa);
Eliminar: casas.remove(0);
Acceder: casas.get(i);
Iterar: for(Casa c : casas) { ... }
Tamaño: casas.size();
Buscar: casas.contains(casa);
```

---

**F) Pilas (Stack / LIFO)**

| Función | Implementación | Justificación | Ubicación |
|---------|---|---|---|
| **Historial de búsquedas recientes** | `Stack<String> pilaSearches` | Usuario quiere ver últimas 5 búsquedas. Al buscar de nuevo, la nueva va al tope. Al sacar, se obtiene la más reciente | `ControladorHistorial.java` |
| **Deshacer última acción** | `Stack<Accion> pilaAcciones` | Usuario intenta deshacer última operación. Pop retorna a estado anterior | `ControladorPrincipal.java` |
| **Validación de paréntesis** (opcional) | Verificar que búsqueda avanzada tenga paréntesis balanceados | Aplicación de estructura LIFO a validación | `ControladorBusqueda.java` |

**Característica LIFO (Last In, First Out):**
```
Búsquedas del usuario:
  1. "apartamento bogota"
  2. "casa cartagena"
  3. "finca medellin" ← Última búsqueda (tope de pila)

Al presionar "Ver últimas búsquedas":
  Pop() → "finca medellin"
  Pop() → "casa cartagena"
  Pop() → "apartamento bogota"
```

**Operaciones:**
```
Push: pila.push(elemento);        // Agregar al tope
Pop:  elemento = pila.pop();      // Quitar y retornar tope
Peek: elemento = pila.peek();     // Ver tope sin quitar
isEmpty: if(pila.isEmpty()) {...} // Verificar si vacío
```

---

**G) Colas (Queue / FIFO)**

| Función | Implementación | Justificación | Ubicación |
|---------|---|---|---|
| **Cola de solicitudes de reserva** | `Queue<SolicitudReserva> colaReservas` | Múltiples usuarios intentan reservar al mismo tiempo. Se procesan en orden FIFO | `ControladorReserva.java` |
| **Atención de clientes** | `Queue<Atencion> colaAtencion` | Simulación de clientes esperando soporte | `ControladorPrincipal.java` |
| **Procesamiento de peticiones** | Las acciones se encolan antes de ejecutar | Garantiza orden de procesamiento | `ControladorPrincipal.java` |

**Característica FIFO (First In, First Out):**
```
Solicitudes de reserva:
  08:00 - Usuario A solicita casa ID=5  ← Primero en llegar
  08:05 - Usuario B solicita casa ID=7
  08:10 - Usuario C solicita casa ID=5  ← Último en llegar

Procesamiento:
  1. Se procesa Usuario A (FIFO: primero entrado)
  2. Se procesa Usuario B
  3. Se procesa Usuario C
```

**Operaciones:**
```
Enqueue: cola.offer(elemento);      // Agregar al final
Dequeue: elemento = cola.poll();    // Quitar y retornar del inicio
Peek: elemento = cola.peek();       // Ver inicio sin quitar
isEmpty: if(cola.isEmpty()) {...}   // Verificar si vacío
```

---

**H) Resolución de Problemas Aplicados**

Cada funcionalidad del sistema resuelve un problema real:

| Problema Real | Solución Técnica | Técnica Aplicada |
|---|---|---|
| "Tengo presupuesto limitado" | Filtrar por rango de precio | Comparación de números, operadores relacionales |
| "Necesito un apartamento en la ciudad" | Búsqueda por ubicación | Búsqueda lineal, comparación de strings |
| "Quiero ver las más baratas primero" | Ordenar por precio ascendente | Algoritmo de ordenamiento (burbuja, inserción, etc.) |
| "¿Cuáles fueron mis últimas búsquedas?" | Mostrar historial reciente | Pila con Pop() |
| "Procesar todas las reservas" | Encolar y desencolar | Cola con enqueue/dequeue |

---

### 4.2 UNIDAD 2: TÉCNICAS FUNDAMENTALES DE PROGRAMACIÓN

#### 4.2.1 FUERZA BRUTA

**Concepto:** Probar todas las posibilidades sistemáticamente sin optimización.

| Caso de Uso | Implementación | Ubicación | Análisis Académico |
|---|---|---|---|
| **Búsqueda lineal simple** | Recorrer todas las propiedades comparando cada una | `ControladorBusqueda.java` | O(n): Revisa cada casa hasta encontrar coincidencia. Ineficiente pero funcionalmente correcto |
| **Búsqueda con múltiples filtros** | Recorrer lista y aplicar cada filtro secuencialmente | `ControladorBusqueda.java` | O(n*m): Donde n=casas, m=filtros. Compara TODAS las casas contra TODOS los criterios |
| **Comparación de preferencias** | Comparar lista de preferencias del usuario contra cada propiedad | `ControladorBusqueda.java` | O(n*k): Verifica cada casa para cada preferencia del usuario |

**Ejemplo pseudocódigo:**
```
Para cada casa en laLista:
    Si casa.ciudad == búsqueda_ciudad:
        Si casa.precio <= presupuesto_usuario:
            Si casa.dormitorios >= dormitorios_minimos:
                Agregar a resultados

Desventaja: Aunque la casa 2 sea la respuesta, revisamos 100 casas
Ventaja: Funciona en cualquier situación, sin requisitos previos
```

**Justificación Académica:**
- Demuestra por qué existen algoritmos más eficientes
- Base conceptual para entender búsqueda lineal vs binaria
- Enseña que la solución "obvia" no siempre es la mejor

**Documentación en código:**
```
Búsqueda por ciudad - Fuerza Bruta:
  Tiempo: O(n)
  Espacio: O(k) donde k = resultados
  Ventaja: Funciona en listas desordenadas
  Desventaja: Lento en listas muy grandes
```

---

#### 4.2.2 RECURSIÓN

La recursión es implementación de función que se llama a sí misma hasta alcanzar un caso base.

**A) Recursión de Pila (Traditional/Directa)**

| Caso de Uso | Descripción | Ubicación | Ejemplo |
|---|---|---|---|
| **Búsqueda recursiva en lista** | Función que busca propiedad por ID recursivamente | `ControladorBusqueda.java` | `buscarPorIdRecursivo(lista, id, indice)` |
| **Navegación de menús** | Menú que se llama a sí mismo para subcategorías | `MenuPrincipal.java` | Menú → Submenu → Subsubmenu |
| **Validación recursiva** | Validar cada carácter de email recursivamente | `ConsolaHelper.java` | `validarEmailRecursivo(email, indice)` |

**Pseudocódigo - Búsqueda recursiva:**
```
función buscarPorIdRecursivo(lista, id, indice):
    caso base 1: si indice >= lista.size()
        retornar null  (no encontró)
    caso base 2: si lista[indice].id == id
        retornar lista[indice]  (encontró)
    caso recursivo: 
        retornar buscarPorIdRecursivo(lista, id, indice + 1)
```

**Justificación:**
- Demuestra cómo un problema grande se divide en subproblemas idénticos
- Enseña importancia del caso base (evitar stack overflow)
- Muestra relación entre iteración y recursión

**Stack trace (visualización):**
```
buscarPorIdRecursivo(lista, 5, 0)
  → lista[0].id ≠ 5, llamar búsqueda(lista, 5, 1)
    → lista[1].id ≠ 5, llamar búsqueda(lista, 5, 2)
      → lista[2].id == 5, retornar Casa
    ← retorna Casa
  ← retorna Casa
← retorna Casa
```

---

**B) Recursión de Cola (Tail Recursion)**

| Caso de Uso | Descripción | Ubicación |
|---|---|---|
| **Procesamiento optimizado de listas** | Procesar reservas una por una sin acumular stack | `ControladorReserva.java` |
| **Suma acumulativa** | Sumar precios de propiedades recursivamente | `ControladorBusqueda.java` |

**Pseudocódigo - Recursión de cola:**
```
función procesarReservasRecursivo(lista, indice, acumulado):
    caso base: si indice >= lista.size()
        retornar acumulado
    caso recursivo:
        procesarReserva(lista[indice])  // hacer trabajo
        retornar procesarReservasRecursivo(lista, indice+1, acumulado+1)
        // La llamada recursiva es la ÚLTIMA operación
```

**Ventaja sobre recursión tradicional:**
- Compilador puede optimizar (tail call optimization)
- No acumula contextos en stack
- Es casi equivalente a iteración en eficiencia

---

**C) Recursión Cruzada (Mutua)**

| Caso de Uso | Descripción |
|---|---|
| **Validación de estados mutuamente excluyentes** | Función A valida si estado es X, llamando a función B para confirmar. Función B valida si es estado Y, llamando a A |
| **Procesamiento alternado** | Una función procesa elementos pares llamando a función que procesa impares |

**Pseudocódigo:**
```
función validarEstadoDisponible(casa, nivel):
    si casa.disponible == true:
        retornar validarEstadoApto(casa, nivel+1)
    si no:
        retornar false

función validarEstadoApto(casa, nivel):
    si casa.propietarioActivo == true:
        retornar validarEstadoDisponible(casa, nivel+1)
    si no:
        retornar false
```

**Justificación académica:**
- Muestra que recursión no es solo autollamada
- Enseña modularización de validaciones complejas
- Demuestra dependencias circulares controladas

---

#### 4.2.3 BACKTRACKING

**Concepto:** Explorar posibilidades, y al encontrar que no llevan a solución, retroceder y probar otra ruta.

| Caso de Uso | Descripción | Ubicación |
|---|---|---|
| **Encontrar combinaciones de propiedades** | Usuario tiene presupuesto para múltiples casas, encontrar combinaciones que encajen | `ControladorBusqueda.java` |
| **Explorar opciones de reserva** | Intentar reservar en múltiples fechas hasta encontrar disponibilidad | `ControladorReserva.java` |
| **Recomendación inteligente** | Explorar filtros progresivamente, retrocediendo si no hay resultados | `ControladorBusqueda.java` |

**Pseudocódigo - Encontrar combinaciones de casas por presupuesto:**

```
función encontrarCombinaciones(lista_casas, presupuesto, indice, 
                                 combinacion_actual, todas_combinaciones):
    // Caso base 1: Agotamos opciones
    si indice >= lista_casas.size():
        si combinacion_actual.size() > 0:
            todas_combinaciones.agregar(copia(combinacion_actual))
        retornar
    
    // Opción 1: Incluir casa actual
    casa = lista_casas[indice]
    si presupuesto >= casa.precio:
        combinacion_actual.agregar(casa)
        encontrarCombinaciones(lista_casas, 
                              presupuesto - casa.precio,
                              indice + 1,
                              combinacion_actual,
                              todas_combinaciones)
        combinacion_actual.remover(casa)  // BACKTRACK
    
    // Opción 2: No incluir casa actual (explorar siguiente)
    encontrarCombinaciones(lista_casas, 
                          presupuesto,
                          indice + 1,
                          combinacion_actual,
                          todas_combinaciones)
```

**Visualización del árbol de decisión:**

```
Presupuesto: $3M, Casas: [Casa1($1M), Casa2($2M), Casa3($1.5M)]

                           [inicio]
                          /        \
                   Incluir Casa1    No incluir Casa1
                        /                 \
                   $2M restante           $3M restante
                    /      \              /      \
              +Casa2    -Casa2        +Casa2    -Casa2
              ($0M)     ($2M)         ($1M)     ($3M)
              SOLUCIÓN  /    \         /    \     /    \
            [C1+C2]   +C3   -C3    +C3   -C3  +C3   -C3
            BACKTRACK BACKTRACK  [C2+C3]  ✓  [C1+C3] ✓
                                BACKTRACK
```

**Justificación académica:**
- Algoritmo fundamental en problemas de combinatoria
- Enseña cómo explorar espacios de búsqueda sistemáticamente
- Demuestra por qué es importante el "deshacer" (backtrack)

---

#### 4.2.4 RESOLUCIÓN DE PROBLEMAS

Cada técnica resuelve un tipo específico de problema:

| Técnica | Problema que resuelve | Aplicación en Inmobiliaria |
|---|---|---|
| **Fuerza bruta** | "Necesito encontrar ALGO" | Buscar cualquier propiedad que coincida |
| **Recursión pila** | "Buscar en estructura jerárquica" | Navegar menús anidados |
| **Recursión cola** | "Procesar lista sin acumular memoria" | Procesar miles de reservas sin excepciones |
| **Backtracking** | "Encontrar TODAS las combinaciones posibles" | Recomendaciones inteligentes |

---

### 4.3 UNIDAD 3: ALGORITMOS DE BÚSQUEDA Y ORDENAMIENTO

#### 4.3.1 ALGORITMOS DE BÚSQUEDA

**A) Búsqueda Lineal (Linear Search)**

| Aspecto | Descripción |
|--------|-------------|
| **Qué es** | Recorrer lista elemento por elemento hasta encontrar coincidencia |
| **Complejidad** | O(n): En peor caso, revisa todos los elementos |
| **Requisito previo** | Ninguno (funciona en listas desordenadas) |
| **Cuándo usar** | Listas pequeñas, datos desordenados, búsqueda frecuente de distintos valores |

**Aplicaciones en inmobiliaria:**
```
Búsqueda por ciudad:
  for i = 0 to casas.size()-1:
      si casas[i].ciudad == "Bogota":
          resultados.add(casas[i])

Búsqueda por ID:
  for i = 0 to casas.size()-1:
      si casas[i].id == id_buscado:
          retornar casas[i]  // Encontró, termina

Ventajas:
  - Funciona en cualquier lista
  - Implementación simple
  
Desventajas:
  - Lento en listas grandes
  - No aprovecha si lista está ordenada
```

**Análisis paso a paso:**
```
Búscar Casa con ID=15 en lista de 100 casas
Caso mejor: ID está en posición 1 → Iteraciones: 1
Caso peor: ID está en posición 100 → Iteraciones: 100
Caso promedio: ID en algún lado → Iteraciones: 50
Complejidad: O(n) = O(100)
```

---

**B) Búsqueda Binaria (Binary Search)**

| Aspecto | Descripción |
|--------|-------------|
| **Qué es** | Dividir en mitades repetidamente, eliminando mitad donde no puede estar respuesta |
| **Complejidad** | O(log n): Reduce búsqueda a la mitad cada iteración |
| **Requisito previo** | **LISTA DEBE ESTAR ORDENADA** |
| **Cuándo usar** | Listas grandes, búsquedas frecuentes del mismo valor |

**Requisito crítico - Ordenamiento previo:**

```
Para busca binaria por precio, lista DEBE estar:
[Casa1($500k), Casa2($800k), Casa3($1.2M), Casa4($1.8M)]

Si lista NO está ordenada:
[$800k, $1.8M, $500k, $1.2M]  ← NO FUNCIONA BÚSQUEDA BINARIA

Proceso previo requerido:
1. Ordenar lista por precio (usando algún algoritmo de ordenamiento)
2. Luego aplicar búsqueda binaria
```

**Aplicaciones en inmobiliaria:**
```
Búsqueda binaria por precio (lista previamente ordenada):

buscaBinaria(lista_ordenada, precio_objetivo):
    bajo = 0
    alto = lista.size() - 1
    
    mientras bajo <= alto:
        medio = (bajo + alto) / 2
        
        si lista[medio].precio == precio_objetivo:
            retornar lista[medio]  // Encontró
        
        si lista[medio].precio < precio_objetivo:
            bajo = medio + 1  // Buscar en mitad derecha (precios mayores)
        
        si no:
            alto = medio - 1  // Buscar en mitad izquierda (precios menores)
    
    retornar no encontrado
```

**Visualización - Búsqueda de precio $1.2M:**

```
Lista ordenada: [$500k, $800k, $1.2M, $1.8M]
Indices:         [0,     1,      2,     3]

Iteración 1:
  bajo=0, alto=3, medio=1
  lista[1]=$800k < $1.2M → bajo=2

Iteración 2:
  bajo=2, alto=3, medio=2
  lista[2]=$1.2M == $1.2M → ¡ENCONTRÓ!

Iteraciones necesarias: 2 (vs. 3 con búsqueda lineal)
Para 1 millón de elementos:
  Lineal: 500,000 iteraciones promedio
  Binaria: log(1,000,000) ≈ 20 iteraciones
```

**Comparación Búsqueda Lineal vs Binaria:**

| Aspecto | Lineal | Binaria |
|--------|--------|---------|
| **Requisitos** | Ninguno | Lista ordenada |
| **Complejidad** | O(n) | O(log n) |
| **Elementos 1000** | ~500 iteraciones | ~10 iteraciones |
| **Elementos 1M** | ~500k iteraciones | ~20 iteraciones |
| **Cuándo usar** | Listas pequeñas, datos desordenados | Listas grandes, búsquedas frecuentes |

---

#### 4.3.2 ALGORITMOS DE ORDENAMIENTO

Se implementarán **manualmente sin usar `sort()` de Java**.

**A) Ordenamiento Burbuja (Bubble Sort)**

| Aspecto | Descripción |
|--------|-------------|
| **Concepto** | Comparar elementos adyacentes, intercambiar si están en orden incorrecto, repetir hasta estar ordenado |
| **Complejidad** | O(n²): Dos loops anidados |
| **Estabilidad** | Estable: mantiene orden relativo de elementos iguales |
| **Mejor caso** | O(n): Si ya está ordenado |
| **Peor caso** | O(n²): Si está ordenado al revés |

**Pseudocódigo - Ordenar casas por precio (menor a mayor):**

```
función burbujaAscendente(lista):
    n = lista.size()
    
    para i = 0 hasta n-1:
        para j = 0 hasta n-i-2:
            // Comparar casas adyacentes
            si lista[j].precio > lista[j+1].precio:
                // Intercambiar
                temporal = lista[j]
                lista[j] = lista[j+1]
                lista[j+1] = temporal
    
    retornar lista
```

**Visualización paso a paso - Ordenar [3, 1, 4, 1, 5]:**

```
Pasada 1:
  [3, 1, 4, 1, 5] → Comparar 3>1 → [1, 3, 4, 1, 5] ← intercambiar
  [1, 3, 4, 1, 5] → Comparar 3<4 → [1, 3, 4, 1, 5]
  [1, 3, 4, 1, 5] → Comparar 4>1 → [1, 3, 1, 4, 5] ← intercambiar
  [1, 3, 1, 4, 5] → Comparar 4<5 → [1, 3, 1, 4, 5]
  Resultado: [1, 3, 1, 4, 5]  ← Mayor está en su lugar

Pasada 2:
  [1, 3, 1, 4, 5] → Comparar 1<3 → [1, 3, 1, 4, 5]
  [1, 3, 1, 4, 5] → Comparar 3>1 → [1, 1, 3, 4, 5] ← intercambiar
  [1, 1, 3, 4, 5] → Comparar 3<4 → [1, 1, 3, 4, 5]
  Resultado: [1, 1, 3, 4, 5]

Pasada 3:
  [1, 1, 3, 4, 5] → Comparar 1=1 → [1, 1, 3, 4, 5]
  [1, 1, 3, 4, 5] → Comparar 1<3 → [1, 1, 3, 4, 5]
  Resultado: [1, 1, 3, 4, 5]  ← ORDENADA
```

**Aplicación en inmobiliaria:**
```
Ordenar casas por precio (menor a mayor):
  Usuario ve: "Casa $500k, Casa $800k, Casa $1.2M"
  Sistema usa burbuja internamente

Ordenar por tamaño (mayor a menor):
  Modificar comparador para lista[j].area < lista[j+1].area
```

---

**B) Ordenamiento Selección (Selection Sort)**

| Aspecto | Descripción |
|--------|-------------|
| **Concepto** | Encontrar mínimo, colocarlo al inicio, repetir con resto |
| **Complejidad** | O(n²): Siempre, sin importar datos |
| **Estabilidad** | No estable |
| **Ventaja** | Menos intercambios que burbuja |
| **Mejor/Peor caso** | Ambos O(n²) |

**Pseudocódigo - Ordenar por tamaño:**

```
función seleccion(lista):
    n = lista.size()
    
    para i = 0 hasta n-1:
        indice_minimo = i
        
        // Encontrar mínimo en resto de lista
        para j = i+1 hasta n-1:
            si lista[j].area < lista[indice_minimo].area:
                indice_minimo = j
        
        // Intercambiar mínimo a posición i
        si indice_minimo != i:
            temporal = lista[i]
            lista[i] = lista[indice_minimo]
            lista[indice_minimo] = temporal
    
    retornar lista
```

**Visualización - Ordenar [64, 25, 12, 22]:**

```
Pasada 1:
  Encontrar mínimo en [64, 25, 12, 22] → 12 (índice 2)
  Intercambiar posición 0 con 2: [12, 25, 64, 22]

Pasada 2:
  Encontrar mínimo en [25, 64, 22] → 22 (índice 3)
  Intercambiar posición 1 con 3: [12, 22, 64, 25]

Pasada 3:
  Encontrar mínimo en [64, 25] → 25 (índice 3)
  Intercambiar posición 2 con 3: [12, 22, 25, 64]

Resultado: [12, 22, 25, 64] ← ORDENADA
```

---

**C) Ordenamiento Inserción (Insertion Sort)**

| Aspecto | Descripción |
|--------|-------------|
| **Concepto** | Construir arreglo ordenado elemento por elemento, insertando cada nuevo elemento en posición correcta |
| **Complejidad** | O(n²) peor caso, O(n) mejor caso |
| **Estabilidad** | Estable |
| **Adaptabilidad** | Excelente para listas casi ordenadas |
| **Uso real** | Insertar un nuevo cliente en lista ordenada |

**Pseudocódigo - Ordenar usuarios por nombre alfabético:**

```
función insercion(lista):
    para i = 1 hasta lista.size()-1:
        elemento = lista[i]  // Elemento a insertar
        j = i - 1
        
        // Correr elementos mayores una posición
        mientras j >= 0 AND lista[j].nombre > elemento.nombre:
            lista[j+1] = lista[j]
            j = j - 1
        
        // Insertar en posición correcta
        lista[j+1] = elemento
    
    retornar lista
```

**Visualización - Insertar usuarios alfabéticamente:**

```
Inicial: [Juan, María, Ana, Carlos]

Paso 1: [Juan] ya ordenado

Paso 2: Insertar María en [Juan]
  Juan > María? Sí → correr Juan
  Posición: [María, Juan]

Paso 3: Insertar Ana en [María, Juan]
  Juan > Ana? Sí → correr Juan a [María, Juan, ?]
  María > Ana? Sí → correr María a [?, María, Juan]
  Posición: [Ana, María, Juan]

Paso 4: Insertar Carlos en [Ana, María, Juan]
  Juan > Carlos? No
  María > Carlos? Sí → correr María
  Ana > Carlos? Sí → correr Ana
  Posición: [Ana, Carlos, María, Juan]

RESULTADO: [Ana, Carlos, Juan, María]
```

---

**D) Ordenamiento por Mezcla (Merge Sort)**

| Aspecto | Descripción |
|--------|-------------|
| **Concepto** | Dividir en mitades, ordenar recursivamente, mezclar ordenado |
| **Complejidad** | O(n log n): Mejor que algoritmos anteriores |
| **Estabilidad** | Estable |
| **Espacio** | O(n): Requiere espacio adicional |
| **Uso real** | Cuando necesitas eficiencia garantizada |

**Pseudocódigo - Merge Sort:**

```
función mergeSortRecursivo(lista, bajo, alto):
    si bajo < alto:
        medio = (bajo + alto) / 2
        
        // Dividir mitad izquierda
        mergeSortRecursivo(lista, bajo, medio)
        
        // Dividir mitad derecha
        mergeSortRecursivo(lista, medio+1, alto)
        
        // Mezclar mitades ordenadas
        mezclar(lista, bajo, medio, alto)

función mezclar(lista, bajo, medio, alto):
    // Crear copia de secciones
    izq = copiar(lista[bajo...medio])
    der = copiar(lista[medio+1...alto])
    
    i = 0, j = 0, k = bajo
    
    // Comparar y mezclar
    mientras i < izq.size() AND j < der.size():
        si izq[i] <= der[j]:
            lista[k] = izq[i]
            i++
        si no:
            lista[k] = der[j]
            j++
        k++
    
    // Copiar restantes
    mientras i < izq.size():
        lista[k] = izq[i]
        i++, k++
    
    mientras j < der.size():
        lista[k] = der[j]
        j++, k++
```

**Visualización - Merge Sort [38, 27, 43, 3]:**

```
                [38, 27, 43, 3]
                       │
           ┌───────────┴───────────┐
        [38, 27]              [43, 3]
          │                     │
      ┌───┴───┐             ┌────┴────┐
    [38]   [27]          [43]     [3]
      │     │              │      │
      └──┬──┘              └───┬──┘
        [27, 38]              [3, 43]
          │                     │
        └────────┬──────────┬───┘
              [3, 27, 38, 43]  ← MEZCLA FINAL ORDENADA
```

**Comparación de Complejidad:**

| Algoritmo | Mejor caso | Promedio | Peor caso | Espacio |
|-----------|-----------|----------|-----------|---------|
| **Burbuja** | O(n) | O(n²) | O(n²) | O(1) |
| **Selección** | O(n²) | O(n²) | O(n²) | O(1) |
| **Inserción** | O(n) | O(n²) | O(n²) | O(1) |
| **Merge Sort** | O(n log n) | O(n log n) | O(n log n) | O(n) |

**Recomendación para inmobiliaria:**
```
N < 100 casas: Burbuja o Inserción (simple, eficiente)
N = 100-1000: Inserción o Merge Sort
N > 1000: Merge Sort (garantiza O(n log n))
```

---

#### 4.3.3 APLICACIONES DE ALGORITMOS EN INMOBILIARIA

| Necesidad | Algoritmo | Justificación |
|-----------|-----------|---|
| "Mostrar casas de menor a mayor precio" | Ordenamiento (Burbuja/Inserción) | Ordena lista antes de mostrar |
| "Encontrar casa con precio exacto $1.2M" | Búsqueda Binaria (requiere ordenamiento previo) | O(log n) después de ordenar |
| "Mostrar todas las casas en rango $500k-$1.5M" | Búsqueda Lineal + Filtro | Revisa todas comparando rango |
| "Ordenar 10,000 casas por disponibilidad" | Merge Sort | Garantiza eficiencia O(n log n) |
| "¿Cuál es la ciudad con más propiedades?" | Búsqueda Lineal + Conteo | Recorre todas, cuenta por ciudad |
| "Ordenar usuarios por nombre alfabético" | Inserción | Excelente para datos parcialmente ordenados |

---

## ESTRUCTURA DE PAQUETES {#estructura-paquetes}

```
com.inmobiliaria/
│
├── model/
│   ├── Casa.java
│   ├── Usuario.java
│   ├── Reserva.java
│   ├── Historial.java
│   └── SistemaInmobiliario.java
│
├── view/
│   ├── MenuPrincipal.java
│   ├── MenuBusqueda.java
│   ├── MenuReserva.java
│   ├── MenuVisualizacion.java
│   ├── MenuHistorial.java
│   └── ConsolaHelper.java
│
├── controller/
│   ├── ControladorPrincipal.java
│   ├── ControladorBusqueda.java
│   ├── ControladorReserva.java
│   ├── ControladorPersistencia.java
│   ├── ControladorHistorial.java
│   └── AlgoritmosOrdenamiento.java (contiene todas las implementaciones manuales)
│
├── algoritmos/
│   ├── BusquedaLineal.java
│   ├── BusquedaBinaria.java
│   ├── BurbubujaSortClass.java (o incluir en AlgoritmosOrdenamiento)
│   ├── SelectionSort.java
│   ├── InsertionSort.java
│   └── MergeSort.java
│
├── util/
│   ├── Validador.java
│   └── Formateador.java
│
└── Main.java
```

**Explicación de estructura:**

- **model/**: Entidades y lógica de datos (sin I/O)
- **view/**: Presentación en consola (sin lógica de negocio)
- **controller/**: Orquestación y algoritmos (conecta modelo y vista)
- **algoritmos/**: Algoritmos de búsqueda y ordenamiento aislados para claridad académica
- **util/**: Funciones auxiliares reutilizables
- **Main.java**: Punto de entrada

---

## ESPECIFICACIÓN DE ENTIDADES {#especificación-entidades}

### 5.1 Casa

```
Nombre: Casa
Paquete: com.inmobiliaria.model
Responsabilidad: Representar una propiedad inmobiliaria

Atributos:
  - int id                    (identificador único, generado automáticamente)
  - String direccion          (ej: "Cra 10 #25-45")
  - String ciudad             (ej: "Bogota", "Cartagena")
  - double precio             (precio mensual en pesos)
  - int area                  (metros cuadrados)
  - int dormitorios           (cantidad)
  - int banos                 (cantidad)
  - boolean disponible        (true si puede reservarse)
  - String descripcion        (detalles adicionales)
  - String propietario        (nombre del dueño)
  - LocalDateTime fechaRegistro (cuándo se añadió)

Métodos públicos:
  + Casa(parámetros)          (constructor con validaciones)
  + getId() : int
  + getDireccion() : String
  + getCiudad() : String
  + getPrecio() : double
  + getArea() : int
  + getDormitorios() : int
  + getBanos() : int
  + isDisponible() : boolean
  + setDisponible(boolean)
  + getDescripcion() : String
  + getPropietario() : String
  + getFechaRegistro() : LocalDateTime
  + equals(Object) : boolean      (compara por ID)
  + compareTo(Casa) : int         (para ordenamiento)
  + toString() : String           (presentación formateada)

Validaciones en constructor:
  - ID > 0
  - Dirección no vacía
  - Ciudad no vacía
  - Precio > 0
  - Area > 0
  - Dormitorios >= 0
  - Baños >= 0
```

### 5.2 Usuario

```
Nombre: Usuario
Paquete: com.inmobiliaria.model
Responsabilidad: Representar un cliente del sistema

Atributos:
  - int id                    (identificador único)
  - String nombre             (nombre completo)
  - String email              (correo electrónico)
  - String telefono           (número de contacto)
  - double presupuesto        (capacidad de pago máxima)
  - ArrayList<String> preferencias (características buscadas)
  - boolean activo            (registrado en sistema)
  - LocalDateTime fechaRegistro

Métodos públicos:
  + Usuario(parámetros)
  + getId() : int
  + getNombre() : String
  + getEmail() : String
  + getTelefono() : String
  + getPresupuesto() : double
  + setPresupuesto(double)
  + getPreferencias() : ArrayList<String>
  + agregarPreferencia(String)
  + removerPreferencia(String)
  + isActivo() : boolean
  + getFechaRegistro() : LocalDateTime
  + toString() : String

Validaciones:
  - Email debe contener "@"
  - Telefono debe contener dígitos
  - Presupuesto >= 0
  - Nombre no vacío
```

### 5.3 Reserva

```
Nombre: Reserva
Paquete: com.inmobiliaria.model
Responsabilidad: Registrar intención de alquiler

Atributos:
  - int id                    (identificador único)
  - int idUsuario             (referencia a usuario)
  - int idCasa                (referencia a propiedad)
  - LocalDateTime fechaReserva (cuándo se hizo)
  - LocalDate fechaInicio     (inicio del contrato)
  - int duracion              (meses)
  - EstadoReserva estado      (enum: PENDIENTE, CONFIRMADA, CANCELADA)
  - String observaciones      (notas)

Enum EstadoReserva:
  - PENDIENTE
  - CONFIRMADA
  - CANCELADA

Métodos públicos:
  + Reserva(parámetros)
  + getId() : int
  + getIdUsuario() : int
  + getIdCasa() : int
  + getFechaReserva() : LocalDateTime
  + getFechaInicio() : LocalDate
  + getDuracion() : int
  + getEstado() : EstadoReserva
  + setEstado(EstadoReserva)
  + getObservaciones() : String
  + setObservaciones(String)
  + toString() : String

Validaciones:
  - idUsuario > 0
  - idCasa > 0
  - Duracion > 0 (mínimo 1 mes)
  - FechaInicio no puede ser en pasado
```

### 5.4 Historial

```
Nombre: Historial
Paquete: com.inmobiliaria.model
Responsabilidad: Registrar acciones del sistema

Atributos:
  - int id
  - int idUsuario             (quién ejecutó acción)
  - TipoAccion tipo           (enum)
  - String descripcion        (qué hizo)
  - LocalDateTime fecha       (cuándo)
  - String resultado          (resultado de acción)

Enum TipoAccion:
  - BUSQUEDA
  - VISUALIZACION
  - RESERVA
  - CANCELACION
  - REGISTRO_USUARIO
  - ACCESO_SISTEMA

Métodos públicos:
  + Historial(parámetros)
  + getId() : int
  + getIdUsuario() : int
  + getTipo() : TipoAccion
  + getDescripcion() : String
  + getFecha() : LocalDateTime
  + getResultado() : String
  + toString() : String
```

### 5.5 SistemaInmobiliario

```
Nombre: SistemaInmobiliario
Paquete: com.inmobiliaria.model
Responsabilidad: Orquestador central de datos

Atributos:
  - ArrayList<Casa> casas
  - ArrayList<Usuario> usuarios
  - ArrayList<Reserva> reservas
  - ArrayList<Historial> historial
  - Stack<String> pilaSearches      (últimas búsquedas)
  - Queue<Reserva> colaReservas    (reservas pendientes)
  - int contadorCasas               (para generar IDs)
  - int contadorUsuarios
  - int contadorReservas
  - int contadorHistorial

Métodos principales:
  + agregarCasa(Casa) : void
  + obtenerCasa(int id) : Casa
  + obtenerTodasCasas() : ArrayList<Casa>
  + eliminarCasa(int id) : boolean
  
  + agregarUsuario(Usuario) : void
  + obtenerUsuario(int id) : Usuario
  + obtenerTodosUsuarios() : ArrayList<Usuario>
  
  + crearReserva(int idUsuario, int idCasa, ...) : boolean
  + obtenerReserva(int id) : Reserva
  + obtenerReservasUsuario(int idUsuario) : ArrayList<Reserva>
  
  + registrarAccion(Historial) : void
  + obtenerHistorial() : ArrayList<Historial>
  
  + agregarBusqueda(String) : void
  + obtenerUltimasBusquedas() : Stack<String>
  
  + encolarReserva(Reserva) : void
  + procesarReserva() : Reserva
```

---

## REQUISITOS FUNCIONALES {#requisitos-funcionales}

### 6.1 RF-001: Registrar Propiedad

**Descripción**: El sistema permite registrar una nueva propiedad inmobiliaria.

**Precondiciones**:
- Usuario tiene acceso al menú de administración

**Pasos**:
1. Usuario selecciona "Registrar propiedad"
2. Sistema solicita: dirección, ciudad, precio, área, dormitorios, baños, descripción, propietario
3. Usuario ingresa datos
4. Sistema valida cada campo
5. Si es válido, agrega casa a lista y guarda en archivo

**Postcondiciones**:
- Propiedad existe en memoria y en archivo
- Propiedad tiene ID único
- Sistema confirma registro exitoso

**Técnicas aplicadas**:
- Validación de entrada (Unidad 1)
- ArrayList (Unidad 1)
- Escritura en archivo (Unidad 1)

---

### 6.2 RF-002: Mostrar Propiedades

**Descripción**: El sistema lista todas las propiedades registradas.

**Precondiciones**:
- Existen propiedades registradas

**Pasos**:
1. Usuario selecciona "Ver propiedades"
2. Sistema obtiene lista completa
3. Sistema formatea y presenta cada propiedad
4. Muestra: ID, dirección, ciudad, precio, área, disponibilidad

**Técnicas aplicadas**:
- Iteración de ArrayList
- Formateo de cadenas (Unidad 1)
- Presentación en consola (Unidad 1)

---

### 6.3 RF-003: Buscar Propiedades

**Descripción**: El usuario busca propiedades según criterios.

**Tipos de búsqueda**:

#### 6.3.1 Búsqueda por ciudad
- Usuario ingresa ciudad
- Sistema aplica búsqueda lineal
- Retorna todas las coincidencias

**Técnicas**: Búsqueda lineal (Unidad 3), Operaciones con cadenas (Unidad 1)

#### 6.3.2 Búsqueda por rango de precio
- Usuario ingresa precio mínimo y máximo
- Sistema filtra propiedades en rango
- Retorna resultados

**Técnicas**: Búsqueda con filtros, Fuerza bruta (Unidad 2)

#### 6.3.3 Búsqueda con múltiples criterios
- Usuario selecciona ciudad, precio, dormitorios mínimos
- Sistema aplica todos los filtros secuencialmente
- Retorna propiedades que cumplen TODO

**Técnicas**: Fuerza bruta (Unidad 2), ArrayList (Unidad 1)

#### 6.3.4 Búsqueda binaria por precio
- Sistema ordena propiedades por precio (Merge Sort)
- Usuario ingresa precio objetivo
- Sistema aplica búsqueda binaria
- Retorna propiedad exacta (si existe) o más cercana

**Técnicas**: Búsqueda binaria (Unidad 3), Ordenamiento (Unidad 3)

**Postcondiciones**:
- Búsqueda se registra en pila de búsquedas recientes
- Búsqueda se registra en historial

---

### 6.4 RF-004: Ordenar Propiedades

**Descripción**: El usuario ve propiedades ordenadas según criterios.

**Tipos de ordenamiento**:

| Criterio | Algoritmo | Ubicación |
|----------|-----------|-----------|
| Por precio (menor a mayor) | Burbuja o Inserción | AlgoritmosOrdenamiento.java |
| Por precio (mayor a menor) | Burbuja o Inserción | AlgoritmosOrdenamiento.java |
| Por tamaño/área | Selección | AlgoritmosOrdenamiento.java |
| Por nombre/dirección | Inserción | AlgoritmosOrdenamiento.java |
| Combinado (eficiente) | Merge Sort | AlgoritmosOrdenamiento.java |

**Técnicas aplicadas**:
- Algoritmos de ordenamiento manual (Unidad 3)
- Comparadores personalizados

---

### 6.5 RF-005: Ver Detalles de Propiedad

**Descripción**: Usuario consulta todos los detalles de una propiedad específica.

**Pasos**:
1. Sistema muestra lista con IDs
2. Usuario selecciona ID
3. Sistema usa búsqueda recursiva para encontrar
4. Muestra detalles completos en formato legible

**Técnicas aplicadas**:
- Búsqueda recursiva (Unidad 2)
- Formateo visual (Unidad 1)

---

### 6.6 RF-006: Reservar Propiedad

**Descripción**: Usuario realiza reserva de una propiedad.

**Precondiciones**:
- Usuario está registrado
- Propiedad existe y está disponible
- Presupuesto del usuario >= precio de propiedad
- Fecha de inicio es válida

**Pasos**:
1. Usuario selecciona propiedad
2. Usuario ingresa fecha de inicio
3. Usuario ingresa duración (meses)
4. Sistema valida disponibilidad y presupuesto
5. Si es válido:
   - Crea objeto Reserva
   - Marca propiedad como no disponible
   - Encola reserva en cola de procesamiento
   - Registra en historial
6. Si no es válido, muestra error específico

**Postcondiciones**:
- Reserva existe con estado PENDIENTE
- Propiedad no disponible para otros usuarios
- Acción registrada en historial

**Técnicas aplicadas**:
- Colas (FIFO) (Unidad 1)
- Validaciones de negocio (Unidad 2)
- Historial (Unidad 1)

---

### 6.7 RF-007: Ver Historial de Búsquedas Recientes

**Descripción**: Usuario ve sus últimas búsquedas en orden LIFO.

**Pasos**:
1. Usuario selecciona "Últimas búsquedas"
2. Sistema muestra pila de búsquedas (tope primero)
3. Muestra hasta 5 últimas búsquedas

**Técnicas aplicadas**:
- Pila (LIFO) (Unidad 1)
- Pop() para obtener tope

---

### 6.8 RF-008: Procesar Cola de Reservas

**Descripción**: Sistema procesa reservas en cola de espera.

**Funcionamiento**:
1. Cada reserva se encola en orden de llegada
2. Sistema puede procesar manualmente o automáticamente
3. Cada reserva procesada es desencolada (FIFO)
4. Cambio de estado: PENDIENTE → CONFIRMADA (o CANCELADA si hay error)

**Técnicas aplicadas**:
- Cola (FIFO) (Unidad 1)
- Poll() para desencolar

---

### 6.9 RF-009: Guardar Datos en Archivos

**Descripción**: Sistema persiste todos los datos en archivos al cerrar.

**Archivos generados**:
- `datos/casas.csv`: Todas las propiedades
- `datos/usuarios.csv`: Todos los usuarios
- `datos/reservas.csv`: Todas las reservas
- `datos/historial.csv`: Historial de acciones

**Formato CSV**: Campos separados por coma, sin espacios después.

**Técnicas aplicadas**:
- I/O de archivos (FileWriter, BufferedWriter)
- Formato CSV (Unidad 1)
- Serialización manual (sin usar librerías)

---

### 6.10 RF-010: Cargar Datos desde Archivos

**Descripción**: Al iniciar, sistema reconstruye estado desde archivos.

**Proceso**:
1. Sistema busca archivos en directorio `datos/`
2. Si existen, lee cada línea (CSV)
3. Parsea valores
4. Crea objetos
5. Añade a colecciones correspondientes
6. Si no existen, inicia con datos vacíos

**Técnicas aplicadas**:
- I/O de archivos (FileReader, BufferedReader)
- Parsing de CSV
- Validación de formato

---

### 6.11 RF-011: Aplicar Backtracking para Recomendaciones

**Descripción**: Sistema encuentra todas las combinaciones posibles de propiedades que encajen en presupuesto.

**Ejemplo**:
- Usuario: "Quiero gastar máximo $3M en 3 propiedades"
- Sistema: "Aquí están todas las combinaciones: {Casa1+Casa2}, {Casa1+Casa3}, {Casa2+Casa4}, ..."

**Técnicas aplicadas**:
- Backtracking (Unidad 2)
- Recursión (Unidad 2)
- Exploración de espacios de solución

---

### 6.12 RF-012: Navegación de Menús (Recursión)

**Descripción**: Sistema permite navegar jerárquicamente por menús.

**Estructura**:
```
Menú Principal
├── Buscar propiedad
│   ├── Por ciudad
│   ├── Por precio
│   ├── Búsqueda avanzada
│   │   ├── Filtrar por dormitorios
│   │   └── Atrás
│   └── Atrás
├── Ver propiedades
├── Historial
└── Salir
```

**Técnicas aplicadas**:
- Recursión de pila (Unidad 2)
- Menús interactivos (Unidad 1)

---

## REQUISITOS NO FUNCIONALES {#requisitos-no-funcionales}

### 7.1 Calidad del Código

| Requisito | Descripción |
|-----------|-------------|
| **Documentación** | Cada clase y método público debe tener comentario JavaDoc |
| **Nomenclatura** | Usar CamelCase, nombres descriptivos, en español o inglés consistentemente |
| **Organización** | Código dividido en paquetes lógicos |
| **Modularidad** | Funciones específicas, reutilizables, sin código duplicado |
| **Consistencia** | Indentación de 4 espacios, llaves en estilo consistente |

### 7.2 Rendimiento

| Requisito | Descripción |
|-----------|-------------|
| **Búsqueda lineal** | Debe ejecutar en < 100ms para 1000 propiedades |
| **Búsqueda binaria** | Debe ejecutar en < 10ms después de ordenar |
| **Ordenamiento** | Merge Sort debe ordenar 1000 elementos en < 500ms |
| **Memoria** | Sin fugas; estructuras se limpian al salir |

### 7.3 Persistencia

| Requisito | Descripción |
|-----------|-------------|
| **Integridad** | Datos no se corrompen entre sesiones |
| **Consistencia** | Mismo archivo siempre tiene mismo contenido |
| **Recuperación** | Si archivo está corrupto, sistema notifica error |

### 7.4 Usabilidad

| Requisito | Descripción |
|-----------|-------------|
| **Menús claros** | Opciones numeradas, instrucciones explícitas |
| **Validación visible** | Errores de entrada son claros y específicos |
| **Confirmaciones** | Acciones críticas piden confirmación |
| **Navegación** | Siempre hay opción "Atrás" o "Salir" |

### 7.5 Compatibilidad

| Requisito | Descripción |
|-----------|-------------|
| **Java** | Versión 8 o superior |
| **Librerías** | Solo java.util, java.io, java.time (estándar) |
| **SO** | Windows, Linux, macOS (código agnóstico) |

---

## CASOS DE USO {#casos-de-uso}

### 8.1 Caso de Uso: Buscar Propiedad

```
Actor Primario: Usuario (Cliente)

Precondiciones:
  - Sistema está ejecutándose
  - Existen propiedades registradas

Flujo Principal:
  1. Usuario selecciona "Buscar propiedad"
  2. Sistema muestra opciones de búsqueda
  3. Usuario selecciona "Por ciudad"
  4. Usuario ingresa "Bogota"
  5. Sistema valida entrada
  6. Sistema ejecuta búsqueda lineal
  7. Sistema presenta 5 coincidencias ordenadas
  8. Sistema registra búsqueda en pila
  9. Usuario regresa al menú

Postcondiciones:
  - Búsqueda registrada en historial
  - Pila contiene nueva búsqueda

Flujos Alternativos:
  A. Usuario ingresa ciudad inválida
    - Sistema muestra "Ciudad no encontrada"
    - Retorna al menú de búsqueda
  
  B. Usuario selecciona búsqueda binaria
    - Requiere que sistema ordene primero (Merge Sort)
    - Aplica búsqueda binaria
    - Más eficiente que búsqueda lineal
```

### 8.2 Caso de Uso: Realizar Reserva

```
Actor Primario: Usuario (Cliente)

Precondiciones:
  - Usuario está registrado
  - Usuario vio detalles de propiedad

Flujo Principal:
  1. Usuario selecciona "Reservar"
  2. Usuario ingresa ID de propiedad
  3. Sistema valida que propiedad existe
  4. Sistema valida que propiedad está disponible
  5. Sistema valida que presupuesto >= precio
  6. Usuario ingresa fecha de inicio
  7. Usuario ingresa duración (meses)
  8. Sistema crea objeto Reserva
  9. Sistema encola en cola de reservas
  10. Sistema marca propiedad como no disponible
  11. Sistema registra en historial
  12. Sistema confirma al usuario

Postcondiciones:
  - Reserva existe con estado PENDIENTE
  - Propiedad no puede reservarse nuevamente
  - Cola de reservas aumentó en 1

Validaciones de Negocio:
  - Presupuesto >= precio
  - Propiedad disponible
  - Duración >= 1 mes
  - Fecha inicio >= hoy
```

---

## FLUJO DEL SISTEMA {#flujo-del-sistema}

### 9.1 Flujo General de Ejecución

```
┌─────────────────────────────────────┐
│    INICIO DEL PROGRAMA (Main.java)  │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  Cargar datos desde archivos        │
│  - casas.csv → ArrayList<Casa>      │
│  - usuarios.csv → ArrayList<Usuario>│
│  - reservas.csv → ArrayList<Reserva>│
│  - historial.csv → ArrayList<...>   │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│    Inicializar Sistema              │
│    - Stack búsquedas                │
│    - Queue reservas                 │
│    - IDs contadores                 │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│    MENÚ PRINCIPAL (Loop)            │
│    1. Buscar propiedad              │
│    2. Ver propiedades               │
│    3. Ordenar propiedades           │
│    4. Reservar                      │
│    5. Ver historial                 │
│    6. Registrar propiedad           │
│    7. Procesar reservas             │
│    8. Salir                         │
└────────┬────────────┬───────────────┘
         │            │
    Usuario            └─► Validar opción
    selecciona              │
         │                  ▼
         └────────────► Ejecutar opción
                            │
                            ▼
                      ┌──────────────────┐
                      │ Según opción:    │
                      │ - Buscar         │
                      │ - Ver            │
                      │ - Ordenar        │
                      │ - Reservar       │
                      │ - Historial      │
                      │ - Registrar      │
                      │ - Procesar       │
                      └────────┬─────────┘
                               │
                         ┌─────▼──────┐
                         │ ¿Salir?    │
                         └─────┬──────┘
                           No  │  Sí
                               │  │
                ┌──────────────┘  ▼
                │         ┌──────────────────┐
                │         │ Guardar datos    │
                │         │ en archivos      │
                │         └────────┬─────────┘
                │                  │
                └──────────────────► FIN
```

### 9.2 Subproceso: Búsqueda de Propiedad

```
Usuario selecciona "Buscar propiedad"
         │
         ▼
┌────────────────────────────────┐
│ Mostrar opciones:              │
│ 1. Por ciudad (lineal)         │
│ 2. Por precio (rango)          │
│ 3. Por precio exacto (binaria) │
│ 4. Búsqueda avanzada          │
│ 5. Atrás                       │
└────────┬───────────────────────┘
         │
    Usuario elige opción
         │
         ▼
    ┌────────────────────────┐
    │ Capturar criterios     │
    │ Validar entrada        │
    └────────┬───────────────┘
             │
             ▼
    ┌────────────────────────┐
    │ ¿Cuál algoritmo?       │
    │ ┌──────────────────┐   │
    │ │ Búsqueda lineal  │───► Recorrer todas
    │ │ Búsqueda binaria │───► Ordenar primero, luego
    │ │ Fuerza bruta     │───► Todos los filtros
    │ │ Backtracking     │───► Combinaciones
    │ └──────────────────┘   │
    └────────┬───────────────┘
             │
             ▼
    ┌────────────────────────┐
    │ Ejecutar algoritmo     │
    │ Obtener resultados     │
    └────────┬───────────────┘
             │
             ▼
    ┌────────────────────────┐
    │ Ordenar resultados     │
    │ (usando algoritmo)     │
    └────────┬───────────────┘
             │
             ▼
    ┌────────────────────────┐
    │ Presentar en consola   │
    │ Actualizar historial   │
    │ Guardar en pila        │
    └────────┬───────────────┘
             │
             ▼
    Retornar a menú
```

### 9.3 Procesamiento de Cola de Reservas

```
Sistema detecta reservas pendientes
         │
         ▼
┌──────────────────────────┐
│ ¿Cola vacía?             │
│ Sí → Salir               │
│ No → Continuar           │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────────┐
│ Desencolarse (FIFO)      │
│ Obtener primera reserva  │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────────┐
│ Validar reserva:         │
│ - Usuario activo?        │
│ - Casa disponible?       │
│ - Presupuesto válido?    │
└────────┬─────────────────┘
         │
      ┌──┴──┐
   Sí │     │ No
      │     │
      ▼     ▼
   ┌─┴──┐ ┌──┴──┐
   │✓    │ │✗    │
   │CONF │ │CAN  │
   └─┬──┘ └──┬──┘
     │       │
     └───┬───┘
         │
         ▼
┌──────────────────────────┐
│ Registrar en historial   │
│ Actualizar estado        │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────────┐
│ ¿Más reservas en cola?   │
│ Sí → Ir a inicio paso 2  │
│ No → Fin                 │
└──────────────────────────┘
```

---

## RESUMEN ACADÉMICO

Este proyecto integra todos los contenidos de "Técnicas de Programación" de forma coherente:

| Unidad | Técnicas | Implementación en Inmobiliaria |
|--------|----------|------|
| **1. Generalidades** | Entrada/salida, archivos, strings, documentación, listas, pilas, colas | Búsqueda de propiedades, menús, persistencia, historial |
| **2. Técnicas fundamentales** | Fuerza bruta, recursión, backtracking | Filtrados múltiples, navegación de menús, recomendaciones |
| **3. Algoritmos** | Búsqueda lineal/binaria, ordenamiento | Búsqueda eficiente, presentación ordenada de propiedades |
| **Arquitectura** | MVC | Separación clara entre presentación, lógica y datos |

El proyecto es **completamente funcional**, **académicamente sólido** y **escalable** para futuras mejoras.

---

**Fin del documento de análisis técnico**
