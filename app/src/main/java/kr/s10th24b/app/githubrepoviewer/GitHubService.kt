package kr.s10th24b.app.githubrepoviewer

import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
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
    fun getObRepos(@Query("q") repo: String, @Query("page") page: Int): Observable<SearchRepositories>

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

        fun create(): GitHubService {
            val httpLoggingIntercepter = HttpLoggingInterceptor()
            httpLoggingIntercepter.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingIntercepter)
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