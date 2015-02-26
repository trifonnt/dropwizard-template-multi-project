# DropWizard RAML API Resource Bundle

This bundle allows a RAML specification to be attached to the resources of the service, exposing a human-readable, HTML representation of the RAML specification on the `/api` resource.

## RAML Specification

The RAML specification documentation can be found [here](https://github.com/raml-org/raml-spec)

## DropWizard Compatability

This bundle is compatible with both DropWizard 0.6 and DropWizard 0.7 and the dependency is based on the version number used.
 
### DropWizard 0.6

Version numbers are in the format `0.6-<library revision>` (eg. `0.6-1`)

### DropWizard 0.7

Version numbers are in the format `0.7-<library revision>` (eg. `0.7-2`)

### Future DropWizard Versions

Version numbers will continue to follow the pattern of DropWizards `<major>.<minor>-<library revision>` structure.

### Legacy DropWizard 0.6 Versions

The following versions are now considered `DEPRECATED`, but still acceptable for use in DropWizard 0.6:

+ `1.0.0`
+ `1.0.1`
+ `1.0.2`
+ `1.0.3`
+ `1.0.4`
+ `1.0.5`
+ `1.1.0`

## Dependency Information

### Maven

The dependency information for this library is:

```xml
<dependency>
    <groupId>${project.groupId}</groupId>
    <artifactId>${project.artifactId}</artifactId>
    <version>${project.version}</version>
</dependency>
```

### Apache Ivy

```xml
<dependency org="${project.groupId}" name="${project.artifactId}" rev="${project.version}"/>
```

### Gradle

```groovy
dependencies {
    runtime group: '${project.groupId}', name: '${project.artifactId}', version: '${project.version}'
}
```

## Endpoints

The following endpoints will be made available on your service:

+ `/api` - The API specification as a HTML viewable endpoint.
+ `/api/raw` - The raw RAML specification as re-emitted from the RAML JSON Parser.

## Example Usage

To add this bundle to your service and expose a RAML specification under the `/api` resource.

```java
bootstrap.addBundle(ApiSpecsBundle.bundle("apispecs/apispecs.raml"));
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