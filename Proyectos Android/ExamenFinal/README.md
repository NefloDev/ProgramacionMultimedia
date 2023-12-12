
# Examen Final

Examen final de la primera evaluación de la asignatura de Programación de Dispositivos Multimedia.

Este examen se trata de una aplicación que recoge datos de la API pública de Pokemon, mostrando una lista de movimientos, y una de items/objetos, además de poder ver un objeto aleatorio de los que se encuentran dentro de la lista completa de la API pública.

El proyecto usa un Navigation Graph para navegar por los fragmentos que se van a mostrar en el layout de la Actividad Principal "Main Activity".

Para recolectar los datos usa Retrofit2 que hace llamadas a la api y transforma los datos que tienen la misma estructura y nombre de campo que el objeto al que se ha configurado el parseo, el cual realiza la librería GSon.

Para mostrar las listas de datos se usan RecyclerViews que permiten mostrar listas de layouts con varios elementos configurados en un Adaptador personalizado.

Para mostrar el detalle de un elemento cuando se pulsa sobre él, el proyecto usa un ViewModel que trata objetos MutableLiveData para actualizar los datos en tiempo de ejecución en segundo plano para no bloquear la aplicación mientras los datos.




## Documentación de la API

[PokeApi](https://pokeapi.co)


## Ejecutar el proyecto

Abre el proyecto en Android Studio y ejecuta la aplicación en un dispositivo virtual o uno físico conectado en tu ordenador.
## Autor

- [@NefloArt](https://github.com/nefloart)

## Profesor

- [@nafarrin](@https://github.com/nafarrin)

