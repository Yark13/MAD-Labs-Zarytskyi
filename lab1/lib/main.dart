import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Money calculator',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.yellowAccent),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Money calculator'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({required this.title, super.key});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _mainNumber = 0;
  final TextEditingController _controller = TextEditingController();
  String _errorMessage = '';

  void _processInput() {
    setState(() {
      final String input = _controller.text.trim();

      if (input == 'reset') {
        _mainNumber = 0;
        _errorMessage = '';
      } else {
        final int? enteredNumber = int.tryParse(input);

        if (enteredNumber != null) {
          _mainNumber += enteredNumber;
          _errorMessage = '';
        } else {
          _errorMessage =
              'You cannot add non-numeric input to the sum of money';
        }
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text('Current Number of money \$ :'),
            Text(
              '$_mainNumber',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            Padding(
              padding: const EdgeInsets.all(16),
              child: TextField(
                controller: _controller,
                decoration: InputDecoration(
                  hintText: 'Enter a number or "reset"',
                  errorText: _errorMessage.isEmpty ? null : _errorMessage,
                ),
                keyboardType: TextInputType.text,
              ),
            ),
            ElevatedButton(
              onPressed: _processInput,
              child: const Text('Add'),
            ),
          ],
        ),
      ),
    );
  }
}
