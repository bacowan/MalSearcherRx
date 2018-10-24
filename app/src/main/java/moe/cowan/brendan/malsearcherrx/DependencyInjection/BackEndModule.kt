package moe.cowan.brendan.malsearcherrx.DependencyInjection

import android.support.v7.widget.RecyclerView
import dagger.Module
import dagger.Provides
import moe.cowan.brendan.malsearcherrx.Model.Services.*
import moe.cowan.brendan.malsearcherrx.View.Dialogs.ClickListener
import moe.cowan.brendan.malsearcherrx.View.Dialogs.ImageTextSearchResultsAdapter
import moe.cowan.brendan.malsearcherrx.View.Dialogs.SearchResultsAdapterFactory
import moe.cowan.brendan.malsearcherrx.View.Dialogs.TextSearchResultsAdapter
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.ImageTextSearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.ImageTextSearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.TextSearchDialogUIModel
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.TextSearchResultUIModel
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.DialogSearchUIEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.ImageTextSearchItemClickEvent
import moe.cowan.brendan.malsearcherrx.View.UIEvents.Search.TextSearchItemClickEvent

@Module
class BackEndModule {
    @Provides
    fun LoginService() : LoginService = AlwaysValidLoginService()

    @Provides
    fun AnimeSearcher() : AnimeSearcher = OnlyPokemonAnimeSearcher()

    @Provides
    fun CharacterSearcher() : CharacterSearcher = OnlyPikachuCharacterSearcher()

    @Provides
    fun ImageTextSearchResutsAdapterFactory(): SearchResultsAdapterFactory<ImageTextSearchDialogUIModel, ImageTextSearchResultUIModel> {
        return object: SearchResultsAdapterFactory<ImageTextSearchDialogUIModel, ImageTextSearchResultUIModel> {
            override fun newAdapter(model: ImageTextSearchDialogUIModel, clickListener: ClickListener<ImageTextSearchResultUIModel>): RecyclerView.Adapter<*> {
                return ImageTextSearchResultsAdapter(model.searchResults, clickListener)
            }

            override fun clickEvent(clickedItem: ImageTextSearchResultUIModel): DialogSearchUIEvent {
                return ImageTextSearchItemClickEvent(clickedItem)
            }
        }
    }

    @Provides
    fun TextSearchResutsAdapterFactory(): SearchResultsAdapterFactory<TextSearchDialogUIModel, TextSearchResultUIModel> {
        return object: SearchResultsAdapterFactory<TextSearchDialogUIModel, TextSearchResultUIModel> {
            override fun newAdapter(model: TextSearchDialogUIModel, clickListener: ClickListener<TextSearchResultUIModel>): RecyclerView.Adapter<*> {
                return TextSearchResultsAdapter(model.searchResults, clickListener)
            }

            override fun clickEvent(clickedItem: TextSearchResultUIModel): DialogSearchUIEvent {
                return TextSearchItemClickEvent(clickedItem)
            }
        }
    }
}