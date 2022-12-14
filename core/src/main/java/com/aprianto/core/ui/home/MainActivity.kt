package com.aprianto.core.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aprianto.core.data.Resource
import com.aprianto.core.databinding.ActivityMainBinding
import com.aprianto.core.ui.ViewModelFactory
import com.aprianto.core.ui.adapter.MenuRvAdapter
import com.aprianto.core.ui.detail.DetailActivity
import com.aprianto.core.utils.UIHelper

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var loading: SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).apply {
            title = "Loading"
            contentText = "Sedang memuat data"
            setCancelable(false)
        }

        val adapter = MenuRvAdapter()
        adapter.onItemClick = { selectedMenu ->
//            UIHelper.showDialog(
//                context = this,
//                title = "TEST",
//                message = "HALOO",
//                style = SweetAlertDialog.SUCCESS_TYPE
//            )
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_PRODUCT_ID, selectedMenu)
            startActivity(intent)

        }
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.menu.observe(this@MainActivity) { menu ->
            if (menu != null) {
                when (menu) {
                    is Resource.Loading -> loading.show()
                    is Resource.Success -> {
                        loading.dismiss()
                        adapter.setData(menu.data)
                    }
                    is Resource.Error -> {
                        UIHelper.showDialog(
                            context = this,
                            title = "Error",
                            message = menu.message,
                            style = SweetAlertDialog.ERROR_TYPE
                        )
                        loading.dismiss()
                    }
                }
            }
        }

        with(binding.rvMenu) {
            layoutManager = GridLayoutManager(context,2)
            setHasFixedSize(true)
            this.adapter = adapter
        }

    }
}