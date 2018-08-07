package com.gmail.victorchuholskiy.languagegame.rules

import android.support.annotation.NonNull
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.disposables.Disposable
import io.reactivex.Scheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import org.junit.runners.model.Statement


/**
 * Created by victor.chuholskiy
 * 07.08.2018.
 */
class RxImmediateSchedulerRule : TestRule {
	private val immediate = object : Scheduler() {
		override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
			// this prevents StackOverflowErrors when scheduling with a delay
			return super.scheduleDirect(run, 0, unit)
		}

		override fun createWorker(): Worker {
			return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
		}
	}

	override fun apply(base: Statement?, description: Description?): Statement {
		return object : Statement() {
			@Throws(Throwable::class)
			override fun evaluate() {
				RxJavaPlugins.setInitIoSchedulerHandler { _ -> immediate }
				RxJavaPlugins.setInitComputationSchedulerHandler { _ -> immediate }
				RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediate }
				RxJavaPlugins.setInitSingleSchedulerHandler { _ -> immediate }
				RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediate }

				try {
					base!!.evaluate()
				} finally {
					RxJavaPlugins.reset()
					RxAndroidPlugins.reset()
				}
			}
		}
	}
}