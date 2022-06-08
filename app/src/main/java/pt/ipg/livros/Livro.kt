package pt.ipg.livros

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Livro(
    var titulo : String,
    var autor: String,
    var idCategoria: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLivros.CAMPO_TITULO, titulo)
        valores.put(TabelaBDLivros.CAMPO_AUTOR, autor)
        valores.put(TabelaBDLivros.CAMPO_CATEGORIA_ID, idCategoria)

        return valores
    }
    companion object {
        fun fromCursor(cursor: Cursor): Livro {
            val posId = cursor.getColumnIndex(BaseColumns._ID) //id
            val posTitulo = cursor.getColumnIndex(TabelaBDLivros.CAMPO_TITULO) //nome
            val posAutor = cursor.getColumnIndex(TabelaBDLivros.CAMPO_AUTOR) //nome
            val posIdCateg = cursor.getColumnIndex(TabelaBDLivros.CAMPO_CATEGORIA_ID) //nome

            val id = cursor.getLong(posId) //id
            val titulo = cursor.getString(posTitulo) //nome
            val autor = cursor.getString(posAutor)
            val idCateg = cursor.getLong(posIdCateg)

            return Livro(titulo, autor, idCateg, id)
        }
    }
}