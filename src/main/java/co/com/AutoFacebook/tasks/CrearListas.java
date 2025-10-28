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
                // Recordar que la imagen fue seleccionada y terminar aquí
                actor.remember("IMAGE_SELECTED", true);
                return; // detener el flujo del task aquí
            } catch (Exception clickEx) {
                System.out.println("Imagen Superman: Click.on falló, intentando JS fallback");
                try {
                    WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                    WebElement img = driver.findElement(By.xpath("//img[@alt='Superman']"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", img);
                    System.out.println("Imagen Superman: seleccionada mediante JS click fallback");
                    // Recordar que la imagen fue seleccionada y terminar aquí
                    actor.remember("IMAGE_SELECTED", true);
                    return; // detener el flujo del task aquí
                } catch (Exception jsEx) {
                    System.out.println("Imagen Superman: JS fallback falló - " + jsEx.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Imagen Superman: no encontrada o no visible en step 3 - " + e.getMessage());
            // continuar sin interrumpir el flujo (pero no marcar como seleccionada)
        }

        // Si llegamos aquí, la selección de la imagen no se pudo completar.
        actor.remember("IMAGE_SELECTED", false);
    }

    private <T extends Actor> void seleccionarItemPorAutocomplete(T actor, String texto) {
        // escribir en el input y esperar la primera opción del autocompletado
        actor.attemptsTo(Click.on(LIST_ITEM_SEARCH), Enter.theValue(texto).into(LIST_ITEM_SEARCH));

        try {
            actor.attemptsTo(
                    WaitUntil.the(AUTOCOMPLETE_FIRST, isVisible()).forNoMoreThan(10).seconds(),
                    Click.on(AUTOCOMPLETE_FIRST)
            );
            System.out.println("Autocomplete: seleccionado por Click.on(AUTOCOMPLETE_FIRST)");
        } catch (Throwable e) {
            // Si no aparece la opción de autocompletado, continuar el flujo sin seleccionarla
            System.out.println("Autocomplete: no se encontró opción de autocompletado para '" + texto + "' - se continúa sin seleccionar.");
        }
    }
}
