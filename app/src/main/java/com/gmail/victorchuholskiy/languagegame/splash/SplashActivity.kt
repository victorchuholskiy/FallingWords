package com.gmail.victorchuholskiy.languagegame.splash

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import com.gmail.victorchuholskiy.languagegame.MainActivity

/**
 * Created by viktor.chukholskiy
 * 24/07/18.
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