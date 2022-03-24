package com.lodz.android.pokemondex.ui.pokedex.binder

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lodz.android.agiledevkt.utils.sp.SpManager
import com.lodz.android.pandora.widget.rv.binder.RecyclerBinder
import com.lodz.android.pandora.widget.rv.recycler.DataVBViewHolder
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.config.Constant
import com.lodz.android.pokemondex.databinding.RvBinderGenBinding

/**
 * 第一世代
 * @author zhouL
 * @date 2022/3/24
 */
class GenBinder(context: Context, binderType: Int) : RecyclerBinder<ArrayList<PkmInfoBean>>(context, binderType) {

    private var mOnPokeClickListener: ((viewHolder: RecyclerView.ViewHolder, item: PkmInfoBean, position: Int) -> Unit)? = null

    private var mList: ArrayList<PkmInfoBean>? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        PokeListViewHolder(getViewBindingLayout(RvBinderGenBinding::inflate, parent))

    override fun getCount(): Int = 1

    fun setData(list: ArrayList<PkmInfoBean>) {
        mList = list
    }

    override fun getData(position: Int): ArrayList<PkmInfoBean>? = mList

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = mList
        if (list.isNullOrEmpty() || holder !is PokeListViewHolder) {
            return
        }
        showItem(holder, list)
    }

    private fun showItem(holder: PokeListViewHolder, list: ArrayList<PkmInfoBean>) {
        holder.apply {
            getVB<RvBinderGenBinding>().apply {
                genTv.text = getGenTitle(getViewType())
                mAdapter?.setData(list)
            }
        }
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager =
        if (SpManager.getPokeDexLayoutType() == Constant.POKE_DEX_LAYOUT_LINEAR) {
            val layoutManager = LinearLayoutManager(getContext())
            layoutManager.orientation = RecyclerView.VERTICAL
            layoutManager
        } else {
            val layoutManager = GridLayoutManager(getContext(), 2)
            layoutManager.orientation = RecyclerView.VERTICAL
            layoutManager
        }

    private fun getGenTitle(binderType: Int): String = when (binderType) {
        Constant.POKE_GENERATION_1 -> getContext().getString(R.string.poke_dex_gen_1)
        Constant.POKE_GENERATION_2 -> getContext().getString(R.string.poke_dex_gen_2)
        Constant.POKE_GENERATION_3 -> getContext().getString(R.string.poke_dex_gen_3)
        Constant.POKE_GENERATION_4 -> getContext().getString(R.string.poke_dex_gen_4)
        Constant.POKE_GENERATION_5 -> getContext().getString(R.string.poke_dex_gen_5)
        Constant.POKE_GENERATION_6 -> getContext().getString(R.string.poke_dex_gen_6)
        Constant.POKE_GENERATION_7 -> getContext().getString(R.string.poke_dex_gen_7)
        Constant.POKE_GENERATION_8 -> getContext().getString(R.string.poke_dex_gen_8)
        else -> getContext().getString(R.string.poke_dex_gen_unknow)
    }

    private inner class PokeListViewHolder(viewBinding: RvBinderGenBinding) : DataVBViewHolder(viewBinding) {
        var mAdapter: GenPokeListAdapter? = null

        init {
            mAdapter = GenPokeListAdapter(getContext())
            viewBinding.pokeRv.layoutManager = getLayoutManager()
            viewBinding.pokeRv.setHasFixedSize(true)
            viewBinding.pokeRv.adapter = mAdapter
            mOnPokeClickListener?.let {
                mAdapter?.setOnItemClickListener(it)
            }
        }
    }

    /** 宝可梦点击监听器 */
    fun setOnPokeClickListener(listener: (viewHolder: RecyclerView.ViewHolder, item: PkmInfoBean, position: Int) -> Unit) {
        mOnPokeClickListener = listener
    }

}