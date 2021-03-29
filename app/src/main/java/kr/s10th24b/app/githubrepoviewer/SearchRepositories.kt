package kr.s10th24b.app.githubrepoviewer

import android.content.ClipData
import java.io.Serializable

data class SearchRepositories(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepositoryItem>
) : Serializable