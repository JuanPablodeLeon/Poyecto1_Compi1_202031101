# Poyecto1_Compi1_202031101 - Manual de Usuario
---

Aplicacion de Android para poder crear formularios a base de codigo


---

Tipos de Datos

### number
Almacena valores numéricos (enteros o decimales)

    number edad = 25

### string
Almacena texto

    string nombre = "Juan"

---

## Expresiones

### Operadores aritméticos

+  -  *  /  ^  %

### Operadores de comparación

>  >=  <  <=  ==  !!

### Operadores lógicos

||  (OR)
&&  (AND)
~   (NOT)

A tomar en cuanta que no se pueden mezclar los operadores logicos
---

## Comentarios

### Una línea
    $ Esto es un comentario

### Múltiples líneas
    /*
       Comentario largo
    */

---

## Colores

Formatos válidos:

    #FFFFFF
    (10,10,10)
    <10,10,10>
    RED, BLUE, 

---

## Section

    SECTION [
        width: 100,
        height: 200,
        pointX: 0,
        pointY: 0,
        orientation: VERTICAL,

        elements: {
            TEXT [...],
            OPEN_QUESTION [...]
        },

        styles [
            "color": RED,
            "background color": WHITE,
            "text size": 12
        ]
    ]

---

## Table

    TABLE [
        width: 100,
        height: 100,
        pointX: 0,
        pointY: 0,

        elements: {
            [
                { TEXT [...] },
                { TEXT [...] }
            ],
            [
                { TEXT [...] },
                { TEXT [...] }
            ]
        }
    ]

---

## Text

    TEXT [
        content: "Hola mundo",
        styles [
            "color": BLUE
        ]
    ]

---

## Bloques de Código

### IF
    IF (10 > 5) {
        TEXT [...]
    } ELSE {
        TEXT [...]
    }
    
---

## Ejemplo de Uso

    number width = 100

    SECTION [
        width: width,
        height: 200,
        pointX: 0,
        pointY: 0,

        elements: {
            TEXT [
                content: "Bienvenido [:smile:]"
            ]
        }
    ]

