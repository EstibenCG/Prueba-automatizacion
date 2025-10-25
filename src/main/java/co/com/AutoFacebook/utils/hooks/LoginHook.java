package co.com.AutoFacebook.utils.hooks;

import co.com.AutoFacebook.models.CredencialesInicioSesion;
import co.com.AutoFacebook.tasks.AbrirPagina;
import co.com.AutoFacebook.tasks.Autenticarse;
import cucumber.api.java.Before;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class LoginHook {

    @Before("@login")
    public void loginBeforeScenario() {
        // Asegurar el cast (si ya existe otro hook global, esto no perjudica)
        setTheStage(new OnlineCast());
        theActorCalled("Usuario");

        String user = System.getenv("TMDB_USER") != null ? System.getenv("TMDB_USER") : "Jaideen";
        String pass = System.getenv("TMDB_PASS") != null ? System.getenv("TMDB_PASS") : "Cont123*";

        List<CredencialesInicioSesion> credenciales = buildCredenciales(user, pass);

        theActorCalled("Usuario").wasAbleTo(
                AbrirPagina.lapagina(),
                Autenticarse.aute(credenciales)
        );
    }

    private List<CredencialesInicioSesion> buildCredenciales(String user, String pass) {
        CredencialesInicioSesion c = new CredencialesInicioSesion();
        c.setUsuario(user);
        c.setClave(pass);
        List<CredencialesInicioSesion> lista = new ArrayList<>();
        lista.add(c);
        return lista;
    }
}

