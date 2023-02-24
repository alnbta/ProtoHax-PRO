package dev.sora.relay.game.utils.math

import dev.sora.relay.game.utils.math.MathUtils.roundToFloat
import java.util.*

object MathHelper {
    val SQRT_2 = sqrt_float(2.0f)
    private const val SIN_BITS = 12
    private const val SIN_MASK = 4095
    private const val SIN_COUNT = 4096
    private const val SIN_COUNT_D4 = 1024
    val PI = roundToFloat(Math.PI)
    val PI2 = roundToFloat(Math.PI * 2.0)
    val PId2 = roundToFloat(Math.PI / 2.0)
    private val radToIndex = roundToFloat(651.8986469044033)
    val deg2Rad = roundToFloat(0.017453292519943295)
    private val SIN_TABLE_FAST = FloatArray(4096)
    var fastMath = false

    /**
     * A table of sin values computed from 0 (inclusive) to 2*pi (exclusive), with steps of 2*PI / 65536.
     */
    private val SIN_TABLE = FloatArray(65536)

    /**
     * Though it looks like an array, this is really more like a mapping.  Key (index of this array) is the upper 5 bits
     * of the result of multiplying a 32-bit unsigned integer by the B(2, 5) De Bruijn sequence 0x077CB531.  Value
     * (value stored in the array) is the unique index (from the right) of the leftmost one-bit in a 32-bit unsigned
     * integer that can cause the upper 5 bits to get that value.  Used for highly optimized "find the log-base-2 of
     * this number" calculations.
     */
    private val multiplyDeBruijnBitPosition: IntArray
    private var field_181163_d = 0.0
    private val field_181164_e: DoubleArray
    private val field_181165_f: DoubleArray

    /**
     * sin looked up in a table
     */
    fun sin(p_76126_0_: Float): Float {
        return if (fastMath) SIN_TABLE_FAST[(p_76126_0_ * radToIndex).toInt() and 4095] else SIN_TABLE[(p_76126_0_ * 10430.378f).toInt() and 65535]
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    fun cos(value: Float): Float {
        return if (fastMath) SIN_TABLE_FAST[(value * radToIndex + 1024.0f).toInt() and 4095] else SIN_TABLE[(value * 10430.378f + 16384.0f).toInt() and 65535]
    }

    fun sqrt_float(value: Float): Float {
        return Math.sqrt(value.toDouble()).toFloat()
    }

    fun sqrt_double(value: Double): Float {
        return Math.sqrt(value).toFloat()
    }

    /**
     * Returns the greatest integer less than or equal to the float argument
     */
    fun floor_float(value: Float): Int {
        val i = value.toInt()
        return if (value < i.toFloat()) i - 1 else i
    }

    /**
     * returns par0 cast as an int, and no greater than Integer.MAX_VALUE-1024
     */
    fun truncateDoubleToInt(value: Double): Int {
        return (value + 1024.0).toInt() - 1024
    }

    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    fun floor_double(value: Double): Int {
        val i = value.toInt()
        return if (value < i.toDouble()) i - 1 else i
    }

    /**
     * Long version of floor_double
     */
    fun floor_double_long(value: Double): Long {
        val i = value.toLong()
        return if (value < i.toDouble()) i - 1L else i
    }

    fun func_154353_e(value: Double): Int {
        return (if (value >= 0.0) value else -value + 1.0).toInt()
    }

    fun abs(value: Float): Float {
        return if (value >= 0.0f) value else -value
    }

    /**
     * Returns the unsigned value of an int.
     */
    fun abs_int(value: Int): Int {
        return if (value >= 0) value else -value
    }

    fun ceiling_float_int(value: Float): Int {
        val i = value.toInt()
        return if (value > i.toFloat()) i + 1 else i
    }

    fun ceiling_double_int(value: Double): Int {
        val i = value.toInt()
        return if (value > i.toDouble()) i + 1 else i
    }

    /**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters.
     */
    fun clamp_int(num: Int, min: Int, max: Int): Int {
        return if (num < min) min else if (num > max) max else num
    }

    /**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters
     */
    fun clamp_float(num: Float, min: Float, max: Float): Float {
        return if (num < min) min else if (num > max) max else num
    }

    fun clamp_double(num: Double, min: Double, max: Double): Double {
        return if (num < min) min else if (num > max) max else num
    }

    fun denormalizeClamp(lowerBnd: Double, upperBnd: Double, slide: Double): Double {
        return if (slide < 0.0) lowerBnd else if (slide > 1.0) upperBnd else lowerBnd + (upperBnd - lowerBnd) * slide
    }

    /**
     * Maximum of the absolute value of two numbers.
     */
    fun abs_max(p_76132_0_: Double, p_76132_2_: Double): Double {
        var p_76132_0_ = p_76132_0_
        var p_76132_2_ = p_76132_2_
        if (p_76132_0_ < 0.0) {
            p_76132_0_ = -p_76132_0_
        }
        if (p_76132_2_ < 0.0) {
            p_76132_2_ = -p_76132_2_
        }
        return if (p_76132_0_ > p_76132_2_) p_76132_0_ else p_76132_2_
    }

    /**
     * Buckets an integer with specifed bucket sizes.  Args: i, bucketSize
     */
    fun bucketInt(p_76137_0_: Int, p_76137_1_: Int): Int {
        return if (p_76137_0_ < 0) -((-p_76137_0_ - 1) / p_76137_1_) - 1 else p_76137_0_ / p_76137_1_
    }

    fun getRandomIntegerInRange(p_76136_0_: Random, p_76136_1_: Int, p_76136_2_: Int): Int {
        return if (p_76136_1_ >= p_76136_2_) p_76136_1_ else p_76136_0_.nextInt(p_76136_2_ - p_76136_1_ + 1) + p_76136_1_
    }

    fun randomFloatClamp(p_151240_0_: Random, p_151240_1_: Float, p_151240_2_: Float): Float {
        return if (p_151240_1_ >= p_151240_2_) p_151240_1_ else p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_
    }

    fun getRandomDoubleInRange(p_82716_0_: Random, p_82716_1_: Double, p_82716_3_: Double): Double {
        return if (p_82716_1_ >= p_82716_3_) p_82716_1_ else p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_
    }

    fun average(values: LongArray): Double {
        var i = 0L
        for (j in values) {
            i += j
        }
        return i.toDouble() / values.size.toDouble()
    }

    fun epsilonEquals(p_180185_0_: Float, p_180185_1_: Float): Boolean {
        return abs(p_180185_1_ - p_180185_0_) < 1.0E-5f
    }

    fun normalizeAngle(p_180184_0_: Int, p_180184_1_: Int): Int {
        return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    fun wrapAngleTo180_float(value: Float): Float {
        var value = value
        value = value % 360.0f
        if (value >= 180.0f) {
            value -= 360.0f
        }
        if (value < -180.0f) {
            value += 360.0f
        }
        return value
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    fun wrapAngleTo180_double(value: Double): Double {
        var value = value
        value = value % 360.0
        if (value >= 180.0) {
            value -= 360.0
        }
        if (value < -180.0) {
            value += 360.0
        }
        return value
    }

    /**
     * parses the string as integer or returns the second parameter if it fails
     */
    fun parseIntWithDefault(p_82715_0_: String, p_82715_1_: Int): Int {
        return try {
            p_82715_0_.toInt()
        } catch (var3: Throwable) {
            p_82715_1_
        }
    }

    /**
     * parses the string as integer or returns the second parameter if it fails. this value is capped to par2
     */
    fun parseIntWithDefaultAndMax(p_82714_0_: String, p_82714_1_: Int, p_82714_2_: Int): Int {
        return Math.max(p_82714_2_, parseIntWithDefault(p_82714_0_, p_82714_1_))
    }

    /**
     * parses the string as double or returns the second parameter if it fails.
     */
    fun parseDoubleWithDefault(p_82712_0_: String, p_82712_1_: Double): Double {
        return try {
            p_82712_0_.toDouble()
        } catch (var4: Throwable) {
            p_82712_1_
        }
    }

    fun parseDoubleWithDefaultAndMax(p_82713_0_: String, p_82713_1_: Double, p_82713_3_: Double): Double {
        return Math.max(p_82713_3_, parseDoubleWithDefault(p_82713_0_, p_82713_1_))
    }

    /**
     * Returns the input value rounded up to the next highest power of two.
     */
    fun roundUpToPowerOfTwo(value: Int): Int {
        var i = value - 1
        i = i or (i shr 1)
        i = i or (i shr 2)
        i = i or (i shr 4)
        i = i or (i shr 8)
        i = i or (i shr 16)
        return i + 1
    }

    /**
     * Is the given value a power of two?  (1, 2, 4, 8, 16, ...)
     */
    private fun isPowerOfTwo(value: Int): Boolean {
        return value != 0 && value and value - 1 == 0
    }

    /**
     * Uses a B(2, 5) De Bruijn sequence and a lookup table to efficiently calculate the log-base-two of the given
     * value.  Optimized for cases where the input value is a power-of-two.  If the input value is not a power-of-two,
     * then subtract 1 from the return value.
     */
    private fun calculateLogBaseTwoDeBruijn(value: Int): Int {
        var value = value
        value = if (isPowerOfTwo(value)) value else roundUpToPowerOfTwo(value)
        return multiplyDeBruijnBitPosition[(value.toLong() * 125613361L shr 27).toInt() and 31]
    }

    /**
     * Efficiently calculates the floor of the base-2 log of an integer value.  This is effectively the index of the
     * highest bit that is set.  For example, if the number in binary is 0...100101, this will return 5.
     */
    fun calculateLogBaseTwo(value: Int): Int {
        return calculateLogBaseTwoDeBruijn(value) - if (isPowerOfTwo(value)) 0 else 1
    }

    fun roundUp(p_154354_0_: Int, p_154354_1_: Int): Int {
        var p_154354_1_ = p_154354_1_
        return if (p_154354_1_ == 0) {
            0
        } else if (p_154354_0_ == 0) {
            p_154354_1_
        } else {
            if (p_154354_0_ < 0) {
                p_154354_1_ *= -1
            }
            val i = p_154354_0_ % p_154354_1_
            if (i == 0) p_154354_0_ else p_154354_0_ + p_154354_1_ - i
        }
    }

    fun func_180183_b(p_180183_0_: Float, p_180183_1_: Float, p_180183_2_: Float): Int {
        return func_180181_b(
            floor_float(p_180183_0_ * 255.0f),
            floor_float(p_180183_1_ * 255.0f),
            floor_float(p_180183_2_ * 255.0f)
        )
    }

    fun func_180181_b(p_180181_0_: Int, p_180181_1_: Int, p_180181_2_: Int): Int {
        var i = (p_180181_0_ shl 8) + p_180181_1_
        i = (i shl 8) + p_180181_2_
        return i
    }

    fun func_180188_d(p_180188_0_: Int, p_180188_1_: Int): Int {
        val i = p_180188_0_ and 16711680 shr 16
        val j = p_180188_1_ and 16711680 shr 16
        val k = p_180188_0_ and 65280 shr 8
        val l = p_180188_1_ and 65280 shr 8
        val i1 = p_180188_0_ and 255 shr 0
        val j1 = p_180188_1_ and 255 shr 0
        val k1 = (i.toFloat() * j.toFloat() / 255.0f).toInt()
        val l1 = (k.toFloat() * l.toFloat() / 255.0f).toInt()
        val i2 = (i1.toFloat() * j1.toFloat() / 255.0f).toInt()
        return p_180188_0_ and -16777216 or (k1 shl 16) or (l1 shl 8) or i2
    }

    fun func_181162_h(p_181162_0_: Double): Double {
        return p_181162_0_ - Math.floor(p_181162_0_)
    }

    //public static long getPositionRandom(Vec3i pos)
    //{
    //    return getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
    //}
    fun getCoordinateRandom(x: Int, y: Int, z: Int): Long {
        var i = (x * 3129871).toLong() xor z.toLong() * 116129781L xor y.toLong()
        i = i * i * 42317861L + i * 11L
        return i
    }

    fun getRandomUuid(rand: Random): UUID {
        val i = rand.nextLong() and -61441L or 16384L
        val j = rand.nextLong() and 4611686018427387903L or Long.MIN_VALUE
        return UUID(i, j)
    }

    fun func_181160_c(p_181160_0_: Double, p_181160_2_: Double, p_181160_4_: Double): Double {
        return (p_181160_0_ - p_181160_2_) / (p_181160_4_ - p_181160_2_)
    }

    fun atan2(p_181159_0_: Double, p_181159_2_: Double): Double {
        var p_181159_0_ = p_181159_0_
        var p_181159_2_ = p_181159_2_
        val d0 = p_181159_2_ * p_181159_2_ + p_181159_0_ * p_181159_0_
        return if (java.lang.Double.isNaN(d0)) {
            Double.NaN
        } else {
            val flag = p_181159_0_ < 0.0
            if (flag) {
                p_181159_0_ = -p_181159_0_
            }
            val flag1 = p_181159_2_ < 0.0
            if (flag1) {
                p_181159_2_ = -p_181159_2_
            }
            val flag2 = p_181159_0_ > p_181159_2_
            if (flag2) {
                val d1 = p_181159_2_
                p_181159_2_ = p_181159_0_
                p_181159_0_ = d1
            }
            val d9 = func_181161_i(d0)
            p_181159_2_ = p_181159_2_ * d9
            p_181159_0_ = p_181159_0_ * d9
            val d2 = field_181163_d + p_181159_0_
            val i = java.lang.Double.doubleToRawLongBits(d2).toInt()
            val d3 = field_181164_e[i]
            val d4 = field_181165_f[i]
            val d5 = d2 - field_181163_d
            val d6 = p_181159_0_ * d4 - p_181159_2_ * d5
            val d7 = (6.0 + d6 * d6) * d6 * 0.16666666666666666
            var d8 = d3 + d7
            if (flag2) {
                d8 = Math.PI / 2.0 - d8
            }
            if (flag1) {
                d8 = Math.PI - d8
            }
            if (flag) {
                d8 = -d8
            }
            d8
        }
    }

    fun func_181161_i(p_181161_0_: Double): Double {
        var p_181161_0_ = p_181161_0_
        val d0 = 0.5 * p_181161_0_
        var i = java.lang.Double.doubleToRawLongBits(p_181161_0_)
        i = 6910469410427058090L - (i shr 1)
        p_181161_0_ = java.lang.Double.longBitsToDouble(i)
        p_181161_0_ = p_181161_0_ * (1.5 - d0 * p_181161_0_ * p_181161_0_)
        return p_181161_0_
    }

    fun hsvToRGB(p_181758_0_: Float, p_181758_1_: Float, p_181758_2_: Float): Int {
        val i = (p_181758_0_ * 6.0f).toInt() % 6
        val f = p_181758_0_ * 6.0f - i.toFloat()
        val f1 = p_181758_2_ * (1.0f - p_181758_1_)
        val f2 = p_181758_2_ * (1.0f - f * p_181758_1_)
        val f3 = p_181758_2_ * (1.0f - (1.0f - f) * p_181758_1_)
        val f4: Float
        val f5: Float
        val f6: Float
        when (i) {
            0 -> {
                f4 = p_181758_2_
                f5 = f3
                f6 = f1
            }

            1 -> {
                f4 = f2
                f5 = p_181758_2_
                f6 = f1
            }

            2 -> {
                f4 = f1
                f5 = p_181758_2_
                f6 = f3
            }

            3 -> {
                f4 = f1
                f5 = f2
                f6 = p_181758_2_
            }

            4 -> {
                f4 = f3
                f5 = f1
                f6 = p_181758_2_
            }

            5 -> {
                f4 = p_181758_2_
                f5 = f1
                f6 = f2
            }

            else -> throw RuntimeException("Something went wrong when converting from HSV to RGB. Input was $p_181758_0_, $p_181758_1_, $p_181758_2_")
        }
        val j = clamp_int((f4 * 255.0f).toInt(), 0, 255)
        val k = clamp_int((f5 * 255.0f).toInt(), 0, 255)
        val l = clamp_int((f6 * 255.0f).toInt(), 0, 255)
        return j shl 16 or (k shl 8) or l
    }

    init {
        for (i in 0..65535) {
            SIN_TABLE[i] = Math.sin(i.toDouble() * Math.PI * 2.0 / 65536.0).toFloat()
        }
        for (j in SIN_TABLE_FAST.indices) {
            SIN_TABLE_FAST[j] = roundToFloat(Math.sin(j.toDouble() * Math.PI * 2.0 / 4096.0))
        }
        multiplyDeBruijnBitPosition = intArrayOf(
            0,
            1,
            28,
            2,
            29,
            14,
            24,
            3,
            30,
            22,
            20,
            15,
            25,
            17,
            4,
            8,
            31,
            27,
            13,
            23,
            21,
            19,
            16,
            7,
            26,
            12,
            18,
            6,
            11,
            5,
            10,
            9
        )
        field_181163_d = java.lang.Double.longBitsToDouble(4805340802404319232L)
        field_181164_e = DoubleArray(257)
        field_181165_f = DoubleArray(257)
        for (k in 0..256) {
            val d0 = k.toDouble() / 256.0
            val d1 = Math.asin(d0)
            field_181165_f[k] = Math.cos(d1)
            field_181164_e[k] = d1
        }
    }

    fun wrapDegrees(p_wrapDegrees_0_: Double): Double {
        return wrapAngleTo180_double(p_wrapDegrees_0_)
    }

    fun wrapDegrees(p_wrapDegrees_0_: Float): Float {
        return wrapAngleTo180_float(p_wrapDegrees_0_)
    }

    fun wrapDegrees(i: Int): Double {
        return wrapAngleTo180_float(i.toFloat()).toDouble()
    }
}