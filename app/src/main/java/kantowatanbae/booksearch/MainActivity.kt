package kantowatanbae.booksearch

import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import kantowatanbae.booksearch.api.books.BooksResponseBody
import kantowatanbae.booksearch.api.books.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                textMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val toolBar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Repository.sharedInstance(applicationContext).api
                    .volumes("intitle:$query")
                    .enqueue(object: Callback<BooksResponseBody> {
                        override fun onResponse(call: Call<BooksResponseBody>, response: Response<BooksResponseBody>) {
                            textMessage.text = response.body().toString()
                        }

                        override fun onFailure(call: Call<BooksResponseBody>, t: Throwable) {
                            textMessage.text = t.message
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
