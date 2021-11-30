package com.picpay.desafio.android.ui.adapter

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.ui.fragments.ContactsFragmentDirections
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListItemViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private var user: User? = null

    init {
        itemView.setOnClickListener {
            user?.let { user ->
                val directions = ContactsFragmentDirections.actionContactsFragmentToContactInfoFragment(user)
                itemView.findNavController().navigate(directions)
            }
        }
    }

    fun bind(user: User) {
        this.user = user

        itemView.name.text = user.name ?: itemView.context.getString(R.string.name_not_found)
        itemView.username.text =
            user.username ?: itemView.context.getString(R.string.user_not_found)
        Glide.with(itemView)
            .load(user.img)
            .error(R.drawable.ic_round_account_circle)
            .into(itemView.picture)
    }
}