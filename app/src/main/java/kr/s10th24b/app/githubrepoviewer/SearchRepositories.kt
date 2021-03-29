package kr.s10th24b.app.githubrepoviewer

import android.content.ClipData
import android.os.Parcelable
import com.google.android.material.internal.ParcelableSparseArray
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class SearchRepositories(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepositoryItem>
) : Parcelable