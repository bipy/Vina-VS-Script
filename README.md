# 基于 Vina 的多线程脚本

我不会告诉你我写这个是因为我的电脑打不开 PyRx (Python 2/3 issue, whatever)



## 功能

调用 Vina 多线程虚拟筛选，并输出 CSV 文件



## 使用方法

1. 编辑 Variables.java 参数，参数说明见注释

2. 运行start.bat

3. 等待



## 注意

1. 要求 Java 8 及以上版本环境
2. 参数不能出现任何中文
3. Main.java 下，线程数 THREAD_COUNT 设置为电脑的CPU核数即可（计算密集型场景，默认4）
4. 如果乱码请尝试用 utf-8 打开文件
5. 开始运行后CPU占用会一直100%，电脑可能卡慢



****

本地测试正常，如果有bug请自行修复调试（逃