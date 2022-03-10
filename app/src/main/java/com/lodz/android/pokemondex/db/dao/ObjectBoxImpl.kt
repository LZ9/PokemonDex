package com.lodz.android.pokemondex.db.dao

import com.lodz.android.corekt.anko.toArrayList
import com.lodz.android.pokemondex.db.table.PokemonInfoTable
import com.lodz.android.pokemondex.db.table.PokemonInfoTable_

/**
 * @author zhouL
 * @date 2022/3/10
 */
class ObjectBoxImpl :PokemonDbService{

    companion object {
        fun get(): ObjectBoxImpl = ObjectBoxImpl()
    }

    override suspend fun updatePokemonList(list: ArrayList<PokemonInfoTable>) {
        if (list.isNotEmpty()) {
            ObjectBox.boxFor(PokemonInfoTable::class.java).put(list)
        }
    }

    override suspend fun getPokemonList(pageNo: Int, pageSize: Int): ArrayList<PokemonInfoTable> {
        if (pageNo <= 0) {
            return ObjectBox.boxFor(PokemonInfoTable::class.java)
                .query().order(PokemonInfoTable_.index).build()
                .find().toArrayList()
        }

        val offset: Long = (pageNo - 1L) * pageSize
        val limit: Long = pageSize.toLong()
        return ObjectBox.boxFor(PokemonInfoTable::class.java)
            .query().order(PokemonInfoTable_.index).build()
            .find(offset, limit).toArrayList()
    }

    override suspend fun getPokemonDetail(index: Int): PokemonInfoTable? =
        ObjectBox.boxFor(PokemonInfoTable::class.java).query(PokemonInfoTable_.index.equal(index))
            .build().findFirst()

    override suspend fun getLocalPokemonCount(): Long =
        ObjectBox.boxFor(PokemonInfoTable::class.java).count()

}