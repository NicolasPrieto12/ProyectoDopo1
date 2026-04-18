# Stacking Items - Proyecto DOPO/POOB

## Descripción

Simulador del problema **Stacking Cups** (Problem J - Maratón Internacional de Programación ICPC 2025).  
Permite apilar tazas y tapas de diferentes tipos en una torre, visualizando el resultado gráficamente.

---

## Autores

| Nombre | Rol |
|--------|-----|
| **Nicolás Prieto** | Desarrollador |
| **Sebastian Peña** | Desarrollador |

Escuela Colombiana de Ingeniería Julio Garavito  
Desarrollo Orientado por Objetos (DOPO/POOB) — 2026-1

---

## Estructura del Proyecto

```
ProyectoDopo1/
├── shapes/                  # Paquete de figuras geométricas
│   ├── ShapeBase.java       # Clase abstracta base
│   ├── Canvas.java          # Ventana gráfica (singleton)
│   ├── Rectangle.java
│   ├── Circle.java
│   └── Triangle.java
├── tower/                   # Paquete principal del simulador
│   ├── Cup.java             # Taza normal
│   ├── OpenerCup.java       # Taza que elimina tapas al entrar
│   ├── HierarchicalCup.java # Taza que desplaza menores
│   ├── Lid.java             # Tapa normal
│   ├── FearfulLid.java      # Tapa miedosa
│   ├── CrazyLid.java        # Tapa que va a la base
│   ├── StickyLid.java       # Tapa pegajosa (tipo propuesto por el equipo)
│   ├── Tower.java           # Torre principal
│   ├── TowerC4Test.java     # Pruebas unitarias ciclo 4
│   ├── TowerCC4Test.java    # Pruebas comunes ciclo 4
│   └── TowerAtest.java      # Pruebas de aceptación
├── TowerContest.java        # Solver del problema de maratón
└── TowerC2Test.java         # Pruebas ciclo 2
```

---

## Tipos de Elementos

### Tazas

| Tipo | Color | Comportamiento |
|------|-------|----------------|
| `normal` | Rojo/Verde/Amarillo/Azul/Magenta | Comportamiento estándar |
| `opener` | Naranja | Al entrar, elimina **todas** las tapas de la torre |
| `hierarchical` | Cyan | Desplaza tazas de menor número; si llega al fondo no se puede remover |

### Tapas

| Tipo | Color | Comportamiento |
|------|-------|----------------|
| `normal` | Negro | Comportamiento estándar |
| `fearful` | Rosa | No entra si su taza no está en la torre; no sale si está tapando su taza |
| `crazy` | Verde | Se ubica en la **base** de la torre en lugar de tapar su taza |
| `sticky` ⭐ | Magenta | Al ser removida, se adhiere a la siguiente taza disponible |

> ⭐ Tipo propuesto por el equipo

---

## Cómo Usar (BlueJ)

### Crear una torre
```java
Tower t = new Tower(4);       // Torre con 4 tazas normales
Tower t = new Tower(9, 100);  // Torre con ancho 9 y altura máxima 100
```

### Agregar tazas
```java
t.pushCup(3);                     // Taza normal número 3
t.pushCup("opener", 5);           // Taza opener número 5
t.pushCup("hierarchical", 3);     // Taza hierarchical número 3
```

### Agregar tapas
```java
t.pushLid(2);                     // Tapa normal para taza 2
t.pushLid("fearful", 2);          // Tapa fearful para taza 2
t.pushLid("crazy", 1);            // Tapa crazy para taza 1
t.pushLid("sticky", 1);           // Tapa sticky para taza 1
```

### Operaciones
```java
t.makeVisible();                                              // Mostrar torre
t.cover();                                                    // Cubrir tazas con tapas sueltas
t.orderTower();                                               // Ordenar de mayor a menor
t.reverseTower();                                             // Invertir orden
t.swap(new String[]{"cup","4"}, new String[]{"lid","2"});    // Intercambiar elementos
t.height();                                                   // Altura de la torre
t.lidedCups();                                                // Números de tazas tapadas
t.stackingItems();                                            // Todos los elementos
t.swapToReduce();                                             // Sugerir swap que reduce altura
t.ok();                                                       // Verificar si está ordenada
```

### TowerContest
```java
TowerContest.solve(3, 4);     // Retorna "POSSIBLE" o "IMPOSSIBLE"
TowerContest.simulate(3, 4);  // Visualiza la solución gráficamente
```

---

## Análisis Dinámico (JaCoCo)

### Resultado Inicial — Ciclo 4

Pruebas ejecutadas: **26/26 ✅**  
Cobertura de código de dominio: **69.7%**

![Reporte JaCoCo Inicial](docs/jacoco-initial.png)

| Clase | Cobertura |
|-------|-----------|
| Tower | 69.6% |
| Cup | 71.8% |
| Lid | 46.8% |
| OpenerCup | 100% |
| HierarchicalCup | 100% |
| FearfulLid | 77.8% |
| CrazyLid | 77.8% |
| StickyLid | 77.8% |
| **Total dominio** | **69.7%** |

**Decisiones tomadas tras el resultado inicial:**
- `Lid` tiene solo 46.8% — se agregarán pruebas para `setPosition`, `setSize`, `setColor` y `makeVisible/makeInvisible`
- `Tower` tiene 69.6% — se agregarán pruebas para `reverseTower`, `removeLid`, `cover` con múltiples tapas y casos borde de `swapToReduce`
- `Cup` tiene 71.8% — se agregarán pruebas para `getXPos`, `getYPos` y `getWidth`
- Meta ciclo 5: superar **75%** de cobertura

### Resultado Final — Ciclo 5

> *Se actualizará al completar el ciclo 5*

---

## Análisis Estático (PMD)

### Resultado Inicial — Ciclo 4

> *Se ejecutará al iniciar el ciclo 5 en IntelliJ*

### Resultado Final — Ciclo 5

> *Se actualizará al completar el ciclo 5*

---

## Pruebas

| Clase | Tipo | Cantidad |
|-------|------|----------|
| `TowerC2Test` | Unitarias ciclo 2 | 8 pruebas |
| `TowerC4Test` | Unitarias ciclo 4 | 14 pruebas |
| `TowerCC4Test` | Comunes ciclo 4 | 12 pruebas |
| `TowerAtest` | Aceptación ciclo 4 | 2 pruebas |
| `TowerContestTest` | Unitarias TowerContest | 9 pruebas |
| `TowerContestCTest` | Comunes TowerContest | 12 pruebas |

---

## Retrospectivas

### Ciclo 4

**1. ¿Qué hicimos bien?**  
Estructuramos el proyecto en dos paquetes aplicando herencia con `ShapeBase`. Los nuevos tipos quedaron visualmente distinguibles y con comportamientos bien definidos.

**2. ¿Qué no hicimos bien?**  
No teníamos claro desde el inicio cómo manejar la lógica de `HierarchicalCup` dentro de `Tower` sin romper encapsulación.

**3. ¿Qué debemos mejorar?**  
Diseñar el diagrama de clases completo antes de codificar.

**4. ¿Qué aprendimos?**  
Uso efectivo de herencia y diseño de subclases con comportamientos polimórficos. Importancia del diagrama de paquetes.

**5. ¿Qué obstáculos encontramos?**  
Conflicto de nombres entre `shapes.Shape` y `java.awt.Shape`. Integración entre paquetes en BlueJ requirió nombres completamente calificados.

**6. ¿Cómo resolvimos los obstáculos?**  
Renombramos la clase abstracta a `ShapeBase` y usamos `shapes.Rectangle` con nombre calificado en el paquete `tower`.

**7. ¿Qué tan satisfechos estamos?**  
Bastante satisfechos. 26/26 pruebas en verde y comportamientos visualmente distinguibles.

**8. ¿Qué haríamos diferente?**  
Diseñar el diagrama de paquetes completo desde el inicio y proponer el nuevo tipo desde el diseño, no al final.

---

### Ciclo 3

**1. ¿Qué hicimos bien?** Implementamos `TowerContest` con `solve()` y `simulate()` correctamente cubriendo todos los casos del problema de maratón.  
**2. ¿Qué no hicimos bien?** Las pruebas iniciales tenían casos incorrectos que tuvimos que corregir en commits posteriores.  
**3. ¿Qué debemos mejorar?** Verificar los casos de prueba contra el enunciado antes de hacer commit.  
**4. ¿Qué aprendimos?** A separar la lógica de resolución de la lógica de simulación visual.  
**5. ¿Qué obstáculos encontramos?** Calcular los anchos proporcionalmente para que la simulación se vea bien en el canvas.  
**6. ¿Cómo resolvimos?** Escalando el ancho máximo disponible proporcionalmente al número de tazas.  
**7. ¿Qué tan satisfechos estamos?** Satisfechos con la solución del problema de maratón.  
**8. ¿Qué haríamos diferente?** Escribir las pruebas antes del código (TDD).

---

### Ciclo 2

**1. ¿Qué hicimos bien?** Implementamos `swap`, `cover` y `swapToReduce` exitosamente con pruebas completas.  
**2. ¿Qué no hicimos bien?** El posicionamiento visual con `updatePositions` fue difícil de depurar.  
**3. ¿Qué debemos mejorar?** Documentar mejor los métodos desde el inicio del ciclo.  
**4. ¿Qué aprendimos?** Manejo de listas de objetos heterogéneos con `instanceof`.  
**5. ¿Qué obstáculos encontramos?** `updatePositions` requirió múltiples iteraciones para funcionar correctamente.  
**6. ¿Cómo resolvimos?** Usando índices de slot y calculando posiciones relativas con deltas.  
**7. ¿Qué tan satisfechos estamos?** Satisfechos con la funcionalidad lograda.  
**8. ¿Qué haríamos diferente?** Aplicar TDD desde el inicio del proyecto.

---

## Requisitos

- Java 11+
- BlueJ 5.x (ciclos 1-4) / IntelliJ IDEA (ciclo 5)
- JUnit 4.12
- JaCoCo 0.8.12 (análisis de cobertura)
