import Benchmark ._
import kmedianas2D ._

val puntos = generarPuntos(2, 10).toSeq
val medianas = inicializarMedianas(2, puntos)
//kMedianasSeq(puntos, medianas, 0.01)
//kMedianasPar(puntos, medianas, 0.01)
tiemposKmedianas(puntos, 2, 0.01)
