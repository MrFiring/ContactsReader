package ru.mrfiring.unit_tests

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class JewelsAndStonesParametrizedTest(
    private val jewels: String,
    private val stones: String,
    private val expected: Int
) {
    private val jewelsAndStones = JewelsAndStones()


    companion object{
        @JvmStatic
        @Parameterized.Parameters(
            name = "Set {index}: jew {0} and stones {1} expect {2}"
        )
        fun data() = listOf(
            arrayOf("aABC", "aABC", 4),
            arrayOf("ZE", "aaabbbe", 0),
            arrayOf("YHaVB", "YYYYHHa", 7),
            arrayOf("bnM", "aAMBC", 1),
            arrayOf("ZE", "ZbbbEEe", 3),
            arrayOf("YHaVB", "yujgggYBVa", 4)
        )
    }


    @Test
    fun `WHEN numJewelsInStones EXPECT correct result`(){
        val actual = jewelsAndStones.numJewelsInStones(jewels, stones)

        assertEquals(expected, actual)
    }

}