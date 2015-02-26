package net.ozwolf.raml.views;

import net.ozwolf.raml.model.RamlModel;
import io.dropwizard.views.View;

public class ApiView extends View {
    private final RamlModel model;

    public ApiView(RamlModel model) {
        super("../templates/index.ftl");
        this.model = model;
    }

    public RamlModel getModel() {
        return model;
    }

    public static ApiView view(RamlModel model) {
        return new ApiView(model);
    }
}
