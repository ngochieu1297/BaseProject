package com.example.baseproject.functional

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class EventBus private constructor() : CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    private val contextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()

    private fun send(event: Any) {
        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()
        cloneContextList.putAll(contextList)
        for ((_, pipe) in cloneContextList) {
            pipe.keys.firstOrNull {
                it == event.javaClass || it == event.javaClass.superclass
            }.let { key ->
                pipe[key]?.send(event)
            }
        }
    }

    private fun <T> registerEvent(
        contextName: String,
        eventDispatcher: CoroutineDispatcher,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit
    ) {
        val pipeList = if (contextList.containsKey(contextName)) {
            contextList[contextName]
        } else {
            val eventPipe = mutableMapOf<Class<*>, PipeData<*>>()
            contextList[contextName] = eventPipe
            eventPipe
        }
        val eventData = PipeData(this, eventDispatcher, eventCallback)
        pipeList?.set(eventClass, eventData)
    }

    private fun unregisterAllEvent() {
        coroutineContext.cancelChildren()
        for ((_, pipe) in contextList) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextList.clear()
    }

    private fun unregisterByContext(contextName: String) {
        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()
        cloneContextList.putAll(contextList)
        val pipesInContext = cloneContextList.filter { it.key == contextName }
        for ((_, pipe) in pipesInContext) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextList.remove(contextName)
    }

    internal class PipeData<T>(
        val coroutineScope: CoroutineScope,
        val eventDispatcher: CoroutineDispatcher,
        val onEvent: (T) -> Unit
    ) {
        private val channel = Channel<T>()

        init {
            coroutineScope.launch {
                channel.consumeEach {
                    launch(eventDispatcher) { onEvent(it) }
                }
            }
        }

        fun send(event: Any) {
            if (!channel.isClosedForSend) {
                TimeUnit.MILLISECONDS.sleep(1)
                coroutineScope.launch { channel.send(event as T) }
            }
        }

        fun cancel() {
            channel.cancel()
        }
    }

    companion object {
        private var instance: EventBus? = null
            get() = if (field == null) {
                field = EventBus()
                field
            } else {
                field
            }

        fun <T> registerEvent(
            contextName: String,
            eventDispatcher: CoroutineDispatcher,
            eventClass: Class<T>,
            eventCallback: (T) -> Unit
        ) {
            instance?.registerEvent(contextName, eventDispatcher, eventClass, eventCallback)
        }

        fun send(event: Any, delaySend: Long = 0) {
            if (delaySend > 0) {
                instance?.launch {
                    delay(delaySend)
                    instance?.send(event)
                }
            } else {
                instance?.send(event)
            }
        }

        fun unregisterAllEvents() {
            instance?.unregisterAllEvent()
        }

        fun unregisterByContext(contextName: String) {
            instance?.unregisterByContext(contextName)
        }
    }
}
