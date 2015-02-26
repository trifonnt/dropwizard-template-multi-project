package net.ozwolf.raml.model;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.raml.model.DocumentationItem;

import static net.ozwolf.raml.utils.MarkDownHelper.fromMarkDown;

public class RamlDocumentationModel {
    private final DocumentationItem item;

    public RamlDocumentationModel(DocumentationItem item) {
        this.item = item;
    }

    public String getId() {
        return Hex.encodeHexString(getTitle().getBytes( Charsets.UTF_8 )); //@Trifon
    }

    public String getTitle() {
        return item.getTitle();
    }

    public String getContent() {
        if (item.getContent() == null) return null;
        return fromMarkDown(item.getContent());
    }

    @Override
    public String toString(){
        return String.format("Documentation = [%s]", getTitle());
    }

    public static RamlDocumentationModel model(DocumentationItem item) {
        return new RamlDocumentationModel(item);
    }
}
