package com.gmail.victorchuholskiy.languagegame.splash

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import com.gmail.victorchuholskiy.languagegame.main.MainActivity

/**
 * Created by victor.chuholskiy
 * 06/08/18
 *
 * Simple splash activity for solving the problem of blank white page at the start of the app
 * and loading list of categories during the first run
 */
class SplashActivity : AppCompatActivity() {

	override fun onResume() {
		super.onResume()
		startActivity(Intent(this@SplashActivity, MainActivity::class.java))
		finish()
	}
}