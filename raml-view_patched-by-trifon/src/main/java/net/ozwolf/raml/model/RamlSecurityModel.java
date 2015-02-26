package net.ozwolf.raml.model;

import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Pair;
import com.googlecode.totallylazy.Sequence;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.raml.model.Response;
import org.raml.model.SecurityScheme;

import java.util.Map;

import static net.ozwolf.raml.utils.TotallyLazyHelper.asRamlHeaderModel;
import static net.ozwolf.raml.utils.TotallyLazyHelper.queryParameterToModel;
import static com.googlecode.totallylazy.Sequences.sequence;

public class RamlSecurityModel {
    private final String name;
    private final SecurityScheme security;

    public RamlSecurityModel(String name, SecurityScheme security) {
        this.name = name;
        this.security = security;
    }

    public String getId() {
        return Hex.encodeHexString(security.getType().getBytes( Charsets.UTF_8 )); //@Trifon
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return security.getType();
    }

    public Sequence<RamlHeaderModel> getHeaders() {
        if (security.getDescribedBy() == null || security.getDescribedBy().getHeaders() == null) return sequence();
        return sequence(security.getDescribedBy().getHeaders().entrySet()).map(asRamlHeaderModel());
    }

    public Sequence<RamlParameterModel> getQueryParameters() {
        if (security.getDescribedBy() == null || security.getDescribedBy().getQueryParameters() == null)
            return sequence();
        return sequence(security.getDescribedBy().getQueryParameters().entrySet()).map(queryParameterToModel());
    }

    public Sequence<Pair<Integer, String>> getResponses() {
        if (security.getDescribedBy() == null || security.getDescribedBy().getResponses() == null) return sequence();
        return sequence(security.getDescribedBy().getResponses().entrySet()).map(asSecurityResponse());
    }

    public static RamlSecurityModel model(String name, SecurityScheme security) {
        return new RamlSecurityModel(name, security);
    }

    @Override
    public String toString(){
        return String.format("Security = [%s]", getType());
    }

    private static Callable1<Map.Entry<String, Response>, Pair<Integer, String>> asSecurityResponse() {
        return new Callable1<Map.Entry<String, Response>, Pair<Integer, String>>() {
            @Override
            public Pair<Integer, String> call(Map.Entry<String, Response> response) throws Exception {
                Integer code = Integer.valueOf(response.getKey());
                String description = (response.getValue().getDescription() == null) ? "" : response.getValue().getDescription();
                return Pair.pair(code, description);
            }
        };
    }
}
