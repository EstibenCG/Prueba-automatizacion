package co.com.AutoFacebook.stepsdefinitions;

import co.com.AutoFacebook.tasks.CrearListas;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class CrearListasStepDefinitions {

    @Dado("^que el usuario ya inició sesión$")
    public void queElUsuarioYaInicioSesion() {
        // Hook @login debe dejar la sesión iniciada
    }

    @Cuando("^crea una lista con nombre '(.+)' y descripción '(.+)'$")
    public void creaUnaListaConNombreYDescripcion(String nombre, String descripcion) {
        theActorInTheSpotlight().attemptsTo(
                CrearListas.con(nombre, descripcion)
        );
    }

    @Entonces("^debería ver la lista creada en mi sección de listas$")
    public void deberiaVerLaListaCreadaEnMiSeccionDeListas() {
        WebDriver driver = BrowseTheWeb.as(theActorInTheSpotlight()).getDriver();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("La URL actual no contiene /u/Jaideen/lists: " + currentUrl, currentUrl.contains("/u/Jaideen/lists"));

        // Verificar que exista un elemento con el nombre de la lista (h1 o h2)
        boolean tituloPresente = !driver.findElements(By.xpath("//*[contains(text(),'NUEVA LISTA PRUEBA')]"))
                .isEmpty();
        Assert.assertTrue("No se encontró el título de la lista 'NUEVA LISTA PRUEBA' en la página.", tituloPresente);
    }
}
