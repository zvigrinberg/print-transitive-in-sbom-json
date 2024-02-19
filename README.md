# print-transitive-in-sbom-json
A Java application that prints all transitive packages of a given package in a CycloneDx SBOM json file

## Instructions

1. build jar with all dependencies
```shell
mvn clean install
```

2. Run the application using the built jar,
Usage is:
```shell
[zgrinber@zgrinber sbom-json-traversor]$ java -jar ~/.m2/repository/org/redhat/zgrinber/sbom-json-traversor/1.0-SNAPSHOT/sbom-json-traversor-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Output:
```shell
Usage: java -jar </path/to/jarfile> </path/to/sbom.json> <purl full package url ( purl) string as shown in sbom ref>
example: java -jar /path/to/jarfile.jar /path/to/sbom.json  pkg:golang/google.golang.org/api@v0.8.0
```
Example
```shell
java -jar ~/.m2/repository/org/redhat/zgrinber/sbom-json-traversor/1.0-SNAPSHOT/sbom-json-traversor-1.0-SNAPSHOT-jar-with-dependencies.jar example-go-sbom.json pkg:golang/google.golang.org/api@v0.8.0
```
Output:
```shell
pkg:golang/cloud.google.com/go@v0.38.0
pkg:golang/github.com/golang/mock@v1.2.0
pkg:golang/github.com/golang/protobuf@v1.2.0
pkg:golang/github.com/google/btree@v0.0.0-20180813153112-4030bb1f1f0c
pkg:golang/github.com/google/go-cmp@v0.2.0
pkg:golang/github.com/google/martian@v2.1.0%2Bincompatible
pkg:golang/github.com/google/pprof@v0.0.0-20181206194817-3ea8567a2e57
pkg:golang/github.com/googleapis/gax-go/v2@v2.0.4
pkg:golang/google.golang.org/grpc@v1.19.0
pkg:golang/cloud.google.com/go@v0.26.0
..............
..............
```
