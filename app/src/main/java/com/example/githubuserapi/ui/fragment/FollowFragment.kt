package com.example.githubuserapi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.data.model.FollowItem
import com.example.githubuserapi.databinding.FragmentFollowBinding
import com.example.githubuserapi.ui.adapter.FollowAdapter
import com.example.githubuserapi.ui.viewmodel.FollowViewModel


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private val followViewModel by viewModels<FollowViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel.userFollowers.observe(viewLifecycleOwner) {
            setFollow(it)
        }

        followViewModel.userFollowing.observe(viewLifecycleOwner) {
            setFollow(it)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        setTab()
    }

    private fun setTab() {
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        username = arguments?.getString(FOLLOW)!!

        when (index) {
            1 -> followViewModel.getFollowers(username)
            2 -> followViewModel.getFollowing(username)
        }
    }

    private fun setFollow(items: ArrayList<FollowItem>) {
        val listUser = ArrayList<FollowItem>()
        for (data in items) {
            val follow = FollowItem(data.avatarUrl, data.username, data.id)
            listUser.addAll(listOf(follow))
        }
        val followersAdapter = FollowAdapter(listUser)
        binding.apply {
            rvFollowUser.layoutManager = LinearLayoutManager(context)
            rvFollowUser.adapter = followersAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val FOLLOW = "follow"
    }
}