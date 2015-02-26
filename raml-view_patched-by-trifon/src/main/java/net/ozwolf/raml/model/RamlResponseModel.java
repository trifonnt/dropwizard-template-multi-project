package net.ozwolf.raml.model;

import com.googlecode.totallylazy.Sequence;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.raml.model.MimeType;

import javax.ws.rs.core.MediaType;

import static net.ozwolf.raml.utils.MarkDownHelper.fromMarkDown;

public class RamlResponseModel {
    private final Integer code;
    private final String description;
    private final Sequence<RamlHeaderModel> headers;
    private final MimeType mimeType;

    public RamlResponseModel(Integer code, String description, Sequence<RamlHeaderModel> headers, MimeType mimeType) {
        this.code = code;
        this.description = description;
        this.headers = headers;
        this.mimeType = mimeType;
    }

    public String getId() {
        return Hex.encodeHexString((code + getContentType()).getBytes( Charsets.UTF_8 )); //@Trifon
    }

    public String getDescription() {
        return fromMarkDown(description);
    }

    public Integer getCode() {
        return code;
    }

    public Sequence<RamlHeaderModel> getHeaders() {
        return headers;
    }

    public String getContentType() {
        return mimeType.getType();
    }

    public boolean isJson() {
        return getContentType().equals(MediaType.APPLICATION_JSON);
    }

    public String getExample() {
        return mimeType.getExample();
    }

    public String getSchema() {
        return mimeType.getSchema();
    }

    @Override
    public String toString() {
        return String.format("Response = [%d - %s]", getCode(), getContentType());
    }

    public static RamlResponseModel model(Integer code, String description, Sequence<RamlHeaderModel> headers, MimeType mimeType) {
        return new RamlResponseModel(code, description, headers, mimeType);
    }
}
