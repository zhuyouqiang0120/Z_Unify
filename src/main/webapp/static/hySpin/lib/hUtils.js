/**
 * 工具类
 * 菜单（menu）、表格（table）
 * 
 * 依赖 jQuery v2.0.3
 * 	   bootstrap v3.3.2
 * 	   netname v1.0
 * 
 * @Author huyi@zensvision.com
 * @date 2014/11/22--2014/
 * */

/**
 * 菜单类, 仅支持2级
 */
Class('hUtil.menu', null, {
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
/*Class('hUtil.menu', null, {
    Static : {
    	** 基于bootstrap的菜单
		 * menu:{ID:'', MName:'', MCode:'', Uri:'', Icon:''}
		 *
    	frameTarget : '',
		init : function( _menus, _frame_target ){
			hUtil.menu.frameTarget = _frame_target;

			var parents = [], childs = [];
			
			for(var i = 0; i < _menus.length; ++i){
				if(_menus[i].MCode === 0){
					parents.push(_menus[i]);
				}else{
					childs.push(_menus[i]);
				}
			}
			hUtil.menu.drawMenu(parents , childs);
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
								cHtml += '<a href="#" style="" class="list-group-item" onclick="' + (_childs[j].Uri === '' ? '' : 'hUtil.menu.switchFrame(\'' + _childs[j].Uri + '\');') + '"><span style="margin-left:10px;"><span class="' + _childs[j].Icon + '"></span> ' + _childs[j].MName + '</span></a>'		
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
					$("#" + hUtil.menu.frameTarget).attr("src", uri);
				}, 500);				
			}
		}
    }
});*/

/**
 * 表格类
 */
Class('hUtil.table', null, {
	Static : {
		/**
		 * [{name : '添加用户', icon : 'glyphicon glyphicon-plus', uri : '', func:'add'},
            {name : '编辑用户', icon : 'glyphicon glyphicon-edit', uri : '', func:'edit'},
            {name : '移除用户', icon : 'glyphicon glyphicon-minus', uri : '', func:'remove'}]
		 */
		tUtil : [],
		/**
		 * {name:'', text:'', isOrder:true, isShow:''}
		 */
		tHead : [],
		/**
		 * [{},{}...]
		 */
		tBody : [],
		tUri : '',
		tCurrPage : 1,
		tPageSize : 15,
		tOrderCase : '',
		tCount : 0,
		isAllowMulit : false, //是否允许多选
		init : function(_uri, _tUtil, _tHead, _tCurrPage, _tPageSize, _isAllowMulit){
			hUtil.table.tHead = _tHead;
			hUtil.table.tUtil = _tUtil;
			hUtil.table.isAllowMulit = _isAllowMulit;
			hUtil.table.tUri = _uri;
			
			hUtil.table.drawUtil(); //绘制动作组
			hUtil.table.drawHead();
			hUtil.table.getData(_tCurrPage, _tPageSize, ''); //获取表格数据并绘制
		},
		refresh : function( _uri ){
			hUtil.table.tUri = _uri;
			hUtil.table.getData(1, hUtil.table.tPageSize, ''); //重新初始化第一页，获取表格数据并绘制
		},
		getData : function( _tCurrPage, _tPageSize, _orderCase ){
			if( hUtil.table.tUri !== ''){
				if( hUtil.Util.isNull(_orderCase) ){
					_orderCase = '';
				}else{
					hUtil.table.tOrderCase = _orderCase; 
				}
				$("#loading").show();
				$.ajax({
					type : "post",
					url : hUtil.table.tUri + '&currPage=' + _tCurrPage + '&pageSize=' + _tPageSize + '&orderCase=' + _orderCase,
					async : false,
					success : function( data ){
						if(typeof data === 'string'){
							data = eval("(" + data + ")");
						}
						$("#loading").hide();
						//清空选中记录
						hUtil.table.selectItems = [];
						
						hUtil.table.tCurrPage = _tCurrPage;
						hUtil.table.tPageSize = _tPageSize;
						//hUtil.table.tCount = data.count;
						hUtil.table.tCount = data.totalRow; //JFinal返回
						hUtil.table.tBody = data.list; //JFinal返回
						hUtil.table.drawTable();
						hUtil.table.drawPagination(_tCurrPage, _tPageSize, data.totalRow);
					},
					dataType:'json',
					timeout : 5000,
					error : function(req, error){
						alert('Get table data err!');
					}
				});
			}
		},
		drawHead : function(){
			var thead = hUtil.table.tHead, headHtml = '';
			if(thead.length > 0){
				if( hUtil.table.isAllowMulit ){
					headHtml += '<tr><td width="3%" onclick="hUtil.table.selectAll();"><span name="tdBox" id="theadTd" class="glyphicon glyphicon-unchecked"></span></td>';
				}else{
					headHtml += '<tr><td><span name="tdBox" id="theadTd" class="glyphicon glyphicon-unchecked"></span></td>';
				}
				for(var j = 0; j < thead.length; ++j){
					if( thead[j].isShow ){
						headHtml += '<td>' + thead[j].text + '&nbsp;<button type="button" onclick="hUtil.table.sort(\'' + thead[j].name + '\');" class="btn btn-link btn-xs" aria-label="Left Align">\
						  										<span id="sort_' + thead[j].name + '" class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>\
						  									</button>\
						</td>';
					}
				}
				headHtml += '</tr>';
			}
			$("#hTable").html('<thead id="tHead">' + headHtml + '</thead><tbody id="tbody"></tbody>');
		},
		drawTable : function(){
			function isUndefined( o ){
				//Boolean([]),Boolean({})->true  Boolean(0)->false
				if( o == 0 ){
					return o;
				}
				return o || '';
			}
			
			var	tbody = hUtil.table.tBody, thead = hUtil.table.tHead, 
				bodyHtml = '', field = [];
			if(tbody.length > 0){
				for(var i = 0; i < tbody.length; ++i){
					bodyHtml += '<tr onclick="hUtil.table.selectRow(' + i + ');">\
								<td><span name="tdBox" id="tbodyTd_' + i + '" class="glyphicon glyphicon-unchecked"></span></td>';
					for(var j = 0; j < thead.length; ++j){
						if( thead[j].isShow ){
							var _type = thead[j].type;
							if(_type === 'tag' || _type == 'switch' ){
								bodyHtml += '<td>' + isUndefined( thead[j].value[ tbody[i][thead[j].name] ] ) + '</td>';
							}else if(_type === 'linkBtn'){
								var addr = tbody[i][thead[j].name];
								bodyHtml += '<td><button ' + (addr == '' ? 'disabled="disabled"' : '') + 'class="btn btn-primary btn-xs" onclick="window.open(\'' + addr  + '\');"><span class="glyphicon glyphicon-search"></span> 查看</button></td>';	
							}else if(_type === 'selected'){
								var value = isUndefined( tbody[i][thead[j].name] );
								if(thead[j].css !== {}){
									bodyHtml += '<td>' + isUndefined( thead[j].css[ value ] ) + '</td>';
								}else{
									bodyHtml += '<td>' + value + '</td>';
								}
							}else if(_type === 'badge'){
								bodyHtml += '<td><span class="badge">' + isUndefined( tbody[i][thead[j].name] ) + '</span></td>';
							}else{
								bodyHtml += '<td>' + isUndefined( tbody[i][thead[j].name] ) + '</td>';
							}
						}
					}
					bodyHtml += '</tr>';
				}
			}else{
				//alert('table is null!');
			}
			$("#tbody").html( bodyHtml );
		},
		sort : function( _field_name ){
			if( !hUtil.Util.isNull( _field_name ) ){
				var _span = $('#sort_' + _field_name), _orderCase = _field_name;
				if( _span.attr('class') === 'glyphicon glyphicon-menu-down' ){
					_span.attr('class', 'glyphicon glyphicon-menu-up');
					_orderCase += ' ASC';
				}else{
					_span.attr('class', 'glyphicon glyphicon-menu-down');
					_orderCase += ' DESC';
				}
				hUtil.table.getData(hUtil.table.tCurrPage, hUtil.table.tPageSize, _orderCase);
			}else{
				console.log('The orderCase is null!');
			}
		},
		pre_index : -1,
		selectItems : [],
		selectRow : function( _index ){
			if( !hUtil.table.isAllowMulit ){
				if(this.pre_index === -1){
					this.pre_index = _index;
				}else{
					$("#tbodyTd_" + this.pre_index).attr('class', 'glyphicon glyphicon-unchecked');
					this.pre_index = _index;
				}
				$("#tbodyTd_" + _index).attr('class', 'glyphicon glyphicon-check');
				this.selectItems = [ _index ];
			}else{
				if($("#tbodyTd_" + _index).hasClass('glyphicon glyphicon-unchecked')){
					$("#tbodyTd_" + _index).attr('class', 'glyphicon glyphicon-check');
					hUtil.table.selectItems.push( _index );
				}else{
					$("#tbodyTd_" + _index).attr('class', 'glyphicon glyphicon-unchecked');
					var buf = [], items = hUtil.table.selectItems;
					for(var i = 0; i < items.length; ++i){
						if(items[i] !== _index){
							buf.push(items[i]);
						}
					}
					hUtil.table.selectItems = buf;
				}
			}
		},
		selectAll : function(){
			if($("#theadTd").hasClass('glyphicon glyphicon-unchecked')){
				$("[name='tdBox']").attr('class', 'glyphicon glyphicon-check');
				hUtil.table.selectItems = [];
				for(var i = 0; i < hUtil.table.tBody.length; ++i){
					hUtil.table.selectItems.push(i);
				}
			}else{
				$("[name='tdBox']").attr('class', 'glyphicon glyphicon-unchecked');
				hUtil.table.selectItems = [];
			}
			
		},
		drawPagination : function( _currPage, _pageSize, _count ){
			var end = Math.ceil(_count / _pageSize),
				orderCase = hUtil.table.tOrderCase;
			var paginHtml = '<ul class="pagination" style="float:left;margin:1px;">\
								<li><button class="btn btn-default" ' + (_currPage === 1 ? 'disabled="disabled"' : '') + ' onclick="hUtil.table.getData(1, ' + _pageSize + ', \'' + orderCase + '\');"><span class="glyphicon glyphicon-fast-backward"></span></button></li>\
								<li><button class="btn btn-default" ' + (_currPage === 1 ? 'disabled="disabled"' : '') + ' onclick="hUtil.table.getData(' + (_currPage - 1) +', ' + _pageSize + ', \'' + orderCase + '\');"><span class="glyphicon glyphicon-chevron-left"></span></button></li>\
								<li><button class="btn btn-default" disabled="disabled"><span>' + _currPage + '</span></button></li>\
								<li><button class="btn btn-default" ' + (_currPage === end || end === 0 ? 'disabled="disabled"' : '') + ' onclick="hUtil.table.getData(' + (_currPage + 1) +', ' + _pageSize + ', \'' + orderCase + '\');"><span class="glyphicon glyphicon-chevron-right"></span></button></li>\
								<li><button class="btn btn-default" ' + (_currPage === end || end === 0 ? 'disabled="disabled"' : '') + ' onclick="hUtil.table.getData(' + end +', ' + _pageSize + ', \'' + orderCase + '\');"><span class="glyphicon glyphicon-fast-forward"></span></button></li>\
							</ul>\
							<div style="float:right"><label class="control-label">当前第<button class="btn btn-default">' + _currPage + '</button>页,共' + end + '页</label></div>';
			
			$("#hPagination").html(paginHtml);
		},
		/**
		 * 增删改查
		 */
		drawUtil : function(){
			var tUtil = hUtil.table.tUtil;
			if(tUtil.length > 0){
				var _leftHtml = '', _uri = '', _type = '', _rightHtml = '';
				for(var i = 0; i < tUtil.length; ++i){
					_uri = tUtil[i].uri, _type = tUtil[i].type, _direct = tUtil[i].direct;
					if(_type === 'btn'){
						var _bufHtml = '';
						_bufHtml += '<button type="button" class="' + tUtil[i].css + '" onclick="hUtil.table.operate(\'' + tUtil[i].name + '\', \'' + tUtil[i].func + '\', \'' + _uri + '\', ' + i + ')"><span class="' + tUtil[i].icon + '"></span> ' + tUtil[i].name + '</button>';
						if(_direct === 'left'){
							_leftHtml += _bufHtml;
						}else{
							_rightHtml += _bufHtml;
						}
					}else if(_type === 'btnG'){
						var _bufHtml = '';
						_bufHtml += '<div class="btn-group">\
				  			<button type="button" class="' + tUtil[i].css + ' dropdown-toggle" data-toggle="dropdown">\
								<span class="' + tUtil[i].icon + '"></span> ' + tUtil[i].name + '<span class="caret"></span>\
								</button><ul class="dropdown-menu" role="menu">';
						for(var j = 0; j < _uri.length; ++j){
							_bufHtml += '<li><a href="#"onclick="hUtil.table.operate(\'' + _uri[j].name + '\', \'' + _uri[j].func + '\', \'' + _uri[j].uri + '\')"><span class="' + _uri[j].icon + '"></span> ' + _uri[j].name + '</a></li>';
						}
						_bufHtml += '</ul></div>';
						if(_direct === 'left'){
							_leftHtml += _bufHtml;
						}else{
							_rightHtml += _bufHtml;
						}
					}else if(_type === 'link'){
						
					}
				}
				$('#hTableUtil').html('<div class="pull-left btn-group">' + _leftHtml + '</div><div class="pull-right btn-group">' + _rightHtml + '</div>');
			}else{
				console.log('Table Util is null');
			}
		},
		/**
		 * 获取当前表单数据组合为实体对象
		 */
		getItem : function( _sign ){
			var field = hUtil.table.tHead,
				selectOption,
				item = '{',
				name = '', value = '', type = '';
			
			for(var i = 0; i < field.length; ++i){
				name = field[i].name,
				value = $('[name="' + name + '"]').val(),
				type = field[i].type;
				
				if(i !== 0){
					item += ', ';
				}
				if(type === 'ajaxSelected'){
					var txt = $('#' + name + ' option:selected').text();
					item += '\'' + name + '\':' + (typeof field[i].value === 'number' ?  txt : '\'' + txt + '\', \'' + name + 'ID\':' + value);
				}else if(type === 'switch'){
					item += '\'' + name + '\':\'' + $('[name="' + name + '"]').attr('switchVal') + '\'';
				}else{
					if(_sign === 'edit'){
						selectOption = hUtil.table.tBody[ hUtil.table.selectItems[0] ];
						item += '\'' + name + '\':' + (typeof value === 'undefined' ? (type === 'tag' || type === 'INDEX' || type === 'sys' || type === 'badge' ? (typeof selectOption[name] === 'number' ? selectOption[name] : '\'' + selectOption[name] + '\'' ) : (typeof field[i].value === 'number' ? field[i].value : '\'' + field[i].value + '\'')) : (type === 'number' ? value : '\'' + value + '\''));
					}else if(_sign === 'add'){
						item += '\'' + name + '\':' + (typeof value === 'undefined' ? (type === 'tag' || type === 'INDEX' || type === 'badge' ? 0 : (typeof field[i].value === 'number' ? field[i].value : '\'' + field[i].value + '\'')) : (type === 'number' ? value : '\'' + value + '\''));
					}
				}
			}
			item += '}';
			return item;
		},
		/**
		 * 检测uri中需要修改的参数
		 */
		checkParam : function( _uri ){
			var _indexF = _uri.indexOf('?');
			if(_indexF === -1){
				return -1;
			}else{
				var param = _uri.substring(_indexF + 1),
					flag_param = '', _indexJ = param.indexOf('#'), 
					_buf = '', name = '', val = '', items = hUtil.table.selectItems, tBody = hUtil.table.tBody,
					currOption;
				if( _indexJ > -1 ){
					var _param = _uri.substring(_indexF + 1);
					flag_param = _param.substring(_indexJ + 1);
					param = _param.substring(0, _indexJ).split('&');
				}else{
					param = param.split('&');
				}
				
				for(var j = 0; j < items.length; ++j){
					currOption = tBody[ items[j] ];
					for(var i = 0; i < param.length; ++i){
						_buf = param[i];
						var _indexE = _buf.indexOf('=');
						name = _buf.substring(0, _indexE);
						val = _buf.substring(_indexE + 1);
						
						if( currOption[ name ] == val && name !== flag_param){
							return currOption.ID;
						}
					}
				}
				return -1;
			}
		},
		/**
		 * 输入检测
		 * bootstrap popover
		 */
		checkInput : function( _targetName, _regex ){
			var val = $("[name='" + _targetName + "']").val();
			if(_regex === ''){
				if(val === ''){
					$("[name='" + _targetName + "']").popover({placement:'top'});
					$("[name='" + _targetName + "']").popover('show');
				}else{
					$("[name='" + _targetName + "']").popover('destroy');
				}
			}else{
				//需完善正则判断
				var reg = new RegExp( _regex );
				if(reg.test(val)){
					$("[name='" + _targetName + "']").popover('destroy');
				}else{
					$("[name='" + _targetName + "']").popover({placement:'top'});
					$("[name='" + _targetName + "']").popover('show');
				}
			}
		},
		checkFormat : function(){
			var field = hUtil.table.tHead;
			
			for(var i = 0; i < field.length; ++i){
				if(field[i].popover && field[i].isShow){
					var name = field[i].name,
						value = $('[name="' + name + '"]').val();
					if(field[i].regex === ''){
						if(value === ''){
							$("[name='" + name + "']").popover({placement:'top'});
							$("[name='" + name + "']").popover('show');
							return false;
						}else{
							$("[name='" + name + "']").popover('destroy');
						}
					}else{
						//需完善正则判断
						var reg = new RegExp( field[i].regex );
						if(reg.test(value)){
							$("[name='" + name + "']").popover('destroy');
						}else{
							$("[name='" + name + "']").popover({placement:'top'});
							$("[name='" + name + "']").popover('show');
							return false;
						}
					}
				}
			}
			return true;
		},
		/**
		 * 表操作
		 */
		operate : function( _name, _func, _uri, _index ){
			var items = hUtil.table.selectItems,
				operateFlag = false,
				param,
				field = hUtil.table.tHead,
				switchFields = [];
			
			function isSingle(){
				if(items.length === 0){
					$("#alertTitle").html( _name );
					$("#alertBody").html( '请先选择您需要 ' + _name + ' 的信息，谢谢~' );
					$("#tableAlert").modal('show');
				}else if(items.length == 1){
					return true;
				}else{
					$("#alertTitle").html( _name );
					$("#alertBody").html( '只能选择一条信息' + _name + '，谢谢~' );
					$("#tableAlert").modal('show');
				}
				return false;
			}
			
			//初始化开关插件
			function switchInit(){
				if( switchFields.length > 0 ){
					for(var i = 0; i < switchFields.length; i++){
						$('#' + switchFields[i].ID).bootstrapSwitch(switchFields[i].option || {});	
					}
					switchFields = [];
				}
			}
			
			switch( _func ){
			case 'add': 
				param = '';
				var _type = '', modalBody = '';
				for(var i = 0; i < field.length; ++i){
					_type = field[i].type;
					if(_type !== 'sys' && _type != 'tag' && _type !== 'INDEX' && _type !== 'badge' && field[i].isShow){
						modalBody += '<tr><td><label for="' + field[i].name + '" class="control-label">' + field[i].text + '</label></td>';
						if(_type === 'selected'){
							modalBody += '<td><select class="form-control" name="' + field[i].name + '">';
							var options = field[i].value;
							for(var j = 0; j < options.length; ++j){
								modalBody += '<option ' + (j === 0 ? 'selected="selected"' : '') + ' value="' + options[j].value + '">' + options[j].name + '</option>';
							}
							modalBody += '</select></td></tr>';	
						}else if(_type === 'switch'){
							var switched = field[i].switched;
							modalBody += '<td style="text-align:left;"><input name="' + field[i].name + '" id="' + field[i].name + 'Switch" type="checkBox" switchVal="' + switched.off.value + '"/></td>';
							switchFields.push({ID:field[i].name + 'Switch', option : {state:false, labelWidth:100, handleWidth:100, onText:switched.on.name || 'ON', offText:switched.off.name || 'OFF', onColor:switched.on.cls || 'primary', offColor:switched.off.cls || 'info', onSwitchChange:function(event, state){ $(this).attr('switchVal', state ? switched.on.value : switched.off.value); }}});
						}else if(_type === 'ajaxSelected'){
							var ajaxUri = field[i].ajaxUri;
							if(typeof ajaxUri !== 'undefined' && ajaxUri !== ''){
								$.ajax({
									type : "post",
									url : ajaxUri,
									async : false,
									success : function( data ){
										if(typeof data === 'string'){
											data = eval("(" + data + ")");
										}
										modalBody += '<td><select class="form-control" name="' + field[i].name + '" id="' + field[i].name + '">';
										var options = data, _fieldName = field[i].ajaxField;
										for(var j = 0; j < options.length; ++j){
											modalBody += '<option ' + (j === 0 ? 'selected="selected"' : '') + ' value="' + options[j].ID + '">' + options[j][ _fieldName ] + '</option>';
										}
										modalBody += '</select></td></tr>';
									},
									dataType:'json',
									timeout : 5000,
									error : function(req, error){
										console.log('Get ajaxSelected data err!');
									}
								});
							}
						}else{
							if(_type === 'user'){
								modalBody += '<td><input type="text" name="' + field[i].name + '" class="form-control" value="' + field[i].value + '" readonly="readonly"></td>\
											</tr>';									
							}else{
								modalBody += '<td><input type="text" class="form-control" name="' + field[i].name + '" placeholder="' + field[i].text + '"\
									' + (field[i].popover ? 'rel="popover" data-content="' + field[i].content + '" data-original-title="' + field[i].title + '"' : '') +
									'onblur="hUtil.table.checkInput(\'' + field[i].name + '\', \'' + (field[i].popover ? field[i].regex : '') + '\');"></td>\
									</tr>';	
							}
						}
					}
				}
				$("#tModalTitle").html(_name);
				$("#hTableBody").html(modalBody);
				$('#tableModal').modal('show');
				switchInit();
				operateFlag = true;
				break;
			case 'edit':
				param = '';
				if( isSingle() ){
					var modalBody = '', _type = '';
					for(var i = 0; i < field.length; ++i){
						_type = field[i].type;
						if(_type !== 'sys' && _type != 'tag' && _type !== 'badge' && field[i].isShow){
							modalBody += '<tr><td><label for="' + field[i].name + '" class="control-label">' + field[i].text + '</label></td>';
							if(_type === 'selected'){
								_val = hUtil.table.tBody[items[0]][field[i].name];
								var options = field[i].value,
									_sel_buf = '', _options = '';
								for(var j = 0; j < options.length; ++j){
									if(_val !== (options[j].value)){
										_options += '<option value="' + options[j].value + '">' + options[j].name + '</option>';
									}else{
										_sel_buf = '<td><select class="form-control" name="' + field[i].name + '"><option value="' + options[j].value + '">' + options[j].name + '</option>';
									}
								}
								modalBody += _sel_buf + _options + '</select></td></tr>';	
							}else if(_type === 'switch'){
								var switched = field[i].switched;
								modalBody += '<td style="text-align:left;"><input name="' + field[i].name + '" id="' + field[i].name + 'Switch" type="checkBox" switchVal="' + hUtil.table.tBody[items[0]][field[i].name] + '"/></td>';
								switchFields.push({ID:field[i].name + 'Switch', option : {state:hUtil.table.tBody[items[0]][field[i].name] == switched.on.value ? true : false, labelWidth:100, handleWidth:100, onText:switched.on.name || 'ON', offText:switched.off.name || 'OFF', onColor:switched.on.cls || 'primary', offColor:switched.off.cls || 'info', onSwitchChange:function(event, state){ $(this).attr('switchVal', state ? switched.on.value : switched.off.value); }}});
							}else if(_type === 'ajaxSelected'){
								var ajaxUri = field[i].ajaxUri;
								if(typeof ajaxUri !== 'undefined' && ajaxUri !== ''){
									$.ajax({
										type : "post",
										url : ajaxUri,
										async : false,
										success : function( data ){
											if(typeof data === 'string'){
												data = eval("(" + data + ")");
											}
											modalBody += '<td><select class="form-control" name="' + field[i].name + '" id="' + field[i].name + '">';
											var options = data, _fieldName = field[i].ajaxField, _val = hUtil.table.tBody[items[0]][field[i].name];
											for(var j = 0; j < options.length; ++j){
												modalBody += '<option ' + (_val === options[j][ field[i].name ] ? 'selected="selected"' : '') + ' value="' + options[j].ID + '">' + options[j][ _fieldName ] + '</option>';
											}
											modalBody += '</select></td></tr>';
										},
										dataType:'json',
										timeout : 5000,
										error : function(req, error){
											console.log('Get ajaxSelected data err!');
										}
									});
								}
							}else{
								modalBody += '<td><input type="text" class="form-control" ' + (field[i].readonly ? 'readonly="readonly"' : '') 
										     + ' name="' + field[i].name + '" placeholder="' + field[i].text + '" value="' 
										     + (_type === 'user' ? field[i].value : hUtil.table.tBody[items[0]][field[i].name]) + '" '
										     + (field[i].popover ? 'rel="popover" data-content="' + field[i].content + '" data-original-title="' + field[i].title + '"' : '') 
										     + 'onblur="hUtil.table.checkInput(\'' + field[i].name + '\', \'' + (field[i].popover ? field[i].regex : '') + '\');"></td>\
										     </tr>';
							}
						}
					}
					$("#tModalTitle").html(_name);
					$("#hTableBody").html(modalBody);
					$('#tableModal').modal('show');
					switchInit();
				}
				operateFlag = true;
				break;
			case 'part'://针对部分字段数据更改
				param = '';
				if( isSingle() ){
					var modalBody = '', currItem = hUtil.table.tBody[items[0]];
					if(_uri.indexOf('#') > -1){
						var _fields = _uri.substring(_uri.indexOf('#') + 1).split('&');
						_uri = _uri.substring(0, _uri.indexOf('#'));
						for(var i = 0; i < field.length; ++i){
							for(var j = 0; j < _fields.length; ++j){
								if(field[i].name === _fields[j]){
									modalBody += '<tr><td><label for="' + field[i].name + '" class="control-label">' + field[i].text + '</label></td>\
												<td><input type="text" class="form-control" name="' + field[i].name + '" ' + (currItem[_fields[j]] !== '' ? 'value="' + currItem[_fields[j]] + '"' : '') + ' placeholder="' + field[i].text + '"\
													' + (field[i].popover ? 'rel="popover" data-content="' + field[i].content + '" data-original-title="' + field[i].title + '"' : '') + '\
													onblur="hUtil.table.checkInput(\'' + field[i].name + '\', \'' + (field[i].popover ? field[i].regex : '') + '\');"></td>\
												</tr>';	
								}								
							}
						}								
						
						$("#tModalTitle").html(_name);
						$("#hTableBody").html(modalBody);
						$('#tableModal').modal('show');
						$("#tableBtn").unbind("click");//去除之前的click时间
						$("#tableBtn").click(function(){
							var _buf = '', _flag = 0, _param = '{ID:' + currItem.ID + ', ';
							for(var j = 0; j < _fields.length; ++j){
								_param += _fields[j] + ':\'' + $('[name="' + _fields[j] + '"]').val() + '\'';
								if(j !== _fields.length - 1){
									_param += ', '
								}else{
									_param += '}';
								}
							}
							if(_flag === _fields.length){
								$("#tableBtn").unbind("click");
								$('#tableModal').modal('hide');
								$("#alertTitle").html( _name );
								$("#alertBody").html('未修改信息~' );
								$("#tableAlert").modal('show');
							}else{
								_param = eval('(' + _param + ')');
								hUtil.table.ajax(_name, _uri, _param);
							}
						});
					}
					operateFlag = false;
				}
				break;
			case 'remove': 
				param = '';
				if(items.length === 0){
					$("#alertTitle").html( _name );
					$("#alertBody").html( '请先选择您需要移除的信息，谢谢~' );
					$("#tableAlert").modal('show');
				}else{
					var premise = hUtil.table.tUtil[ _index ].premise, 
						currItem = {};
					operateFlag = true;
					if(typeof premise !== 'undefined'){
						for(var j = 0; j < items.length; ++j){
							currItem = hUtil.table.tBody[items[j]];
							for(var i = 0; i < premise.length; ++i){
								if(currItem[ premise[i].name ] == premise[i].value){
									$("#alertTitle").html(premise[i].text);
									$("#alertBody").html('ID 为  ' + currItem.ID + premise[i].debug);
									$('#tableAlert').modal('show');
									operateFlag = false;
									return;
								}
							}
						}
					}
					if( operateFlag ){
						$("#tModalTitle").html(_name);
						$("#hTableBody").html('是否确定移除当前选中的 ' + items.length + ' 条数据？');
						$('#tableModal').modal('show');
					}
				}
				break;
			case 'delete': 
				param = '';
				if(items.length === 0){
					$("#alertTitle").html( _name );
					$("#alertBody").html( '请先选择您需要删除的信息，谢谢~' );
					$("#tableAlert").modal('show');
				}else{
					$("#tModalTitle").html(_name);
					$("#hTableBody").html('是否确定彻底删除当前选中的 ' + items.length + ' 条数据？');
					$('#tableModal').modal('show');
					operateFlag = true;
				}
				break;
			case 'direct'://分析uri获取选中条目的某个参数并传递到请求-->支持单条数据操作时
				param = '';
				if( isSingle() ){
					$("#tModalTitle").html(_name);
					$("#hTableBody").html('是否确定' + _name + '？');
					$('#tableModal').modal('show');
					$("#tableBtn").unbind("click");
					
					var fields, currItem = hUtil.table.tBody[ items[0] ];
					
					if(_uri.indexOf('>') > 0){
						fields = _uri.split('>');
						_uri = fields[0];
						
						param = '{';
						for(var i = 1; i < fields.length; i++){
							if( fields[i] ){
								param += '\'' + fields[i] + '\':\'' + currItem[ fields[i] ] + '\'';
								if(i != fields.length - 1){
									param += ',';
								}
							}
						}
						param += '}';
					}
					$("#tableBtn").click(function(){
						if( typeof param == 'string' ){
							param = eval('(' + param + ')');
						}
						hUtil.table.ajax(_name, _uri, param);
					});
				}
				operate = false;
				break;
			case 'refresh': //该请求获取新的table数据
				if( _uri ){
					hUtil.table.refresh( _uri );
				}else{
					$("#alertTitle").html( _name );
					$("#alertBody").html( '请求地址为空，请先检查，谢谢~' );
					$("#tableAlert").modal('show');
				}
				operate = false;
				break;
			case 'default': //直接执行请求
				$("#tModalTitle").html(_name);
				$("#hTableBody").html('是否确定' + _name + '？');
				$('#tableModal').modal('show');
				$("#tableBtn").unbind("click");
				$("#tableBtn").click(function(){
					$("#hTableBody").html('正在执行，请稍后。。。');
					hUtil.table.ajax(_name, _uri, {});
				});
				operate = false;
				break;
			case 'submit': //对多条进行操作，传递参数：ID集合
				param = '';
				if(items.length === 0){
					$("#alertTitle").html( _name );
					$("#alertBody").html( '请先选择您需要 ' + _name + ' 的信息，谢谢~' );
					$("#tableAlert").modal('show');
				}else{
					var premise = hUtil.table.tUtil[ _index ].premise, 
						currItem = {};
					operateFlag = true;
					if( premise ){
						for(var j = 0; j < items.length; ++j){
							currItem = hUtil.table.tBody[items[j]];
							for(var i = 0; i < premise.length; ++i){
								if(currItem[ premise[i].name ] == premise[i].value){
									$("#alertTitle").html(premise[i].text);
									$("#alertBody").html('ID 为  ' + currItem.ID + premise[i].debug);
									$('#tableAlert').modal('show');
									operateFlag = false;
									return;
								}
							}
						}
					}
					if( operateFlag ){
						var flag = hUtil.table.checkParam(_uri);
						if( flag === -1 ){
							$("#tModalTitle").html(_name);
							$("#hTableBody").html('是否确定 ' + _name + ' 当前选中的 ' + items.length + ' 条数据？');
							$('#tableModal').modal('show');
							operateFlag = true;
						}else{
							$("#alertTitle").html( _name );
							$("#alertBody").html( 'ID 为  ' + flag + ' 的条目信息已' + _name + '~' );
							$("#tableAlert").modal('show');
							operateFlag = false;
						}
					}
				}
				break;
			case 'extend': //对单条数据执行扩展方法
				operateFlag = false;
				if( isSingle() ){
					Extend[ hUtil.table.tUtil[ _index ].extend ](_name, items[0]);
				}
				break;
			case 'extendMulit': //对多条数据执行扩展方法
				operateFlag = false;
				if(items.length === 0){
					$("#alertTitle").html( _name );
					$("#alertBody").html( '请先选择您需要 ' + _name + ' 的信息，谢谢~' );
					$("#tableAlert").modal('show');
				}else{
					Extend[ hUtil.table.tUtil[ _index ].extend ]( _name, items );
				}
				break;
			}
			
			if( operateFlag ){
				$("#tableBtn").unbind("click");
				//执行operate
				$("#tableBtn").click(function(){
					if(_func === 'add' || _func === 'edit'){
						if( !hUtil.table.checkFormat() ){
							$("#hTableWarn").html("您的信息不完整，请补全后再提交，谢谢~");
							return;
						}
						param = hUtil.table.getItem( _func );
					}else{
						param = '{\'IDs\' : \'';
						for(var i = 0; i < items.length; ++i){
							//此处默认删除是按照主键ID，如需拓展可根据field中type=INDEX参数进行判断
							param += hUtil.table.tBody[items[i]].ID;
							if(i != items.length - 1){
								param += ',';
							}
						}
						param += '\'}';
					}
					if(typeof param === 'string'){
						param = eval('(' + param + ')');
					}
					hUtil.table.ajax(_name, _uri, param);	
				});
			}
		},
		ajax : function(_name, _uri, _param){
			$("#tableBtn").unbind("click");
			if(_uri !== ''){
				$.ajax({
					type : "post",
					url : _uri,
					data : _param,
					async : false,
					success : function( data ){
						if(typeof data === 'string'){
							data = eval("(" + data + ")");
						}
						$("#tableModal").modal('hide');
						$("#alertTitle").html(_name);
						if(data.success){
							$("#alertBody").html(_name + '成功！');
						}else{
							$("#alertBody").html(_name + '失败！');
						}
						$('#tableAlert').modal('show');
						$('#alertBtn').click(function(){
							$('#alertBtn').unbind('click');
							hUtil.table.getData(hUtil.table.tCurrPage, hUtil.table.tPageSize);
						});
					},
					dataType:'json',
					timeout : 5000,
					error : function(req, error){
						alert('Get table data err!');
					}
				});
			}
		},
	}
});


Class('hUtil.Util', null, {
	Static : {
		isNull : function( _param ){
			if(typeof _param == 'object'){
				if( _param == null ){
					return true;
				}
			}else if(typeof _param == 'string'){
				if( _param == '' ){
					return true;
				}
			}else if(typeof _param == 'array'){
				if(_param.length == 0){
					return true;
				}
			}else if(typeof _param == 'undefined'){
				return true;
			}
			return false;
		}
	}
});

Class('hUtil.Time', null, {
	Static : {
		getCurrTime : function(){
			 var now = new Date(); 
		   		strYear = now.getFullYear(),     
		    	strDay = now.getDate(),     
		    	strMonth = now.getMonth()+1,
		    	strHour = now.getHours(),
		    	strMin = now.getMinutes(),
		    	strSec = now.getSeconds();
		   		
		   	return (strMonth < 10 ? '0' + strMonth : strMonth) + "/" + (strDay < 10 ? '0' + strDay : strDay) + "/" + strYear + " " + (strHour < 10 ? '0' + strHour : strHour) + ":" + (strMin < 10 ? '0' + strMin : strMin) + ":" + (strSec < 10 ? '0' + strSec : strSec);
		},
		getDifferDay : function( _num ){ //+ 将来天数， - 以前天数      
		    var differDay = new Date(); 
		   		differDay.setTime((new Date()).getTime() + _num*1000*60*60*24),
		   		strYear = differDay.getFullYear(),     
		    	strDay = differDay.getDate(),     
		    	strMonth = differDay.getMonth()+1,
		    	strHour = differDay.getHours(),
		    	strMin = differDay.getMinutes(),
		    	strSec = differDay.getSeconds();
		    
		    return (strMonth < 10 ? '0' + strMonth : strMonth) + "/" + (strDay < 10 ? '0' + strDay : strDay) + "/" + strYear + " " + (strHour < 10 ? '0' + strHour : strHour) + ":" + (strMin < 10 ? '0' + strMin : strMin) + ":" + (strSec < 10 ? '0' + strSec : strSec);   
		}
	}
});

Class('hUtil.extend', null, {
	Static : {
		//深层拷贝，避免内存复用
		deepCopy : function( _type, src ){
			if( _type === 'array' ){
				return $.extend(true, [], src); 	
			}else if( _type === 'object' ){
				return $.extend(true, {}, src);
			}
		},
	}
});
	
