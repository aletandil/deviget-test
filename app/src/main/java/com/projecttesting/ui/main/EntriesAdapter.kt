package com.projecttesting.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projecttesting.data.models.Entry
import com.projecttesting.data.repositories.EntryRepository
import com.projecttesting.utils.loadImage
import kotlinx.android.synthetic.main.list_item_entry.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Period
import org.joda.time.format.PeriodFormatterBuilder
import java.util.*


class EntriesAdapter(

    private var items: Collection<Entry>,
    private val clickListener: (Entry, MainFragment.ActionType) -> Unit,
    private val entryRepository: EntryRepository
) :
    RecyclerView.Adapter<EntriesAdapter.ViewHolderEntry>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolderEntry(LayoutInflater.from(parent.context).inflate(com.projecttesting.R.layout.list_item_entry, parent, false))

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

    fun updateEntries(entries: List<Entry>?) {
        entries?.let {
            items = entries
            notifyDataSetChanged()
        }
    }

    class ViewHolderEntry(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            item: Entry,
            firstRow: Boolean,
            lastRow: Boolean,
            onClickEntry: (Entry, MainFragment.ActionType) -> Unit,
            entryRepository: EntryRepository) = with(itemView) {
            txt_author.text = item.author
            txt_title.text = item.title
            item.thumbnail?.let { entryImgUrl ->
                img_thumb.loadImage(entryImgUrl)
            }
            txt_comments.text = itemView.context.getString(com.projecttesting.R.string.entry_comments, item.comments.toString())
            txt_time.text =  getTimeAgo(item)

            item.let {
                if (!it.readed) {
                    img_status.visibility = View.VISIBLE
                } else {
                    img_status.visibility = View.INVISIBLE
                }
            }

            entry_root_layout.onClick {
                onClickEntry(item, MainFragment.ActionType.OPEN_ENTRY)
            }

            btn_dismiss_post.onClick {
                onClickEntry(item, MainFragment.ActionType.DISMISS_ENTRY)

            }
        }

        private fun getTimeAgo(item: Entry): String {
            val dateEntryEntity = Date(item.created!! * 1000)

            val timeZone = DateTimeZone.getDefault()
            val dateEntry = DateTime(dateEntryEntity.time)
            val now = DateTime(timeZone)
            val period = Period(dateEntry, now)
            val builder = PeriodFormatterBuilder()
            if (period.years !== 0) {
                builder.appendYears().appendSuffix(" years ago ")
            } else if (period.months !== 0) {
                builder.appendMonths().appendSuffix(" months ago ")
            } else if (period.days !== 0) {
                builder.appendDays().appendSuffix(" days ago ")
            } else if (period.hours !== 0) {
                builder.appendHours().appendSuffix(" hours ago ")
            } else if (period.minutes !== 0) {
                builder.appendMinutes().appendSuffix(" minutes ago ")
            } else if (period.seconds !== 0) {
                builder.appendSeconds().appendSuffix(" seconds ago ")
            }
            val formatter = builder.printZeroNever().toFormatter()

            val elapsed = formatter.print(period)
            return elapsed
        }
    }


}