package com.gmail.victorchuholskiy.languagegame.game

import com.gmail.victorchuholskiy.languagegame.BasePresenter
import com.gmail.victorchuholskiy.languagegame.BaseView

/**
 * Created by victor.chuholskiy
 * 06/08/18.
 *
 * This specifies the contract between the view and the presenter.
 */
interface GameContract {

	interface View : BaseView<Presenter> {
		fun startRound(word: String, translate: String)

		fun answerCorrect()

		fun answerIncorrect()

		fun showResult(correctAnswersNumber: Int)
	}

	interface Presenter : BasePresenter {
		fun gameAreaReady()

		fun rightBtnClick()

		fun wrongBtnClick()

		fun timerOut()
	}
}