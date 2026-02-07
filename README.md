Backlog v1.

Historias de Usuario MVP (US-01 a US-07).
US-01 — Ver el nuevo juego en el catálogo
Como usuario de la app
 Quiero ver “Piedra, Papel o Tijeras” en el catálogo de juegos
 Para poder acceder al nuevo juego como una opción más
Criterios (Given/When/Then)
Given que estoy en la pantalla “Catálogo de juegos”
 When la app carga la lista de juegos
 Then veo “Lotería”, “Adivina el número” y “Piedra, Papel o Tijeras” como opciones disponibles


Given que el catálogo está visible
 When toco “Piedra, Papel o Tijeras”
 Then navego a la pantalla del juego “Piedra, Papel o Tijeras”

US-02 — Elegir arma en la pantalla del juego
Como usuario
 Quiero seleccionar Piedra, Papel o Tijeras
 Para definir mi jugada antes de iniciar la partida
Criterios (Given/When/Then)
Given que estoy en la pantalla de “Piedra, Papel o Tijeras”
 When toco una opción (ej. “Papel”)
 Then esa opción queda seleccionada visualmente
 And cualquier selección previa queda deseleccionada


Given que hay una opción seleccionada
 When toco otra opción distinta
 Then se actualiza la selección a la nueva opción
 And el UI refleja el cambio (aura/realce/estado activo)

US-03 — Validación: no jugar sin selección
Como usuario
 Quiero que la app me avise si intento jugar sin elegir arma
 Para evitar llamadas inútiles al servidor y confusión
Criterios (Given/When/Then)
Given que estoy en la pantalla del juego
 And no he seleccionado ninguna arma
 When presiono “¡JUGAR!”
 Then la app muestra un mensaje “Selecciona un arma” (Snackbar/Toast)
 And no se realiza ninguna llamada al backend

US-04 — Jugar una ronda y recibir resultado del backend
Como usuario
 Quiero jugar una ronda contra el servidor
 Para saber si gané, perdí o empaté
Criterios (Given/When/Then)
Given que tengo un arma seleccionada
 When presiono “¡JUGAR!”
 Then la app hace una solicitud GET al backend enviando mi jugada
 And la app muestra estado de carga mientras espera respuesta
 And el botón “¡JUGAR!” queda deshabilitado durante la carga


Given que el backend responde exitosamente
 When la app recibe la respuesta
 Then se muestra el panel “Último resultado” con:
 And mi jugada
 And la jugada del rival
 And el resultado (GANASTE / PERDISTE / EMPATE)
 And el mensaje explicativo (“El papel envuelve a la piedra”)

US-05 — Contrato de respuesta consistente
Como desarrollador del frontend
 Quiero un formato de respuesta estable del backend
 Para renderizar el resultado sin lógica duplicada
Criterios (Given/When/Then)
Given que hago GET /api/rps/play?move=PAPER
 When el backend responde 200 OK
 Then recibo un JSON que incluye:
 And playerMove en {ROCK, PAPER, SCISSORS}
 And serverMove en {ROCK, PAPER, SCISSORS}
 And outcome en {WIN, LOSE, DRAW}
 And message como texto legible
 And timestamp (opcional)

US-06 — Manejo de errores de entrada (backend)
Como consumidor del backend
 Quiero que el backend valide la jugada
 Para evitar resultados incorrectos o crashes
Criterios (Given/When/Then)
Given que hago una solicitud con move inválido (ej. move=FIRE)
 When el backend procesa la solicitud
 Then responde 400 Bad Request
 And devuelve { errorCode, message } con mensaje claro


Given que hago una solicitud sin move
 When el backend procesa la solicitud
 Then responde 400 Bad Request con mensaje de parámetro requerido

US-07 — Manejo de error de red (frontend)
Como usuario
 Quiero que la app me avise si falla la conexión
 Para poder reintentar sin quedarme “atorado”
Criterios (Given/When/Then)
Given que tengo un arma seleccionada
 When presiono “¡JUGAR!” y ocurre un error de red/timeout
 Then la app muestra un mensaje “No se pudo conectar al servidor”
 And el botón se vuelve a habilitar
 And puedo presionar “¡JUGAR!” nuevamente para reintentar


Historias de Usuario NTH (US-08, US-09)
 US-08 — Persistencia mínima del último resultado (recomendado)
Como usuario
 Quiero que el último resultado no desaparezca al rotar la pantalla
 Para no perder el contexto de lo que acaba de pasar
Criterios (Given/When/Then)
Given que ya jugué una ronda y veo “Último resultado”
 When roto el dispositivo o se recrea la pantalla
 Then el “Último resultado” sigue visible con la misma información

US-09 (Opcional) — Ver estadísticas
Como usuario
 Quiero ver mi historial de ganadas/perdidas/empatadas
 Para medir mi “performance” contra el rival
Criterios (Given/When/Then)
Given que estoy en la pantalla de PPT
 When toco el ícono de leaderboard
 Then veo una pantalla/panel con totales: ganadas, perdidas, empatadas
 And los totales se actualizan tras cada partida


Contrato API definitivo (Backend Spring Boot Kotlin)
Endpoint
GET /api/rps/play?move=ROCK|PAPER|SCISSORS
move obligatorio


move es case-insensitive recomendado (acepta rock, ROCK, Rock), pero el backend siempre responde normalizado en MAYÚSCULAS.


Respuestas
✅ 200 OK — Resultado de la ronda
Response JSON
{
  "playerMove": "PAPER",
  "serverMove": "ROCK",
  "outcome": "WIN",
  "message": "El papel envuelve a la piedra",
  "rule": "PAPER_BEATS_ROCK",
  "timestamp": "2026-02-07T18:30:00Z"
}

Semántica de campos
playerMove: jugada del usuario (ROCK, PAPER, SCISSORS)


serverMove: jugada del servidor (ROCK, PAPER, SCISSORS)


outcome: WIN | LOSE | DRAW


message: texto humano para UI


rule: identificador estable para depurar / testear (muy útil en pruebas)


timestamp: ISO-8601 UTC (opcional pero recomendado para trazabilidad)



❌ 400 Bad Request — Error de validación
Response JSON

{
  "errorCode": "INVALID_MOVE",
  "message": "move debe ser uno de: ROCK, PAPER, SCISSORS",
  "details": {
    "received": "FIRE",
    "expected": ["ROCK", "PAPER", "SCISSORS"]
  },
  "timestamp": "2026-02-07T18:30:00Z"
}

Códigos sugeridos:
MISSING_MOVE


INVALID_MOVE



DTOs exactos (Kotlin) — Backend
Enums (contrato)
enum class RpsMove { ROCK, PAPER, SCISSORS }
enum class RpsOutcome { WIN, LOSE, DRAW }

// Identificador estable de la regla aplicada (sirve para tests/telemetría)
enum class RpsRule {
    DRAW,                 // mismo movimiento
    ROCK_BEATS_SCISSORS,  // ROCK gana a SCISSORS
    PAPER_BEATS_ROCK,     // PAPER gana a ROCK
    SCISSORS_BEATS_PAPER  // SCISSORS gana a PAPER
}

Success DTO
data class RpsPlayResponse(
    val playerMove: RpsMove,
    val serverMove: RpsMove,
    val outcome: RpsOutcome,
    val message: String,
    val rule: RpsRule,
    val timestamp: String // ISO-8601, ej. Instant.now().toString()
)

Error DTO
data class ApiErrorResponse(
    val errorCode: String,
    val message: String,
    val details: Map<String, Any>? = null,
    val timestamp: String
)

Nota de “arquitectura con cerebro”: rule te ahorra dolores. Puedes testear por rule en vez de comparar strings del message (que cambia fácil).

DTOs exactos (Kotlin) — Frontend Android
En Android usa los mismos nombres para evitar mapping raro. Si usas Kotlin serialization o Gson/Moshi, esto es directo:
enum class RpsMove { ROCK, PAPER, SCISSORS }
enum class RpsOutcome { WIN, LOSE, DRAW }
enum class RpsRule { DRAW, ROCK_BEATS_SCISSORS, PAPER_BEATS_ROCK, SCISSORS_BEATS_PAPER }

data class RpsPlayResponse(
    val playerMove: RpsMove,
    val serverMove: RpsMove,
    val outcome: RpsOutcome,
    val message: String,
    val rule: RpsRule,
    val timestamp: String
)

data class ApiErrorResponse(
    val errorCode: String,
    val message: String,
    val details: Map<String, Any>? = null,
    val timestamp: String
)

Matriz de reglas 3x3 (cobertura total)

Filas = jugada del usuario
 Columnas = jugada del servidor
Player \ Server
ROCK
PAPER
SCISSORS
ROCK
DRAW (DRAW)
LOSE (PAPER_BEATS_ROCK)
WIN (ROCK_BEATS_SCISSORS)
PAPER
WIN (PAPER_BEATS_ROCK)
DRAW (DRAW)
LOSE (SCISSORS_BEATS_PAPER)
SCISSORS
LOSE (ROCK_BEATS_SCISSORS)
WIN (SCISSORS_BEATS_PAPER)
DRAW (DRAW)


Mensajes “humanos” (por regla)
DRAW → “Empate: ambos eligieron {move}”


ROCK_BEATS_SCISSORS → “La piedra rompe las tijeras”


PAPER_BEATS_ROCK → “El papel envuelve a la piedra”


SCISSORS_BEATS_PAPER → “Las tijeras cortan el papel”

Casos de prueba mínimos (9 combinaciones)
Para tu reporte / README, puedes listar así:
ROCK vs ROCK → DRAW / DRAW


ROCK vs PAPER → LOSE / PAPER_BEATS_ROCK


ROCK vs SCISSORS → WIN / ROCK_BEATS_SCISSORS


PAPER vs ROCK → WIN / PAPER_BEATS_ROCK


PAPER vs PAPER → DRAW / DRAW


PAPER vs SCISSORS → LOSE / SCISSORS_BEATS_PAPER


SCISSORS vs ROCK → LOSE / ROCK_BEATS_SCISSORS


SCISSORS vs PAPER → WIN / SCISSORS_BEATS_PAPER


SCISSORS vs SCISSORS → DRAW / DRAW

