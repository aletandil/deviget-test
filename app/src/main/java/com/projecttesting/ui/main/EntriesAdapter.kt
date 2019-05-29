package com.projecttesting.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.projecttesting.R
import com.projecttesting.data.models.TopEntriesDataChildrenResponse
import com.projecttesting.data.repositories.EntryRepository
import com.projecttesting.utils.loadImage
import kotlinx.android.synthetic.main.list_item_entry.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class EntriesAdapter(

    private var items: Collection<TopEntriesDataChildrenResponse>,
    private val clickListener: (TopEntriesDataChildrenResponse, MainFragment.ActionType) -> Unit,
    private val entryRepository: EntryRepository
) :
    RecyclerView.Adapter<EntriesAdapter.ViewHolderEntry>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolderEntry(LayoutInflater.from(parent.context).inflate(R.layout.list_item_entry, parent, false))

    override fun onBindViewHolder(holder: ViewHolderEntry, position: Int) {
        val entry = items.elementAt(position)
        holder.bind(items.elementAt(position), isFirstPosition(position), isLastPosition(position), clickListener, entryRepository)
    }

    private fun isFirstPosition(position: Int) = position == 0

    private fun isLastPosition(position: Int) = position == itemCount - 1

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    fun updateEntries(entries: Collection<TopEntriesDataChildrenResponse>?) {
        entries?.let {
            items = entries
            notifyDataSetChanged()
        }
    }

    class ViewHolderEntry(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            item: TopEntriesDataChildrenResponse,
            firstRow: Boolean,
            lastRow: Boolean,
            onClickEntry: (TopEntriesDataChildrenResponse, MainFragment.ActionType) -> Unit,
            entryRepository: EntryRepository) = with(itemView) {
            txt_author.text = item.data?.author
            txt_title.text = item.data?.title
            item.data?.thumbnail?.let { entryImgUrl ->
                img_thumb.loadImage(entryImgUrl)
            }
            txt_comments.text = itemView.context.getString(R.string.entry_comments, item.data?.comments.toString())

            item.data.let {
                if (it!!.readed) {
                    img_status.visibility = View.VISIBLE
                } else {
                    img_status.visibility = View.INVISIBLE
                }
            }

            entry_root_layout.onClick {
                onClickEntry(item, MainFragment.ActionType.OPEN_ENTRY)
            }
        }

    }
}