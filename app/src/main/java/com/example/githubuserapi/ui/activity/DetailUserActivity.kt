package com.example.githubuserapi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.data.model.UserDetail
import com.example.githubuserapi.databinding.ActivityDetailUserBinding
import com.example.githubuserapi.ui.adapter.SectionPagerAdapter
import com.example.githubuserapi.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private val detailViewModel by viewModels<DetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        supportActionBar?.title = "Detail User's"

        detailViewModel.userGitDetail.observe(this) { detailUser ->
            setUserDetail(detailUser)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        showSectionPager()
        getDetail()
    }

    private fun getDetail(){
        val dataUser = intent.getStringExtra(DETAIL_USER)
        if (dataUser != null) {
            sectionPagerAdapter.username = dataUser
            detailViewModel.getUserDetail(dataUser)
        }
    }

    private fun setUserDetail(detailUser: UserDetail) {
        detailUserBinding.apply {
            Log.d(TAG, detailUser.toString())
            tvItemUsername.text = StringBuilder("@").append(detailUser.username)
            tvItemName.text = detailUser.name
            tvFollower.text = detailUser.followers.toString()
            tvFollowing.text = detailUser.following.toString()
            tvCompany.text = detailUser.company
            tvLocation.text = detailUser.location
            tvRepository.text = detailUser.publicRepos.toString()

            Glide.with(this@DetailUserActivity)
                .load(detailUser.avatarUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(imgPhotoUser)
        }
    }

    private fun showSectionPager(){
        detailUserBinding.apply {
            sectionPagerAdapter = SectionPagerAdapter(this@DetailUserActivity)
            val viewPager: ViewPager2 = viewPager
            viewPager.adapter = sectionPagerAdapter

            val tabs: TabLayout = tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            supportActionBar?.elevation = 0f
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            detailUserBinding.progressBarDetail.visibility = View.VISIBLE
        } else {
            detailUserBinding.progressBarDetail.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "DetailUserActivity"
        const val DETAIL_USER = "Detail_User"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.Followers,
            R.string.Following
        )
    }
}