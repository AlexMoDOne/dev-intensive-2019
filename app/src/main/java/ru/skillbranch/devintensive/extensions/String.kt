package ru.skillbranch.devintensive.extensions

fun String.truncate(len: Int = 16): String {
    if (this.length <= len) return this

    val str = this.take(len)
    val str2 = str.trim()

    val len1 = str.length
    val len2 = str2.length

    return when (len1 == len2 || len1 == len2 + 1) {
        true -> "$str2..."
        false -> str2
    }
}

fun String.stripHtml(): String? =
    this.replace("[\\s]+".toRegex(), " ")
        .replace(Regex("<(/?[^>]+)>"), "")
        .replace(Regex("&(.+);"), "")
