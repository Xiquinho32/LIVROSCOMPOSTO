package pt.ipg.livros

data class Livro(
    var id: Long,
    var titulo : String,
    var autor: String,
    var idCategoria: Long
) {
}