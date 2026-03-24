package com.example.pkm_forms.Models.Styles

data class StylesGeneral(

    @JvmField
    var color: Any? = null,
    @JvmField
    var backgroundColor : Any? = null,
    @JvmField
    var fontFamily: Any? = null,
    @JvmField
    var textSize: Any? = null,
    @JvmField
    var border: BorderStyles? = null,

    @JvmField
    var tieneColor : Boolean = false,
    @JvmField
    var tieneBackgroundColor : Boolean = false,
    @JvmField
    var tieneFontFamily: Boolean = false,
    @JvmField
    var tieneTextSize: Boolean = false,
    @JvmField
    var tieneBorder: Boolean = false,
){
    override fun toString(): String {
        val partes = mutableListOf<String>()

        color?.let {partes.add("\"color\": $it")}
        backgroundColor?.let {partes.add("\"background color\": $it")}
        fontFamily?.let {partes.add("\"font family\": $it")}
        textSize?.let {partes.add("\"text size\": $it")}
        border?.let {partes.add("\"border\": $it")}

        return "styles [ ${partes.joinToString (", ")} ]"
    }
}
