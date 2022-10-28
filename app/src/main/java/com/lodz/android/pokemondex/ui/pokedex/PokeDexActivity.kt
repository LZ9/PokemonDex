package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lodz.android.pandora.mvvm.base.activity.BaseRefreshVmActivity
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.pandora.utils.viewmodel.bindViewModel
import com.lodz.android.pandora.widget.rv.anko.grid
import com.lodz.android.pandora.widget.rv.anko.setup
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.databinding.ActivityPokedexListBinding

/**
 * 图鉴页面
 * @author zhouL
 * @date 2022/3/11
 */
class PokeDexActivity : BaseRefreshVmActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PokeDexActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mViewModel by bindViewModel { PokeDexViewModel() }

    override fun getViewModel(): PokeDexViewModel = mViewModel

    private val mBinding: ActivityPokedexListBinding by bindingLayout(ActivityPokedexListBinding::inflate)

    override fun getViewBindingLayout(): View = mBinding.root

    /** 适配器 */
    private lateinit var mAdapter: PokemonListAdapter

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(R.string.main_pokedex)
        getTitleBarLayout().elevation = 0f
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = mBinding.recyclerView.grid(2)
            .setup(PokemonListAdapter(getContext()))
    }

    override fun onClickBackBtn() {
        super.onClickBackBtn()
        finish()
    }

    override fun onClickReload() {
        super.onClickReload()
        showStatusLoading()
        mViewModel.requestDataList(getContext())
    }

    override fun onDataRefresh() {
        mViewModel.requestDataList(getContext())
    }

    override fun setListeners() {
        super.setListeners()
    }

    override fun setViewModelObserves() {
        super.setViewModelObserves()
        mViewModel.mDataList.observe(getLifecycleOwner()) {
            mAdapter.setTreeData(it)
        }
    }

    override fun initData() {
        super.initData()
        showStatusLoading()
        mViewModel.requestDataList(getContext())
    }
}