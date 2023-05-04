package com.heechan.moredetailed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heechan.moredetailed.model.data.PapagoRes
import com.heechan.moredetailed.model.service.PapagoService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val papagoService = RetrofitClient.get().create(PapagoService::class.java)

    val inputMessage = MutableLiveData<String>()
    val startLanage = MutableLiveData<String>()
    val resultData = MutableLiveData<PapagoRes>()

    fun translated() {
        if(inputMessage.value.isNullOrBlank()){
            return
        }

        viewModelScope.launch {
            val result = papagoService.translate(
                sourceLang = "ko",
                targetLang = "en",
                text = inputMessage.value.toString()
            )

            val body = result.body() ?: return@launch
            resultData.value = body

            if (result.isSuccessful) {
                val translatedText = body.message.result.translatedText
                Log.d("[ApiTest]", translatedText)
            }
        }

    }
}