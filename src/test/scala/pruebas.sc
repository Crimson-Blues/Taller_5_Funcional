import Benchmark ._
import kmedianas2D ._


//-------Creación de gráficas---------


//-----Cantidades de puntos preestablecidos-----
val puntos10_2 = generarPuntos(2, 10).toSeq
val puntos100_8 = generarPuntos(16,100).toSeq
val puntos1000_32 = generarPuntos(32,1000).toSeq
val puntos10000_128 = generarPuntos(128, 10000).toSeq
val puntos100000_4 = generarPuntos(4, 100000).toSeq
val puntos10000_5000 = generarPuntos(5000, 10000).toSeq
val puntos100000_256 = generarPuntos(256, 100000).toSeq

//----Gráficas------
//probarKmedianas(puntos10_2,2,0.01)
//probarKmedianas(puntos100_8,8,0.01)
//probarKmedianas(puntos1000_32,32,0.1)
//probarKmedianas(puntos10000_128,128,0.1)


//--------Pruebas paralelismo de tareas-------
//---------Medianas inicializadas------
val medianas10_2 = inicializarMedianas(2,puntos10_2)
val medianas100_8 = inicializarMedianas(8, puntos100_8)
val medianas1000_32 = inicializarMedianas(32, puntos1000_32)
val medianas10000_128 = inicializarMedianas(128, puntos10000_128)
val medianas100000_4 = inicializarMedianas(4, puntos100000_4)
val medianas10000_5000 = inicializarMedianas(5000, puntos10000_5000)

//--------Clasificar Seq y Par--------
//Seq:
val clasificarSeq1 = tiempoDe(clasificarSeq(puntos10_2, medianas10_2))
val clasificarSeq2 = tiempoDe(clasificarSeq(puntos100_8, medianas100_8))
val clasificarSeq3 = tiempoDe(clasificarSeq(puntos1000_32, medianas1000_32))
val clasificarSeq4 = tiempoDe(clasificarSeq(puntos10000_128, medianas10000_128))

//Par:
val clasificarPar1 = tiempoDe(clasificarPar(1)(puntos10_2, medianas10_2))
val clasificarPar2 = tiempoDe(clasificarPar(10)(puntos100_8, medianas100_8))
val clasificarPar3 = tiempoDe(clasificarPar(100)(puntos1000_32, medianas1000_32))
val clasificarPar4 = tiempoDe(clasificarPar(1000)(puntos10000_128, medianas10000_128))

//Aceleraciones:
val accelClasif1 = clasificarSeq1.value/clasificarPar1.value
val accelClasif2 = clasificarSeq2.value/clasificarPar2.value
val accelClasif3 = clasificarSeq3.value/clasificarPar3.value
val accelClasif4 = clasificarSeq4.value/clasificarPar4.value

//--------Actualizar Seq y Par--------

//-------- Clasificación de puntos--------
val Mapa1 = clasificarSeq(puntos10_2, medianas10_2)
val Mapa2 = clasificarSeq(puntos100_8, medianas100_8)
val Mapa3 = clasificarSeq(puntos1000_32, medianas1000_32)
val Mapa4 = clasificarSeq(puntos10000_128, medianas10000_128)
val Mapa5 = clasificarSeq(puntos100000_4, medianas100000_4)
val Mapa6 = clasificarSeq(puntos10000_5000, medianas10000_5000)

//Seq:
val actualizarSeq1 = tiempoDe(actualizarSeq(Mapa1, medianas10_2))
val actualizarSeq2 = tiempoDe(actualizarSeq(Mapa2, medianas100_8))
val actualizarSeq3 = tiempoDe(actualizarSeq(Mapa3, medianas1000_32))
val actualizarSeq4 = tiempoDe(actualizarSeq(Mapa4, medianas10000_128))
val actualizarSeq5 = tiempoDe(actualizarSeq(Mapa5, medianas100000_4))

//Par:
val actualizarPar1 = tiempoDe(actualizarPar(Mapa1, medianas10_2))
val actualizarPar2 = tiempoDe(actualizarPar(Mapa2, medianas100_8))
val actualizarPar3 = tiempoDe(actualizarPar(Mapa3, medianas1000_32))
val actualizarPar4 = tiempoDe(actualizarPar(Mapa4, medianas10000_128))
val actualizarPar5 = tiempoDe(actualizarPar(Mapa5, medianas100000_4))

//Aceleraciones:
val accelAct1 = actualizarSeq1.value/actualizarPar1.value
val accelAct2 = actualizarSeq2.value/actualizarPar2.value
val accelAct3 = actualizarSeq3.value/actualizarPar3.value
val accelAct4 = actualizarSeq4.value/actualizarPar4.value
val accelAct5 = actualizarSeq5.value/actualizarPar5.value

//--------hayConvergencia Seq y Par--------
//-------Medianas actualizadas-------
val medianasNuevas1 = actualizarSeq(Mapa1, medianas10_2)
val medianasNuevas2 = actualizarSeq(Mapa2, medianas100_8)
val medianasNuevas3 = actualizarSeq(Mapa3, medianas1000_32)
val medianasNuevas4 = actualizarSeq(Mapa4, medianas10000_128)
val medianasNuevas5 = actualizarSeq(Mapa6, medianas10000_5000)

//Seq:
val hayConvergenciaSeq1 = tiempoDe(hayConvergenciaSeq(0.01, medianas10_2, medianasNuevas1))
val hayConvergenciaSeq2 = tiempoDe(hayConvergenciaSeq(0.01, medianas100_8,medianasNuevas2))
val hayConvergenciaSeq3 = tiempoDe(hayConvergenciaSeq(0.01, medianas1000_32, medianasNuevas3))
val hayConvergenciaSeq4 = tiempoDe(hayConvergenciaSeq(0.01, medianas10000_128, medianasNuevas4))
val hayConvergenciaSeq5 = tiempoDe(hayConvergenciaSeq(0.01, medianas10000_5000, medianasNuevas5))

//Par:
val hayConvergenciaPar1 = tiempoDe(hayConvergenciaPar(0.01, medianas10_2, medianasNuevas1))
val hayConvergenciaPar2 = tiempoDe(hayConvergenciaPar(0.01, medianas100_8,medianasNuevas2))
val hayConvergenciaPar3 = tiempoDe(hayConvergenciaPar(0.01, medianas1000_32, medianasNuevas3))
val hayConvergenciaPar4 = tiempoDe(hayConvergenciaPar(0.01, medianas10000_128, medianasNuevas4))
val hayConvergenciaPar5 = tiempoDe(hayConvergenciaPar(0.01, medianas10000_5000, medianasNuevas5))

//Aceleraciones:
val accelConv1 = hayConvergenciaSeq1.value/hayConvergenciaPar1.value
val accelConv2 = hayConvergenciaSeq2.value/hayConvergenciaPar2.value
val accelConv3 = hayConvergenciaSeq3.value/hayConvergenciaPar3.value
val accelConv4 = hayConvergenciaSeq4.value/hayConvergenciaPar4.value
val accelConv5 = hayConvergenciaSeq5.value/hayConvergenciaPar5.value

//--------Pruebas paralelismo de datos--------

//-------Mediana de prueba----------
val origen = new Punto(0.0,0.0)

//Seq:
val calculePromedioSeq1 = tiempoDe(calculePromedioSeq(origen, puntos10_2))
val calculePromedioSeq2 = tiempoDe(calculePromedioSeq(origen, puntos100_8))
val calculePromedioSeq3 = tiempoDe(calculePromedioSeq(origen, puntos1000_32))
val calculePromedioSeq4 = tiempoDe(calculePromedioSeq(origen, puntos10000_128))
val calculePromedioSeq5 = tiempoDe(calculePromedioSeq(origen, puntos100000_256))

//Par:
val calculePromedioPar1 = tiempoDe(calculePromedioPar(origen, puntos10_2))
val calculePromedioPar2 = tiempoDe(calculePromedioPar(origen, puntos100_8))
val calculePromedioPar3 = tiempoDe(calculePromedioPar(origen, puntos1000_32))
val calculePromedioPar4 = tiempoDe(calculePromedioPar(origen, puntos10000_128))
val calculePromedioPar5 = tiempoDe(calculePromedioPar(origen, puntos100000_256))

//Aceleraciones:
val accelProm1 = calculePromedioSeq1.value/calculePromedioPar1.value
val accelProm2 = calculePromedioSeq2.value/calculePromedioPar2.value
val accelProm3 = calculePromedioSeq3.value/calculePromedioPar3.value
val accelProm4 = calculePromedioSeq4.value/calculePromedioPar4.value
val accelProm5 = calculePromedioSeq5.value/calculePromedioPar5.value


// --------- tiemposKMedianas - Tablas ---------
tiemposKmedianas(generarPuntos(4, 256).toSeq, 4, 0.01)
tiemposKmedianas(generarPuntos(16, 256).toSeq, 16, 0.01)

tiemposKmedianas(generarPuntos(16, 1024).toSeq, 16, 0.01)
tiemposKmedianas(generarPuntos(64, 1024).toSeq, 64, 0.01)

tiemposKmedianas(generarPuntos(16, 4096).toSeq, 16, 0.01)
tiemposKmedianas(generarPuntos(64, 4096).toSeq, 64, 0.01)
tiemposKmedianas(generarPuntos(128, 4096).toSeq, 128, 0.01)

tiemposKmedianas(generarPuntos(64, 16384).toSeq, 64, 0.01)
tiemposKmedianas(generarPuntos(128, 16384).toSeq, 128, 0.01)
tiemposKmedianas(generarPuntos(256, 16384).toSeq, 256, 0.01)

tiemposKmedianas(generarPuntos(64, 65536).toSeq, 64, 0.01)
tiemposKmedianas(generarPuntos(128, 65536).toSeq, 128, 0.01)
tiemposKmedianas(generarPuntos(256, 65536).toSeq, 256, 0.01)


// --------- Promedio de Secuencias de Puntos ---------
/* AVISO: NO PROBAR TOMA APROX. 30 MINS
val eta = 0.01
val numRepeticiones = 5

val configuraciones: Vector[(Int, Int)] = Vector((256, 4), (256, 16), (1024, 16), (1024, 64),
  (4096, 16), (4096, 64), (4096, 128), (16384, 64), (16384, 128), (16384, 256), (65536, 64),
  (65536, 128), (65536, 256))

println("| #Puntos (n) | #Clusters (k) | Tiempo Sec (ms) | Tiempo Par (ms) | Aceleración |")
println("|-------------|---------------|-----------------|-----------------|-------------|")

for (i <- configuraciones.indices) {
  val (n_val, k_val) = configuraciones(i)
  val resultados = for (_ <- 0 until numRepeticiones) yield {
    val puntos = generarPuntos(k_val, n_val).toSeq
    val (tiempoSeq, tiempoPar, _) = tiemposKmedianas(puntos, k_val, eta)
    (tiempoSeq.value, tiempoPar.value)
  }
  val promSeq = resultados.map(_._1).sum / numRepeticiones
  val promPar = resultados.map(_._2).sum / numRepeticiones
  val promAcel = promSeq / promPar

  printf("| %-12d | %-13d | %-16.4f | %-15.4f | %-10.4fx |\n",
    n_val, k_val, promSeq, promPar, promAcel)
}
 */