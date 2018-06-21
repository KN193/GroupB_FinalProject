function Dsy(){
	this.Items = {};
}
Dsy.prototype.add = function(id,iArray){
	this.Items[id] = iArray;
}
Dsy.prototype.Exists = function(id){
	if(typeof(this.Items[id]) == "undefined") return false;
	return true;
}

function change(v){
	var str="0";
	for(i=0;i<v;i++){
		str+=("_"+(document.getElementById(s[i]).selectedIndex-1));
	};
	var ss=document.getElementById(s[v]);
	with(ss){
		length = 0;
		options[0]=new Option(opt0[v],opt0[v]);
		if(v && document.getElementById(s[v-1]).selectedIndex>0 || !v){
			if(dsy.Exists(str)){
				ar = dsy.Items[str];
				for(i=0;i<ar.length;i++){
					options[length]=new Option(ar[i],ar[i]);
				}//end for
				if(v){ options[0].selected = true; }
			}
		}//end if v
		if(++v<s.length){change(v);}
	}//End with
}

var dsy = new Dsy();

dsy.add("0",["Book","class","food"]);


dsy.add("0_0_0",["Java","Database","others"]);

dsy.add("0_0_1",["international Law","Australian Law","others"]);


dsy.add("0_0",["computer science","Law","others"]);


dsy.add("0_1_0",["Java","Database","others"]);

dsy.add("0_1_1",["international Law","Australian Law","others"]);

dsy.add("0_1",["computer scinece","Law","others"]);

dsy.add("0_2_0",["burger","noodle","others"]);

dsy.add("0_2_1",["dishes","soap","snack","others"]);

dsy.add("0_2",["Australian food","Chinese food"]);

dsy.add("0",["Book","class","food","others"]);

var s=["s_province","s_city","s_county"];//三个select的name
var opt0 = ["general service","specific service","service"];//初始值
function _init_area(){  //初始化函数
	for(i=0;i<s.length-1;i++){
	  document.getElementById(s[i]).onchange=new Function("change("+(i+1)+")");
	}
	change(0);
}
