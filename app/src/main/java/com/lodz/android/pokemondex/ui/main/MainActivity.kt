package com.lodz.android.pokemondex.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.appbar.AppBarLayout
import com.lodz.android.corekt.anko.dp2px
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.corekt.utils.SnackbarUtils
import com.lodz.android.pandora.base.activity.AbsActivity
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.pandora.widget.contract.OnAppBarStateChangeListener
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.databinding.ActivityMainBinding
import com.lodz.android.pokemondex.ui.pokedex.PokeDexActivity

class MainActivity : AbsActivity() {

    private val mBinding: ActivityMainBinding by bindingLayout(ActivityMainBinding::inflate)

    override fun getAbsViewBindingLayout(): View = mBinding.root

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        mBinding.titleBarLayout.addExpandView(createSettingView())
    }

    private fun createSettingView(): View {
        val settingBtn = ImageView(getContext())
        settingBtn.setImageResource(com.lodz.android.pandora.R.drawable.pandora_ic_menu)
        settingBtn.setPadding(dp2px(15), 0, dp2px(15), 0)
        settingBtn.setOnClickListener {
            showDevelopSnackbar()
        }
        return settingBtn
    }

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

        // 图鉴
        mBinding.pokedexBtn.setOnClickListener {
            PokeDexActivity.start(getContext())
        }

        // 招式
        mBinding.skillBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        // 技巧机
        mBinding.machineBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        // 特性
        mBinding.abilitiesBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        // 道具
        mBinding.itemBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        // 性格
        mBinding.characterBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        // 地点
        mBinding.placeBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        // 属性
        mBinding.typesBtn.setOnClickListener {
            showDevelopSnackbar()
        }

        // 我的队伍
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