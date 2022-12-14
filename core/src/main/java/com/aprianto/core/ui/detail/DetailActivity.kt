package com.aprianto.core.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aprianto.core.R
import com.aprianto.core.databinding.ActivityDetailBinding
import com.aprianto.core.domain.model.Menu
import com.aprianto.core.ui.ViewModelFactory
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCT_ID = "PRODUCT_ID"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val detailMenu = intent.getParcelableExtra<Menu>(EXTRA_PRODUCT_ID)
        showDetailMenu(detailMenu)
    }

    private fun showDetailMenu(detailMenu: Menu?) {
        detailMenu?.let {
            binding.btnBack.setOnClickListener {
                finish()
            }
            binding.tvPreviewProductName.text = it.productName
            binding.tvProductName.text = it.productName
            binding.labelProductCategory.tvProductCategory.text = it.productCategory
            binding.tvProductPrice.text = it.productPrice.toString()
            binding.tvProductDescription.text = it.productDescription
            Glide.with(this)
                .load(it.productImage)
                .into(binding.ivProductImage)
            setStatusFavorite(it.isFavorite)
            binding.btnAddFavorite.setOnClickListener { _ ->
                viewModel.setFavoriteMenu(it, true)
            }
            binding.btnRemoveFavorite.setOnClickListener { _ ->
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).apply {
                    title = "Hapus Favorite"
                    contentText = "Apakah anda akan menghapus menu ini dari Favorite?"
                    setCancelable(false)
                    cancelText = "Batalkan"
                    setCancelClickListener { dismiss() }
                    confirmText = "Hapus"
                    setConfirmClickListener { _ ->
                        viewModel.setFavoriteMenu(
                            it,
                            false
                        )
                        this.dismiss()
                    }
                    show()
                }

            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            /* jika sudah diset favorit -> hanya tampilkan tombol remove favorite */
            binding.btnAddFavorite.isVisible = false
            binding.btnRemoveFavorite.isVisible = true
        } else {
            binding.btnAddFavorite.isVisible = true
            binding.btnRemoveFavorite.isVisible = false
        }
    }
}