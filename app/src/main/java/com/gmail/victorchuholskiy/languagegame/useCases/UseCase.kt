package com.gmail.victorchuholskiy.languagegame.useCases

import io.reactivex.Observable

/**
 * Created by victor.chuholskiy
 * 06/08/18
 *
 * Base use case
 */
interface UseCase<RESPONSE> {
	fun execute(): Observable<RESPONSE>
}