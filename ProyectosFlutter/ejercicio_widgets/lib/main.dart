import 'package:flutter/material.dart';
import 'dart:convert' as convert;
import 'package:http/http.dart' as http;
import 'package:bordered_text/bordered_text.dart';

const Color greenByDefault = Color(0xFF607D8B);
var appBarHeight = AppBar().preferredSize.height;

void main() {
  runApp(const MyApp());
}

void navigate(BuildContext context, Widget widget){
  Navigator.of(context).push(MaterialPageRoute(builder: (BuildContext cont){
    return widget;
  }));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Ejercicio Widgets',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const LoginScreen(),
    );
  }
}

class LoginScreen extends StatelessWidget{
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
            body: SingleChildScrollView(
                child: Container(
                    padding: const EdgeInsets.all(20),
                    height: MediaQuery.of(context).size.height - MediaQuery.of(context).padding.top,
                    decoration: const BoxDecoration(
                        image: DecorationImage(
                            image: AssetImage("assets/images/travel.jpg"),
                            opacity: 0.3,
                            repeat: ImageRepeat.repeat
                        )
                    ),
                    child: Center(
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Image.asset("assets/images/login.png", scale: 1.1),
                            const Padding(padding: EdgeInsets.all(10),
                              child: Text("Las comarcas de la comunidad",
                                  style: TextStyle(
                                      fontFamily: 'Love',
                                      fontSize: 40,
                                      color: greenByDefault
                                  ),
                                  textAlign: TextAlign.center
                              ),
                            ),
                            const CustomInputText(hint: "User", isPassword: false,),
                            const CustomInputText(hint: "Password", isPassword: true,),
                            CustomButton(text: "LOG IN",
                                onPressedFunction: () {navigate(context, const RegionScreen());})
                          ],)
                    )
                )
            )
        )
    );
  }}

class CustomButton extends StatelessWidget{
  const CustomButton({super.key, required this.text, required this.onPressedFunction});

  final String text;
  final Function() onPressedFunction;
  
  @override
  Widget build(BuildContext context) {
    return Padding(padding: const EdgeInsets.fromLTRB(10, 40, 0, 10),
      child: ElevatedButton(onPressed: onPressedFunction,
          style: ButtonStyle(
              backgroundColor: const MaterialStatePropertyAll(greenByDefault),
              foregroundColor: const MaterialStatePropertyAll(Colors.white),
              shape: MaterialStatePropertyAll(RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10.0)
              )),
              minimumSize: const MaterialStatePropertyAll(Size(200, 50))
          ),
          child: Text(text))
    );
  }
  
}

class CustomInputText extends StatelessWidget{
  const CustomInputText({super.key, required this.hint, required this.isPassword});
  final String hint;
  final bool isPassword;

  @override
  Widget build(BuildContext context) {
    return Padding(padding: const EdgeInsets.all(20),
      child: TextField(
        decoration: InputDecoration(
          border: const OutlineInputBorder(),
          hintText: hint,
          filled: true,
          fillColor: Colors.white,
        ),
        obscureText: isPassword,
      ),
    );
  }
}

class RegionScreen extends StatefulWidget{
  const RegionScreen({super.key});

  @override
  State<StatefulWidget> createState() => RegionScreenState();
}

class RegionScreenState extends State<RegionScreen>{

  static const baseUrl = "node-comarques-rest-server-production.up.railway.app";
  static const baseApiGet = "/api/comarques/";

  List<dynamic> regions = [];
  bool finished = false;

  List<Widget> _getRegionWidgets(){
    List<Widget> regionWidgets = [];
    for (int i = 0; i < regions.length; i++) {
      regionWidgets.add(CustomRoundedImage(
        imageUrl: regions[i]['img'],
        name: regions[i]['provincia'],
      ));
    }
    return regionWidgets;
  }

  Future getRegions() async{
    final url = Uri.https(baseUrl, baseApiGet);
    var response = await http.get(url);

    if(response.statusCode == 200){
      String body = convert.utf8.decode(response.bodyBytes);
      final bodyJSON = convert.jsonDecode(body) as List;
      setState(() {
        regions.addAll(bodyJSON);
      });
    }
  }

  @override
  void initState() {
    super.initState();
    getRegions();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(child: Scaffold(
        appBar: AppBar(
          backgroundColor: greenByDefault,
        ),
        body: SingleChildScrollView(
            child: Container(
                padding: const EdgeInsets.all(20),
                decoration: const BoxDecoration(
                  image: DecorationImage(
                      image: AssetImage("assets/images/travel.jpg"),
                      opacity: 0.3,
                      repeat: ImageRepeat.repeat
                  ),
                ),
                child: Center(child: Column(children: _getRegionWidgets()))
            )
        )
    ));
  }

}

class CustomRoundedImage extends StatelessWidget{
  const CustomRoundedImage({super.key, required this.imageUrl, required this.name});

  final String imageUrl;
  final String name;

  @override
  Widget build(BuildContext context) {
    return Padding(padding: const EdgeInsets.fromLTRB(0, 0, 0, 20),
      child: GestureDetector(
          onTap: (){navigate(context, RegionsListScreen(regionName: name));},
          child: CircleAvatar(
            radius: 100,
            backgroundImage: NetworkImage(imageUrl),
            child: BorderedText(
              strokeColor: Colors.black,
              strokeWidth: 3.0,
              child: Text(name,
                  style: const TextStyle(
                    color: Colors.white,
                    fontFamily: 'Love',
                    fontSize: 40,
                  )
              ),
            ),
          )
      ),
    );
  }
}

class RegionsListScreen extends StatefulWidget{
  const RegionsListScreen({super.key, required this.regionName});

  final String regionName;

  @override
  State<StatefulWidget> createState() => RegionListScreenState();
}

class RegionListScreenState extends State<RegionsListScreen>{

  static const baseUrl = "node-comarques-rest-server-production.up.railway.app";
  static String baseApiGet = "/api/comarques/";

  List<dynamic> regions = [];
  List<dynamic> data = [];

  List<Widget> _getListWidgets(){
    List<Widget> widgetList = [];
    for(int i = 0; i < data.length; i++){
      widgetList.add(Card(
        clipBehavior: Clip.antiAliasWithSaveLayer,
        child: Stack(
          children: [
            GestureDetector(onTap: (){navigate(context, RegionInfoScreen(data: data[i]));},
              child: Image.network(data[i]['img'], height: 200, width: 400,
                fit: BoxFit.cover,
              ),
            ),
            Positioned(bottom: 10, left: 10,
                child: BorderedText(strokeWidth: 3.0, strokeColor: Colors.black,
                    child: Text(regions[i],
                        style: const TextStyle(
                          fontSize: 20, color: Colors.white, fontFamily: 'Love',
                        )
                    )
                )
            )
          ],
        ),
      ));
    }
    return widgetList;
  }

  Future getRegions() async{
    final url = Uri.https(baseUrl, baseApiGet + widget.regionName);
    var response = await http.get(url);

    if(response.statusCode == 200){
      String body = convert.utf8.decode(response.bodyBytes);
      final bodyJSON = convert.jsonDecode(body) as List;
      setState(() { regions.addAll(bodyJSON); });
      getData();
    }
  }

  Future getData() async{
      for(int i = 0; i < regions.length; i++){
        final url = Uri.https(baseUrl, "${baseApiGet}infoComarca/${regions[i]}");
        var response = await http.get(url);

        if(response.statusCode == 200){
          String body = convert.utf8.decode(response.bodyBytes);
          final bodyJSON = convert.jsonDecode(body);
          setState(() { data.add(bodyJSON); });
        }
      }
  }

  @override
  void initState() {
    super.initState();
    getRegions();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
            appBar: AppBar(
                backgroundColor: greenByDefault,
                title: Text("Comarcas de ${widget.regionName}",
                    style: const TextStyle(fontSize: 24, fontFamily: 'Love')
                )
            ),
            body: SingleChildScrollView(
                child: Container(
                    padding: const EdgeInsets.all(20),
                    decoration: const BoxDecoration(
                        image: DecorationImage(
                            image: AssetImage("assets/images/travel.jpg"),
                            opacity: 0.3, repeat: ImageRepeat.repeat
                        )
                    ),
                    child: Center(child: Column(children: _getListWidgets()))
                )
            )
        )
    );
  }
}

class RegionInfoScreen extends StatelessWidget{
  const RegionInfoScreen({super.key, required this.data});

  final dynamic data;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
            appBar: AppBar(
                backgroundColor: greenByDefault,
                title: Text(data['comarca'],
                    style: const TextStyle(fontSize: 24, fontFamily: 'Love')
                )
            ),
            body: SingleChildScrollView(
              child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Image.network(data['img']),
                    Padding(
                        padding: const EdgeInsets.all(10),
                        child: Text(data['comarca'],
                            style: const TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold
                            )
                        )
                    ),
                    Padding(
                        padding: const EdgeInsets.all(10),
                        child: Text("Capital: ${data['capital']}",
                            style: const TextStyle(fontSize: 18)
                        )
                    ),
                    Padding(
                        padding: const EdgeInsets.fromLTRB(10, 5, 5, 10),
                        child: Text(data['desc'], style: const TextStyle(fontSize: 16))
                    ),
                    Positioned(left: 10, top: 10,
                        child: CustomButton(
                            text: "Weather",
                            onPressedFunction: (){navigate(context, WeatherInfoScreen(data: data));})
                    )
                  ]
              ),
            )
        )
    );
  }

}

class WeatherInfoScreen extends StatelessWidget{
  const WeatherInfoScreen({super.key, required this.data});

  final dynamic data;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
            appBar: AppBar(
                backgroundColor: greenByDefault,
                title: Text(data['comarca'],
                    style: const TextStyle(fontSize: 24, fontFamily: 'Love')
                )
            ),
            body: SingleChildScrollView(
              child: Column(
                  children: [
                    Padding(padding: const EdgeInsets.all(20),
                        child: Image.asset("assets/images/weatherIcon.png")
                    ),
                    const Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Image(
                            image: AssetImage("assets/images/barometerIcon.png"),
                            width: 65,
                          ),
                          Text("5.4º", style: TextStyle(fontSize: 24))
                        ]
                    ),
                    const Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Padding(padding: EdgeInsets.all(10),
                              child: Image(
                                image: AssetImage("assets/images/windIcon.png"),
                                width: 40,
                              )),
                          Padding(padding: EdgeInsets.all(10),
                              child: Text("9.4km/h", style: TextStyle(fontSize: 18))
                          ),
                          Padding(padding: EdgeInsets.all(10),
                              child: Text("Poniente", style: TextStyle(fontSize: 18))
                          ),
                          Icon(Icons.arrow_back)
                        ]
                    ),
                    Padding(padding: const EdgeInsets.all(15),
                        child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text("Población:    ${data['poblacio']}",
                                style: const TextStyle(fontSize: 16),
                              ),
                              Text("Latitud:    ${data['latitud']}",
                                style: const TextStyle(fontSize: 16),
                              ),
                              Text("Longitud:    ${data['longitud']}",
                                style: const TextStyle(fontSize: 16),
                              )
                            ]
                        )
                    )
                  ]
              )
            )
        )
    );
  }

}