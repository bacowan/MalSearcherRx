package moe.cowan.brendan.malsearcherrx.Model

import io.reactivex.Observable
import javax.inject.Inject

class AlwaysValidLoginService @Inject constructor(): LoginService {
    override fun VerifyLogin(username: String): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}