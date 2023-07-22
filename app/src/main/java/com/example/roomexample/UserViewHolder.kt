package com.example.roomexample

import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample.databinding.RowUserBinding

class UserViewHolder(private val bind: RowUserBinding, private val listener: OnUserListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(user: UserModel) {
        bind.textUsername.text = user.username

        bind.textUsername.setOnClickListener {
            listener.Onclick(user.id)
        }
    }
}