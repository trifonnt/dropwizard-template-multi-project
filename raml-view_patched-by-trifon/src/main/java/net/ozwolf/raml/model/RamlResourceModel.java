package net.ozwolf.raml.model;

import com.googlecode.totallylazy.Sequence;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Resource;

import static net.ozwolf.raml.utils.MarkDownHelper.fromMarkDown;
import static net.ozwolf.raml.utils.TotallyLazyHelper.asRamlActionModel;
import static net.ozwolf.raml.utils.TotallyLazyHelper.asRamlResourceModel;
import static com.googlecode.totallylazy.Sequences.last;
import static com.googlecode.totallylazy.Sequences.sequence;

public class RamlResourceModel {
    private final Resource resource;
    private final Sequence<RamlSecurityModel> security;

    public RamlResourceModel(Resource resource, Sequence<RamlSecurityModel> security) {
        this.resource = resource;
        this.security = security;
    }

    public String getId() {
        return Hex.encodeHexString(getUri().getBytes( Charsets.UTF_8 )); //@Trifon
    }

    public String getUri() {
        return resource.getParentUri() + resource.getRelativeUri();
    }

    public String getDisplayName() {
        String defaultDisplayName = getUri().equals("/") ? "Root" : last(sequence(getUri().split("/")));
        return StringUtils.defaultString(resource.getDisplayName(), defaultDisplayName);
    }

    public Sequence<RamlActionModel> getActions() {
        if (resource.getActions() == null) return sequence();
        return sequence(resource.getActions().values()).map(asRamlActionModel(security));
    }

    public Sequence<RamlResourceModel> getResources() {
        if (resource.getResources() == null) return sequence();
        return sequence(resource.getResources().values()).map(asRamlResourceModel(security));
    }

    public String getDescription() {
        if (resource.getDescription() == null) return null;
        return fromMarkDown(resource.getDescription());
    }

    @Override
    public String toString() {
        return String.format("Resource = [%s]", getUri());
    }

    public static RamlResourceModel model(Resource resource, Sequence<RamlSecurityModel> security) {
        return new RamlResourceModel(resource, security);
    }
}
