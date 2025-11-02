import Benchmark ._
import kmedianas2D ._

val puntos = generarPuntos(3, 16).toSeq
val medianas = inicializarMedianas(3, puntos)
kMedianasSeq(puntos, medianas, 0.01)
kMedianasPar(puntos, medianas, 0.01)
tiemposKmedianas(puntos, 3, 0.01)
probarKmedianas(puntos, 3, 0.01)

