# MAPEO: PROBLEMAS INMOBILIARIOS → TÉCNICAS DE PROGRAMACIÓN
## Justificación Académica del Contexto

---

## INTRODUCCIÓN

Este documento conecta **problemas reales que enfrenta una inmobiliaria** con **técnicas específicas de programación** que los resuelven, justificando por qué cada técnica es académicamente válida y práctica.

**Objetivo**: Demostrar que este NO es un proyecto artificial, sino una aplicación genuina de técnicas de programación a un dominio real.

---

## BLOQUE 1: PROBLEMAS DE GESTIÓN DE INVENTARIO

### 1.1 Problema Real
**"Tenemos 500 propiedades registradas. Cuando un cliente llama preguntando por apartamentos en Bogotá, nuestro personal debe buscar manualmente revisando archivos. Necesitamos automatizar."**

### 1.2 Técnica de Programación: BÚSQUEDA LINEAL
**Cómo se implementa**:
```
Para cada propiedad en la lista:
    Si ciudad de propiedad == "Bogota":
        Agregar a resultados
```

### 1.3 Justificación Académica
- **Complejidad**: O(n) donde n = cantidad de propiedades
- **Ventaja**: Funciona en listas desordenadas
- **Desventaja**: Lento en listas muy grandes
- **Por qué es académica**: Demuestra por qué existen algoritmos más eficientes
- **Aplicación en inmobiliaria**: Primera búsqueda, sin requisitos de ordenamiento

### 1.4 Implementación en Proyecto
```java
// ControladorBusqueda.java
public static ArrayList<Casa> busquedaLinealPorCiudad(
    ArrayList<Casa> casas, String ciudad) {
    ArrayList<Casa> resultados = new ArrayList<>();
    
    for (int i = 0; i < casas.size(); i++) {
        if (casas.get(i).getCiudad()
            .equalsIgnoreCase(ciudad)) {
            resultados.add(casas.get(i));
        }
    }
    
    return resultados; // O(n)
}
```

---

## BLOQUE 2: PROBLEMAS DE PRESENTACIÓN Y ORDENAMIENTO

### 2.1 Problema Real
**"Los clientes nos dicen: 'Quiero los apartamentos más baratos primero'. Actualmente mostramos en orden de registro. Necesitamos mostrar ordenados por precio."**

### 2.2 Técnicas de Programación: ALGORITMOS DE ORDENAMIENTO

#### Solución Simple: BURBUJA
**Implementación**:
```
Comparar pares adyacentes
Si están en orden incorrecto, intercambiar
Repetir hasta que toda la lista esté ordenada
```

**Complejidad**: O(n²)
- Mejor caso: O(n) si ya está ordenada
- Peor caso: O(n²) si está invertida
- Promedio: O(n²)

#### Solución Intermedia: INSERCIÓN
**Implementación**:
```
Para cada elemento:
    Encontrar posición correcta en lista ya ordenada
    Insertar elemento en esa posición
```

**Complejidad**: O(n²) peor, O(n) si casi está ordenado

#### Solución Eficiente: MERGE SORT
**Implementación**:
```
Dividir lista en mitades recursivamente
Ordenar cada mitad
Mezclar mitades ordenadas

Resultado: O(n log n) siempre
```

### 2.3 Justificación Académica
- **Burbuja**: Simple, enseña conceptos básicos, ineficiente en escala
- **Inserción**: Eficiente si datos son parcialmente ordenados, intuitivo
- **Merge Sort**: Eficiente siempre, demuestra divide y conquista
- **Comparación**: Enseña análisis de complejidad: cómo cambia con datos

### 2.4 Aplicación en Inmobiliaria

| Escenario | Algoritmo Recomendado | Razón |
|-----------|---|---|
| 10 propiedades | Burbuja | Simple, suficiente |
| 100 propiedades | Inserción | Más eficiente |
| 1000+ propiedades | Merge Sort | Garantiza O(n log n) |
| Datos casi ordenados | Inserción | Detecta ya está ordenado |

---

## BLOQUE 3: PROBLEMAS DE BÚSQUEDA EFICIENTE

### 3.1 Problema Real
**"Buscamos propiedades por precio exacto (ejemplo: $1.2M). La búsqueda lineal tarda 10 segundos en BD de 1M de propiedades. ¿Podemos hacerlo más rápido?"**

### 3.2 Técnica de Programación: BÚSQUEDA BINARIA

**Idea**:
```
Ordenar propiedades por precio una sola vez
Luego, buscar usando "divide y conquista"

Si precio objetivo está en mitad:
    Buscar en mitad derecha
Si no:
    Buscar en mitad izquierda

Resultado: O(log n) = 20 iteraciones para 1M elementos
vs. 500k iteraciones con búsqueda lineal
```

### 3.3 Justificación Académica
- **Precondición crítica**: Lista DEBE estar ordenada
- **Complejidad**: O(log n) vs O(n)
- **Trade-off**: Costo de ordenar una vez (O(n log n)) vs búsquedas múltiples
- **Aplicación**: Cuando hay muchas búsquedas del mismo tipo

### 3.4 Comparación Visual

```
Búsqueda Lineal:     [1] [2] [3] [4] [5] ... [1000000]
Para encontrar 1M:   ⬅ ⬅ ⬅ ⬅ ⬅ ⬅ ⬅ ⬅ ...   (500k comparaciones)

Búsqueda Binaria:    [1] [2] [3] [4] [5] ... [1000000]
                      └─────→ (divido)
                              
                     [500k-1M]
                     └─→ (divido)
                     
                     [750k-1M]
                     └→ (divido)
                     
Para encontrar 1M:   (20 comparaciones máximo)
```

### 3.5 Aplicación en Inmobiliaria

```
// ControladorBusqueda.java

// PASO 1: Ordenar una sola vez (Merge Sort O(n log n))
AlgoritmosOrdenamiento.mergeSortAscendente(casas, "precio", 0, n-1);

// PASO 2: Búsqueda binaria múltiples veces (cada una O(log n))
Casa casa1 = BusquedaBinaria.busquedaBinariaPorPrecio(casas, 1200000);
Casa casa2 = BusquedaBinaria.busquedaBinariaPorPrecio(casas, 800000);
Casa casa3 = BusquedaBinaria.busquedaBinariaPorPrecio(casas, 2500000);

// Costo total: O(n log n) + 3*O(log n)
// Sin búsqueda binaria sería: 3*O(n) = muy más lento
```

---

## BLOQUE 4: PROBLEMAS DE ACCESO A DATOS (I/O)

### 4.1 Problema Real
**"Cuando el sistema se apaga, pierden todos los datos de propiedades registradas. Necesitamos guardar información de forma persistente."**

### 4.2 Técnica de Programación: LECTURA/ESCRITURA DE ARCHIVOS

**Solución**:
```
Al cerrar programa:
    Guardar lista de casas en archivo casas.csv
    Guardar lista de usuarios en archivo usuarios.csv
    Guardar lista de reservas en archivo reservas.csv

Al abrir programa:
    Leer archivos CSV
    Recrear listas en memoria
    Sistema continúa con estado anterior
```

### 4.3 Justificación Académica
- **I/O**: Concepto fundamental en programación
- **Persistencia**: Datos viven más allá que el programa
- **Formato CSV**: Simple, legible, sin dependencias
- **Alternativa académica a BD**: Demuestra lectura/escritura manual

### 4.4 Aplicación en Inmobiliaria

```
FLUJO GUARDADO:
Casa.java, Usuario.java, Reserva.java
       │
       ▼
ControladorPersistencia.guardarEnArchivos()
       │
       ├─► casas.csv
       ├─► usuarios.csv
       ├─► reservas.csv
       └─► historial.csv

FLUJO CARGA:
ControladorPersistencia.cargarDesdeArchivos()
       │
       ├─► Lee casas.csv
       │   └─► Recrea ArrayList<Casa>
       │
       ├─► Lee usuarios.csv
       │   └─► Recrea ArrayList<Usuario>
       │
       ├─► Lee reservas.csv
       │   └─► Recrea ArrayList<Reserva>
       │
       └─► Lee historial.csv
           └─► Recrea ArrayList<Historial>
```

### 4.5 Formato CSV Específico

```
CASAS.CSV:
id,direccion,ciudad,precio,area,dormitorios,banos,disponible,descripcion,propietario
1,Cra 10 #25-45,Bogota,1500000,80,2,1,true,Apto moderno centro,Juan

USUARIOS.CSV:
id,nombre,email,telefono,presupuesto,activo
1,Ana López,ana@email.com,3001234567,5000000,true

RESERVAS.CSV:
id,idUsuario,idCasa,fechaReserva,fechaInicio,duracion,estado,observaciones
1,1,2,2024-01-15T10:30:00,2024-02-01,12,CONFIRMADA,

HISTORIAL.CSV:
id,idUsuario,tipo,descripcion,fecha,resultado
1,1,BUSQUEDA,Búsqueda por ciudad Bogota,2024-01-15T10:25:00,EXITOSA
```

---

## BLOQUE 5: PROBLEMAS CON BÚSQUEDAS COMPLEJAS

### 5.1 Problema Real
**"Un cliente dice: 'Quiero un apartamento en Bogotá, máximo $2M, con mínimo 2 dormitorios, que tenga agua caliente'. ¿Cómo buscamos eso?"**

### 5.2 Técnica de Programación: FUERZA BRUTA CON FILTROS

**Idea**:
```
Recorrer TODAS las propiedades
Para cada propiedad:
    ¿Es Bogotá? SI → continuar : NO → saltar
    ¿Precio <= 2M? SI → continuar : NO → saltar
    ¿Dormitorios >= 2? SI → continuar : NO → saltar
    ¿Tiene agua caliente en descripción? SI → agregar resultado
```

**Complejidad**: O(n * m) donde n = propiedades, m = filtros

### 5.3 Justificación Académica
- **Fuerza bruta**: Problemático pero correcto
- **Desventaja**: O(n*m) crece exponencialmente con filtros
- **Ventaja**: Funciona siempre, sin requisitos previos
- **Enseñanza**: Por qué índices y búsquedas optimizadas existen

### 5.4 Aplicación en Inmobiliaria

```java
// ControladorBusqueda.java

public static ArrayList<Casa> busquedaAvanzada(
    ArrayList<Casa> casas,
    String ciudad,
    double precioMax,
    int dormitoriosMin,
    String caracteristica) {
    
    ArrayList<Casa> resultados = new ArrayList<>();
    
    // Fuerza bruta: O(n*m)
    for (Casa casa : casas) {
        if (casa.getCiudad().equalsIgnoreCase(ciudad) &&
            casa.getPrecio() <= precioMax &&
            casa.getDormitorios() >= dormitoriosMin &&
            casa.getDescripcion().toLowerCase()
                .contains(caracteristica.toLowerCase())) {
            
            resultados.add(casa);
        }
    }
    
    return resultados;
}
```

**Análisis**:
```
n = 1000 propiedades
m = 4 filtros (ciudad, precio, dormitorios, característica)

Peor caso: O(1000 * 4) = 4000 comparaciones
Si tuviéramos 10 filtros: O(1000 * 10) = 10,000 comparaciones

Vs. Búsqueda binaria (si lista está indexada):
O(log 1000) = 10 comparaciones

Conclusión: Para búsquedas frecuentes con muchos filtros,
habría que crear índices (base de datos). Pero para 
inmobiliaria pequeña, fuerza bruta es acceptable.
```

---

## BLOQUE 6: PROBLEMAS DE HISTORIAL Y DESHACER

### 6.1 Problema Real
**"El usuario quiere ver sus últimas 5 búsquedas. Cuando intenta deshacer, quiere volver a la búsqueda anterior."**

### 6.2 Técnica de Programación: PILA (STACK) - LIFO

**Idea**:
```
Búsquedas del usuario:
  08:00 → Búsqueda #1: "apartamentos Bogotá"
  08:05 → Búsqueda #2: "casas Cartagena"
  08:10 → Búsqueda #3: "fincas Medelín"  ← Última (tope de pila)

Usuario dice "Ver últimas búsquedas":
  Pop → "fincas Medelín"       (más reciente)
  Pop → "casas Cartagena"
  Pop → "apartamentos Bogotá"  (más antigua)

Usuario dice "Deshacer":
  Pop → Volver a búsqueda anterior
```

### 6.3 Justificación Académica
- **Estructura LIFO**: Last In, First Out
- **Aplicación real**: Undo en editores, browser back button, llamadas recursivas
- **Stack overflow**: Cuando se llena, memoria se agota (importante entender)
- **Implementación**: ArrayList con métodos push/pop

### 6.4 Aplicación en Inmobiliaria

```java
// SistemaInmobiliario.java

private Stack<String> pilaSearches = new Stack<>();

// Cuando usuario busca:
public void agregarBusquedaReciente(String busqueda) {
    pilaSearches.push(busqueda);  // O(1)
}

// Cuando usuario pide "últimas búsquedas":
public void mostrarUltimasBusquedas() {
    Stack<String> temp = pilaSearches.clone();
    
    while (!temp.isEmpty()) {
        String busqueda = temp.pop();  // O(1)
        System.out.println(busqueda);
    }
}

// Cuando usuario dice "deshacer":
public String obtenerBusquedaAnterior() {
    if (!pilaSearches.isEmpty()) {
        return pilaSearches.pop();  // O(1)
    }
    return null;
}
```

**Visualización de Stack**:
```
ANTES: pilaSearches
┌──────────────────┐
│ "fincas Medelín" │ ← tope (top)
├──────────────────┤
│ "casas Cartagena"│
├──────────────────┤
│ "apartamentos"   │
└──────────────────┘

DESPUÉS DE pop():
┌──────────────────┐
│ "casas Cartagena"│ ← nuevo tope
├──────────────────┤
│ "apartamentos"   │
└──────────────────┘

Devolvió: "fincas Medelín"
```

---

## BLOQUE 7: PROBLEMAS DE PROCESAMIENTO EN ORDEN FIFO

### 7.1 Problema Real
**"Múltiples clientes intentan reservar la misma propiedad simultáneamente. La inmobiliaria debe procesarlas en orden: primera reserva gana."**

### 7.2 Técnica de Programación: COLA (QUEUE) - FIFO

**Idea**:
```
Reservas llegan en orden:
  08:00 → Cliente A: Casa ID=5
  08:05 → Cliente B: Casa ID=5
  08:10 → Cliente C: Casa ID=7

Cola procesa en orden FIFO:
  1. Procesa Cliente A (entra primero, sale primero)
     ✓ Reserva confirmada
  2. Procesa Cliente B 
     ✗ Casa ya no disponible
  3. Procesa Cliente C
     ✓ Reserva confirmada
```

### 7.3 Justificación Académica
- **Estructura FIFO**: First In, First Out
- **Aplicación real**: Colas en bancos, call centers, impresas (spooler)
- **Justicia**: Quien llega primero se atiende primero
- **Implementación**: Queue con LinkedList, métodos offer/poll

### 7.4 Aplicación en Inmobiliaria

```java
// SistemaInmobiliario.java

private Queue<Reserva> colaReservas = new LinkedList<>();

// Cuando cliente hace reserva:
public void encolarReserva(Reserva reserva) {
    colaReservas.offer(reserva);  // Agrega al final, O(1)
}

// Procesar cola secuencial:
public void procesarColaReservas() {
    while (!colaReservas.isEmpty()) {
        Reserva reserva = colaReservas.poll();  // Quita del inicio, O(1)
        
        // Validar
        if (esReservaValida(reserva)) {
            reserva.setEstado(EstadoReserva.CONFIRMADA);
        } else {
            reserva.setEstado(EstadoReserva.CANCELADA);
        }
        
        registrarEnHistorial(reserva);
    }
}
```

**Visualización de Queue**:
```
RESERVAS ENTRANDO (Enqueue):
└─ Entrada: A → B → C → D
              ↓
         ┌─────────────────┐
         │ A │ B │ C │ D   │
         └─────────────────┘

PROCESAMIENTO (Dequeue):
         ┌─────────────────┐
         │ A │ B │ C │ D   │
         └─────────────────┘
          ↓
    Procesar A (salida)
         ┌─────────────┐
         │ B │ C │ D   │
         └─────────────┘
          ↓
    Procesar B (salida)
         ┌───────┐
         │ C │ D │
         └───────┘
```

---

## BLOQUE 8: PROBLEMAS DE RECURSIÓN Y NAVEGACIÓN

### 8.1 Problema Real
**"El menú tiene submenús que tienen submenús. Necesitamos navegar hacia adentro (profundidad) y luego salir."**

### 8.2 Técnica de Programación: RECURSIÓN

**Idea**:
```
mostrarMenu():
    Mostrar opciones
    Usuario elige
    
    Si "Buscar propiedad":
        → mostrarSubmenúBusqueda()
            Mostrar tipos de búsqueda
            Usuario elige
            
            Si "Por ciudad":
                → mostrarCiudades()
                    Usuario elige ciudad
                    Retorna resultados
                ← Vuelve a mostrarSubmenúBusqueda
        ← Vuelve a mostrarMenu
    
    Si "Salir":
        Termina
```

### 8.3 Justificación Académica
- **Recursión**: Función que se llama a sí misma
- **Caso base**: Condición para dejar de llamarse
- **Caso recursivo**: Hacer trabajo y llamarse con problema menor
- **Stack trace**: Pila de llamadas en memoria (por qué tiene límite)

### 8.4 Aplicación en Inmobiliaria

```java
// MenuPrincipal.java

public void mostrarMenu() {
    // Caso base: usuario quiere salir
    if (usuarioQuiereSalir) {
        return;  // Termina recursión
    }
    
    // Presentar opciones
    System.out.println("=== MENÚ PRINCIPAL ===");
    System.out.println("1. Buscar propiedad");
    System.out.println("2. Ver historial");
    System.out.println("3. Salir");
    
    int opcion = scanner.nextInt();
    
    // Caso recursivo: procesar opción
    if (opcion == 1) {
        mostrarMenuBusqueda();  // Llamada recursiva
    } else if (opcion == 2) {
        mostrarMenuHistorial();  // Otra recursión
    } else if (opcion == 3) {
        usuarioQuiereSalir = true;
    }
    
    // Volver a mostrar este menú
    mostrarMenu();  // Recursión: vuelve a empezar
}

public void mostrarMenuBusqueda() {
    System.out.println("=== BÚSQUEDA ===");
    System.out.println("1. Por ciudad");
    System.out.println("2. Atrás");
    
    int opcion = scanner.nextInt();
    
    if (opcion == 1) {
        String ciudad = scanner.nextLine();
        // Buscar
    } else if (opcion == 2) {
        return;  // Vuelve a menú anterior (fin de esta recursión)
    }
    
    mostrarMenuBusqueda();  // Recursión en mismo menú
}
```

**Stack trace visual**:
```
mostrarMenu()
  └─ Usuario selecciona "Buscar"
    └─► mostrarMenuBusqueda()
          └─ Usuario selecciona "Por ciudad"
            └─► buscarPorCiudad()
                  └─ Retorna resultados
                └─► mostrarMenuBusqueda() (llama de nuevo)
                  └─ Usuario selecciona "Atrás"
                  └─► return (fin mostrarMenuBusqueda)
          └─► mostrarMenu() (llama de nuevo)
              └─ Usuario selecciona "Salir"
              └─► return (fin mostrarMenu)

Terminó programa
```

---

## BLOQUE 9: PROBLEMAS DE COMBINATORIA - BACKTRACKING

### 9.1 Problema Real
**"Cliente tiene presupuesto de $4M para alquilar múltiples propiedades (roommates). Queremos mostrar TODAS las combinaciones posibles que encajen."**

### 9.2 Técnica de Programación: BACKTRACKING

**Idea**:
```
Explorar cada propiedad:
    Opción 1: Incluir esta propiedad
        Si cabe en presupuesto:
            Agregar a combinación actual
            Explorar resto de propiedades
            RETROCEDER: remover de combinación
    
    Opción 2: No incluir esta propiedad
        Explorar resto de propiedades

Resultado: Todas las combinaciones válidas encontradas
```

### 9.3 Justificación Académica
- **Backtracking**: Explorar árbol de posibilidades
- **Poda**: Si rama no funciona, descartarla
- **Complejidad**: O(2^n) peor caso, pero poda puede reducir
- **Aplicación**: Puzzle solving, planificación, combinatoria

### 9.4 Aplicación en Inmobiliaria

```java
// ControladorBusqueda.java

public void encontrarCombinacionesPresupuesto(
    ArrayList<Casa> casas,
    double presupuestoTotal) {
    
    ArrayList<ArrayList<Casa>> todasCombinaciones = new ArrayList<>();
    ArrayList<Casa> combinacionActual = new ArrayList<>();
    
    backtracking(casas, presupuestoTotal, 0, 
                 combinacionActual, todasCombinaciones);
    
    // Mostrar resultados
    mostrarCombinaciones(todasCombinaciones);
}

private void backtracking(
    ArrayList<Casa> casas,
    double presupuestoRestante,
    int indice,
    ArrayList<Casa> combinacionActual,
    ArrayList<ArrayList<Casa>> todasCombinaciones) {
    
    // Caso base: revisamos todas las propiedades
    if (indice >= casas.size()) {
        if (combinacionActual.size() > 0) {
            todasCombinaciones.add(new ArrayList<>(combinacionActual));
        }
        return;
    }
    
    Casa casaActual = casas.get(indice);
    
    // Opción 1: INCLUIR esta casa
    if (presupuestoRestante >= casaActual.getPrecio()) {
        combinacionActual.add(casaActual);  // Agregar
        
        backtracking(casas,
                    presupuestoRestante - casaActual.getPrecio(),
                    indice + 1,
                    combinacionActual,
                    todasCombinaciones);
        
        combinacionActual.remove(casaActual);  // RETROCESO
    }
    
    // Opción 2: NO INCLUIR esta casa
    backtracking(casas,
                presupuestoRestante,
                indice + 1,
                combinacionActual,
                todasCombinaciones);
}
```

**Árbol de decisión (3 casas, presupuesto $4M)**:
```
                           INICIO
                          /      \
                   Incluir C1    No C1
                    $3M left      $4M left
                    /     \        /    \
                +C2    -C2    +C2    -C2
                $1M    $3M    $2M    $4M
               /  \    / \    / \    / \
            +C3  -C3 +C3 -C3 +C3 -C3 +C3 -C3

Combinaciones válidas encontradas:
  • {C1, C2} = $2M ✓
  • {C1, C3} = $2.5M ✓
  • {C2, C3} = $2M ✓
  • {C1, C2, C3} = $3.5M ✓
```

---

## BLOQUE 10: PROBLEMAS DE VALIDACIÓN - OPERACIONES CON CADENAS

### 10.1 Problema Real
**"Clientes ingresan emails inválidos. Necesitamos validar que sean correctos."**

### 10.2 Técnica de Programación: OPERACIONES CON CADENAS

**Validación de email**:
```
Regla 1: Debe contener @
Regla 2: Debe tener dominio después @
Regla 3: Dominio debe tener punto (.)

Usuario ingresa: "ana@email.com"
  ✓ Contiene @ (posición 3)
  ✓ Después de @ hay "email.com"
  ✓ "email.com" contiene . (posición 5)
  → Válido
```

### 10.3 Justificación Académica
- **Cadenas**: Datos fundamentales
- **Métodos String**: indexOf, contains, substring, length, etc.
- **Validación**: Entrada confiable es clave
- **Preparación**: Normalizar mayúsculas/minúsculas

### 10.4 Aplicación en Inmobiliaria

```java
// Validador.java

public static boolean esEmailValido(String email) {
    // Regla 1: Contiene @
    int posicionArroba = email.indexOf('@');
    if (posicionArroba == -1) {
        return false;  // No tiene @
    }
    
    // Regla 2: @ no está al inicio ni al final
    if (posicionArroba == 0 || posicionArroba == email.length() - 1) {
        return false;
    }
    
    // Regla 3: Después de @ hay un punto
    String dominio = email.substring(posicionArroba + 1);
    if (!dominio.contains(".")) {
        return false;
    }
    
    // Regla 4: El punto no está al inicio ni al final del dominio
    int posicionPunto = dominio.indexOf('.');
    if (posicionPunto == 0 || posicionPunto == dominio.length() - 1) {
        return false;
    }
    
    return true;
}

public static String normalizarBusqueda(String busqueda) {
    // Trim: elimina espacios inicio/final
    // toLowerCase: convierte a minúsculas para comparación
    return busqueda.trim().toLowerCase();
}

public static boolean buscaCoincide(String casa, String criterio) {
    // Usar contains para búsqueda flexible
    return casa.toLowerCase().contains(
        criterio.toLowerCase()
    );
}
```

**Ejemplos**:
```
validar("ana@email.com")     → true ✓
validar("ana.email.com")     → false (sin @)
validar("@email.com")        → false (@ al inicio)
validar("ana@.com")          → false (@ sin nombre usuario)
validar("ana@emailcom")      → false (dominio sin punto)

normalizar("  BOGOTA  ")     → "bogota"
coincide("Apartamento tipo ABC", "ABC") → true
```

---

## BLOQUE 11: PROBLEMAS DE DOCUMENTACIÓN

### 11.1 Problema Real
**"Nuevo programador se une al equipo. Necesita entender el código sin pasar días leyéndolo."**

### 11.2 Técnica de PROGRAMACIÓN: DOCUMENTACIÓN JAVADOC

**Idea**:
```
/**
 * Documentación de método
 * 
 * @param nombre_parametro descripción
 * @return descripción de lo que retorna
 */
public tipo metodo(tipo nombre) {
    // código
}
```

### 11.3 Justificación Académica
- **Documentación**: Código autodocumentado
- **Mantenibilidad**: Futuro programador entiende rápido
- **Contrato**: Qué espera el método, qué retorna
- **Buenas prácticas**: Standard en industria profesional

### 11.4 Aplicación en Inmobiliaria

```java
/**
 * Busca propiedades disponibles en una ciudad específica.
 * 
 * Implementa búsqueda lineal para iterar todas las propiedades
 * y retorna las que coinciden con la ciudad buscada.
 * 
 * Complejidad: O(n) donde n es cantidad de propiedades
 * 
 * @param casas ArrayList que contiene todas las propiedades registradas
 * @param ciudad Nombre de la ciudad a buscar (ej: "Bogota")
 * @return ArrayList con las propiedades que coinciden. Si no hay,
 *         retorna lista vacía (no null)
 * 
 * @example
 *   ArrayList<Casa> resultado = busquedaLinealPorCiudad(casas, "Bogota");
 *   // resultado contiene todas las casas en Bogota
 */
public static ArrayList<Casa> busquedaLinealPorCiudad(
    ArrayList<Casa> casas, 
    String ciudad) {
    // ... implementación ...
}
```

---

## RESUMEN FINAL: MAPEO COMPLETO

| # | Problema Real | Técnica | Complejidad | Implementación |
|---|---|---|---|---|
| 1 | Buscar propiedades por ciudad | Búsqueda Lineal | O(n) | ControladorBusqueda |
| 2 | Presentar ordenado por precio | Algoritmos ordenamiento | O(n²) a O(n log n) | AlgoritmosOrdenamiento |
| 3 | Búsqueda eficiente precio exacto | Búsqueda Binaria | O(log n) | ControladorBusqueda |
| 4 | Persistencia de datos | I/O Archivos CSV | O(n) | ControladorPersistencia |
| 5 | Búsqueda con múltiples filtros | Fuerza Bruta | O(n*m) | ControladorBusqueda |
| 6 | Ver últimas búsquedas | Pila (Stack) LIFO | O(1) | ControladorHistorial |
| 7 | Deshacer última acción | Pila (Stack) | O(1) | ControladorHistorial |
| 8 | Procesar reservas en orden | Cola (Queue) FIFO | O(1) | ControladorReserva |
| 9 | Navegación menús jerárquicos | Recursión | O(profundidad) | MenuPrincipal |
| 10 | Encontrar combinaciones | Backtracking | O(2^n) poda | ControladorBusqueda |
| 11 | Validar emails | Operaciones Cadenas | O(longitud) | Validador |
| 12 | Código mantenible | Documentación JavaDoc | N/A | Todas las clases |

---

## CONCLUSIÓN

Cada técnica de programación implementada en este proyecto **resuelve un problema real** que una inmobiliaria enfrenta. No es un ejercicio artificial:

- **Búsquedas** → Encontrar propiedades rápidamente
- **Ordenamientos** → Mostrar las mejores opciones primero
- **I/O de archivos** → Guardar datos entre sesiones
- **Pilas y colas** → Gestionar órdenes de llegada
- **Recursión** → Navegar menús jerárquicos
- **Backtracking** → Encontrar todas las opciones
- **Documentación** → Mantener código profesional

El proyecto es **académicamente riguroso** porque enseña técnicas fundamentales mientras las **aplica contextualmente** a un dominio real comprensible.

---

**Fin del documento de mapeo**
