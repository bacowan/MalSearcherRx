package moe.cowan.brendan.malsearcherrx.Model.Services

import io.reactivex.Observable

interface LoginService {
    fun verifyLogin(username: String) : Observable<Boolean>
}