package dev.haqim.scrumdingerkmmlib.domain.models

enum class Theme(val colorName: String) {
    bubblegum("bubblegum"),
    buttercup("buttercup"),
    indigo("indigo"),
    lavender("lavender"),
    magenta("magenta"),
    navy("navy"),
    orange("orange"),
    oxblood("oxblood"),
    periwinkle("periwinkle"),
    poppy("poppy"),
    purple("purple"),
    seafoam("seafoam"),
    sky("sky"),
    tan("tan"),
    teal("teal"),
    yellow("yellow"),
    white("white"),
    black("black");

    override fun toString(): String {
        return colorName.replaceFirstChar { it.uppercaseChar() }
    }

    val id: String
        get() = colorName

    val accentColor: String
        get (){
            return when (this) {
                bubblegum, buttercup, lavender, orange, periwinkle, poppy, seafoam, sky, tan,teal, yellow, white -> black.colorName
                indigo, magenta, navy, oxblood, purple, black -> white.colorName
            }
        }

    companion object {
        fun fromColorName(colorName: String): Theme? {
            return entries.find { it.colorName.equals(colorName, ignoreCase = true) }
        }
    }
}
