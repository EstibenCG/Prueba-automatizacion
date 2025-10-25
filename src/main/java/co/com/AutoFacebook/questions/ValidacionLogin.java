package co.com.AutoFacebook.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import static co.com.AutoFacebook.userinterface.autenticacion.MENSAJE_LOGIN;


public class ValidacionLogin implements Question<Boolean>{
    private static final Logger logger = LoggerFactory.getLogger(ValidacionLogin.class);

    private static final String MENSAJE_ESPERADO = "Logged In Successfully";

    public static ValidacionLogin validacionLogin(){
        return new ValidacionLogin();
    }

    @Override
    public Boolean answeredBy (Actor actor){
        try{
            String texto = Text.of(MENSAJE_LOGIN).viewedBy(actor).asString().trim();
            logger.info("Texto encontrado en MENSAE LOGIN: " +texto);
            return MENSAJE_ESPERADO.equalsIgnoreCase(texto);
        }catch(Exception e){
            logger.error("No se encontró el Mensaje" +e.getMessage());
            return false;
        }
    }

}
