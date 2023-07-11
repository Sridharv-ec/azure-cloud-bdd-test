$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/speechToText.feature");
formatter.feature({
  "name": "Azure cogninitive service Speech to text feature file",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@speechToText"
    }
  ]
});
formatter.scenario({
  "name": "Azure congnitive Speech to text",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@speechToText"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "convert text \"Hello AI world!\" to speech",
  "keyword": "And "
});
formatter.match({
  "location": "SpeechToTextStepDef.convert_text_to_speech(String)"
});
