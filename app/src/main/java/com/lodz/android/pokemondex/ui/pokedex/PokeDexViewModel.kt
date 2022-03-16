package com.lodz.android.pokemondex.ui.pokedex

import androidx.lifecycle.MutableLiveData
import com.lodz.android.pokemondex.bean.base.PageBean
import com.lodz.android.corekt.anko.IoScope
import com.lodz.android.pandora.mvvm.vm.BaseRefreshViewModel
import com.lodz.android.pokemondex.db.table.PokemonInfoTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 * @author zhouL
 * @date 2022/3/11
 */
class PokeDexViewModel : BaseRefreshViewModel() {

    var mDataList = MutableLiveData<Pair<Boolean, PageBean<ArrayList<PokemonInfoTable>>>>()


    fun requestDataList(page: Int){
        IoScope().launch {
            val count = page * PageBean.DEFAULT_PAGE_SIZE
            if (count > PageBean.DEFAULT_TOTAL) {
                val pageBean = PageBean<ArrayList<PokemonInfoTable>>()
                pageBean.data = null
                pageBean.pageNum = page
                launch(Dispatchers.Main) {
                    mDataList.value = Pair(page == PageBean.DEFAULT_START_PAGE_NUM, pageBean)
                }
                return@launch
            }

            val list = ArrayList<PokemonInfoTable>()
            for (i in 1..PageBean.DEFAULT_PAGE_SIZE) {
                list.add(PokemonInfoTable())
            }
            delay(1000)
            val pageBean = PageBean<ArrayList<PokemonInfoTable>>()
            pageBean.data = list
            pageBean.pageNum = page
            launch(Dispatchers.Main) {
                mDataList.value = Pair(page == PageBean.DEFAULT_START_PAGE_NUM, pageBean)
            }
        }
    }
}