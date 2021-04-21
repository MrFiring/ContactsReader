package ru.mrfiring.unit_tests

import org.junit.Assert.assertEquals
import org.junit.Test


class JewelsAndStonesTest {
    private val jewelsAndStones = JewelsAndStones()

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN jewels is blank EXPECTED IllegalArgumentException`(){
        jewelsAndStones.numJewelsInStones(
            jewels = "",
            stones = "aaBBZZ"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN jewels has not only letters EXPECTED IllegalArgumentException`(){
        jewelsAndStones.numJewelsInStones(
            jewels = "aA[159Z",
            stones = "aaBBZZ"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN stones has not only letters EXPECTED IllegalArgumentException`(){
        jewelsAndStones.numJewelsInStones(
            jewels = "aAZ",
            stones = "aa[159BBZZ"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN jewels has duplicate chars EXPECTED IllegalArgumentException`(){
        jewelsAndStones.numJewelsInStones(
            jewels = "aAZZ",
            stones = "aaBBZZ"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN stones length more than 50 chars EXPECTED IllegalArgumentException`(){
        jewelsAndStones.numJewelsInStones(
            jewels = "aAZZ",
            stones = "DkiZTYlGzeIVZznYuvyqoJVtLfBALejEeCtuVZdNvBqkPbNmmOvfnkptArwy"

        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN jewels has whitespaces EXPECTED IllegalArgumentException`(){
        jewelsAndStones.numJewelsInStones(
            jewels = "aA Z",
            stones = "DjEeCtuVZdNvBqkPbNmmOvfnkptArwy"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN stones has whitespaces EXPECTED IllegalArgumentException`(){
        jewelsAndStones.numJewelsInStones(
            jewels = "aAZ",
            stones = "Dj EeCtuVZdNvBqkPbNmmOvfnkptArwy"
        )
    }

    @Test
    fun `WHEN jewel is AaDC and stones is AAaaDDCC expected 8`(){
        val actual = jewelsAndStones.numJewelsInStones(
            jewels = "AaDC",
            stones = "AAaaDDCC"
        )

        assertEquals(8, actual)
    }

}