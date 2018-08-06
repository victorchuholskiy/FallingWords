package com.gmail.victorchuholskiy.languagegame.main.game

import android.content.res.AssetManager
import android.util.Log
import com.gmail.victorchuholskiy.languagegame.data.models.TranslationQuestion
import com.gmail.victorchuholskiy.languagegame.useCases.parseFile.ParseFileUseCaseImpl
import com.gmail.victorchuholskiy.languagegame.useCases.prepareRoundQuestions.PrepareRoundQuestionsUseCaseImpl

/**
 * Created by victor.chuholskiy
 * 06/08/18.
 */
class GamePresenter(private val view: GameContract.View,
					private val assetManager: AssetManager)
	: GameContract.Presenter {

	init {
		view.presenter = this
	}

	private val roundQuestions = ArrayList<TranslationQuestion>()
	private var isGameAreaReady = false
	private var currentQuestion = 0

	override fun start() {
			ParseFileUseCaseImpl(assetManager)
					.execute()
					.flatMap { PrepareRoundQuestionsUseCaseImpl(it).execute() }
					.subscribe {
						roundQuestions.clear()
						roundQuestions.addAll(it)
						if (isGameAreaReady) {
							currentQuestion = 0
							view.startRound(roundQuestions[currentQuestion].eng, roundQuestions[currentQuestion].spa)
						}
					}
	}


	override fun gameAreaReady() {
		isGameAreaReady = true
		if (!roundQuestions.isEmpty()) {
			currentQuestion = 0
			view.startRound(roundQuestions[currentQuestion].eng, roundQuestions[currentQuestion].spa)
		}
	}

	override fun rightBtnClick() {
		processAnswer(roundQuestions[currentQuestion].isCorrect)
	}

	override fun wrongBtnClick() {
		processAnswer(!roundQuestions[currentQuestion].isCorrect)
	}

	override fun timerOut() {
		processAnswer(false)
	}

	private fun processAnswer(isAnswerCorrect: Boolean) {
		when (isAnswerCorrect) {
			true -> view.answerCorrect()
			false -> view.answerIncorrect()
		}
		roundQuestions[currentQuestion].isUserAnswerCorrect = isAnswerCorrect
		currentQuestion++
		if (currentQuestion >= roundQuestions.size) {

		} else {
			view.startRound(roundQuestions[currentQuestion].eng, roundQuestions[currentQuestion].spa)
		}
	}
}