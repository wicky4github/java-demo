function getByClass(oParent, sClass){
    var aEle = oParent.getElementsByTagName('*');
    var aRes = [];
    for (var i = 0; i < aEle.length; i++) {
        if(aEle[i].className == sClass){
            aRes.push(aEle[i]);
        }
    }
    return aRes;
}
function getStyle(obj, attr){
    if (obj.currentStyle) {
        return obj.currentStyle[attr];
    }else {
        return getComputedStyle(obj, false)[attr];
    }
}

var PIXEL = 30;  // 每个方块宽高
var ROWS = 21;   // 舞台方块行数
var COLS = 11;   // 舞台方块列数
var Game = {
    init: function(){  //初始化游戏
        this.iSocre = 0;  // 积分
        //获取舞台，设置宽高属性
        var oStage = getByClass(document, "stage")[0];
        oStage.style.width = PIXEL * COLS + 'px';
        oStage.style.height = PIXEL * ROWS + 'px';
        this.oStage = oStage;

        this.loadImg();

        this.createLine(); // 创建参考线
        this.createShape(); // 随机生成形状

        this.aStatus = [];
        for (var i = 0; i < ROWS; i++) {
            this.aStatus[i] = [];
            for (var j = 0; j < COLS; j++) {
                this.aStatus[i][j] = 0;
            }
        }
        this.addKeyDownEvent(); // 添加键盘事件
    },
    createLine: function(){  // 创建参考线
        var oLine = document.createElement("div");
        oLine.style.width = parseInt(getStyle(this.oStage, 'width')) + 'px';
        oLine.style.height = parseInt(getStyle(this.oStage, 'height')) + 'px';
        oLine.id = "line";
        oLine.style.position = "absolute";

        for (var i = 0; i < ROWS; i++) {
            for (var j = 0; j < COLS; j++) {
                var oDiv = document.createElement("div");
                oDiv.style.width = PIXEL - 2 + 'px';
                oDiv.style.height = PIXEL - 2 + 'px';
                oDiv.style.border = '1px dashed #eee';
                oDiv.style.float = 'left';

                oLine.appendChild(oDiv);
            }
        }

        oLine.show = 0;
        oLine.style.display = 'none';//默认隐藏参考线
        this.oStage.appendChild(oLine);

        this.addButtonEvent(); //添加 按钮点击事件
    },
    addButtonEvent: function(){ // 按钮事件。切换参考线显示隐藏
        document.getElementById('showLine').onclick = function(){
            var line = document.getElementById("line");
            if (line.show % 2) {
                line.style.display = 'none';
                this.innerHTML = '显示网格';
            }else{
                line.style.display = 'block';
                this.innerHTML = '隐藏网格';
            }
            line.show++;
        };
    },
    createShape: function(){
        if(this.nextShape){ // 如果当前有下有一个形状
            this.oStage.appendChild(this.nextShape);  // 将下一个形状插入舞台
            this.currentShape = this.nextShape;
            this.autoDown();  // 添加形状下降事件
            if (this.currentShape == this.nextShape) {  // 如果当前形状和下一个形状是同个对象
                this.nextShape = this.drawShape();  // 重新画一个形状
                getByClass(document, 'next')[0].appendChild(this.nextShape); // 插入到预览
            }
        }else{ // 初始化游戏没有下一个形状
            this.nextShape = this.drawShape(); // 画出
            getByClass(document, 'next')[0].appendChild(this.nextShape); // 插进去
            this.createShape(); // 调用自身再判断
        }
    },
    drawShape: function(){  // 画一个形状，返回这个形状
        var index = Math.floor(Math.random() * this.shape.length);
        // var index = 4;
        var attr = this.shape[index];  // 短变量名称存储形状属性
        //设置当前形状容器样式
        var shape = document.createElement("div");
        shape.className = attr.type;
        shape.style.position = "absolute";
        // 保证初始化时居中
        var cols = (attr.cols % 2 == 1) ? attr.cols : (attr.cols + 1);
        shape.style.left = (COLS * PIXEL - cols * PIXEL) / 2 + 'px';
        shape.style.top = -PIXEL * attr.rows + 'px';  // 容器放在舞台最上面
        // 存储行列信息 for 键盘事件
        shape.rows = attr.rows;
        shape.cols = attr.cols;
        
        //根据坐标创建方块
        for (var i = 0; i < attr.rows; i++) {
            for (var j = i * attr.cols; j < i * attr.cols + attr.cols; j++) {
                
                if (attr.point[j]) { // 坐标非0创建
                    var oDiv = document.createElement("div");
                    //设置方块属性
                    oDiv.className = 'block';
                    oDiv.style.background = attr.bg;
                    oDiv.style.position = 'absolute';
                    oDiv.style.left = (j % attr.cols) * PIXEL + 'px';
                    oDiv.style.top = (i % attr.rows) * PIXEL + 'px';
                    oDiv.style.width = PIXEL + 'px';
                    oDiv.style.height = PIXEL + 'px';
                    oDiv.center = attr.point[j];   //2代表中心点
                    // if (oDiv.center == 2) oDiv.style.background = "green";

                    shape.appendChild(oDiv); //把方块插入容器
                }
            }
        }
        
        return shape;
    },
    autoDown: function(){ // 定时器：让当前形状容器自动下降
        var _this = this;

        this.currentShape.timer = setInterval(function(){
            // 获得当前形状最下面的形状，判断他的底部碰到
            var bottom = _this.getBottom();
            if(bottom.offsetTop + bottom.offsetHeight + _this.currentShape.offsetTop >= PIXEL * ROWS){  // 最下面的方块到边缘了
                clearInterval(_this.currentShape.timer);
                _this.updateStatus();
                _this.createShape(); // 游戏未结束，继续创建形状
            }
            // 下降时，循环所有当前形状，判断下一个位置在当前状态数组是否为1，为1则更新状态信息
            if(_this.isNextDownWillBump()){
                _this.updateStatus();
                if(_this.checkGameOver()){ // 每次开始下降都判断游戏是否结束
                    _this.stopGame();
                }else{
                    clearInterval(_this.currentShape.timer);
                    _this.createShape(); // 游戏未结束，继续创建形状
                }
            }else{
                _this.currentShape.style.top = _this.currentShape.offsetTop + PIXEL + 'px';
            }
        }, 500);

    },
    isNextDownWillBump: function(){  // 检查向下移动1个单位是否会和其他的碰撞
        var aEle = this.currentShape.getElementsByTagName("div");
        for (var i = 0; i < aEle.length; i++) {
            var x = (aEle[i].offsetLeft + aEle[i].parentNode.offsetLeft) / PIXEL;
            var y = (aEle[i].offsetTop + aEle[i].parentNode.offsetTop + PIXEL) / PIXEL;

            if(typeof(this.aStatus[y]) != "undefined" && this.aStatus[y][x] == 1){
                return true;
            }
        }
        return false;
    },
    checkGameOver: function(){  // 检查游戏是否结束
        var aEle = this.currentShape.getElementsByTagName("div");
        for (var i = 0; i < aEle.length; i++) {
            if (aEle[i].offsetTop + aEle[i].parentNode.offsetTop + PIXEL <= 0) {  // 当前形状，存在一个方块在舞台上方，而且无法下降，则游戏结束
                return true;
            }
        }
        return false;
    },
    stopGame: function(){ // 结束游戏
        console.log("Game Over!");
        clearInterval(this.currentShape.timer);
        document.onkeydown = null;  //清除键盘事件
    },
    updateStatus: function(){ //更新状态数组信息
        this.flushStatus();
        while ((row = this.checkClear())) {  // 循环检查
            this.clearRow(row);  // 清除行
            this.addScore();
        }
    },
    checkClear: function(){  // 检查是否可清除，返回可清除的行
        var block = 0;
        for (var i = ROWS - 1; i >= 0; i--) {
            for (var j = 0; j < COLS ; j++) {
                if (this.aStatus[i][j] == 1) {
                    block++;
                }
            }
            if (block == COLS) {
                return i;
            }
            block = 0;
        }
        return false;
    },
    flushStatus: function(){  // 通过舞台上所有方块更新状态数组
        for (i = 0; i < ROWS; i++) {
            this.aStatus[i] = [];
            for (var j = 0; j < COLS; j++) {
                this.aStatus[i][j] = 0;
            }
        }
        var aEle = getByClass(this.oStage, 'block');
        for (i = 0; i < aEle.length; i++) {
            var r = (aEle[i].offsetTop + aEle[i].parentNode.offsetTop) / PIXEL;
            var c = (aEle[i].offsetLeft + aEle[i].parentNode.offsetLeft) / PIXEL;
            if (typeof(this.aStatus[r]) != "undefined") {
                this.aStatus[r][c] = 1;
            }
        }
    },
    clearRow: function(row){ // 清除行
        //遍历所有方块
        var aEle = getByClass(this.oStage, 'block');
        for (var i = 0; i < aEle.length; i++) {
            if ((aEle[i].offsetTop + aEle[i].parentNode.offsetTop) / PIXEL == row) { // 方块在需要消除的行上
                aEle[i].parentNode.removeChild(aEle[i]);
            }
        }
        var aParent = [];  // 需要向下移动1个单位的容器
        aEle = getByClass(this.oStage, 'block');
        for (i = 0; i < aEle.length; i++) {
            if ((aEle[i].offsetTop + aEle[i].parentNode.offsetTop) / PIXEL < row) {
                aEle[i].style.top = aEle[i].offsetTop + PIXEL + 'px';
            }
        }
        this.flushStatus(); // 刷新数组状态信息
    },
    addScore: function(){
        getByClass(document, "score")[0].getElementsByTagName("span")[0].innerText = ++this.iSocre;
    },
    getBottom: function(){  // 获得最下面的方块
        var aEle = this.currentShape.getElementsByTagName("div");
        var bottom = aEle[0];
        for (var i = 0; i < aEle.length; i++) {
            if(aEle[i].offsetTop > bottom.offsetTop){
                bottom = aEle[i];
            }
        }
        return bottom;
    },
    addKeyDownEvent: function(){  // 添加键盘按下事件
        var _this = this;
        this.flag = false;
        var str = "";

        document.onkeydown = function(e){
            var ev = e || event;
            _this.flag = !_this.flag;
            
            switch(ev.keyCode){
                case 32:
                    break;
                case 37://左
                    _this.move({left: -PIXEL});
                    break;

                case 38://上   改变形状
                    _this.rotate();
                    break;

                case 39://右
                    _this.move({left: PIXEL});
                    break;

                case 40://下
                    if(_this.isNextDownWillBump()){  // 每次向下移动都判断下面是否有方块
                        break;
                    }
                    _this.move({top: PIXEL});
            }
        };
    },
    move: function(json){ // 移动当前形状容器
        for(var attr in json){
            var aEle = this.currentShape.getElementsByTagName("div");
            for (var i = 0; i < aEle.length; i++) {
                if(attr == "left" && (aEle[i].offsetLeft + this.currentShape.offsetLeft + json[attr] < 0 || aEle[i].offsetLeft + PIXEL + this.currentShape.offsetLeft + json[attr] > PIXEL * COLS)){
                    return;
                }
                if(attr == "top" && (aEle[i].offsetTop + PIXEL + this.currentShape.offsetTop >= PIXEL * ROWS)){
                    return;
                }
            }
            var offset = "offset" + attr.replace(/(\w)/,function(v){return v.toUpperCase();});
            var target = this.currentShape[offset] + json[attr];

            if(attr == "top"){
                if(target <= PIXEL * ROWS - this.currentShape.rows * PIXEL)
                {
                    clearInterval(this.currentShape.timer);  // 向下移动形状先清除自动下降的定时器
                }else {
                    break;
                }
            }
            this.currentShape.style[attr] = target + 'px';
            if (this.isBump()) {
                console.log("b");
                this.currentShape.style[attr] = this.currentShape[offset] - json[attr] + 'px';
            }
            if(attr == "top"){
                this.autoDown();  // 按完就重新让定时器工作
            }
        }
    },
    isBump: function(){ //碰撞检测
        var cur = this.currentShape.getElementsByTagName("div");
        var all = getByClass(this.oStage, "block");

        var aBlock = [];
        var i = 0, j = 0;
        for (i = 0; i < all.length; i++) {
            if (all[i].parentNode != this.currentShape) {
                aBlock.push(all[i]);
            }
        }

        for (i = 0; i < aBlock.length; i++) {
            for (j = 0; j < cur.length; j++) {
                var l1 = all[i].offsetLeft + all[i].parentNode.offsetLeft;
                var r1 = all[i].offsetLeft + all[i].parentNode.offsetLeft + all[i].offsetWidth;
                var t1 = all[i].offsetTop + all[i].parentNode.offsetTop;
                var b1 = all[i].offsetTop + all[i].parentNode.offsetTop + all[i].offsetHeight;

                var l2 = cur[j].offsetLeft + cur[j].parentNode.offsetLeft;
                var r2 = cur[j].offsetLeft + cur[j].parentNode.offsetLeft + cur[j].offsetWidth;
                var t2 = cur[j].offsetTop + cur[j].parentNode.offsetTop;
                var b2 = cur[j].offsetTop + cur[j].parentNode.offsetTop + cur[j].offsetHeight;
                if (!(l1 >= r2 || r1 <= l2 || t1 >= b2 || b1 <= t2)) {
                    return true;
                }
            }
        }
        
    },
    rotate: function(){  // 旋转形状
        var aDiv = this.currentShape.getElementsByTagName("div");
        var oCenter;  // 中心形状
        for (var i = 0; i < aDiv.length; i++) {
            if (aDiv[i].center == 2){  // 坐标为2 就是中心形状
                oCenter = aDiv[i];
                break;
            }
        }
        var oriPos = [];  // 初始位置
        var aPos = [];   // 旋转后位置
        for (i = 0; i < aDiv.length; i++) {
            if (aDiv[i] != oCenter) {

                var x1 = aDiv[i].offsetLeft;
                var y1 = aDiv[i].offsetTop;
                oriPos.push({left: x1, top: y1});
                var x2 = oCenter.offsetLeft;
                var y2 = oCenter.offsetTop;
                var r = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                // 诱导公式：sin（π/2+α）= cosα，cos（π/2+α）= -sinα
                var cos = (x1 - x2) / r;  // 已知cosA 则 cos(A+90) = -sinA
                var sin = (y1 - y2) / r;   // 已知sinA 则 sin(A+90) = cosA

                var l = x2 + r * -sin;
                var t = y2 + r * cos;

                // 旋转坐标超出舞台
                if(l + this.currentShape.offsetLeft < 0 || l + this.currentShape.offsetLeft + PIXEL > PIXEL * COLS || t + this.currentShape.offsetTop < 0 || t + this.currentShape + PIXEL > PIXEL * ROWS){
                    return;
                }else{
                    aPos.push({left: l, top: t});
                }
            }else{
                // 中心形状坐标无需改变，插一个空json占位
                aPos.push({});
                oriPos.push({});
            }
        }
        for (i = 0; i < aDiv.length; i++) {  // 设置属性，完成形变
            if (aDiv[i] != oCenter) {
                aDiv[i].style.left = aPos[i].left + 'px';
                aDiv[i].style.top = aPos[i].top + 'px';
            }
        }
        if (this.isBump()) {  // 形变后，和已存在形状重叠了，换回原位置
            for (i = 0; i < aDiv.length; i++) {
                if (aDiv[i] != oCenter) {
                    aDiv[i].style.left = oriPos[i].left + 'px';
                    aDiv[i].style.top = oriPos[i].top + 'px';
                }
            }
            return;  // 不更新行列信息
        }
        // 旋转形状后，该形状行列就相反了
        var tmp = this.currentShape.rows;
        this.currentShape.rows = this.currentShape.cols;
        this.currentShape.cols = tmp;
    },

    shape: [ // 形状信息：类型，坐标，行，列，背景样式
        {
            type: 'S',
            point: [
                0,1,1,
                1,2,0
            ],
            rows: 2,
            cols: 3,
            bg: "url(S.png) 100% 100% no-repeat",
        },
        {
            type: 'Z',
            point: [
                1,1,0,
                0,2,1
            ],
            rows: 2,
            cols: 3,
            bg: "url(Z.png) 100% 100% no-repeat",
        },
        {
            type: 'L',
            point: [
                1,0,
                1,0,
                2,1,
            ],
            rows: 3,
            cols: 2,
            bg: "url(L.png) 100% 100% no-repeat",
        },
        {
            type: 'J',
            point: [
                0,1,
                0,1,
                1,2,
            ],
            rows: 3,
            cols: 2,
            bg: "url(J.png) 100% 100% no-repeat",
        },
        {
            type: 'I',
            point: [
                1,
                1,
                2,
                1
            ],
            rows: 4,
            cols: 1,
            bg: "url(I.png) 100% 100% no-repeat",
        },
        {
            type: 'O',
            point: [
                2,1,
                1,1
            ],
            rows: 2,
            cols: 2,
            bg: "url(O.png) 100% 100% no-repeat",
        },
        {
            type: 'T',
            point: [
                0,1,0,
                1,2,1
            ],
            rows: 2,
            cols: 3,
            bg: "url(T.png) 100% 100% no-repeat",
        },
    ],
    loadImg: function(){
        var _this = this;
        for (var i = 0; i < this.shape.length; i++) {
            (function(){
                var img = new Image();
                img.src = _this.shape[i].bg.match(/\w\.png/);
            })();
        }
    },
};

Game.init();  // 初始化游戏