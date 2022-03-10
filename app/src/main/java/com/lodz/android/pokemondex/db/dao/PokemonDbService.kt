package com.lodz.android.pokemondex.db.dao

import com.lodz.android.pokemondex.db.table.PokemonInfoTable

/**
 * 数据库接口
 * @author zhouL
 * @date 2022/3/10
 */
interface PokemonDbService {

    /** 更新宝可梦列表[list] */
    suspend fun updatePokemonList(list: ArrayList<PokemonInfoTable>)

    /** 获取宝可梦列表，页码[pageNo]，每页数量[pageSize] */
    suspend fun getPokemonList(pageNo: Int, pageSize: Int): ArrayList<PokemonInfoTable>

    /** 获取宝全部可梦列表 */
    suspend fun getAllPokemonList() = getPokemonList(-1, 0)

    /** 根据编号[index]获取宝可梦详情 */
    suspend fun getPokemonDetail(index: Int): PokemonInfoTable?

    /** 获取本地保存的宝可梦数量 */
    suspend fun getLocalPokemonCount(): Long
}