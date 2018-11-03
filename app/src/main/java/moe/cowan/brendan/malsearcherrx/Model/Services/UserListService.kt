package moe.cowan.brendan.malsearcherrx.Model.Services

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Model.DataModels.UserAnimeList

interface UserListService {
    fun getUserList(username: String): Observable<UserAnimeList>
}