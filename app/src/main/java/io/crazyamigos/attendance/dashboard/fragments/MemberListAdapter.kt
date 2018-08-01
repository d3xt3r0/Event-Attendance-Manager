package io.crazyamigos.attendance.dashboard.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.crazyamigos.attendance.R
import kotlinx.android.synthetic.main.member_row.view.*

class MemberListAdapter(val names: ArrayList<String>) : RecyclerView.Adapter<MemberListAdapter.ViewHolderMember>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMember {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.member_row, parent, false)
        return ViewHolderMember(layout)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: ViewHolderMember, position: Int) {
        holder.bind(names.get(position))
    }

    class ViewHolderMember(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(name: String) {
            view.participant_nameView.text = name
        }

    }
}