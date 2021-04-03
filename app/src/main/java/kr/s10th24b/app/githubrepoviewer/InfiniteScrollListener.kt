package kr.s10th24b.app.githubrepoviewer

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import splitties.toast.toast

class InfiniteScrollListener(
    val func: () -> Unit,
    val layoutManager: LinearLayoutManager,
    val searchRepoProgressBar: ProgressBar
) : RecyclerView.OnScrollListener() {
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var previousTotal = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
            Log.d("KHJ","FirstCompletelyVisibleItemPosition: $firstVisibleItem")

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)
            ) {
                // at the end.
                toast("Scroll end Reached!")
                func()
                searchRepoProgressBar.visibility = View.VISIBLE
                loading = true
            }
        }
    }
}