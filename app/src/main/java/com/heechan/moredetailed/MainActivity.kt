package com.heechan.moredetailed

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.heechan.moredetailed.databinding.ActivityMainBinding
import com.heechan.moredetailed.model.service.PapagoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.test.setOnClickListener {
            val retrofit = RetrofitClient.get()

            val papagoService = retrofit.create(PapagoService::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val result = papagoService.translate(
                    sourceLang = "ko",
                    targetLang = "en",
                    text = "안녕하세요"
                )

                val body = result.body() ?: return@launch

                if(result.isSuccessful){
                    val translatedText = body.message.result.translatedText

                    withContext(Dispatchers.Main){
                        Toast.makeText(this@MainActivity, translatedText, Toast.LENGTH_SHORT).show()
                        Log.d("[ApiTest]", translatedText)
                    }
                }
            }
        }
    }
}