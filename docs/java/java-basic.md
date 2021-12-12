## 数据类型
### 基本类型
+ byte/8bit
+ char/16bit
+ short/16bit
+ double/64bit
+ float/32bit
+ int/32bit
+ long/64bit
+ boolean/~

boolean 只有两个值：true 和 false，可以使用1bit存储，但是具体大小没有明确规定。JVM 会在编译时期将 boolean 类型的数据转换为 int，使用 1 来表示 true，0 表示 false。JVM 支持 boolean 数组，但是是通过读写 byte 数组来实现的。

1byte=8bit，8位作为一个字节，字符（char）是 Java 中的一种基本数据类型，由两个字节组成。在 UTF-8 编码中，一个英文字符占1个字节，一个中文（含繁体）占3个字节，英文标点占1个字节，中文标点占3个字节。而 Unicode 编码中英文和中文都是占2个字节，标点也是一样。

### 包装类型
基本类型都有对应的包装类型，基本类型与其对应的包装类型之间的赋值使用自动装箱与拆箱完成。
````
Integer x = 2; // 装箱
int y = x;     // 拆箱
````
### 缓存池


## String

## 运算

## 继承

## Object 通用方法

## 关键字

## 反射

## 异常

## 泛型

## 注解


