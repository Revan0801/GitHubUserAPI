package com.example.githubuserapi.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.R
import com.example.githubuserapi.data.model.SearchItem
import com.example.githubuserapi.databinding.ActivityMainBinding
import com.example.githubuserapi.ui.adapter.SearchAdapter
import com.example.githubuserapi.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val searchViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.title = "Github User's Search"

        activityMainBinding.rvGitUser.setHasFixedSize(true)

        searchViewModel.userGitSearch.observe(this) { user ->
            setUserData(user)
        }

        searchViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        searchViewModel.isDataFound.observe(this) {
            onDataFound(it)
        }

        showRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menuSearch).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Gunakan method ini ketika search selesai atau OK
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }
            // Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    private fun setUserData(items: List<SearchItem>) {
        val listUser = ArrayList<SearchItem>()
        for (data in items) {
            val user = SearchItem(data.username, data.id, data.avatarUrl)
            listUser.addAll(listOf(user))
        }
        val searchAdapter = SearchAdapter(listUser)
        activityMainBinding.rvGitUser.adapter = searchAdapter

        searchAdapter.setOnItemCallback(object : SearchAdapter.OnItemClickCallback {

            override fun onItemClicked(data: SearchItem){
                val intentMoveToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                intentMoveToDetail.putExtra(DetailUserActivity.DETAIL_USER, data.username)
                startActivity(intentMoveToDetail)
            }
        })
    }

    private fun showRecyclerView() {
        showLoading(false)
        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvGitUser.layoutManager = GridLayoutManager(applicationContext, 2)
            } else {
                rvGitUser.layoutManager = layoutManager
            }
            val itemDecoration = DividerItemDecoration(applicationContext, layoutManager.orientation)
            rvGitUser.addItemDecoration(itemDecoration)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun onDataFound(found: Boolean) {
        activityMainBinding.apply {
            if (found){
                rvGitUser.visibility = View.VISIBLE

            } else {
                rvGitUser.visibility = View.INVISIBLE

                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.messageErrorSearch),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}