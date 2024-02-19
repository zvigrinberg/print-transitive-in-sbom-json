package org.redhat.zgrinber;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length != 2)
        {
            System.out.println("Usage: java -jar </path/to/jarfile> </path/to/sbom.json> <purl full package url ( purl) string as shown in sbom ref>");
            System.out.println("example: java -jar /path/to/jarfile.jar /path/to/sbom.json  pkg:golang/google.golang.org/api@v0.8.0");
        }
        else {
            printPackageDependencies(args[0], args[1]);
        }

    }

    private static void printPackageDependencies(String SBOMPath, String packageNameFull) throws IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.readTree(Files.readString(Path.of(SBOMPath)));
        ArrayNode jsonArray = (ArrayNode) json.get("dependencies");
        JsonNode jsonPackage =  getPackageJsonFromDependencies(packageNameFull, jsonArray);
        printAllDependencies(jsonPackage,jsonArray);


    }

    private static void printAllDependencies(JsonNode jsonPackage, ArrayNode jsonArray) {
        ArrayNode arrayJsonOfDependsOn = (ArrayNode) jsonPackage.get("dependsOn");
        if(arrayJsonOfDependsOn == null || arrayJsonOfDependsOn.size() == 0) {
            return;
        }
        arrayJsonOfDependsOn.forEach( dep -> {
            System.out.println(dep.asText());
            JsonNode currentDependency = getPackageJsonFromDependencies(dep.asText(), jsonArray);
            if(currentDependency != null) {
                printAllDependencies(currentDependency, jsonArray);
            }
        });
    }

    private static JsonNode getPackageJsonFromDependencies(String packageNameFull, ArrayNode jsonArray) {
        for (JsonNode jsonNode : jsonArray) {
            if (jsonNode.get("ref").textValue().trim().equals(packageNameFull)) {
                return jsonNode;
            }
        }
        return null;

    }
}