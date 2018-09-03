package moe.cowan.brendan.malsearcherrx.Model

import android.os.SystemClock
import io.reactivex.Observable

class AlwaysValidLoginService : LoginService {
    override fun verifyLogin(username: String): Observable<Boolean> {
        return Observable.create { emitter ->
            SystemClock.sleep(2000)
            emitter.onNext(true)
        }
    }
}