package pt.ipg.livros

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Categoria(var nome: String, var id: Long = -1) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDCategorias.CAMPO_NOME, nome)

        return valores
    }
    //para comparar os resultados no teste query
    companion object{
        fun fromCursor(cursor: Cursor) : Categoria{
            val posId = cursor.getColumnIndex(BaseColumns._ID) //id
            val posNome = cursor.getColumnIndex(TabelaBDCategorias.CAMPO_NOME) //nome

            val id  = cursor.getLong(posId) //id
            val nome = cursor.getString(posNome) //nome

            return Categoria(nome, id)
        }
    }
}