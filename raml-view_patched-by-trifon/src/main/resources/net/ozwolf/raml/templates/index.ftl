[#ftl]
<!DOCTYPE html>
<html>
<head>
    <title>${model.title?html} (v${model.version?html})</title>
    <link rel="stylesheet" type="text/css" href="/api/assets/css/raml-docs-base.css"/>
    <link rel="stylesheet" type="text/css" href="/api/assets/css/raml-docs.css"/>

    [#if model.hasStylesheets()]
        [#list model.stylesheets as stylesheet]
            <link rel="stylesheet" type="text/css" href="${stylesheet}"/>
        [/#list]
    [/#if]
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
    <script src="//google-code-prettify.googlecode.com/svn/loader/run_prettify.js?lang=css&skin=default"></script>

    <script type="text/javascript">
        function toggleItemVisibility(id) {
            $("#" + id).toggle();
        }

        $(document).ready(function () {
            prettyPrint();
        });
    </script>
</head>
<body>
<div id="content">
    <!-- Page Title -->
    <div class="info">
        <strong>Version</strong>: ${model.version?html}
        <span class="right">
            [#list model.protocols as protocol]
            <code>${protocol}</code>
            [/#list]
            &nbsp;
            <a href="/api/raw">Raw Specification</a>
        </span>
    </div>
    <h1 id="title">
    ${model.title?html}
    </h1>

[#if model.documentation?has_content]
    <!-- Documentation -->
    <h2>Documentation</h2>

    <div id="documentation">
        [#list model.documentation as item]
            <h3><a href="javascript:toggleItemVisibility('documentation-${item.id}')">${item.title?html}</a></h3>

            <div class="highlight-block" id="documentation-${item.id}" style="display: none;">
            ${item.content}
            </div>
        [/#list]
    </div>
[/#if]

    <!-- Endpoints -->
    <h2>Endpoints</h2>

    <div id="endpoints">
    [#list model.resources as resource]
        <div class="endpoint">
            <div class="header">
                <a href="javascript:toggleItemVisibility('endpoint-${resource.id}')">${resource.displayName?html}</a>
                [#if resource.description??]
                    <div class="right">
                    ${resource.description}
                    </div>
                [/#if]
            </div>

            <div id="endpoint-${resource.id}" class="details" style="display: none;">
                [@printResource resource/]
            </div>
        </div>
    [/#list]
    </div>
</div>

[#macro printResource resource]
    [@printActions resource/]
    [#list resource.resources as child]
        [@printResource child/]
    [/#list]
[/#macro]

[#macro printActions resource]
    [#list resource.actions as action]
    <div class="uri ${action.type}">
        <div class="header">
            ${action.type} ${resource.uri}
            [#if action.deprecated]
            <span class="right">
                <code class="deprecated">DEPRECATED</code>
            </span>
            [/#if]
        </div>
        [#if action.description??]
        <div class="description">
            ${action.description}
        </div>
        [/#if]
        <div class="details">
            [@printParameters action/]
            [@printSecurity action resource.id/]
            [@printRequests action resource.id/]
            [@printResponses action resource.id/]
        </div>
    </div>
    [/#list]
[/#macro]

[#macro printParameters action]
    [#if action.parameters?has_content]
    <p class="section-header">
        Parameters
    </p>
    <div class="section">
        <table class="parameters">
            [#list action.parameters as parameter]
                <tr>
                    <td class="name">${parameter.name}</td>
                    <td>
                    <p>
                        <code>${parameter.parameterType}</code>
                        <code>${parameter.dataType}</code>
                        <span class="flags">(${parameter.flags})</span>
                        [#if parameter.description??]
                            ${parameter.description}
                        [/#if]
                        [#if parameter.default??]
                            <p>
                                <strong>Default:</strong>
                                <code>${parameter.default}</code>
                            </p>
                        [/#if]
                        [#if parameter.allowedValues?has_content]
                            <p>
                                <strong>Choices:</strong>
                                [#list parameter.allowedValues as allowedValue]
                                    <code>${allowedValue}</code>
                                [/#list]
                            </p>
                        [/#if]
                        [#if parameter.pattern??]
                            <p>
                                <strong>Pattern:</strong>
                                <code>${parameter.pattern}</code>
                            </p>
                        [/#if]
                        [#if parameter.example??]
                            <p>
                                <strong>Example:</strong>
                                <code>${parameter.example}</code>
                            </p>
                        [/#if]
                    </td>
                </tr>
            [/#list]
        </table>
    </div>
    [/#if]
[/#macro]

[#macro printSecurity action resourceId]
    [#list action.security as security]
    <p class="section-header">
        Security <code>${security.type}</code>
                <span class="right">
                    <a href="javascript:toggleItemVisibility('endpoint-${resourceId}-${action.type}-security-${security.id}')">Toggle</a>
                </span>
    </p>
    <div class="section" id="endpoint-${resourceId}-${action.type}-security-${security.id}" style="display: none;">
        [#if security.headers?has_content]
            <h5>Headers</h5>
            <div class="highlight-block">
                [#list security.headers as header]
                    <p>
                        <span class="label">${header.name}</span>: <span class="value">${header.display}</span>
                    </p>
                [/#list]
            </div>
        [/#if]
        [#if security.queryParameters?has_content]
            <h5>Query Parameters</h5>
            <div class="highlight-block">
                [#list security.queryParameters as queryParameter]
                    <p>
                        <span class="label">${queryParameter.name}</span>: <span class="value">${queryParameter.display}</span>
                    </p>
                [/#list]
            </div>
        [/#if]
        [#if security.responses?has_content]
            <h5>Responses</h5>
            <div class="highlight-block">
                [#list security.responses as response]
                    <p>
                        <span class="label">${response.first()}</span>: <span class="value">${response.second()}</span>
                    </p>
                [/#list]
            </div>
        [/#if]
    </div>
    [/#list]
[/#macro]

[#macro printRequests action resourceId]
    [#list action.requests as request]
    <p class="section-header">
        Request <code>${request.contentType}</code>
                <span class="right">
                    <a href="javascript:toggleItemVisibility('endpoint-${resourceId}-${action.type}-request-${request.id}')">Toggle</a>
                </span>
    </p>
    <div class="section" id="endpoint-${resourceId}-${action.type}-request-${request.id}" style="display: none;">
        <h5>Headers</h5>
        <div class="highlight-block">
            <span class="label">Content-Type</span>: <span class="value">${request.contentType}</span>
            [#list request.headers as header]
                <br/>
                <span class="label">${header.name}</span>: <span class="value">${header.display}</span>
            [/#list]
        </div>
        <h5>Example</h5>
        [#if request.example??]
            [#if request.json]
                <pre class="prettyprint lang-json">${request.example}</pre>
            [#else]
                <code>${request.example}</code>
            [/#if]
        [#else]
            <p class="alert">
                No <code>example</code> provided.
            </p>
        [/#if]
        <h5>Schema</h5>
        [#if request.schema??]
            [#if request.json]
                <pre class="prettyprint lang-json">${request.schema}</pre>
            [#else]
                <code>${request.schema}</code>
            [/#if]
        [#else]
            <p class="alert">
                No <code>schema</code> provided.
            </p>
        [/#if]
    </div>
    [/#list]
[/#macro]

[#macro printResponses action resourceId]
    [#list action.responses as response]
    <p class="section-header">
        Response <code>${response.code}</code><code>${response.contentType}</code>
                <span class="right">
                    <a href="javascript:toggleItemVisibility('endpoint-${resourceId}-${action.type}-response-${response.code}-${response.id}')">Toggle</a>
                </span>
    </p>
    <div class="section" id="endpoint-${resourceId}-${action.type}-response-${response.code}-${response.id}" style="display: none;">
        [#if response.description??]
            <div class="description">
                ${response.description}
            </div>
        [/#if]
        <p class="section-sub-header">
            Headers
        </p>
        <div class="highlight-block">
            <span class="label">Content-Type</span>: <span class="value">${response.contentType}</span>
            [#list response.headers as header]
                <br/>
                <span class="label">${header.name}</span>: <span class="value">${header.display}</span>
            [/#list]
        </div>

        <p class="section-sub-header">
            Example
            [#if response.example??]
            <span class="right">
                <a href="javascript: toggleItemVisibility('endpoint-${resourceId}-${action.type}-response-${response.code}-${response.id}-example')">View</a>
            </span>
            [/#if]
        </p>
        [#if response.example??]
            <div id="endpoint-${resourceId}-${action.type}-response-${response.code}-${response.id}-example" style="display: none;">
            [#if response.json]
                <pre class="prettyprint lang-json">${response.example}</pre>
            [#else]
                <code>${response.example}</code>
            [/#if]
            </div>
        [#else]
            <p class="alert">
                No <code>example</code> provided.
            </p>
        [/#if]
        <p class="section-sub-header">
            Schema
            [#if response.schema??]
            <span class="right">
                <a href="javascript: toggleItemVisibility('endpoint-${resourceId}-${action.type}-response-${response.code}-${response.id}-schema')">View</a>
            </span>
            [/#if]
        </p>
        [#if response.schema??]
            <div id="endpoint-${resourceId}-${action.type}-response-${response.code}-${response.id}-schema" style="display: none;">
            [#if response.json]
                <pre class="prettyprint lang-json">${response.schema}</pre>
            [#else]
                <code>${response.schema}</code>
            [/#if]
            </div>
        [#else]
            <p class="alert">
                No <code>schema</code> provided.
            </p>
        [/#if]
    </div>
    [/#list]
[/#macro]
</body>
</html>