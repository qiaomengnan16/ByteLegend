package com.bytelegend.app.client.api

import kotlinext.js.Object
import kotlinext.js.jsObject

external fun delete(p: dynamic): Boolean = definedExternally

// https://youtrack.jetbrains.com/issue/KT-42743
class JSObjectBackedMap<V>(
    private val delegate: dynamic
) : MutableMap<String, V> {
    constructor() : this(jsObject())

    override val size: Int
        get() = keys.size

    override fun containsKey(key: String) = get(key) != null

    override fun containsValue(value: V) = values.contains(value)

    override fun get(key: String): V? = delegate[key]

    override fun isEmpty(): Boolean = size == 0

    override val entries: MutableSet<MutableMap.MutableEntry<String, V>>
        get() = JSObjectBackedEntrySet(delegate)
    override val keys: MutableSet<String>
        get() = JSObjectBackedKeySet(delegate)
    override val values: MutableCollection<V>
        get() = JSObjectBackedValueCollection(delegate)

    override fun clear() {
        val iterator = keys.iterator()
        while (iterator.hasNext()) {
            iterator.remove()
        }
    }

    override fun put(key: String, value: V): V? {
        val oldValue = get(key)
        delegate[key] = value
        return oldValue
    }

    override fun putAll(from: Map<out String, V>) {
        from.forEach { (k, v) -> put(k, v) }
    }

    override fun remove(key: String): V? {
        val value = delegate[key]
        delete(delegate[key])
        return value
    }
}

internal class JSObjectBackedValueCollection<V>(
    private val delegate: dynamic
) : MutableCollection<V> {
    override val size: Int
        get() = Object.keys(delegate).size

    override fun contains(element: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(element: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<V> = JSObjectBackedValueIterator(delegate)

    override fun remove(element: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }
}

internal class JSObjectBackedEntry<V>(
    private val delegate: dynamic,
    override val key: String
) : MutableMap.MutableEntry<String, V> {
    override val value: V
        get() = delegate[key]

    override fun setValue(newValue: V): V {
        val oldValue = value
        delegate[key] = newValue
        return oldValue
    }
}

internal class JSObjectBackedEntryIterator<V>(
    private val delegate: dynamic
) : MutableIterator<MutableMap.MutableEntry<String, V>> {
    private val keyIterator: JSObjectBackedKeyIterator = JSObjectBackedKeyIterator(delegate)
    override fun hasNext(): Boolean = keyIterator.hasNext()

    override fun next(): MutableMap.MutableEntry<String, V> = JSObjectBackedEntry(delegate, keyIterator.next())

    override fun remove() = keyIterator.remove()
}

internal class JSObjectBackedEntrySet<V>(
    private val delegate: dynamic
) : MutableSet<MutableMap.MutableEntry<String, V>> {
    override fun add(element: MutableMap.MutableEntry<String, V>): Boolean {
        TODO("Not yet implemented all")
    }

    override fun addAll(elements: Collection<MutableMap.MutableEntry<String, V>>): Boolean {
        TODO("Not yet implemented all all")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<String, V>> = JSObjectBackedEntryIterator(delegate)

    override fun remove(element: MutableMap.MutableEntry<String, V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<MutableMap.MutableEntry<String, V>>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<MutableMap.MutableEntry<String, V>>): Boolean {
        TODO("Not yet implemented")
    }

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: MutableMap.MutableEntry<String, V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<MutableMap.MutableEntry<String, V>>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}

internal class JSObjectBackedKeySet(
    private val delegate: dynamic
) : MutableSet<String> {
    override fun add(element: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection<String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<String> = JSObjectBackedKeyIterator(delegate)

    override fun remove(element: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<String>): Boolean {
        TODO("Not yet implemented")
    }

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}

internal class JSObjectBackedKeyIterator(
    private val delegate: dynamic
) : MutableIterator<String> {
    var currentIndex = 0

    override fun hasNext(): Boolean = Object.keys(delegate).size > currentIndex

    override fun next(): String = Object.keys(delegate)[currentIndex++]

    override fun remove() {
        delete(delegate[Object.keys(delegate)[currentIndex]])
    }
}

internal class JSObjectBackedValueIterator<V>(
    private val delegate: dynamic
) : MutableIterator<V> {
    private val keyIterator: JSObjectBackedKeyIterator = JSObjectBackedKeyIterator(delegate)
    override fun hasNext(): Boolean = keyIterator.hasNext()

    override fun next(): V {
        return delegate[keyIterator.next()]
    }

    override fun remove() {
        keyIterator.remove()
    }
}
