package pt.ipg.livros

import android.content.ContentValues
import android.provider.BaseColumns

data class Categoria(var nome: String, var id: Long = -1) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDCategorias.CAMPO_NOME, nome)

        return valores
    }
}