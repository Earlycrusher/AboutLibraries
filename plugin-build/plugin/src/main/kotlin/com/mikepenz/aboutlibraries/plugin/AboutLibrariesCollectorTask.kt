package com.mikepenz.aboutlibraries.plugin

import com.mikepenz.aboutlibraries.plugin.model.CollectedContainer
import com.mikepenz.aboutlibraries.plugin.util.DependencyCollector
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

@CacheableTask
abstract class AboutLibrariesCollectorTask : DefaultTask() {

    /** holds the collected set of dependencies*/
    @Internal
    protected lateinit var collectedDependencies: CollectedContainer

    /**
     * Collect the dependencies via the available configurations for the current project
     */
    fun configure() {
        collectedDependencies = DependencyCollector().collect(project)
    }

    @OutputFile
    protected val dependencyCache = File(project.buildDir, "generated/aboutLibraries/dependency_cache.json")

    @TaskAction
    fun action() {
        if (!::collectedDependencies.isInitialized) {
            configure()
        }
        dependencyCache.writeText(groovy.json.JsonOutput.toJson(collectedDependencies))
    }
}