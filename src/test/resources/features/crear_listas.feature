#language: es

Característica: Crear listas en TMDB
  Como usuario autenticado
  Quiero crear una nueva lista y agregar elementos
  Para organizar mis filmes favoritos

  @login @crearlistas
  Escenario: Crear una lista y agregar Batman y Superman
    Dado que el usuario ya inició sesión
    Cuando crea una lista con nombre 'NUEVA LISTA PRUEBA' y descripción 'ESTA ES UNA LISTA DE PRUEBA'
    Entonces debería ver la lista creada en mi sección de listas

