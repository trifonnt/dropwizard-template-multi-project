package net.ozwolf.raml.model;

import com.googlecode.totallylazy.Predicate;
import com.googlecode.totallylazy.Sequence;
import org.junit.BeforeClass;
import org.junit.Test;
import org.raml.model.ActionType;
import org.raml.model.Protocol;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static net.ozwolf.raml.configuration.ApiSpecsConfiguration.configuration;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ModelTest {
    private static RamlModel model;

    @BeforeClass
    public static void setUp() {
        ModelTest.model = RamlModel.model("apispecs-test/apispecs.raml", configuration());
    }

    @Test
    public void shouldLoadRamlAndExposeModelInformationForView() {
        assertThat(model.getTitle(), is("Test API"));
        assertThat(model.getVersion(), is("1.0-SNAPSHOT"));
        assertThat(model.getProtocols(), hasItem(Protocol.HTTP));
    }

    @Test
    public void shouldLoadResourcesAsModels() {
        Sequence<RamlResourceModel> resources = model.getResources();

        assertThat(resources.size(), is(1));

        assertThat(resources.first().getId(), is("2f626f6f6b73"));
        assertThat(resources.first().getDisplayName(), is("Books"));
        assertThat(resources.first().getDescription(), is("<p>resource for getting and managing <code>books</code></p>\n"));
        assertThat(resources.first().getUri(), is("/books"));

        assertThat(resources.first().getActions().size(), is(2));

        assertThat(resources.first().getResources().size(), is(1));

        assertThat(resources.first().getResources().first().getId(), is("2f626f6f6b732f7b69647d"));
        assertThat(resources.first().getResources().first().getActions().size(), is(1));
    }

    @Test
    public void shouldLoadDocumentation() {
        Sequence<RamlDocumentationModel> documentation = model.getDocumentation();

        assertThat(documentation.size(), is(1));

        assertThat(documentation.first().getId(), is("53756d6d617279"));
        assertThat(documentation.first().getTitle(), is("Summary"));
        assertThat(documentation.first().getContent(), is("<h2>Summary</h2>\n<p>This API specification is for a test purposes only.  This should use <code>markdown4j</code> to parse the Markdown.</p>\n"));
    }

    @Test
    public void shouldLoadPostActionInformation() throws IOException {
        Sequence<RamlResourceModel> resources = model.getResources();

        assertThat(resources.size(), is(1));

        assertThat(resources.first().getUri(), is("/books"));

        Sequence<RamlActionModel> actions = resources.first().getActions();

        assertThat(actions.size(), is(2));

        RamlActionModel post = actions.find(forType(ActionType.POST))
                .getOrThrow(new IllegalArgumentException("No [POST] action found on resource"));

        // Test basic action information
        assertThat(post.getType(), is("POST"));
        assertThat(post.getDescription(), is("<p>add a book to the collection</p>\n"));
        assertThat(post.isDeprecated(), is(true));

        // Test security information
        assertThat(post.getSecurity().size(), is(1));
        verifyBasicSecurity(post.getSecurity().first());

        // Test request information
        assertThat(post.getRequests().size(), is(1));
        assertThat(post.getRequests().first().getId(), is("6170706c69636174696f6e2f6a736f6e"));
        assertThat(post.getRequests().first().getContentType(), is("application/json"));
        assertThat(post.getRequests().first().isJson(), is(true));

        assertThat(post.getRequests().first().getHeaders().size(), is(1));
        assertThat(post.getRequests().first().getHeaders().first().getName(), is("x-call-id"));
        assertThat(post.getRequests().first().getHeaders().first().getDisplay(), is("abcd-efab-1234-5678"));
        assertThat(post.getRequests().first().getHeaders().first().getDisplay(), is("abcd-efab-1234-5678"));

        assertThat(post.getRequests().first().getExample(), is(fixture("apispecs-test/examples/book-post-request.json")));
        assertThat(post.getRequests().first().getSchema(), is(fixture("apispecs-test/schemas/book-request.json")));

        // Test parameter information
        assertThat(post.getParameters().size(), is(1));
        assertThat(post.getParameters().first().getName(), is("x-call-id"));

        // Test response information
        assertThat(post.getResponses().size(), is(2));

        RamlResponseModel response = post.getResponses()
                .find(forCodeAndContentType(201, MediaType.APPLICATION_JSON))
                .getOrThrow(new IllegalArgumentException("No response found for [201, application/json]"));

        assertThat(response.getDescription(), is("<p>book successfully added to collection</p>\n"));
        assertThat(response.getCode(), is(201));
        assertThat(response.getContentType(), is(MediaType.APPLICATION_JSON));
        assertThat(response.isJson(), is(true));
        assertThat(response.getExample(), is(fixture("apispecs-test/examples/book-response.json")));
        assertThat(response.getSchema(), is(fixture("apispecs-test/schemas/book-response.json")));
    }

    @Test
    public void shouldLoadGetActionInformationForList() {
        Sequence<RamlResourceModel> resources = model.getResources();

        assertThat(resources.size(), is(1));

        assertThat(resources.first().getUri(), is("/books"));

        Sequence<RamlActionModel> actions = resources.first().getActions();

        assertThat(actions.size(), is(2));

        RamlActionModel get = actions.find(forType(ActionType.GET))
                .getOrThrow(new IllegalArgumentException("No [GET] action found on resource"));

        // Test basic action information
        assertThat(get.getType(), is("GET"));
        assertThat(get.getDescription(), is("<p>return a list of books</p>\n"));
        assertThat(get.isDeprecated(), is(false));

        // Test security information
        assertThat(get.getSecurity().size(), is(1));
        assertThat(get.getSecurity().first().getQueryParameters().size(), is(1));
        assertThat(get.getSecurity().first().getQueryParameters().first().getName(), is("custom-sec"));

        // Test request information
        assertThat(get.getRequests(), empty());

        // Test parameter information
        assertThat(get.getParameters().size(), is(1));
        assertThat(get.getParameters().first().getName(), is("genre"));
        assertThat(get.getParameters().first().getDescription(), is("<p>the genre to filter on</p>\n"));
        assertThat(get.getParameters().first().getDataType(), is("string"));
        assertThat(get.getParameters().first().getFlags(), is("optional"));
        assertThat(get.getParameters().first().getAllowedValues().size(), is(6));
        assertThat(get.getParameters().first().getAllowedValues(), hasItems("War", "Crime", "Sci-Fi", "Romance", "Comedy", "Fantasy"));
        assertThat(get.getParameters().first().getPattern(), nullValue());
        assertThat(get.getParameters().first().getExample(), is("War"));
        assertThat(get.getParameters().first().getDisplay(), is("War"));
        assertThat(get.getParameters().first().getDefault(), is("War"));
    }

    @Test
    public void shouldLoadHeaderInformationFromResponse() {
        Sequence<RamlResourceModel> resources = model.getResources();

        assertThat(resources.size(), is(1));

        Sequence<RamlResourceModel> children = resources.first().getResources();

        assertThat(children.size(), is(1));

        assertThat(children.first().getUri(), is("/books/{id}"));

        RamlResponseModel response = children.first()
                .getActions()
                .find(forType(ActionType.GET))
                .getOrThrow(new IllegalArgumentException("Could not find [GET] action"))
                .getResponses()
                .find(forCodeAndContentType(200, MediaType.APPLICATION_JSON))
                .getOrThrow(new IllegalArgumentException("No response found for [200, application/json]"));

        assertThat(response.getHeaders().size(), is(1));
        assertThat(response.getHeaders().first().getName(), is("x-call-id"));
        assertThat(response.getHeaders().first().getDisplay(), is("abcd-efab-1234-5678"));
    }

    private void verifyBasicSecurity(RamlSecurityModel model) {
        assertThat(model.getType(), is("basic"));
        assertThat(model.getHeaders().size(), is(1));
        assertThat(model.getHeaders().first().getName(), is("Authorization"));
        assertThat(model.getHeaders().first().getDisplay(), is("Basic (.*)"));
        assertThat(model.getResponses().size(), is(1));
        assertThat(model.getResponses().first().first(), is(401));
        assertThat(model.getResponses().first().second(), is("Invalid username or password provided\n"));
    }

    private static Predicate<RamlActionModel> forType(final ActionType actionType) {
        return new Predicate<RamlActionModel>() {
            @Override
            public boolean matches(RamlActionModel model) {
                return model.getType().equals(actionType.name());
            }
        };
    }

    private static Predicate<RamlResponseModel> forCodeAndContentType(final Integer code, final String contentType) {
        return new Predicate<RamlResponseModel>() {
            @Override
            public boolean matches(RamlResponseModel model) {
                return model.getCode().equals(code) && model.getContentType().equals(contentType);
            }
        };
    }
}
