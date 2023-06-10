package com.heechan.moredetailed

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.heechan.moredetailed.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        binding.txtMainLangLabelEn.setOnClickListener(changeLang)
        binding.txtMainLangLabelKo.setOnClickListener(changeLang)
        binding.imgMainTranslateArrow.setOnClickListener(changeLang)
    }

    val changeLang = { _ : View ->
        viewModel.changeLanguage()
        val animTree = AnimationUtils.loadAnimation(this, R.anim.ani_roate_360)
        binding.imgMainTranslateArrow.startAnimation(animTree)
    }
}