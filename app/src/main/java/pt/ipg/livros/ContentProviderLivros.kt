package pt.ipg.livros

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderLivros: ContentProvider() {
    var dbOpenHelper : BDLivrosOpenHelper? = null
    override fun onCreate(): Boolean {

        dbOpenHelper = BDLivrosOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {

        val db = dbOpenHelper!!.readableDatabase //ler bd, nao é null

        val colunas = p1 as Array<String>
        val selArgs = p3 as Array<String>

        val id = uri.lastPathSegment //da o ultimo segmento
        //content://pt.ipg.Livros/categoria/5 <- é o 5

        return when(getUriMatcher().match(uri)){
            URI_LIVROS -> TabelaBDLivros(db).query(colunas, p2, selArgs, null, null, p4)
            URI_CATEGORIAS -> TabelaBDCategorias(db).query(colunas, p2, selArgs, null, null, p4)
            URI_CATEGORIAS_ESPECIFICA -> TabelaBDCategorias(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("$id"), null, null, null)
            URI_LIVROS_ESPECIFICA -> TabelaBDLivros(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("$id"),null, null, null)
            else -> null
        }
    }

    override fun getType(uri: Uri): String? =
        when(getUriMatcher().match(uri)){
            URI_LIVROS -> "$MULTIPLOS_REGISTOS/${TabelaBDLivros.NOME}"
            URI_CATEGORIAS -> "$MULTIPLOS_REGISTOS/${TabelaBDCategorias.NOME}"
            URI_CATEGORIAS_ESPECIFICA -> "$UNICO_REGISTO/${TabelaBDCategorias.NOME}"
            URI_LIVROS_ESPECIFICA -> "$UNICO_REGISTO/${TabelaBDLivros.NOME}"
            else -> null
        }



    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase//escrver na bd, nao é null

        requireNotNull(values)

        val id = when(getUriMatcher().match(uri)){
            URI_LIVROS -> TabelaBDLivros(db).insert(values)
            URI_CATEGORIAS -> TabelaBDCategorias(db).insert(values)
            else -> -1
        }
        if(id == -1L) return null //significa que nao consegui inserir

        return Uri.withAppendedPath(uri, "$id")
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {

        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment //da o ultimo segmento
        //content://pt.ipg.Livros/categoria/5 <- é o 5

        return when(getUriMatcher().match(uri)){
            URI_CATEGORIAS_ESPECIFICA -> TabelaBDCategorias(db).delete( "${BaseColumns._ID}=?", arrayOf("$id"))
            URI_LIVROS_ESPECIFICA -> TabelaBDLivros(db).delete( "${BaseColumns._ID}=?", arrayOf("$id"))
            else -> 0
        }
    }

    override fun update(uri: Uri, p1: ContentValues?, values: String?, p3: Array<out String>?): Int {
        requireNotNull(values)
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment //da o ultimo segmento
        //content://pt.ipg.Livros/categoria/5 <- é o 5

        return when(getUriMatcher().match(uri)){
            URI_CATEGORIAS_ESPECIFICA -> TabelaBDCategorias(db).update(values, "${BaseColumns._ID}=?", arrayOf("$id"))
            URI_LIVROS_ESPECIFICA -> TabelaBDLivros(db).update(values,  "${BaseColumns._ID}=?", arrayOf("$id"))
            else -> 0
        }
    }
    companion object{
        const val AUTORIDADE = "pt.ipg.livros"
        const val URI_CATEGORIAS = 100
        const val URI_LIVROS = 200

        const val URI_CATEGORIAS_ESPECIFICA = 101
        const val URI_LIVROS_ESPECIFICA = 201

        const val UNICO_REGISTO = "vnd.android.cursor.item"
        const val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"

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