package net.ozwolf.raml.model;

import org.raml.model.parameter.Header;

public class RamlHeaderModel {
    private final String name;
    private final Header header;

    public RamlHeaderModel(String name, Header header) {
        this.name = name;
        this.header = header;
    }

    public String getName(){
        return name;
    }

    public String getDisplay(){
        if (header.getPattern() != null) return header.getPattern();
        if (header.getExample() != null) return header.getExample();
        return "value";
    }

    @Override
    public String toString(){
        return String.format("Header = [%s]", getName());
    }

    public static RamlHeaderModel model(String name, Header header){
        return new RamlHeaderModel(name, header);
    }
}
