package com.heechan.moredetailed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.heechan.moredetailed.model.service.PapagoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<TextView>(R.id.test)

        button.setOnClickListener {
            val retrofit = RetrofitClient.get()

            val papagoService = retrofit.create(PapagoService::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val result = papagoService.translate(
                    sourceLang = "ko",
                    targetLang = "en",
                    text = "안녕하세요"
                )

                if(result.isSuccessful){
                    Log.d("[ApiTest]", result.body().toString())
                }
            }
        }
    }
}