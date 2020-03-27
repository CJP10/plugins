import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.json.JSONObject
import java.io.File
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


open class BootstrapTask : DefaultTask() {

    private fun formatDate(date: Date?) = with(date ?: Date()) {
        SimpleDateFormat("yyyy-MM-dd").format(this)
    }

    private fun hash(file: ByteArray): String {
        return MessageDigest.getInstance("SHA-512").digest(file).fold("", { str, it -> str + "%02x".format(it) }).toUpperCase()
    }

    @TaskAction
    fun boostrap() {
        if (project == project.rootProject) {
            val bootstrapDir = File("${project.buildDir}/bootstrap")
            val bootstrapReleaseDir = File("${project.buildDir}/bootstrap/release")

            bootstrapDir.mkdirs()
            bootstrapReleaseDir.mkdirs()

            val plugins = ArrayList<JSONObject>()

            project.subprojects.forEach {
                if (it.project.properties.containsKey("PluginName") && it.project.properties.containsKey("PluginDescription")) {
                    val plugin = it.project.tasks.get("jar").outputs.files.singleFile

                    val releases = ArrayList<JsonBuilder>()

                    releases.add(JsonBuilder(
                            "version" to it.project.version,
                            "requires" to ProjectVersions.apiVersion,
                            "date" to formatDate(Date()),
                            "url" to "https://github.com/CJP10/plugins/blob/master/release/${it.project.name}-${it.project.version}.jar?raw=true",
                            "sha512sum" to hash(plugin.readBytes())
                    ))

                    val pluginObject = JsonBuilder(
                            "name" to it.project.extra.get("PluginName"),
                            "id" to nameToId(it.project.extra.get("PluginName") as String),
                            "description" to it.project.extra.get("PluginDescription"),
                            "provider" to "CJP10",
                            "projectUrl" to "https://github.com/CJP10/plugins",
                            "releases" to releases.toTypedArray()
                    ).jsonObject()

                    plugins.add(pluginObject)
                }
            }

            File("plugins.json").printWriter().use { out ->
                out.println(plugins.toString())
            }
        }

    }
}
