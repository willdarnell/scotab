package org.scotab.serializers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonDecoder
import org.scotab.models.GameByIdResponse

object GameByIdResponseSerializer : KSerializer<GameByIdResponse> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GameByIdResponse") {
        element<Map<String, Int>>("game")
        element<Map<String, Int>>("team")
        element<Map<String, Int>>("field_goals")
        element<Map<String, Int>>("threepoint_goals")
        element<Map<String, Int>>("freethrows_goals")
        element<Map<String, Int>>("rebounds")
        element<Int>("assists")
        element<Int>("steals")
        element<Int>("blocks")
        element<Int>("turnovers")
        element<Int>("personal_fouls")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: GameByIdResponse) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, MapSerializer(String.serializer(), Int.serializer()), value.game)
            encodeSerializableElement(descriptor, 1, MapSerializer(String.serializer(), Int.serializer()), value.team)
            encodeSerializableElement(descriptor, 2, MapSerializer(String.serializer(), Int.serializer()), value.field_goals)
            encodeSerializableElement(descriptor, 3, MapSerializer(String.serializer(), Int.serializer()), value.threepoint_goals)
            encodeSerializableElement(descriptor, 4, MapSerializer(String.serializer(), Int.serializer()), value.freethrows_goals)
            encodeSerializableElement(descriptor, 5, MapSerializer(String.serializer(), Int.serializer()), value.rebounds)
            encodeIntElement(descriptor, 6, value.assists)
            encodeIntElement(descriptor, 7, value.steals)
            encodeIntElement(descriptor, 8, value.blocks)
            encodeIntElement(descriptor, 9, value.turnovers)
            encodeIntElement(descriptor, 10, value.personal_fouls)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): GameByIdResponse {
        return decoder.decodeStructure(descriptor) {
            var game: Map<String, Int> = emptyMap()
            var team: Map<String, Int> = emptyMap()
            var field_goals: Map<String, Int> = emptyMap()
            var threepoint_goals: Map<String, Int> = emptyMap()
            var freethrows_goals: Map<String, Int> = emptyMap()
            var rebounds: Map<String, Int> = emptyMap()
            var assists = 0
            var steals = 0
            var blocks = 0
            var turnovers = 0
            var personal_fouls = 0

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> game = decodeSerializableElement(descriptor, 0, MapSerializer(String.serializer(), Int.serializer()))
                    1 -> team = decodeSerializableElement(descriptor, 1, MapSerializer(String.serializer(), Int.serializer()))
                    2 -> field_goals = decodeSerializableElement(descriptor, 2, MapSerializer(String.serializer(), Int.serializer()))
                    3 -> threepoint_goals = decodeSerializableElement(descriptor, 3, MapSerializer(String.serializer(), Int.serializer()))
                    4 -> freethrows_goals = decodeSerializableElement(descriptor, 4, MapSerializer(String.serializer(), Int.serializer()))
                    5 -> rebounds = decodeSerializableElement(descriptor, 5, MapSerializer(String.serializer(), Int.serializer()))
                    6 -> assists = decodeIntElement(descriptor, 6)
                    7 -> steals = decodeIntElement(descriptor, 7)
                    8 -> blocks = decodeIntElement(descriptor, 8)
                    9 -> turnovers = decodeIntElement(descriptor, 9)
                    10 -> personal_fouls = decodeIntElement(descriptor, 10)
                    else -> break@loop
                }
            }

            GameByIdResponse(game, team, field_goals, threepoint_goals, freethrows_goals, rebounds, assists, steals, blocks, turnovers, personal_fouls)
        }
    }
}