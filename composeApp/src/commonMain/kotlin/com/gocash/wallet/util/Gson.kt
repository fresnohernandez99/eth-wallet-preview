package com.gocash.wallet.util

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class Gson {
    var json: Json = initialize()

    @OptIn(InternalSerializationApi::class)
    fun <T : Any> toJson(data: T, serializerAux: SerializationStrategy<T>? = null): String {
        // Usar el serializador correspondiente a la clase de data
        val serializer = serializerAux ?: data::class.serializer() as KSerializer<T>
        return json.encodeToString(serializer, data)
    }

    fun <T : Any> fromJson(
        jsonString: String,
        clazz: KClass<T>,
        serializerAux: DeserializationStrategy<T>? = null
    ): T {
        val serializer = serializerAux ?: serializerFor(clazz)
        return json.decodeFromString(serializer, jsonString)
    }

    @OptIn(InternalSerializationApi::class)
    private fun <T : Any> serializerFor(clazz: KClass<T>): KSerializer<T> {
        return clazz.serializer() // Asegúrate de que la clase tenga el serializador
    }

    companion object {
        fun initialize(): Json {
            return Json { encodeDefaults = true }
        }
    }
}