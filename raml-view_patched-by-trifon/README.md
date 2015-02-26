# --- MAINTENANCE MODE ---

# DropWizard RAML API Resource Bundle

[![Build Status](https://travis-ci.org/ozwolf-software/dropwizard-raml-view.svg?branch=dropwizard-0.7-jdk-7)](https://travis-ci.org/ozwolf-software/dropwizard-raml-view)

This bundle allows a RAML specification to be attached to the resources of the service, exposing a human-readable, HTML representation of the RAML specification on the `/api` resource.

## RAML Specification

The RAML specification documentation can be found [`here`](https://github.com/raml-org/raml-spec)

## Compatability

This project has 3 compatability branches:

+ **DropWizard 0.7** for **Java 8** - `LIVE` - New features will be applied primarily to this branch.
    + *Version Numbers* 
        + `3.*`
+ **DropWizard 0.7** for **Java 7** - `MAINTENANCE` - Only "must-have" features will be applied.
    + *Version Numbers*
        + `2.*`
        + `0.7-*`
+ **DropWizard 0.6** for **Java 7** - `END OF LIFE` - This branch is no longer maintained.
    + *Version Numbers*
        + `0.6-*`
        + `1.0.*`
        + `1.1.*`

## Endpoints

The following endpoints will be made available on your service:

+ `/api` - The API specification as a HTML viewable endpoint.
+ `/api/raw` - The raw RAML specification as re-emitted from the RAML JSON Parser.

## Example Usage

To add this bundle to your service and expose a RAML specification under the `/api` resource.

```java
bootstrap.addBundle(RamlView.bundle("apispecs/apispecs.raml"));
```

## Functionality Caveat

This project currently provides functionality that meet my usage needs for RAML specifications.  It is by no means a comprehensive HTML representation of the RAML specification.  This means particular components that other people need _will_ be missing.

## Contributions

Contributions to this project are welcome.

## Dependencies

This project uses the following dependency projects:

+ [RAML Java Parser](https://github.com/raml-org/raml-java-parser)
+ [DropWizard](https://github.com/dropwizard/dropwizard)
+ [Commons Language 3](https://github.com/apache/commons-lang)
+ [Commons Codec](https://github.com/apache/commons-codec)
+ [Markdown4j](https://github.com/jdcasey/markdown4j)
+ [TotallyLazy](https://github.com/daviddenton/totallylazy)