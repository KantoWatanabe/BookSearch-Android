package kantowatanbae.booksearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kantowatanbae.booksearch.model.Books
import kotlinx.android.synthetic.main.books_item.view.*
import kantowatanbae.booksearch.R

class BooksAdapter(private val context: Context, private val books: List<Books>) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    class BooksViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val title : TextView = view.title
        private val thumbnail: ImageView = view.thumbnail

        fun bind(context: Context, books: List<Books>, position: Int) {
            val item = books[position]
            title.text = item.title
            Glide.with(context).load(item.thumbnail).into(thumbnail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.books_item, parent,false)
        return BooksViewHolder(item)
    }


    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(context, books, position)
    }
}