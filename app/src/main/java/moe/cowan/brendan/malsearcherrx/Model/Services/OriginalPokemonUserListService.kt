package moe.cowan.brendan.malsearcherrx.Model.Services

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime
import moe.cowan.brendan.malsearcherrx.Model.DataModels.UserAnimeList

class OriginalPokemonUserListService: UserListService {
    override fun getUserList(username: String): Observable<UserAnimeList> {
        return Observable.create { emitter ->
            emitter.onNext(UserAnimeList(listOf(
                    Anime("Original Pokemon", "https://cdn.ndtv.com/tech/images/gadgets/pikachu_hi_pokemon.jpg?output-quality=70&output-format=webp", 1))))
        }
    }
}