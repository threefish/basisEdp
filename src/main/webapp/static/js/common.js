/**
 * Created by 30695 on 2016/11/20 0020.
 */
var core = {
    showMsg: function (msg, status) {
        if (status) {
            layer.msg(msg, {icon: 7, time: 2000});
        } else {
            layer.msg(msg, {icon: 1});
        }
    },
    msg: function (msg) {
        layer.msg(msg, {icon: 1});
    },
    error: function (msg) {
        layer.msg(msg, {icon: 7, time: 2000});
    },
    tips: function (dom, msg) {
        layer.tips(msg, dom);
    },
    openUrl: function (url, title, width, height, closeBtn) {
        layer.open({
            type: 2,
            shadeClose: false,
            title: title,
            shade: 0.3,
            closeBtn: closeBtn,
            area: [width + 'px', height + 'px'], // 宽高
            content: base + url
        });
    },
    confirm: function (msg, fun) {
        layer.confirm(msg, {
            btn: ['确定', '取消']
        }, function (index) {
            if (fun) {
                fun();
            }
            layer.close(index);
        });
    },
    showIcon: function (domid) {
        if (domid) {
            layer.open({
                type: 2,
                title: '选择图标',
                shadeClose: false,
                shade: 0.3,
                area: ['800px', '60%'],
                content: base + '/setting/icon/index?domid=' + domid
            });
        } else {
            core.showMsg("参数不正确");
        }
    },
    closeFrameWindow: function () {
        parent.layer.close(parent.layer.getFrameIndex(window.name));
    },
    /*
     * 使用方法
     *  core.showMenusTree({
     *       title: "修改上级菜单",
     *       url: "/sysMenu/tree",
     *       isCheckbox: false,
     *       target: ['menuPid', 'menuPidDesc'],
     *       data: {id: "id", pid: "pid", name: "menuName"},
     *       onSelect: function (data) {
     *          console.log(data)
     *       },
     *       onOk: function (data) {
     *          console.log(data)
     *       }
     *   });
     * **/
    showMenusTree: function (option) {
        var treeManager;
        var opt = {
            title: option.title ? option.title : "选择器",
            url: option.url ? option.url : "",
            isCheckbox: option.isCheckbox ? option.isCheckbox : false,
            target: [option.target[0], option.target[1]],
            data: option.data,
            onOk: option.onOk,
            onSelect: option.onSelect,
        };
        var treeSetting = {
            async: {
                enable: true,
                type: "post",
                url: base + opt.url,
                autoParam: ["id", "name"]
            },
            check: {
                enable: opt.isCheckbox
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: opt.data.id,
                    pIdKey: opt.data.pid,
                    rootPId: 0
                },
                key: {
                    name: opt.data.name
                }
            }, view: {
                showIcon: true
            },
            callback: {
                beforeClick: zTreeBeforeClick,
                onAsyncSuccess: zTreeOnAsyncSuccess
            }
        };
        var idEl = $("#" + opt.target[0]);
        var iddescEl = $("#" + opt.target[1]);

        function zTreeBeforeClick(treeId, data, clickFlag) {
            if (opt.onSelect) {
                return opt.onSelect(data);
            }
        }

        function zTreeOnAsyncSuccess(treeId, data, clickFlag) {
            var rootNode = treeManager.getNodesByParam(opt.data.id, 0)[0];
            treeManager.expandNode(rootNode, true, false, true);
            if (idEl.val() != "") {
                if (!opt.isCheckbox) {
                    var node = treeManager.getNodesByParam(opt.data.id, idEl.val())[0];
                    treeManager.selectNode(node);
                } else {
                    var idsArr = (idEl.val() + "").split(",");
                    for (var i in idsArr) {
                        var node = treeManager.getNodesByParam(opt.data.id, idsArr[i])[0];
                        treeManager.checkNode(node, true, false);
                    }
                }
            }
        }

        layer.open({
            scrollbar: false,
            type: 1,
            title: opt.title,
            skin: 'layui-layer-rim',
            area: ['420px', '400px'],
            content: "<div id='yh_class_tree' class='ztree' style='width: 100%'></div>",
            btn: ['确定', '取消'],
            yes: function (index, layero) {
                var data;
                if (opt.isCheckbox) {
                    data = treeManager.getCheckedNodes();
                    var idArr = new Array();
                    var descArr = new Array();
                    for (var i in data) {
                        idArr.push(data[i][opt.data.id]);
                        descArr.push(data[i][opt.data.name]);
                    }
                    idEl.val(idArr.join(","));
                    iddescEl.val(descArr.join(","));
                } else {
                    data = treeManager.getSelectedNodes()[0];
                    if (data) {
                        idEl.val(data[opt.data.id]);
                        iddescEl.val(data[opt.data.name]);
                    }
                }
                layer.close(index);
                if (opt.onOk) {
                    opt.onOk(data);
                }
            }, cancel: function (index) {
            }, success: function (layero, index) {
                treeManager = $.fn.zTree.init($("#yh_class_tree"), treeSetting);
            }
        });
    }
};


