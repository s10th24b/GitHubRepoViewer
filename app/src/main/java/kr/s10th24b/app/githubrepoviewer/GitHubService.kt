package kr.s10th24b.app.githubrepoviewer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("/search/repositories")
    fun repos(@Query("q") repo: String): Call<SearchRepositories>

    @GET("users/{author}/repos")
    fun authors(@Path("author") author: String): Call<List<RepositoryItem>>
}