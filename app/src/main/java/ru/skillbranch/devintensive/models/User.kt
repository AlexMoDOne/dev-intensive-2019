package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {

    class Builder {
        var id: String? = null

        fun id(id: String) = apply { this.id = id }

        var firstName: String? = null

        fun firstName(firstName: String) = apply { this.firstName = firstName }

        var lastName: String? = null

        fun lastName(lastName: String) = apply { this.lastName = lastName }

        var avatar: String? = null

        fun avatar(avatar: String?) = apply { this.avatar = avatar }

        var rating: Int = 0

        fun rating(rating: Int) = apply { this.rating = rating }

        var respect: Int = 0

        fun respect(respect: Int) = apply { this.respect = respect }

        var lastVisit: Date? = null

        fun lastVisit(lastVisit: Date?) = apply { this.lastVisit = lastVisit }

        var isOnline: Boolean = false

        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }

        fun build() = User("$id", firstName,lastName, avatar, rating, respect, lastVisit, isOnline)
    }

    constructor(id: String, firstName: String?, lastName: String?) : this(id, firstName, lastName, null)

    constructor(id: String) : this(id, "John", "Doe $id")

    init {
        println("It`s Alive!!!\n $firstName $lastName")
    }

    fun printMe(): Unit {
        println(
            """
            id :$id,
            firstName :$firstName,
            lastName : $lastName,
            avatar : $avatar,
            rating : $rating,
            respect : $respect,
            lastVisit : $lastVisit,
            isOnline : $isOnline
        """.trimIndent()
        )
    }

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullName: String?): User {
            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }
}
