package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String {
    if (this.length - 1 <= value)
        return this
    val result = this.trimStart().substring(0, value + 1)
    if (result[result.length - 1].isWhitespace()) {
        return result.trim()
    }
    return if (value + 2 <= this.length - 1 && this.trimStart()[value + 2].isWhitespace()) {
        result
    } else "$result..."
}

fun String.stripHtml(): String? =
    this.replace("[\\s]+".toRegex(), " ")
        .replace(Regex("<(/?[^>]+)>"), "")
        .replace(Regex("&(.+);"), "")
