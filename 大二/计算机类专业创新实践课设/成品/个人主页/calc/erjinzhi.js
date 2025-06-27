function add() {
      var number1 = document.getElementById("number1").value;
      var number2 = document.getElementById("number2").value;

      var decimal1 = parseInt(number1, 2);
      var decimal2 = parseInt(number2, 2);

      var result = (decimal1 + decimal2).toString(2);

      document.getElementById("result").innerHTML = result;
    }

    function subtract() {
      var number1 = document.getElementById("number1").value;
      var number2 = document.getElementById("number2").value;

      var decimal1 = parseInt(number1, 2);
      var decimal2 = parseInt(number2, 2);

      var result = (decimal1 - decimal2).toString(2);

      document.getElementById("result").innerHTML = result;
    }

    function multiply() {
      var number1 = document.getElementById("number1").value;
      var number2 = document.getElementById("number2").value;

      var decimal1 = parseInt(number1, 2);
      var decimal2 = parseInt(number2, 2);

      var result = (decimal1 * decimal2).toString(2);

      document.getElementById("result").innerHTML = result;
    }

    function divide() {
      var number1 = document.getElementById("number1").value;
      var number2 = document.getElementById("number2").value;

      var decimal1 = parseInt(number1, 2);
      var decimal2 = parseInt(number2, 2);

      var result = (decimal1 / decimal2).toString(2);

      document.getElementById("result").innerHTML = result;
    }

    function and() {
      var number1 = document.getElementById("number1").value;
      var number2 = document.getElementById("number2").value;

      var decimal1 = parseInt(number1, 2);
      var decimal2 = parseInt(number2, 2);

      var result = (decimal1 & decimal2).toString(2);

      document.getElementById("result").innerHTML = result;
    }

    function or() {
      var number1 = document.getElementById("number1").value;
      var number2 = document.getElementById("number2").value;

      var decimal1 = parseInt(number1, 2);
      var decimal2 = parseInt(number2, 2);

      var result = (decimal1 | decimal2).toString(2);

      document.getElementById("result").innerHTML = result;
    }