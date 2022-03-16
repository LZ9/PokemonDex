package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lodz.android.pokemondex.bean.base.PageBean
import com.lodz.android.corekt.anko.toastShort
import com.lodz.android.pandora.mvvm.base.activity.BaseRefreshVmActivity
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.pandora.utils.viewmodel.bindViewModel
import com.lodz.android.pandora.widget.rv.recycler.RecyclerLoadMoreHelper
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.databinding.ActivityPokedexListBinding
import com.lodz.android.pokemondex.db.table.PokemonInfoTable

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
    /** 加载更多帮助类 */
    private lateinit var mLoadMoreHelper: RecyclerLoadMoreHelper<PokemonInfoTable>

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(R.string.main_pokedex)
        getTitleBarLayout().elevation = 0f
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = PokemonListAdapter(getContext())
        val layoutManager = LinearLayoutManager(getContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        mBinding.recyclerView.layoutManager = layoutManager
        mAdapter.onAttachedToRecyclerView(mBinding.recyclerView)// 如果使用网格布局请设置此方法
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.adapter = mAdapter
        mLoadMoreHelper = RecyclerLoadMoreHelper(mAdapter)
    }

    override fun onClickBackBtn() {
        super.onClickBackBtn()
        finish()
    }

    override fun onDataRefresh() {
        mViewModel.requestDataList(PageBean.DEFAULT_START_PAGE_NUM)
    }

    override fun setListeners() {
        super.setListeners()
        mLoadMoreHelper.setListener(object : RecyclerLoadMoreHelper.Listener {
            override fun onLoadMore(currentPage: Int, nextPage: Int, size: Int, position: Int) {
                mViewModel.requestDataList(nextPage)
            }

            override fun onClickLoadFail(reloadPage: Int, size: Int) {
                mViewModel.requestDataList(reloadPage)
            }
        })

        mAdapter.setOnItemClickListener { viewHolder, item, position ->
            toastShort(position.toString())
        }
    }

    override fun setViewModelObserves() {
        super.setViewModelObserves()
        mViewModel.mDataList.observe(getLifecycleOwner()) {
            val isFirst = it.first
            val pageBean = it.second
            val list = pageBean.data
            if (isFirst){
                setSwipeRefreshFinish()
                if (list == null){
                    showStatusNoData()
                    return@observe
                }
                mLoadMoreHelper.config(list, pageBean.total, pageBean.pageSize, true, 1)
                showStatusCompleted()
                return@observe
            }
            if (list == null){
                mLoadMoreHelper.loadComplete()
                return@observe
            }
            val datas = mAdapter.getData()?.toMutableList() ?: ArrayList()
            datas.addAll(list)
            mLoadMoreHelper.loadMoreSuccess(datas)
        }
    }

    override fun initData() {
        super.initData()
        showStatusLoading()
        mViewModel.requestDataList(PageBean.DEFAULT_START_PAGE_NUM)
    }




    //    "fighting" -> R.color.fighting
    //      "flying" -> R.color.flying
    //      "poison" -> R.color.poison
    //      "ground" -> R.color.ground
    //      "rock" -> R.color.rock
    //      "bug" -> R.color.bug
    //      "ghost" -> R.color.ghost
    //      "steel" -> R.color.steel
    //      "fire" -> R.color.fire
    //      "water" -> R.color.water
    //      "grass" -> R.color.grass
    //      "electric" -> R.color.electric
    //      "psychic" -> R.color.psychic
    //      "ice" -> R.color.ice
    //      "dragon" -> R.color.dragon
    //      "fairy" -> R.color.fairy
    //      "dark" -> R.color.dark
    //      normal -> R.color.gray_21
}