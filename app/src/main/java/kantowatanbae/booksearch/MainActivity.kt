package kantowatanbae.booksearch

import android.os.Bundle
import android.view.Menu
import android.widget.ProgressBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kantowatanbae.booksearch.adapter.BooksAdapter
import kantowatanbae.booksearch.api.books.BooksResponseBody
import kantowatanbae.booksearch.api.books.Repository
import kantowatanbae.booksearch.model.Books
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var booksRecyclerView: RecyclerView
    private lateinit var booksAdapter: BooksAdapter

    private lateinit var progressBar: ProgressBar

    private var books: ArrayList<Books> = ArrayList()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        booksRecyclerView = findViewById(R.id.books_recycler_view)
        booksRecyclerView.setHasFixedSize(true)
        booksRecyclerView.layoutManager = LinearLayoutManager(this)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        booksRecyclerView.addItemDecoration(itemDecoration)

        booksAdapter = BooksAdapter(this, books)
        booksRecyclerView.adapter = booksAdapter

        progressBar = findViewById(R.id.progress_bar)

        val toolBar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                showProgress()
                Repository.sharedInstance(applicationContext).api
                    .volumes("intitle:$query")
                    .enqueue(object: Callback<BooksResponseBody> {
                        override fun onResponse(call: Call<BooksResponseBody>, response: Response<BooksResponseBody>) {
                            hideProgress()
                            books.clear()
                            response.body()?.items?.forEach {
                                books.add(Books(it.id, it.volumeInfo.title, it.volumeInfo.imageLinks?.thumbnail))
                            }
                            booksAdapter?.notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<BooksResponseBody>, t: Throwable) {
                            hideProgress()
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                        }
                    })
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }

    private fun showProgress() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = ProgressBar.INVISIBLE
    }

    /*
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_search -> {
            Toast.makeText(this, "Search!", Toast.LENGTH_LONG)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    */
}
