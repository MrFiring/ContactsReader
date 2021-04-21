package ru.mrfiring.unit_tests

/*
You're given strings jewels representing the types of stones that are jewels, and stones representing the stones you have.
Each character in stones is a type of stone you have.
You want to know how many of the stones you have are also jewels.

Letters are case sensitive, so "a" is considered a different type of stone from "A".

Input: jewels = "aA", stones = "aAAbbbb"
Output: 3

Input: jewels = "z", stones = "ZZ"
Output: 0

Constraints:
   * 1 <= jewels.length, stones.length <= 50
   * jewels and stones consist of only English letters.
   * All the characters of jewels are unique.
 */

class JewelsAndStones {

    fun numJewelsInStones(jewels: String, stones: String): Int {
        validateJewels(jewels)
        validateStones(stones)

        var counter = 0

        for (stone in stones) {
            if (jewels.contains(stone)) {
                counter++
            }
        }

        return counter
    }

    private fun validateJewels(jewels: String) {
        if (jewels.isBlank() || !jewels.hasLettersOnly()) {
            throw IllegalArgumentException("Jewels string doesn't match the constraints.")
        }

        val checkedChars = mutableListOf<Char>()

        //Check duplication of chars in jewel string.
        //Add char to checkedChars only if there are no such char
        for (c in jewels) {
            if (checkedChars.contains(c)) {
                throw IllegalArgumentException("Jewels string has a duplicate char: $c")
            }
            checkedChars.add(c)
        }
    }

    private fun validateStones(stones: String) {
        if (stones.isBlank() || !stones.hasLettersOnly()) {
            throw IllegalArgumentException("Jewels string doesn't match the constraints.")
        }
    }


}