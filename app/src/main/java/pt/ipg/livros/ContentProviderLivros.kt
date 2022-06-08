package pt.ipg.livros

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class ContentProviderLivros: ContentProvider() {
    var dbOpenHelper : BDLivrosOpenHelper? = null
    override fun onCreate(): Boolean {

        dbOpenHelper = BDLivrosOpenHelper(context)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
    companion object{
        const val AUTORIDADE = "pt.ipg.livros"
        const val URI_CATEGORIAS = 100
        const val URI_LIVROS = 200

        const val URI_CATEGORIAS_ESPECIFICA = 101
        const val URI_LIVROS_ESPECIFICA = 201

        fun getUriMatcher() : UriMatcher{
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH) //nao existe correspondência

            uriMatcher.addURI(AUTORIDADE, TabelaBDCategorias.NOME, URI_CATEGORIAS)//add endereços
            uriMatcher.addURI(AUTORIDADE, TabelaBDLivros.NOME, URI_LIVROS)//add endereços

            uriMatcher.addURI(AUTORIDADE,"${TabelaBDCategorias.NOME}/#", URI_CATEGORIAS_ESPECIFICA)//add endereços com uma categoria especifica
            uriMatcher.addURI(AUTORIDADE,"${TabelaBDLivros.NOME}/#", URI_LIVROS_ESPECIFICA)

            return uriMatcher
        }
    }
}