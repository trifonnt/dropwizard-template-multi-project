package net.ozwolf.raml.model;

import com.googlecode.totallylazy.Predicate;
import com.googlecode.totallylazy.Sequence;
import com.googlecode.totallylazy.Sequences;
import org.raml.model.Action;
import org.raml.model.SecurityReference;

import java.util.List;

import static net.ozwolf.raml.utils.MarkDownHelper.fromMarkDown;
import static net.ozwolf.raml.utils.TotallyLazyHelper.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class RamlActionModel {
    private final Action action;
    private final Sequence<RamlSecurityModel> securityModels;

    public RamlActionModel(Action action, Sequence<RamlSecurityModel> securityModels) {
        this.action = action;
        this.securityModels = securityModels;
    }

    public String getType() {
        return action.getType().name();
    }

    public String getDescription() {
        if (action.getDescription() == null) return null;
        return fromMarkDown(action.getDescription());
    }

    public Sequence<RamlParameterModel> getParameters() {
        Sequence<RamlParameterModel> uriParameters = sequence();

        if (action.getResource().getResolvedUriParameters() != null)
            uriParameters = sequence(action.getResource().getResolvedUriParameters().entrySet()).map(uriParameterToModel());

        Sequence<RamlParameterModel> queryParameters = sequence();
        if (action.getQueryParameters() != null)
            queryParameters = sequence(action.getQueryParameters().entrySet()).map(queryParameterToModel());

        Sequence<RamlParameterModel> headerParameters = sequence();
        if (action.getHeaders() != null)
            headerParameters = sequence(action.getHeaders().entrySet()).map(headerParameterToModel());

        return uriParameters.join(queryParameters).join(headerParameters);
    }

    public Sequence<RamlRequestModel> getRequests() {
        if (action.getBody() == null) return sequence();
        Sequence<RamlHeaderModel> headers = sequence();
        if (action.getHeaders() != null) headers = sequence(action.getHeaders().entrySet()).map(asRamlHeaderModel());
        return sequence(action.getBody().values()).map(asRamlRequestModel(headers));
    }

    public Sequence<RamlResponseModel> getResponses() {
        if (action.getResponses() == null) return sequence();
        return sequence(action.getResponses().entrySet()).fold(Sequences.<RamlResponseModel>sequence(), asRamlResponseModel());
    }

    public Sequence<RamlSecurityModel> getSecurity() {
        return securityModels.filter(forReferences(action.getSecuredBy()));
    }

    public boolean isDeprecated() {
        return action.getIs().contains("deprecated");
    }

    public static RamlActionModel model(Action action, Sequence<RamlSecurityModel> securityModels) {
        return new RamlActionModel(action, securityModels);
    }

    private static Predicate<RamlSecurityModel> forReferences(final List<SecurityReference> references) {
        return new Predicate<RamlSecurityModel>() {
            @Override
            public boolean matches(RamlSecurityModel model) {
                for (SecurityReference reference : references)
                    if (model.getName().equals(reference.getName())) return true;

                return false;
            }
        };
    }
}
