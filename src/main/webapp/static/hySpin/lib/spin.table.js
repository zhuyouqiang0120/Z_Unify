/**
 * 工具类
 * 表格（table）
 * 
 * 依赖 jQuery v2.0.3
 * 	   bootstrap v3.3.2
 * 	   netname v1.0
 * 
 * @Author hy.spin
 * @date 2015/8/12
 * */

Class('spin.table', null, {
	Static : {
		currPage : 1,
		pageSize : 15,
		orderCase : '',
		count : 0,
		list : [],
		uri : [],
		field : [],
		util : [],
		isAllowMulit : false, //是否允许多选
		pre_index : -1,
		selectItems : [],
		init : function( _conf ){
			_conf = _conf || {};
			
			this.pageSize = _conf.pageSize || 15;
			this.orderCase = _conf.orderCase || '';
			
			this.field = _conf.field || [];
			this.util = _conf.util || [];
			this.isAllowMulit = _conf.isAllowMulit || false;
			this.uri = _conf.uri || '';
			
			//初始化组件
			$("#spin-table").html('<div id="table-util"></div>' + 
								  '<table id="table-content" class="table table-bordered table-hover">' + 
								  	'<thead id="table-head"></thead>' + 
									'<tbody id="table-body"></tbody>' + 
								  '</table>' + 
								  '<div id="table-pagination"></div>');
			
			this.drawUtil(); //绘制动作组
			this.drawHead();
			this.getData(this.currPage, this.pageSize, ''); //获取表格数据并绘制
		},
		refresh : function( _uri ){
			spin.table.uri = _uri;
			spin.table.getData(1, spin.table.pageSize, ''); //重新初始化第一页，获取表格数据并绘制
		},
		getData : function( _currPage, _pageSize, _orderCase ){
			if( this.uri !== ''){
				if( !_orderCase ){
					_orderCase = '';
				}else{
					this.orderCase = _orderCase; 
				}
				$("#loading").show();
				$.ajax({
					type : "post",
					url : spin.table.uri + '&currPage=' + _currPage + '&pageSize=' + _pageSize + '&orderCase=' + _orderCase,
					async : false,
					success : function( data ){
						if(typeof data === 'string'){
							data = eval("(" + data + ")");
						}
						$("#loading").hide();
						//清空选中记录
						spin.table.selectItems = [];
						
						spin.table.currPage = _currPage;
						spin.table.pageSize = _pageSize;
						spin.table.count = data.totalRow; //JFinal返回
						spin.table.list = data.list; //JFinal返回
						spin.table.drawTable();
						spin.table.drawPagination(_currPage, _pageSize, data.totalRow);
					},
					dataType:'json',
					timeout : 5000,
					error : function(req, error){
						alert('Get table data err!');
					}
				});
			}
		},
		drawUtil : function(){
			var tUtil = spin.table.util;
			if(tUtil.length > 0){
				$('#table-util').html( spin.button.getButton( tUtil, 'table' ) );
			}else{
				console.log('Table Util is null');
			}
		},
		drawHead : function(){
			var thead = spin.table.field || [], headHtml = '';
			if( thead.length > 0 ){
				if( this.isAllowMulit ){
					headHtml += '<tr><td width="3%" onclick="spin.table.selectAll();"><span name="tdBox" id="theadTd" class="glyphicon glyphicon-unchecked"></span></td>';
				}else{
					headHtml += '<tr><td><span name="tdBox" id="theadTd" class="glyphicon glyphicon-unchecked"></span></td>';
				}
				for(var j = 0; j < thead.length; ++j){
					if( thead[j].isShow ){
						headHtml += '<td>' + thead[j].text + '&nbsp;<button type="button" onclick="spin.table.sort(\'' + thead[j].name + '\');" class="btn btn-link btn-xs" aria-label="Left Align">' +
  										'<span id="sort_' + thead[j].name + '" class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>' +
  										'</button>' + 
									'</td>';
					}
				}
				headHtml += '</tr>';
			}
			$("#table-head").html( headHtml );
		},
		drawTable : function(){
			function isUndefined( o ){
				if( o == 0 ){
					return o;
				}
				return o || '';
			}
			
			var	tbody = spin.table.list, thead = spin.table.field, 
				bodyHtml = '', field = [];
			if(tbody.length > 0){
				for(var i = 0; i < tbody.length; ++i){
					bodyHtml += '<tr onclick="spin.table.selectRow(' + i + ');">\
								<td><span name="tdBox" id="tbodyTd_' + i + '" class="glyphicon glyphicon-unchecked"></span></td>';
					for(var j = 0; j < thead.length; ++j){
						var value = tbody[i][thead[j].name];
						if( thead[j].isShow ){
							var _type = thead[j].type;
							if(_type === 'tag' || _type == 'switch' ){
								bodyHtml += '<td>' + isUndefined( thead[j].value[ tbody[i][thead[j].name] ] ) + '</td>';
							}else if(_type === 'linkBtn'){
								var addr = tbody[i][thead[j].name];
								bodyHtml += '<td><button ' + (addr == '' ? 'disabled="disabled"' : '') + 'class="btn btn-primary btn-xs" onclick="window.open(\'' + addr  + '\');"><span class="glyphicon glyphicon-search"></span> 查看</button></td>';	
							}else if(_type === 'selected'){
								value = isUndefined( value );
								if(thead[j].css !== {}){
									bodyHtml += '<td>' + isUndefined( thead[j].css[ value ] ) + '</td>';
								}else{
									bodyHtml += '<td>' + value + '</td>';
								}
							}else if(_type === 'badge'){
								bodyHtml += '<td><span class="badge">' + isUndefined( value ) + '</span></td>';
							}else if(_type === 'rangetime'){
								value = value ? eval('(' + value + ')') : {start:'', end:''};
								bodyHtml += !value.end || ( value.end > spin.time.getCurrTime() ) ? '<td><span class="label label-info">有效</span></td>' : '<td><span class="label label-danger">过期</span></td>';
							}else{
								bodyHtml += '<td>' + isUndefined( value ) + '</td>';
							}
						}
					}
					bodyHtml += '</tr>';
				}
			}else{
				console.log('Table\' list is null!');
			}
			$("#table-body").html( bodyHtml );
		},
		drawPagination : function( _currPage, _pageSize, _count ){
			var end = Math.ceil(_count / _pageSize),
				orderCase = spin.table.orderCase;
			var paginHtml = '<ul class="pagination" style="float:left;margin:1px;">\
								<li><button class="btn btn-default" ' + (_currPage === 1 ? 'disabled="disabled"' : '') + ' onclick="spin.table.getData(1, ' + _pageSize + ', \'' + orderCase + '\');"><span class="glyphicon glyphicon-fast-backward"></span></button></li>\
								<li><button class="btn btn-default" ' + (_currPage === 1 ? 'disabled="disabled"' : '') + ' onclick="spin.table.getData(' + (_currPage - 1) +', ' + _pageSize + ', \'' + orderCase + '\');"><span class="glyphicon glyphicon-chevron-left"></span></button></li>\
								<li><button class="btn btn-default" disabled="disabled"><span>' + _currPage + '</span></button></li>\
								<li><button class="btn btn-default" ' + (_currPage === end || end === 0 ? 'disabled="disabled"' : '') + ' onclick="spin.table.getData(' + (_currPage + 1) +', ' + _pageSize + ', \'' + orderCase + '\');"><span class="glyphicon glyphicon-chevron-right"></span></button></li>\
								<li><button class="btn btn-default" ' + (_currPage === end || end === 0 ? 'disabled="disabled"' : '') + ' onclick="spin.table.getData(' + end +', ' + _pageSize + ', \'' + orderCase + '\');"><span class="glyphicon glyphicon-fast-forward"></span></button></li>\
							</ul>\
							<div style="float:right"><label class="control-label">当前第<button class="btn btn-default">' + _currPage + '</button>页,共' + end + '页</label></div>';
			
			$("#table-pagination").html( paginHtml );
		},
		sort : function( _field_name ){
			if( _field_name ){
				var _span = $('#sort_' + _field_name), _orderCase = _field_name;
				if( _span.attr('class') === 'glyphicon glyphicon-menu-down' ){
					_span.attr('class', 'glyphicon glyphicon-menu-up');
					_orderCase += ' ASC';
				}else{
					_span.attr('class', 'glyphicon glyphicon-menu-down');
					_orderCase += ' DESC';
				}
				spin.table.getData(spin.table.currPage, spin.table.pageSize, _orderCase);
			}else{
				console.log('The orderCase is null!');
			}
		},
		selectRow : function( _index ){
			if( !spin.table.isAllowMulit ){
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
					spin.table.selectItems.push( _index );
				}else{
					$("#tbodyTd_" + _index).attr('class', 'glyphicon glyphicon-unchecked');
					var buf = [], items = spin.table.selectItems;
					for(var i = 0; i < items.length; ++i){
						if(items[i] !== _index){
							buf.push(items[i]);
						}
					}
					spin.table.selectItems = buf;
				}
			}
		},
		selectAll : function(){
			if($("#theadTd").hasClass('glyphicon glyphicon-unchecked')){
				$("[name='tdBox']").attr('class', 'glyphicon glyphicon-check');
				spin.table.selectItems = [];
				for(var i = 0; i < spin.table.list.length; ++i){
					spin.table.selectItems.push(i);
				}
			}else{
				$("[name='tdBox']").attr('class', 'glyphicon glyphicon-unchecked');
				spin.table.selectItems = [];
			}
		},
		/** 检测uri中需要修改的参数 */
		checkParam : function( _uri ){
			var _indexF = _uri.indexOf('?');
			if(_indexF === -1){
				return -1;
			}else{
				var param = _uri.substring(_indexF + 1),
					flag_param = '', _indexJ = param.indexOf('#'), 
					_buf = '', name = '', val = '', items = spin.table.selectItems, tBody = spin.table.list,
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
		/** 表操作 */
		operate : function( _name, _func, _uri, _index ){
			var items = spin.table.selectItems,
				operateFlag = false,
				param,
				field = spin.table.field,
				switchFields = [],
				content = '';
			
			switch( _func ){
			case 'add': 
				param = '';
				content = spin.form.getForm( 'add', field );
				operateFlag = true;
				break;
			case 'edit':
				param = '';
				if( items.length === 1 ){
					content = spin.form.getForm( 'edit', field, spin.table.list[items[0]] );
					operateFlag = true;
				}else if( items.length === 0 ){
					content = '请先选择您需要 ' + _name + ' 的信息，谢谢~';
				}else{
					content = '只能选择一条信息' + _name + '，谢谢~';
				}
				break;
			case 'part'://针对部分字段数据更改
				param = '';
				if( items.length === 1 ){
					if(_uri.indexOf('#') > -1){
						spin.form.getForm('part', field, spin.table.list[items[0]], _uri); //获取部分字段form
						
						spin.modal.show(1, _name, true, content, function(){
							var _buf = '', _flag = 0, _param = '{ID:' + currItem.ID + ', ',
								_fields = _uri.substring(_uri.indexOf('#') + 1).split('&');
							for(var j = 0; j < _fields.length; ++j){
								_param += _fields[j] + ':\'' + $('[name="' + _fields[j] + '"]').val() + '\'';
								if(j !== _fields.length - 1){
									_param += ', '
								}else{
									_param += '}';
								}
							}
							if(_flag === _fields.length){
								spin.modal.show( 1, _name, false, '未修改信息~' );
							}else{
								_param = eval('(' + _param + ')');
								spin.table.ajax(_name, _uri, _param);
							}
						});
					}
					operateFlag = false;
				}else{
					content = '请只选择一条信息进行操作~';
				}
				break;
			case 'remove': 
				param = '';
				if(items.length === 0){
					content = '请先选择您需要移除的信息，谢谢~'
				}else{
					var premise = spin.table.util[ _index ].premise, 
						currItem = {};
					operateFlag = true;
					if( premise ){
						for(var j = 0; j < items.length; ++j){
							currItem = spin.table.list[items[j]];
							for(var i = 0; i < premise.length; ++i){
								if(currItem[ premise[i].name ] == premise[i].value){
									spin.modal.show( 1, premise[i].text, false, 'ID 为  ' + currItem.ID + premise[i].debug );
									operateFlag = false;
									return;
								}
							}
						}
					}
					if( operateFlag ){
						content = '是否确定移除当前选中的 ' + items.length + ' 条数据？';
					}
				}
				break;
			case 'delete': 
				param = '';
				if(items.length === 0){
					content = '请先选择您需要删除的信息，谢谢~';
				}else{
					content = '是否确定彻底删除当前选中的 ' + items.length + ' 条数据？';
					operateFlag = true;
				}
				break;
			case 'direct'://分析uri获取选中条目的某个参数并传递到请求-->支持单条数据操作时
				param = '';
				if( items.length === 1 ){
					var fields, currItem = spin.table.list[ items[0] ] || {};
					
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
						param = eval('(' + param + ')');
					}
					
					spin.modal.show(1, _name, true, '是否确定' + _name + '？', function(){
						spin.table.ajax( _name, _uri, param);
					});
					return;
				}else{
					content = '请只选择一条信息进行操作，谢谢~';
				}
				break;
			case 'refresh': //该请求获取新的table数据
				if( _uri ){
					spin.table.refresh( _uri );
					return;
				}else{
					content = '请求地址为空，请先检查，谢谢~';
				}
				operate = false;
				break;
			case 'default': //直接执行请求
				spin.modal.show( 1, _name, true, '是否确定' + _name + '？', function(){
					spin.table.ajax(_name, _uri, {});
				});
				return;
			case 'submit': //对多条进行操作，传递参数：ID集合
				param = '';
				if(items.length === 0){
					content = '请先选择您需要 ' + _name + ' 的信息，谢谢~';
				}else{
					var premise = spin.table.util[ _index ].premise, 
						currItem = {};
					operateFlag = true;
					if( premise ){
						for(var j = 0; j < items.length; ++j){
							currItem = spin.table.list[ items[j] ];
							for(var i = 0; i < premise.length; ++i){
								if( premise[i].type ){
									if( premise[i].type === 'rangetime' ){
										var rangeTime = currItem[ premise[i].name ],
											rangeTime = typeof rangeTime === 'string' ? eval('(' + rangeTime + ')') : rangeTime ;
										if( ( rangeTime.end > spin.time.getCurrTime() ) === premise[i].value ){
											spin.modal.show( 1, premise[i].text, false, 'ID 为  ' + currItem.ID + premise[i].debug );
											operateFlag = false;
											return;
										}
									}
								}else{
									if(currItem[ premise[i].name ] === premise[i].value){
										spin.modal.show(1, premise[i].text, false, 'ID 为  ' + currItem.ID + premise[i].debug);
										operateFlag = false;
										return;
									}
								}
							}
						}
					}
					if( operateFlag ){
						var flag = spin.table.checkParam( _uri );
						if( flag === -1 ){
							content = '是否确定 ' + _name + ' 当前选中的 ' + items.length + ' 条数据？';
							operateFlag = true;
						}else{
							content = 'ID 为  ' + flag + ' 的条目信息已' + _name + '~';
							operateFlag = false;
						}
					}
				}
				break;
			case 'extend': //对数据执行扩展方法
				operateFlag = false;
				var item = spin.table.util[ _index ].item || '';
				if( item === 'none' ){
					Extend[ spin.table.util[ _index ].extend ](_name, items[0]);
					return;
				}else if( item === 'single' ){
					if( items.length === 1 ){
						Extend[ spin.table.util[ _index ].extend ](_name, items[0]);
						return;
					}else{
						content = '请只选择一条信息进行操作~';
					}
				}else if( item === 'mulit' ){
					if(items.length === 0){
						content = '请先选择您需要 ' + _name + ' 的信息，谢谢~';
					}else{
						Extend[ spin.table.util[ _index ].extend ]( _name, items );
						return;
					}
				}
				break;
			}
			
			if( operateFlag ){
				if(_func === 'add' || _func === 'edit'){
					spin.modal.show(1, _name, true, content);
					setTimeout( spin.form.formModuleInit, 2 );//组件初始化
					
					if( !spin.form.checkFormat( field ) ){
						spin.form.error("您的信息不完整，请补全后再提交，谢谢~");
					}else{
						spin.form.error('');
						spin.modal.bind(function(){
							param = spin.form.getItem( _func, field, spin.table.list[ items[0] ] );
							if( param ){
								if(typeof param === 'string'){
									param = eval('(' + param + ')');
								}
								spin.table.ajax(_name, _uri, param);
							}
						});
					}
				}else{
					spin.modal.show(1, _name, true, content, function(){
						param = '{\'ids\' : \'';
						for(var i = 0; i < items.length; ++i){
							//此处默认删除是按照主键ID，如需拓展可根据field中type=INDEX参数进行判断
							param += spin.table.list[items[i]].ID;
							if(i != items.length - 1){
								param += ',';
							}
						}
						param += '\'}';
						if(typeof param === 'string'){
							param = eval('(' + param + ')');
						}
						spin.table.ajax(_name, _uri, param);	
					});
				}
			}else{
				spin.modal.show( 1, _name, false, content );
			}
		},
		ajax : function(_name, _uri, _param){
			if( _uri ){
				$.ajax({
					type : "post",
					url : _uri,
					data : _param,
					async : false,
					success : function( data ){
						if(typeof data === 'string'){
							data = eval("(" + data + ")");
						}
						spin.modal.show( 350, _name, true, _name + (data.success ? '成功！' : '失败！' ), function(){
							spin.table.getData(spin.table.currPage, spin.table.pageSize);
							spin.modal.hide();
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