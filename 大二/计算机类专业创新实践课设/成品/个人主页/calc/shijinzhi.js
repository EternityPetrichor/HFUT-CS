  function appendToDisplay(value) {
        var display = document.getElementById("display");
        display.value += value;
    }

    function appendOperatorToDisplay(operator) {
      var display = document.getElementById("display");
      var lastChar = display.value.slice(-1);
      if (lastChar !== '+' && lastChar !== '-' && lastChar !== '*' && lastChar !== '/' && lastChar !== '!' && lastChar !== '.') {
        display.value += operator;
      }
    }

    function appendParenthesis(parenthesis) {
      var display = document.getElementById("display");
      display.value += parenthesis;
    }

    
    function calculate() {
      var display = document.getElementById("display");
      var expression = display.value;

      // 处理阶乘运算
      expression = expression.replace(/(\d+)!/g, function(match, num) {
        var result = 1;
        for (var i = 1; i <= num; i++) {
          result *= i;
        }
        return result;
      });

      // 使用eval()函数进行基本计算
      try {
        var result = eval(expression);
        if (isNaN(result) || !isFinite(result)) {
          throw "计算结果非法";
        }
        display.value = result;
      } catch (err) {
        display.value = "错误";
      }
    }


    function deleteLastChar() {
      var display = document.getElementById("display");
      display.value = display.value.slice(0, -1);
    }

    function clearDisplay() {
      document.getElementById("display").value = "";
    }