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
        actor.attemptsTo(
                Click.on(LINK_USER_LISTS),
                Click.on(LINK_NEW_LIST),
                Click.on(INPUT_LIST_NAME),
                Enter.theValue(nombre).into(INPUT_LIST_NAME),
                Click.on(INPUT_LIST_DESCRIPTION),
                Enter.theValue(descripcion).into(INPUT_LIST_DESCRIPTION)
        );

        try {
            actor.attemptsTo(
                    WaitUntil.the(BTN_STEP1_SUBMIT, isClickable()).forNoMoreThan(15).seconds(),
                    Scroll.to(BTN_STEP1_SUBMIT),
                    Click.on(BTN_STEP1_SUBMIT)
            );
        } catch (Exception e) {
            try {
                actor.attemptsTo(Scroll.to(BTN_STEP1_SUBMIT), Click.on(BTN_STEP1_SUBMIT));
            } catch (Exception ex) {
            }
        }

        try {
            actor.attemptsTo(WaitUntil.the(LIST_ITEM_SEARCH, isVisible()).forNoMoreThan(10).seconds());
        } catch (Exception ex) {
            actor.attemptsTo(WaitUntil.the(TAB_STEP3, isVisible()).forNoMoreThan(10).seconds());
        }

        seleccionarItemPorAutocomplete(actor, "BATMAN");
        seleccionarItemPorAutocomplete(actor, "SUPERMAN");

        actor.attemptsTo(Click.on(TAB_STEP3));

        try {
            actor.attemptsTo(
                    WaitUntil.the(IMG_SUPERMAN, isVisible()).forNoMoreThan(15).seconds(),
                    Scroll.to(IMG_SUPERMAN)
            );
            try {
                actor.attemptsTo(WaitUntil.the(IMG_SUPERMAN, isClickable()).forNoMoreThan(10).seconds(), Click.on(IMG_SUPERMAN));
                System.out.println("Imagen Superman: seleccionada con Click.on(IMG_SUPERMAN)");
                actor.remember("IMAGE_SELECTED", true);
                return;
            } catch (Exception clickEx) {
                try {
                    WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                    WebElement img = driver.findElement(By.xpath("//img[@alt='Superman']"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", img);
                    System.out.println("Imagen Superman: seleccionada mediante JS click fallback");
                    actor.remember("IMAGE_SELECTED", true);
                    return;
                } catch (Exception jsEx) {
                    System.out.println("Imagen Superman: JS fallback falló - " + jsEx.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Imagen Superman: no encontrada o no visible en step 3 - " + e.getMessage());
        }

        actor.remember("IMAGE_SELECTED", false);
    }

    private <T extends Actor> void seleccionarItemPorAutocomplete(T actor, String texto) {
        actor.attemptsTo(Click.on(LIST_ITEM_SEARCH), Enter.theValue(texto).into(LIST_ITEM_SEARCH));

        try {
            actor.attemptsTo(
                    WaitUntil.the(AUTOCOMPLETE_FIRST, isVisible()).forNoMoreThan(10).seconds(),
                    Click.on(AUTOCOMPLETE_FIRST)
            );
            System.out.println("Autocomplete: seleccionado por Click.on(AUTOCOMPLETE_FIRST)");
        } catch (Throwable e) {
            System.out.println("Autocomplete: no se encontró opción de autocompletado para '" + texto + "' - se continúa sin seleccionar.");
        }
    }
}
