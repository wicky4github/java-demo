package TankGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.util.regex.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * 坦克游戏类（窗口）
 * 功能：
 *     画出坦克
 *     可操作移动，敌人随机移动
 *     可发射子弹，可控制发射子弹的数量，敌人自动发射子弹，可控制敌人发射子弹数量
 *     击中敌人，敌人消失，爆炸效果；被击中，自己消失，爆炸效果
 *     防止敌人移动时重叠
 *     可以分关
 *     可暂停继续
 *     可记录成绩，可存盘退出（敌人坐标）
 *     带BGM
 */
class TankGame extends JFrame implements ActionListener{
    StartPanel startPanel;    // 开始面板
    MapPanel mapPanel;        // 地图面板
    JMenuBar menubar;         // 菜单栏
    // 开始菜单
    JMenu gameMenu;                // 开始
    JMenuItem newGameItem;        // 新游戏
    JMenuItem exitGameItem;       // 直接退出游戏
    JMenuItem saveExitGameItem;   // 存盘退出游戏
    JMenuItem loadGameItem;       // 读取上次游戏
    TankGame(){
        // 读取杀死敌人记录
        Recorder.getEnemyKilleRecord();
        init();
    }
    public void init(){
        startPanel = new StartPanel();   // 创建开始面板，显示文字
        this.add(startPanel);

        Thread startInterval = new Thread(startPanel);  // 文字闪烁定时器
        startInterval.start();

        addMenubar();   // 添加菜单栏

        this.setBounds(1366/2-(MapPanel.MAP_WIDTH+200)/2, 80, MapPanel.MAP_WIDTH + 200, MapPanel.MAP_HEIGHT + 150);
        this.setTitle("坦克大战");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    // 添加菜单栏
    public void addMenubar(){
        menubar = new JMenuBar();
        
        gameMenu = new JMenu("游戏(G)");
        gameMenu.setMnemonic('G');  // 设置快捷按键(ALT + g) char类型
        newGameItem = new JMenuItem("开始新游戏(N)");
        newGameItem.setActionCommand("new_game");
        newGameItem.setMnemonic('N');
        newGameItem.addActionListener(this);   // 通过自身，注册菜单选项监听事件
        exitGameItem = new JMenuItem("退出游戏(E)");
        exitGameItem.setActionCommand("exit_game");
        exitGameItem.setMnemonic('E');
        exitGameItem.addActionListener(this);   // 通过自身，注册菜单选项监听事件
        saveExitGameItem = new JMenuItem("存盘退出游戏(S)");
        saveExitGameItem.setActionCommand("save_exit_game");
        saveExitGameItem.setMnemonic('S');
        saveExitGameItem.addActionListener(this);   // 通过自身，注册菜单选项监听事件
        loadGameItem = new JMenuItem("继续上次游戏(C)");
        loadGameItem.setActionCommand("load_game");
        loadGameItem.setMnemonic('C');
        loadGameItem.addActionListener(this);   // 通过自身，注册菜单选项监听事件
        
        gameMenu.add(newGameItem);
        gameMenu.add(exitGameItem);
        gameMenu.add(saveExitGameItem);
        gameMenu.add(loadGameItem);

        menubar.add(gameMenu);
        this.setJMenuBar(this.menubar);
    }
    public void actionPerformed(ActionEvent e){         // 重写actionPerformed
        if (e.getActionCommand().equals("new_game")) {
            startGame();
        } else if (e.getActionCommand().equals("exit_game")) {
            Recorder.keepEnemyKilledRecord();   // 保存杀死敌人记录
            System.exit(0);
        } else if (e.getActionCommand().equals("save_exit_game")) {
            Recorder.keepEnemyKilledRecord(); // 保存杀死敌人记录
            if (this.mapPanel != null) {
                Recorder.setAllEnemies(mapPanel.enemies);
                Recorder.saveGameRecord();
            }
            System.exit(0);
        } else if (e.getActionCommand().equals("load_game")) {
            Vector<Point> points = Recorder.getGameRecord();
            startGame(points);
        }
    }
    public void startGame(){    // 开始游戏
        startGame(null);
    }
    public void startGame(Vector<Point> points){
        this.mapPanel = new MapPanel(points);  // 创建地图面板
        this.add(this.mapPanel);
        this.addKeyListener(this.mapPanel);  // 通过地图，注册键盘监听事件

        Thread mapInterval = new Thread(this.mapPanel);
        mapInterval.start();         // 启动刷新地图的线程（定时器）

        this.remove(startPanel);    //  移除开始面板
        this.setVisible(true);     // 刷新游戏窗口
    }
    public static void main(String[] args) {
        new TankGame();  // 运行就开始游戏
    }
}
/**
 * 开始面板
 */
class StartPanel extends JPanel implements Runnable{
    private int times = 0;
    public void paint(Graphics g){
        super.paint(g);   // 初始化画笔
        g.fillRect(0, 0, MapPanel.MAP_WIDTH, MapPanel.MAP_HEIGHT);
        if (times % 2 == 0) {
            //当前是第1关
            g.setColor(Color.yellow);
            Font font = new Font("Arial", Font.BOLD, 30);
            g.setFont(font);
            g.drawString("stage: 1", MapPanel.MAP_WIDTH / 3, MapPanel.MAP_HEIGHT / 2);
       }
    }
    public void run(){
        while(true){
            try{
                Thread.sleep(300);
            }catch(Exception e){
                e.printStackTrace();    
            }
            times++;
            repaint();   // 重绘地图
        }
    }
}
/**
 * 坦克地图--绘图：继承一个面板，重写paint方法 - 继承自JComponent
 */
class MapPanel extends JPanel implements KeyListener, Runnable {
    public final static int MAP_WIDTH = 400;    // 地图宽度
    public final static int MAP_HEIGHT = 400;   // 地图高度
    Hero hero;                                          // 主角
    Hero sampleHero;                                    // 主角样本
    Vector<Enemy> enemies = new Vector<Enemy>();        // 地图的所有敌人
    Vector<Enemy> sampleEnemies = new Vector<Enemy>();  // 所有敌人样本
    Vector<Enemy> killedEnemies = new Vector<Enemy>();  // 击杀的所有敌人样本
    ArrayList<Image> imgs = new ArrayList<Image>();     // 爆炸的图片资源
    Vector<Bomb> bombs = new Vector<Bomb>();            // 地图上可能产生的炸弹
    MapPanel(Vector<Point> points){
        // 播放声音
        Sound sound = new Sound("res/bgm.wav");
        sound.start();
        //创建主人公
        createHero();
        //创建敌人
        createEnemies(points);
        // 创建样本坦克，提示信息（不参与战斗）
        createSample();
        // 把所有敌人坦克存储到对象中，用来判断是否与其他的坦克碰撞
        for (int k = 0; k < enemies.size(); k++) {
            Enemy enemy = enemies.get(k);
            enemy.setAllEnemies(enemies);      
        }
        hero.setAllEnemies(enemies);
        // 载入炸弹图片
        loadBombPic();
    }
    public void createHero(){
        hero = new Hero(MapPanel.MAP_WIDTH / 2, MapPanel.MAP_HEIGHT - 30/2);
    }
    public void createEnemies(Vector<Point> points){
        int enemiesNum = Recorder.getEnemiesNum();
        if (points != null && points.size() != 0) {
            enemiesNum = points.size();
        }
        for (int i = 0; i < enemiesNum; i++) {
            int x = (i + 1) * 30, y = 15, direct = 3;
            if (points != null && points.size() != 0) {
                Point point = points.get(i);
                x = point.x; y = point.y; direct = point.direct;
            }
            Enemy enemy = new Enemy(x, y);
            enemy.direct = direct;
            enemy.setIndex(i);
            enemies.add(enemy);
        }
        for (Enemy enemy : enemies) {
            // 让敌人自动移动
            Thread enemyInteval = new Thread(enemy);
            enemyInteval.start();
        }
    }
    public void createSample(){
        sampleHero = new Hero(50, MapPanel.MAP_HEIGHT + 50);
        for (int s = 0; s < 1; s++) {
            Enemy enemy = new Enemy(60 * (s+2), MapPanel.MAP_HEIGHT + 50);
            sampleEnemies.add(enemy);
        }
        for (int i = 0; i < 1; i++) {
            Enemy enemy = new Enemy(MapPanel.MAP_WIDTH + 60, 60 * (i+2));
            killedEnemies.add(enemy);
        }
    }
    public void loadBombPic(){
        try{
            // 创建图片
            for (int j = 1; j <= 3; j++) {
                File f = new File("res", "bomb_" + j +".gif");
                if (f.exists()) {
                    Image img = new ImageIcon(f.getAbsolutePath()).getImage();   // 图片存在，载入图片
                    imgs.add(img);
                } else {
                    throw new Exception("炸弹图片：" + f.getName() + " 不存在！");
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public void showInfo(Graphics g){
        // 画出样本坦克，以及信息
        drawTank(g, sampleHero);
        g.setColor(Color.black);
        g.drawString(Recorder.getMyLife()+"", sampleHero.x+20, sampleHero.y+5);
        for (int s = 0; s < sampleEnemies.size(); s++) {
            Enemy enemy = sampleEnemies.get(s);
            drawTank(g, enemy);
            g.drawString(Recorder.getEnemiesNum()+"", enemy.x+20, enemy.y+5);
        }
        // 画出击杀敌人总数
        for (int i = 0; i < killedEnemies.size(); i++) {
            Enemy enemy = killedEnemies.get(i);
            drawTank(g, enemy);
            g.setColor(Color.black);
            g.drawString(Recorder.getEnemyKilledNum()+"", enemy.x+20, enemy.y+5);
        }
        Font f = new Font("黑体", Font.BOLD, 30);
        g.setFont(f);
        g.setColor(Color.black);
        g.drawString("总成绩：", MapPanel.MAP_WIDTH+10, 30);
    }
    // paint调用情况：最小化再最大化；窗口大小发生变化；创建新对象； repaint被调用
    public void paint(Graphics g){ // Graphics是画笔 java.awt.Graphics
        super.paint(g);  // 调用默认方法，初始化一下
        // 填充地图背景
        g.fillRect(0, 0, MapPanel.MAP_WIDTH, MapPanel.MAP_HEIGHT);
        // 显示信息
        showInfo(g);
        // 画出主角坦克
        if (bombs.size() == 0 && Recorder.getMyLife() > 0 && !hero.isLive) {
            createHero();    // 炸弹消失再创建主角
        }
        if (hero.isLive) {
            drawTank(g, hero);
        }
        // System.out.println("主角坐标x: " + (hero.x + hero.width / 2) + ", 主角坐标y: " + (hero.y + hero.height / 2));
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isLive) {
                drawTank(g, enemy);
            }
        }
        // 画子弹
        drawBullet(g);
        // 画炸弹
        drawBomb(g);
    }
    /**
     * 重绘地图定时器
     */
    public void run(){
        while(true){
            try{
                Thread.sleep(50);
            }catch(Exception e){
                e.printStackTrace();
            }
            hitEnemy();   // 判断是否击中敌人
            if (hero.isLive) {
                hitHero();   // 判断主角是否被击中
            }
            repaint();  // 重绘地图
        }
    }
    /**
     * 监听键盘按下事件
     */
    public void keyPressed(KeyEvent e){
        // 根据方向调用不同函数（可读性）
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.direct = 1;
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.direct = 2;
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.direct = 3;
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.direct = 4;
            hero.moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            if (hero.isLive && hero.bullets.size() < 5) {    // 主角子弹数小于5个才能射击
                hero.shot();  
            }
        }
        repaint();  // 重绘地图
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    /**
     * 绘制坦克，根据坐标绘制（圆中心点）
     */
    public void drawTank(Graphics g, Tank tank){
        g.setColor(tank.getColor());
        int bodyW = 0, bodyH = 0, bodyX = 0, bodyY = 0, r = 0;
        switch (tank.direct) {
        case 1:
        case 3:
            g.fill3DRect(tank.x - tank.width / 2, tank.y - tank.height / 2, tank.wheelW, tank.wheelH, false);  // 画左轮
            g.fill3DRect(tank.x + tank.width / 2 - tank.wheelW, tank.y - tank.height / 2, tank.wheelW, tank.wheelH, false);  // 画右轮
            bodyW = tank.width - tank.wheelW * 2 - tank.spaceH * 2;
            bodyH = tank.height - tank.spaceV * 2;
            bodyX = tank.x - tank.width / 2 + tank.wheelW + tank.spaceH;
            bodyY = tank.y - tank.height / 2 + tank.spaceV;
            g.fill3DRect(bodyX, bodyY, bodyW, bodyH, false);  // 画身体
            r = bodyW < bodyH ? bodyW / 2 : bodyH / 2;
            break;
        case 2:
        case 4:
            g.fill3DRect(tank.x - tank.height / 2, tank.y - tank.width / 2, tank.wheelH, tank.wheelW, false);  // 画左轮
            g.fill3DRect(tank.x - tank.height / 2, tank.y + tank.width / 2 - tank.wheelW, tank.wheelH, tank.wheelW, false);  // 画右轮
            bodyW = tank.height - tank.spaceV * 2;
            bodyH = tank.width - tank.wheelW * 2 - tank.spaceH * 2;
            bodyX = tank.x - tank.height / 2 + tank.spaceV;
            bodyY = tank.y - tank.width / 2 + tank.wheelW + tank.spaceH;
            g.fill3DRect(bodyX, bodyY, bodyW, bodyH, false);  // 画身体
            r = bodyW < bodyH ? bodyW / 2 : bodyH / 2;
            break;
        }
        g.fillOval(tank.x - 5, tank.y - 5, 10, 10); //画盖子
        switch (tank.direct) {
        case 1:
            g.drawLine(tank.x, tank.y - tank.height / 2, tank.x, tank.y);   //画出线
            break;
        case 3:
            g.drawLine(tank.x, tank.y + tank.height / 2, tank.x, tank.y);   //画出线
            break;
        case 2:
            g.drawLine(tank.x + tank.height / 2, tank.y, tank.x, tank.y);   //画出线
            break;
        case 4:
            g.drawLine(tank.x - tank.height / 2, tank.y, tank.x, tank.y);   //画出线
            break;
        }
    }
    /**
     * 绘制子弹
     */
    public void drawBullet(Graphics g){
        // 遍历主角的子弹
        for (int i = 0; i < hero.bullets.size(); i++) {
            Bullet heroBullet = hero.bullets.get(i);    // 单个取出，存储起来，避免冲突
            if (heroBullet.isLive) {
                g.setColor(hero.getColor());
                g.fill3DRect(heroBullet.x - 1, heroBullet.y - 1, 2, 2, false);  // 子弹存活，画出
            } else {
                hero.bullets.remove(heroBullet);  // 子弹死亡，移除
            }
        }
        // 遍历敌人的子弹
        for (int j = 0; j < enemies.size(); j++) {
            Enemy enemy = enemies.get(j);
            if (enemy.isLive) {
                for (int k = 0; k < enemy.bullets.size(); k++) {
                    Bullet enemyBullet = enemy.bullets.get(k);
                    if (enemyBullet.isLive) {
                        g.setColor(enemy.getColor());
                        g.fill3DRect(enemyBullet.x - 1, enemyBullet.y - 1, 2, 2, false);  // 子弹存活，画出
                    } else {
                        enemy.bullets.remove(enemyBullet);  // 子弹死亡，移除
                    }
                }
            }
        }
    }
    /**
     * 判断子弹是否射中敌人（绘图定时器里判断）
     */
    public void hitEnemy(){
        // 遍历主角的子弹
        for (int i = 0; i < hero.bullets.size(); i++) {
            // 存储主角子弹
            Bullet bullet = hero.bullets.get(i);
            // 主角子弹正在飞
            if (bullet.isLive) {
                // 遍历敌人
                for (int j = 0; j < enemies.size(); j++) {
                    // 存储敌人
                    Enemy enemy = enemies.get(j);
                    // 敌人没死
                    if (enemy.isLive) {
                        // 射中敌人
                        if (bullet.y <= enemy.y + enemy.height / 2 && bullet.y >= enemy.y - enemy.height / 2 && bullet.x <= enemy.x + enemy.width / 2 && bullet.x >= enemy.x - enemy.width / 2) {
                            // 设置敌人和子弹都死亡
                            bullet.isLive = false;
                            enemy.isLive = false;
                            // 减少敌人数量
                            Recorder.reduceEnemies();
                            // 增加敌人总数
                            Recorder.increaseEnemyKilledNum();
                            // 创建炸弹到敌人的坐标
                            Bomb bomb = new Bomb(enemy.x - enemy.width / 2, enemy.y - enemy.height / 2);
                            bombs.add(bomb);
                        }
                    }
                }
            }
        }
    }
    /**
     * 判断自己是否被击中
     */
    public void hitHero(){
        // 遍历敌人
        for (int j = 0; j < enemies.size(); j++) {
            // 存储敌人
            Enemy enemy = enemies.get(j);
            // 遍历敌人子弹，敌人和你同归情况，无需判断敌人死没
            for (int i = 0; i < enemy.bullets.size(); i++) {
                // 存储敌人子弹
                Bullet bullet = enemy.bullets.get(i);
                if (bullet.isLive) {
                    // 敌人击中主角
                    if (bullet.y <= hero.y + hero.height / 2 && bullet.y >= hero.y - hero.height / 2 && bullet.x <= hero.x + hero.width / 2 && bullet.x >= hero.x - hero.width / 2) {
                        // 设置主角和敌人子弹都死亡
                        bullet.isLive = false;
                        hero.isLive = false;
                        Recorder.reduceMyLife();
                        // 创建炸弹到主角的坐标
                        Bomb bomb = new Bomb(hero.x - hero.width / 2, hero.y - hero.height / 2);
                        bombs.add(bomb);
                    }
                }
            }
        }
    }
    /**
     * 画炸弹
     */
    public void drawBomb(Graphics g){
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.time > 6) {
                g.drawImage(this.imgs.get(0), bomb.x, bomb.y, 30, 30, this);
            } else if (bomb.time > 3){
                g.drawImage(this.imgs.get(1), bomb.x, bomb.y, 30, 30, this);
            } else {
                g.drawImage(this.imgs.get(2), bomb.x, bomb.y, 30, 30, this);
            }
            bomb.lifeDown();   // 减少炸弹时间
            if (bomb.time == 0) {
                bombs.remove(bomb);  // 时间到，移除炸弹
            }
        }
    }
}
/**
 * 坦克类
 */
class Tank{
    public int x = 15, y = 15;        // 坦克中心点坐标
    public int direct = 1;          // 坦克的方向  1 ↑ 2 → 3 ↓ 4 ←
    private Color color;             // 坦克的颜色
    public int width = 30;          // 坦克的宽
    public int height = 30;         // 坦克的高
    public int wheelW;              // 坦克轮子的宽
    public int wheelH;              // 坦克轮子的高
    public int spaceH;              // 坦克身体与轮子间的水平间隔
    public int spaceV;              // 坦克身体与轮子间的上下间隔
    public int speed = 1;           // 坦克的速度
    public boolean isLive = true;   // 坦克是否存活
    public boolean canMove = true;  // 坦克可以向前移动
    public Vector<Enemy> allEnemies = new Vector<Enemy>();
    private int index = 0;
    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return index;
    }
    Tank(int x, int y){
        this.x = x;
        this.y = y;
        this.wheelW = this.wheelW == 0 ? this.width / 6 : this.width;
        this.wheelH = this.height;
        this.spaceH = this.spaceH == 0 ? this.width / 30 : this.spaceH;
        this.spaceV = this.spaceV ==0 ? this.width / 6 : this.spaceV;
    }
    /**
     * 创建子弹
     */
    public Bullet createBullet(){
        // 根据方向创建子弹
        Bullet bullet = null;
        switch (direct) {
        case 1:
            bullet = new Bullet(x, y - height / 2); break;
        case 2:
            bullet = new Bullet(x + height / 2, y); break;
        case 3:
            bullet = new Bullet(x, y + height / 2); break;
        case 4:
            bullet = new Bullet(x - height / 2, y); break;
        }
        bullet.direct = this.direct;
        // 添加线程（子弹移动定时器）
        Thread bulletInterval = new Thread(bullet);
        bulletInterval.start();
        return bullet;
    }
    /**
     * 向上移动
     */
    public void moveUp(){
        this.canMove = false; 
        if (y - height / 2 <= 5) {
            y = height / 2;
        } else {
            if (!this.isTouchOtherEnemy()) {
                y -= speed;
                this.canMove = true;
            }
        }
    }
    /**
     * 向右移动
     */
    public void moveRight(){
        this.canMove = false; 
        if (x + width / 2 >= MapPanel.MAP_WIDTH) {
            x = MapPanel.MAP_WIDTH - width / 2;
        } else { 
            if (!this.isTouchOtherEnemy()) {
                x += speed;
                this.canMove = true;
            }
        }
    }
    /**
     * 向下移动
     */
    public void moveDown(){
        this.canMove = false; 
        if (y + height / 2 >= MapPanel.MAP_HEIGHT) {
            y = MapPanel.MAP_HEIGHT - height / 2;
        } else {
            if (!this.isTouchOtherEnemy()) {
                y += speed;
                canMove = true;
            }
        }
    }
    /**
     * 向左移动
     */
    public void moveLeft(){
        this.canMove = false; 
        if (x - width / 2 <= 5) {
            x = width / 2;
        } 
        else {
            if (!this.isTouchOtherEnemy()) {
                x -= speed;
                this.canMove = true;
            }
        }
    }
    /**
     * 判断敌人是否碰撞
     */
    public boolean isTouchOtherEnemy(){
        int size = this.allEnemies.size();
        int l1 = 0, t1 = 0, r1 = 0, b1 = 0;
        int l2 = 0, t2 = 0, r2 = 0, b2 = 0;
        if (this.direct == 1 || this.direct == 3) {
            l1 = this.x - this.width / 2;
            t1 = this.y - this.height / 2;
            r1 = this.x + this.width / 2;
            b1 = this.y + this.height / 2;
        } else if (this.direct == 2 || this.direct == 4) {
            l1 = this.x - this.height / 2;
            t1 = this.y - this.width / 2;
            r1 = this.x + this.height / 2;
            b1 = this.y + this.width / 2;
        }
        for (int i = 0; i < size; i++) {
            Enemy enemy = this.allEnemies.get(i);
            if (enemy != this && enemy.isLive) {
                if (enemy.direct == 1 || enemy.direct == 3) {
                    l2 = enemy.x - enemy.width / 2;
                    t2 = enemy.y - enemy.height / 2;
                    r2 = enemy.x + enemy.width / 2;
                    b2 = enemy.y + enemy.height / 2;
                } else if (enemy.direct == 2 || enemy.direct == 4) {
                    l2 = enemy.x - enemy.width / 2;
                    t2 = enemy.y - enemy.height / 2;
                    r2 = enemy.x + enemy.width / 2;
                    b2 = enemy.y + enemy.height / 2;
                }
                boolean isLeftInRange=(l1>=l2&&l1<=r2);
                boolean isRightInRange=(r1>=l2&&r1<=r2);
                boolean isTopInRange=(t1>=t2&&t1<=b2);
                boolean isBottomInRange=(b1>=t2&&b1<=b2);
                if (this.direct == 1) {
                    if (isTopInRange && (isLeftInRange || isRightInRange)) {
                        return true;
                        // if(this.getIndex()==0)System.out.print("isTopInRange:"+isTopInRange+",isLeftInRange:"+isLeftInRange+",isRightInRange:"+isRightInRange+"|");
                        // if(this.getIndex()==0)System.out.println("本坦克: "+this.getIndex()+"方向："+this.direct+"坐标：(left:"+l1+", right:"+r1+",top:"+t1+",bottom:"+b1+")与"+"其他坦克: "+enemy.getIndex()+"方向："+enemy.direct+"坐标：(left:"+l2+", right:"+r2+",top:"+t2+",bottom:"+b2+")相撞");
                    }
                }
                if (this.direct == 2) {
                    if (isRightInRange && (isTopInRange || isBottomInRange)) {
                        // if(this.getIndex()==0)System.out.print("isRightInRange:"+isRightInRange+",isTopInRange:"+isTopInRange+",isBottomInRange:"+isBottomInRange+"|");
                        // if(this.getIndex()==0)System.out.println("本坦克: "+this.getIndex()+"方向："+this.direct+"坐标：(left:"+l1+", right:"+r1+",top:"+t1+",bottom:"+b1+")与"+"其他坦克: "+enemy.getIndex()+"方向："+enemy.direct+"坐标：(left:"+l2+", right:"+r2+",top:"+t2+",bottom:"+b2+")相撞");
                        return true;
                    }
                }
                if (this.direct == 3) {
                    if (isBottomInRange && (isLeftInRange || isRightInRange)) {
                        return true;
                        // if(this.getIndex()==0)System.out.print("isBottomInRange:"+isBottomInRange+",isLeftInRange:"+isLeftInRange+",isRightInRange:"+isRightInRange+"|");
                        // if(this.getIndex()==0)System.out.println("本坦克: "+this.getIndex()+"方向："+this.direct+"坐标：(left:"+l1+", right:"+r1+",top:"+t1+",bottom:"+b1+")与"+"其他坦克: "+enemy.getIndex()+"方向："+enemy.direct+"坐标：(left:"+l2+", right:"+r2+",top:"+t2+",bottom:"+b2+")相撞");
                    }
                }
                if (this.direct == 4) {
                    if (isLeftInRange && (isTopInRange || isBottomInRange)) {
                        // if(this.getIndex()==0)System.out.print("isLeftInRange:"+isLeftInRange+",isTopInRange:"+isTopInRange+",isBottomInRange:"+isBottomInRange+"|");
                        // if(this.getIndex()==0)System.out.println("本坦克: "+this.getIndex()+"方向："+this.direct+"坐标：(left:"+l1+", right:"+r1+",top:"+t1+",bottom:"+b1+")与"+"其他坦克: "+enemy.getIndex()+"方向："+enemy.direct+"坐标：(left:"+l2+", right:"+r2+",top:"+t2+",bottom:"+b2+")相撞");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return color;
    }
    public void setAllEnemies(Vector<Enemy> enemies){
        this.allEnemies = enemies;
    }
    public String toString(){
        return "坦克坐标：(" + x + ", " + y + "), 是否存活：" + isLive + ",宽：" + width + ",高：" + height;
    }
}
/**
 * 主角类
 */
class Hero extends Tank{
    public int speed = 10;  // 移动速度
    public Color color = Color.yellow;
    public Vector<Bullet> bullets = new Vector<Bullet>();  // 所有子弹
    Hero(int x, int y){
        super(x, y);
        setSpeed(this.speed);
        setColor(this.color);
    }
    /**
     * 发射子弹
     */
    public void shot(){
        Bullet bullet = createBullet();
        bullets.add(bullet);
    }
    public String toString(){
        return "主角坐标：(" + x + ", " + y + ")";
    }
}
/**
 * 敌人类
 */
class Enemy extends Tank implements Runnable{
    public int speed = 5;
    public Color color = Color.cyan;
    public Vector<Bullet> bullets = new Vector<Bullet>();  // 敌人所有子弹
    Enemy(int x, int y){
        super(x, y);
        setSpeed(this.speed);
        setColor(this.color);
    }
    /**
     * 敌人自动移动定时器
     */
    public void run(){
        // System.out.println("敌人坐标x:" + x + ",敌人坐标y:" + y);
        while(true){
            try{
                Thread.sleep(50);   // 定时器时间
            }catch(Exception e){
                e.printStackTrace();    
            }
            // 敌人死亡退出线程
            if (!this.isLive) break;
            // 敌人自动移动 朝反方向移动
            int direct = (int)(Math.random() * 4) + 1;
            // System.out.println("敌人下一个方向：" + direct);
            switch(direct){
            case 1: // 方向朝上 则向下移动
                this.direct = 3;
                for (int i = 0; i < 30; i++) {
                    super.moveDown();
                    try{
                        Thread.sleep(50);   // 定时器速度
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if (!this.canMove) {
                        break;
                    }
                }     
                break;
            case 2: // 方向朝右，向左移动
                this.direct = 4; 
                for (int i = 0; i < 30; i++) {
                    super.moveLeft();
                    try{
                        Thread.sleep(50);   // 定时器速度
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if (!this.canMove) {
                        break;
                    }
                }
                break;
            case 3: // 方向朝下，向上移动
                this.direct = 1;
                for (int i = 0; i < 30; i++) {
                    super.moveUp();
                    try{
                        Thread.sleep(50);   // 定时器速度
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if (!this.canMove) {
                        break;
                    }
                }
                break;
            case 4: // 方向朝左，向右移动
                this.direct = 2;
                for (int i = 0; i < 30; i++) {
                    super.moveRight();
                    try{
                        Thread.sleep(50);   // 定时器速度
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if (!canMove) {
                        break;
                    }
                }
                break;
            }
            // 给敌人子弹
            if (bullets.size() < 2) {
                Bullet bullet = createBullet();
                bullets.add(bullet);
            }
        }
    }
    public String toString(){
        return "敌人"+ getIndex() +"坐标：(" + x + ", " + y + "), 是否存活：" + isLive;
    }
}
/**
 * 子弹类
 */
class Bullet implements Runnable{
    public int x, y;                  // 子弹坐标
    public int speed = 10;            // 子弹移动速度
    public boolean isLive = true;    // 子弹是否存活
    public int direct;               // 子弹方向
    Bullet(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void run(){              // 子弹移动线程
        while(true){
            try{
                Thread.sleep(50);
            }catch(Exception e){
                e.printStackTrace();
            }
            // System.out.println("子弹坐标x:" + x + ", 子弹坐标y:" + y);
            if (x <= 0 || y <=0 || x >= MapPanel.MAP_WIDTH || y >= MapPanel.MAP_HEIGHT) {   // 超出边界，子弹死亡
                isLive = false;
                break;
            }
            switch(direct){
            case 1: y -= speed; break;   // 上
            case 2: x += speed; break;   // 右
            case 3: y += speed; break;   // 下
            case 4: x -= speed; break;   // 左
            }
        }
    }
    public String toString(){
        return "子弹坐标：(" + x + ", " + y + "), 是否存活：" + isLive;
    }
}
/**
 * 炸弹类
 */
class Bomb {
    int x, y;
    int time = 9;
    boolean isLive = true;
    Bomb(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void lifeDown(){
        if (time > 0) {
            time--;
        } else {
            isLive = false;
        }
    }
    public String toString(){
        return "炸弹坐标：(" + x + ", " + y + "), 是否存活：" + isLive;
    }
}
/**
 * 记录类，设置游戏
 */
class Recorder {
    private static int enemiesNum = 10;     // 敌人数量
    private static int myLife = 3;          // 主角生命
    private static int enemyKilledNum = 0;  // 杀死敌人的总数
    private static Vector<Enemy> allEnemies;    //  所有敌人
    public static void setEnemiesNum(int n){    // 设置敌人数量
        Recorder.enemiesNum = n;
    }
    public static void setMyLife(int n){        // 设置玩家生命
        Recorder.myLife = n;
    }
    public static int getEnemiesNum(){          // 获取敌人数量
        return Recorder.enemiesNum;
    }
    public static int getMyLife(){              // 获取玩家生命
        return Recorder.myLife;
    }
    public static void reduceEnemies(){         // 减少敌人的数量
        Recorder.enemiesNum--;
    }
    public static void reduceMyLife(){          // 减少玩家的生命
        Recorder.myLife--;
    }
    public static int getEnemyKilledNum(){      //  获取杀死敌人的总数
        return Recorder.enemyKilledNum;
    }
    public static void increaseEnemyKilledNum(){  // 增加杀死敌人的总数
        ++Recorder.enemyKilledNum;
    }
    public static void keepEnemyKilledRecord(){  // 保存玩家杀死敌人的记录
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("record.txt"), "utf-8"));
            bw.write("杀死敌人的总数:" + enemyKilledNum);
            bw.newLine();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if (bw != null) {
                try{
                    bw.close();
                }catch(IOException e2){
                    e2.printStackTrace();
                }
            }
        }
    }
    public static void getEnemyKilleRecord(){   // 从文件获取杀死敌人的数量
        File record = new File("record.txt");
        if (record.exists()) {
            BufferedReader br = null;
            try{
                br = new BufferedReader(new InputStreamReader(new FileInputStream("record.txt"), "utf-8"));
                String line = null;
                if((line = br.readLine()) != null){
                    try{
                        Matcher m = Pattern.compile("\\d+").matcher(line);
                        if (m.find()) {
                            Recorder.enemyKilledNum = Integer.parseInt(m.group());
                        }
                    }catch(Exception e3){
                        System.out.println("字符串：\"" + line + "\"匹配不到数字");
                    }   
                }
            }catch(IOException e1){
                e1.printStackTrace();
            }finally{
                if (br != null) {
                    try{
                        br.close();
                    }catch(Exception e2){
                        e2.printStackTrace();
                    }
                }
            }
        }
    }
    public static void setAllEnemies(Vector<Enemy> enemies){
        allEnemies = enemies;
    }
    public static Vector<Enemy> getAllEnemies(){
        return allEnemies;
    }
    public static void saveGameRecord(){    // 保存游戏记录（敌人位置）
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("record.txt", true), "utf-8"));
            // 保存敌人坐标，方向，死亡情况，数量
            for (Enemy enemy : allEnemies) {
                if (enemy.isLive) {
                    String point = enemy.x + "," + enemy.y + "," + enemy.direct;
                    bw.write(point);
                    bw.newLine();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if (bw != null) {
                try{
                    bw.close();
                }catch(IOException e2){
                    e2.printStackTrace();
                }
            }
        }
    }
    public static Vector<Point> getGameRecord(){    //获取游戏记录（敌人位置）
        File record = new File("record.txt");
        Vector<Point> points = new Vector<Point>();
        if (record.exists()) {
            BufferedReader br = null;
            try{
                br = new BufferedReader(new InputStreamReader(new FileInputStream("record.txt"), "utf-8"));
                br.readLine();          // 读取第一行，跳过这行（杀死敌人的总数）
                String line = null;
                while((line = br.readLine()) != null){
                    String[] p = line.split(",");
                    Point point = 
                        new Point(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
                    points.add(point);
                }
            }catch(IOException e1){
                e1.printStackTrace();
            }finally{
                if (br != null) {
                    try{
                        br.close();
                    }catch(Exception e2){
                        e2.printStackTrace();
                    }
                }
            }
        }
        return points;
    }
}
/**
 * 坐标类
 */
class Point{
    public int x; // x坐标
    public int y; // y坐标
    public int direct; // 方向
    Point(int x, int y, int direct){
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
}
/**
 * 播放声音的类
 */
class Sound extends Thread{
    private String filename;
    public Sound(String filename){
        this.filename = filename;
    }
    public void run(){
        File soundFile = new File(filename);
        AudioInputStream audioInputStream = null;     // 音频输入流
        try{
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);  // 通过文件获取音频输入流
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

        AudioFormat audioFormat = audioInputStream.getFormat(); // 音频格式化流
        SourceDataLine sourceDataLine = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);  // 数据信息，准备格式化

        try{
            sourceDataLine = (SourceDataLine)AudioSystem.getLine(info);    // 传递音频数据信息，通过音频系统读取音频流
            sourceDataLine.open(audioFormat);
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        sourceDataLine.start();     //打开音乐

        int data = 0;
        byte[] buffer = new byte[1024];
        try{
            while(data != -1){
                data = audioInputStream.read(buffer, 0, buffer.length);
                if (data >= 0) {
                    sourceDataLine.write(buffer, 0, data);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }finally{
            sourceDataLine.drain();   // 处理残余流
            sourceDataLine.close();
        }
    }
}