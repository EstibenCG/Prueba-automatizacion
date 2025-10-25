package co.com.AutoFacebook.tasks;

import co.com.AutoFacebook.userinterface.InicioFacebook;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Click;
import static co.com.AutoFacebook.userinterface.autenticacion.LINK_LOGIN;
public class AbrirPagina implements Task {

    public static AbrirPagina lapagina(){ return Tasks.instrumented(AbrirPagina.class);}

        @Override
    public <T extends Actor> void performAs(T actor){
        actor.attemptsTo(
                Open.browserOn(new InicioFacebook()),
                Click.on(LINK_LOGIN)
        );
    }
}
