import 'package:flutter/material.dart';

const _appName = "MyFirstApp";

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: _appName,
      theme: ThemeData.light(),
      darkTheme: ThemeData.dark(),
      home: const SafeArea(
        child: MyHomePage()
      ),
    );
  }
}

class MyHomePage extends StatelessWidget{
  const MyHomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(_appName),
        elevation: 5,
        titleSpacing: 30,
        toolbarHeight: 65,
      ),
      body: MainContainer(),
    );
  }
}

class MainContainer extends StatelessWidget{
  const MainContainer({super.key});

  @override
  Widget build(BuildContext context) {
    return const Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        CustomItem(label: "Element1"),
        CustomItem(label: "Element2"),
        CustomItem(label: "Element3"),
        CustomItem(label: "Element4"),
        CustomItem(label: "Element5"),
        CustomItem(label: "Element6"),
      ],
    );
  }
}

class CustomItem extends StatefulWidget{
  final String label;

  const CustomItem({Key? key, required this.label}) : super(key : key);

  @override
  State<StatefulWidget> createState() => _CustomItemState(label : label);
}

class _CustomItemState extends State<CustomItem>{
  final String label;

  _CustomItemState({Key? key, required this.label}) : super();
  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Checkbox(value: true, onChanged: (bool? value){}),
        Text(label),
      ],
    );
  }
}

