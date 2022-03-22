package com.lodz.android.pokemondex.ui.pokedex

import androidx.lifecycle.MutableLiveData
import com.lodz.android.corekt.anko.IoScope
import com.lodz.android.pandora.mvvm.vm.BaseRefreshViewModel
import com.lodz.android.pokemondex.bean.poke.pkm.PokemonInfoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.ArrayList

/**
 * @author zhouL
 * @date 2022/3/11
 */
class PokeDexViewModel : BaseRefreshViewModel() {

    var mDataList = MutableLiveData<ArrayList<PokemonInfoBean>>()


    fun requestDataList() {
        IoScope().launch {
            val list = ArrayList<PokemonInfoBean>()
            for (i in 1..898){
                val format = DecimalFormat("000")
                val bean = PokemonInfoBean()
                bean.index = "#${format.format(i)}"
                bean.imgUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${format.format(i)}.png"
                list.add(bean)
            }
            launch(Dispatchers.Main) {
                setSwipeRefreshFinish()
                mDataList.value = list
            }
        }

    }
}