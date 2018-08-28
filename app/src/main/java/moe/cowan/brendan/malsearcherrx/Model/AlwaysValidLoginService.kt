package moe.cowan.brendan.malsearcherrx.Model

import io.reactivex.Observable

class AlwaysValidLoginService: LoginService {
    override fun VerifyLogin(username: String): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}