package com.gmail.victorchuholskiy.languagegame.useCases

import com.gmail.victorchuholskiy.languagegame.base.BaseSchedulersTest
import com.gmail.victorchuholskiy.languagegame.data.models.TranslationModel
import com.gmail.victorchuholskiy.languagegame.data.models.TranslationQuestion
import com.gmail.victorchuholskiy.languagegame.useCases.prepareRoundQuestions.PrepareRoundQuestionsUseCaseImpl
import io.reactivex.Observable
import org.junit.Test
import io.reactivex.observers.TestObserver
import java.util.*


/**
 * Created by victor.chuholskiy
 * 07.08.2018.
 */
class PrepareRoundUseCaseTest: BaseSchedulersTest() {

	@Test
	fun prepareRound_fullRange() {
		val list = ArrayList<TranslationModel>()
		list.add(TranslationModel("one", "uno"))
		list.add(TranslationModel("two", "dos"))
		list.add(TranslationModel("three", "tres"))
		list.add(TranslationModel("four", "cuatro"))
		list.add(TranslationModel("five", "cinco"))
		list.add(TranslationModel("six", "seis"))
		list.add(TranslationModel("seven", "siete"))
		list.add(TranslationModel("eight", "ocho"))
		list.add(TranslationModel("nine", "nueve"))
		list.add(TranslationModel("ten", "diez"))

		val listEng = ArrayList<String>()
		for (model in list) {
			listEng.add(model.eng!!)
		}

		val testObserver: TestObserver<List<TranslationQuestion>> = PrepareRoundQuestionsUseCaseImpl(list).execute().test()
		testObserver
				.assertNoErrors()
				.assertValue {
					it.isNotEmpty() && it.size == list.size
				}
				.assertValue {
					Observable.fromIterable(it)
							.map(TranslationQuestion::eng)
							.toList()
							.blockingGet()
							.containsAll(listEng)
				}
	}

	@Test
	fun prepareRound_oneElement() {
		val list = ArrayList<TranslationModel>()
		list.add(TranslationModel("one", "uno"))

		val listEng = ArrayList<String>()
		for (model in list) {
			listEng.add(model.eng!!)
		}

		val testObserver: TestObserver<List<TranslationQuestion>> = PrepareRoundQuestionsUseCaseImpl(list).execute().test()
		testObserver
				.assertNoErrors()
				.assertValue {
					it.isNotEmpty()
							&& it.size == list.size
							&& it[0].eng == list[0].eng
							&& it[0].spa == list[0].spa
				}
	}

	@Test
	fun prepareRound_zeroElements() {
		val testObserver: TestObserver<List<TranslationQuestion>> = PrepareRoundQuestionsUseCaseImpl(arrayListOf()).execute().test()
		testObserver
				.assertNoErrors()
				.assertValue {
					it.isEmpty()
				}
	}
}
