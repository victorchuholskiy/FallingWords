package com.gmail.victorchuholskiy.languagegame.useCases

import android.content.Context
import android.content.res.AssetManager
import com.gmail.victorchuholskiy.languagegame.base.BaseSchedulersTest
import com.gmail.victorchuholskiy.languagegame.data.models.TranslationModel
import com.gmail.victorchuholskiy.languagegame.useCases.parseFile.ParseFileUseCase
import com.gmail.victorchuholskiy.languagegame.useCases.parseFile.ParseFileUseCaseImpl
import io.reactivex.Observable
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import io.reactivex.observers.TestObserver
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.mockito.ArgumentMatchers.anyString
import java.io.FileInputStream
import java.util.*

/**
 * Created by victor.chuholskiy
 * 07.08.2018.
 *
 * Unit tests for the implementation of [ParseFileUseCaseImpl]
 */
class ParseFileUseCaseTest: BaseSchedulersTest() {

	@Mock private lateinit var context: Context
	@Mock private lateinit var assetManager: AssetManager

	private var parseFileUseCase: ParseFileUseCase? = null

	@Before
	fun setUp() {
		MockitoAnnotations.initMocks(this)
		doReturn(assetManager).`when`(context).assets

		val resource = ParseFileUseCaseTest::class.java.classLoader.getResource("words_test.json")
		val inputStream = FileInputStream(resource.path)
		doReturn(inputStream).`when`(assetManager).open(anyString())

		parseFileUseCase = ParseFileUseCaseImpl(assetManager)
	}

	@Test
	fun parseFile_checkCorrectness() {
		val testObserver: TestObserver<List<TranslationModel>> = parseFileUseCase!!.execute().test()
		testObserver
				.assertNoErrors()
				.assertValue {
					it.isNotEmpty()
				}
				.assertValue {
					Observable.fromIterable(it)
							.map(TranslationModel::eng)
							.toList()
							.blockingGet() == Arrays.asList("primary school", "teacher", "pupil")
				}
	}
}
