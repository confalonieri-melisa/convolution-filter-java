# 🧵 Convolution Filter - Concurrent Image Processing
*This Java project implements a multithreaded image processing pipeline using convolution filters. It explores concurrency patterns, task coordination, and synchronization strategies while processing images through multiple filtering stages. Developed as part of the Concurrent Programming course at UNQ.*

*Below is a brief description in Spanish*

## 📚 Descripción del proyecto
Este repositorio contiene un trabajo práctico grupal realizado para la materia Programación Concurrente de la Universidad Nacional de Quilmes (UNQ).

El sistema procesa imagenes mediante la aplicación de filtros de convolución organizados en un pipeline de tres etapas, donde cada filtro transforma la imagen y pasa su resultado a la siguiente fase.

Para aprovechar al máximo el procesamiento paralelo, se hace uso de estrategias de concurrencia que permiten coordinar y sincronizar eficientemente la ejecución de tareas. Entre ellas destacan:

* Uso del patrón Productor-Consumidor
* Administración de workers mediante pools de threads
* Uso de monitores para sincronización
* Coordinación de tareas con dependencias entre regiones

El repositorio también incluye un [informe técnico](docs/Informe%20-%20Convolution.pdf) que documenta las decisiones de diseño, la arquitectura del sistema y una evaluación comparativa entre la ejecución secuencial y concurrente, con el objetivo de analizar su comportamiento y rendimiento.

## 🛠️ Tecnologías
* Java 21
* Java Standard Library (java.awt, javax.imageio)
  
## 🚀 Cómo ejecutar
1. Clonar el repositorio
```bash
git clone https://github.com/confalonieri-melisa/convolution-filter-java
```
2. Ejecutar el proyecto
* Ejecutar ``Main`` para experimentar con distintos parámetros, filtros e imagenes personalizados.
* Ejecutar ``PerformanceTest`` para replicar las pruebas documentadas en el informe.
