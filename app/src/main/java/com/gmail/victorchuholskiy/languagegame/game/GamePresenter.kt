package com.gmail.victorchuholskiy.languagegame.game

import android.content.res.AssetManager
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
	private var currentQuestion = -1

	override fun start() {
		if (currentQuestion < 0) {
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
	}


	override fun gameAreaReady() {
		isGameAreaReady = true
		if (!roundQuestions.isEmpty()) {
			currentQuestion = 0
			view.startRound(roundQuestions[currentQuestion].eng, roundQuestions[currentQuestion].spa)
		}
	}

	override fun rightBtnClick() {
		if (currentQuestion in 0 until roundQuestions.size) {
			processAnswer(roundQuestions[currentQuestion].isCorrect)
		}
	}

	override fun wrongBtnClick() {
		if (currentQuestion in 0 until roundQuestions.size) {
			processAnswer(!roundQuestions[currentQuestion].isCorrect)
		}
	}

	override fun timerOut() {
		if (currentQuestion in 0 until roundQuestions.size) {
			processAnswer(false)
		}
	}

	private fun processAnswer(isAnswerCorrect: Boolean) {
		when (isAnswerCorrect) {
			true -> view.answerCorrect()
			false -> view.answerIncorrect()
		}
		roundQuestions[currentQuestion].isUserAnswerCorrect = isAnswerCorrect
		currentQuestion++
		if (currentQuestion >= roundQuestions.size) {
			var count = 0
			for (answer in roundQuestions) {
				if (answer.isUserAnswerCorrect!!) {
					count++
				}
			}
			currentQuestion = -1
			roundQuestions.clear()
			view.showResult(count)
		} else {
			view.startRound(roundQuestions[currentQuestion].eng, roundQuestions[currentQuestion].spa)
		}
	}
}