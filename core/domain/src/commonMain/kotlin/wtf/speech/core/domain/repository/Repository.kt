package wtf.speech.core.domain.repository

interface Repository<T> {
    fun getById(id: Int): T
    fun getAll(): List<T>
    fun add(entity: T)
    fun update(entity: T)
    fun delete(entity: T)
}
