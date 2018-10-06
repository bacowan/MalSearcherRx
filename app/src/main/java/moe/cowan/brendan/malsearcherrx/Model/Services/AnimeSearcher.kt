package moe.cowan.brendan.malsearcherrx.Model.Services

import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime

interface AnimeSearcher {
    fun getMatchingAnime(searchString: String): Observable<List<Anime>>
}