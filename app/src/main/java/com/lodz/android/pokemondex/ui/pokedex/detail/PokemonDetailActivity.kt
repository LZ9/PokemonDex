package com.lodz.android.pokemondex.ui.pokedex.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lodz.android.pandora.mvvm.base.activity.BaseVmActivity

import android.view.View
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.corekt.anko.*
import com.lodz.android.pandora.mvvm.vm.BaseViewModel
import com.lodz.android.pandora.utils.viewmodel.bindViewModel
import com.lodz.android.pokemondex.databinding.ActivityPokemonDetailBinding

/**
 * 宝可梦详情
 * @author zhouL
 * @date 2022/10/28
 */
class PokemonDetailActivity : BaseVmActivity() {

    companion object {
        private const val EXTRA_POKE_ID = "extra_poke_id"
        private const val EXTRA_POKE_NAME = "extra_poke_name"

        fun start(context: Context, pokeId: Int, pokeName: String) {
            val intent = Intent(context, PokemonDetailActivity::class.java).intentOf(
                EXTRA_POKE_ID to pokeId,
                EXTRA_POKE_NAME to pokeName
            )
            context.startActivity(intent)
        }
    }

    private val mViewModel by bindViewModel { PokemonDetailViewModel() }

    override fun getViewModel(): BaseViewModel = mViewModel

    private val mBinding: ActivityPokemonDetailBinding by bindingLayout(ActivityPokemonDetailBinding::inflate)

    override fun getViewBindingLayout(): View = mBinding.root

    private val mPokeId by intentExtras<String>(EXTRA_POKE_ID)
    private val mPokeName by intentExtras<String>(EXTRA_POKE_NAME)

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(mPokeName ?: "未知")
    }

    override fun onClickBackBtn() {
        super.onClickBackBtn()
        finish()
    }

    override fun setViewModelObserves() {
        super.setViewModelObserves()

    }

    override fun initData() {
        super.initData()
        showStatusCompleted()
    }
}