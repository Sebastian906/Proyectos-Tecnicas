# REQUISITOS TÉCNICOS Y MAPEO DE TÉCNICAS
## Sistema de Gestión Inmobiliaria en Java

---

## TABLA DE CONTENIDOS
1. [Requisitos Técnicos Detallados](#requisitos-técnicos)
2. [Matriz de Mapeo de Técnicas por Funcionalidad](#matriz-mapeo)
3. [Estructura de Datos Esperada](#estructura-datos)
4. [Algoritmos Manuales Requeridos](#algoritmos-manuales)
5. [Checklist de Implementación](#checklist)
6. [Ejemplos de Flujo de Datos](#flujos-datos)
7. [Criterios de Evaluación Académica](#criterios-evaluacion)

---

## REQUISITOS TÉCNICOS DETALLADOS {#requisitos-técnicos}

### 1.1 Requisitos de Lenguaje y Entorno

```
Lenguaje:           Java 8+
IDE recomendado:    Eclipse, IntelliJ IDEA, NetBeans, o VS Code + plugins
JDK:                Java Development Kit 11 o superior
Compilación:        javac (línea de comandos)
Ejecución:          java (desde consola)
Sistema Archivos:   UTF-8 encoding
Path separador:     /
```

### 1.2 Requisitos de Estructura

```
Directorios obligatorios:
/
├── src/
│   └── com/inmobiliaria/
│       ├── model/
│       ├── view/
│       ├── controller/
│       ├── algoritmos/
│       ├── util/
│       └── Main.java
│
├── datos/
│   ├── casas.csv
│   ├── usuarios.csv
│   ├── reservas.csv
│   └── historial.csv
│
├── bin/ (generado automáticamente)
│   └── com/inmobiliaria/
│
└── README.md (documentación)

Convención de nombres:
  - Paquetes: minúsculas (com.inmobiliaria.model)
  - Clases: PascalCase (CasaRepository)
  - Métodos: camelCase (obtenerPropiedad)
  - Constantes: UPPER_SNAKE_CASE (PRECIO_MINIMO)
  - Variables: camelCase (totalReservas)
```

### 1.3 Requisitos de Compilación

```
Compilar todo:
  javac -d bin -sourcepath src src/com/inmobiliaria/**/*.java

Compilar paquete:
  javac -d bin -sourcepath src src/com/inmobiliaria/model/*.java

Ejecutar:
  cd bin
  java com.inmobiliaria.Main
```

### 1.4 Requisitos de Archivos Persistentes

```
Formato: CSV (Comma-Separated Values)

ARCHIVO: datos/casas.csv
─────────────────────────────────────────────────────────
Estructura:
  id,direccion,ciudad,precio,area,dormitorios,banos,disponible,descripcion,propietario

Ejemplo de contenido:
  1,Cra 10 #25-45,Bogota,1500000,80,2,1,true,Apto moderno centro,Juan Pérez
  2,Av Paseo #50-20,Cartagena,2500000,120,3,2,true,Casa frente al mar,María García
  3,Calle 5 #8-15,Medellin,800000,60,1,1,false,Apartamento pequeño,Carlos Mendez

Reglas:
  - Sin espacios después de coma
  - Campos de texto sin comillas
  - Disponible como true/false
  - Precio siempre numérico


ARCHIVO: datos/usuarios.csv
─────────────────────────────────────────────────────────
Estructura:
  id,nombre,email,telefono,presupuesto,activo

Ejemplo:
  1,Ana López,ana@email.com,3001234567,5000000,true
  2,Roberto Franco,roberto@email.com,3157654321,3000000,true


ARCHIVO: datos/reservas.csv
─────────────────────────────────────────────────────────
Estructura:
  id,idUsuario,idCasa,fechaReserva,fechaInicio,duracion,estado,observaciones

Ejemplo:
  1,1,2,2024-01-15T10:30:00,2024-02-01,12,CONFIRMADA,Cliente requiere mudanza lenta
  2,2,1,2024-01-16T14:22:00,2024-03-01,6,PENDIENTE,Esperar confirmación banco


ARCHIVO: datos/historial.csv
─────────────────────────────────────────────────────────
Estructura:
  id,idUsuario,tipo,descripcion,fecha,resultado

Ejemplo:
  1,1,BUSQUEDA,Búsqueda por ciudad Bogota,2024-01-15T10:25:00,EXITOSA
  2,1,VISUALIZACION,Visualización casa ID=2,2024-01-15T10:26:00,EXITOSA
```

### 1.5 Requisitos de Dependencias

**Librerías permitidas SOLO de java.util, java.io, java.time:**

```
java.util:
  - ArrayList<E>
  - Stack<E>
  - Queue<E>, LinkedList<E>
  - Scanner
  - Collections

java.io:
  - File
  - FileReader, FileWriter
  - BufferedReader, BufferedWriter
  - IOException

java.time:
  - LocalDate
  - LocalDateTime
  - DateTimeFormatter

NO están permitidas:
  - javax.swing (Swing GUI)
  - java.awt (AWT)
  - JDBC o librerías de BD
  - JSON libraries externas
  - Apache Commons
  - Terceros no estándar
```

---

## MATRIZ DE MAPEO DE TÉCNICAS POR FUNCIONALIDAD {#matriz-mapeo}

### 2.1 Tabla Completa de Mapeo

| # | Funcionalidad | Técnicas Primarias | Técnicas Secundarias | Ubicación Código |
|---|---|---|---|---|
| **F1** | Captura menú | Scanner, validación entrada | Strings (equalsIgnoreCase) | MenuPrincipal.java |
| **F2** | Registrar propiedad | ArrayList.add() | Validación, I/O consola | ControladorPrincipal |
| **F3** | Mostrar propiedades | ArrayList iteración | Formateo strings | MenuVisualizacion |
| **F4** | Búsqueda por ciudad | Búsqueda Lineal O(n) | Strings.contains() | ControladorBusqueda |
| **F5** | Búsqueda por rango precio | Búsqueda Lineal + Filtro | Comparadores numéricos | ControladorBusqueda |
| **F6** | Búsqueda múltiple criterios | Fuerza Bruta O(n*m) | ArrayList, condicionales | ControladorBusqueda |
| **F7** | Búsqueda binaria precio | Búsqueda Binaria O(log n) | Merge Sort previo | ControladorBusqueda |
| **F8** | Ordenar por precio | Burbuja/Inserción/Merge | Comparadores | AlgoritmosOrdenamiento |
| **F9** | Ordenar por área | Selección/Inserción | Comparadores personalizados | AlgoritmosOrdenamiento |
| **F10** | Ordenar por nombre | Inserción (O(n) si casi ordenado) | Comparación strings | AlgoritmosOrdenamiento |
| **F11** | Ordenamiento masivo | Merge Sort O(n log n) | División y conquista | AlgoritmosOrdenamiento |
| **F12** | Ver detalles propiedad | Búsqueda Recursiva | Stack de recursión | ControladorBusqueda |
| **F13** | Realizar reserva | Validaciones, ArrayList.add | Colas enqueue | ControladorReserva |
| **F14** | Procesar cola reservas | Cola FIFO, poll() | Validaciones | ControladorReserva |
| **F15** | Historial búsquedas | Pila LIFO, push/pop | Stack | ControladorHistorial |
| **F16** | Historial completo | ArrayList iteración | Formateo | ControladorHistorial |
| **F17** | Guardar en archivo | FileWriter, BufferedWriter | Formato CSV | ControladorPersistencia |
| **F18** | Cargar desde archivo | FileReader, BufferedReader | Parsing CSV | ControladorPersistencia |
| **F19** | Validación email | Strings.indexOf('@') | Operaciones cadena | Validador |
| **F20** | Recomendaciones inteligentes | Backtracking | Recursión | ControladorBusqueda |
| **F21** | Navegación menús | Recursión de pila | Menús jerárquicos | MenuPrincipal |
| **F22** | Validar paréntesis búsqueda | Pila para paréntesis | Estructura LIFO | ControladorBusqueda |
| **F23** | Procesamiento listas | Recursión de cola | Tail recursion | ControladorReserva |

### 2.2 Matriz de Unidades vs Técnicas

```
                           UNIDAD 1          UNIDAD 2          UNIDAD 3
                        Generalidades    Técnicas Fund.    Búsqueda/Orden.
                        
Entrada/Salida               ✓
Archivos                      ✓
Strings                       ✓
Documentación                 ✓
ArrayList                     ✓
Pila (Stack)                  ✓
Cola (Queue)                  ✓
Fuerza Bruta                              ✓
Recursión Pila                           ✓
Recursión Cola                           ✓
Recursión Cruzada                        ✓
Backtracking                             ✓
Búsqueda Lineal                                              ✓
Búsqueda Binaria                                             ✓
Burbuja Sort                                                 ✓
Selección Sort                                               ✓
Inserción Sort                                               ✓
Merge Sort                                                   ✓

Funcionalidades que usan Unidad 1:        F1-F3, F13-F18
Funcionalidades que usan Unidad 2:        F4-F7, F12-F15, F20-F23
Funcionalidades que usan Unidad 3:        F4, F7-F11, F19
```

### 2.3 Complejidad Esperada por Algoritmo

```
BÚSQUEDAS:
╔════════════════════╦═══════════╦════════════════════════════╗
║ Algoritmo          ║ Complej.  ║ Aplicación                 ║
╠════════════════════╬═══════════╬════════════════════════════╣
║ Lineal             ║ O(n)      ║ Buscar por ciudad          ║
║ Binaria            ║ O(log n)  ║ Buscar por precio exacto   ║
║ Fuerza Bruta       ║ O(n*m)    ║ Búsqueda con filtros       ║
║ Backtracking       ║ O(2^n)    ║ Combinaciones (restringida)║
╚════════════════════╩═══════════╩════════════════════════════╝

ORDENAMIENTOS:
╔════════════════════╦══════════════╦════════════════════════════╗
║ Algoritmo          ║ Complejidad  ║ Mejor caso / Peor caso     ║
╠════════════════════╬══════════════╬════════════════════════════╣
║ Burbuja            ║ O(n²)        ║ O(n) / O(n²)               ║
║ Selección          ║ O(n²)        ║ O(n²) / O(n²)              ║
║ Inserción          ║ O(n²)        ║ O(n) / O(n²)               ║
║ Merge Sort         ║ O(n log n)   ║ O(n log n) / O(n log n)    ║
╚════════════════════╩══════════════╩════════════════════════════╝
```

---

## ESTRUCTURA DE DATOS ESPERADA {#estructura-datos}

### 3.1 Especificación de Clases del Modelo

#### Casa.java
```
ATRIBUTOS:
  private int id;                    // Identificador único
  private String direccion;          // "Cra 10 #25-45"
  private String ciudad;             // "Bogota"
  private double precio;             // Valor en pesos
  private int area;                  // Metros cuadrados
  private int dormitorios;           // Cantidad de cuartos
  private int banos;                 // Cantidad de baños
  private boolean disponible;        // true = puede reservarse
  private String descripcion;        // "Apto moderno..."
  private String propietario;        // "Juan Pérez"
  private LocalDateTime fechaRegistro;

MÉTODOS PRINCIPALES:
  + Casa(int id, String dir, String city, double precio, 
         int area, int dorm, int banos, boolean disp, 
         String desc, String owner)
  + getId() : int
  + getCiudad() : String
  + getPrecio() : double
  + getArea() : int
  + getDormitorios() : int
  + isDisponible() : boolean
  + setDisponible(boolean) : void
  + equals(Object) : boolean              // Compara por ID
  + compareTo(Casa) : int                 // Para ordenamientos
  + toString() : String                   // Formato legible

VALIDACIONES EN CONSTRUCTOR:
  - id > 0
  - direccion.length() > 0
  - ciudad.length() > 0
  - precio > 0
  - area > 0
  - dormitorios >= 0
  - banos >= 0
  
MÉTODOS AUXILIARES:
  + isPrecioEnRango(double min, double max) : boolean
  + contieneEnDescripcion(String texto) : boolean
  + tieneDormitorioMinimo(int minimo) : boolean
```

#### Usuario.java
```
ATRIBUTOS:
  private int id;
  private String nombre;
  private String email;
  private String telefono;
  private double presupuesto;
  private ArrayList<String> preferencias;
  private boolean activo;
  private LocalDateTime fechaRegistro;

MÉTODOS:
  + Usuario(int id, String nombre, String email, 
            String telefono, double presupuesto)
  + getId() : int
  + getNombre() : String
  + getEmail() : String
  + getPresupuesto() : double
  + setPresupuesto(double) : void
  + getPreferencias() : ArrayList<String>
  + agregarPreferencia(String) : void
  + removerPreferencia(String) : void
  + isActivo() : boolean
  + toString() : String

VALIDACIONES:
  - nombre no vacío
  - email contiene "@"
  - presupuesto >= 0
  - telefono tiene dígitos
```

#### Reserva.java
```
ATRIBUTOS:
  private int id;
  private int idUsuario;
  private int idCasa;
  private LocalDateTime fechaReserva;
  private LocalDate fechaInicio;
  private int duracion;            // en meses
  private EstadoReserva estado;    // PENDIENTE, CONFIRMADA, CANCELADA
  private String observaciones;

ENUM EstadoReserva:
  PENDIENTE, CONFIRMADA, CANCELADA

MÉTODOS:
  + Reserva(int id, int idUser, int idCasa, 
            LocalDate inicio, int duracion)
  + getId() : int
  + getIdUsuario() : int
  + getIdCasa() : int
  + getFechaInicio() : LocalDate
  + getDuracion() : int
  + getEstado() : EstadoReserva
  + setEstado(EstadoReserva) : void
  + toString() : String

VALIDACIONES:
  - idUsuario > 0
  - idCasa > 0
  - duracion > 0
  - fechaInicio no pasado
```

#### Historial.java
```
ATRIBUTOS:
  private int id;
  private int idUsuario;
  private TipoAccion tipo;
  private String descripcion;
  private LocalDateTime fecha;
  private String resultado;

ENUM TipoAccion:
  BUSQUEDA, VISUALIZACION, RESERVA, CANCELACION, 
  REGISTRO_USUARIO, ACCESO_SISTEMA

MÉTODOS:
  + Historial(int id, int idUser, TipoAccion tipo,
              String desc, String resultado)
  + getId() : int
  + getIdUsuario() : int
  + getTipo() : TipoAccion
  + toString() : String
```

#### SistemaInmobiliario.java
```
ATRIBUTOS:
  private ArrayList<Casa> casas;
  private ArrayList<Usuario> usuarios;
  private ArrayList<Reserva> reservas;
  private ArrayList<Historial> historial;
  private Stack<String> pilaSearches;
  private Queue<Reserva> colaReservas;
  
  // Contadores de ID
  private int contadorCasas = 0;
  private int contadorUsuarios = 0;
  private int contadorReservas = 0;
  private int contadorHistorial = 0;

MÉTODOS PRINCIPALES:
  // Gestión de casas
  + agregarCasa(Casa) : boolean
  + obtenerCasa(int id) : Casa
  + obtenerTodasCasas() : ArrayList<Casa>
  + eliminarCasa(int id) : boolean
  + obtenerCasasPorCiudad(String) : ArrayList<Casa>
  + obtenerCasasPorRango(double min, double max) : ArrayList<Casa>
  
  // Gestión de usuarios
  + agregarUsuario(Usuario) : boolean
  + obtenerUsuario(int id) : Usuario
  + obtenerTodosUsuarios() : ArrayList<Usuario>
  
  // Gestión de reservas
  + crearReserva(int idUser, int idCasa, LocalDate, int) : boolean
  + obtenerReservasUsuario(int idUser) : ArrayList<Reserva>
  + procesarReservaDelaCola() : Reserva
  
  // Pila de búsquedas
  + agregarBusquedaReciente(String) : void
  + obtenerUltimasBusquedas() : Stack<String>
  
  // Cola de reservas
  + encolarReserva(Reserva) : void
  + procesarReserva() : Reserva
  + tamanoCola() : int
  
  // Historial
  + registrarAccion(Historial) : void
  + obtenerHistorial() : ArrayList<Historial>
```

### 3.2 Relaciones Entre Entidades

```
Usuario (1) ──◄── (n) Reserva
  id             idUsuario

Casa (1) ──◄── (n) Reserva
  id                idCasa

Usuario (1) ──◄── (n) Historial
  id             idUsuario

Reserva en Queue en SistemaInmobiliario
Búsquedas en Stack en SistemaInmobiliario
```

---

## ALGORITMOS MANUALES REQUERIDOS {#algoritmos-manuales}

### 4.1 Búsqueda Lineal

**Ubicación**: `ControladorBusqueda.java` o `algoritmos/BusquedaLineal.java`

**Firma de método**:
```java
public static Casa busquedaLinealPorID(ArrayList<Casa> casas, int idBuscado)
public static ArrayList<Casa> busquedaLinealPorCiudad(ArrayList<Casa> casas, String ciudad)
public static ArrayList<Casa> busquedaLinealConFiltro(ArrayList<Casa> casas, 
                                                      double precioMin, 
                                                      double precioMax)
```

**Requisitos**:
- NO usar `stream()` de Java 8
- NO usar `filter()` de Java 8
- NO usar métodos de Collections
- Implementar manualmente con loops `for` o `while`
- Incluir variables de conteo para análisis de complejidad
- Documentar caso mejor/peor/promedio

### 4.2 Búsqueda Binaria

**Ubicación**: `ControladorBusqueda.java` o `algoritmos/BusquedaBinaria.java`

**Precondición**: La lista DEBE estar ordenada por el atributo buscado

**Firma**:
```java
public static Casa busquedaBinariaPorPrecio(ArrayList<Casa> casasOrdenadas, double precio)
public static int busquedaBinariaIndice(ArrayList<Casa> casasOrdenadas, Casa objetivo)
```

**Requisitos**:
- Validar que lista esté ordenada antes de ejecutar
- Implementar técnica de dividir en mitades
- Retornar índice (no la casa) o null si no existe
- Contar iteraciones para análisis

### 4.3 Ordenamiento Burbuja

**Ubicación**: `AlgoritmosOrdenamiento.java`

**Firma**:
```java
public static void burbujaAscendente(ArrayList<Casa> casas, String criterio)
// criterio: "precio", "area", "dormitorios"

public static void burbujaDescendente(ArrayList<Casa> casas, String criterio)
```

**Requisitos**:
- Implementar comparaciones manuales
- Implementar intercambios manuales (sin usar método sort)
- Incluir contadores de comparaciones e intercambios
- Documentar que es O(n²)
- Optimización opcional: flag de cambio para salida temprana

### 4.4 Ordenamiento Selección

**Ubicación**: `AlgoritmosOrdenamiento.java`

**Firma**:
```java
public static void seleccionAscendente(ArrayList<Casa> casas, String criterio)
```

**Requisitos**:
- Encontrar mínimo en cada pasada
- Intercambiar con posición actual
- Documentar O(n²) siempre (no hay caso mejor)

### 4.5 Ordenamiento Inserción

**Ubicación**: `AlgoritmosOrdenamiento.java`

**Firma**:
```java
public static void insercionAscendente(ArrayList<Casa> casas, String criterio)
```

**Requisitos**:
- Construir subarreglo ordenado
- Insertar nuevo elemento en posición correcta
- Correr elementos mayores una posición
- Documentar O(n) si casi ordenado, O(n²) peor caso

### 4.6 Ordenamiento Merge Sort

**Ubicación**: `AlgoritmosOrdenamiento.java`

**Firma**:
```java
public static void mergeSortAscendente(ArrayList<Casa> casas, String criterio, 
                                       int bajo, int alto)
private static void mezclar(ArrayList<Casa> casas, int bajo, int medio, int alto)
```

**Requisitos**:
- Implementar recursión de división
- Implementar función de mezcla separada
- O(n log n) garantizado
- Requiere espacio adicional O(n)
- Documentar por qué es más eficiente que burbuja

### 4.7 Búsqueda Recursiva

**Ubicación**: `ControladorBusqueda.java`

**Firma**:
```java
public static Casa busquedaRecursivaPorID(ArrayList<Casa> casas, int id, int indice)
// Caso base: indice >= casas.size() → retornar null
// Caso base: casas[indice].id == id → retornar casa
// Caso recursivo: llamar con indice+1
```

**Requisitos**:
- Incluir casos base para evitar stack overflow
- Demostrar cómo se divide el problema
- Documentar stack trace de llamadas

### 4.8 Recursión de Cola

**Ubicación**: `ControladorReserva.java`

**Firma**:
```java
public static int procesarReservasRecursivo(ArrayList<Reserva> reservas, int indice)
// Procesar cada reserva y retornar total procesadas
```

**Requisitos**:
- Llamada recursiva es la ÚLTIMA operación
- Incluir parámetro acumulador
- Documentar ventaja sobre recursión tradicional

### 4.9 Backtracking - Combinaciones

**Ubicación**: `ControladorBusqueda.java`

**Firma**:
```java
public static ArrayList<ArrayList<Casa>> encontrarCombinaciones(
    ArrayList<Casa> casas, 
    double presupuestoTotal, 
    int indice, 
    ArrayList<Casa> combinacionActual, 
    ArrayList<ArrayList<Casa>> todasCombinaciones)
```

**Requisitos**:
- Explorar incluir/no incluir cada casa
- Retroceder (remover de combinación) al explorar otra rama
- Documentar árbol de decisión
- Mostrar todas las soluciones válidas

---

## CHECKLIST DE IMPLEMENTACIÓN {#checklist}

### 5.1 Fase 1: Estructura Base

```
□ Crear estructura de directorios
  □ src/com/inmobiliaria/
  □ datos/
  □ bin/

□ Crear clase Main.java
  □ Punto de entrada del programa
  □ Llama a ControladorPrincipal

□ Crear interfaces básicas
  □ Definir flujo menú principal
  □ Implementar loop principal
```

### 5.2 Fase 2: Clases del Modelo

```
□ Casa.java
  □ Atributos definidos
  □ Constructor con validaciones
  □ Getters y setters
  □ equals() y compareTo()
  □ toString()
  □ JavaDoc completo

□ Usuario.java
  □ Atributos definidos
  □ Constructor con validaciones
  □ Métodos de preferencias
  □ toString()
  □ JavaDoc

□ Reserva.java
  □ Enum EstadoReserva
  □ Atributos y constructor
  □ Validaciones
  □ JavaDoc

□ Historial.java
  □ Enum TipoAccion
  □ Atributos y constructor
  □ JavaDoc

□ SistemaInmobiliario.java
  □ ArrayList de cada entidad
  □ Stack de búsquedas
  □ Queue de reservas
  □ Métodos CRUD básicos
  □ Métodos de búsqueda primitivos
  □ JavaDoc completo
```

### 5.3 Fase 3: Controladores

```
□ ControladorPrincipal.java
  □ Loop principal
  □ Coordinación de submenús
  □ Llamadas a otros controladores

□ ControladorBusqueda.java
  □ Búsqueda lineal (manual)
  □ Búsqueda binaria (manual)
  □ Búsqueda recursiva (manual)
  □ Fuerza bruta multi-filtro
  □ Backtracking (combinaciones)

□ ControladorReserva.java
  □ Crear reserva
  □ Validar disponibilidad
  □ Encolar en cola
  □ Procesar cola (FIFO)
  □ Recursión de cola

□ ControladorPersistencia.java
  □ Guardar casas en CSV
  □ Cargar casas desde CSV
  □ Guardar usuarios
  □ Cargar usuarios
  □ Guardar reservas
  □ Cargar reservas
  □ Guardar historial
  □ Cargar historial

□ ControladorHistorial.java
  □ Registrar acción
  □ Mostrar historial completo
  □ Mostrar últimas búsquedas (pila)
  □ Operaciones push/pop en pila

□ AlgoritmosOrdenamiento.java
  □ Burbuja ascendente/descendente
  □ Selección ascendente
  □ Inserción ascendente
  □ Merge Sort ascendente
  □ Documentación de complejidad
  □ Método comparador genérico
```

### 5.4 Fase 4: Vistas (Menús)

```
□ MenuPrincipal.java
  □ Menú inicial con 8+ opciones
  □ Recursión de menús si es necesario
  □ Validación de entrada
  □ Llamadas a submenús

□ MenuBusqueda.java
  □ Opciones de búsqueda
  □ Captura de criterios
  □ Llamadas a ControladorBusqueda

□ MenuReserva.java
  □ Selección de propiedad
  □ Ingreso de fechas
  □ Confirmación de reserva

□ MenuVisualizacion.java
  □ Mostrar lista de propiedades
  □ Mostrar detalles de una propiedad
  □ Formateo visual

□ MenuHistorial.java
  □ Mostrar historial completo
  □ Mostrar últimas búsquedas
  □ Mostrar reservas de usuario

□ ConsolaHelper.java
  □ Método para pedir string
  □ Método para pedir entero
  □ Método para pedir double
  □ Método para pedir selección (1-n)
  □ Método para mostrar menú
  □ Método para mostrar tabla
  □ Validación de entrada
```

### 5.5 Fase 5: Utilidades

```
□ Validador.java
  □ Validar email (contiene @)
  □ Validar teléfono (contiene dígitos)
  □ Validar número positivo
  □ Validar fecha válida
  □ Validar cadena no vacía

□ Formateador.java
  □ Formatear precio en moneda
  □ Formatear fecha/hora
  □ Centrar texto
  □ Crear líneas separadoras
```

### 5.6 Fase 6: Archivos de Datos

```
□ datos/casas.csv
  □ Crear archivo
  □ Agregar mínimo 10 propiedades de ejemplo
  □ Verificar formato correcto

□ datos/usuarios.csv
  □ Crear archivo
  □ Agregar mínimo 5 usuarios de ejemplo
  □ Verificar formato correcto

□ datos/reservas.csv
  □ Crear archivo (puede estar vacío al inicio)

□ datos/historial.csv
  □ Crear archivo (puede estar vacío al inicio)
```

### 5.7 Fase 7: Documentación

```
□ JavaDoc en todas las clases
  □ Descripción de clase
  □ @author
  □ @version

□ JavaDoc en todos los métodos públicos
  □ Descripción
  □ @param
  □ @return
  □ @throws

□ Comentarios en lógica compleja
  □ Algoritmos
  □ Validaciones críticas
  □ Casos especiales

□ README.md
  □ Descripción del proyecto
  □ Cómo compilar
  □ Cómo ejecutar
  □ Estructura de directorios
  □ Funcionalidades
  □ Técnicas aplicadas

□ Documento de análisis
  □ Ya completado en análisis técnico
```

### 5.8 Fase 8: Pruebas

```
□ Casos de prueba unitarios
  □ Crear casa válida/inválida
  □ Búsqueda lineal
  □ Búsqueda binaria (con lista ordenada)
  □ Ordenamientos

□ Casos de prueba de integración
  □ Flujo completo: registrar → buscar → reservar
  □ Persistencia: guardar → cargar
  □ Pila: push → pop
  □ Cola: enqueue → dequeue

□ Pruebas manuales por consola
  □ Todos los menús funcionan
  □ Validaciones funcionan
  □ Archivos se crean/cargan correctamente
  □ Búsquedas retornan resultados correctos
  □ Ordenamientos funcionan en ambas direcciones
  □ Cola procesa FIFO correcto
  □ Pila retorna LIFO correcto
```

---

## EJEMPLOS DE FLUJO DE DATOS {#flujos-datos}

### 6.1 Flujo: Usuario Busca por Ciudad

```
Main.java
  └─► ControladorPrincipal.mostrarMenuPrincipal()
        └─► Usuario selecciona opción 1 (Buscar)
          └─► MenuBusqueda.mostrarOpcionesBusqueda()
            └─► Usuario selecciona 1 (Por ciudad)
              └─► MenuBusqueda.capturarCiudad()
                └─► ConsolaHelper.pedirString("¿Qué ciudad?")
                  └─► Usuario ingresa "Bogota"
                    └─► ControladorBusqueda.buscarPorCiudad("Bogota")
                      └─► BusquedaLineal.busquedaLinealPorCiudad(
                            casas, 
                            "Bogota"
                          )
                        └─► Recorre cada casa:
                          ├─ Casa 1: ciudad.equalsIgnoreCase("Bogota") → True ✓
                          ├─ Casa 2: ciudad.equalsIgnoreCase("Bogota") → False
                          └─ Casa 3: ciudad.equalsIgnoreCase("Bogota") → True ✓
                          
                        └─► Retorna [Casa1, Casa3]
                      
                      └─► ControladorHistorial.registrarAccion(
                            BUSQUEDA, "Búsqueda por ciudad", "EXITOSA"
                          )
                      
                      └─► ControladorHistorial.agregarBusquedaReciente(
                            "Bogota"
                          )
                      
                      └─► MenuVisualizacion.mostrarResultados([Casa1, Casa3])
                        └─► Itera y formatea cada casa
                          └─► Muestra en consola
      
      └─► Loop continúa esperando siguiente opción
```

**Técnicas demostradas**:
- Entrada/salida (Scanner, printf)
- Cadenas (equalsIgnoreCase)
- Búsqueda lineal O(n)
- ArrayList iteración
- Pila (push)
- Historial

---

### 6.2 Flujo: Ordenar por Precio

```
Usuario selecciona "Ordenar propiedades"
  └─► MenuBusqueda.mostrarOpcionesOrdenamiento()
    └─► Usuario selecciona 1 (Por precio ascendente)
      └─► ControladorBusqueda.obtenerTodasCasas()
        └─► SistemaInmobiliario.obtenerTodasCasas()
          └─► Retorna ArrayList<Casa> con 10 elementos
        
      └─► AlgoritmosOrdenamiento.mergeSortAscendente(
            casas, "precio", 0, 9
          )
        └─► Caso base? No (0 < 9)
          └─► medio = 4
            └─► mergeSortAscendente(casas, 0, 4)
              └─► Recursión izquierda...
                └─► Retorna mitad izquierda ordenada
            
            └─► mergeSortAscendente(casas, 5, 9)
              └─► Recursión derecha...
                └─► Retorna mitad derecha ordenada
            
            └─► mezclar(casas, 0, 4, 9)
              └─► Compara precio de izquierda vs derecha
              └─► Ordena en un Array nuevo
              └─► Copia resultado a casas original
      
      └─► Retorna casas ordenadas por precio
    
    └─► MenuVisualizacion.mostrarResultados(casasOrdenadas)
      └─► Muestra en orden: precio más bajo primero
```

**Técnicas demostradas**:
- Recursión de división
- Merge Sort O(n log n)
- Comparadores
- ArrayList ordenado

---

### 6.3 Flujo: Procesar Cola de Reservas

```
Sistema al finalizar reserva:
  └─► ControladorReserva.crearReserva(1, 2, fecha, duracion)
    └─► SistemaInmobiliario.encolarReserva(reserva)
      └─► colaReservas.offer(reserva)
        └─► Cola ahora contiene 1 elemento
    
    └─► Simular procesamiento automático o manual
  
  └─► ControladorReserva.procesarColaReservas()
    └─► Mientras no esté vacía:
      └─► SistemaInmobiliario.colaReservas.poll()
        └─► Obtiene reserva del inicio (FIFO)
        └─► Valida:
          ├─ Usuario existe?
          ├─ Casa existe?
          ├─ Presupuesto >= precio?
          └─ Fecha válida?
      
      └─► Si todo válido:
        └─► reserva.setEstado(CONFIRMADA)
      └─► Si algo falla:
        └─► reserva.setEstado(CANCELADA)
      
      └─► ControladorHistorial.registrarAccion(
            RESERVA, "...", resultado
          )
      
      └─► Continúa con siguiente reserva en cola

  └─► Confirmación: "Se procesaron X reservas"
```

**Técnicas demostradas**:
- Cola FIFO (First In, First Out)
- poll() para desencolar
- Validaciones secuenciales
- Historial

---

### 6.4 Flujo: Búsqueda Binaria (con Ordenamiento Previo)

```
Usuario selecciona "Buscar por precio exacto"
  └─► ControladorBusqueda.buscarPorPrecioExacto(1200000)
    
    └─► Validar que casas están ordenadas por precio
      └─► Si NO:
        └─► AlgoritmosOrdenamiento.mergeSortAscendente(
              casas, "precio", 0, n-1
            )
        └─► Ordena lista
      └─► Si SÍ: continuar
    
    └─► BusquedaBinaria.busquedaBinariaPorPrecio(
          casasOrdenadas, 1200000
        )
      └─► bajo = 0, alto = 9
        └─► Iteración 1:
          ├─ medio = (0 + 9) / 2 = 4
          ├─ casas[4].precio = 1500000
          ├─ 1500000 > 1200000?
          └─ alto = 3 (buscar en mitad izquierda)
        
        └─► Iteración 2:
          ├─ bajo = 0, alto = 3
          ├─ medio = (0 + 3) / 2 = 1
          ├─ casas[1].precio = 800000
          ├─ 800000 < 1200000?
          └─ bajo = 2 (buscar en mitad derecha)
        
        └─► Iteración 3:
          ├─ bajo = 2, alto = 3
          ├─ medio = (2 + 3) / 2 = 2
          ├─ casas[2].precio = 1200000
          ├─ 1200000 == 1200000?
          └─ ¡ENCONTRÓ! Retorna casas[2]
    
    └─► MenuVisualizacion.mostrarDetalles(casa)
      └─► Muestra toda la información de la casa
```

**Técnicas demostradas**:
- Ordenamiento previo (Merge Sort)
- Búsqueda binaria O(log n)
- Dividir en mitades
- Validación de precondiciones

---

## CRITERIOS DE EVALUACIÓN ACADÉMICA {#criterios-evaluacion}

### 7.1 Criterios de Completitud Técnica

```
UNIDAD 1 - GENERALIDADES (25%)
├─ Entrada/salida consola (scanner, printf)        [✓ Debe implementarse]
├─ I/O de archivos (CSV, lectura/escritura)        [✓ Debe implementarse]
├─ Operaciones con cadenas                         [✓ Debe implementarse]
├─ Documentación JavaDoc                           [✓ Debe implementarse]
├─ ArrayList dinámico                              [✓ Debe implementarse]
├─ Pila (Stack) con LIFO                           [✓ Debe implementarse]
└─ Cola (Queue) con FIFO                           [✓ Debe implementarse]

UNIDAD 2 - TÉCNICAS FUNDAMENTALES (25%)
├─ Fuerza bruta con análisis O(n*m)                [✓ Debe implementarse]
├─ Recursión de pila                               [✓ Debe implementarse]
├─ Recursión de cola                               [✓ Debe implementarse]
├─ Recursión cruzada (mutua)                       [○ Opcional pero recomendado]
└─ Backtracking con árbol de decisión              [✓ Debe implementarse]

UNIDAD 3 - ALGORITMOS (30%)
├─ Búsqueda lineal O(n)                            [✓ Manual, sin Arrays.search()]
├─ Búsqueda binaria O(log n)                       [✓ Manual, con precondición]
├─ Burbuja sort O(n²)                              [✓ Manual, con comparaciones]
├─ Selección sort O(n²)                            [✓ Manual, buscar mínimo]
├─ Inserción sort O(n²)                            [✓ Manual, insertar ordenado]
└─ Merge sort O(n log n)                           [✓ Manual, divide y conquista]

ARQUITECTURA MVC (20%)
├─ Model (5 entidades mínimo)                      [✓ Debe implementarse]
├─ View (menús en consola)                         [✓ Debe implementarse]
├─ Controller (lógica de negocio)                  [✓ Debe implementarse]
└─ Separación clara de responsabilidades           [✓ Debe implementarse]
```

### 7.2 Criterios de Calidad de Código

```
DOCUMENTACIÓN:
├─ JavaDoc en todas las clases                     [✓ 100%]
├─ JavaDoc en métodos públicos                     [✓ 100%]
├─ Comentarios en lógica compleja                  [✓ Donde sea necesario]
└─ Explicación de algoritmos en comentarios        [✓ Complejidad O(n), casos]

NOMENCLATURA:
├─ Clases en PascalCase                            [✓]
├─ Métodos en camelCase                            [✓]
├─ Constantes en UPPER_SNAKE_CASE                  [✓]
├─ Variables descriptivas                          [✓]
└─ Nombres en español o inglés (consistente)       [✓]

ORGANIZACIÓN:
├─ Paquetes lógicos (model, view, controller)      [✓]
├─ Archivos separados por responsabilidad          [✓]
├─ Sin código duplicado                            [✓]
└─ Métodos pequeños y enfocados                    [✓]

VALIDACIONES:
├─ Constructor valida entrada                      [✓]
├─ Métodos validan precondiciones                  [✓]
├─ Errores informativos al usuario                 [✓]
└─ Manejo de excepciones apropiado                 [✓]
```

### 7.3 Criterios de Funcionalidad

```
BÚSQUEDAS:
├─ Búsqueda por ciudad retorna correctas           [✓]
├─ Búsqueda por precio retorna rango correcto      [✓]
├─ Búsqueda binaria requiere ordenamiento previo   [✓]
├─ Búsqueda con múltiples criterios retorna AND    [✓]
└─ Búsqueda recursiva funciona correctamente       [✓]

ORDENAMIENTOS:
├─ Burbuja ordena correctamente (ascendente)       [✓]
├─ Selección ordena correctamente                  [✓]
├─ Inserción ordena correctamente                  [✓]
├─ Merge sort ordena correctamente                 [✓]
└─ Pueden cambiar de ascendente a descendente      [✓]

PILAS:
├─ Push agrega búsqueda reciente                   [✓]
├─ Pop retorna búsqueda más reciente (LIFO)        [✓]
└─ Muestra últimas 5 búsquedas en orden LIFO       [✓]

COLAS:
├─ Enqueue agrega reserva al final                 [✓]
├─ Dequeue procesa de inicio (FIFO)                [✓]
├─ Reservas se procesan en orden llegada           [✓]
└─ Cola refleja estado real del sistema            [✓]

PERSISTENCIA:
├─ Guardar crea archivos CSV válidos               [✓]
├─ Cargar reconstruye estado anterior              [✓]
├─ Datos persisten entre sesiones                  [✓]
└─ Formato CSV es legible y editable               [✓]
```

### 7.4 Rúbrica de Evaluación Final

```
Escala: 0-100

TÉCNICAS DE PROGRAMACIÓN (40 puntos)
├─ Unidad 1 (Generalidades): 0-10
│  ├─ Entrada/salida (2)
│  ├─ Archivos (2)
│  ├─ Strings (2)
│  ├─ Documentación (2)
│  └─ Listas, pilas, colas (2)
├─ Unidad 2 (Técnicas): 0-15
│  ├─ Fuerza bruta (3)
│  ├─ Recursión (6)
│  └─ Backtracking (6)
└─ Unidad 3 (Algoritmos): 0-15
   ├─ Búsquedas (5)
   ├─ Ordenamientos (5)
   └─ Complejidad documentada (5)

ARQUITECTURA MVC (25 puntos)
├─ Model bien diseñado (8)
├─ View separada de lógica (8)
├─ Controller orquesta bien (5)
└─ Separación clara (4)

CALIDAD DE CÓDIGO (20 puntos)
├─ Documentación y comentarios (5)
├─ Nomenclatura consistente (5)
├─ Organización y estructura (5)
└─ Sin errores de compilación (5)

FUNCIONALIDAD (15 puntos)
├─ Sistema funciona sin crashes (5)
├─ Todas las operaciones funcionan (5)
└─ Interfaces intuitivas (5)

TOTAL: 100 puntos
```

---

**Fin del documento de requisitos técnicos y mapeo**
