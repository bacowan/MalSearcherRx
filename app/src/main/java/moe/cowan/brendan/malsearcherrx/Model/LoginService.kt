package moe.cowan.brendan.malsearcherrx.Model

import io.reactivex.Observable

interface LoginService {
    fun VerifyLogin(username: String) : Observable<Boolean>
}