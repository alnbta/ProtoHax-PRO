package dev.sora.relay.cheat.module.impl

import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.ListValue

class ModuleChatBypass : CheatModule("ChatBypass"){
    private val modeValue = listValue("BypassMode", arrayOf("Unicode", "RandomUnicode"), "Multi")
    private val chanceValue = floatValue("Chance", 0.4F, 0F, 1F)
    fun getStr(str:String):String{
        val sb = StringBuilder()
        for (char in str.toCharArray()) {
            when (modeValue.get()) {
                "Unicode" -> {
                    if (char.code in 33..128) {
                        sb.append(Character.toChars(char.code + 65248))
                    } else {
                        sb.append(char)
                    }
                }

                "RandomUnicode" -> {
                    if ((Math.random() < chanceValue.get()) && (char.code in 33..128)) {
                        sb.append(Character.toChars(char.code + 65248))
                    } else {
                        sb.append(char)
                    }
                }
            }
        }
        return sb.toString()
    }
}