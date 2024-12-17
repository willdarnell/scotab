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
import kotlinx.serialization.json.int
import org.scotab.models.Game

object CustomGameSerializer : KSerializer<Game> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Game") {
        element<Int>("id")
        element<String>("date")
        element<String>("time")
        element<Long>("timestamp")
        element<String>("timezone")
        element<String?>("stage")
        element<String?>("week")
        element<String?>("venue")
        element<Map<String, String>>("status")
        element<Map<String, String>>("league")
        element<Map<String, String>>("country")
        element<Map<String, String>>("teams")
        element<Map<String, Int>>("scores")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Game) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.id)
            encodeStringElement(descriptor, 1, value.date)
            encodeStringElement(descriptor, 2, value.time)
            encodeLongElement(descriptor, 3, value.timestamp)
            encodeStringElement(descriptor, 4, value.timezone)
            encodeNullableSerializableElement(descriptor, 5, String.serializer(), value.stage)
            encodeNullableSerializableElement(descriptor, 6, String.serializer(), value.week)
            encodeNullableSerializableElement(descriptor, 7, String.serializer(), value.venue)
            encodeSerializableElement(descriptor, 8, MapSerializer(String.serializer(), String.serializer()), value.status)
            encodeSerializableElement(descriptor, 9, MapSerializer(String.serializer(), AnySerializer), value.league)
            encodeSerializableElement(descriptor, 10, MapSerializer(String.serializer(), AnySerializer), value.country)
            encodeSerializableElement(descriptor, 11, MapSerializer(String.serializer(), AnySerializer), value.teams)
            //encodeSerializableElement(descriptor, 12, MapSerializer(String.serializer(), Int.serializer()), value.scores)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): Game {
        return decoder.decodeStructure(descriptor) {
            var id = 0
            var date = ""
            var time = ""
            var timestamp = 0L
            var timezone = ""
            var stage: String? = null
            var week: String? = null
            var venue: String? = null
            var status: Map<String, String> = emptyMap()
            var league: Map<String, String> = emptyMap()
            var country: Map<String, String> = emptyMap()
            var teams: Map<String, Map<String, String>> = emptyMap()
            var scores: Map<String, Map<String, Int>> = emptyMap()

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> id = decodeIntElement(descriptor, 0)
                    1 -> date = decodeStringElement(descriptor, 1)
                    2 -> time = decodeStringElement(descriptor, 2)
                    3 -> timestamp = decodeLongElement(descriptor, 3)
                    4 -> timezone = decodeStringElement(descriptor, 4)
                    5 -> stage = decodeNullableSerializableElement(descriptor, 5, String.serializer())
                    6 -> week = decodeNullableSerializableElement(descriptor, 6, String.serializer())
                    7 -> venue = decodeNullableSerializableElement(descriptor, 7, String.serializer())
                    8 -> status = decodeSerializableElement(descriptor, 8, MapSerializer(String.serializer(), String.serializer()))
                    9 -> league = decodeSerializableElement(descriptor, 9, MapSerializer(String.serializer(), String.serializer()))
                    10 -> country = decodeSerializableElement(descriptor, 10, MapSerializer(String.serializer(), String.serializer()))
                    11 -> teams = decodeSerializableElement(descriptor, 11, MapSerializer(String.serializer(), MapSerializer(String.serializer(), String.serializer())))
                    12 -> scores = decodeSerializableElement(descriptor, 12, MapSerializer(String.serializer(), MapSerializer(String.serializer(), Int.serializer())))
                    else -> break@loop
                }
            }

            Game(id, date, time, timestamp, timezone, stage, week, venue, status, league, country, teams, scores)
        }
    }
}

object AnySerializer : KSerializer<Any> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Any")

    override fun serialize(encoder: Encoder, value: Any) {
        when (value) {
            is Int -> encoder.encodeInt(value)
            is String -> encoder.encodeString(value)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    override fun deserialize(decoder: Decoder): Any {
        val input = decoder as? JsonDecoder ?: throw IllegalStateException("This serializer can be used only with JSON format")
        val element = input.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> {
                if (element.isString) element.content else element.int
            }
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}