package dev.sora.relay.cheat.module.impl

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.Ability
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType
import com.nukkitx.protocol.bedrock.packet.*
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.utils.TimerUtil
import dev.sora.relay.game.utils.movement.MovementUtils.isMoving
import kotlin.math.cos
import kotlin.math.sin

class ModuleSpeed : CheatModule("Speed") {
    private val modeValue = listValue("Mode", arrayOf("Hop", "LowHop", "Legit"), "Hop")
    private val speedValue = floatValue("HopSpeed", 0.39f, 0f, 2f)
    private val lowHopSpeedValue = floatValue("LowHopSpeed", 0.39f, 0f, 2f)
    private val hopYValue = floatValue("HopY", 0.32f, 0f, 1f)
    private val damageBoostXZValue = floatValue("DamageBoostXZ", 1.2f, 0.5f, 3f)
    private val damageBoostYValue = floatValue("DamageBoostY", 1.2f, 0.5f, 3f)
    private val fallSpeedValue = floatValue("FallSpeed", 0.08f, 0f, 0.5f)
    private var onDamage = 0
    private val damageTimer = TimerUtil()

    @Listen
    fun onTick(event: EventTick) {
        when (modeValue.get().lowercase()) {
            "legit" -> {
                if (mc.thePlayer.motionY == 0.0 && isMoving(mc)) {
                    mc.thePlayer.jump(session)
                }
            }

            "hop" -> {
                strafe(speedValue.get())
            }
        }
    }

    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        if (damageTimer.delay(1000f)) {
            onDamage = 0
        }
        if (event.packet is EntityEventPacket) {
            if (event.packet.type== EntityEventType.HURT && event.packet.runtimeEntityId==mc.thePlayer.entityId){
                onDamage=1
                damageTimer.reset()
                chat("Damage Boosting!")
            }
        }
    }

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        when {
            modeValue.get() == "LowHop" -> {
                if (event.packet is PlayerAuthInputPacket && isMoving(mc)) strafe(
                    lowHopSpeedValue.get(),
                    if (mc.thePlayer.inputData.contains(PlayerAuthInputData.JUMPING)) lowHopSpeedValue.get() else if (mc.thePlayer.inputData.contains(
                            PlayerAuthInputData.SNEAKING
                        )
                    ) -hopYValue.get() else if (mc.thePlayer.onGround) 0.08f else -0.1f
                )
            }

            else -> {
                if (event.packet is RequestAbilityPacket && event.packet.ability == Ability.FLYING) {
                    event.cancel()
                }
            }
        }
    }

    private fun strafe(speed: Float, motionY: Float) {
        val yaw = direction
        val y=motionY.toDouble() * (if(onDamage==1) damageBoostYValue.get() else 1.0f)
        session.netSession.inboundPacket(SetEntityMotionPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            motion = Vector3f.from(
                -sin(yaw) * speed * (if(onDamage==1) damageBoostXZValue.get() else 1.0f),
                if(y>=1) 1.0 else y,
                cos(yaw) * speed * (if(onDamage==1) damageBoostXZValue.get() else 1.0f)
            )
        })
    }

    private fun strafe(speed: Float) {
        if (!isMoving(mc)) {
            if (mc.thePlayer.motionY > 0) {
                val y=(-fallSpeedValue.get() * (if(onDamage==1) damageBoostYValue.get() else 1.0f)).toDouble()
                session.netSession.inboundPacket(SetEntityMotionPacket().apply {
                    runtimeEntityId = mc.thePlayer.entityId
                    motion = Vector3f.from(
                        0.0,
                        if(y>=1) 1.0 else y,
                        0.0
                    )
                })
            }
        } else {
            val yaw = direction
            var motionY = if (mc.thePlayer.motionY == 0.0) hopYValue.get().toDouble() else 0.0
            if (mc.thePlayer.motionY <= 0.1 && mc.thePlayer.motionY > 0) motionY = (-fallSpeedValue.get()).toDouble()
            if (motionY != 0.0) {
                val y=motionY * (if(onDamage==1) damageBoostYValue.get() else 1.0f)
                session.netSession.inboundPacket(SetEntityMotionPacket().apply {
                    runtimeEntityId = mc.thePlayer.entityId
                    motion = Vector3f.from(
                        -sin(yaw) * speed * (if(onDamage==1) damageBoostXZValue.get() else 1.0f),
                        if(y>=1) 1.0 else y,
                        cos(yaw) * speed * (if(onDamage==1) damageBoostXZValue.get() else 1.0f)
                    )
                })
            }
        }
    }
}