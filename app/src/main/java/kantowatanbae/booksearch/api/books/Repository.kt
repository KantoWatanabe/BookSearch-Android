package kantowatanbae.booksearch.api.books

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class Repository private constructor(context: Context) {

    companion object {
        private const val BASE_URL : String = "https://www.googleapis.com/"
        private const val TIMEOUT : Long = 60

        private var mInstance : Repository? = null

        fun sharedInstance(context: Context) : Repository {
            if (mInstance == null) {
                mInstance = Repository(context)
            }
            return mInstance!!
        }
    }

    val api : Service

    init {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .build()
                return@Interceptor chain.proceed(request)
            })
            .build()

        api = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(Service::class.java)
    }
}