import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.scotab.models.Game

object CustomGameSerializer : KSerializer<Game> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Game") {
        element<Int>("id")
        element<String>("league")
        element<Int>("season")
        element<Map<String, String?>>("date")
        element<Int>("stage")
        element<Map<String, String?>>("status") // Make status values nullable
        element<Map<String, String?>>("periods") // Make periods values nullable
        element<Map<String, String?>>("arena")
        element<Map<String, Map<String, String>>>("teams")
        element<Map<String, Map<String, String>>>("scores")
        element<List<String>>("officials")
        element<Int?>("timesTied")
        element<Int?>("leadChanges")
        element<String?>("nugget")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Game) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.id)
            encodeStringElement(descriptor, 1, value.league)
            encodeIntElement(descriptor, 2, value.season)
            encodeSerializableElement(
                descriptor,
                3,
                MapSerializer(String.serializer(), String.serializer().nullable),
                value.date
            )
            encodeIntElement(descriptor, 4, value.stage)
            encodeSerializableElement(
                descriptor,
                5,
                MapSerializer(String.serializer(), String.serializer().nullable),
                value.status
            )
            encodeSerializableElement(
                descriptor,
                6,
                MapSerializer(String.serializer(), String.serializer().nullable),
                value.periods
            )
            encodeSerializableElement(
                descriptor,
                7,
                MapSerializer(String.serializer(), String.serializer().nullable),
                value.arena
            )
            encodeSerializableElement(
                descriptor,
                8,
                MapSerializer(
                    String.serializer(),
                    MapSerializer(String.serializer(), String.serializer())
                ),
                value.teams
            )
            encodeSerializableElement(
                descriptor,
                9,
                MapSerializer(
                    String.serializer(),
                    MapSerializer(String.serializer(), String.serializer())
                ),
                value.scores
            )
            encodeSerializableElement(
                descriptor,
                10,
                ListSerializer(String.serializer()),
                value.officials
            )
            encodeNullableSerializableElement(descriptor, 11, Int.serializer(), value.timesTied)
            encodeNullableSerializableElement(descriptor, 12, Int.serializer(), value.leadChanges)
            encodeNullableSerializableElement(descriptor, 13, String.serializer(), value.nugget)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): Game {
        return decoder.decodeStructure(descriptor) {
            var id = 0
            var league = ""
            var season = 0
            var date: Map<String, String?> = emptyMap()
            var stage = 0
            var status: Map<String, String?> = emptyMap()
            var periods: Map<String, String?> = emptyMap()
            var arena: Map<String, String?> = emptyMap()
            var teams: Map<String, Map<String, String>> = emptyMap()
            var scores: Map<String, Map<String, String>> = emptyMap()
            var officials: List<String> = emptyList()
            var timesTied: Int? = null
            var leadChanges: Int? = null
            var nugget: String? = null

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> id = decodeIntElement(descriptor, 0)
                    1 -> league = decodeStringElement(descriptor, 1)
                    2 -> season = decodeIntElement(descriptor, 2)
                    3 -> date = decodeSerializableElement(
                        descriptor,
                        3,
                        MapSerializer(String.serializer(), String.serializer().nullable)
                    )
                    4 -> stage = decodeIntElement(descriptor, 4)
                    5 -> status = decodeSerializableElement(
                        descriptor,
                        5,
                        MapSerializer(String.serializer(), String.serializer().nullable)
                    )
                    6 -> periods = decodeSerializableElement(
                        descriptor,
                        6,
                        MapSerializer(String.serializer(), String.serializer().nullable)
                    )
                    7 -> arena = decodeSerializableElement(
                        descriptor,
                        7,
                        MapSerializer(String.serializer(), String.serializer().nullable)
                    )
                    8 -> teams = decodeSerializableElement(
                        descriptor,
                        8,
                        MapSerializer(
                            String.serializer(),
                            MapSerializer(String.serializer(), String.serializer())
                        )
                    )
                    9 -> scores = decodeSerializableElement(
                        descriptor,
                        9,
                        MapSerializer(
                            String.serializer(),
                            MapSerializer(String.serializer(), String.serializer())
                        )
                    )
                    10 -> officials = decodeSerializableElement(
                        descriptor,
                        10,
                        ListSerializer(String.serializer())
                    )
                    11 -> timesTied =
                        decodeNullableSerializableElement(descriptor, 11, Int.serializer())
                    12 -> leadChanges =
                        decodeNullableSerializableElement(descriptor, 12, Int.serializer())
                    13 -> nugget =
                        decodeNullableSerializableElement(descriptor, 13, String.serializer())
                    else -> break@loop
                }
            }

            Game(
                id,
                league,
                season,
                date,
                stage,
                status,
                periods,
                arena,
                teams,
                scores,
                officials,
                timesTied,
                leadChanges,
                nugget
            )
        }
    }
}