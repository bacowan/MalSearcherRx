package moe.cowan.brendan.malsearcherrx.Model.Services

import android.os.SystemClock
import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Model.DataModels.Anime
import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter
import moe.cowan.brendan.malsearcherrx.Model.DataModels.Language
import moe.cowan.brendan.malsearcherrx.Utilities.Optional

class OnlyPikachuCharacterSearcher : CharacterSearcher {
    @Override
    override fun getMatchingCharacter(searchString: String, anime: Optional<Anime>): Observable<List<AnimeCharacter>> {

        val english = Language("English")
        val japanese = Language("Japanese")
        val french = Language("French")

        val pikachu = AnimeCharacter("Pikachu", "https://cdn.vox-cdn.com/thumbor/6x2xbMVb75Lefx-ZusHzwttXR_c=/0x0:1280x960/920x613/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg", listOf(english, japanese, french), 1)
        val pichu = AnimeCharacter("Pichu", "http://static.pokemonpets.com/images/monsters-images-300-300/4172-Pichu-Spikyeared.png", listOf(english, japanese), 2)
        val notPikachu = AnimeCharacter("Not Pikachu", "https://cdn.bulbagarden.net/upload/thumb/4/42/183Marill.png/600px-183Marill.png", listOf(english), 3)
        val alsoNotPikachu = AnimeCharacter("Also Not Pikachu", "https://cdn.bulbagarden.net/upload/thumb/9/9b/778Mimikyu.png/250px-778Mimikyu.png", listOf(english, japanese), 4)

        return Observable.create { emitter ->
            SystemClock.sleep(500)
            anime.ifPresent(
                {
                    if (it.databaseId == 1.toLong()) {
                        emitter.onNext(listOf(pikachu, pichu))
                    } else {
                        emitter.onNext(listOf(notPikachu, alsoNotPikachu))
                    }
                },
                {
                    emitter.onNext(listOf(pikachu, notPikachu, pichu, alsoNotPikachu))
                }
            )
        }
    }
}