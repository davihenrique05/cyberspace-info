package com.example.cyberspace_info.listaeventosnaturais.model

data class GeometryEventModel(
    val date: String,
    val coordinates : Array<Double>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeometryEventModel

        if (date != other.date) return false
        if (!coordinates.contentEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + coordinates.contentHashCode()
        return result
    }
}
