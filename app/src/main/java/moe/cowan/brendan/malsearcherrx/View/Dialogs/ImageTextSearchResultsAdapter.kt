package moe.cowan.brendan.malsearcherrx.View.Dialogs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.ImageTextSearchResultUIModel

class ImageTextSearchResultsAdapter(
        private val dataSet: List<ImageTextSearchResultUIModel>,
        private val clickListener: ClickListener<ImageTextSearchResultUIModel>)
    : RecyclerView.Adapter<ImageTextSearchResultsAdapter.SearchResultViewHolder>()
{

    class SearchResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_text_search_result_row, parent, false)
        return SearchResultViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val boundItem = dataSet[position]

        holder.itemView.setOnClickListener {
            clickListener.onClick(boundItem)
        }

        holder.textView.text = dataSet[position].title

        if (boundItem.imageUrl != "") {
            Picasso.with(holder.textView.context)
                    .load(boundItem.imageUrl)
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_broken_image)
                    .fit()
                    .into(holder.imageView)
        }
        else {
            holder.imageView.setImageResource(R.drawable.ic_broken_image)
        }
    }

    @Override
    override fun getItemCount() = dataSet.size
}