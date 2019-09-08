package com.whyn.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

class InjectManifest implements Plugin<Project> {
    private static final String SAVED_ANDROID_MANIFEST = "AndroidManifest_old.xml"

    void apply(Project project) {
        project.android.defaultConfig.javaCompileOptions.annotationProcessorOptions
                .arguments = ['AndroidManifestPath': project.android.sourceSets.main.manifest.srcFile.absolutePath]
        dependencies(project);
        project.extensions.create('manifestConfig', ManifestConfigExt, project)

        project.afterEvaluate {

            def variants = determineVariants(project)
            project.android[variants].all { variant ->
                configureVariant(project, variant)
            }

        }
    }

    private static String determineVariants(Project project) {
        if (project.plugins.findPlugin('com.android.application')) {
            return 'applicationVariants';
        } else if (project.plugins.findPlugin('com.android.library')) {
            return 'libraryVariants';
        } else {
            throw new ProjectConfigurationException('The com.android.application or com.android.library plugin must be applied to the project', null)
        }
    }


    private static void configureVariant(Project project, def variant) {
        def javaCompile = variant.hasProperty('javaCompiler') ? variant.javaCompiler : variant.javaCompile
        javaCompile.doLast {
            println "detected: originManifestPath:${project.manifestConfig.originManifestPath}"
            println "detected: genManifestPath:$project.buildDir/generated/source/apt/${variant.buildType.name}/AndroidManifest.xml"
            println "detected: saveOrigin:${project.manifestConfig.saveOrigin}"

            def genManifest = new File("$project.buildDir/generated/source/apt/${variant.buildType.name}/AndroidManifest.xml")
            def originManifest = new File(project.manifestConfig.originManifestPath);
            if (genManifest.exists()) {
                check(originManifest, project.manifestConfig.saveOrigin)
                deleteOriginal(originManifest)
                if (genManifest.renameTo(originManifest)) {
                    println 'Inject AndroidManifest successfully.'
                } else {
                    println 'Inject AndriodManifest failed.'
                }
            }
        }
    }

    private static void check(File originManifest, boolean saved) {
        String parentDir = originManifest.getParent();
        if (parentDir == null)
            return;
        File savedFile = new File(parentDir, SAVED_ANDROID_MANIFEST)
        if (saved && !savedFile.exists()) {
            if (originManifest.renameTo(savedFile)) {
                println "original $originManifest.name has been saved as $SAVED_ANDROID_MANIFEST"
            } else {
                println "failed to saved original $originManifest.name"
            }
        }
    }

    private static void deleteOriginal(File originManifest) {
        if (originManifest.exists())
            originManifest.delete()
    }


    private static void dependencies(Project project) {
        project.repositories {
            maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
        }

        project.dependencies {
            annotationProcessor 'com.link184:injectmanifest-compiler:0.1.6-SNAPSHOT'
            compileOnly 'com.link184:injectmanifest-annotations:0.1.2-SNAPSHOT'
        }
    }

}
//
class ManifestConfigExt {
    def String originManifestPath
    def String genManifestPath
    def boolean saveOrigin

    ManifestConfigExt(Project project) {
//        originManifestPath = "$project.projectDir/src/main/AndroidManifest.xml"
        originManifestPath = project.android.sourceSets.main.manifest.srcFile.absolutePath
        genManifestPath = "$project.buildDir/generated/source/apt/release/AndroidManifest.xml"
        saveOrigin = true
    }

}


