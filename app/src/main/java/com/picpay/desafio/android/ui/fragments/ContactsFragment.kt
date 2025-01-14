package com.picpay.desafio.android.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.util.Resource
import com.picpay.desafio.android.presentation.viewmodel.ContactViewModel
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import com.picpay.desafio.android.ui.view.ContactsActivity
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    lateinit var viewModel: ContactViewModel
    lateinit var userAdapter: UserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ContactsActivity).viewModel
        setUpRecyclerView()

        viewModel.users.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { usersResponse ->
                        userAdapter.setData(usersResponse)
                        viewModel.saveContacts(usersResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()

                    response.message?.let {message ->
                        AlertDialog.Builder(context)
                            .setCancelable(false)
                            .setMessage(message)
                            .setPositiveButton(context?.getString(R.string.ok_button), null)
                            .create()
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.getSavedContacts().observe(viewLifecycleOwner, Observer { savedContacts ->
            if (savedContacts == null || savedContacts.isEmpty()) viewModel.getUsers() else userAdapter.setData(
                savedContacts
            )
        })

        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility = View.INVISIBLE
            viewModel.getUsers()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun hideProgressBar() {
        user_list_progress_bar.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        user_list_progress_bar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        userAdapter = UserListAdapter()
        recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}