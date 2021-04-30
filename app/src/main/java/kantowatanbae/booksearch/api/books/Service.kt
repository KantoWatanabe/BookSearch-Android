package kantowatanbae.booksearch.api.books


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("books/v1/volumes")
    fun volumes(@Query("q") q: String) : Call<BooksResponseBody>
}