package moe.cowan.brendan.malsearcherrx.View.Dialogs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import moe.cowan.brendan.malsearcherrx.R
import moe.cowan.brendan.malsearcherrx.View.UIData.UIModels.Search.SearchResultUIModel

class SearchResultsAdapter(private val dataSet: List<SearchResultUIModel>): RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder>() {
    class SearchResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_row, parent, false)
        return SearchResultViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.textView.text = dataSet[position].title
        if (dataSet[position].imageUrl != "") {
            Picasso.with(holder.textView.context)
                    .load(dataSet[position].imageUrl)
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