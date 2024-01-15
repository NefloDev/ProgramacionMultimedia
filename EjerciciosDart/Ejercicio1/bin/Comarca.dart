import 'package:intl/intl.dart';

class Comarca{
  final String nombre;
  final String capital;
  final int poblacion;
  final String imagen;
  final String descripcion;
  final double latitud;
  final double longitud;

  Comarca(this.nombre, this.capital, this.poblacion, this.imagen, this.descripcion,
      this.latitud, this.longitud);

  factory Comarca.fromJson(dynamic json){
    return Comarca(json['comarca'], json['capital'],
        int.parse(json['poblacio'].toString().replaceAll('.', '')), json['img'],
        json['desc'], json['latitud'], json['longitud']);
  }

  String toString(){
    final format = NumberFormat("###,###,###,###");
    return "Nombre: ${nombre}\n"
        "Capital: ${capital}\n"
        "Población: ${format.format(poblacion)}\n"
        "Imagen: ${Uri.parse(imagen)}\n"
        "Descripción: ${descripcion}\n"
        "Coordenadas: (${latitud}, ${longitud})";
  }
}