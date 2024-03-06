import 'package:flutter/material.dart';

import 'package:sd_camera_plugin/sd_camera_plugin.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _sdCameraPlugin = SdCameraPlugin();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: ElevatedButton(onPressed: () async{
            await _sdCameraPlugin.getCameraPlugin();
          },
          child: Text("Invoke"),),
        ),
      ),
    );
  }
}
