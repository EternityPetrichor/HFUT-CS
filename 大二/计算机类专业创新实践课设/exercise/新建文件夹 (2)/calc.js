function FloatAdd(f,d){
	var c,b,a;
	try{c=f.toString().split(".")[1].length}
	catch(g){c=0}
	try{b=d.toString().split(".")[1].length}
	catch(g){b=0}a=Math.pow(10,Math.max(c,b));
	return(f*a+d*a)/a}
function FloatSub(f,d){
	var c,b,a,h;
	try{
		c=f.toString().split(".")[1].length
	}catch(g){c=0}
	try{
		b=d.toString().split(".")[1].length
	}catch(g){b=0}a=Math.pow(10,Math.max(c,b));h=(c>=b)?c:b;
	return((f*a-d*a)/a).toFixed(h)}
function FloatMul(d,b){
	var a=0,f=d.toString(),c=b.toString();
	try{a+=f.split(".")[1].length
	}catch(g){}
	try{a+=c.split(".")[1].length
	}catch(g){}
	return Number(f.replace(".",""))*Number(c.replace(".",""))/Math.pow(10,a)}
function FloatDiv(arg1,arg2){
	var t1=0,t2=0,r1,r2;
	try{
		t1=arg1.toString().split(".")[1].length
	}catch(e){}
	try{
		t2=arg2.toString().split(".")[1].length
	}catch(e){}
	with(Math){
		r1=Number(arg1.toString().replace(".",""));
		r2=Number(arg2.toString().replace(".",""));
		return(r1/r2)*pow(10,t2-t1)
	}
}
var g_type=0;
var endNumber=true;
var mem=0,carry=10,layer=0;
var hexnum="0123456789abcdef";
var angle="d",stack="",level="0";
var $c_get=function(a){return document.getElementById(a)};
var lastOperator="";
var isMaxLen=false;
function inputkey(b,c){
	if(isMaxLen==false){
		if(lastOperator=="="){
			$c_get("completeEprsPanel").innerHTML=b
		}
		else{
			if(lastOperator=="m"){
				$c_get("completeEprsPanel").innerHTML=""
			}
			$c_get("completeEprsPanel").innerHTML+=b
		}
		var a=b.charCodeAt(0);
		if((carry==2&&(a==48||a==49))||(carry==8&&a>=48&&a<=55)||(carry==10&&(a>=48&&a<=57||a==46))||(carry==16&&((a>=48&&a<=57)||(a>=97&&a<=102)))){
			if(endNumber){
				endNumber=false;
				if(a==46){
					if($c_get("gaoji").value.indexOf(".")!=-1){

					}
					else{
						$c_get("gaoji").value+=b
					}
				}
				else{
					$c_get("gaoji").value=b
				}
			}
			else{
				if($c_get("gaoji").value==null||$c_get("gaoji").value=="0"){
					if(a==46){
						if($c_get("gaoji").value.indexOf(".")!=-1){

						}
						else{
							$c_get("gaoji").value+=b
						}
					}
					else{
						$c_get("gaoji").value=b
					}
				}
				else{
					if(a==46){
						if($c_get("gaoji").value.indexOf(".")!=-1){

						}
						else{
							$c_get("gaoji").value+=b
						}
					}
					else{
						$c_get("gaoji").value+=b
					}
				}
			}
		}
		lastOperator="";
		try{
			var g=c||window.event;
			var f=g.srcElement||g.target;f.blur()
		}
		catch(d){}
		if($c_get("gaoji").value.length>16){
			isMaxLen=true
		}
	}
	else{
		alert("\u53ea\u80fd\u8f93\u5165\u4e0d\u5927\u4e8e17\u4f4d\u7684\u5b57\u7b26")
	}
}
function changeSign(a){
	if($c_get("gaoji").value!="0"){
		if($c_get("gaoji").value.substr(0,1)=="-"){
			$c_get("gaoji").value=$c_get("gaoji").value.substr(1)
		}
		else{
			$c_get("gaoji").value="-"+$c_get("gaoji").value
		}
	}
	try{
		var d=a||window.event;var c=d.srcElement||d.target;c.blur()
	}
	catch(b){

	}
}
function inputfunction(a,b,c){
	endNumber=true;
	if($c_get("upperFile").checked){
		$c_get("gaoji").value=decto(funcalc(b,(todec($c_get("gaoji").value,carry))),carry).toFixed(12)
	}
	else{
		$c_get("gaoji").value=decto(funcalc(a,(todec($c_get("gaoji").value,carry))),carry).toFixed(12)
	}
	$c_get("upperFile").checked=false;
	$c_get("hyperbolic").checked=false;
	inputshift();
	try{var g=c||window.event;var f=g.srcElement||g.target;f.blur()}
	catch(d){}
}
function inputtrig(c,a,i,b,d){
	if($c_get("hyperbolic").checked){
		inputfunction(i,b)
	}
	else{
		inputfunction(c,a)
	}
	try{
		var h=d||window.event;
		var g=h.srcElement||h.target;g.blur()
	}catch(f){}
}
function operation(g,b,c){
	endNumber=true;
	var a=stack.substr(stack.lastIndexOf("(")+1)+$c_get("gaoji").value;
	while(b!=0&&(b<=(level.charAt(level.length-1)))){
		a=parse(a);level=level.slice(0,-1)
	}
	if(a.match(/^(.*\d[\+\-\*\/\%\^\&\|x])?([+-]?[0-9a-f\.]+)$/)){}
	$c_get("gaoji").value=RegExp.$2;
	stack=stack.substr(0,stack.lastIndexOf("(")+1)+a+g;
	$c_get("operator").value=" "+g+" ";level=level+b;
	$c_get("completeEprsPanel").innerHTML+=$c_get("operator").value;lastOperator=g;
	try{
		var h=c||window.event;
		var f=h.srcElement||h.target;f.blur()
	}catch(d){}isMaxLen=false
}
function addbracket(d,a){
	endNumber=true;
	document.calc.display.value=0;stack=stack+"(";
	document.calc.operator.value="   ";
	level=level+0;
	layer+=1;
	document.calc.bracket.value="(="+layer;if(lastOperator=="="){
		$c_get("completeEprsPanel").innerHTML=d.value
	}
	else{
		if(lastOperator=="m"){
			$c_get("completeEprsPanel").innerHTML=""
		}
		$c_get("completeEprsPanel").innerHTML+=d.value
	}
	lastOperator=d.value;
	try{
		var f=a||window.event;
		var c=f.srcElement||f.target;
		c.blur()
	}catch(b){}
}
function disbracket(f,b){
	endNumber=true;
	var a=stack.substr(stack.lastIndexOf("(")+1)+document.calc.display.value;
	while((level.charAt(level.length-1))>0){
		a=parse(a);
		level=level.slice(0,-1)
	}
	document.calc.display.value=a;
	stack=stack.substr(0,stack.lastIndexOf("("));
	document.calc.operator.value="   ";
	level=level.slice(0,-1);
	layer-=1;
	if(layer>0){
		document.calc.bracket.value="(="+layer
	}
	else{document.calc.bracket.value=""}if(lastOperator=="="){$c_get("completeEprsPanel").innerHTML=f.value}else{if(lastOperator=="m"){$c_get("completeEprsPanel").innerHTML=""}$c_get("completeEprsPanel").innerHTML+=f.value}lastOperator=f.value;try{var g=b||window.event;var d=g.srcElement||g.target;d.blur()}catch(c){}}
function result(b){
	endNumber=true;
	while(layer>0){
		disbracket()
	}
	var a=stack+$c_get("gaoji").value;
	while((level.charAt(level.length-1))>0){
		a=parse(a);level=level.slice(0,-1)
	}
	c_get("gaoji").value=a;
	c_get("bracket").value="";
	c_get("operator").value="";
	stack="";
	level="0";
	lastOperator="=";
	try{var f=b||window.event;
	var d=f.srcElement||f.target;
	d.blur()
	}catch(c){}
	isMaxLen=false
}
function backspace(a){
	try{
		document.getElementById("completeEprsPanel").innerHTML=document.getElementById("completeEprsPanel").innerHTML.substring(0,document.getElementById("completeEprsPanel").innerHTML.length-1)
	}
	catch(c){}
	if(!endNumber){
		if(c_get("gaoji").value.length>1){
			c_get("gaoji").value=c_get("gaoji").value.substring(0,c_get("gaoji").value.length-1)
		}
		else{
			c_get("gaoji").value=0}
		}
	try{var d=a||window.event;
		var b=d.srcElement||d.target;b.blur()
	}catch(c){}
	if(c_get("gaoji").value.length<=16){
		isMaxLen=false
	}
}
function clearall(a){
	c_get("gaoji").value=0;
	endNumber=true;
	stack="";
	level="0";
	layer="";
	c_get("operator").value="";
	c_get("bracket").value="";
	document.getElementById("completeEprsPanel").innerHTML="";
	try{
		var d=a||window.event;
		var c=d.srcElement||d.target;c.blur()
	}catch(b){}
	isMaxLen=false
}
function inputChangCarry(a,b){
	endNumber=true;
	c_get("gaoji").value=(decto(todec(c_get("gaoji").value,carry),a));
	carry=a;
	c_get("sin").className=(carry!=10)?"cal_btn  cal_btn_dis ":"cal_btn cal_btn_gre";
	c_get("cos").className=(carry!=10)?"cal_btn cal_btn_dis ":"cal_btn cal_btn_gre";
	c_get("tan").className=(carry!=10)?"cal_btn cal_btn_dis ":"cal_btn cal_btn_gre";
	c_get("bt").className=(carry!=10)?"cal_btn cal_btn_dis ":"cal_btn cal_btn_gre";
	c_get("pi").className=(carry!=10)?"cal_btn cal_btn_dis ":"cal_btn cal_btn_gre";
	c_get("e").className=(carry!=10)?"cal_btn cal_btn_dis ":"cal_btn cal_btn_gre";
	c_get("completeDot").className=(carry!=10)?"cal_btn cal_btn_dis ":"cal_btn ";
	c_get("complete2").className=(carry<=2)?"cal_btn cal_btn_dis":"cal_btn fontArial";
	c_get("complete3").className=(carry<=2)?"cal_btn cal_btn_dis":"cal_btn fontArial";
	c_get("complete4").className=(carry<=2)?"cal_btn cal_btn_dis":"cal_btn fontArial";
	c_get("complete5").className=(carry<=2)?"cal_btn cal_btn_dis":"cal_btn fontArial";
	c_get("complete6").className=(carry<=2)?"cal_btn cal_btn_dis":"cal_btn fontArial";
	c_get("complete7").className=(carry<=2)?"cal_btn cal_btn_dis":"cal_btn fontArial";
	c_get("complete8").className=(carry<=8)?"cal_btn cal_btn_dis":"cal_btn fontArial";
	c_get("complete9").className=(carry<=8)?"cal_btn cal_btn_dis":"cal_btn fontArial";
	c_get("ka").className=(carry<=10)?"cal_btn cal_btn_dis":"cal_btn cal_btn_gre";
	c_get("kb").className=(carry<=10)?"cal_btn cal_btn_dis":"cal_btn cal_btn_gre";
	c_get("kc").className=(carry<=10)?"cal_btn cal_btn_dis":"cal_btn cal_btn_gre";
	c_get("kd").className=(carry<=10)?"cal_btn cal_btn_dis":"cal_btn cal_btn_gre";
	c_get("ke").className=(carry<=10)?"cal_btn cal_btn_dis":"cal_btn cal_btn_gre";
	c_get("kf").className=(carry<=10)?"cal_btn cal_btn_dis":"cal_btn cal_btn_gre";
	try{
		var f=b||window.event;
		var d=f.srcElement||f.target;d.blur()
	}catch(c){}
}
function inputChangAngle(a,b){
	endNumber=true;
	angle=a;
	if(angle=="d"){
		c_get("gaoji").value=radiansToDegress(c_get("gaoji").value)
	}
	else{
		c_get("gaoji").value=degressToRadians(c_get("gaoji").value)
	}
	endNumber=true;
	try{
		var f=b||window.event;
		var d=f.srcElement||f.target;d.blur()
	}catch(c){}
}
function inputshift(a){
	if(c_get("upperFile").checked){
		c_get("bt").innerHTML="deg";
		c_get("ln").innerHTML="exp";
		c_get("log").innerHTML="expd";
		if(c_get("hyperbolic").checked){
			c_get("sin").innerHTML="ahs";
			c_get("cos").innerHTML="ahc";
			c_get("tan").innerHTML="aht"
		}
		else{
			c_get("sin").innerHTML="asin";
			c_get("cos").innerHTML="acos";
			c_get("tan").innerHTML="atan"
		}
		c_get("sqr").innerHTML="x^.5";
		c_get("cube").innerHTML="x^.3";
		c_get("changeDecimal").innerHTML="\u5c0f\u6570"
	}
	else{
		c_get("bt").innerHTML="d.ms";
		c_get("ln").innerHTML="ln";
		c_get("log").innerHTML="log";
		if(c_get("hyperbolic").checked){
			c_get("sin").innerHTML="hsin";
			c_get("cos").innerHTML="hcos";
			c_get("tan").innerHTML="htan"
		}
		else{
			c_get("sin").innerHTML="sin";
			c_get("cos").innerHTML="cos";
			c_get("tan").innerHTML="tan"
		}
		c_get("sqr").innerHTML="x^2";
		c_get("cube").innerHTML="x^3";
		c_get("changeDecimal").innerHTML="\u53d6\u6574"
	}
	try{
		var d=a||window.event;
		var c=d.srcElement||d.target;c.blur()
	}catch(b){}
}
function clearmemory(a){
	mem=0;
	c_get("memory").value=" ";
	try{
		var d=a||window.event;
		var c=d.srcElement||d.target;c.blur()
	}catch(b){}
}
function getmemory(a){
	endNumber=true;
	c_get("gaoji").value=decto(mem,carry);
	try{
		var d=a||window.event;
		var c=d.srcElement||d.target;
		c.blur()
	}
	catch(b){}
}
function putmemory(a){
	endNumber=true;
	if(c_get("gaoji").value!=0){
		mem=todec(c_get("gaoji").value,carry);
		c_get("memory").value=" M "
	}
	else{
		c_get("memory").value="   "
	}
	lastOperator="m";
	try{
		var d=a||window.event;
		var c=d.srcElement||d.target;c.blur()
	}catch(b){}
}
function addmemory(a){endNumber=true;mem=parseFloat(mem)+parseFloat(todec(c_get("gaoji").value,carry));if(mem==0){c_get("memory").value="   "}else{c_get("memory").value=" M "}lastOperator="m";try{var d=a||window.event;var c=d.srcElement||d.target;c.blur()}catch(b){}}function multimemory(a){endNumber=true;mem=parseFloat(mem)*parseFloat(todec(c_get("gaoji").value,carry));if(mem==0){c_get("memory").value="   "}else{c_get("memory").value=" M "}lastOperator="m";try{var d=a||window.event;var c=d.srcElement||d.target;c.blur()}catch(b){}}function todec(c,d){if(d==10||c==0){return(c)}var e=(c.charAt(0)=="-");if(e){c=c.substr(1)}var a=0;for(var b=1;b<=c.length;b++){a=a*d+hexnum.indexOf(c.charAt(b-1))}if(e){a=-a}return(a)}function decto(c,b){var d=(c<0);if(b==10||c==0){return(c)}c=""+Math.abs(c);var a="";while(c!=0){a=hexnum.charAt(c%b)+a;c=Math.floor(c/b)}if(d){a="-"+a}return(a)}
function parse(a){if(a.match(/^(.*\d[\+\-\*\/\%\^\&\|x\<])?([+-]?[0-9a-f\.]+)([\+\-\*\/\%\^\&\|x\<])([+-]?[0-9a-f\.]+)$/)){return(RegExp.$1+cypher(RegExp.$2,RegExp.$3,RegExp.$4))}else{return(a)}}function cypher(c,b,a){c=todec(c,carry);a=todec(a,carry);if(b=="+"){return(decto(parseFloat(c)+parseFloat(a),carry))}if(b=="-"){return(decto(c-a,carry))}if(b=="*"){return(decto(FloatMul(c,a),carry))}if(b=="/"&&a!=0){return(decto(FloatDiv(c,a),carry))}if(b=="%"){return(decto(c%a,carry))}if(b=="&"){return(decto(c&a,carry))}if(b=="|"){return(decto(c|a,carry))}if(b=="^"){return(decto(Math.pow(c,a),carry))}if(b=="x"){return(decto(c^a,carry))}if(b=="<"){return(decto(c<<a,carry))}alert("\u9664\u6570\u4e0d\u80fd\u4e3a\u96f6");return(c)}function funcalc(fun,num){with(Math){if(fun=="pi"){return(PI)}if(fun=="e"){return(E)}if(fun=="abs"){return(abs(num))}if(fun=="ceil"){return(ceil(num))}if(fun=="round"){return(round(num))}if(fun=="floor"){return(floor(num))}if(fun=="deci"){return(num-floor(num))}if(fun=="ln"&&num>0){return(log(num))}if(fun=="exp"){return(exp(num))}if(fun=="log"&&num>0){return(log(num)*LOG10E)}if(fun=="expdec"){return(pow(10,num))}if(fun=="cube"){return(num*num*num)}if(fun=="cubt"){return(pow(num,1/3))}if(fun=="sqr"){return(num*num)}if(fun=="sqrt"&&num>=0){return(sqrt(num))}if(fun=="!"){return(factorial(num))}if(fun=="recip"&&num!=0){return(1/num)}if(fun=="dms"){return(dms(num))}if(fun=="deg"){return(deg(num))}if(fun=="~"){return(~num)}if(angle=="d"){if(fun=="sin"){return(sin(degressToRadians(num)))}if(fun=="cos"){return(cos(degressToRadians(num)))}if(fun=="tan"){return(tan(degressToRadians(num)))}if(fun=="arcsin"&&abs(num)<=1){return(radiansToDegress(asin(num)))}if(fun=="arccos"&&abs(num)<=1){return(radiansToDegress(acos(num)))}if(fun=="arctan"){return(radiansToDegress(atan(num)))}}else{if(fun=="sin"){return(sin(num))}if(fun=="cos"){return(cos(num))}if(fun=="tan"){return(tan(num))}if(fun=="arcsin"&&abs(num)<=1){return(asin(num))}if(fun=="arccos"&&abs(num)<=1){return(acos(num))}if(fun=="arctan"){return(atan(num))}}if(fun=="hypsin"){return((exp(num)-exp(0-num))*0.5)}if(fun=="hypcos"){return((exp(num)+exp(-num))*0.5)}if(fun=="hyptan"){return((exp(num)-exp(-num))/(exp(num)+exp(-num)))}if(fun=="ahypsin"|fun=="hypcos"|fun=="hyptan"){alert("\u5bf9\u4e0d\u8d77,\u516c\u5f0f\u8fd8\u6ca1\u6709\u67e5\u5230!");return(num)}alert("\u8d85\u51fa\u51fd\u6570\u5b9a\u4e49\u8303\u56f4");return(num)}}function factorial(b){b=Math.abs(parseInt(b));var a=1;for(;b>0;b-=1){a*=b}return(a)}function dms(n){var neg=(n<0);with(Math){n=abs(n);var d=floor(n);var m=floor(60*(n-d));var s=(n-d)*60-m}var dms=d+m/100+s*0.006;if(neg){dms=-dms}return(dms)}
function deg(n){
	var neg=(n<0);
	with(Math){
		n=abs(n);
		var d=floor(n);
		var m=floor((n-d)*100);
		var s=(n-d)*100-m
	}
	var deg=d+m/60+s/36;
	if(neg){deg=-deg}
		return(deg)
}
function degressToRadians(a){
	return(a*Math.PI/180)
}
function radiansToDegress(a){
	return(a*180/Math.PI)
}
var data={left:"",sign:"",right:"",result:""};
var current=false;
var m="";
var lastIsMemory=false;
var isMaxLength=false;
var c_get=function(a){
	return document.getElementById(a)
};
var c_getByName=function(b,e,f){
	var a="";
	if(!b){
		a=document.getElementsByTagName(e)
	}
	else{
		a=c_get(b).getElementsByTagName(e)
	}
	if(f){
		var c=[];
		for(var d=0;d<a.length;d++){
			if(a[d].nodeName==f){
				c.push(a[d])
			}
		}
		return c
	}
	return a
};
var calculator={
	arith:function(a){
		if(parseFloat(data.sign)!=-2){switch(parseFloat(data.sign)){case 0:data.result=FloatAdd(parseFloat(data.left),parseFloat(data.right));break;case 1:data.result=FloatSub(parseFloat(data.left),parseFloat(data.right));break;case 2:data.result=FloatMul(parseFloat(data.left),parseFloat(data.right));break;case 3:data.result=FloatDiv(parseFloat(data.left),parseFloat(data.right));break}data.left="";data.sign=a;data.right="";current=false;c_get("resultIpt").value=data.result}},foo:function(a){if(!lastIsMemory){if(data.left==""){if(data.result!=""){data.left=data.result;current=true}else{return false}}else{if(data.right==""){current=true}else{calculator.arith(a);data.left=data.result;data.sign=a;data.right="";current=true}}}else{if(data.left!=""&&data.right!=""){calculator.arith(a);data.left=data.result;data.sign=a;data.right="";current=true}else{data.left=m;current=true}lastIsMemory=false}data.sign=a},input:function(a,b){if(b!=-2){if(data.sign.toString()=="-2"){$c_get("baseEprsPanel").innerHTML=a.innerHTML}else{if(!lastIsMemory){$c_get("baseEprsPanel").innerHTML+=a.innerHTML}else{$c_get("baseEprsPanel").innerHTML=a.innerHTML}}}switch(b){case -1:if(isMaxLength==false){if(c_get("resultIpt").value.substring(0,1)=="0"&&a.innerHTML=="0"&&c_get("resultIpt").value.length<=1){}else{if(!lastIsMemory){if(current){if((data.right.toString().indexOf(".")!=-1&&a.value==".")){return false}else{if(a.value=="."&&(data.right==""||data.right=="0")){data.right="0"+a.value}else{if(data.right.toString().length<=1&&data.right=="0"){data.right=a.innerHTML}else{data.right+=a.innerHTML}}c_get("resultIpt").value=data.right}}else{if((data.left.toString().indexOf(".")!=-1&&a.value==".")){return false}else{if(a.value=="."&&(data.left==""||data.left=="0")){data.left="0"+a.value}else{if(data.left.toString().length<=1&&data.left=="0"){data.left=a.innerHTML}else{data.left+=a.innerHTML}}c_get("resultIpt").value=data.left}}}else{current=false;data.left=a.innerHTML;c_get("resultIpt").value=data.left;lastIsMemory=false}if(data.sign.toString()=="-2"){data.sign=""}if(c_get("resultIpt").value.length>16){isMaxLength=true}}}else{alert("\u53ea\u80fd\u8f93\u5165\u4e0d\u5927\u4e8e17\u4f4d\u7684\u5b57\u7b26")}break;case -2:if(data.left!=""&&data.right!=""){calculator.arith(-2)}else{if(data.result!=""&&data.sign!=""&&parseFloat(data.sign)!=-2){data.right=data.left;data.left=data.result;calculator.arith(-2)}}isMaxLength=false;break;case -3:if(c_get("resultIpt").value.substring(0,1)=="-"){c_get("resultIpt").value=c_get("resultIpt").value.substr(1)}else{c_get("resultIpt").value="-"+c_get("resultIpt").value}if(current==false){if(data.left==""){if(data.result!=""){data.left=data.result;data.left=-Number(data.left)}}else{data.left=c_get("resultIpt").value}}else{data.right=c_get("resultIpt").value}break;case 0:calculator.foo(0);isMaxLength=false;break;case 1:calculator.foo(1);isMaxLength=false;break;case 2:calculator.foo(2);isMaxLength=false;break;case 3:calculator.foo(3);isMaxLength=false;break}a.blur()},output:function(a,c,b){if(b){a.innerHTML+=c}else{a.innerHTML=c}},remove:function(){if(current==false){if(data.left.length>0){data.left=data.left.substring(0,data.left.length-1);c_get("resultIpt").value=data.left}}else{if(data.right.length>0){data.right=data.right.substring(0,data.right.length-1);c_get("resultIpt").value=data.right}}try{c_get("baseEprsPanel").innerHTML=c_get("baseEprsPanel").innerHTML.substring(0,c_get("baseEprsPanel").innerHTML.length-1)}catch(a){}c_get("simpleDel").blur();if(c_get("resultIpt").value.length<=16){isMaxLength=false}},clearAll:function(){c_get("resultIpt").value=0;current=false;data.left=data.right=data.result="";c_get("baseEprsPanel").innerHTML="";c_get("simpleClearAllBtn").blur();isMaxLength=false},memory:function(b,a){switch(a){case 0:if(c_get("resultIpt").value!=""){m=parseFloat(c_get("resultIpt").value)}lastIsMemory=true;break;case 1:if(m!=""){if(parseFloat(data.sign)!=-2&&data.sign.toString()!=""){if(current){data.right=m}else{data.left=m}c_get("baseEprsPanel").innerHTML+=m}c_get("resultIpt").value=m}lastIsMemory=true;break;case 2:m="";break;case -1:if(m==""){if(c_get("resultIpt").value!=""){m=parseFloat(c_get("resultIpt").value)}}else{if(c_get("resultIpt").value!=""){m=parseFloat(m)+parseFloat(c_get("resultIpt").value)}}lastIsMemory=true;break;case -2:if(m==""){if(c_get("resultIpt").value!=""){m=parseFloat(c_get("resultIpt").value)}}else{if(c_get("resultIpt").value!=""){m=parseFloat(m)*parseFloat(c_get("resultIpt").value)}}lastIsMemory=true;break}b.blur()}};var byKeyBoard=function(d,e){var a=function(f){return document.getElementById(f)};var c=Number(d);var b="simple";if(c_get("calculator_base").style.display=="none"){b="complete"}if(c==48||c==96){a(b+"0").click();return false}if(c==49||c==97){a(b+"1").click();return false}if(c==50||c==98){a(b+"2").click();return false}if(c==51||c==99){a(b+"3").click();return false}if(c==52||c==100){a(b+"4").click();return false}if(c==53||c==101){a(b+"5").click();return false}if(c==54||c==102){a(b+"6").click();return false}if(c==55||c==103){a(b+"7").click();return false}if(c==56||c==104){a(b+"8").click();return false}if(c==57||c==105){a(b+"9").click();return false}if(c==13||c==187){a(b+"Equal").click();return false}if(c==190||c==110){a(b+"Dot").click();return false}if(c==109||c==189){a(b+"Subtr").click();return false}switch(d){case 107:a(b+"Add").click();break;case 109:a(b+"Subtr").click();break;case 189:a(b+"Subtr").click();break;case 106:a(b+"Multi").click();break;case 111:a(b+"Divi").click();break;case 46:a(b+"Del").click();break}};
function jisuanqi_run(){
	document.onkeydown=function(a){
		var c=a||window.event;var b=c.keyCode;byKeyBoard(b,c)
	};
	inputChangCarry(10)};
