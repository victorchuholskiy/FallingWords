package com.gmail.victorchuholskiy.languagegame.main

import com.gmail.victorchuholskiy.languagegame.BasePresenter
import com.gmail.victorchuholskiy.languagegame.BaseView

/**
 * Created by victor.chuholskiy
 * 06/08/18.
 *
 * This specifies the contract between the view and the presenter.
 */
interface MainContract {

	interface View : BaseView<Presenter> {

	}

	interface Presenter : BasePresenter {

	}
}