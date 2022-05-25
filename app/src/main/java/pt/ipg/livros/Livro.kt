package pt.ipg.livros

import android.content.ContentValues

data class Livro(
    var id: Long,
    var titulo : String,
    var autor: String,
    var idCategoria: Long
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLivros.CAMPO_TITULO, titulo)
        valores.put(TabelaBDLivros.CAMPO_AUTOR, autor)
        valores.put(TabelaBDLivros.CAMPO_CATEGORIA_ID, idCategoria)

        return valores
    }
}