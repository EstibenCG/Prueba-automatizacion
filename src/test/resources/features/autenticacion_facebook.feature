#language: es
  #author: Edwin1538

Característica: Autenticacion en la pagina de Facebook
  Como usuario de Facebook
  Quiero autenticarme en el portal de Facebook.
  Para poder acceder al contenido.

  @registro

  Escenario: Verificar la autenticacion exitosa en la página de Facebok.
    Dado que el usuario se encuentra en la página de inicio de sesión
    Cuando le asigne el rol docente a un usuario
      | Usuario | Clave       |
      | Jaideen    | Cont123* |
    Entonces se debe verificar que el usuario ahora cuente con el rol Docente.