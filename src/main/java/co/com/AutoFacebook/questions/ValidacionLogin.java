package co.com.AutoFacebook.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;


public class ValidacionLogin implements Question<Boolean>{
    private static final Logger logger = LoggerFactory.getLogger(ValidacionLogin.class);

    private static final String MENSAJE_ESPERADO = "/u/Jaideen";

    public static ValidacionLogin validacionLogin(){
        return new ValidacionLogin();
    }

    @Override
    public Boolean answeredBy (Actor actor){
        try{
            String currentUrl = BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
            logger.info("URL actual: " + currentUrl);
            return currentUrl != null && currentUrl.toLowerCase().contains(MENSAJE_ESPERADO.toLowerCase());
        }catch(Exception e){
            logger.error("No se pudo obtener la URL actual: " + e.getMessage());
            return false;
        }
    }

}
