package com.gmail.victorchuholskiy.languagegame.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gmail.victorchuholskiy.languagegame.R
import com.gmail.victorchuholskiy.languagegame.utils.replaceFragmentInActivity
import com.gmail.victorchuholskiy.languagegame.utils.setupActionBar

class MainActivity : AppCompatActivity() {

	private lateinit var presenter: MainPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// Set up the toolbar.
		setupActionBar(R.id.toolbar) {}

		val galleryFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
				as MainFragment? ?: MainFragment.newInstance().also {
			replaceFragmentInActivity(it, R.id.contentFrame)
		}

		// Create the presenter
		presenter = MainPresenter(galleryFragment)
	}
}
