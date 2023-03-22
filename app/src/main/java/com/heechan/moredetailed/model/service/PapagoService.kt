package com.heechan.moredetailed.model.service

import com.heechan.moredetailed.BuildConfig
import com.heechan.moredetailed.model.data.PapagoRes
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PapagoService {
    @POST("/v1/papago/n2mt")
    suspend fun translate(
        @Header("Content-Type")
        contentType : String = "application/x-www-form-urlencoded; charset=UTF-8",
        @Header("X-Naver-Client-Id")
        naverClientId : String = BuildConfig.NAVER_CLIENT_ID,
        @Header("X-Naver-Client-Secret")
        naverClientSecret : String = BuildConfig.NAVER_CLIENT_SECRET,

        @Query("source")
        sourceLang : String,
        @Query("target")
        targetLang : String,
        @Query("text")
        text : String,
    ) : Response<PapagoRes>
}