import 'dart:convert' as convert;
import 'package:http/http.dart' as http;

import 'Comarca.dart';

const BASE_URL = "node-comarques-rest-server-production.up.railway.app";
const BASE_API_GET = "/api/comarques/";

void main(List<String> args){
  if(args.length >= 2){
    if(args[0] == 'comarcas'){
      showComarcas(args.sublist(1, args.length).join(" "));
    }else if(args[0] == 'infocomarca'){
      showInfoComarca(args.sublist(1, args.length).join(" "));
    }else{
      print("\x1B[31m${"Orden desconocida"}\x1B[0m");
      print("Comandos:\n"
          "comarcas <Provincia> - Devuelve una lista de comarcas pertenecientes a la provincia\n"
          "infocomarca <Comarca> - Devuelve la información acerca de una comarca");
    }
  }else{
    print("\x1B[31m${"Número de argumentos incorrecto"}\x1B[0m");
    print("Comandos:\n"
        "comarcas <Provincia> - Devuelve una lista de comarcas pertenecientes a la provincia\n"
        "infocomarca <Comarca> - Devuelve la información acerca de una comarca");
  }
}

void showComarcas(String provincia){
  final searchProvincia = BASE_API_GET + provincia;
  final url = Uri.https(BASE_URL, searchProvincia);
  var response = http.get(url);
  response.then((data) {
    if(data.statusCode==200){
      String body = convert.utf8.decode(data.bodyBytes);
      final bodyJSON = convert.jsonDecode(body) as List;
      bodyJSON.forEach((element) {print('${element}');});
    }else{
      print("\x1B[31m${"No se reconoce la provincia"}\x1B[0m");
    }
  });
}

void showInfoComarca(String comarca){
  final searchComarca = "${BASE_API_GET}infocomarca/${comarca}";
  final url = Uri.https(BASE_URL, searchComarca);
  var response = http.get(url);
  response.then((data) {
    if(data.statusCode==200){
      String body = convert.utf8.decode(data.bodyBytes);
      final bodyJSON = convert.jsonDecode(body);
      final region = Comarca.fromJson(bodyJSON);
      print(region.toString());
    }else{
      print("\x1B[31m${"No se reconoce la comarca"}\x1B[0m");
    }
  });
}