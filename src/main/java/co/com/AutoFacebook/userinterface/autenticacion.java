package co.com.AutoFacebook.userinterface;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class autenticacion extends PageObject{
    public static Target INPUT_USUARIO = Target.the("Ingreso del Usuario").located(By.id("username"));
    public static Target INPUT_CLAVE= Target.the("Ingreso del password").located(By.id("password"));
    public static Target BTN_INICIOSESION= Target.the("Clic boton inicio sesion").located(By.id("submit"));

    public static Target MENSAJE_LOGIN = Target.the("mensaje de login").locatedBy("//h1[@class='post-title' and text()='Logged In Successfully']");

}
