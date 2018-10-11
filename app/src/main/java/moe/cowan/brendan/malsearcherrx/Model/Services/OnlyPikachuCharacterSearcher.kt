package moe.cowan.brendan.malsearcherrx.Model.Services

import android.os.SystemClock
import io.reactivex.Observable
import moe.cowan.brendan.malsearcherrx.Model.DataModels.AnimeCharacter

class OnlyPikachuCharacterSearcher: CharacterSearcher {
    @Override
    override fun getMatchingCharacter(searchString: String, animeId: Long?): Observable<List<AnimeCharacter>> {
        return Observable.create { emitter ->
            SystemClock.sleep(500)
            if (animeId == 1.toLong()) {
                emitter.onNext(listOf(
                        AnimeCharacter("Pikachu", "https://cdn.vox-cdn.com/thumbor/6x2xbMVb75Lefx-ZusHzwttXR_c=/0x0:1280x960/920x613/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg", 1),
                        AnimeCharacter("Pichu", "http://static.pokemonpets.com/images/monsters-images-300-300/4172-Pichu-Spikyeared.png", 2)
                ))
            }
            else {
                emitter.onNext(listOf(
                        AnimeCharacter("Not Pikachu", "https://cdn.bulbagarden.net/upload/thumb/4/42/183Marill.png/600px-183Marill.png", 3),
                        AnimeCharacter("Also Not Pikachu", "https://cdn.bulbagarden.net/upload/thumb/9/9b/778Mimikyu.png/250px-778Mimikyu.png", 4)
                ))
            }
        }
    }
}