package dev.sora.relay.cheat.module

import dev.sora.relay.cheat.module.impl.*
import dev.sora.relay.game.GameSession

class ModuleManager(private val session: GameSession) {

    val modules = mutableListOf<CheatModule>()
    fun getModuleByName(name: String): CheatModule? {
        for (module in modules) {
            if (module.name.equals(name, true)) return module
        }
        return null
    }

    fun registerModule(module: CheatModule) {
        module.session = session
        modules.add(module)
        session.eventManager.registerListener(module)
    }

    fun init() {
        registerModule(ModuleFly())
        registerModule(ModuleVelocity())
        registerModule(ModuleKillAura())
        registerModule(ModuleSpammer())
        registerModule(ModuleBGM())
        registerModule(ModuleDisabler())
        registerModule(ModuleOpFightBot())
        registerModule(ModuleNoSkin())
        registerModule(ModuleDeviceSpoof())
        registerModule(ModuleGodmode())
        registerModule(ModuleAntiKick())
        registerModule(ModuleAntiCrasher())
        registerModule(ModuleComboOneHitExploit())
        registerModule(ModuleCriticals())
        registerModule(ModuleNoHurt())
        registerModule(ModuleSpeed())
        registerModule(ModuleHighJump())
        registerModule(ModuleFakeLag())
        registerModule(ModuleNoSoundEvent())
        registerModule(ModuleNoChat())
        registerModule(ModuleResourcePackSpoof)
        registerModule(ModuleAntiBot)
        registerModule(ModuleNoFall())
        registerModule(ModuleAntiBlind())
        registerModule(ModuleFastBreak())
        registerModule(ModuleBlink())
        registerModule(ModuleChatBypass())
        registerModule(ModulePacketLogger())
        registerModule(ModuleBlockFly())
        registerModule(ModuleInventoryHelper())
    }
}