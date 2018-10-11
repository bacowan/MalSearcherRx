package moe.cowan.brendan.malsearcherrx.Model.Services

import android.os.SystemClock
import io.reactivex.Observable
import javax.inject.Inject

class AlwaysValidLoginService @Inject constructor() : LoginService {
    override fun verifyLogin(username: String): Observable<Boolean> {
        return Observable.create { emitter ->
            SystemClock.sleep(500)
            emitter.onNext(true)
        }
    }
}