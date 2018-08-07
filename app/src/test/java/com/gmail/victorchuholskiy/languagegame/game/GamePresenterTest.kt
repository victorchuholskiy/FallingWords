package com.gmail.victorchuholskiy.languagegame.game

import android.content.Context
import android.content.res.AssetManager
import com.gmail.victorchuholskiy.languagegame.base.BaseSchedulersTest
import com.gmail.victorchuholskiy.languagegame.capture
import com.gmail.victorchuholskiy.languagegame.data.models.TranslationModel
import com.gmail.victorchuholskiy.languagegame.data.models.TranslationQuestion
import com.gmail.victorchuholskiy.languagegame.useCases.ParseFileUseCaseTest
import com.gmail.victorchuholskiy.languagegame.useCases.parseFile.ParseFileUseCase
import com.gmail.victorchuholskiy.languagegame.useCases.prepareRoundQuestions.PrepareRoundQuestionsUseCase
import io.reactivex.Observable
import io.reactivex.observers.TestObserver

import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import java.io.FileInputStream

/**
 * Created by victor.chuholskiy
 * 07/08/18.
 *
 * Unit tests for the implementation of [GamePresenter]
 */
class GamePresenterTest: BaseSchedulersTest() {

	@Mock private lateinit var context: Context
	@Mock private lateinit var gameView: GameContract.View
	@Mock private lateinit var assetManager: AssetManager
	@Mock private lateinit var parseFileUseCase: ParseFileUseCase
	@Mock private lateinit var prepareRoundQuestionsUseCase: PrepareRoundQuestionsUseCase

	@Captor private lateinit var getStringArgumentsCaptor: ArgumentCaptor<String>

	private lateinit var gamePresenter: GamePresenter

	@Before
	fun setupStatisticsPresenter() {
		MockitoAnnotations.initMocks(this)

		Mockito.doReturn(assetManager).`when`(context).assets
		val resource = ParseFileUseCaseTest::class.java.classLoader.getResource("words_test.json")
		val inputStream = FileInputStream(resource.path)
		Mockito.doReturn(inputStream).`when`(assetManager).open(ArgumentMatchers.anyString())

		// Get a reference to the class under test
		gamePresenter = GamePresenter(gameView, assetManager)
	}

	@Test
	fun createPresenter_setsThePresenterToView() {
		// Get a reference to the class under test
		gamePresenter = GamePresenter(gameView, assetManager)

		// Then the presenter is set to the view
		verify(gameView).presenter = gamePresenter
	}

	@Test
	fun startFirstRound_GameAreaReady_CallViewToDisplay() {
		val eng = "one"
		val spa = "uno"
		val models = ArrayList<TranslationModel>()
		models.add(TranslationModel(eng, spa))

		val questions = ArrayList<TranslationQuestion>()
		questions.add(TranslationQuestion(eng, spa, true))

		`when`(parseFileUseCase.execute()).thenReturn(Observable.just<List<TranslationModel>>(models))
		val testObserver = TestObserver<List<TranslationModel>>()
		parseFileUseCase.execute().subscribe(testObserver)
		testObserver.assertValueCount(1)
		testObserver.assertNoErrors()

		`when`(prepareRoundQuestionsUseCase.execute()).thenReturn(Observable.just<List<TranslationQuestion>>(questions))
		val testQuestionObserver = TestObserver<List<TranslationQuestion>>()
		prepareRoundQuestionsUseCase.execute().subscribe(testQuestionObserver)
		testQuestionObserver.assertValueCount(1)
		testQuestionObserver.assertNoErrors()

		gamePresenter.gameAreaReady()
		gamePresenter.start()

		verify(gameView).startRound(capture(getStringArgumentsCaptor), capture(getStringArgumentsCaptor))
	}

	@Test
	fun startFirstRound_GameAreaNotReady() {
		val eng = "one"
		val spa = "uno"
		val models = ArrayList<TranslationModel>()
		models.add(TranslationModel(eng, spa))

		val questions = ArrayList<TranslationQuestion>()
		questions.add(TranslationQuestion(eng, spa, true))

		`when`(parseFileUseCase.execute()).thenReturn(Observable.just<List<TranslationModel>>(models))
		val testObserver = TestObserver<List<TranslationModel>>()
		parseFileUseCase.execute().subscribe(testObserver)
		testObserver.assertValueCount(1)
		testObserver.assertNoErrors()

		`when`(prepareRoundQuestionsUseCase.execute()).thenReturn(Observable.just<List<TranslationQuestion>>(questions))
		val testQuestionObserver = TestObserver<List<TranslationQuestion>>()
		prepareRoundQuestionsUseCase.execute().subscribe(testQuestionObserver)
		testQuestionObserver.assertValueCount(1)
		testQuestionObserver.assertNoErrors()

		gamePresenter.start()
	}

	@Test
	fun timerOut_CallViewToDisplay() {
		val models = ArrayList<TranslationModel>()
		models.add(TranslationModel("one", "uno"))
		models.add(TranslationModel("two", "dos"))

		val questions = ArrayList<TranslationQuestion>()
		for (model in models) {
			questions.add(TranslationQuestion(model.eng!!, model.spa!!, true))
		}

		`when`(parseFileUseCase.execute()).thenReturn(Observable.just<List<TranslationModel>>(models))
		val testObserver = TestObserver<List<TranslationModel>>()
		parseFileUseCase.execute().subscribe(testObserver)
		testObserver.assertValueCount(1)
		testObserver.assertNoErrors()

		`when`(prepareRoundQuestionsUseCase.execute()).thenReturn(Observable.just<List<TranslationQuestion>>(questions))
		val testQuestionObserver = TestObserver<List<TranslationQuestion>>()
		prepareRoundQuestionsUseCase.execute().subscribe(testQuestionObserver)
		testQuestionObserver.assertValueCount(1)
		testQuestionObserver.assertNoErrors()

		gamePresenter.gameAreaReady()
		gamePresenter.start()
		gamePresenter.timerOut()
		verify(gameView).answerIncorrect()
		verify(gameView, times(2)).startRound(capture(getStringArgumentsCaptor), capture(getStringArgumentsCaptor))
	}
}