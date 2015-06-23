package preso.rest.fun;

import info.magnolia.registry.RegistrationException;
import info.magnolia.rest.client.RestClient;
import info.magnolia.rest.client.registry.RestClientRegistry;
import info.magnolia.resteasy.client.RestEasyClient;
import info.magnolia.ui.form.field.definition.SelectFieldDefinition;
import info.magnolia.ui.form.field.definition.SelectFieldOptionDefinition;
import info.magnolia.ui.form.field.factory.SelectFieldFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;

import preso.rest.fun.FavouriteJokeSelectFieldFactory.Definition;

import com.vaadin.data.Item;

/**
 * FavouriteJoke SelectFieldFactory.
 */
public class FavouriteJokeSelectFieldFactory extends SelectFieldFactory<Definition> {

    private RestClientRegistry registry;

    @Inject
    public FavouriteJokeSelectFieldFactory(Definition definition, Item relatedFieldItem, RestClientRegistry registry) {
        super(definition, relatedFieldItem);
        this.registry = registry;
    }

    @Override
    public List<SelectFieldOptionDefinition> getSelectFieldOptionDefinition() {
        List<SelectFieldOptionDefinition> fields = new ArrayList<SelectFieldOptionDefinition>();

        try {
            RestClient client = registry.getRestClient("ICNDBRestClient");

            ICNDBService service = ((RestEasyClient) client).getClientService(ICNDBService.class);

            for (JsonNode joke : service.all("explicit").path("value")) {
                SelectFieldOptionDefinition field = new SelectFieldOptionDefinition();
                field.setName(definition.getName());
                field.setLabel(StringUtils.abbreviate(joke.path("joke").asText(), 100));
                field.setValue(joke.path("id").asText());
                fields.add(field);
            }

        } catch (RegistrationException e) {
            SelectFieldOptionDefinition field = new SelectFieldOptionDefinition();
            field.setName(definition.getName());
            field.setLabel("It looks like an error has occured. Please contact admin or developers about it: " + e.getMessage());
            field.setValue(e.getMessage());
            fields.add(field);
        }

        return fields;
    }

    /**
     * Definition for custom select field.
     */
    public static class Definition extends SelectFieldDefinition {

        public Definition() {
        }
    }

}
