/**
 * 工具类
 * modal 模态框
 * 
 * 依赖 jQuery v2.0.3
 * 	   bootstrap v3.3.2
 * 	   netname v1.0
 * 
 * @Author hy.spin
 * @date 2015/8/16
 * */

Class('spin.button', null, {
	Static : {
		getButton : function( _button, _sign ){
			_button = _button || [];
			if(_button.length > 0){
				var _leftHtml = '', _uri = '', _type = '', _rightHtml = '';
				for(var i = 0; i < _button.length; ++i){
					_uri = _button[i].uri, _type = _button[i].type, _direct = _button[i].direct;
					if(_type === 'btn'){
						var _bufHtml = '';
						_bufHtml += '<button type="button" class="' + _button[i].css + '" onclick="spin.' + _sign + '.operate(\'' + _button[i].name + '\', \'' + _button[i].func + '\', \'' + _uri + '\', ' + i + ')"><span class="' + _button[i].icon + '"></span> ' + _button[i].name + '</button>';
						if(_direct === 'left'){
							_leftHtml += _bufHtml;
						}else{
							_rightHtml += _bufHtml;
						}
					}else if(_type === 'btnG'){
						var _bufHtml = '';
						_bufHtml += '<div class="btn-group">\
				  			<button type="button" class="' + _button[i].css + ' dropdown-toggle" data-toggle="dropdown">\
								<span class="' + _button[i].icon + '"></span> ' + _button[i].name + '<span class="caret"></span>\
								</button><ul class="dropdown-menu" role="menu">';
						for(var j = 0; j < _uri.length; ++j){
							_bufHtml += '<li><a href="#" onclick="spin.' + _sign + '.operate(\'' + _uri[j].name + '\', \'' + _uri[j].func + '\', \'' + _uri[j].uri + '\')"><span class="' + _uri[j].icon + '"></span> ' + _uri[j].name + '</a></li>';
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
				return '<div class="pull-left btn-group">' + _leftHtml + '</div><div class="pull-right btn-group">' + _rightHtml + '</div>';
			}else{
				console.log('Button Util is null');
			}
		}
	}
});

/** 表单 */
Class('spin.form', null, {
	Static : {
		field : [],
		switchFields : [],//开关组件
		//组件初始化
		formModuleInit : function(){
			var switchFields = spin.form.switchFields || [];
			//switch开关组件
			if( switchFields.length > 0 ){
				for(var i = 0; i < spin.form.switchFields.length; i++){
					$('#' + switchFields[i].ID).bootstrapSwitch(switchFields[i].option || {});	
				}
				spin.form.switchFields = [];
			}
			//datetime时间组件
			if( $(".form-datetime").size() > 0 ){
				$(".form-datetime").datetimepicker({
				    format: "mm/dd/yyyy hh:ii",
				    autoclose: true,
				    todayBtn: true,
				    pickerPosition: "bottom-left"
				});
			}
		},
		/**输入检测 bootstrap popover */
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
		checkFormat : function( _field ){
			for(var i = 0; i < _field.length; ++i){
				if(_field[i].popover && _field[i].isShow){
					var name = _field[i].name,
						value = $('[name="' + name + '"]').val();
					if( _field[i].regex ){
						if( value ){
							$("[name='" + name + "']").popover({placement:'top'});
							$("[name='" + name + "']").popover('show');
							return false;
						}else{
							$("[name='" + name + "']").popover('destroy');
						}
					}else{
						//需完善正则判断
						var reg = new RegExp( _field[i].regex );
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
		error : function( _msg ){
			$('#form-warn').html( _msg ? '<span class="glyphicon glyphicon-warning-sign"></span> ' + _msg : '' );
		},
		/** 获取当前表单数据组合为实体对象 */
		getItem : function( _func, _field, _currItem ){
			var item = {}, name = '', value = '', type = '';
			
			for(var i = 0; i < _field.length; ++i){
				name = _field[i].name,
				value = $('[name="' + name + '"]').val() || '',
				type = _field[i].type;
				
				var val = _field[i].value;
				if( _field[i].isShow && type !== 'group' && type !== 'rangetime' ){
					if(type === 'ajaxSelected' ){
						item[ name ] = $('#' + name + ' option:selected').text() || '';
						item[ name + 'ID' ] = value;
					}else if(type === 'switch'){
						item[ name ] = $('[name="' + name + '"]').attr('switchVal');
					}else if( type === 'tag' ){ //初始值是数字
						item[ name ] = _func === 'edit' ? _currItem[ name ] : 0;
					}else if( type === 'sys' ){
						item[ name ] = _func === 'edit' ? _currItem[ name ] : val;
					}else{
						if( typeof val === 'number' || typeof val === 'string' ){
							item[ name ] = val;
						}else{
							item[ name ] = value || '';
						}
					}
				}else{
					item[ name ] = _func === 'edit' ? _currItem[ name ] : val;
				}
				if( type === 'group' ){
					value = spin.module.group.getValue();
					if( value ){
						item[ name ] = value;
					}else{
						spin.form.error('请至少补全两条选项~');
						return false;
					}
				}else if( type === 'rangetime' ){
					value = spin.module.rangetime.getValue( name );
					if( value ){
						item[ name ] = value;
					}else{
						spin.form.error('起止时间格式不正确~');
						return false;
					}
				}
			}
			return item;
		},
		getForm : function( _func, _field, _currItem, _uri ){
			this.field = _field || [];
			
			var formHTML = '';
			if( _func === 'add' ){
				var _type = '';
				for(var i = 0; i < _field.length; ++i){
					_type = _field[i].type;
					if( _type !== 'sys' && _type != 'tag' && _type !== 'INDEX' && _type !== 'badge' ){
						formHTML += '<div class="form-item">' +
										'<div class="item-label ' + ( _type === 'rangetime' || _type === 'group' ? 'col-md-2 label-time' : '') + '">' + _field[i].text + '</div>' +
										'<div class="item-input">';
						if(_type === 'selected'){
							formHTML += '<select class="form-control" name="' + _field[i].name + '">';
							var options = _field[i].value;
							for(var j = 0; j < options.length; ++j){
								formHTML += '<option ' + (j === 0 ? 'selected="selected"' : '') + ' value="' + options[j].value + '">' + options[j].name + '</option>';
							}
							formHTML += '</select>';
						}else if(_type === 'switch'){
							var switched = _field[i].switched;
							formHTML += '<input name="' + _field[i].name + '" id="' + _field[i].name + 'Switch" type="checkBox" switchVal="' + switched.off.value + '"/>';
							spin.form.switchFields.push({ID:_field[i].name + 'Switch', option : {state:false, labelWidth:100, handleWidth:100, onText:switched.on.name || 'ON', offText:switched.off.name || 'OFF', onColor:switched.on.cls || 'primary', offColor:switched.off.cls || 'info', onSwitchChange:function(event, state){ $(this).attr('switchVal', state ? switched.on.value : switched.off.value); }}});
						}else if(_type === 'ajaxSelected'){
							var ajaxUri = _field[i].ajaxUri || '';
							if( ajaxUri ){
								$.ajax({
									type : "post",
									url : ajaxUri,
									async : false,
									success : function( data ){
										if(typeof data === 'string'){
											data = eval("(" + data + ")");
										}
										formHTML += '<select class="form-control" name="' + _field[i].name + '" id="' + _field[i].name + '">';
										var options = data, __fieldName = _field[i].ajaxField;
										for(var j = 0; j < options.length; ++j){
											formHTML += '<option ' + (j === 0 ? 'selected="selected"' : '') + ' value="' + options[j].ID + '">' + options[j][ __fieldName ] + '</option>';
										}
										formHTML += '</select>';
									},
									dataType:'json',
									timeout : 5000,
									error : function(req, error){
										console.log('Get ajaxSelected data err!');
									}
								});
							}
						}else if( _type === 'group' ){
							formHTML += spin.module.group.getGroup( _field[i].name, _field[i].text );
						}else if( _type === 'rangetime' ){
							formHTML += spin.module.rangetime.getRangetime( _field[i].name );
						}else{
							if( _type === 'creator' || _type === 'operator' ){
								formHTML += '<input type="text" name="' + _field[i].name + '" class="form-control" value="' + _field[i].value + '" readonly="readonly">';									
							}else{
								formHTML += '<input type="text" class="form-control" name="' + _field[i].name + '" placeholder="' + _field[i].text + '"\
									' + (_field[i].popover ? 'rel="popover" data-content="' + _field[i].content + '" data-original-title="' + _field[i].title + '"' : '') +
									'onblur="spin.form.checkInput(\'' + _field[i].name + '\', \'' + (_field[i].popover ? _field[i].regex : '') + '\');">';	
							}
						}
						formHTML += '</div></div>';
					}
				}
			}else if( _func === 'edit' ){
				var _type = '';
				for(var i = 0; i < _field.length; ++i){
					_type = _field[i].type;
					
					if(_type !== 'sys' && _type != 'tag' && _type !== 'badge'){
						formHTML += '<div class="form-item">' +
										'<div class="item-label ' + ( _type === 'rangetime' || _type === 'group' ? 'col-md-2 label-time' : '') + '">' + _field[i].text + '</div>' +
										'<div class="item-input">';
						
						_val = _currItem[_field[i].name];
						if(_type === 'selected'){
							var options = _field[i].value,
								_sel_buf = '', _options = '';
							for(var j = 0; j < options.length; ++j){
								if(_val !== (options[j].value)){
									_options += '<option value="' + options[j].value + '">' + options[j].name + '</option>';
								}else{
									_sel_buf = '<select class="form-control" name="' + _field[i].name + '"><option value="' + options[j].value + '">' + options[j].name + '</option>';
								}
							}
							formHTML += _sel_buf + _options + '</select>';	
						}else if(_type === 'switch'){
							var switched = _field[i].switched || {};
							formHTML += '<input name="' + _field[i].name + '" id="' + _field[i].name + 'Switch" type="checkBox" switchVal="' + _val + '"/>';
							spin.form.switchFields.push({ID:_field[i].name + 'Switch', option : {state:_val == switched.on.value ? true : false, labelWidth:100, handleWidth:100, onText:switched.on.name || 'ON', offText:switched.off.name || 'OFF', onColor:switched.on.cls || 'primary', offColor:switched.off.cls || 'info', onSwitchChange:function(event, state){ $(this).attr('switchVal', state ? switched.on.value : switched.off.value); }}});
						}else if(_type === 'ajaxSelected'){
							var ajaxUri = _field[i].ajaxUri;
							if( ajaxUri ){
								$.ajax({
									type : "post",
									url : ajaxUri,
									async : false,
									success : function( data ){
										if(typeof data === 'string'){
											data = eval("(" + data + ")");
										}
										formHTML += '<select class="form-control" name="' + _field[i].name + '" id="' + _field[i].name + '">';
										var options = data, _fieldName = _field[i].ajaxField;
										for(var j = 0; j < options.length; ++j){
											formHTML += '<option ' + (_val === options[j][ _field[i].name ] ? 'selected="selected"' : '') + ' value="' + options[j].ID + '">' + options[j][ _fieldName ] + '</option>';
										}
										formHTML += '</select>';
									},
									dataType:'json',
									timeout : 5000,
									error : function(req, error){
										console.log('Get ajaxSelected data err!');
									}
								});
							}
						}else if( _type === 'group' ){
							formHTML += spin.module.group.editGroup( _field[i].text, _val );
						}else if( _type === 'rangetime' ){
							formHTML += spin.module.rangetime.editRangetime( _field[i].name, _val );
						}else{
							formHTML += '<input type="text" class="form-control" ' 
									     + ' name="' + _field[i].name + '" placeholder="' + _field[i].text + '" value="' 
									     + (_type === 'operator' ? _field[i].value : _val) + '" '
									     + ( _type === 'creator' || _type === 'operator' || _type === 'INDEX' ? 'readonly="readonly"' : '')
									     + (_field[i].popover ? 'rel="popover" data-content="' + _field[i].content + '" data-original-title="' + _field[i].title + '"' : '') 
									     + 'onblur="spin.form.checkInput(\'' + _field[i].name + '\', \'' + (_field[i].popover ? _field[i].regex : '') + '\');">';
						}
						formHTML += '</div></div>';
					}
				}
			}else if( _func === 'part' ){
				var _fields = _uri.substring(_uri.indexOf('#') + 1).split('&');
				_uri = _uri.substring(0, _uri.indexOf('#'));
				for(var i = 0; i < field.length; ++i){
					for(var j = 0; j < _fields.length; ++j){
						if(field[i].name === _fields[j]){
							formHTML += '<div class="form-item">' +
											'<span class="item-label"><label for="' + field[i].name + '" class="control-label">' + field[i].text + '</label></span>' +
											'<span class="item-input"><input type="text" class="form-control" name="' + field[i].name + '" ' + (currItem[_fields[j]] !== '' ? 'value="' + currItem[_fields[j]] + '"' : '') + ' placeholder="' + field[i].text + '"' +
													(field[i].popover ? 'rel="popover" data-content="' + field[i].content + '" data-original-title="' + field[i].title + '"' : '') + 
													'onblur="spin.form.checkInput(\'' + field[i].name + '\', \'' + (field[i].popover ? field[i].regex : '') + '\');"></span>'
										'</div>';	
						}								
					}
				}								
			}
			formHTML = '<div class="spin-form">' + formHTML + '</div><div id="form-warn"></div>';
			return formHTML;
		}
	}
});

/** 模态提示框 */
Class('spin.modal', null, {
	Static : {
		/** 标准 modal */
		show : function( _delay, _title, _btnFlag, _content, _bind ){
			this.hide();
			
			var modalHTML = '<div class="modal-dialog">' +
								'<div id="modal-content" class="modal-content">' +
									'<div class="modal-header">' +
										'<button type="button" class="close" data-dismiss="modal">' +
											'<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>' +
										'</button>' +
										'<h4 class="modal-title">' + ( _title || '提示' ) + '</h4>' +
									'</div>' +
									'<div class="modal-body">' + ( _content || '提示为空' ) + '</div>' +
									'<div class="modal-footer">' +
										( _btnFlag ?
										'<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> 取消</button>' +
										'<button type="button" class="btn btn-info" id="modal-deter"><span class="glyphicon glyphicon-ok"></span> 确定</button>'
										: '' ) +
									'</div>' +
								'</div>' +
							'</div>';
			
			if( $('#spin-modal').size() === 0 ){
				$('body').append('<div id="spin-modal" class="modal fade">' + modalHTML + '</div>');
			}else{
				$('#spin-modal').html( modalHTML );
			}
			
			setTimeout(function(){
				$('#spin-modal').modal({backdrop: 'static', keyboard: false, show : true});
				
				if( _bind && _btnFlag ){
					$('#modal-deter').unbind('click');
					$('#modal-deter').click( _bind );
				}
			}, _delay || 0 );
		},
		/** 双modal */
		addOtherModal : function( _title, _btnFlag, _content, _bind ){
			if( $('#modal-content-other').size() === 0 ){
				var modalHTML = '<div id="modal-content-other" class="modal-content">' +
									'<div class="modal-header">' +
										'<button type="button" class="close" onclick="spin.modal.hideOtherModal();">' +
											'<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>' +
										'</button>' +
										'<h4 class="modal-title">' + ( _title || '提示' ) + '</h4>' +
										'</div>' +
										'<div class="modal-body">' + ( _content || '提示为空' ) + '</div>' +
									'<div class="modal-footer">' +
											( _btnFlag ?
											'<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> 取消</button>' +
											'<button type="button" class="btn btn-info" id="modal-deter"><span class="glyphicon glyphicon-ok"></span> 确定</button>'
											: '' ) +
									'</div>' +
								'</div>';
				
				$('.modal-dialog').append( modalHTML );
			}else{
				$('#modal-content-other .modal-body').html( _content );
				$('#modal-content').removeClass('modal-move-center');
				$('#modal-content-other').removeClass('modal-move-top');
			}
			$('#modal-content').addClass('modal-move-left'); //左移
			setTimeout(function(){
				$('#modal-content-other').addClass('modal-move-right'); //渐入
			}, 2);
			
			if( _bind && _btnFlag ){
				$('#modal-deter-other').unbind('click');
				$('#modal-deter-other').click( _bind );
			}
		},
		hideOtherModal : function(){
			$('#modal-content-other .modal-body').html('');
			$('#modal-content-other').removeClass('modal-move-right').addClass('modal-move-top');
			$('#modal-content').removeClass('modal-move-left').addClass('modal-move-center');
		},
		hide : function(){
			//解除事件绑定
			$('#modal-deter').unbind('click');
			
			if( $('#spin-modal').size() > 0 ){
				$('#spin-modal').modal('hide');
			}else{
				$('.modal').modal('hide');
			}
		},
		bind : function( _function ){
			$('#modal-deter').unbind('click');
			$('#modal-deter').click( _function );
		}
	}
});

/** 组件类 */
Class('spin.module', null, {
	Static : {
		/** form-group */
		group : {
			idx : 2,
			getGroup : function( _name, _title ){
				this.name = _name;
				this.title = _title;
				
				return '<div id="form-group">' + 
							'<div id="item-option-1" class="item-option">' +
								'<span class="option-num">1</span>' +
								'<input class="form-control option-input" type="text" placeholder="请输入' + _title + '"> ' +
								'<a class="btn btn-default" id="item-option-btn-1" onclick="spin.module.uploadimg.getUploadimg( 1 );"><span class="glyphicon glyphicon-picture"></span></a> ' +
							'</div>' + 
							'<div id="item-option-2" class="item-option">' +
								'<span class="option-num">2</span>' +
								'<input class="form-control option-input" type="text" placeholder="请输入' + _title + '"> ' +
								'<a class="btn btn-default" id="item-option-btn-2" onclick="spin.module.uploadimg.getUploadimg( 2 );"><span class="glyphicon glyphicon-picture"></span></a> ' +
							'</div>' + 
						'</div>' + 
						'<button class="btn btn-info" onclick="spin.module.group.addOption(\'' + _title + '\');"><span class="glyphicon glyphicon-plus"></span> 添加' + _title + '</button>';
			},
			editGroup : function( _title, _value ){
				_value = _value ? (typeof _value === 'string' ? eval('(' + _value + ')') : _value) : [{text:''}, {text:''}]; 

				var optionHTML = '<div id="form-group">';
				for(var i = 0; i < _value.length; ++ i){
					optionHTML += '<div id="item-option-' + (i + 1) + '" class="item-option">' +
									  '<span class="option-num">' + (i + 1) + '</span>' +
									  '<input class="form-control option-input" type="text" placeholder="请输入' + _title + '" value="' + _value[i].text + '"> ' +
									  '<a class="btn btn-default" id="item-option-btn-' + (i + 1) + '" onclick="spin.module.uploadimg.getUploadimg( ' + (i + 1) + ' );">' + 
									  		( _value[i].img ? '<img src="' + _value[i].img + '" width="15" height="15">' : '<span class="glyphicon glyphicon-picture"></span>' ) + 
									  '</a> ' +
								  '</div>';
				}
				optionHTML += '</div><button class="btn btn-info" onclick="spin.module.group.addOption(\'' + _title + '\');"><span class="glyphicon glyphicon-plus"></span> 添加' + _title + '</button>';
				return optionHTML;
			},
			addOption : function( _title ){
				var len = $('#form-group div').size() || 0;
				if( len > 0 ){
					this.idx += 1;
					var	option = '<div id="item-option-' + this.idx + '" class="item-option">' +
									'<span class="option-num">' + this.idx + '</span>' +
									'<input class="form-control option-input" type="text" placeholder="请输入' + _title + '"> ' +
									'<a class="btn btn-default" id="item-option-btn-' + this.idx + '" onclick="spin.module.uploadimg.getUploadimg( ' + this.idx + ' );">' + 
							  			'<span class="glyphicon glyphicon-picture"></span>' + 
							  		'</a> ' +
							  		'<a class="btn btn-default" onclick="spin.module.group.deleteOption(' + this.idx + ');"><span class="glyphicon glyphicon-trash"></span></a>' +
								 '</div>';
					$('#form-group').append( option );
				}
			},
			deleteOption : function( _idx ){
				if( $('#item-option-' + _idx).size() > 0 ){
					$('#item-option-' + _idx).remove();
				}
			},
			getValue : function(){
				var data = [],
					groups = $('#form-group div');
				for( var i = 0; i < groups.length; ++ i ){
					var text = $('#' + groups[i].id + ' input').val(),
						img = $('#' + groups[i].id + ' img').attr('src');
					if( text || img ){
						data.push({id:i, text:text, img:img});
					}
				}
				if( data.length >= 2 ){
					return JSON.stringify( data );
				}else{
					return false;
				}
			}
		},
		rangetime : {
			getRangetime : function( _name ){
				return '<div id="form-rangetime">' + 
							'<div class="input-group date form-datetime">' +
				                '<input class="form-control" size="16" type="text" readonly id="' + _name + 'StartTime">' +
				                '<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>' +
				                '<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>' +
				            '</div>' +
				    		'<div class="input-group date form-datetime">' +
				                '<input class="form-control" size="16" type="text" readonly id="' + _name + 'EndTime">' +
				                '<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>' +
				                '<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>' +
				            '</div>' + 
				        '</div>';
			},
			editRangetime : function( _name, _value ){
				_value = _value ? (typeof _value === 'string' ? eval('(' + _value + ')') : _value) : {start:'', end:''};
				return '<div id="form-rangetime">' + 
							'<div class="input-group date form-datetime">' +
				                '<input class="form-control" size="16" type="text" readonly id="' + _name + 'StartTime" value="' + (_value.start || '') + '">' +
				                '<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>' +
				                '<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>' +
				            '</div>' +
				    		'<div class="input-group date form-datetime">' +
				                '<input class="form-control" size="16" type="text" readonly id="' + _name + 'EndTime" value="' + (_value.end || '') + '">' +
				                '<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>' +
				                '<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>' +
				            '</div>' + 
				        '</div>'
			},
			getValue : function( _name ){
				var start = $('#' + _name + 'StartTime').val() || '',
					end = $('#' + _name + 'EndTime').val() || '';
				
				if( !start ){
					start = spin.time.getCurrTime();
				}
				if( end ){
					return start >= end ? false : '{"start":"' + spin.time.getCurrTime() + '", "end":"' + end + '"}';
				}else{
					return '{"start":"' + start + '", "end":""}';
				}
			}
		},
		uploadimg : {
			getUploadimg : function( _idx ){
				var uploadHTML = '<div id="spin-uploadimg">' +
									 '<div class="uploadimg-img"></div>' + 
									 '<div class="uploadimg-alert">图片文件大小不能超过1M，支持图片类型：png 或 jpg</div>' +
									 '<form id="imageForm" action="../upload/uploadFile" method="post" enctype="multipart/form-data">' +
							             '<div class="row fileupload-buttonbar">' +
								            '<div class="col-md-4">' +
								                '<span class="btn btn-success fileinput-button" >' +
								                    '<i class="glyphicon glyphicon-plus"></i> ' +
								                    '<span> 选择图片 </span>' +
								                    '<input type="file" name="img" onchange="spin.module.uploadimg.showImg( this.files, ' + _idx + ' );"/>' +
								                '</span>' +
								            '</div>' +
								            '<div class="col-md-4">' +
									            '<button type="submit" id="imgUploadBtn" class="btn btn-warning cancel" disabled="disabled" onclick="spin.module.uploadimg.uploadFile(' + _idx + ' );">' +
								                    '<i class="glyphicon glyphicon-upload"></i> ' +
								                    '<span> 上传图片 </span>' +
								                '</button>' +
							                '</div>' +
								        '</div>' +
								     '</form>' + 
								 '</div>';
				spin.modal.addOtherModal( '上传图片', false, uploadHTML );
			},
			closeUploadimg : function(){
				if($('#spin-uploadimg').size() > 0){
					$('#spin-uploadimg').remove();
				}
			},
			showImg : function( _file, _idx ){
				var file = _file[ '0' ], reader = new FileReader();
				this.currFile = file;
				if( file.size > 1024 * 1024 ){
					$('#imgUploadBtn').attr('disabled', 'disabled');
					$('.uploadimg-alert').html('图片超过1M，请重新选择适合的图片');
				}else{
					$('#imgUploadBtn').removeAttr('disabled');
					$('.uploadimg-alert').html('图片文件大小不能超过1M，支持图片类型：png 或 jpg');
					reader.onload = function(){  
						$(".uploadimg-img").css('background', 'url(' + this.result + ') center no-repeat');
					};
					reader.readAsDataURL( file ); 
				}
			},
			uploadFile : function( _idx ){
				var currFile = this.currFile || {};
				$('#imageForm').ajaxForm({  
			        dataType: 'json',  
			        success: function( data ){
			        	if( data.success ){
			        		spin.module.uploadimg.closeUploadimg();
			        		spin.modal.hideOtherModal();
			        		$("#item-option-btn-" + _idx).html('<img src="' + data.path.substring(data.path.indexOf('webapps/') + 7) + currFile.name + '" width="15" height="15"/>');
			        	}
			        }
			    });
			}
		}
	}
});

/** 时间计算 */
Class('spin.time', null, {
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

/** 深层拷贝 */
Class('spin.extend', null, {
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