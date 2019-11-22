package com.lucky.jacklamb.utils;

public class Jacklabm {
	
	public static boolean first=true;
	
	public static void welcome() {
		if(!first)
			return;
		first=false;
		String d=str3();
		System.out.println(d);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getD() {
		int i=(int) (Math.random() * (8 - 1) + 1);
		switch (i) {
		case 1:
			return str1();
		case 2:
			return str2();
		case 3:
			return str3();
		case 4:
			return str4();
		case 5:
			return str5();
		case 6:
			return str6();
		case 7:
			return str7();
		case 8:
			return str8();
		}
		return null;
	}

	public static String exception(String titleinfo,String m1,String m2) {
		String exception="<!doctype html>" + 
				"<html lang=\"en\">" + 
				"<head>" + 
				"<title>"+titleinfo+"</title>" + 
				"<style type=\"text/css\">h1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} h2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} h3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} b {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} p {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;} a {color:black;} a.name {color:black;} .line {height:1px;background-color:#525D76;border:none;}</style>" + 
				"</head>" + 
				"<body>" + 
				"<h1>"+titleinfo+"</h1>" + 
				"<hr class=\"line\" /><p><b>Type</b> Status Report</p>" + 
				"<p><b>Message</b> "+m1+"</p>" + 
				"<p><b>Description</b> "+m2+"</p>" + 
				"<hr class=\"line\" /><h3>JackLamb Lucky[noxml]/1.0.00</h3></body></html>";
		return exception;
	}
	
	public static String str1() {
		String d="     ,---------------------------------,      ,---------,\r\n" + 
				"         ,-----------------------,          ,\"        ,\"|\r\n" + 
				"      ,\"                      ,\" |        ,\"        ,\"  |\r\n" + 
				"      +-----------------------+  |      ,\"        ,\"    |\r\n" + 
				"      |  .-----------------.  |  |     +---------+      |\r\n" + 
				"      |  |                 |  |  |     | -==----'|      |\r\n" + 
				"      |  |  Lucky          |  |  |     |         |      |\r\n" + 
				"      |  |  version: 1.0.0 |  |  |/----|`---=    |      |\r\n" + 
				"      |  |  C:\\>_          |  |  |   ,/|==== ooo |      ;\r\n" + 
				"      |  |                 |  |  |  // | ((FK))  |    ,\"\r\n" + 
				"      |  '-----------------'  |,\" .;'| |((7075)) |  ,\"\r\n" + 
				"      +-----------------------+  ;;  | |         |,\"\r\n" + 
				"         /_)______________(_/  //'   | +---------+\r\n" + 
				"    ___________________________/___  `,\r\n" + 
				"   /  oooooooooooooooo  .o.  oooo /,   \\,\"-----------\r\n" + 
				"  / ==ooooooooooooooo==.o.  ooo= //   ,`\\--{:)     ,\"\r\n" + 
				" /_==__==========__==_ooo__ooo=_/'   /___________,\"\r\n" + 
				"——————————————————————————————————\r\n\n--------------------------------------------------\n##\n## Lucky[NOXML版]\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------\n\n" ;
		return d;
	}
	
	public static String str2() {
		String d="/**\r\n" + 
				" * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐\r\n" + 
				" * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐\r\n" + 
				" * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘\r\n" + 
				" * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐\r\n" + 
				" * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │\r\n" + 
				" * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤\r\n" + 
				" * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \\ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │\r\n" + 
				" * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │\r\n" + 
				" * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│\" '│ Enter  │               │ 4 │ 5 │ 6 │   │\r\n" + 
				" * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤\r\n" + 
				" * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │\r\n" + 
				" * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││\r\n" + 
				" * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│\r\n" + 
				" * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘\r\n" + 
				" */\r\n" + 
				"————————————————Hello I'm Lucky :)————————————————\r\n\n--------------------------------------------------\n##\n## Lucky[NOXML版]\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------\n\n";
		return d;
	}
	
	public static String str3() {
		String d="———————————————————————GitHub：FK7075—————————————————————————\n"+
				"  .--,       .--,\r\n" + 
				" ( (  \\.---./  ) )\r\n" + 
				"  '.__/o   o\\__.'\r\n" + 
				"     {=  ^  =}\r\n" + 
				"      >  -  <\r\n" + 
				"     /       \\\r\n" + 
				"    //       \\\\\r\n" + 
				"   //|   .   |\\\\\r\n" + 
				"   \"'\\       /'\"_.-~^`'-.\r\n" + 
				"      \\  _  /--'         `\r\n" + 
				"    ___)( )(___\r\n" + 
				"   (((__) (__)))    Hello I'm Jack,Lucky is my girlfriend.\r\n\n" + 
				"———————————————————————GitHub：FK7075—————————————————————————\r\n\n--------------------------------------------------\n##\n## Lucky[NOXML版]\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------";
		return d;
	}
	
	public static String str4() {
		String d="#         ┌─┐       ┌─┐\r\n" + 
				"#      ┌──┘ ┴───────┘ ┴──┐\r\n" + 
				"#      │                 │\r\n" + 
				"#      │       ───       │\r\n" + 
				"#      │  ─┬┘       └┬─  │\r\n" + 
				"#      │                 │\r\n" + 
				"#      │       ─┴─       │\r\n" + 
				"#      │                 │\r\n" + 
				"#      └───┐         ┌───┘\r\n" + 
				"#          │         │\r\n" + 
				"#          │         │\r\n" + 
				"#          │         │\r\n" + 
				"#          │         └──────────────┐\r\n" + 
				"#          │                        │\r\n" + 
				"#          │                        ├─┐\r\n" + 
				"#          │                        ┌─┘\r\n" + 
				"#          │                        │\r\n" + 
				"#          └─┐  ┐  ┌───────┬──┐  ┌──┘\r\n" + 
				"#            │ ─┤ ─┤       │ ─┤ ─┤\r\n" + 
				"#            └──┴──┘       └──┴──┘\r\n" + 
				"#                Hello I'm Lucky :)\n" + 
				"#                \r\n" + 
				"————————————————————————————————————————————————\r\n\n\n--------------------------------------------------\n##\n## Lucky[NOXML版]\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------\n\n";
		return d;
	}
	
	public static String str5() {
		String d="▄▄▄▄▄\r\n" + 
				"            ▀▀▀██████▄▄▄       _______________\r\n" + 
				"          ▄▄▄▄▄  █████████▄  /                 \\\r\n" + 
				"         ▀▀▀▀█████▌ ▀▐▄ ▀▐█ |    I'm Lucky :)   |\r\n" + 
				"       ▀▀█████▄▄ ▀██████▄██ | _________________/\r\n" + 
				"       ▀▄▄▄▄▄  ▀▀█▄▀█════█▀ |/\r\n" + 
				"            ▀▀▀▄  ▀▀███ ▀       ▄▄\r\n" + 
				"         ▄███▀▀██▄████████▄ ▄▀▀▀▀▀▀█▌   ______________________________ \r\n" + 
				"       ██▀▄▄▄██▀▄███▀ ▀▀████      ▄██  █                              \\\\ \r\n" + 
				"    ▄▀▀▀▄██▄▀▀▌████▒▒▒▒▒▒███     ▌▄▄▀▀▀▀█_____________________________ //\r\n" + 
				"    ▌    ▐▀████▐███▒▒▒▒▒▐██▌\r\n" + 
				"    ▀▄▄▄▄▀   ▀▀████▒▒▒▒▄██▀\r\n" + 
				"              ▀▀█████████▀\r\n" + 
				"            ▄▄██▀██████▀█\r\n" + 
				"          ▄██▀     ▀▀▀  █\r\n" + 
				"         ▄█             ▐▌\r\n" + 
				"     ▄▄▄▄█▌              ▀█▄▄▄▄▀▀▄\r\n" + 
				"    ▌     ▐                ▀▀▄▄▄▀\r\n" + 
				"     ▀▀▄▄▀     ██   \r\n" + 
				" \\  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀ ▀ \r\n" + 
				" \\- ▌       .....Follow Me.....           ▀ ▀      \r\n" + 
				"  - ▌                            (o)          ▀ \r\n" + 
				" /- ▌            Go Go Go !               ▀ ▀           \r\n" + 
				" /  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀ ▀       \r\n" + 
				"               ██\r\n" + 
				"————————————————\r\n\n\n--------------------------------------------------\n##\n## Lucky[NOXML版]\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------\n\n";
		return d;
	}
	
	public static String str6() {
		String d="<!--\r\n" + 
				"                       ::\r\n" + 
				"                      :;J7, :,                        ::;7:\r\n" + 
				"                      ,ivYi, ,                       ;LLLFS:\r\n" + 
				"                      :iv7Yi                       :7ri;j5PL\r\n" + 
				"                     ,:ivYLvr                    ,ivrrirrY2X,\r\n" + 
				"                     :;r@Wwz.7r:                :ivu@kexianli.\r\n" + 
				"                    :iL7::,:::iiirii:ii;::::,,irvF7rvvLujL7ur\r\n" + 
				"                   ri::,:,::i:iiiiiii:i:irrv177JX7rYXqZEkvv17\r\n" + 
				"                ;i:, , ::::iirrririi:i:::iiir2XXvii;L8OGJr71i\r\n" + 
				"              :,, ,,:   ,::ir@mingyi.irii:i:::j1jri7ZBOS7ivv,\r\n" + 
				"                 ,::,    ::rv77iiiriii:iii:i::,rvLq@huhao.Li\r\n" + 
				"             ,,      ,, ,:ir7ir::,:::i;ir:::i:i::rSGGYri712:\r\n" + 
				"           :::  ,v7r:: ::rrv77:, ,, ,:i7rrii:::::, ir7ri7Lri\r\n" + 
				"          ,     2OBBOi,iiir;r::        ,irriiii::,, ,iv7Luur:\r\n" + 
				"        ,,     i78MBBi,:,:::,:,  :7FSL: ,iriii:::i::,,:rLqXv::\r\n" + 
				"        :      iuMMP: :,:::,:ii;2GY7OBB0viiii:i:iii:i:::iJqL;::\r\n" + 
				"       ,     ::::i   ,,,,, ::LuBBu BBBBBErii:i:i:i:i:i:i:r77ii\r\n" + 
				"      ,       :       , ,,:::rruBZ1MBBqi, :,,,:::,::::::iiriri:\r\n" + 
				"     ,               ,,,,::::i:  @arqiao.       ,:,, ,:::ii;i7:\r\n" + 
				"    :,       rjujLYLi   ,,:::::,:::::::::,,   ,:i,:,,,,,::i:iii\r\n" + 
				"    ::      BBBBBBBBB0,    ,,::: , ,:::::: ,      ,,,, ,,:::::::\r\n" + 
				"    i,  ,  ,8BMMBBBBBBi     ,,:,,     ,,, , ,   , , , :,::ii::i::\r\n" + 
				"    :      iZMOMOMBBM2::::::::::,,,,     ,,,,,,:,,,::::i:irr:i:::,\r\n" + 
				"    i   ,,:;u0MBMOG1L:::i::::::  ,,,::,   ,,, ::::::i:i:iirii:i:i:\r\n" + 
				"    :    ,iuUuuXUkFu7i:iii:i:::, :,:,: ::::::::i:i:::::iirr7iiri::\r\n" + 
				"    :     :rk@Yizero.i:::::, ,:ii:::::::i:::::i::,::::iirrriiiri::,\r\n" + 
				"     :      5BMBBBBBBSr:,::rv2kuii:::iii::,:i:,, , ,,:,:i@petermu.,\r\n" + 
				"          , :r50EZ8MBBBBGOBBBZP7::::i::,:::::,: :,:,::i;rrririiii::\r\n" + 
				"              :jujYY7LS0ujJL7r::,::i::,::::::::::::::iirirrrrrrr:ii:\r\n" + 
				"           ,:  :@kevensun.:,:,,,::::i:i:::::,,::::::iir;ii;7v77;ii;i,\r\n" + 
				"           ,,,     ,,:,::::::i:iiiii:i::::,, ::::iiiir@xingjief.r;7:i,\r\n" + 
				"        , , ,,,:,,::::::::iiiiiiiiii:,:,:::::::::iiir;ri7vL77rrirri::\r\n" + 
				"         :,, , ::::::::i:::i:::i:i::,,,,,:,::i:i:::iir;@Secbone.ii:::\r\n" + 
				"\r\n" + 
				"--\r\n" + 
				"——————————————————————Hello I,m Lucky :)——————————————————————\r\n\n\n--------------------------------------------------\n##\n## Lucky[NOXML版]\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------\n\n";
		return d;
	}
	
	public static String str7() {
		String d="#\r\n" + 
				"#                  ___====-_  _-====___\r\n" + 
				"#            _--^^^#####//      \\\\#####^^^--_\r\n" + 
				"#         _-^##########// (    ) \\\\##########^-_\r\n" + 
				"#        -############//  |\\^^/|  \\\\############-\r\n" + 
				"#      _/############//   (@::@)   \\\\############\\_\r\n" + 
				"#     /#############((     \\\\//     ))#############\\\r\n" + 
				"#    -###############\\\\    (oo)    //###############-\r\n" + 
				"#   -#################\\\\  / VV \\  //#################-\r\n" + 
				"#  -###################\\\\/      \\//###################-\r\n" + 
				"# _#/|##########/\\######(   /\\   )######/\\##########|\\#_\r\n" + 
				"# |/ |#/\\#/\\#/\\/  \\#/\\##\\  |  |  /##/\\#/  \\/\\#/\\#/\\#| \\|\r\n" + 
				"# `  |/  V  V  `   V  \\#\\| |  | |/#/  V   '  V  V  \\|  '\r\n" + 
				"#    `   `  `      `   / | |  | | \\   '      '  '   '\r\n" + 
				"#                     (  | |  | |  )\r\n" + 
				"#                    __\\ | |  | | /__\r\n" + 
				"#                   (vvv(VVV)(VVV)vvv)\r\n\n" + 
				"—————————————————————Hello I,m Lucky :)—————————————————————\r\n\n\n--------------------------------------------------\n##\n## Lucky[NOXML版]\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------\n\n";
		return d;
	}
	
	public static String str8() {
		String d="#                       _oo0oo_\r\n" + 
				"#                      o8888888o\r\n" + 
				"#                      88\" . \"88\r\n" + 
				"#                      (| -_- |)\r\n" + 
				"#                      0\\  =  /0\r\n" + 
				"#                    ___/`---'\\___\r\n" + 
				"#                  .' \\\\|     |// '.\r\n" + 
				"#                 / \\\\|||  :  |||// \\\r\n" + 
				"#                / _||||| -:- |||||- \\\r\n" + 
				"#               |   | \\\\\\  -  /// |   |\r\n" + 
				"#               | \\_|  ''\\---/''  |_/ |\r\n" + 
				"#               \\  .-\\__  '-'  ___/-. /\r\n" + 
				"#             ___'. .'  /--.--\\  `. .'___\r\n" + 
				"#          .\"\" '<  `.___\\_<|>_/___.' >' \"\".\r\n" + 
				"#         | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\r\n" + 
				"#         \\  \\ `_.   \\_ __\\ /__ _/   .-` /  /\r\n" + 
				"#     =====`-.____`.___ \\_____/___.-`___.-'=====\r\n" + 
				"#                       `=---='\r\n" + 
				"#\r\n" + 
				"#\r\n" + 
				"#     ~~~~~~~~~~~~~~~Hello Lucky :)~~~~~~~~~~~~~~~~~~\r\n" + 
				"#\r\n" + 
				"#               佛祖保佑         永无BUG\r\n" + 
				"————————————————————————————————————————————————\r\n\n\n--------------------------------------------------\n##\n## Lucky[NOXML版]\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------\n\n";
		return d;
	}
}
