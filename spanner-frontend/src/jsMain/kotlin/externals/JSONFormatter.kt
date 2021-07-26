package externals

import org.w3c.dom.HTMLDivElement

@JsModule("json-formatter-js")
@JsName("default")
external class JSONFormatter(
    json: Any?,
    open: Number? = definedExternally,
    config: JSONFormatterConfiguration? = definedExternally,
    key: String? = definedExternally
) {
    val json: Any
    val open: Number
    val config: JSONFormatterConfiguration
    fun render(): HTMLDivElement
}

@JsModule("json-formatter-js")
external interface JSONFormatterConfiguration {
    val hoverPreviewEnabled: Boolean?
    val hoverPreviewArrayCount: Number?
    val hoverPreviewFieldCount: Number?
    val animateOpen: Boolean?
    val animateClose: Boolean?
    val theme: String?
    val useToJSON: Boolean?
    val sortPropertiesBy: ((a: String, b: String) -> Number)?
}