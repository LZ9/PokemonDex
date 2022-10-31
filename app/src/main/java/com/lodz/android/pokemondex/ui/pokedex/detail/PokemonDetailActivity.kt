package com.lodz.android.pokemondex.ui.pokedex.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle

import android.view.View
import androidx.annotation.ColorInt
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.corekt.anko.*
import com.lodz.android.corekt.utils.StatusBarUtil
import com.lodz.android.imageloaderkt.ImageLoader
import com.lodz.android.pandora.mvvm.base.activity.AbsVmActivity
import com.lodz.android.pandora.mvvm.vm.BaseViewModel
import com.lodz.android.pandora.utils.viewmodel.bindViewModel
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.databinding.ActivityPokemonDetailBinding

/**
 * 宝可梦详情
 * @author zhouL
 * @date 2022/10/28
 */
class PokemonDetailActivity : AbsVmActivity() {

    companion object {
        private const val EXTRA_POKE_BEAN = "extra_poke_bean"
        private const val EXTRA_BACK_COLOR = "extra_back_color"

        fun start(context: Context, bean: PkmInfoBean, @ColorInt backColor: Int) {
            val intent = Intent(context, PokemonDetailActivity::class.java).intentOf(
                EXTRA_POKE_BEAN to bean,
                EXTRA_BACK_COLOR to backColor
            )
            context.startActivity(intent)
        }
    }

    private val mViewModel by bindViewModel { PokemonDetailViewModel() }

    override fun getViewModel(): BaseViewModel = mViewModel

    private val mBinding: ActivityPokemonDetailBinding by bindingLayout(ActivityPokemonDetailBinding::inflate)

    override fun getAbsViewBindingLayout(): View = mBinding.root

    private val mPokeBean by intentSerializableExtras<PkmInfoBean>(EXTRA_POKE_BEAN)
    private val mBackColor by intentExtras<Int>(EXTRA_BACK_COLOR)

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        initPokeColor()
    }

    private fun initPokeColor() {
        val color = mBackColor ?: Color.WHITE
        mBinding.topLayout.setBackgroundColor(color)
        StatusBarUtil.setColor(window, color)
        ImageLoader.create(getContext()).loadUrl(mPokeBean?.imgUrl ?: "").into(mBinding.pokeImg)
    }

    override fun setListeners() {
        super.setListeners()
        mBinding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun setViewModelObserves() {
        super.setViewModelObserves()

    }

    override fun initData() {
        super.initData()
        mBinding.nameTv.text = mPokeBean?.name
        mBinding.indexTv.text = mPokeBean?.index
    }
}