package rateLimitors

import java.util.*
import kotlin.collections.HashMap
enum class Type { LRU, MRU }

class Cache<T>(private val type: Type, private val size: Int) {
    private val map = mutableMapOf<T, Int>()
    private var rank: Int = 0

    fun put(value: T): T? {
        var evictedKey: T? = null

        when {
            map.containsKey(value) -> {
                // Increase rank of existing value.
                map[value] = rank++
            }
            map.size == size -> {
                // Remove the lowest or highest rank item in the map depending on Type.
                evictedKey = findKeyToEvict()
                map.remove(evictedKey)
                map[value] = rank++
            }
            else -> {
                // Add the new item.
                map[value] = rank++
            }
        }

        return evictedKey
    }

    private fun findKeyToEvict(): T? {
        val rankToEvict = when (type) {
            Type.MRU -> Collections.max(map.values)
            Type.LRU -> Collections.min(map.values)
        }
        return map.entries.find { it.value == rankToEvict }?.key
    }

    override fun toString(): String = StringBuilder().apply {
        val list = mutableListOf<String>().apply {
            for (entry in map)
                add("'${entry.key}'->rank=${entry.value}")
        }
        append(list.joinToString(", ", "{", "}"))
    }.toString()
}

class LowCostLRUCache<K, V>(private val capacity: Int = 5) {
    private val cache = HashMap<K, V>()
    private val insertionOrder = LinkedList<K>()

    fun put(key: K, value: V): K? {
        var evictedKey: K? = null
        if (cache.size >= capacity) {
            evictedKey = getKeyToEvict()
            cache.remove(evictedKey)
        }
        cache[key] = value
        insertionOrder.addLast(key)
        return evictedKey
    }

    fun get(key: K): V? = cache[key]

    private fun getKeyToEvict(): K? = insertionOrder.removeFirst()

    override fun toString() = cache.toString()
}

