package com.gmail.victorchuholskiy.languagegame.base

import android.support.annotation.NonNull
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * Created by viktor.chukholskiy
 * 07/08/18.
 */
open class BaseSchedulersTest {
	@Before
	fun setUpRxSchedulers() {
		val immediate = object : Scheduler() {
			override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
				return super.scheduleDirect(run, 0, unit)
			}

			override fun createWorker(): Worker {
				return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
			}
		}
		RxJavaPlugins.setInitIoSchedulerHandler { _ -> immediate }
		RxJavaPlugins.setInitComputationSchedulerHandler { _ -> immediate }
		RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediate }
		RxJavaPlugins.setInitSingleSchedulerHandler { _ -> immediate }
		RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediate }
	}
}