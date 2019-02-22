/**
 * 工具类
 * 组 group
 * 
 * 依赖 jQuery v2.0.3
 * 	   bootstrap v3.3.2
 * 	   netname v1.0
 * 
 * @Author hy.spin
 * @date 2015/8/13
 * */

Class('spin.group', null, {
	Static : {
		conf : {},
		currPage : 1, 
		pageSize : 15,
		orderCase : '',
		count : 0,
		list : [],
		selectGroups : [],
		init : function( _conf, _target ){
			this.conf = _conf || {};
			
			this.currPage = 1;
			this.pageSize = _conf.pageSize || 10;
			this.orderCase = _conf.orderCase || '';

			this.drawUtil();
			this.getGroup( this.currPage, this.pageSize, this.orderCase );
		},
		getGroup : function( _currPage, _pageSize, _orderCase  ){
			if( spin.group.conf && spin.group.conf.uri ){
				if( !_orderCase ){
					_orderCase = '';
				}else{
					spin.group.tOrderCase = _orderCase; 
				}
				$("#loading").show();
				$.ajax({
					type : "post",
					url : spin.group.conf.uri + '&currPage=' + _currPage + '&pageSize=' + _pageSize + '&orderCase=' + _orderCase,
					async : false,
					success : function( data ){
						if(typeof data === 'string'){
							data = eval("(" + data + ")");
						}
						$("#loading").hide();
						//清空选中记录
						spin.group.selectGroups = [];
						
						spin.group.currPage = _currPage;
						spin.group.pageSize = _pageSize;

						spin.group.count = data.totalRow; //JFinal返回
						spin.group.list = data.list; //JFinal返回
						
						spin.group.drawGroup();
						spin.group.drawPagination(_currPage, _pageSize, data.totalRow);
					},
					dataType:'json',
					timeout : 5000,
					error : function(req, error){
						alert('Get group data err!');
					}
				});
			}else{
				console.log('URI配置有误');
			}
		},
		drawGroup : function(){
			var list = this.list || [],
				listHTML = '';
			
			if( list.length > 0 ){
				for(var i = 0; i < list.length; ++ i){
					listHTML += '<div id="group-box">' + 
									'<div class="box-title"><h4>' + (list[i].Name || '') + '</h4></div>' +
									'<div class="box-summary"><small><span class="glyphicon glyphicon-map-marker"></span> ' + (list[i].Summary || '') + '</small></div>' +
									'<div class="box-footer">' + 
										'<a class="footer-left" data-toggle="tooltip" data-placement="bottom" title="选中组" onclick="spin.group.selectGroup(' + i + ');"><span id="group' + i + '" class="glyphicon glyphicon-unchecked"></span></a>' + 
										'<div class="footer-center" data-toggle="tooltip" data-placement="bottom" title="创建时间"><small><span class="glyphicon glyphicon-calendar"></span> ' + (list[i].CreateTime.substring(0, 10) || '') + '</small></div>' + 
										'<a class="footer-right" data-toggle="tooltip" data-placement="bottom" title="进入组" onclick="spin.group.enterGroup(' + i + ');"><span class="glyphicon glyphicon-share-alt"></span></a>' + 
									'</div>' +
								'</div>';
				}
				if( $('#group-core').size() === 0 ){
					$('#spin-group').append('<div id="group-core">' + listHTML + '</div>');
				}else{
					$('#group-core').html( listHTML );
				}
				$('[data-toggle="tooltip"]').tooltip(); //bootstrap 提示工具初始化
			}else{
				console.log('Group\'s list is empty!');
			}
		},
		drawPagination : function(){
			
		},
		drawUtil : function(){
			var gUtil = spin.group.conf.util || [];
			if( gUtil.length > 0 ){
				$('#spin-group').append( '<div class="group-util">' + spin.button.getButton( gUtil, 'group' ) + '</div>');
			}else{
				console.log('Table Util is null');
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
							spin.group.getGroup(spin.group.currPage, spin.group.pageSize);
							spin.modal.hide();
						} );
					},
					dataType:'json',
					timeout : 5000,
					error : function(req, error){
						alert('Get table data err!');
					}
				});
			}
		},
		selectGroup : function( _index ){
			if($("#group" + _index).hasClass('glyphicon glyphicon-unchecked')){
				$("#group" + _index).attr('class', 'glyphicon glyphicon-check');
				spin.group.selectGroups.push( _index );
			}else{
				$("#group" + _index).attr('class', 'glyphicon glyphicon-unchecked');
				var buf = [], items = spin.group.selectGroups;
				for(var i = 0; i < items.length; ++i){
					if(items[i] !== _index){
						buf.push(items[i]);
					}
				}
				spin.group.selectGroups = buf;
			}
		},
		selectAll : function(){
			if($("#theadTd").hasClass('glyphicon glyphicon-unchecked')){
				$("[name='tdBox']").attr('class', 'glyphicon glyphicon-check');
				spin.group.selectGroups = [];
				for(var i = 0; i < spin.group.list.length; ++i){
					spin.group.selectGroups.push(i);
				}
			}else{
				$("[name='tdBox']").attr('class', 'glyphicon glyphicon-unchecked');
				spin.group.selectGroups = [];
			}
		},
		operate : function( _name, _func, _uri, _idx ){
			var items = spin.group.selectGroups || [],
				field = spin.group.conf.field || [],
				operateFlag = false,
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
					content = spin.form.getForm( 'edit', field, spin.group.list[ items[0]] );
					operateFlag = true;
				}else if( items.length === 0 ){
					content = '请先选择您需要 ' + _name + ' 的信息，谢谢~';
				}else{
					content = '只能选择一条信息' + _name + '，谢谢~';
				}
				break;
			case 'remove': 
				param = '';
				if(items.length === 0){
					content = '请先选择您需要移除的信息，谢谢~';
				}else{
					var premise = spin.group.conf.field[ _idx ].premise, 
						currItem = {};
					operateFlag = true;
					if( premise ){
						for(var j = 0; j < items.length; ++j){
							currItem = spin.group.list[items[j]];
							for(var i = 0; i < premise.length; ++i){
								if(currItem[ premise[i].name ] == premise[i].value){
									spin.modal.show(1, premise[i].text, false, 'ID 为  ' + currItem.ID + premise[i].debug );
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
			}
			
			if( operateFlag ){
				if(_func === 'add' || _func === 'edit'){
					spin.modal.show(1, _name, true, content);
					setTimeout(spin.form.formModuleInit, 2);
					
					spin.modal.bind(function(){
						if( !spin.form.checkFormat( field ) ){
							spin.form.error("您的信息不完整，请补全后再提交，谢谢~");
							return;
						}else{
							spin.form.error('');
							param = spin.form.getItem( _func, field, spin.group.list[ items[0]]);
							
							if( param ){
								if(typeof param === 'string'){
									param = eval('(' + param + ')');
								}
								spin.group.ajax(_name, _uri, param);
							}
						}
					});
				}else{
					spin.modal.show(1, _name, true, content, function(){
						param = '{\'ids\' : \'';
						for(var i = 0; i < items.length; ++i){
							//此处默认删除是按照主键ID，如需拓展可根据field中type=INDEX参数进行判断
							param += spin.group.list[items[i]].ID;
							if(i != items.length - 1){
								param += ',';
							}
						}
						param += '\'}';
						if(typeof param === 'string'){
							param = eval('(' + param + ')');
						}
						spin.group.ajax(_name, _uri, param);	
					});
				}
			}else{
				spin.modal.show( 1, _name, false, content );
			}
		},
		enterGroup : function( _index ){
			var gid = spin.group.list[ _index ].GID || '';
			if( gid ){
				spin.group_inner.init( gid );
			}
		},
	}
});