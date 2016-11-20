/**
 * Created by 30695 on 2016/11/20 0020.
 */

/*
 *
 *  new showCkTree({
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
 *
 *
 * **/
var showCkTree = function (option) {
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
};
