package com.picpay.desafio.android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.models.User

class UserListAdapter : RecyclerView.Adapter<UserListItemViewHolder>() {

    private var oldUsersList = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        return UserListItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(oldUsersList[position])
    }

    override fun getItemCount(): Int = oldUsersList.size

    fun setData(newUsersList: List<User>) {
        val diffUtil = UserListDiffCallback(oldUsersList, newUsersList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldUsersList = newUsersList
        diffResults.dispatchUpdatesTo(this)
    }
}