package kr.s10th24b.app.githubrepoviewer

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kr.s10th24b.app.githubrepoviewer.databinding.ItemRecyclerBinding
import java.io.Serializable

class RepoRecylcerViewAdapter(_context: Context) : RecyclerView.Adapter<RepoRecyclerViewHolder>() {
    lateinit var binding: ItemRecyclerBinding
    var repoItems = mutableListOf<RepositoryItem>()
    var context = _context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoRecyclerViewHolder {
        // LayoutInflater 는 화면요소이므로 컨텍스트가 필요하고
        // inflate 에서 아이템 레이아웃을 지정해서 호출하면 클래스로 변환된다.
        binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent,false)
        return RepoRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoRecyclerViewHolder, position: Int) {
        val repoItem = repoItems[position]
        holder.setItem(repoItem, context)
    }

    override fun getItemCount() = repoItems.size
}

// 모든 레이아웃은 코드로 변환되면 View 클래스가 된다.
class RepoRecyclerViewHolder(_binding: ItemRecyclerBinding) :
    RecyclerView.ViewHolder(_binding.root) {
    var binding = _binding
    fun setItem(item: RepositoryItem, context: Context) {

//        val requestOption = RequestOptions()
//        requestOption.placeholder(R.drawable.github_icon)
//        requestOption.error(R.drawable.ic_launcher_background)

        Glide.with(context)
            .load(item.owner.avatar_url)
//            .apply(requestOption)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.repoItemProgressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.repoItemProgressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.repoItemImageView)
        binding.repoItemRepoNameTextView.text = item.name
        binding.repoItemAuthorTextView.text = item.owner.login
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, ViewPagerActivity::class.java)
            intent.putExtra("repoItem", item as Serializable)
            itemView.context.startActivity(intent)
        }
    }
}