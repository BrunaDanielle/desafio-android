package com.picpay.desafio.android.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.models.User
import kotlinx.android.synthetic.main.fragment_contact_info.view.*

class ContactInfoFragment : Fragment() {

    private val args: ContactInfoFragmentArgs by navArgs()
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_info, container, false)

        user = args.ContactInfos ?: User(" ", "", 0, "")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.tvName.text = user.name
        view.tvUserName.text = user.username
        Glide.with(view)
            .load(user.img)
            .error(R.drawable.ic_round_account_circle)
            .into(view.imageView)
    }
}