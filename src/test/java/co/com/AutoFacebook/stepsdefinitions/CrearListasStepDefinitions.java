package co.com.AutoFacebook.stepsdefinitions;

import co.com.AutoFacebook.tasks.CrearListas;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;

import org.junit.Assert;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class CrearListasStepDefinitions {

    @Dado("^que el usuario ya inició sesión$")
    public void queElUsuarioYaInicioSesion() {
        // Hook @login debe dejar la sesión iniciada
    }

    @Cuando("^crea una lista con nombre '(.+)' y descripción '(.+)'$")
    public void creaUnaListaConNombreYDescripcion(String nombre, String descripcion) {
        // Recordar el nombre de la lista por si queremos usarlo después
        theActorInTheSpotlight().remember("LIST_NAME", nombre);

        theActorInTheSpotlight().attemptsTo(
                CrearListas.con(nombre, descripcion)
        );
    }

    @Entonces("^la imagen de Superman debería estar seleccionada$")
    public void deberiaVerLaListaCreadaEnMiSeccionDeListas() {
        // Comprobación simplificada: el test termina cuando la imagen fue seleccionada
        boolean imagenSeleccionada = false;
        Object recall = theActorInTheSpotlight().recall("IMAGE_SELECTED");
        if (recall instanceof Boolean) {
            imagenSeleccionada = (Boolean) recall;
        }
        Assert.assertTrue("La imagen no fue seleccionada correctamente.", imagenSeleccionada);
    }
}
