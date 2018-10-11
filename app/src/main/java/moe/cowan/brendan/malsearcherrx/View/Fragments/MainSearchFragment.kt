package moe.cowan.brendan.malsearcherrx.View.Fragments

import android.content.Intent
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.search_fragment.*
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.MainSearchUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.MainSearchUIPost
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.ShowAnimeSearch
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.AnimeSearchViewModel
import moe.cowan.brendan.malsearcherrx.Presenter.ViewModels.CharacterSearchViewModel
import moe.cowan.brendan.malsearcherrx.View.Dialogs.SearchDialog
import moe.cowan.brendan.malsearcherrx.View.Dialogs.SearchResultKey
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIPosts.ShowCharacterSearch
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.*
import javax.inject.Inject

const val AnimeResultCode = 1
const val CharacterResultCode = 2

class MainSearchFragment : ReactiveFragment<MainSearchUIEvent, MainSearchUIModel, MainSearchUIPost>() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override val layout get() = R.layout.search_fragment

    private val searchResultsSubject: BehaviorSubject<SearchResultUIModel> = BehaviorSubject.create()

    @Override
    override fun setupUiEvents() : Observable<MainSearchUIEvent> {
        val searchAnimeEvents = RxView.clicks(anime_search_button)
                .map { StartAnimeSearchEvent() as MainSearchUIEvent }

        val searchCharacterEvents = RxView.clicks(character_search_button)
                .map { StartCharacterSearchEvent() as MainSearchUIEvent }

        val searchLanguageEvents = RxView.clicks(language_search_button)
                .map { StartLanguageSearchEvent() as MainSearchUIEvent }

        val searchAnimeResultEvents = searchResultsSubject
                .map { SearchAnimeResultEvent(it) }

        return searchAnimeEvents
                .mergeWith(searchCharacterEvents)
                .mergeWith(searchLanguageEvents)
                .mergeWith(searchAnimeResultEvents)
    }

    @Override
    override fun updateUIPost(post: MainSearchUIPost) {
        when (post) {
            is ShowAnimeSearch -> {
                val fragment = fragmentFactory.createDialogFragment<SearchDialog, AnimeSearchViewModel>()
                fragment.setTargetFragment(this, AnimeResultCode)
                fragment.show(fragmentManager, "")
            }
            is ShowCharacterSearch -> {
                val fragment = fragmentFactory.createDialogFragment<SearchDialog, CharacterSearchViewModel>()
                fragmentFactory.initializeViewModel<CharacterSearchViewModel>(this) { vm -> vm.anime = currentModel?.anime }
                fragment.setTargetFragment(this, CharacterResultCode)
                fragment.show(fragmentManager, "")
            }
        }
    }

    @Override
    override fun updateUIModel(model: MainSearchUIModel) {
        if (model.anime != null) {
            anime_search_button.text = model.anime.title
        }

        if (model.character != null) {
        }

        if (model.language != null) {
        }

    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AnimeResultCode) {
            val anime = data?.getSerializableExtra(SearchResultKey)
            if (anime is SearchResultUIModel) {
                searchResultsSubject.onNext(anime)
            }
        }
    }

}