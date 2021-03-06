//package com.bytelegend.utils
//
//import org.apache.maven.model.io.xpp3.MavenXpp3Reader
//import java.io.File
//import java.io.FileReader
//import java.lang.StringBuilder
//
///**
// * This converts pom.xml of https://github.com/blindpirate/dynamodb-cross-region-library/commits/master
// * to dynamodb-cross-region-library.gradle.kts
// */
//class ConvertPomToGradleKts {
//}
//
//fun main(args: Array<String>) {
//    val inputPomXml = File("/Users/zhb/Projects/bytelegend-app/server/dynamodb-cross-region-library/pom.xml")//File(args[0])
//    val outputGradleKts = File("/Users/zhb/Projects/bytelegend-app/server/sync-server/dynamodb-cross-region-library.gradle.kts")//File(args[0])
////    val outputGradleKts = File(args[1])
//
//    val mavenReader = MavenXpp3Reader()
//    val model = mavenReader.read(FileReader(inputPomXml))
//
//    // <properties> in pom
//    val properties = model.properties
//    val dependencies = model.dependencies
//
//    val awsJavaSdkBom = "com.amazonaws:aws-java-sdk-bom:${properties.getProperty("aws.java.sdk.version")!!}"
//    val otherDependencies = dependencies.map {
//        it.scope to
//        when {
//            it.version == null -> "${it.groupId}:${it.artifactId}"
//            it.version.startsWith("$") -> "${it.groupId}:${it.artifactId}:${properties.getProperty(it.version.substring(0, it.version.length - 1).substring(2))!!}"
//            else -> "${it.groupId}:${it.artifactId}:${it.version}"
//        }
//    }.joinToString("\n") {
//        val configuration = if(it.first == "test") "testImplementation" else "implementation"
//        "$configuration(\"${it.second}\")"
//    }
//
//    outputGradleKts.writeText(
//        """
//        /**** THIS FILE IS AUTO-GENERATED BY ConvertPomToGradleKts, DO NOT MODIFY IT MANUALLY! ****/
//        plugins {
//            id("java-library")
//        }
//
//        dependencies {
//            implementation(platform("$awsJavaSdkBom"))
//            $otherDependencies
//        }
//        """
//    )
//}