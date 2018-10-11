package moe.cowan.brendan.malsearcherrx.Model.Services

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter

interface CharacterSearcher {
    fun getMatchingCharacter(searchString: String, animeId: Long?): Observable<List<AnimeCharacter>>
}