package com.heechan.moredetailed

import android.os.Bundle
import androidx.activity.viewModels
import com.heechan.moredetailed.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        binding.btnMainTranslated.setOnClickListener{
            viewModel.translated()
        }
    }
}