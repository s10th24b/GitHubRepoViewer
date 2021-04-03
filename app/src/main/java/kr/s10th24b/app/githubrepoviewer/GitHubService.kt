package kr.s10th24b.app.githubrepoviewer

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.EMPTY_RESPONSE
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
//    repo = 해당 searchText 가 포함된 모든 repo
//
//    author = 해당 searchText 가 포함된 모든 repo
//
//    근데 둘이 쿼리문이 조금 다르다..
//    path 가 다르다 해야하나.
//
//    api.github 에서 search 를 쓰면 text-match 로 문자열이 포함된 것도 찾아주는데, search 가 아닌 그냥 /repositories 이렇게 찾으면 딱 맞아 떨어지는 이름만 찾아준다.
//    결국 두가지 버전 모두 클래스를 만들어야 했다.

    @GET("search/repositories")
    fun getCallRepos(@Query("q") repo: String, @Query("page") page: Int): Call<SearchRepositories>

    @GET("search/repositories")
    fun getObRepos(
        @Query("q") repo: String,
        @Query("page") page: Int
    ): Observable<SearchRepositories>

    @GET("search/repositories?q=tetris+&per_page=200")
    fun repos(): Call<SearchRepositories>

    @GET("users/{author}/repos")
    fun getObAuthors(@Path("author") author: String): Observable<List<RepositoryItem>>

    @GET("users/{author}/repos")
    fun getCallAuthors(@Path("author") author: String): Call<List<RepositoryItem>>

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val USER_ID = ""
        private const val USER_PW = ""

        fun create(observer: PublishSubject<Boolean>): GitHubService {
            val progressListener = object : ProgressListener {
                var firstUpdate = true
                override fun update(
                    bytesRead: Long,
                    contentLength: Long,
                    done: Boolean,
                    observer: PublishSubject<Boolean>
                ) {
                    if (done) {
                        observer.onNext(done)
                        Log.d("KHJ", "Progress Done.")
                    } else {
                        if (firstUpdate) {
                            firstUpdate = false
                            if (contentLength == -1L) {
                                Log.d("KHJ", "content-length: unknown")
                            } else Log.d("KHJ", "content-length: $contentLength")
                        }
                    }
                    // 왜 자꾸 Content-Length 가 -1, 즉 unknown인지 검색해봤는데,
                    // You won’t be able to get the progress if the header Content-Length is not set in the response.
                    // 깃허브 API에서 제공하는 response에서 헤더에 Content-Length 항목이 설정되어 있지 않아서 그런 것이었다..
                    // 아예 없다... 로딩바가 아니라 그냥 프로그레스 루프로 만족해야할듯.
                    Log.d("KHJ", "contentLength: $contentLength")
                    Log.d("KHJ", "bytesRead: $bytesRead")

                    if (bytesRead != -1L) {
                        Log.d("KHJ", "${100 * bytesRead / contentLength}% done\n")
                    }
                }
            }

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val networkInterceptor = Interceptor { chain ->
                //                    val newRequest = chain.request().newBuilder().build()
                //                    val response = chain.proceed(newRequest)
                val originalResponse = chain.proceed(chain.request())
                originalResponse.newBuilder()
                    .body(
                        ProgressResponseBody(
                            originalResponse.body ?: EMPTY_RESPONSE,
                            progressListener,
                            observer
                        )
                    )
                    .build()
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(networkInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()
            return retrofit.create(GitHubService::class.java)
        }
    }
}