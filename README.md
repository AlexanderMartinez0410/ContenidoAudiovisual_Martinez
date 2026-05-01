# Sistema de Gestion de Contenido Audiovisual

## Descripcion del Problema o de la Actividad
El presente trabajo tiene como proposito el ampliar un proyecto Java preexistente, tomando como punto de partida el codigo base correspondiente al Ejemplo 30 de la Unidad 2, disponible en el repositorio oficial de la asignatura. La ampliacion implica la incorporacion de nuevas clases y el aumento de las funcionalidades existentes, aplicando conceptos avanzados de Programacion Orientada a Objetos (POO).

## Objetivos y Proposito del Proyecto
El objetivo principal es transformar un sistema basico de catalogacion en una herramienta de gestion robusta y escalable. Los propositos especificos incluyen:
- Implementar relaciones complejas de POO (Asociacion, Agregacion y Composicion).
- Expandir la jerarquia de herencia con nuevos tipos de contenido.
- Desarrollar un sistema funcional con operaciones CRUD para cada modelo.
- Reestructurar el codigo siguiendo una arquitectura limpia y segmentada por paquetes.

## Contexto del Proyecto
El sistema original modela un catalogo de contenidos multimedia estructurado a partir de una clase abstracta principal denominada ContenidoAudiovisual. Esta clase se deriva en diversas subclases mediante la aplicacion del paradigma de la herencia, encapsulando atributos segun el tipo de contenido. En su estado inicial, el sistema presentaba una organizacion jerarquica util pero limitada en interaccion y funcionalidad.

## Analisis del Problema y Diseno (Diagramas)
El analisis se centro en identificar puntos de expansion basados en requerimientos academicos y criterios de desarrollo profesional. Se identificaron las limitaciones del sistema original y se propusieron las siguientes mejoras:
- Incorporacion de nuevos tipos de contenido (Anuncio Publicitario y Webinar).
- Extension de funcionalidades mediante clases de soporte (Actor, Temporada, Investigador).
- Uso de UML para documentar la estructura original y la evolucion hacia el sistema final.

Se destaca que el diseno de los diagramas de clases se realizo utilizando herramientas como Lucidchart, asegurando la correcta representacion de la herencia y las relaciones entre objetos.

## Desarrollo y Explicacion del Codigo

### Etapa 1: Preparacion del Entorno
- Clonacion del repositorio base desde GitHub.
- Reconstruccion del proyecto en el IDE (Eclipse) para asegurar la correcta deteccion de la clase main y la gestion de dependencias.

### Etapa 2: Incorporacion de Nuevas Clases y Relaciones
Se agregaron clases que enriquecen la informacion de los contenidos principales:

1. Actor (Relacionada con Pelicula)
   - Relacion: Agregacion.
   - Justificacion: Los actores existen independientemente de la pelicula. Si se elimina una pelicula, el objeto Actor persiste.

2. Temporada (Relacionada con SerieDeTV)
   - Relacion: Composicion.
   - Justificacion: El ciclo de vida de las temporadas esta ligado estrictamente a la serie. No tiene sentido una temporada sin su serie matriz.

3. Investigador (Relacionada con Documental)
   - Relacion: Asociacion.
   - Justificacion: Actua como colaborador o conferencista invitado. Es una relacion de "uso" donde una clase accede a la informacion de la otra.

### Etapa 3: Expansion con Nuevas Subclases
Se crearon dos nuevas subclases de ContenidoAudiovisual:
- Anuncio Publicitario: Gestiona contenidos de indole comercial.
- Webinar: Orientada a contenidos educativos e interactivos.

Ambas implementan:
- Herencia: Uso de super() para inicializar atributos de la clase padre.
- Polimorfismo: Sobrescritura del metodo mostrarDetalles() para proporcionar informacion especifica de cada tipo.

### Etapa 4: Reestructuracion y Arquitectura
Para evitar malas practicas de segmentacion, el proyecto se organizo en paquetes:
- com.audiovisual.core: Contiene la clase abstracta base.
- com.audiovisual.models: Organizado en subpaquetes por dominio (pelicula, serietv, documental, etc.). Cada paquete incluye su clase modelo y su respectivo Manager.
- com.audiovisual.app: Contiene la logica de la interfaz de usuario (Menu).

## Funcionalidades Implementadas
El sistema ofrece un CRUD (Create, Read, Update, Delete) completo para:
- Peliculas (con gestion de Actores).
- Series de TV (con gestion de Temporadas).
- Documentales (asociados a un Investigador).
- Webinars.
- Anuncios Publicitarios.

Se desarrollo una clase Manager para cada tipo de contenido, encargada de la logica de negocio y manipulacion de datos.

## Instrucciones para Clonar y Ejecutar

### Requisitos
- Java JDK 17 o superior.
- IDE (Eclipse, IntelliJ IDEA o VS Code).
- Git instalado.

### Clonacion
```bash
git clone https://github.com/AlexanderMartinez0410/ContenidoAudiovisual_Martinez.git
```

### Ejecucion
1. Importe el proyecto en su IDE como un proyecto Java existente.
2. Asegurese de que la carpeta src este marcada como Source Folder.
3. Localice la clase MenuAudiovisual en el paquete com.audiovisual.app.
4. Ejecute el archivo para iniciar el menu interactivo en consola.

## Resultados y Pruebas
El sistema ha sido probado mediante ciclos de creacion, edicion y eliminacion de registros:
- Creacion de Peliculas con listas de actores asignadas.
- Edicion de Series de TV modificando sus temporadas.
- Eliminacion de registros y verificacion de persistencia en memoria durante la ejecucion.
- Validacion del polimorfismo al listar todos los contenidos audiovisuales.

## Conclusiones y Recomendaciones
- La segmentacion por paquetes mejora significativamente la mantenibilidad del codigo.
- El uso de relaciones de agregacion y composicion permite modelar el mundo real con mayor precision.
- Se recomienda en futuras versiones implementar persistencia en base de datos o archivos JSON.

## Referencias
- Documentacion oficial de Java (Oracle).
- Ejemplo 30 de la Unidad 2 - Material de la Asignatura.
- Guia de Lucidchart para diagramacion UML.
