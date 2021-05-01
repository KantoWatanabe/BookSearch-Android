package kantowatanbae.booksearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kantowatanbae.booksearch.model.Books
import kotlinx.android.synthetic.main.books_item.view.*
import kantowatanbae.booksearch.R


class BooksViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val title : TextView = view.title

    fun bind(books: List<Books>, position: Int) {
        val item = books[position]
        title.text = item.title
    }

}

class BooksAdapter(private val books: List<Books>) : RecyclerView.Adapter<BooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.books_item, parent,false)
        return BooksViewHolder(item)
    }


    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(books, position)
    }
}