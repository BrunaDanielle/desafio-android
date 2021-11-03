package com.picpay.desafio.android.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.util.Resource
import com.picpay.desafio.android.presentation.viewmodel.ContactViewModel
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    private val viewModel: ContactViewModel by viewModel()
    private val userAdapter: UserListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        viewModel.users.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    showProgressBar(false)
                    showRecyclerView(true)
                    response.data?.let { usersResponse ->
                        userAdapter.setData(usersResponse)
                    }
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    showRecyclerView(true)

                    AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setMessage(getString(R.string.data_failure))
                        .setPositiveButton(context?.getString(R.string.ok_button), null)
                        .create()
                        .show()

                }
                is Resource.Loading -> {
                    showProgressBar(true)
                }
            }
        })


        swipeRefreshLayout.setOnRefreshListener {
            showRecyclerView(false)
            viewModel.reloadContacts()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showRecyclerView(showRecyclerView: Boolean) {
        recyclerView.isVisible = showRecyclerView
    }

    private fun showProgressBar(showProgressBar: Boolean) {
        user_list_progress_bar.isVisible = showProgressBar
    }

    private fun setUpRecyclerView() {
        recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}