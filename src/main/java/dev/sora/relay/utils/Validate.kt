package dev.sora.relay.utils

import com.nimbusds.jose.util.ArrayUtils
import java.util.regex.Pattern

object Validate {
    private const val DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE =
        "The value %s is not in the specified exclusive range of %s to %s"
    private const val DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE =
        "The value %s is not in the specified inclusive range of %s to %s"
    private const val DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s"
    private const val DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null"
    private const val DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false"
    private const val DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE =
        "The validated array contains null element at index: %d"
    private const val DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE =
        "The validated collection contains null element at index: %d"
    private const val DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank"
    private const val DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty"
    private const val DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty"
    private const val DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty"
    private const val DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty"
    private const val DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d"
    private const val DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE =
        "The validated character sequence index is invalid: %d"
    private const val DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d"
    private const val DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false"
    private const val DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s"
    private const val DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s"
    fun isTrue(expression: Boolean, message: String?, value: Long) {
        require(expression) { String.format(message!!, value) }
    }

    fun isTrue(expression: Boolean, message: String?, value: Double) {
        require(expression) { String.format(message!!, value) }
    }

    fun isTrue(expression: Boolean, message: String?, vararg values: Any?) {
        require(expression) { String.format(message!!, *values) }
    }

    fun isTrue(expression: Boolean) {
        require(expression) { "The validated expression is false" }
    }

    fun <T> notNull(`object`: T): T {
        return notNull<T>(`object`, "The validated object is null")
    }

    fun <T> notNull(`object`: T?, message: String?, vararg values: Any?): T {
        return `object` ?: throw NullPointerException(String.format(message!!, *values))
    }

    fun <T> notEmpty(array: Array<T>?, message: String?, vararg values: Any?): Array<T> {
        return if (array == null) {
            throw NullPointerException(String.format(message!!, *values))
        } else if (array.size == 0) {
            throw IllegalArgumentException(String.format(message!!, *values))
        } else {
            array
        }
    }

    fun <T> notEmpty(array: Array<T>?): Array<T> {
        return notEmpty(array, "The validated array is empty")
    }

    fun <T : Collection<*>?> notEmpty(collection: T, message: String?, vararg values: Any?): T {
        return if (collection == null) {
            throw NullPointerException(String.format(message!!, *values))
        } else if (collection.isEmpty()) {
            throw IllegalArgumentException(String.format(message!!, *values))
        } else {
            collection
        }
    }

    fun <T : Collection<*>?> notEmpty(collection: T): T {
        return notEmpty(collection, "The validated collection is empty")
    }

    fun <T : Map<*, *>?> notEmpty(map: T, message: String?, vararg values: Any?): T {
        return if (map == null) {
            throw NullPointerException(String.format(message!!, *values))
        } else if (map.isEmpty()) {
            throw IllegalArgumentException(String.format(message!!, *values))
        } else {
            map
        }
    }

    fun <T : Map<*, *>?> notEmpty(map: T): T {
        return notEmpty(map, "The validated map is empty")
    }

    fun <T : CharSequence?> notEmpty(chars: T?, message: String?, vararg values: Any?): T {
        return if (chars == null) {
            throw NullPointerException(String.format(message!!, *values))
        } else if (chars.length == 0) {
            throw IllegalArgumentException(String.format(message!!, *values))
        } else {
            chars
        }
    }

    fun <T : CharSequence?> notEmpty(chars: T): T {
        return notEmpty<T>(chars, "The validated character sequence is empty")
    }


    fun <T> validIndex(array: Array<T>, index: Int, message: String?, vararg values: Any?): Array<T> {
        notNull(array)
        return if (index >= 0 && index < array.size) {
            array
        } else {
            throw IndexOutOfBoundsException(String.format(message!!, *values))
        }
    }

    fun <T> validIndex(array: Array<T>, index: Int): Array<T> {
        return validIndex(array, index, "The validated array index is invalid: %d", index)
    }

    fun <T : Collection<*>?> validIndex(collection: T, index: Int, message: String?, vararg values: Any?): T {
        notNull(collection)
        return if (index >= 0 && index < collection!!.size) {
            collection
        } else {
            throw IndexOutOfBoundsException(String.format(message!!, *values))
        }
    }

    fun <T : Collection<*>?> validIndex(collection: T, index: Int): T {
        return validIndex(collection, index, "The validated collection index is invalid: %d", index)
    }

    fun <T : CharSequence?> validIndex(chars: T, index: Int, message: String?, vararg values: Any?): T {
        notNull(chars)
        return if (index >= 0 && index < chars!!.length) {
            chars
        } else {
            throw IndexOutOfBoundsException(String.format(message!!, *values))
        }
    }

    fun <T : CharSequence?> validIndex(chars: T, index: Int): T {
        return validIndex(chars, index, "The validated character sequence index is invalid: %d", index)
    }

    fun validState(expression: Boolean) {
        check(expression) { "The validated state is false" }
    }

    fun validState(expression: Boolean, message: String?, vararg values: Any?) {
        check(expression) { String.format(message!!, *values) }
    }

    fun matchesPattern(input: CharSequence?, pattern: String?) {
        require(Pattern.matches(pattern, input)) {
            String.format(
                "The string %s does not match the pattern %s",
                input,
                pattern
            )
        }
    }

    fun matchesPattern(input: CharSequence?, pattern: String?, message: String?, vararg values: Any?) {
        require(Pattern.matches(pattern, input)) { String.format(message!!, *values) }
    }

    fun <T> inclusiveBetween(start: T, end: T, value: Comparable<T>) {
        require(!(value.compareTo(start) < 0 || value.compareTo(end) > 0)) {
            String.format(
                "The value %s is not in the specified inclusive range of %s to %s",
                value,
                start,
                end
            )
        }
    }

    fun <T> inclusiveBetween(start: T, end: T, value: Comparable<T>, message: String?, vararg values: Any?) {
        require(!(value.compareTo(start) < 0 || value.compareTo(end) > 0)) { String.format(message!!, *values) }
    }

    fun inclusiveBetween(start: Long, end: Long, value: Long) {
        require(!(value < start || value > end)) {
            String.format(
                "The value %s is not in the specified inclusive range of %s to %s",
                value,
                start,
                end
            )
        }
    }

    fun inclusiveBetween(start: Long, end: Long, value: Long, message: String?) {
        require(!(value < start || value > end)) { String.format(message!!) }
    }

    fun inclusiveBetween(start: Double, end: Double, value: Double) {
        require(!(value < start || value > end)) {
            String.format(
                "The value %s is not in the specified inclusive range of %s to %s",
                value,
                start,
                end
            )
        }
    }

    fun inclusiveBetween(start: Double, end: Double, value: Double, message: String?) {
        require(!(value < start || value > end)) { String.format(message!!) }
    }

    fun <T> exclusiveBetween(start: T, end: T, value: Comparable<T>) {
        require(!(value.compareTo(start) <= 0 || value.compareTo(end) >= 0)) {
            String.format(
                "The value %s is not in the specified exclusive range of %s to %s",
                value,
                start,
                end
            )
        }
    }

    fun <T> exclusiveBetween(start: T, end: T, value: Comparable<T>, message: String?, vararg values: Any?) {
        require(!(value.compareTo(start) <= 0 || value.compareTo(end) >= 0)) { String.format(message!!, *values) }
    }

    fun exclusiveBetween(start: Long, end: Long, value: Long) {
        require(!(value <= start || value >= end)) {
            String.format(
                "The value %s is not in the specified exclusive range of %s to %s",
                value,
                start,
                end
            )
        }
    }

    fun exclusiveBetween(start: Long, end: Long, value: Long, message: String?) {
        require(!(value <= start || value >= end)) { String.format(message!!) }
    }

    fun exclusiveBetween(start: Double, end: Double, value: Double) {
        require(!(value <= start || value >= end)) {
            String.format(
                "The value %s is not in the specified exclusive range of %s to %s",
                value,
                start,
                end
            )
        }
    }

    fun exclusiveBetween(start: Double, end: Double, value: Double, message: String?) {
        require(!(value <= start || value >= end)) { String.format(message!!) }
    }

    fun isInstanceOf(type: Class<*>, obj: Any?) {
        require(type.isInstance(obj)) {
            String.format(
                "Expected type: %s, actual: %s",
                type.name,
                if (obj == null) "null" else obj.javaClass.name
            )
        }
    }

    fun isInstanceOf(type: Class<*>, obj: Any?, message: String?, vararg values: Any?) {
        require(type.isInstance(obj)) { String.format(message!!, *values) }
    }

    fun isAssignableFrom(superType: Class<*>, type: Class<*>?) {
        require(superType.isAssignableFrom(type)) {
            String.format(
                "Cannot assign a %s to a %s",
                if (type == null) "null" else type.name,
                superType.name
            )
        }
    }

    fun isAssignableFrom(superType: Class<*>, type: Class<*>?, message: String?, vararg values: Any?) {
        require(superType.isAssignableFrom(type)) { String.format(message!!, *values) }
    }
}