package dev.sora.relay.cheat.module.impl
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType
import com.nukkitx.protocol.bedrock.packet.AnimatePacket
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketOutbound

class ModuleCriticals : CheatModule("Criticals") {
    private val modeValue = listValue("Mode", arrayOf("MovePacket"), "MovePacket")

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound){
        if(event.packet is InventoryTransactionPacket){
            if(event.packet.transactionType == TransactionType.ITEM_USE_ON_ENTITY && event.packet.actionType == 1){ //Attack
                if(modeValue.get() == "MovePacket"){
                    event.session.netSession.outboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = event.session.thePlayer.entityId
                        position = event.session.thePlayer.vec3Position.add(0f, 0.2f, 0f)
                        isOnGround = false
                    })

                    event.session.netSession.outboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = event.session.thePlayer.entityId
                        position = event.session.thePlayer.vec3Position.add(0f, 0.04f, 0f)
                        isOnGround = false
                    })
                }
            }
        }
    }
}