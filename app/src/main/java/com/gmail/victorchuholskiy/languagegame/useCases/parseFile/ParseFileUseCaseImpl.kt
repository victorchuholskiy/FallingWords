package com.gmail.victorchuholskiy.languagegame.useCases.parseFile

import android.content.res.AssetManager
import com.gmail.victorchuholskiy.languagegame.data.models.TranslationModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.Exceptions
import io.reactivex.schedulers.Schedulers
import java.io.IOException

/**
 * Created by victor.chuholskiy
 * 06/08/18
 */
class ParseFileUseCaseImpl(private val assets: AssetManager): ParseFileUseCase {
	override fun execute(): Observable<List<TranslationModel>> {

		return Observable.just(assets)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.map {
					try {
						val inputStream = it.open("words_v2.json")
						val size = inputStream.available()
						val buffer = ByteArray(size)
						inputStream.read(buffer)
						inputStream.close()

						val listType = object : TypeToken<List<TranslationModel>>() {}.type
						val list: List<TranslationModel> = Gson().fromJson(String(buffer, Charsets.UTF_8), listType)
						list
					} catch (e: IOException) {
						throw Exceptions.propagate(e)
					}
				}
	}
}