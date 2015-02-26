package net.ozwolf.raml.model;

import com.googlecode.totallylazy.Sequence;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.raml.model.MimeType;

import javax.ws.rs.core.MediaType;

public class RamlRequestModel {
    private final MimeType mimeType;
    private final Sequence<RamlHeaderModel> headers;

    public RamlRequestModel(MimeType mimeType, Sequence<RamlHeaderModel> headers) {
        this.mimeType = mimeType;
        this.headers = headers;
    }

    public String getId(){
        return Hex.encodeHexString(getContentType().getBytes( Charsets.UTF_8 )); //@Trifon
    }

    public String getContentType(){
        return mimeType.getType();
    }

    public Sequence<RamlHeaderModel> getHeaders(){
        return headers;
    }

    public boolean isJson(){
        return getContentType().equals(MediaType.APPLICATION_JSON);
    }

    public String getExample(){
        return mimeType.getExample();
    }

    public String getSchema(){
        return mimeType.getSchema();
    }

    @Override
    public String toString(){
        return String.format("Request = [%s]", getContentType());
    }

    public static RamlRequestModel model(MimeType mimeType, Sequence<RamlHeaderModel> headers){
        return new RamlRequestModel(mimeType, headers);
    }
}
