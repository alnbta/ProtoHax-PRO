package dev.sora.relay.game.utils.math

import java.util.*

object MathUtils {
    private val RANDOM: Random = Random()
    @JvmStatic
    fun rand(min: Double, max: Double): Double {
        return RANDOM.nextDouble() * (max - min) + min
    }
    @JvmStatic
    fun rand(min: Float, max: Float): Double {
        return RANDOM.nextDouble() * (max - min) + min
    }
    const val PI = Math.PI.toFloat()
    const val PI2 = Math.PI.toFloat() * 2f
    const val PId2 = Math.PI.toFloat() / 2f
    private val ASIN_TABLE = FloatArray(65536)

    @JvmStatic
    fun asin(value: Float): Float {
        return ASIN_TABLE[((value + 1.0f).toDouble() * 32767.5).toInt() and 65535]
    }

    @JvmStatic
    fun acos(value: Float): Float {
        return Math.PI.toFloat() / 2f - ASIN_TABLE[((value + 1.0f).toDouble() * 32767.5).toInt() and 65535]
    }

    @JvmStatic
    fun getAverage(vals: IntArray): Int {
        return if (vals.size <= 0) {
            0
        } else {
            val i = getSum(vals)
            i / vals.size
        }
    }

    @JvmStatic
    fun getSum(vals: IntArray): Int {
        return if (vals.size <= 0) {
            0
        } else {
            var i = 0
            for (j in vals.indices) {
                val k = vals[j]
                i += k
            }
            i
        }
    }

    @JvmStatic
    fun roundDownToPowerOfTwo(`val`: Int): Int {
        val i = MathHelper.roundUpToPowerOfTwo(`val`)
        return if (`val` == i) i else i / 2
    }

    @JvmStatic
    fun equalsDelta(f1: Float, f2: Float, delta: Float): Boolean {
        return Math.abs(f1 - f2) <= delta
    }

    @JvmStatic
    fun toDeg(angle: Float): Float {
        return angle * 180.0f / MathHelper.PI
    }

    @JvmStatic
    fun toRad(angle: Float): Float {
        return angle / 180.0f * MathHelper.PI
    }
    @JvmStatic
    fun roundToFloat(d: Double): Float {
        return (Math.round(d * 1.0E8).toDouble() / 1.0E8).toFloat()
    }

    init {
        for (i in 0 until 65536) ASIN_TABLE[i] = Math.asin((i / 32767.5 - 1.0)).toFloat()
        for (j in -1..1) {
            ASIN_TABLE[((j + 1.0) * 32767.5).toInt() and 65535] = Math.asin(j.toDouble()).toFloat()
        }
    }
}