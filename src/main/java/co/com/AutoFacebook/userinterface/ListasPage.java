package co.com.AutoFacebook.userinterface;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class ListasPage extends PageObject {

    public static Target LINK_USER_LISTS = Target.the("Link user lists").located(By.cssSelector("a[href='/u/Jaideen/lists']"));

    public static Target LINK_NEW_LIST = Target.the("Link new list").located(By.cssSelector("a[href='/list/new']"));

    public static Target INPUT_LIST_NAME = Target.the("Input list name").located(By.id("name"));
    public static Target INPUT_LIST_DESCRIPTION = Target.the("Input list description").located(By.id("description"));

    public static Target BTN_STEP1_SUBMIT = Target.the("Button step 1 submit").located(By.id("step_1_submit"));

    public static Target NOTIFICATION_SUCCESS = Target.the("Success notification").located(By.xpath("//*[contains(text(),'Tus cambios se han guardado correctamente') or contains(text(),'¡Elemento añadido!') or contains(text(),'¡Imagen guardada!')]") );

    public static Target LIST_ITEM_SEARCH = Target.the("List item search").located(By.id("list_item_search"));

    public static Target AUTOCOMPLETE_FIRST = Target.the("First autocomplete option").located(By.cssSelector(
            ".ui-autocomplete li:first-child, .suggestions li:first-child, ul.typeahead li:first-child, .tt-suggestion:first-child, " +
            "li.k-list-item:first-child, .ac_results .ac_item:first-child, .ac_item.poster.list_item:first-child, .k-list-item:first-child, .k-list-item .k-list-item-text:first-child"
    ));

    public static Target TAB_STEP3 = Target.the("Tab step 3").located(By.cssSelector("a[href*='active_nav_item=step_3']"));

    public static Target IMG_SUPERMAN = Target.the("Imagen Superman").locatedBy("//img[@alt='Superman']");


}
