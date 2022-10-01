package com.example.githubuserapi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.api.ApiConfig
import com.example.githubuserapi.data.model.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _userGitDetail = MutableLiveData<UserDetail>()
    val userGitDetail: LiveData<UserDetail> = _userGitDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetail(userDetail: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(userDetail)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userGitDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    companion object {
        private const val TAG = "DetailViewModel"
    }
}