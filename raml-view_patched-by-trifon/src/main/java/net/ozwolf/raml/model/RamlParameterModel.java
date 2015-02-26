package net.ozwolf.raml.model;

import com.googlecode.totallylazy.Sequence;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.Header;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import static net.ozwolf.raml.utils.MarkDownHelper.fromMarkDown;
import static com.googlecode.totallylazy.Sequences.sequence;

public class RamlParameterModel {
    private final String name;
    private final AbstractParam parameter;

    public RamlParameterModel(String name, AbstractParam parameter) {
        this.name = name;
        this.parameter = parameter;
    }

    public String getName() {
        return name;
    }

    public String getParameterType() {
        if (parameter instanceof UriParameter) return "path";
        if (parameter instanceof QueryParameter) return "query";
        if (parameter instanceof Header) return "header";
        return "unknown";
    }

    public String getDataType() {
        return parameter.getType().name().toLowerCase();
    }

    public String getFlags() {
        Sequence<String> flags = sequence(parameter.isRequired() ? "required" : "optional");
        if (parameter.isRepeat()) flags = flags.append("repeatable");
        return flags.toString(", ");
    }

    public String getDescription() {
        return fromMarkDown(parameter.getDescription());
    }

    public String getPattern() {
        return parameter.getPattern();
    }

    public String getExample() {
        return parameter.getExample();
    }

    public String getDefault(){
        return parameter.getDefaultValue();
    }

    public Sequence<String> getAllowedValues() {
        return sequence(parameter.getEnumeration());
    }

    public String getDisplay() {
        if (parameter.getPattern() != null) return parameter.getPattern();
        if (parameter.getExample() != null) return parameter.getExample();
        return "value";
    }

    @Override
    public String toString(){
        return String.format("Parameter = [%s - %s]", getName(), getParameterType());
    }

    public static RamlParameterModel model(String name, AbstractParam parameter) {
        return new RamlParameterModel(name, parameter);
    }
}
