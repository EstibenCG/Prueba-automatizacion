#language: es
  #author: Edwin1538

Característica: Creación de un usuario tipo Docente
  Como usuario del Sistema de Ingreso General Académico SIGA
  Quiero poder asignarle a un usuario registrado el rol Docente
  Para poder asociarlo a una de las clases registradas con anterioridad

  @registro

  Escenario: Verificar el registro de un docente de manera exitosa.
    Dado que el usuario se encuentra en el módulo de registro
    Cuando le asigne el rol docente a un usuario
      | Usuario Registrado | Nombre | Apellido |
      | 1 Carlos@gmail.com | Carlos | Bodoque  |
    Entonces se debe verificar que el usuario ahora cuente con el rol Docente.