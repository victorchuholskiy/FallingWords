package com.gmail.victorchuholskiy.languagegame.interactors.parseFile

import com.gmail.victorchuholskiy.languagegame.data.models.TranslationModel
import com.gmail.victorchuholskiy.languagegame.interactors.UseCase
import io.reactivex.Observable

/**
 * Created by user
 * 26.07.2018.
 */
interface DBParseFileUseCase: UseCase<List<TranslationModel>> {
	override fun execute(): Observable<List<TranslationModel>>
}