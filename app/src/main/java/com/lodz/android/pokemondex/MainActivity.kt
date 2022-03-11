package com.lodz.android.pokemondex

import android.graphics.Color
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.corekt.utils.SnackbarUtils
import com.lodz.android.pandora.base.activity.AbsActivity
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.pandora.widget.contract.OnAppBarStateChangeListener
import com.lodz.android.pokemondex.databinding.ActivityMainBinding

class MainActivity : AbsActivity() {

    private val mBinding: ActivityMainBinding by bindingLayout(ActivityMainBinding::inflate)

    override fun getAbsViewBindingLayout(): View = mBinding.root

    override fun setListeners() {
        super.setListeners()
        mBinding.appBarLayout.addOnOffsetChangedListener(object : OnAppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: Int, delta: Double) {
                when (state) {
                    EXPANDED -> {// 完全展开
                        mBinding.titleBarLayout.visibility = View.VISIBLE
                        mBinding.titleBarLayout.visibility = View.GONE
                    }
                    COLLAPSED -> {// 完全折叠
                        mBinding.titleBarLayout.alpha = 1f
                    }
                    else -> { // 滑动中
                        mBinding.titleBarLayout.alpha = (1 - delta).toFloat()
                        mBinding.titleBarLayout.visibility = View.VISIBLE
                    }
                }

            }

        })

        mBinding.pokedexBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        mBinding.skillBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        mBinding.machineBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        mBinding.abilitiesBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        mBinding.itemBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        mBinding.characterBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        mBinding.placeBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        mBinding.typesBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        mBinding.teamBtn.setOnClickListener {
            showDevelopSnackbar()
        }
    }

    private fun showDevelopSnackbar(){
        SnackbarUtils.createShort(mBinding.root, "开发中")
            .setBackgroundColor(getColorCompat(R.color.color_d04741))
            .show()
    }

}