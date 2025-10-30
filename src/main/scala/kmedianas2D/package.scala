package object kmedianas2D {
  import common._

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
}
