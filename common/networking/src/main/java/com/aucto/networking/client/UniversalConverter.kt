package com.aucto.networking.client

import com.google.gson.GsonBuilder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class UniversalConverter : Converter.Factory() {

    private var acceptNullConverter: Converter.Factory
    private var declineNullConverter: Converter.Factory = GsonConverterFactory.create(GsonBuilder().create())

    init {
        val gsonConverter = GsonBuilder().serializeNulls().create()
        acceptNullConverter = GsonConverterFactory.create(gsonConverter)
    }

    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return if (type == Unit::class.java) {
            UnitConverter
        } else {
            acceptNullConverter.responseBodyConverter(type, annotations, retrofit)
        }
    }

    override fun requestBodyConverter(
        type: Type, parameterAnnotations: Array<Annotation>,
        annotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return if (annotations[0].annotationClass == DeclineNullValue::class) {
            declineNullConverter.requestBodyConverter(
                type,
                parameterAnnotations,
                annotations,
                retrofit
            )
        } else {
            acceptNullConverter.requestBodyConverter(
                type,
                parameterAnnotations,
                annotations,
                retrofit
            )
        }
    }

    //A converter factory that handles an empty response body or else kicks it off to the next converter
    //Necessary because the GSON converter factory will throw an error if it gets a null body
    private object UnitConverter : Converter<ResponseBody, Unit> {
        override fun convert(value: ResponseBody) {
            value.close()
        }
    }
}

// Always put this annotation at the first position
@Retention(AnnotationRetention.RUNTIME)
annotation class DeclineNullValue
