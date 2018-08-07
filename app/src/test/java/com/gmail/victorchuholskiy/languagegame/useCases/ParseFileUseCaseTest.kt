package com.gmail.victorchuholskiy.languagegame.useCases

import android.content.res.AssetManager
import com.gmail.victorchuholskiy.languagegame.useCases.parseFile.ParseFileUseCase
import com.gmail.victorchuholskiy.languagegame.useCases.parseFile.ParseFileUseCaseImpl
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import org.junit.BeforeClass
import org.junit.AfterClass

/**
 * Created by victor.chuholskiy
 * 07.08.2018.
 */
class ParseFileUseCaseTest {

	private var parseFileUseCase: ParseFileUseCase? = null

	private var assetManager = Mockito.mock(AssetManager::class.java)

	@Before
	fun setUp() {
		parseFileUseCase = ParseFileUseCaseImpl(assetManager)
	}

	@Test
	fun parseFile() {
		parseFileUseCase!!.execute()
	}

	/*@BeforeClass
	fun setUpRxSchedulers() {
		val immediate = object : Scheduler() {
			override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
				// this prevents StackOverflowErrors when scheduling with a delay
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
	}*/

	companion object {
		@BeforeClass
		fun setUpClass() {
			RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
		}

		@AfterClass
		fun tearDownClass() {
			RxAndroidPlugins.reset()
		}
	}
}
