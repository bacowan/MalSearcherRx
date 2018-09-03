package moe.cowan.brendan.malsearcherrx.Model

import io.reactivex.Observable

interface LoginService {
    fun verifyLogin(username: String) : Observable<Boolean>
}