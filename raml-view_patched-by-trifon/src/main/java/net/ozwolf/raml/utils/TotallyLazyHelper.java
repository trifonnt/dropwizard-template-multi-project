package net.ozwolf.raml.utils;

import net.ozwolf.raml.model.*;
import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Sequence;
import org.raml.model.*;
import org.raml.model.parameter.Header;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import java.util.Map;

import static com.googlecode.totallylazy.Sequences.first;
import static com.googlecode.totallylazy.Sequences.sequence;

public class TotallyLazyHelper {
    public static Callable1<Resource, RamlResourceModel> asRamlResourceModel(final Sequence<RamlSecurityModel> security) {
        return new Callable1<Resource, RamlResourceModel>() {
            @Override
            public RamlResourceModel call(Resource resource) throws Exception {
                return RamlResourceModel.model(resource, security);
            }
        };
    }

    public static Callable1<DocumentationItem, RamlDocumentationModel> asRamlDocumentationModel() {
        return new Callable1<DocumentationItem, RamlDocumentationModel>() {
            @Override
            public RamlDocumentationModel call(DocumentationItem item) throws Exception {
                return RamlDocumentationModel.model(item);
            }
        };
    }

    public static Callable1<Action, RamlActionModel> asRamlActionModel(final Sequence<RamlSecurityModel> securityModels) {
        return new Callable1<Action, RamlActionModel>() {
            @Override
            public RamlActionModel call(Action action) throws Exception {
                return RamlActionModel.model(action, securityModels);
            }
        };
    }

    public static Callable1<Map.Entry<String, UriParameter>, RamlParameterModel> uriParameterToModel() {
        return new Callable1<Map.Entry<String, UriParameter>, RamlParameterModel>() {
            @Override
            public RamlParameterModel call(Map.Entry<String, UriParameter> parameter) throws Exception {
                return RamlParameterModel.model(parameter.getKey(), parameter.getValue());
            }
        };
    }

    public static Callable1<Map.Entry<String, QueryParameter>, RamlParameterModel> queryParameterToModel() {
        return new Callable1<Map.Entry<String, QueryParameter>, RamlParameterModel>() {
            @Override
            public RamlParameterModel call(Map.Entry<String, QueryParameter> parameter) throws Exception {
                return RamlParameterModel.model(parameter.getKey(), parameter.getValue());
            }
        };
    }

    public static Callable1<Map.Entry<String, Header>, RamlParameterModel> headerParameterToModel() {
        return new Callable1<Map.Entry<String, Header>, RamlParameterModel>() {
            @Override
            public RamlParameterModel call(Map.Entry<String, Header> parameter) throws Exception {
                return RamlParameterModel.model(parameter.getKey(), parameter.getValue());
            }
        };
    }

    public static Callable1<MimeType, RamlRequestModel> asRamlRequestModel(final Sequence<RamlHeaderModel> headers) {
        return new Callable1<MimeType, RamlRequestModel>() {
            @Override
            public RamlRequestModel call(MimeType mimeType) throws Exception {
                return RamlRequestModel.model(mimeType, headers);
            }
        };
    }

    public static Callable1<Map.Entry<String, Header>, RamlHeaderModel> asRamlHeaderModel() {
        return new Callable1<Map.Entry<String, Header>, RamlHeaderModel>() {
            @Override
            public RamlHeaderModel call(Map.Entry<String, Header> header) throws Exception {
                return RamlHeaderModel.model(header.getKey(), header.getValue());
            }
        };
    }

    public static Callable2<Sequence<RamlResponseModel>, Map.Entry<String, Response>, Sequence<RamlResponseModel>> asRamlResponseModel() {
        return new Callable2<Sequence<RamlResponseModel>, Map.Entry<String, Response>, Sequence<RamlResponseModel>>() {
            @Override
            public Sequence<RamlResponseModel> call(Sequence<RamlResponseModel> models, Map.Entry<String, Response> response) throws Exception {
                Sequence<RamlResponseModel> result = models;
                Integer code = Integer.valueOf(response.getKey());
                Sequence<RamlHeaderModel> headers = sequence(response.getValue().getHeaders().entrySet()).map(asRamlHeaderModel());

                for (MimeType mimeType : response.getValue().getBody().values())
                    result = result.append(RamlResponseModel.model(code, response.getValue().getDescription(), headers, mimeType));

                return result;
            }
        };
    }

    public static Callable1<Map<String, SecurityScheme>, RamlSecurityModel> asRamlSecurityModel() {
        return new Callable1<Map<String, SecurityScheme>, RamlSecurityModel>() {
            @Override
            public RamlSecurityModel call(Map<String, SecurityScheme> security) throws Exception {
                return RamlSecurityModel.model(first(security.entrySet()).getKey(), first(security.entrySet()).getValue());
            }
        };
    }
}
