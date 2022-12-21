package com.aprianto.core.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aprianto.core.R
import com.aprianto.core.databinding.ActivityDetailBinding
import com.aprianto.core.domain.model.Menu
import com.aprianto.core.utils.UIHelper
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            binding.tvProductPrice.text = UIHelper.castCustomRupiah(it.productPrice)
            binding.tvProductDescription.text = it.productDescription
            Glide.with(this)
                .load(it.productImage)
                .into(binding.ivProductImage)
            setStatusFavorite(it.isFavorite)
            binding.btnAddFavorite.setOnClickListener { _ ->
                viewModel.setFavoriteMenu(it, true)
                setStatusFavorite(true)
                UIHelper.showDialog(
                    this,
                    getString(R.string.title_saved),
                    String.format(getString(R.string.info_menu_saved), it.productName)
                )
            }
            binding.btnRemoveFavorite.setOnClickListener { _ ->
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).apply {
                    title = getString(R.string.title_remove_favorite)
                    contentText = getString(R.string.confirm_remove_favorite)
                    setCancelable(false)
                    cancelText = getString(R.string.action_cancel)
                    setCancelClickListener { dismiss() }
                    confirmText = getString(R.string.action_remove)
                    setConfirmClickListener { _ ->
                        viewModel.setFavoriteMenu(it, false)
                        setStatusFavorite(false)
                        this.dismiss()
                        UIHelper.showDialog(
                            this@DetailActivity,
                            getString(R.string.title_removed),
                            String.format(getString(R.string.info_menu_removed), it.productName)
                        )
                    }
                    show()
                }
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            /* if saved favorite -> show button remove favorite */
            binding.btnAddFavorite.isVisible = false
            binding.btnRemoveFavorite.isVisible = true
        } else {
            binding.btnAddFavorite.isVisible = true
            binding.btnRemoveFavorite.isVisible = false
        }
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "PRODUCT_ID"
    }
}