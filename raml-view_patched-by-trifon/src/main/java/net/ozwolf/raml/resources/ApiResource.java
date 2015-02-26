package net.ozwolf.raml.resources;

import net.ozwolf.raml.configuration.ApiSpecsConfiguration;
import net.ozwolf.raml.model.RamlModel;
import net.ozwolf.raml.views.ApiView;
import org.raml.emitter.RamlEmitter;
import org.raml.parser.visitor.RamlDocumentBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class ApiResource {
    private final String specificationFile;
    private final ApiSpecsConfiguration configuration;

    public ApiResource(String specificationFile, ApiSpecsConfiguration configuration) {
        this.specificationFile = specificationFile;
        this.configuration = configuration;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public ApiView get() {
        return ApiView.view(RamlModel.model(specificationFile, configuration));
    }

    @Path("/raw")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String raw() {
        return new RamlEmitter().dump(new RamlDocumentBuilder().build(specificationFile));
    }

    public static ApiResource resource(String specificationFile, ApiSpecsConfiguration configuration) {
        return new ApiResource(specificationFile, configuration);
    }
}
