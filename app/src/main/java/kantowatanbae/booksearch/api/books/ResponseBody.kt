package kantowatanbae.booksearch.api.books

data class BooksResponseBody(val totalItems: Int, val items: List<Item>)

data class Item(val id: String, val volumeInfo: VolumeInfo)

data class VolumeInfo(val title: String, val authors: List<String>, val publisher: String, val publishedDate: String, val description: String, val imageLinks: ImageLinks?)

data class ImageLinks(val smallThumbnail: String?, val thumbnail: String?)
