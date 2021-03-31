package rateLimitors

import java.util.*
import kotlin.collections.HashMap
/**
    One drawback to using the LRU implementations is the cost of finding which key needs to be evicted once capacity has
    been reached but this way will allow for implementing it w/out incurring the cost.
    LRU cache has no performance impact for insertions into caches
    once their capacity has been reached. Performance is O(1) and for cache eviction, it is O(1).

    (LRU): Evict the mapped items by lowest rank.
    (MRU): Most Recent: evict the item in the map w/ the highest rank.
 */

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

/**
   My write and remove is O(1).
 */

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

    /**
      The hashmap get is O(1).
     */
    fun get(key: K): V? = cache[key]

    /**
      The header of the insertions origin is removed which is O(1) still since this
      is a linked list it's inexpensive to remove the item from the head.
     */
    private fun getKeyToEvict(): K? = insertionOrder.removeFirst()

    override fun toString() = cache.toString()
}

