package com.gmail.victorchuholskiy.languagegame.main.game

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import com.gmail.victorchuholskiy.languagegame.R
import android.view.animation.TranslateAnimation
import com.gmail.victorchuholskiy.languagegame.utils.ANSWER_TIMER_MILLISECONDS
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by victor.chuholskiy
 * 06/08/18.
 */
class GameFragment : Fragment(), GameContract.View {

	override lateinit var presenter: GameContract.Presenter

	private lateinit var animation: TranslateAnimation

	override fun onCreateView(inflater: LayoutInflater,
							  container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment_main, container, false)

		val gameAreaView = view.findViewById<View>(R.id.v_game_area)
		gameAreaView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
			override fun onGlobalLayout() {
				gameAreaView.viewTreeObserver.removeOnGlobalLayoutListener(this)
				animation = TranslateAnimation(0f,0f,0f, gameAreaView.height.toFloat())
				animation.duration = ANSWER_TIMER_MILLISECONDS
				animation.setAnimationListener(object : Animation.AnimationListener {
					override fun onAnimationRepeat(p0: Animation?) {

					}

					override fun onAnimationEnd(p0: Animation?) {
						presenter.timerOut()
					}

					override fun onAnimationStart(p0: Animation?) {

					}
				})
				presenter.gameAreaReady()
			}
		})

		view.findViewById<View>(R.id.btn_right).setOnClickListener {
			presenter.rightBtnClick()
		}

		view.findViewById<View>(R.id.btn_wrong).setOnClickListener {
			presenter.wrongBtnClick()
		}
		return view
	}

	override fun onResume() {
		super.onResume()
		presenter.start()
	}

	override fun startRound(word: String, translate: String) {
		tv_question.text = String.format(getString(R.string.is_translation_correct), word)
		tv_translation.text = translate
		tv_translation.startAnimation(animation)
	}

	override fun answerCorrect() {
		Log.d("AAA", "answerCorrect")
	}

	override fun answerIncorrect() {
		Log.d("AAA", "answerIncorrect")
	}

	companion object {
		fun newInstance() = GameFragment()
	}
}