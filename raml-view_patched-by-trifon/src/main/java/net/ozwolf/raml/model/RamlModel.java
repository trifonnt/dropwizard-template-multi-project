package net.ozwolf.raml.model;

import com.googlecode.totallylazy.Sequence;
import net.ozwolf.raml.configuration.ApiSpecsConfiguration;
import org.raml.model.Protocol;
import org.raml.model.Raml;
import org.raml.parser.visitor.RamlDocumentBuilder;

import java.net.URI;
import java.util.List;

import static com.googlecode.totallylazy.Sequences.sequence;
import static net.ozwolf.raml.utils.TotallyLazyHelper.*;

public class RamlModel {
    private final Raml raml;
    private final ApiSpecsConfiguration configuration;

    public RamlModel(String specificationFile, ApiSpecsConfiguration configuration) {
        this.raml = new RamlDocumentBuilder().build(specificationFile);
        this.configuration = configuration;
    }

    public String getTitle() {
        return raml.getTitle();
    }

    public String getVersion() {
        return raml.getVersion();
    }

    public Sequence<Protocol> getProtocols() {
        if (raml.getProtocols() == null || raml.getProtocols().isEmpty()) return sequence(Protocol.HTTP);
        return sequence(raml.getProtocols());
    }

    public boolean hasStylesheets() {
        return !configuration.getStylesheets().isEmpty();
    }

    public List<URI> getStylesheets() {
        return configuration.getStylesheets();
    }

    public Sequence<RamlDocumentationModel> getDocumentation() {
        if (raml.getDocumentation() == null) return sequence();
        return sequence(raml.getDocumentation()).map(asRamlDocumentationModel());
    }

    public Sequence<RamlResourceModel> getResources() {
        if (raml.getResources() == null) return sequence();
        return sequence(raml.getResources().values()).map(asRamlResourceModel(getSecurity()));
    }

    private Sequence<RamlSecurityModel> getSecurity() {
        return sequence(raml.getSecuritySchemes()).map(asRamlSecurityModel());
    }

    @Override
    public String toString() {
        return String.format("RAML = [%s - v%s]", raml.getTitle(), raml.getVersion());
    }

    public static RamlModel model(String specificationFile, ApiSpecsConfiguration configuration) {
        return new RamlModel(specificationFile, configuration);
    }
}
