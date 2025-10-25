package co.com.AutoFacebook.stepsdefinitions;

import co.com.AutoFacebook.models.CredencialesInicioSesion;
import co.com.AutoFacebook.questions.ValidacionLogin;
import co.com.AutoFacebook.tasks.AbrirPagina;
import co.com.AutoFacebook.tasks.Autenticarse;
import cucumber.api.DataTable;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class AutenticacionStepDefinitions {
    @Dado("^que el usuario se encuentra en la página de inicio de sesión$")
    public void queElUsuarioSeEncuentraEnLaPáginaDeInicioDeSesión() {
        theActorInTheSpotlight().wasAbleTo(AbrirPagina.lapagina());
    }

    @Cuando("^le asigne el rol docente a un usuario$")
    public void leAsigneElRolDocenteAUnUsuario(List<CredencialesInicioSesion> credenciales) {
        theActorInTheSpotlight().attemptsTo(Autenticarse.aute(credenciales));
    }

    @Entonces("^se debe verificar que el usuario ahora cuente con el rol Docente\\.$")
    public void seDebeVerificarQueElUsuarioAhoraCuenteConElRolDocente() {
        theActorInTheSpotlight().should(seeThat(ValidacionLogin.validacionLogin()));
    }

}