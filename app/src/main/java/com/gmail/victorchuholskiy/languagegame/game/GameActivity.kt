package com.gmail.victorchuholskiy.languagegame.game

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gmail.victorchuholskiy.languagegame.R
import com.gmail.victorchuholskiy.languagegame.utils.replaceFragmentInActivity
import com.gmail.victorchuholskiy.languagegame.utils.setupActionBar

class GameActivity : AppCompatActivity() {

	private lateinit var presenter: GamePresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// Set up the toolbar.
		setupActionBar(R.id.toolbar) {}

		val galleryFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
				as GameFragment? ?: GameFragment.newInstance().also {
			replaceFragmentInActivity(it, R.id.contentFrame)
		}

		// Create the presenter
		presenter = GamePresenter(galleryFragment, assets)
	}
}
