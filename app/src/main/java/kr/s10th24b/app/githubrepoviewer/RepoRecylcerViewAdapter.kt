package kr.s10th24b.app.githubrepoviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.item_recycler.view.*

class RepoRecylcerViewAdapter() : RecyclerView.Adapter<RepoRecyclerViewHolder>() {
    var repoItems = mutableListOf<RepoItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoRecyclerViewHolder {
        // LayoutInflater 는 화면요소이므로 컨텍스트가 필요하고
        // inflate 에서 아이템 레이아웃을 지정해서 호출하면 클래스로 변환된다.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent,false)
        return RepoRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoRecyclerViewHolder, position: Int) {
        val repoItem = repoItems[position]
        holder.setItem(repoItem)
    }

    override fun getItemCount() = repoItems.size
}
// 모든 레이아웃은 코드로 변환되면 View 클래스가 된다.
class RepoRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setItem(item: RepoItem) {
//        itemView.repoItemImageView = ""
        itemView.repoItemRepoNameTextView.text = item.title
        itemView.repoItemAuthorTextView.text = item.author
    }


}