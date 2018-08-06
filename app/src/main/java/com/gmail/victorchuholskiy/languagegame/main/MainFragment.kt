package com.gmail.victorchuholskiy.languagegame.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.victorchuholskiy.languagegame.R
import android.view.animation.TranslateAnimation
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main.*
import android.util.DisplayMetrics
import android.util.Log

/**
 * Created by victor.chuholskiy
 * 06/08/18.
 */
class MainFragment : Fragment(), MainContract.View {

	override lateinit var presenter: MainContract.Presenter

	private var gameAreaHeight: Int = 0

	override fun onCreateView(inflater: LayoutInflater,
							  container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment_main, container, false)

		val gameAreaView = view.findViewById<View>(R.id.v_game_area)
		gameAreaView.viewTreeObserver.addOnGlobalLayoutListener {
			gameAreaHeight = gameAreaView.height
		}

		view.findViewById<TextView>(R.id.tv_question).text = String.format(getString(R.string.is_translation_correct), "test")
		return view
	}

	override fun onResume() {
		super.onResume()
		startAnim()
	}

	fun startAnim() {
		val animation = TranslateAnimation(0f,0f,0f, gameAreaHeight.toFloat())
		animation.duration = 6000
		tv_translation.startAnimation(animation)
	}

	companion object {
		fun newInstance() = MainFragment()
	}
}