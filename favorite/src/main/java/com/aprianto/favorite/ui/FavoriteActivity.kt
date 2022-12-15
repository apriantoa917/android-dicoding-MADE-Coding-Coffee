package com.aprianto.favorite.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.aprianto.core.ui.adapter.MenuRvAdapter
import com.aprianto.core.ui.detail.DetailActivity
import com.aprianto.favorite.databinding.ActivityFavoriteBinding
import com.aprianto.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val adapter = MenuRvAdapter()
        adapter.onItemClick = { selectedMenu ->
            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_PRODUCT_ID, selectedMenu)
            startActivity(intent)
        }

        viewModel.menu.observe(this@FavoriteActivity) { menu ->
            if (menu.isEmpty()) {
                binding.tvEmptyFavorite.isVisible = true
                binding.rvMenu.isVisible = false
            } else {
                binding.tvEmptyFavorite.isVisible = false
                binding.rvMenu.isVisible = true
                adapter.setData(menu)
            }
        }

        with(binding.rvMenu) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }
}