package moe.cowan.brendan.malsearcherrx.Model.Services

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter
import moe.cowan.brendan.malsearcherrx.Utilities.Optional

interface CharacterSearcher {
    fun getMatchingCharacter(searchString: String, animeId: Optional<Long>): Observable<List<AnimeCharacter>>
}