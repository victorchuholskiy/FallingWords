package com.gmail.victorchuholskiy.languagegame.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by viktor.chukholskiy
 * 02/08/18.
 */
data class TranslationModel (@SerializedName("text_eng") val eng: String?,
							 @SerializedName("text_spa") val spa: String?)