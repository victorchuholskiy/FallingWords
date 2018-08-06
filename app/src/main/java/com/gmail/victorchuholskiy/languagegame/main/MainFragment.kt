package com.gmail.victorchuholskiy.languagegame.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.victorchuholskiy.languagegame.R

/**
 * Created by victor.chuholskiy
 * 06/08/18.
 */
class MainFragment : Fragment(), MainContract.View {

	override lateinit var presenter: MainContract.Presenter

	override fun onCreateView(inflater: LayoutInflater,
							  container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment_main, container, false)
		return view
	}

	companion object {
		fun newInstance() = MainFragment()
	}
}