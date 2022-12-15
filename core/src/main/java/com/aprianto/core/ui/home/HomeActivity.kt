package com.aprianto.core.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aprianto.core.data.Resource
import com.aprianto.core.databinding.ActivityMainBinding
import com.aprianto.core.ui.adapter.MenuRvAdapter
import com.aprianto.core.ui.detail.DetailActivity
import com.aprianto.core.utils.UIHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
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
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_PRODUCT_ID, selectedMenu)
            startActivity(intent)
        }

        viewModel.menu.observe(this@HomeActivity) { menu ->
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
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            this.adapter = adapter
        }

        binding.btnFavorite.setOnClickListener {
            try {
                startActivity(Intent(this, Class.forName("com.aprianto.favorite.ui.FavoriteActivity")))
            } catch (e: Exception) {
                UIHelper.showDialog(
                    context = this,
                    title = "Error",
                    message = e.message,
                    style = SweetAlertDialog.ERROR_TYPE
                )
            }
        }

    }
}