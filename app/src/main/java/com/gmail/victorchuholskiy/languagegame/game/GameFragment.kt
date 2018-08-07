package com.gmail.victorchuholskiy.languagegame.game

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.gmail.victorchuholskiy.languagegame.R
import android.view.animation.TranslateAnimation
import com.gmail.victorchuholskiy.languagegame.utils.ANSWER_TIMER_MILLISECONDS
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_play_area.*
import kotlinx.android.synthetic.main.layout_results.*

/**
 * Created by victor.chuholskiy
 * 06/08/18.
 */
class GameFragment : Fragment(), GameContract.View {

	override lateinit var presenter: GameContract.Presenter

	private lateinit var wordAnimation: AnimationSet
	private lateinit var blinkingAnimation: Animation

	override fun onCreateView(inflater: LayoutInflater,
							  container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment_main, container, false)

		blinkingAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha_hide)
		blinkingAnimation.setAnimationListener(object : Animation.AnimationListener {
					override fun onAnimationRepeat(p0: Animation?) {

					}

					override fun onAnimationEnd(p0: Animation?) {
						v_answer_signal.background = null
					}

					override fun onAnimationStart(p0: Animation?) {

					}
				})

		val gameAreaView = view.findViewById<View>(R.id.v_game_area)
		gameAreaView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
			override fun onGlobalLayout() {
				gameAreaView.viewTreeObserver.removeOnGlobalLayoutListener(this)
				wordAnimation = AnimationSet(true)

				val movingAnimation = TranslateAnimation(0f,0f,-100f, gameAreaView.height.toFloat() - 100f)
				movingAnimation.duration = ANSWER_TIMER_MILLISECONDS
				movingAnimation.setAnimationListener(object : Animation.AnimationListener {
					override fun onAnimationRepeat(p0: Animation?) {

					}

					override fun onAnimationEnd(p0: Animation?) {
						presenter.timerOut()
					}

					override fun onAnimationStart(p0: Animation?) {

					}
				})

				val blinkAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_blink)
				blinkAnimation.duration = ANSWER_TIMER_MILLISECONDS / 2

				wordAnimation.addAnimation(movingAnimation)
				wordAnimation.addAnimation(blinkAnimation)
				presenter.gameAreaReady()
			}
		})

		view.findViewById<View>(R.id.btn_right).setOnClickListener {
			presenter.rightBtnClick()
		}

		view.findViewById<View>(R.id.btn_wrong).setOnClickListener {
			presenter.wrongBtnClick()
		}

		view.findViewById<View>(R.id.btn_repeat).setOnClickListener {
			game_area_layout.visibility = View.VISIBLE
			game_result_layout.visibility = View.GONE
			presenter.start()
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
		tv_translation.startAnimation(wordAnimation)
	}

	override fun answerCorrect() {
		v_answer_signal.setBackgroundResource(R.drawable.bg_right_answer)
		v_answer_signal.startAnimation(blinkingAnimation)
	}

	override fun answerIncorrect() {
		v_answer_signal.setBackgroundResource(R.drawable.bg_wrong_answer)
		v_answer_signal.startAnimation(blinkingAnimation)
	}

	override fun showResult(correctAnswersNumber: Int) {
		game_area_layout.visibility = View.GONE
		game_result_layout.visibility = View.VISIBLE
		tv_count_correct_answers.text = String.format(getString(R.string.count_of_correct_answers), correctAnswersNumber)
	}

	companion object {
		fun newInstance() = GameFragment()
	}
}