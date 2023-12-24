package com.example.unidad1.modelo


class TotalMesa(val mesa: Int) {
    private val items: MutableList<ItemMenu> = mutableListOf()
    var propinaSi: Boolean = true

    fun agregarUnidad(itemMenu: ItemMenu, cantidad: Int) {
        repeat(cantidad) {
            items.add(itemMenu)
        }
    }

    fun limpiarItems() {
        items.clear()
    }

    fun calculoTotalSinPropina(): Int {
        return items.sumBy { it.precio }
    }

    fun calculoPropina(): Double {
        return if (propinaSi) calculoTotalSinPropina() * 0.1 else 0.0
    }

    fun calculoConPropina(): Double {
        return calculoTotalSinPropina() + calculoPropina()
    }
}
