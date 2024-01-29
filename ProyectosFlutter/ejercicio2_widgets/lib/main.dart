import 'package:flutter/material.dart';
import 'dart:convert' as convert;
import 'package:http/http.dart' as http;
import 'package:bordered_text/bordered_text.dart';

const Color greenByDefault = Color(0xFF607D8B);
var appBarHeight = AppBar().preferredSize.height;
Map<String, String> users = {"admin": "admin"};

void main() {
  runApp(const MyApp());
}

void navigate(BuildContext context, Widget widget) {
  Navigator.of(context).push(MaterialPageRoute(builder: (BuildContext cont) {
    return widget;
  }));
}

int signIn(String username, String pass){
  if(username.isEmpty || pass.isEmpty){
    return 1;
  }
  if(users.containsKey(username)){
    return 2;
  }
  users.putIfAbsent(username, () => pass);
  return 0;
}

int login(String username, String pass, BuildContext context, Widget widget) {
  if (!users.containsKey(username)) {
    return 1;
  }
  if (!(users[username] == pass)) {
    return 2;
  }
  navigate(context, widget);
  return 0;
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Ejercicio Widgets',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        useMaterial3: true,
      ),
      home: const LoginScreen(),
    );
  }
}

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<StatefulWidget> createState() => LoginScreenState();
}

class LoginScreenState extends State<LoginScreen> {
  TextEditingController username = TextEditingController();
  TextEditingController password = TextEditingController();

  String warning = "";

  void logInUser() {
    setState(() {
      switch (login(username.value.text, password.value.text, context,
          const RegionScreen())) {
        case 1:
          warning = "Username not registered";
          break;
        case 2:
          warning = "Password incorrect";
          password.text = "";
        default:
          warning = "";
          username.text = "";
          password.text = "";
      }
    });
  }

  void showSignUpAlert(){
    showDialog(
        context: context,
        builder: (context) {
          String registerWarning = "";
          String registerSuccess = "";
          return StatefulBuilder(builder: (context, setState){
            return AlertDialog(
                shape: const RoundedRectangleBorder(
                    borderRadius: BorderRadius.all(
                        Radius.circular(20)
                    )
                ),
                title: const Text("Register",
                    style: TextStyle(fontSize: 40),
                    textAlign: TextAlign.center
                ),
                backgroundColor: Colors.white,
                contentPadding: const EdgeInsets.only(top: 20),
                content: Wrap(
                  clipBehavior: Clip.hardEdge,
                    alignment: WrapAlignment.center,
                    children: [
                      Text(registerSuccess,
                        style: const TextStyle(
                            color: Colors.green,
                            fontWeight: FontWeight.bold
                        ),
                      ),
                      Text(registerWarning,
                        style: const TextStyle(
                            color: Colors.red,
                            fontWeight: FontWeight.bold
                        ),
                      ),
                      Row(
                        children: [
                          const Padding(padding: EdgeInsets.only(left: 20),
                              child: Icon(Icons.person)),
                          Flexible(child: CustomInputText(hint: "Username", isPassword: false, controller: username))
                        ],
                      ),
                      Row(
                          children: [
                            const Padding(padding: EdgeInsets.only(left: 20),
                                child: Icon(Icons.password)),
                            Flexible(child: CustomInputText(hint: "Password", isPassword: true, controller: password))
                          ]
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: [
                          Padding(
                              padding: const EdgeInsets.all(20),
                              child: ElevatedButton(
                                  style: const ButtonStyle(
                                    backgroundColor: MaterialStatePropertyAll(Colors.transparent),
                                    shadowColor: MaterialStatePropertyAll(Colors.transparent),
                                    surfaceTintColor: MaterialStatePropertyAll(Colors.transparent),
                                  ),
                                  onPressed: (){
                                    setState(() {
                                      switch (signIn(username.text, password.text)){
                                        case 1:
                                          registerWarning = "Missing Data";
                                          registerSuccess = "";
                                          break;
                                        case 2:
                                          registerWarning = "Username already exists";
                                          registerSuccess = "";
                                          break;
                                        default:
                                          registerWarning = "";
                                          registerSuccess = "User succesfully registered";
                                      }
                                      username.text = "";
                                      password.text = "";
                                    });
                                  },
                                  child: const Text("Register", style: TextStyle(color: Colors.blue))
                              )
                          ),
                          Padding(padding: const EdgeInsets.all(20),
                            child: ElevatedButton(
                                style: const ButtonStyle(
                                backgroundColor: MaterialStatePropertyAll(Colors.transparent),
                                shadowColor: MaterialStatePropertyAll(Colors.transparent),
                                surfaceTintColor: MaterialStatePropertyAll(Colors.transparent),
                                ),
                                onPressed: (){
                                  Navigator.pop(context);
                                },
                                child: const Text("Cancel",
                                style: TextStyle(color: Colors.red))
                            )
                          )
                        ],
                      )
                    ]
                )
            );
          });
        });
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
            body: Container(
                    padding: const EdgeInsets.symmetric(vertical: 0, horizontal: 20),
                    height: MediaQuery.of(context).size.height -
                        MediaQuery.of(context).padding.top,
                    decoration: const BoxDecoration(
                        image: DecorationImage(
                            image: AssetImage("assets/images/travel.jpg"),
                            opacity: 0.3,
                            repeat: ImageRepeat.repeat)
                    ),
                    child: Center(
                      child: SingleChildScrollView(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Image.asset("assets/images/login.png", scale: 1.1),
                              const Padding(
                                padding: EdgeInsets.all(10),
                                child: Text("Las comarcas de la comunidad",
                                    style: TextStyle(
                                        fontFamily: 'Love',
                                        fontSize: 40,
                                        color: greenByDefault),
                                    textAlign: TextAlign.center),
                              ),
                              Text(warning,
                                  style: const TextStyle(
                                      color: Colors.red,
                                      fontWeight: FontWeight.bold)),
                              CustomInputText(
                                  hint: "User",
                                  isPassword: false,
                                  controller: username),
                              CustomInputText(
                                hint: "Password",
                                isPassword: true,
                                controller: password,
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceAround,
                                children: [
                                  CustomButton(
                                      text: "Log in", onPressedFunction: logInUser),
                                  CustomButton(
                                      text: "Register",
                                      onPressedFunction: () {
                                        showSignUpAlert();
                                      })
                                ],
                              )
                            ],
                          )
                      )
                    )
                )
            )
    );
  }
}

class CustomButton extends StatelessWidget {
  const CustomButton(
      {super.key, required this.text, required this.onPressedFunction});

  final String text;
  final Function() onPressedFunction;

  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.fromLTRB(10, 40, 10, 10),
        child: ElevatedButton(
            onPressed: onPressedFunction,
            style: ButtonStyle(
                backgroundColor: const MaterialStatePropertyAll(Colors.lightBlue),
                foregroundColor: const MaterialStatePropertyAll(Colors.white),
                shape: MaterialStatePropertyAll(RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10.0)))),
            child: Text(text)));
  }
}

class CustomInputText extends StatelessWidget {
  const CustomInputText(
      {super.key,
      required this.hint,
      required this.isPassword,
      required this.controller});
  final String hint;
  final bool isPassword;
  final TextEditingController controller;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20),
      child: TextField(
        controller: controller,
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

class RegionScreen extends StatefulWidget {
  const RegionScreen({super.key});

  @override
  State<StatefulWidget> createState() => RegionScreenState();
}

class RegionScreenState extends State<RegionScreen> {
  static const baseUrl = "node-comarques-rest-server-production.up.railway.app";
  static const baseApiGet = "/api/comarques/";

  List<dynamic> regions = [];
  bool finished = false;

  List<Widget> _getRegionWidgets() {
    List<Widget> regionWidgets = [];
    for (int i = 0; i < regions.length; i++) {
      regionWidgets.add(CustomRoundedImage(
        imageUrl: regions[i]['img'],
        name: regions[i]['provincia'],
      ));
    }
    return regionWidgets;
  }

  Future getRegions() async {
    final url = Uri.https(baseUrl, baseApiGet);
    var response = await http.get(url);

    if (response.statusCode == 200) {
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
    return SafeArea(
        child: Scaffold(
            appBar: AppBar(
              backgroundColor: greenByDefault,
            ),
            body: Container(
                height: MediaQuery.of(context).size.height -
                    MediaQuery.of(context).padding.top,
                    padding: const EdgeInsets.all(20),
                    decoration: const BoxDecoration(
                      image: DecorationImage(
                          image: AssetImage("assets/images/travel.jpg"),
                          opacity: 0.3,
                          repeat: ImageRepeat.repeat),
                    ),
                    child: SingleChildScrollView(
                      child: Center(
                          child: Column(children: _getRegionWidgets()
                          )
                      )
                    )
                )
        )
    );
  }
}

class CustomRoundedImage extends StatelessWidget {
  const CustomRoundedImage(
      {super.key, required this.imageUrl, required this.name});

  final String imageUrl;
  final String name;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(0, 0, 0, 20),
      child: GestureDetector(
          onTap: () {
            navigate(context, RegionsListScreen(regionName: name));
          },
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
                  )),
            ),
          )),
    );
  }
}

class RegionsListScreen extends StatefulWidget {
  const RegionsListScreen({super.key, required this.regionName});

  final String regionName;

  @override
  State<StatefulWidget> createState() => RegionListScreenState();
}

class RegionListScreenState extends State<RegionsListScreen> {
  static const baseUrl = "node-comarques-rest-server-production.up.railway.app";
  static String baseApiGet = "/api/comarques/";

  List<dynamic> regions = [];
  List<dynamic> data = [];

  List<Widget> _getListWidgets() {
    List<Widget> widgetList = [];
    for (int i = 0; i < data.length; i++) {
      widgetList.add(Card(
        clipBehavior: Clip.antiAliasWithSaveLayer,
        child: Stack(
          children: [
            GestureDetector(
              onTap: () {
                navigate(context, RegionInfoScreen(data: data[i]));
              },
              child: Image.network(
                data[i]['img'],
                height: 200,
                width: 400,
                fit: BoxFit.cover,
              ),
            ),
            Positioned(
                bottom: 10,
                left: 10,
                child: BorderedText(
                    strokeWidth: 3.0,
                    strokeColor: Colors.black,
                    child: Text(regions[i],
                        style: const TextStyle(
                          fontSize: 20,
                          color: Colors.white,
                          fontFamily: 'Love',
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

  Future getRegions() async {
    final url = Uri.https(baseUrl, baseApiGet + widget.regionName);
    var response = await http.get(url);

    if (response.statusCode == 200) {
      String body = convert.utf8.decode(response.bodyBytes);
      final bodyJSON = convert.jsonDecode(body) as List;
      setState(() {
        regions.addAll(bodyJSON);
      });
      getData();
    }
  }

  Future getData() async {
    for (int i = 0; i < regions.length; i++) {
      final url = Uri.https(baseUrl, "${baseApiGet}infoComarca/${regions[i]}");
      var response = await http.get(url);

      if (response.statusCode == 200) {
        String body = convert.utf8.decode(response.bodyBytes);
        final bodyJSON = convert.jsonDecode(body);
        setState(() {
          data.add(bodyJSON);
        });
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
                    style: const TextStyle(fontSize: 24, fontFamily: 'Love'))),
            body: Container(
                padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
                height: MediaQuery.of(context).size.height -
                    MediaQuery.of(context).padding.top,
                    decoration: const BoxDecoration(
                        image: DecorationImage(
                            image: AssetImage("assets/images/travel.jpg"),
                            opacity: 0.3,
                            repeat: ImageRepeat.repeat)),
                    child: Center(
                        child: SingleChildScrollView(
                            child: Column(children: _getListWidgets())
                        )
                    )
                )
        )
    );
  }
}

class RegionInfoScreen extends StatelessWidget {
  const RegionInfoScreen({super.key, required this.data});

  final dynamic data;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
            appBar: AppBar(
                backgroundColor: greenByDefault,
                title: Text(data['comarca'],
                    style: const TextStyle(fontSize: 24, fontFamily: 'Love'))),
            body: SingleChildScrollView(
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Image.network(data['img']),
                      Padding(
                          padding: const EdgeInsets.all(10),
                          child: Text(data['comarca'],
                              style: const TextStyle(
                                  fontSize: 20, fontWeight: FontWeight.bold))),
                      Padding(
                          padding: const EdgeInsets.all(10),
                          child: Text("Capital: ${data['capital']}",
                              style: const TextStyle(fontSize: 18))),
                      Padding(
                          padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
                          child: Text(data['desc'],
                              textAlign: TextAlign.justify,
                              style: const TextStyle(fontSize: 16))),
                      CustomButton(
                          text: "Weather",
                          onPressedFunction: () {
                            navigate(context, WeatherInfoScreen(data: data));
                          })
                    ]),
              )
            ));
  }
}

class WeatherInfoScreen extends StatelessWidget {
  const WeatherInfoScreen({super.key, required this.data});

  final dynamic data;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
            appBar: AppBar(
                backgroundColor: greenByDefault,
                title: Text(data['comarca'],
                    style: const TextStyle(fontSize: 24, fontFamily: 'Love'))),
            body: SingleChildScrollView(
                child: Column(children: [
              Padding(
                  padding: const EdgeInsets.all(20),
                  child: Image.asset("assets/images/weatherIcon.png")),
              const Row(mainAxisAlignment: MainAxisAlignment.center, children: [
                Image(
                  image: AssetImage("assets/images/barometerIcon.png"),
                  width: 65,
                ),
                Text("5.4º", style: TextStyle(fontSize: 24))
              ]),
              const Row(mainAxisAlignment: MainAxisAlignment.center, children: [
                Padding(
                    padding: EdgeInsets.all(10),
                    child: Image(
                      image: AssetImage("assets/images/windIcon.png"),
                      width: 40,
                    )),
                Padding(
                    padding: EdgeInsets.all(10),
                    child: Text("9.4km/h", style: TextStyle(fontSize: 18))),
                Padding(
                    padding: EdgeInsets.all(10),
                    child: Text("Poniente", style: TextStyle(fontSize: 18))),
                Icon(Icons.arrow_back)
              ]),
              Padding(
                  padding: const EdgeInsets.all(15),
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          "Población:    ${data['poblacio']}",
                          style: const TextStyle(fontSize: 16),
                        ),
                        Text(
                          "Latitud:    ${data['latitud']}",
                          style: const TextStyle(fontSize: 16),
                        ),
                        Text(
                          "Longitud:    ${data['longitud']}",
                          style: const TextStyle(fontSize: 16),
                        )
                      ]))
            ]))));
  }
}
