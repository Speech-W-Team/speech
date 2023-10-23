package wtf.speech.compass.core

interface ScreenBuilder {
    val id: String
    fun build(params: Map<String, String>?, extra: Extra?): Screen
}

interface DeepLinkScreenBuilder: ScreenBuilder {
    val deepLinkPattern: String

    fun matches(deepLink: String): Boolean
    fun extractParameters(deepLink: String): Map<String, String>
}

abstract class BaseDeepLinkScreenBuilder : DeepLinkScreenBuilder {
    override fun matches(deepLink: String): Boolean {
        // Convert {parameter} format to regex-friendly format
        val regexPattern = deepLinkPattern.replace(Regex("\\{[^}]+\\}"), ".+")
        return Regex(regexPattern).matches(deepLink)
    }

    override fun extractParameters(deepLink: String): Map<String, String> {
        val patternParts = deepLinkPattern.split("/")
        val deepLinkParts = deepLink.split("/")

        val parameters = mutableMapOf<String, String>()
        for (index in patternParts.indices) {
            if (patternParts[index].startsWith("{") && patternParts[index].endsWith("}")) {
                val key = patternParts[index].trim('{', '}')
                parameters[key] = deepLinkParts.getOrNull(index) ?: ""
            }
        }

        return parameters
    }
}
