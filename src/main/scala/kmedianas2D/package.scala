package object kmedianas2D {
  import common._
  import scala.collection.parallel.CollectionConverters._
  import scala.annotation.tailrec
  import scala.util.Random
  import scala.collection.{Map, Seq}


  class Punto(val x: Double, val y:Double) {
    private def cuadrado(v:Double): Double = v*v

    def distanciaAlCuadrado(that: Punto): Double =
      cuadrado(that.x-x) + cuadrado(that.y-y)

    private def round(v:Double):Double = (v*100).toInt / 100.0

    override def toString = s"(${round(x)}, ${round(y)})"
  }

  def hallarPuntoMasCercano(p:Punto, medianas: Seq[Punto]):Punto ={
    assert(medianas.nonEmpty)
    medianas.map(pto=>(pto,p.distanciaAlCuadrado(pto))).sortWith((a, b)=>(a._2<b._2)).head._1
  }

  def combinarMapas(mapas: List[Map[Punto, Seq[Punto]]], medianas: Seq[Punto]): Map[Punto, Seq[Punto]] = {
    mapas match {
      case Nil => Map.empty[Punto, Seq[Punto]]
      case _ =>{
        (for{
          med <- medianas
        } yield {
          val puntos = mapas.flatMap(_.getOrElse(med, Seq()))
          (med, puntos)
        }).toMap
      }
    }
  }

  def clasificarSeq(puntos: Seq[Punto], medianas:Seq[Punto]): Map[Punto, Seq[Punto]] ={
    puntos.groupBy(hallarPuntoMasCercano(_,medianas))
  }

  def clasificarPar(umb:Int)(puntos: Seq[Punto], medianas:Seq[Punto]): Map[Punto, Seq[Punto]] ={
    if(puntos.length <= umb) puntos.groupBy(hallarPuntoMasCercano(_,medianas))
    else {
      val breakPoint = puntos.length/2
      val (p1, p2) = parallel(clasificarPar(umb)(puntos.slice(0,breakPoint), medianas),
                              clasificarPar(umb)(puntos.slice(breakPoint, puntos.length), medianas))

    combinarMapas(List(p1,p2), medianas)
    }
  }


  def actualizarSeq(clasif: Map[Punto, Seq[Punto]], medianasViejas: Seq[Punto]): Seq[Punto] = {
    for(medianaVieja <- medianasViejas)
      yield calculePromedioSeq(medianaVieja, clasif.getOrElse(medianaVieja,Seq()))
  }

  def actualizarPar(clasif: Map[Punto, Seq[Punto]], medianasViejas: Seq[Punto]): Seq[Punto] = {
    val tasks = for(medianaVieja <- medianasViejas)
      yield task(calculePromedioPar(medianaVieja, clasif.getOrElse(medianaVieja,Seq())))

    for(t <- tasks) yield t.join()
  }

  def calculePromedioSeq(medianaVieja: Punto, puntos: Seq[Punto]): Punto ={
    if(puntos.isEmpty) medianaVieja
    else {
      new Punto(puntos.map(p=>p.x).sum/puntos.length, puntos.map(p=>p.y).sum/puntos.length)
    }
  }

  def calculePromedioPar(medianaVieja: Punto, puntos: Seq[Punto]): Punto ={
    if(puntos.isEmpty) medianaVieja
    else {
      val puntosPar = puntos.par
      new Punto(puntosPar.map(p=>p.x).sum/puntos.length, puntosPar.map(p=>p.y).sum/puntos.length)
    }
  }

  def hayConvergenciaSeq(eta: Double, medianasViejas: Seq[Punto],
                         medianasNuevas:Seq[Punto]): Boolean ={
    @tailrec
    def convergenciaIter(medianasViejasIter: Seq[Punto],
                         medianasNuevasIter: Seq[Punto]): Boolean = {
      (medianasViejasIter, medianasNuevasIter) match {
        case (Nil, _) => true
        case (_, Nil) => true
        case (a, b) => {
          if(a.head.distanciaAlCuadrado(b.head) > eta) false
          else convergenciaIter(a.tail, b.tail)
        }
      }
    }
    convergenciaIter(medianasViejas, medianasNuevas)
  }

  def hayConvergenciaPar(eta: Double, medianasViejas: Seq[Punto],
                         medianasNuevas:Seq[Punto]): Boolean ={
    val umb = 5
    if(medianasViejas.length <= umb) hayConvergenciaSeq(eta, medianasViejas, medianasNuevas)
    else {
      val n = medianasViejas.length
      val mitad = n/2
      val (b1, b2) = parallel(hayConvergenciaPar(eta, medianasViejas.slice(0, mitad),medianasNuevas.slice(0, mitad)),
        hayConvergenciaPar(eta, medianasViejas.slice(mitad, n),medianasNuevas.slice(mitad, n)))

      b1 && b2
    }
  }

  @tailrec
  final def kMedianasSeq(puntos: Seq[Punto], medianas: Seq[Punto], eta: Double): Seq[Punto] = {
    val mapaClusters = clasificarSeq(puntos,medianas)
    val medianasNuevas = actualizarSeq(mapaClusters, medianas)
    if(hayConvergenciaSeq(eta, medianas, medianasNuevas)) medianasNuevas
    else kMedianasSeq(puntos, medianasNuevas, eta)
  }

  @tailrec
  final def kMedianasPar(puntos: Seq[Punto], medianas: Seq[Punto], eta: Double): Seq[Punto] = {
    val umb = 10
    val mapaClusters = clasificarPar(umb)(puntos,medianas)
    val medianasNuevas = actualizarPar(mapaClusters, medianas)
    if(hayConvergenciaPar(eta, medianas, medianasNuevas)) medianasNuevas
    else kMedianasPar(puntos, medianasNuevas, eta)
  }

  def generarPuntos(k: Int, num: Int): Seq[Punto] = {
    val randx = new Random
    val randy = new Random
    (0 until num)
      .map({ i =>
        val x = ((i + 1) % k) * 1.0 / k + randx.nextDouble() * 0.5
        val y = ((i + 5) % k) * 1.0 / k + randy.nextDouble() * 0.5
        new Punto(x, y)
      })
  }

  def inicializarMedianas(k: Int, puntos: Seq[Punto]): Seq[Punto] = {
    Random.shuffle(puntos).take(k)
  }

}
