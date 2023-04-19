package com.nadhifhayazee.core_data.util

import kotlin.math.sqrt
import kotlin.random.Random

object MyPokemonUtil {

    fun getFiftyFiftyPossibility(): Boolean {
        val random = Random.nextDouble()
        return random >= 0.5
    }

    fun isPrime(): Boolean {
        val n = Random.nextInt(1, 8)
        if (n < 2) {
            return false
        }
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }

    fun getRenamedName(firstName: String, count: Int): String {
        var fib1 = 0
        var fib2 = 1
        var renamedCount = 0

        for (i in 1..count) {
            renamedCount = fib1
            fib1 = fib2
            fib2 = renamedCount + fib1
        }

        return "$firstName-$renamedCount"
    }

}