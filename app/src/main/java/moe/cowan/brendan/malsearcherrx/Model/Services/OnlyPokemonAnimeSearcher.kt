package moe.cowan.brendan.malsearcherrx.Model.Services

import android.os.SystemClock
import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime

class OnlyPokemonAnimeSearcher: AnimeSearcher {
    @Override
    override fun getMatchingAnime(searchString: String): Observable<List<Anime>> {
        return Observable.create { emitter ->
            SystemClock.sleep(500)
            emitter.onNext(listOf(
                    Anime("Original Pokemon", "https://cdn.ndtv.com/tech/images/gadgets/pikachu_hi_pokemon.jpg?output-quality=70&output-format=webp", 1),
                    Anime("New Pokemon", "https://cdn.bulbagarden.net/upload/b/b9/172Pichu.png", 2)
            ))
        }
    }
}