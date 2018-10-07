package moe.cowan.brendan.malsearcherrx.View

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.Reactive.UIModels.Search.SearchResultUIModel

class SearchDialogAdapter(private val dataSet: List<SearchResultUIModel>): RecyclerView.Adapter<SearchDialogAdapter.SearchResultViewHolder>() {
    class SearchResultViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_row, parent, false) as TextView
        return SearchResultViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.textView.text = dataSet[position].title
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher_background, 0, 0, 0)
    }

    @Override
    override fun getItemCount() = dataSet.size
}