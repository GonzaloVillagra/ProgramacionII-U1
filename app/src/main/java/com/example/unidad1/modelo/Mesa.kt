package com.example.unidad1.modelo

import android.view.Menu

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int {
        return itemMenu.precio.toInt() * cantidad
    }
}
