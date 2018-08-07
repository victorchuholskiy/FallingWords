package com.gmail.victorchuholskiy.languagegame.useCases.prepareRoundQuestions

import com.gmail.victorchuholskiy.languagegame.data.models.TranslationQuestion
import com.gmail.victorchuholskiy.languagegame.useCases.UseCase
import io.reactivex.Observable

/**
 * Created by victor.chuholskiy
 * 06/08/18
 */
interface PrepareRoundQuestionsUseCase: UseCase<List<TranslationQuestion>> {
	override fun execute(): Observable<List<TranslationQuestion>>
}