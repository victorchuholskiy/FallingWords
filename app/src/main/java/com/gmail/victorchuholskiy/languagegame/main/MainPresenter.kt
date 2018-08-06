package com.gmail.victorchuholskiy.languagegame.main

/**
 * Created by victor.chuholskiy
 * 06/08/18.
 */
class MainPresenter(private val view: MainContract.View)
	: MainContract.Presenter {

	init {
		view.presenter = this
	}

	override fun start() {

	}
}