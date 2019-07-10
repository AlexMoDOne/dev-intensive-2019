package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        val parts: List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return when {
            fullName.isNullOrBlank() -> Pair(null, null)
            else -> Pair(firstName, lastName)
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return payload.toCharArray().map { character ->
            if (character.isUpperCase()) transliterateCharacter(character.toLowerCase()).capitalize()
            else transliterateCharacter(
                character
            )
        }.joinToString("").replace(" ", divider)
    }

    private fun transliterateCharacter(character: Char): String = when (character) {
        'а' -> "a"
        'б' -> "b"
        'в' -> "v"
        'г' -> "g"
        'д' -> "d"
        'е' -> "e"
        'ё' -> "e"
        'ж' -> "zh"
        'з' -> "z"
        'и' -> "i"
        'й' -> "i"
        'к' -> "k"
        'л' -> "l"
        'м' -> "m"
        'н' -> "n"
        'о' -> "o"
        'п' -> "p"
        'р' -> "r"
        'с' -> "s"
        'т' -> "t"
        'у' -> "u"
        'ф' -> "f"
        'х' -> "h"
        'ц' -> "c"
        'ч' -> "ch"
        'ш' -> "sh"
        'щ' -> "sh'"
        'ъ' -> ""
        'ы' -> "i"
        'ь' -> ""
        'э' -> "e"
        'ю' -> "yu"
        'я' -> "ya"
        else -> character.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val name = firstName.orEmpty().trim().getOrNull(0)?.toUpperCase()
        val surname = lastName.orEmpty().trim().getOrNull(0)?.toUpperCase()
        val firstInit = name?.toString() ?: ""
        val secondInit = surname?.toString() ?: ""
        return "$firstInit$secondInit".ifEmpty { null }
    }

    fun String.truncate(value: Int = 16): String {
        if (this.length - 1 <= value)
            return this
        val result = this.trimStart().substring(0, value + 1)
        if (result[result.length - 1].isWhitespace()) {
            return result.trim()
        }
        if (value + 2 <= this.length - 1 && this.trimStart()[value + 2].isWhitespace()) {
            return result
        } else return "$result..."

    }
}