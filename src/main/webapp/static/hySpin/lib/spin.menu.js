/**
 * 菜单类
 * 菜单（menu）
 * 
 * 依赖 jQuery v2.0.3
 * 	   bootstrap v3.3.2
 * 	   netname v1.0
 * 
 * @Author hy.spin
 * @date 2015/8/12
 * */

/**
 * 菜单类, 仅支持2级
 */
Class('spin.menu', null, {
	Static : {
		/*
		 * 	基于jQuery的菜单
		 * menu:{ID:'', MName:'', MCode:'', Uri:'', Icon:''}
		 */
		init : function( _menus, _rightFrame ){
			var parents = [], childs = [],
				menuHTML = '';
			
			for(var i = 0; i < _menus.length; ++i){
				if(_menus[i].MCode === 0){
					parents.push(_menus[i]);
				}else{
					childs.push(_menus[i]);
				}
			}
			this.drawMenu(parents , childs, _rightFrame);
		},
		drawMenu : function( _parents, _childs, _rightFrame ){
			var pHtml = '',
				cHtml = '';
			
			if(_parents.length > 0){
				for(var i = 0; i < _parents.length; ++i){
					pHtml += '<hmenu>' +
					    		'<div class="menuTitle">' +
			    					'<span class="' + _parents[i].Icon + '"></span>' + _parents[i].MName + //'<span style="float:right;" class="glyphicon glyphicon-menu-up"></spam>' + 
							    '</div>';
					
					if(_childs.length > 0){
						cHtml = '<ul class="menuChild">'
						for(var j = 0; j < _childs.length; ++j){
							if(_childs[j].MCode === _parents[i].ID){
								cHtml += '<li><cite></cite><a href="' + _childs[j].Uri + '" target="' + _rightFrame + '"><span class="' + _childs[j].Icon + '"></span>  ' + _childs[j].MName + '</a><i></i></li>'
							}
						}
						cHtml += '</ul>';
					}
					pHtml += cHtml + '</hmenu>';	
				}
			}
			
			$("#hMenu").html(pHtml);
			this.bindEvt(); //绑定事件
		},
		bindEvt : function(){
			$(".menuChild li").click(function(){
				$(".menuChild li.active").removeClass("active")
				$(this).addClass("active");
			});
			
			$('.menuTitle').click(function(){
				var $ul = $(this).next('ul');
				$('hMenu').find('ul').slideUp();
				if($ul.is(':visible')){
					$(this).next('ul').slideUp();
				}else{
					$(this).next('ul').slideDown();
				}
			});
		}
	}
});
/*Class('spin.menu', null, {
    Static : {
    	** 基于bootstrap的菜单
		 * menu:{ID:'', MName:'', MCode:'', Uri:'', Icon:''}
		 *
    	frameTarget : '',
		init : function( _menus, _frame_target ){
			spin.menu.frameTarget = _frame_target;

			var parents = [], childs = [];
			
			for(var i = 0; i < _menus.length; ++i){
				if(_menus[i].MCode === 0){
					parents.push(_menus[i]);
				}else{
					childs.push(_menus[i]);
				}
			}
			spin.menu.drawMenu(parents , childs);
		},
		drawMenu : function( _parents, _childs){
			var pHtml = '',
				cHtml = '';
			if(_parents.length > 0){
				for(var i = 0; i < _parents.length; ++i){
					pHtml += '<div class="panel panel-default">\
								<div class="panel-heading" role="tab" id="parent_' + _parents[i].ID + '">\
									<h5 class="panel-title">\
										<a onclick="$(\'#child_' + _parents[i].ID + '\').collapse(\'toggle\');" data-toggle="collapse" class="' + (i === 0 ? '' : 'collapsed') + '" data-parent="#menu" href="#child_' + _parents[i].ID + '" aria-expanded="' + (i === 0 ? 'true' : 'false') + '" aria-controls="child_' + _parents[i].MCode + '">\
											<span class="' + _parents[i].Icon + '"></span> ' + _parents[i].MName + '\
										</a>\
									</h5>\
								</div>\
								<div id="child_' + _parents[i].ID + '" class="panel-collapse collapse ' + (i === 0 ? 'in' : '') + '" role="tabpanel" aria-labelledby="parent_' + _parents[i].ID + '">';
					
					if(_childs.length > 0){
						cHtml = '<div class="list-group">'
						for(var j = 0; j < _childs.length; ++j){
							if(_childs[j].MCode === _parents[i].ID){
								cHtml += '<a href="#" style="" class="list-group-item" onclick="' + (_childs[j].Uri === '' ? '' : 'spin.menu.switchFrame(\'' + _childs[j].Uri + '\');') + '"><span style="margin-left:10px;"><span class="' + _childs[j].Icon + '"></span> ' + _childs[j].MName + '</span></a>'		
							}
						}
						cHtml += '</div>';
					}
					pHtml += cHtml + '</div></div>';
				}
			}
			$("#menu").html(pHtml);
		},
		switchFrame : function( uri ){
			if(uri.length !== 0){
				$("#loading").show(); //loading
				setTimeout(function(){
					$("#" + spin.menu.frameTarget).attr("src", uri);
				}, 500);				
			}
		}
    }
});*/