package com.gmail.victorchuholskiy.languagegame.useCases.parseFile

import com.gmail.victorchuholskiy.languagegame.data.models.TranslationModel
import com.gmail.victorchuholskiy.languagegame.useCases.UseCase
import io.reactivex.Observable

/**
 * Created by victor.chuholskiy
 * 06/08/18
 */
interface ParseFileUseCase: UseCase<List<TranslationModel>> {
	override fun execute(): Observable<List<TranslationModel>>
}