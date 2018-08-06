package com.gmail.victorchuholskiy.languagegame.interactors.prepareRoundQuestions

import com.gmail.victorchuholskiy.languagegame.data.models.TranslationQuestion
import com.gmail.victorchuholskiy.languagegame.interactors.UseCase
import io.reactivex.Observable

/**
 * Created by user
 * 26.07.2018.
 */
interface PrepareRoundQuestionsUseCase: UseCase<List<TranslationQuestion>> {
	override fun execute(): Observable<List<TranslationQuestion>>
}