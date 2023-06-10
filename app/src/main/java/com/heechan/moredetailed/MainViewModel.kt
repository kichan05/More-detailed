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
    val resultMessage = MutableLiveData<String>("번역할 글을 입력하면\n자동으로 일본어를 거쳐서 번역해줍니다.")
    val directTranslatedMessage = MutableLiveData<String>("일본어를 거치지 않고 번역한 글입니다.")

    val langList = Language.values()
    val selectStartLangIndex = MutableLiveData(0)

    private val startLang : Language
        get() = langList[selectStartLangIndex.value!!]

    private val targetLang : Language
        get() = langList[(selectStartLangIndex.value!! + 1) % 2]

    fun translated() {
        if (inputMessage.value.isNullOrBlank()) {
            return
        }

        viewModelScope.launch(
            CoroutineExceptionHandler { _, e ->
                Log.e("[Translated]", e.toString())
            }
        ) {
            val input2jp = translated(inputMessage.value!!, startLang, Language.JP)
            val jp2target = translated(input2jp, Language.JP, targetLang)

            resultMessage.value = jp2target
        }

        viewModelScope.launch(
            CoroutineExceptionHandler { _, e ->
                Log.e("[Translated]", e.toString())
            }
        ) {
            val result = translated(inputMessage.value!!, startLang, targetLang)

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