package net.ozwolf.raml;

import net.ozwolf.raml.configuration.ApiSpecsConfiguration;
import net.ozwolf.raml.resources.ApiResource;
import io.dropwizard.Bundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import static net.ozwolf.raml.configuration.ApiSpecsConfiguration.configuration;

/**
 * # RAML Docs Bundle
 *
 * This bundle is a DropWizard bundle that displays a RAML API specification
 *
 * ## Example Usage
 *
 * ```java
 * bootstrap.addBundle(RamlView.bundle("apispecs/apispecs.raml"));
 * ```
 */
public class RamlView implements Bundle {
    private final String specificationFile;
    private final ApiSpecsConfiguration configuration;

    private RamlView(String specificationFile, ApiSpecsConfiguration configuration) {
        this.specificationFile = specificationFile;
        this.configuration = configuration;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/raml-docs-assets", "/api/assets", null, "raml-view"));
    }

    @Override
    public void run(Environment environment) {
        environment.jersey().register(ApiResource.resource(specificationFile, configuration));
    }

    /**
     * Instantiate the RAML API documentation bundle.
     *
     * @param specificationFile The path to the specification resource
     * @return The DropWizard bundle
     */
    public static RamlView bundle(String specificationFile) {
        return new RamlView(specificationFile, configuration());
    }

    /**
     * Instantiate the RAML API documentation bundle with a custom configuration.
     *
     * @param specificationFile The path to the specification resource
     * @param configuration     The API specs configuration
     * @return The DropWizard bundle.
     */
    public static RamlView bundle(String specificationFile, ApiSpecsConfiguration configuration) {
        return new RamlView(specificationFile, configuration);
    }
}
