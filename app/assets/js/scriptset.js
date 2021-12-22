var hh;
//DOMが構築されたら実行する
$(document).ready(function(){
// デバイスタイプの読み込み
	loadAction();
	
// メニューの処理	
	// id=tocのタグにメニュー（toc.html）を読み込む
	$("#toc").load("./toc.html",function(d){
		hh = $('#toc .acc').attr('data-maxheight');
		menuPosition();
		//ページが完全に読み込まれたときと、windowがリサイズされたときに実行する
		//$(window).on("load resize", menuPosition);
	});

	// id=scriptdisabledのタグ（JavaScript無効時のメッセージ）を隠す
	$("#scriptdisabled").hide();

//外部読み込みの呼び出し
	getHash = [];
	getHtml = [];
	$('.getData').each(function(i){
		var t = $(this);
		var txtlink = t.html();
		var getLink = $('a',this).attr('href');
		
		if(getLink.indexOf('./') !== -1){
			getLink = getLink.split('./')[1];
		}
			
		if(getLink.indexOf('#') !== -1){
			getHtml[i] = getLink.split('#')[0];
			getHash[i] = '#' + getLink.split('#')[1];	
		}else{
			getHtml[i] = getLink;
			getHash[i] = 'none';
		};
		
		$.ajax({
			type: 'GET',
			url: getHtml[i],
			dataType: 'html',
			processData: false,
			success: function(data){
				if(getHash[i] == 'none'){
					loadHtml = $(data).filter('#main').html();
					if(!loadHtml){
						loadHtml = data;
					}
				}else{
					loadHtml = $(data).filter(getHash[i]).html();
					if(!loadHtml){
						loadHtml = $(data).find(getHash[i]).html();
					}
				}
				if(!loadHtml){
					loadHtml = txtlink;
				}
				t.html(loadHtml);
				loadAction();
			}
		 });
	});

// div.stepsには、class="hideblock"を追加
    $("div.steps").addClass("hideblock");

// h2の次にあるh3には、class="h2next"を追加
    $("h2 + h3").addClass("h2next");

// h5操作手順をボタンで開閉する。
	// 指定した要素の先頭にボタンを付与。h5.icon_openを定義。（印刷：非表示）
	$("h5").livequery(function() {
		$(this).addClass("icon_open");
		$(this).prepend("<input type='button' value='↓開く' class='noprint' />");
	});
	// 指定した要素にマウスが重なるとclass=title_hoverを付与、マウスが外れると削除。（印刷：非表示）
	$("h5").live("hover", function () {
		if(event.type == "mouseover") {
			$(this).addClass("hx_hover");
		} else {
			$(this).removeClass("hx_hover");
		}
	});
	// 指定した要素をクリックすると以下を実行。
	$("h5.icon_open").livequery("click", function(){
		$(this).removeClass("icon_open");
		$(this).addClass("icon_close");
		// 同じ階層の次の要素（div class="steps"）を、アニメーションして表示する。
		$(this).next().show('fast');
		// 指定した要素のボタンを変更。（印刷：非表示）
		$("input",this).replaceWith("<input type='button' value='→とじる' class='noprint' />");
	});
	// 指定した要素をクリックすると以下を実行。
	$("h5.icon_close").livequery("click", function(){
		$(this).removeClass("icon_close");
		$(this).addClass("icon_open");
		// 同じ階層の次の要素（div class="steps"）を、アニメーションして隠す。
		$(this).next().hide('fast');
		// 指定した要素のボタンを変更。（印刷：非表示）
		$("input",this).replaceWith("<input type='button' value='↓開く' class='noprint' />");
	});
	// ボタンですべてのh5を開く。
	$("#allopenh5").click(function(){
		$("h5").each(function(){
			$(this).removeClass("icon_open");
			$(this).addClass("icon_close");
			// 同じ階層の次の要素（div class="steps"）を、アニメーションして表示する。
			$(this).next().show('fast');
			// 指定した要素のボタンを変更。（印刷：非表示）
			$("input",this).replaceWith("<input type='button' value='→とじる' class='noprint' />");
		});
	});
	// ボタンですべてのh5を閉じる。
	$("#allcloseh5").click(function(){
		$("h5").each(function(){
			$(this).removeClass("icon_close");
			$(this).addClass("icon_open");
			// 同じ階層の次の要素を、アニメーションして隠す。
			$(this).next().hide('fast');
			// 指定した要素のボタンを変更。（印刷：非表示）
			$("input",this).replaceWith("<input type='button' value='↓開く' class='noprint' />");
		});
	});

// ポイントとヒントの本文の表示/非表示を切り替える
	// 指定した要素にborder-bottom属性を追加する。
	$("div.step_supple").livequery(function () {
		$(this).addClass("box_lined");
	});
	// 指定した要素をすべて隠す。（印刷：表示）
	$("div.step_point > *:not('h6, .deptlist'), div.step_hint > *:not('h6, .deptlist')").livequery(function () {
		$(this).addClass("hideblock");
	});
	// 指定した要素にボタンを付与。（印刷：非表示）
	$("div.step_point > h6, div.step_hint > h6").livequery(function () {
		$(this).append("　<input type='button' value='開く/とじる' class='noprint' />");
	});
	// 指定した要素にマウスが重なるとclass=title_hoverを付与、マウスが外れると削除。（印刷：非表示）
	$("div.step_point > h6, div.step_hint > h6").live("hover", function () {
		if(event.type == "mouseover") {
			$(this).addClass("hx_hover");
		} else {
			$(this).removeClass("hx_hover");
		}
	});
	// 指定した要素をクリックすると以下を実行。（live関数はiOSに非対応）
	$("div.step_point > h6, div.step_hint > h6").livequery("click", function(){
		// 同じ階層に並ぶ要素の表示/非表示を切り替える。（印刷：表示）
		$(this).nextAll(":not('.deptlist')").toggleClass("hideblock");
		// 上記要素であるdiv.step_pointなどにborder-bottom属性を追加/削除する。（印刷：表示）
		$(this).parent().toggleClass('box_lined');
	});

	// "switch_"という文字列から始まるclassがある場合にセレクトメニューを生成する。
	// xmlから読み込む。
	var thisTag = $('[class^="switch_"]').get(0);
	var thisTitle = document.title;
	var valJson = [];
	var optWord = [];
	var optAttr = [];
	
	//selectメニューのxmlを読み込む
	$.ajax({
		type: "GET",
		url: "selects.xml",
		dataType: "xml",
		success: function(xml){
				var group = $('AllMenu', xml).children().get(0).tagName;
				$(xml).find(group).each(function(m){
					var thisTag = $('.switch' + (m+1));
					menuTtl = $(this).find('title').text();
					
					//xmlのtitleタグに文字列があり、かつHTMLの.switch_***に.noneTitleがなければ以下を実行
					//.switch_***と同じタグに.noneTitleがあればtitleタグの文字列は表示されない
					if(menuTtl && !$(thisTag).is('.noneTitle')){
						$(thisTag).append(menuTtl + ' ');
					}
					
					//selectの読み込みと書き出し
					$('select',this).each(function(s, elem){
						sltID = $(this).attr('id');
						
						$(thisTag).append('<select id="'+ sltID +'">');
						
						//optionの読み込みと書き出し
						var optVal = [];
						$('option',this).each(function(i){				
							opt = '<option />';							
							
							//xmlのoptionタグのなかにwordタグがあれば以下を実行
							//optValはtype*で固定
							if($(this).children().is('word')){
								optVal[i] = 'type' + (i+1);			
								optTxt = $(this).attr('value');

								var obj = new Object();
								var wordObj = new Object();
								
								$('word', this).each(function(w){
									wVal = $(this).attr('value');
									wordObj[wVal] = $(this).text();							
								});
								
								obj.type = optVal[i];
								obj.word = wordObj;
								optWord[i] = obj;
								
							}else{
								optVal[i] = $(this).attr('value');
								optTxt = $(this).text();
							}
							$('#' + sltID).append($(opt).val(optVal[i]).text(optTxt));
						});							
						selectMenu(sltID, optVal, optWord);
						
					});
				});					
			},
		error: function(){$(thisTag).text("メニューの読み込みに失敗しました。")}
	});		
});

//ページが完全に読み込まれたときと、windowがリサイズされたときに実行する
$(window).on("load resize", menuPosition);

function selectMenu(sltID, sltVal, optWord){	
	// 部署選択メニューによる表示制御とクッキー制御。
	if($.cookie(sltID)) {
		// クッキーの内容を変数strに設定。
		str = $.cookie(sltID);
	}else{
		if(optWord.length > 0){
			// クッキーが無くてoptWordに要素がある場合は、変数strを"type1"に設定。
			str = 'type1';
		}else{
			// それ以外でクッキーが無い場合は、変数strを"all"に設定。
			str = "all";
		}
	};
	$('#' + sltID + ' option[value=' + str + ']').attr("selected", "selected");
	// ほかの部署を隠す。どれでもなければすべて表示。（印刷：非表示）
	var notStr = [];
	$(sltVal).each(function(){
		if(this != str){notStr = notStr.concat(this);}
	});
	strSelect (str, notStr, optWord);
	
	// 見出し1部のセレクトボックスを選ぶと、表示を切り替える。
	$("select#" + sltID).change(function () {
		// 選んだセレクトボックス名を取得。
		$("#" + sltID + " option:selected").each(function () {
			str = $(this).val();
			// クッキーにstrをセットする。ディレクトリ依存。有効期限は30日間。
			$.cookie(sltID, str, {expires: null});
		});
		// ほかの部署を隠す。どれでもなければすべて表示。（印刷：非表示）
		var notStr = [];
		$('#' + sltID + ' option:not(:selected)').each(function(i){
			notStr[i] = $(this).val();
		});
		strSelect (str, notStr, optWord);
	});

};

// セレクトメニューで選んでいない項目のブロックを隠す。どれでもなければすべて表示。（印刷：非表示）
// strにtypeの文字列があるものは本文中の特定の文字列が変更される。
function strSelect (str, notStr, optWord) {
	if(str.indexOf('type') !== -1){
		var thisKey = [];
		var thisWord = [];
		
		$(optWord).each(function(i){
			if(this.type == str){
					var w = this.word;
					var i = 0;
					for(var key in w){
						thisKey[i] = key;
						thisWord[i] = w[key];
						i++;
					}
			}
		});
		
		notStrClass = notStr.join(' ');
		$(notStr).each(function(i){
			return notStr[i] = '.' + this + ',';
		});
		$(notStr.toString()).removeClass(notStrClass).addClass(str);
		
		$(thisKey).each(function(i, n){
			$("span." + this).livequery(function(){
				$(this).text(thisWord[i]);
			});
		});
	}else if(str == "all"){
		$(notStr).each(function(){
			$("." + this).livequery(function () {
				$(this).removeClass("hideblock2");
			});
		});
	}else{
		
		$(notStr).each(function(){
			if(this !== 'all'){
				$("." + this).livequery(function () {
					$(this).addClass("hideblock2");
				});
			}
		});
		$("." + str).livequery(function () {
			$(this).removeClass("hideblock2");
		});
	};
};

//デバイスがPCのとき、ウィンドウのサイズなどによってメニューのpositionをfixedかstaticにする
function menuPosition(){
	var UA = navigator.userAgent;
	var dw = screen.width;
	var wh = $(window).height();
	//PCの場合に実行
	if (!navigator.userAgent.match(/iPhone/i) || !navigator.userAgent.match(/iPod/i) || !navigator.userAgent.match(/iPad/i) || !navigator.userAgent.match(/Android/i)) {
		//(1)IE6〜8でデバイス横サイズが495px以上1119px以下のときと、
		//(2)ウィンドウ縦サイズが任意のサイズ以下のとき（toc.htmlのdata-maxheight属性で指定）
		//#menuのスタイルを設定（tablet.cssの代わりに）
		if(((UA.match(/msie 8./i) || UA.match(/msie 7./i) || UA.match(/msie 6./i)) && ((495 < dw) && (dw< 1119))) || (wh < hh)){
			$('#menu').css({
				'position':'static',
				'float':'left'
			});
		}else{
			$('#menu').removeAttr('style');
		};
	};
};

function loadAction(){
	// iPhone、iPod、iPad、Androidの場合に実行
	if (navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPod/i) || navigator.userAgent.match(/iPad/i) || navigator.userAgent.match(/Android/i)) {
		// class=fancyzoomのタグaごとに以下を実行
		$("a.fancyzoom").each(function(){
			// 同タグをdivタグに変更する
			$(this).replaceWith("<div>" + $(this).html() + "</div>");
		});

		// h2のid属性を削除（h2へのリンクを禁止。メニューの上下動を防ぐ）
		$("h2").removeAttr("id");
		// class=thispageのタグaごとに以下を実行
		$("a.thispage").each(function(){
			// 同タグのリンク先をheading1に変更する
			$(this).replaceWith("<a href=&quot;#heading1&quot;>" + $(this).html() + "</a>");
		});
	}else{
	//PCの場合
		setupZoom();
	};
};

function setupZoom(){
	//class="fancyzoom"の要素がページ内にあったらfancyzoom()を呼び出す
	if($("a").hasClass("fancyzoom")){
		$('a.fancyzoom').fancyzoom();
	}
};