package co.com.AutoFacebook.userinterface;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class ListasPage extends PageObject {

    // NOTA: Asumí selectores razonables porque no tenemos los selectores reales de la página.
    // Ajusta estos selectores si no coinciden con la estructura real del sitio.

    // Link a la sección de listas en el perfil del usuario (específico para Jaideen)
    public static Target LINK_USER_LISTS = Target.the("Link user lists").located(By.cssSelector("a[href='/u/Jaideen/lists']"));

    // Link para crear una nueva lista
    public static Target LINK_NEW_LIST = Target.the("Link new list").located(By.cssSelector("a[href='/list/new']"));

    // Inputs del formulario de creación de lista
    public static Target INPUT_LIST_NAME = Target.the("Input list name").located(By.id("name"));
    public static Target INPUT_LIST_DESCRIPTION = Target.the("Input list description").located(By.id("description"));

    // Botón para continuar/guardar en el paso 1
    public static Target BTN_STEP1_SUBMIT = Target.the("Button step 1 submit").located(By.id("step_1_submit"));

    // Notificación genérica de éxito (texto parcial esperado)
    public static Target NOTIFICATION_SUCCESS = Target.the("Success notification").located(By.xpath("//*[contains(text(),'Tus cambios se han guardado correctamente') or contains(text(),'¡Elemento añadido!') or contains(text(),'¡Imagen guardada!')]"));

    // Buscador para añadir items a la lista
    public static Target LIST_ITEM_SEARCH = Target.the("List item search").located(By.id("list_item_search"));

    // Selector del primer elemento en la lista de autocompletado (varios selectores para cubrir opciones)
    // Agrego selectores que coinciden con el DOM proporcionado por el usuario: .ac_item.poster.list_item, li.k-list-item, .k-list-item-text, .ac_results
    public static Target AUTOCOMPLETE_FIRST = Target.the("First autocomplete option").located(By.cssSelector(
            ".ui-autocomplete li:first-child, .suggestions li:first-child, ul.typeahead li:first-child, .tt-suggestion:first-child, " +
            "li.k-list-item:first-child, .ac_results .ac_item:first-child, .ac_item.poster.list_item:first-child, .k-list-item:first-child, .k-list-item .k-list-item-text:first-child"
    ));

    // Link/tab al paso 3
    public static Target TAB_STEP3 = Target.the("Tab step 3").located(By.cssSelector("a[href*='active_nav_item=step_3']"));

    // Imagen Superman por alt
    public static Target IMG_SUPERMAN = Target.the("Imagen Superman").locatedBy("//img[@alt='Superman']");

    // Elemento tooltip hover
    public static Target TOOLTIP_NO_CLICK = Target.the("Tooltip hover").located(By.cssSelector(".no_click.tooltip_hover"));

    // Link para regresar a la lista del usuario
    public static Target LINK_BACK_TO_LISTS = Target.the("Back to user lists").located(By.cssSelector("a[href='/u/Jaideen/lists']"));

}
