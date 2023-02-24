package dev.sora.relay.utils

import java.util.*

object RandomUtils {
    private val RANDOM = Random()
    fun nextBytes(count: Int): ByteArray {
        Validate.isTrue(count >= 0, "Count cannot be negative.", arrayOfNulls<Any>(0))
        val result = ByteArray(count)
        RANDOM.nextBytes(result)
        return result
    }

    fun nextInt(startInclusive: Int, endExclusive: Int): Int {
        Validate.isTrue(
            endExclusive >= startInclusive,
            "Start value must be smaller or equal to end value.",
            arrayOfNulls<Any>(0)
        )
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.", arrayOfNulls<Any>(0))
        return if (startInclusive == endExclusive) startInclusive else startInclusive + RANDOM.nextInt(endExclusive - startInclusive)
    }

    fun nextLong(startInclusive: Long, endExclusive: Long): Long {
        Validate.isTrue(
            endExclusive >= startInclusive,
            "Start value must be smaller or equal to end value.",
            arrayOfNulls<Any>(0)
        )
        Validate.isTrue(startInclusive >= 0L, "Both range values must be non-negative.", arrayOfNulls<Any>(0))
        return if (startInclusive == endExclusive) startInclusive else nextDouble(
            startInclusive.toDouble(),
            endExclusive.toDouble()
        ).toLong()
    }

    fun nextDouble(startInclusive: Double, endInclusive: Double): Double {
        Validate.isTrue(
            endInclusive >= startInclusive,
            "Start value must be smaller or equal to end value.",
            arrayOfNulls<Any>(0)
        )
        Validate.isTrue(startInclusive >= 0.0, "Both range values must be non-negative.", arrayOfNulls<Any>(0))
        return if (startInclusive == endInclusive) startInclusive else startInclusive + (endInclusive - startInclusive) * RANDOM.nextDouble()
    }

    fun nextFloat(startInclusive: Float, endInclusive: Float): Float {
        Validate.isTrue(
            endInclusive >= startInclusive,
            "Start value must be smaller or equal to end value.",
            arrayOfNulls<Any>(0)
        )
        Validate.isTrue(startInclusive >= 0.0f, "Both range values must be non-negative.", arrayOfNulls<Any>(0))
        return if (startInclusive == endInclusive) startInclusive else startInclusive + (endInclusive - startInclusive) * RANDOM.nextFloat()
    }
}