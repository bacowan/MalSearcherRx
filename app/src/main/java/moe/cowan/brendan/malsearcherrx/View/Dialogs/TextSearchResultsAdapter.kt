package moe.cowan.brendan.malsearcherrx.View.Dialogs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.TextSearchResultUIModel

class TextSearchResultsAdapter(
        private val dataSet: List<TextSearchResultUIModel>,
        private val clickListener: ClickListener<TextSearchResultUIModel>)
    : RecyclerView.Adapter<TextSearchResultsAdapter.SearchResultViewHolder>()
{

    class SearchResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view)
    }

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_search_result_row, parent, false)
        return SearchResultViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val boundItem = dataSet[position]

        holder.itemView.setOnClickListener {
            clickListener.onClick(boundItem)
        }

        holder.textView.text = dataSet[position].text
    }

    @Override
    override fun getItemCount() = dataSet.size
}