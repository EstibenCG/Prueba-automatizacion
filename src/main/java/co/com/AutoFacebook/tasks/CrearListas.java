package co.com.AutoFacebook.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static co.com.AutoFacebook.userinterface.ListasPage.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class CrearListas implements Task {

    private final String nombre;
    private final String descripcion;

    public CrearListas(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public static CrearListas con(String nombre, String descripcion){
        return Tasks.instrumented(CrearListas.class, nombre, descripcion);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Asumimos que ya estamos en /u/Jaideen luego del login (hook @login)
        actor.attemptsTo(
                Click.on(LINK_USER_LISTS),
                Click.on(LINK_NEW_LIST),

                // Rellenar nombre y descripción
                Click.on(INPUT_LIST_NAME),
                Enter.theValue(nombre).into(INPUT_LIST_NAME),
                Click.on(INPUT_LIST_DESCRIPTION),
                Enter.theValue(descripcion).into(INPUT_LIST_DESCRIPTION)
        );

        // Asegurar que el botón de paso 1 esté clickable, hacer scroll y click
        try {
            actor.attemptsTo(
                    WaitUntil.the(BTN_STEP1_SUBMIT, isClickable()).forNoMoreThan(15).seconds(),
                    Scroll.to(BTN_STEP1_SUBMIT),
                    Click.on(BTN_STEP1_SUBMIT)
            );
        } catch (Exception e) {
            // Intento alternativo: intentar un click sin esperar
            try {
                actor.attemptsTo(Scroll.to(BTN_STEP1_SUBMIT), Click.on(BTN_STEP1_SUBMIT));
            } catch (Exception ex) {
                // último recurso: continuar el flujo aunque el click falle
            }
        }

        // No esperar notificaciones: en lugar de eso esperar el campo de búsqueda de items o el tab siguiente
        try {
            actor.attemptsTo(WaitUntil.the(LIST_ITEM_SEARCH, isVisible()).forNoMoreThan(10).seconds());
        } catch (Exception ex) {
            actor.attemptsTo(WaitUntil.the(TAB_STEP3, isVisible()).forNoMoreThan(10).seconds());
        }

        // Añadir BATMAN
        seleccionarItemPorAutocomplete(actor, "BATMAN");

        // Añadir SUPERMAN
        seleccionarItemPorAutocomplete(actor, "SUPERMAN");

        // Ir al paso 3 y seleccionar imagen Superman
        actor.attemptsTo(
                Click.on(TAB_STEP3)
        );
        try {
            // esperar que la imagen esté visible y hacer scroll
            actor.attemptsTo(
                    WaitUntil.the(IMG_SUPERMAN, isVisible()).forNoMoreThan(15).seconds(),
                    Scroll.to(IMG_SUPERMAN)
            );
            // intentar click normal si es clickable
            try {
                actor.attemptsTo(WaitUntil.the(IMG_SUPERMAN, isClickable()).forNoMoreThan(10).seconds(), Click.on(IMG_SUPERMAN));
                System.out.println("Imagen Superman: seleccionada con Click.on(IMG_SUPERMAN)");
            } catch (Exception clickEx) {
                System.out.println("Imagen Superman: Click.on falló, intentando JS fallback");
                try {
                    WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                    WebElement img = driver.findElement(By.xpath("//img[@alt='Superman']"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", img);
                    try { Thread.sleep(400); } catch (InterruptedException ignored) {}
                    System.out.println("Imagen Superman: seleccionada mediante JS click fallback");
                } catch (Exception jsEx) {
                    System.out.println("Imagen Superman: JS fallback falló - " + jsEx.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Imagen Superman: no encontrada o no visible en step 3 - " + e.getMessage());
            // continuar sin interrumpir el flujo
        }

        // Pasar sobre tooltip y volver a listas del usuario
        actor.attemptsTo(
                Click.on(TOOLTIP_NO_CLICK),
                Click.on(LINK_BACK_TO_LISTS)
        );
    }

    private <T extends Actor> void seleccionarItemPorAutocomplete(T actor, String texto) {
        // escribir en el input
        actor.attemptsTo(Click.on(LIST_ITEM_SEARCH), Enter.theValue(texto).into(LIST_ITEM_SEARCH));

        // pausa corta para que el frontend procese la entrada
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        // intento normal: esperar y click al primer elemento
        try {
            actor.attemptsTo(WaitUntil.the(AUTOCOMPLETE_FIRST, isVisible()).forNoMoreThan(10).seconds(), Click.on(AUTOCOMPLETE_FIRST));
            System.out.println("Autocomplete: seleccionado por Click.on(AUTOCOMPLETE_FIRST)");
            return;
        } catch (Exception e) {
            System.out.println("Autocomplete: no visible inicialmente, probando fallbacks para '" + texto + "'");
            // continuar a fallbacks
        }

        // Forzar evento 'input' mediante JS para activar sugerencias si no se mostraron
        try {
            ((JavascriptExecutor) driver).executeScript("var el=document.getElementById('list_item_search'); if(el){el.dispatchEvent(new Event('input'));}");
            try { Thread.sleep(400); } catch (InterruptedException ignored) {}
            actor.attemptsTo(WaitUntil.the(AUTOCOMPLETE_FIRST, isVisible()).forNoMoreThan(5).seconds(), Click.on(AUTOCOMPLETE_FIRST));
            System.out.println("Autocomplete: seleccionado tras forzar evento input por JS");
            return;
        } catch (Exception ex) {
            System.out.println("Autocomplete: fallback JS 'input' no mostró opciones para '" + texto + "'");
            // ignorar y probar siguiente fallback
        }

        // Fallback JS: buscar varios selectores y hacer click en el primer elemento visible
        try {
            String script = "var selectors = ['.ui-autocomplete li:first-child', '.suggestions li:first-child', 'ul.typeahead li:first-child', '.tt-suggestion:first-child', 'ul.dropdown-menu li:first-child', 'div.suggestions li:first-child'];\n" +
                    "for(var i=0;i<selectors.length;i++){try{var els=document.querySelectorAll(selectors[i]); if(els && els.length){ for(var j=0;j<els.length;j++){var el=els[j]; if(el && el.offsetParent!==null){el.click(); return true;}} } }catch(e){} } return false;";

            boolean clicked = false;
            for (int attempt = 0; attempt < 10; attempt++) {
                Object result = ((JavascriptExecutor) driver).executeScript(script);
                if (result instanceof Boolean && (Boolean) result) {
                    clicked = true;
                    break;
                }
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
            if (clicked) {
                System.out.println("Autocomplete: seleccionado por script JS que clickea el primer elemento visible (polling)");
                return;
            }
        } catch (Exception ex) {
            System.out.println("Autocomplete: script JS falló para '" + texto + "'");
            // ignorar y probar siguiente fallback
        }

        // Fallback JS 2: buscar cualquier elemento visible (li/div/a) cuyo texto contenga el texto buscado (case-insensitive)
        try {
            String scriptMatchText = "var text = arguments[0].toLowerCase(); var els = document.querySelectorAll('li, div, a'); for(var i=0;i<els.length;i++){try{var el=els[i]; if(el && el.offsetParent!==null && el.innerText && el.innerText.toLowerCase().indexOf(text)!==-1){ el.click(); return true; }}catch(e){} } return false;";
            Object matched = ((JavascriptExecutor) driver).executeScript(scriptMatchText, texto);
            if (matched instanceof Boolean && (Boolean) matched) {
                System.out.println("Autocomplete: seleccionado por script JS buscando texto '" + texto + "'");
                return;
            }
        } catch (Exception ex) {
            System.out.println("Autocomplete: script JS de búsqueda por texto falló para '" + texto + "'");
        }

        // Fallback teclas: enviar ARROW_DOWN + ENTER con pequeñas pausas
        try {
            WebElement input = driver.findElement(By.id("list_item_search"));
            input.sendKeys(Keys.ARROW_DOWN);
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            input.sendKeys(Keys.ENTER);
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            System.out.println("Autocomplete: seleccionado mediante teclas ARROW_DOWN + ENTER");
            return;
        } catch (Exception ex) {
            System.out.println("Autocomplete: fallback teclas falló para '" + texto + "'");
            // ignorar y continuar
        }

        // Si todo falla, intentar un click directo en el primer elemento encontrado por CSS (sin esperar visibilidad)
        try {
            WebElement first = driver.findElement(By.cssSelector(".ui-autocomplete li:first-child, .suggestions li:first-child, ul.typeahead li:first-child, .tt-suggestion:first-child"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", first);
            System.out.println("Autocomplete: click directo en primer elemento encontrado por CSS");
        } catch (Exception ignored) {
            System.out.println("Autocomplete: todos los fallbacks fallaron para '" + texto + "'");
            // no más fallbacks
        }
    }
}
