package com.example.unidad1


import java.text.NumberFormat
import java.util.Locale
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.unidad1.modelo.ItemMenu
import com.example.unidad1.modelo.TotalMesa

class MainActivity : AppCompatActivity() {

    private lateinit var totalMesa: TotalMesa
    var valorComida: TextView? = null
    var valorPropina: TextView? = null
    var valorCuenta: TextView? = null
    var valorTotalPC: TextView? = null
    var valorTotalCazuela: TextView? = null
    var unidadesCazuela: EditText? = null
    var unidadesPC: EditText? = null
    var swPropina: Switch? = null

    val pastelChoclo = "Pastel de Choclo"
    val cazuela = "Cazuela"
    val valorPastel = 12000
    val valorCazuela = 10000
    val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        totalMesa = TotalMesa(1)

        unidadesCazuela = findViewById(R.id.UnidadesCazuela)
        unidadesPC = findViewById(R.id.UnidadesPC)
        swPropina = findViewById(R.id.swPropina)
        valorComida = findViewById(R.id.ValorComida)
        valorPropina = findViewById(R.id.ValorPropina)
        valorCuenta = findViewById(R.id.ValorCuenta)
        valorTotalCazuela = findViewById(R.id.ValorTotalCazuela)
        valorTotalPC = findViewById(R.id.ValorTotalPC)

        actualizarTotales()

        swPropina?.setOnCheckedChangeListener { _, isChecked ->
            totalMesa.propinaSi = isChecked
            actualizarTotales()
        }

        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                actualizarTotales()
            }
        }

        unidadesPC?.addTextChangedListener(textWatcher)
        unidadesCazuela?.addTextChangedListener(textWatcher)
    }

    private fun actualizarTotales() {
        totalMesa.limpiarItems()

        val cantidadPastel = unidadesPC?.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = unidadesCazuela?.text.toString().toIntOrNull() ?: 0

        totalMesa.agregarUnidad(ItemMenu(pastelChoclo, valorPastel), cantidadPastel)
        totalMesa.agregarUnidad(ItemMenu(cazuela, valorCazuela), cantidadCazuela)


        actualizarSubTotal()
        actualizarPropina()
        actualizarPrecioTotal()
        actualizarPrecioPorProducto()
    }

    private fun actualizarSubTotal() {
        val totalComida = totalMesa.calculoTotalSinPropina()
        valorComida?.text = formatoMoneda.format(totalComida)    }


    private fun actualizarPropina() {
        val totalSinPropina = totalMesa.calculoTotalSinPropina()
        val propina = totalMesa.calculoPropina()
        valorPropina?.text = formatoMoneda.format(propina)
    }

    private fun actualizarPrecioTotal() {
        val total = totalMesa.calculoConPropina()
        valorCuenta?.text = formatoMoneda.format(total)
    }

    fun actualizarPrecioPorProducto() {
        val totalPC = unidadesPC?.text.toString().toIntOrNull() ?: 0
        val totalCazuela = unidadesCazuela?.text.toString().toIntOrNull() ?: 0

        val valorPcTotal= totalPC * valorPastel.toInt()
        val valorCazuelaTotal = totalCazuela * valorCazuela.toInt()

        valorTotalPC?.setText(formatoMoneda.format(valorPcTotal))
        valorTotalCazuela?.setText(formatoMoneda.format(valorCazuelaTotal))

      }
    }
