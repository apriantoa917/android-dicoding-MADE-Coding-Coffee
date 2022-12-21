package com.aprianto.core.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aprianto.core.R
import com.aprianto.core.data.Resource
import com.aprianto.core.databinding.ActivityMainBinding
import com.aprianto.core.domain.model.Menu
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
            title = getString(R.string.title_loading)
            contentText = getString(R.string.info_loading_text)
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

                        binding.btnSort.setOnClickListener {
                            sortMenu(menu.data, adapter)
                        }
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
                startActivity(
                    Intent(
                        this,
                        Class.forName("com.aprianto.favorite.ui.FavoriteActivity")
                    )
                )
            } catch (e: Exception) {
                UIHelper.showDialog(
                    context = this,
                    title = "Error",
                    message = e.message,
                    style = SweetAlertDialog.ERROR_TYPE
                )
                Log.e(
                    "INTENT_MODULE_FAVORITE",
                    "Error while open module FAVORITE : ${e.message}",
                    e
                )
            }
        }
    }

    private fun sortMenu(menu: List<Menu>?, adapter: MenuRvAdapter) {
        val alertdialogbuilder = AlertDialog.Builder(this)
        alertdialogbuilder.setTitle(getString(R.string.title_sort))
        val items = arrayOf(
            getString(R.string.sort_menu_by_name),
            getString(R.string.sort_menu_by_price_ASC),
            getString(R.string.sort_menu_by_price_DESC),
        )
        alertdialogbuilder.setItems(
            items
        ) { _, index ->
            when (index) {
                0 -> adapter.setData(menu?.sortedBy { it.productName })
                1 -> adapter.setData(menu?.sortedBy { it.productPrice })
                2 -> adapter.setData(menu?.sortedByDescending { it.productPrice })
            }
        }
        alertdialogbuilder.create().show()
    }

}