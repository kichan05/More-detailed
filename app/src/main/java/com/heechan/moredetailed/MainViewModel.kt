package com.heechan.moredetailed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heechan.moredetailed.model.service.PapagoService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val papagoService = RetrofitClient.get().create(PapagoService::class.java)

    val inputMessage = MutableLiveData<String>()
    val resultMessage = MutableLiveData<String>()
    val directTranslatedMessage = MutableLiveData<String>()
    val startLang = MutableLiveData<String>()

    fun translated() {
        if (inputMessage.value.isNullOrBlank()) {
            return
        }

        viewModelScope.launch(
            CoroutineExceptionHandler { _, e ->
                Log.e("[Translated]", e.toString())
            }
        ) {
            val input2jp = translated(inputMessage.value!!, Language.KO, Language.JP)
            val jp2target = translated(input2jp, Language.JP, Language.EN)

            resultMessage.value = jp2target
        }

        viewModelScope.launch(
            CoroutineExceptionHandler { _, e ->
                Log.e("[Translated]", e.toString())
            }
        ) {
            val result = translated(inputMessage.value!!, Language.KO, Language.EN)

            directTranslatedMessage.value = result
        }
    }

    private suspend fun translated(
        message: String,
        startLang: Language,
        targetLang: Language
    ): String {
        val response = papagoService.translate(
            sourceLang = startLang.langCode,
            targetLang = targetLang.langCode,
            text = message
        )

        val body = response.body()
        if (response.isSuccessful && body != null) {
            return body.message.result.translatedText
        }

        Log.e("[Translated]", response.errorBody().toString())
        throw Exception("Papago api exception")
    }
}