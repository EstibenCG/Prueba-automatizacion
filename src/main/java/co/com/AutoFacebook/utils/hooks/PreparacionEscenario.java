package co.com.AutoFacebook.utils.hooks;

import cucumber.api.java.Before;
import net.serenitybdd.screenplay.actors.OnlineCast;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class PreparacionEscenario {
    @Before
    public void setUp(){
        setTheStage(new OnlineCast());
        theActorCalled("Usuario");
    }
}
