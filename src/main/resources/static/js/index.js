// Table Init
var TableInit = function(paramId, paramUrl, paramColumns) {
    var oTableInit = new Object();
    oTableInit.Init = function() {
        $(paramId).bootstrapTable({
            url: paramUrl,              //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            contentType : "application/x-www-form-urlencoded",
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pagination : true,
            showPaginationSwitch : false, //显示切换分页
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 30, 50],             //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            // height: 300,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            // showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            // cardView: false,                    //是否显示详细视图
            // detailView: false,                   //是否显示父子表
            columns : paramColumns
        });
    };

    oTableInit.queryParams = function(params) {
        var param = {
            limit : this.limit,
            offset : this.offset,
            pageNumber: this.pageNumber,
            pageSize : this.pageSize
        };
        return param;
    };
    return oTableInit;
};


var AhnUploader = function(params) {
    const oAhnUploader = new WebUploader.Uploader(params);

    oAhnUploader.on('fileQueued', function(file) {
        $(params.fileContainer).children('.showImage').remove();
        var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
            '<img>' +
            // '<div class="img_file_name">' + file.name + '</div>' +
            '</div>'
        );
        var fileImg = $li.find('img');
        var ratio = window.devicePixelRatio || 1;

        $(params.fileListId).append($li);
        oAhnUploader.makeThumb(file, function(error, src) {
            if (error) {
                fileImg.replaceWith('<span>不能预览</span>');
                return;
            }
            fileImg.attr('src', src);
        }, 100, 100);
    });

    oAhnUploader.on( 'uploadProgress', function( file, percentage ) {

        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<p class="progress"><span class="progress-bar" role="progressbar"></span></p>').appendTo( $li ).find('span');
        }

        percentage = percentage * 100;
        $percent.text(percentage + '%');
        $percent.css( 'width', percentage * 100 + '%' );

    });

    return oAhnUploader;

};

function getProductFromValue() {
    return {
        id : $("#productId").val()
        ,categoryId : $("#categoryId").val()
        ,name : $("#name").val()
        , subtitle : $("#subtitle").val()
        , mainImage : $("#mainImage").val()
        , subImage : $("#subImage").val()
        , subImage2 : $("#subImage2").val()
        , subImage3 : $("#subImage3").val()
        , attribute : ''
        , detail : $("#detail").val()
        , price : $("#price").val()
        , vipPrice: $("#vipPrice").val()
        , spec : ''
        , stock : $("#stock").val()
        , status : $("#status").val()
        , country : $("#country").val()
        , city : $("#city").val()
        , variety : $("#variety").val()
        , treatment : $("#treatment").val()
        , flavor : $("#flavor").val()
        , param1 : $("#param1").val()
        , param2 : $("#param2").val()
        , param3 : $("#param3").val()
        , param4 : $("#param4").val()
        , param5 : $("#param5").val()
        , createtime : ''
        , updatetime : ''
    }
}

function getUserFormValue() {
    return {
        id : $("#userId").val()
        , openid : $("#openid").val()
        , unionid : $("#unionid").val()
        , username : $("#username").val()
        , password : $("#password").val()
        , passwordCheck : $("#passwordCheck").val()
        , roleNo : $("#roleNo").val()
        , role : $("#role").val()
        , status : $("#status").val()
        , realname : $("#realname").val()
        , company : $("#company").val()
        , companyType : $("#companyType").val()
        , deviceSerial : $("#deviceSerial").val()
        , deviceModel : $("#deviceModel").val()
        , deviceColor : $("#deviceColor").val()
        , phone : $("#phone").val()
        , email : $("#email").val()
        , sex : $("#sex").val()
        , birthday : $("#birthday").val()
        , wechat : $("#wechat").val()
        , qq : $("#qq").val()
        , province : $("#province").val()
        , city : $("#city").val()
        , area : $("#area").val()
        , address : $("#address").val()
        , question : $("#question").val()
        , answer : $("#answer").val()
        , imagePhoto : $("#imagePhoto").val()
        , param1 : $("#param1").val()
        , param2 : $("#param2").val()
        , param3 : $("#param3").val()
        , param4 : $("#param4").val()
        , param5 : $("#param5").val()
        , createtime : $("#createtime").val()
        , updatetime : $("#updatetime").val()
    }
}

class Product {
    save(param) {
        // console.log(param);

        $.ajax({
            url : '/api/product/create'
            , type : "POST"
            , contentType: "application/json"
            , data : JSON.stringify(param)
            , success : function (response) {
                // console.log(response);
                if (response.status === 0) {
                    swal({ title : response.msg, text : param.name.toString(), icon: "success", closeOnClickOutside: false}).then(function () {
                        window.location.href = '/manager/product/list';
                    });
                } else {
                    swal(response.msg,'Error', 'error');
                }
            }
            ,error : function (err) {
                // console.log(err);
            }
        });
    };
    edit(param) {
        // console.log(param);

    }
}

$(function () {
    UserInfo = {
        login : function () {
            var self = this;

            if (! this.validateNull()) {
                return;
            }

            var param = {
                username : this.getUserObjectValue().username
                , password : this.getUserObjectValue().password
            }

            $.ajax({
                url : '/api/user/login'
                , data : param
                , contentType : 'application/x-www-form-urlencoded'
                , method: 'POST'
                , success : function (response) {
                    if (response.status === 0) {
                        window.location.href = '/manager/home';
                    } else {
                        swal('登录失败','', 'error');
                    }
                }
                , error : function (e,a,r) {
                    swal(e+a+r,'','error');
                }
            })
        }
        , create : function () {
            var self = this;
            if (!self.validateNull() && !self.validatePasswordSameCheck() ) {
                return;
            }

            $.ajax({
                url : '/api/user/create'
                , data : JSON.stringify(this.getUserObjectValue())
                , contentType : 'application/json'
                , method: 'POST'
                , success : function (response) {
                    if (response.status === 0) {
                        swal({ title : "创建成功", text : self.getUserObjectValue().username.toString(), icon: "success", closeOnClickOutside: false}).then(function () {
                            window.location.href = '/manager/customer/list';
                        });
                    } else {
                        swal('创建用户失败，请再试试','', 'error');
                    }
                }
                , error : function (e,a,r) {
                    swal(e+a+r,'','error');
                }
            });

        }
        , edit : function () {
            var self = this;
            var msg = '';
            var USER_FORM = self.getUserObject();

            if ($.trim(USER_FORM.email.val()) === "") {
                msg += '邮箱/';
            }

            if(msg.toString() != "") {
                var msgStr = msg.toString().substr(0, msg.toString().length - 1);
                swal(msgStr + '不能为空', '','error');
                return;
            }

            $.ajax({
                url : '/api/user/edit'
                , data : JSON.stringify(this.getUserObjectValue())
                , contentType : 'application/json'
                , method : 'POST'
                , success : function (response) {
                    if (response.status === 0) {
                        swal({ title : response.msg, text : self.getUserObjectValue().username.toString(), icon: "success", closeOnClickOutside: false}).then(function () {
                            window.location.href = '/manager/customer/list';
                        });
                    } else {
                        swal(response.msg,'Error', 'error');
                    }
                }
                , error : function (e,a,r) {
                    swal(e, a+r, 'error');
                }
            });
        }
        ,validateNull : function () {
            var msg = '';
            var USER_FORM = this.getUserObject();

            if ($.trim(USER_FORM.username.val()) === "") {
                msg += '用户名/';
            }
            if ($.trim(USER_FORM.password.val()) === "") {
                msg += '密码/';
            }

            if(msg.toString() != "") {
                var msgStr = msg.toString().substr(0, msg.toString().length - 1);
                swal(msgStr + '不能为空', '','error');
                return false;
            } else {
                return true;
            }

        }
        ,validatePasswordSameCheck : function () {
            var USER_FORM = this.getUserObject();
            if ($.trim(USER_FORM.password.val()) !== $.trim(USER_FORM.passwordCheck.val())) {
                swal('密码不一致', '', 'error');
                USER_FORM.password.val('');
                USER_FORM.passwordCheck.val('');
                return false
            } else {
                return true;
            }

        }

        , getUserObject : function () {
            return {
                id : $("#userId")
                    , openid : $("#openid")
                    , unionid : $("#unionid")
                    , username : $("#username")
                    , password : $("#password")
                    , roleNo : $("#roleNo")
                    , role : $("#role")
                    , status : $("#status")
                    , realname : $("#realname")
                    , company : $("#company")
                    , companyType : $("#companyType")
                    , deviceSerial : $("#deviceSerial")
                    , deviceModel : $("#deviceModel")
                    , deviceColor : $("#deviceColor")
                    , phone : $("#phone")
                    , email : $("#email")
                    , sex : $("#sex")
                    , birthday : $("#birthday")
                    , wechat : $("#wechat")
                    , qq : $("#qq")
                    , province : $("#province")
                    , city : $("#city")
                    , area : $("#area")
                    , address : $("#address")
                    , question : $("#question")
                    , answer : $("#answer")
                    , imagePhoto : $("#imagePhoto")
                    , param1 : $("#param1")
                    , param2 : $("#param2")
                    , param3 : $("#param3")
                    , param4 : $("#param4")
                    , param5 : $("#param5")
                    , createtime : $("#createtime")
                    , updatetime : $("#updatetime")
            }
        }
        , getUserObjectValue : function () {
            return {
                id : $("#userId").val()
                , openid : $("#openid").val()
                , unionid : $("#unionid").val()
                , username : $("#username").val()
                , password : $("#password").val()
                , roleNo : $("#roleNo").val()
                , role : $("#role").val()
                , status : $("#status").val()
                , realname : $("#realname").val()
                , company : $("#company").val()
                , companyType : $("#companyType").val()
                , deviceSerial : $("#deviceSerial").val()
                , deviceModel : $("#deviceModel").val()
                , deviceColor : $("#deviceColor").val()
                , phone : $("#phone").val()
                , email : $("#email").val()
                , sex : $("#sex").val()
                , birthday : $("#birthday").val()
                , wechat : $("#wechat").val()
                , qq : $("#qq").val()
                , province : $("#province").val()
                , city : $("#city").val()
                , area : $("#area").val()
                , address : $("#address").val()
                , question : $("#question").val()
                , answer : $("#answer").val()
                , imagePhoto : $("#imagePhoto").val()
                , param1 : $("#param1").val()
                , param2 : $("#param2").val()
                , param3 : $("#param3").val()
                , param4 : $("#param4").val()
                , param5 : $("#param5").val()
                , createtime : $("#createtime").val()
                , updatetime : $("#updatetime").val()
            }
        }
    }

});







