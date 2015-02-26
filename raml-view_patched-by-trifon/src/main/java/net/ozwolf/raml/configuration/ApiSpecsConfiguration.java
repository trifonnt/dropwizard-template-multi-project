package net.ozwolf.raml.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.google.common.collect.Lists.newLinkedList;

/**
 * # API Specification View Configuration
 *
 * This configuration can be used to provide configuration information to the
 *
 * ## Example Usage
 *
 * ```java
 * ApiSpecsBundle.bundle("apispecs/apispecs.raml", ApiSpecsConfiguration.configuration().withStylesheet("/my-assets/css/override.css"));
 * ```
 */
public class ApiSpecsConfiguration {
    @Valid
    @JsonProperty
    private final List<URI> stylesheets;

    public ApiSpecsConfiguration() {
        this.stylesheets = newLinkedList();
    }

    /**
     * Returns a list of stylesheets for the view.  These will be loaded _after_ the primary stylesheets and in the order
     * provided.
     *
     * @return The list of stylesheet URIs.
     */
    public List<URI> getStylesheets() {
        return stylesheets;
    }

    /**
     * Add a stylesheet override
     *
     * @param stylesheetUri The URI to the stylesheet override.  Can be relative or absolute to the `/api` resource.
     * @return The updated configuration
     */
    public ApiSpecsConfiguration withStylesheet(String stylesheetUri) {
        this.stylesheets.add(URI.create(stylesheetUri));
        return this;
    }

    /**
     * Create a new API specs configuration
     *
     * @return The new configuration
     */
    public static ApiSpecsConfiguration configuration() {
        return new ApiSpecsConfiguration();
    }
}
