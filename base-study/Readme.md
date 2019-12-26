使用的java版本：1.8.0_11,垃圾收集器为：新生代：Parallel Scavenge收集器   老生代：Parallel Old

VM参数：-Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails 

-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:/tmp/

-Xms ：内存最小值为20M

-Xmx：内存最大值为20M

-Xmn：新生代为10M，则老生代同样为10M

-XX:SurvivorRatio=8：新生代中eden和survivor的比率，从上面配置可知：eden区为8M，from survivor和to survivor各为1M。

-XX:+PrintGCDetails ：打印GC详细日志信息

-XX:+HeapDumpOnOutOfMemoryError：OOM时输出堆栈信息

-XX:HeapDumpPath：堆栈信息输出的目录

## 1：强引用GC回收分析

### 1.1 代码：

```
byte[] byte1 = new byte[ONE_MB];
byte[] byte2 = new byte[ONE_MB];
byte[] byte3 = new byte[2 * ONE_MB];
byte[] byte4 = new byte[2 * ONE_MB];
byte[] byte5 = new byte[2 * ONE_MB];
byte[] byte6 = new byte[2 * ONE_MB];
byte[] byte7 = new byte[2 * ONE_MB];
byte[] byte8 = new byte[2 * ONE_MB];
byte[] byte9 = new byte[2 * ONE_MB];
```

### 1.2 GC日志：

```
[GC (Allocation Failure) [PSYoungGen: 6653K->863K(9216K)] 6653K->4959K(19456K), 0.0034892 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) --[PSYoungGen: 7277K->7277K(9216K)] 11373K->15477K(19456K), 0.0048281 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 7277K->2787K(9216K)] [ParOldGen: 8200K->8192K(10240K)] 15477K->10979K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0101014 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 7036K->6882K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15229K->15074K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0084835 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 6882K->6856K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15074K->15048K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0080821 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
java.lang.OutOfMemoryError: Java heap space
Dumping heap to d:/tmp/\java_pid14348.hprof ...
Heap dump file created [16208674 bytes in 0.024 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
at StrongReferenceDemo.main(StrongReferenceDemo.java:24)
Heap
 PSYoungGen      total 9216K, used 6984K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 85% used [0x00000000ff600000,0x00000000ffcd21e0,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 8192K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 80% used [0x00000000fec00000,0x00000000ff400080,0x00000000ff600000)
 Metaspace       used 3176K, capacity 4568K, committed 4864K, reserved 1056768K
  class space    used 339K, capacity 392K, committed 512K, reserved 1048576K

```

### 1.3 GC日志分析：

第一次younggc：
在byte[] byte4 = new byte[2 * ONE_MB] 执行原因：应为此时新生代的eden区内存已经占用了6M以上，无法再放入2M
[GC (Allocation Failure) [PSYoungGen: 6653K->863K(9216K)] 6653K->4959K(19456K), 0.0034892 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 

第一次gc完的结果为：新生代内存使用了863K，老生代使用了：4096K
gc完后，需要执行byte4的初始化操作，执行后内存布局，约等于：新生代内存使用了2911K，老生代使用了：4096K

byte[] byte5 = new byte[2 * ONE_MB];  此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了4959K，老生代使用了：4096K
byte[] byte6 = new byte[2 * ONE_MB];  此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了7007K，老生代使用了：4096K

byte[] byte7 = new byte[2 * ONE_MB];  此时新生代的eden区内存占用了超过7M，无法再放入2M
[GC (Allocation Failure) --[PSYoungGen: 7277K->7277K(9216K)] 11373K->15477K(19456K), 0.0048281 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]  

**此处为什么是yonggc，是因为在老生代可用内存 > 历次从新生代晋升到老生代的内存时，则冒险一次，进行一次yonggc。本次yonggc没有实质性的内容改变，所以还是要执行full gc**。

[Full GC (Ergonomics) [PSYoungGen: 7277K->2787K(9216K)] [ParOldGen: 8200K->8192K(10240K)] 15477K->10979K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0101014 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 

第三次gc（full gc）完的结果为:新生代内存使用了2787K，老生代使用了：8192K。
gc完后，需要执行byte7的初始化操作，执行后内存布局，约等于：新生代内存使用了4835K，老生代使用了：8192K。
byte[] byte8 = new byte[2 * ONE_MB]; 此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了6883K，老生代使用了：8192K

byte[] byte9 = new byte[2 * ONE_MB]; 此时eden区内存不足，需要执行full  gc
[Full GC (Ergonomics) [PSYoungGen: 7036K->6882K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15229K->15074K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0084835 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 

第四次执行之后: 发现内存没有什么改变，新生代内存使用了6882K，老生代使用了：8192K。

[Full GC (Allocation Failure) [PSYoungGen: 6882K->6856K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15074K->15048K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0080821 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]

第五次执行GC后，发现并不能空余出足够的空间，用于存放2M的数据，于是出现了OOM。

### 1.4 总结

在据有强引用的情况下，即使出现OOM，垃圾收集器也不会去回收强引用。

## 2：软引用GC回收分析

### 2.1 代码：

```
byte[] byte1 = new byte[ONE_MB];
byte[] byte2 = new byte[ONE_MB];
byte[] byte3 = new byte[2 * ONE_MB];
SoftReference<byte[]> byte3Ref = new SoftReference<byte[]>(byte3);
byte3 = null;
byte[] byte4 = new byte[2 * ONE_MB];
byte[] byte5 = new byte[2 * ONE_MB];
byte[] byte6 = new byte[2 * ONE_MB];
byte[] byte7 = new byte[2 * ONE_MB];
byte[] byte8 = new byte[2 * ONE_MB];
byte[] byte9 = new byte[2 * ONE_MB];
```

### 2.2 GC日志:

```
[GC (Allocation Failure) [PSYoungGen: 6653K->911K(9216K)] 6653K->5015K(19456K), 0.0046627 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) --[PSYoungGen: 7325K->7325K(9216K)] 11429K->15525K(19456K), 0.0033280 secs] [Times: user=0.03 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 7325K->2787K(9216K)] [ParOldGen: 8200K->8192K(10240K)] 15525K->10979K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0095494 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 7037K->6883K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15229K->15075K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0128955 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 6883K->4310K(9216K)] [ParOldGen: 8192K->8690K(10240K)] 15075K->13000K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0084480 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
Heap
 PSYoungGen      total 9216K, used 6440K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 78% used [0x00000000ff600000,0x00000000ffc4a250,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 8690K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 84% used [0x00000000fec00000,0x00000000ff47c8c8,0x00000000ff600000)
 Metaspace       used 3150K, capacity 4568K, committed 4864K, reserved 1056768K
  class space    used 336K, capacity 392K, committed 512K, reserved 1048576K
```

### 2.3 GC日志分析

第一次younggc：
在byte[] byte4 = new byte[2 * ONE_MB] 执行原因：应为此时新生代的eden区内存已经占用了6M以上，无法再放入2M
[GC (Allocation Failure) [PSYoungGen: 6653K->943K(9216K)] 6653K->5047K(19456K), 0.0033578 secs] [Times: user=0.00 sys=0.01, real=0.00 secs]  

第一次gc完的结果为：新生代内存使用了943K，老生代使用了：4104K
gc完后，需要执行byte4的初始化操作，执行后内存布局，约等于：新生代内存使用了2991K，老生代使用了：4104K

byte[] byte5 = new byte[2 * ONE_MB];  此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了5039K，老生代使用了：4104K
byte[] byte6 = new byte[2 * ONE_MB];  此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了7087K，老生代使用了：4104K

byte[] byte7 = new byte[2 * ONE_MB];  此时新生代的eden区内存占用了超过7M，无法再放入2M
[GC (Allocation Failure) --[PSYoungGen: 7357K->7357K(9216K)] 11461K->15557K(19456K), 0.0043992 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 

此处为什么是yonggc，是因为在老生代可用内存 > 历次从新生代晋升到老生代的内存时，则冒险一次，进行一次yonggc。本次yonggc没有实质性的内容改变，所以还是要执行full gc。

[Full GC (Ergonomics) [PSYoungGen: 7357K->2787K(9216K)] [ParOldGen: 8200K->8192K(10240K)] 15557K->10979K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0080787 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

第三次gc（full gc）完的结果为:新生代内存使用了2787K，老生代使用了：8192K。
gc完后，需要执行byte7的初始化操作，执行后内存布局，约等于：新生代内存使用了4835K，老生代使用了：8192K。
byte[] byte8 = new byte[2 * ONE_MB]; 此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了6883K，老生代使用了：8192K


byte[] byte9 = new byte[2 * ONE_MB]; 此时eden区内存不足，需要执行full  gc
[Full GC (Ergonomics) [PSYoungGen: 7037K->6883K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15229K->15075K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0137220 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 

第四次执行之后: 发现内存没有什么改变，新生代内存使用了6883K，老生代使用了：8192K。

马上就要出现oom了，此时作为softReference的对象byte3，需要回收掉，以释放其占用的内存。

**可以看到此处回收掉了byte3所占用的2M内存，说明对象只有SoftReference时，在将要出现OOM时，会被垃圾收集器回收掉**。
[Full GC (Allocation Failure)[PSYoungGen: 6883K->4310K(9216K)] [ParOldGen: 8192K->8690K(10240K)] 15075K->13000K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0107278 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
第五次，执行之后: 新生代内存使用了4310K，老生代使用了：8690K。
需要执行byte4的初始化操作，执行后内存布局，约等于：新生代内存使用了6440K，老生代使用了：8690K

### 2.4 总结

在据有软引用的情况下，只有当快出现OOM时，垃圾收集器才会去回收掉该部分内存。

## 3：弱引用GC回收分析

### 3.1 代码：

```
byte[] byte1 = new byte[ONE_MB];
byte[] byte2 = new byte[ONE_MB];
byte[] byte3 = new byte[2 * ONE_MB];
WeakReference<byte[]> byte3Ref = new WeakReference<byte[]>(byte3);
byte3 = null;
byte[] byte4 = new byte[2 * ONE_MB];
byte[] byte5 = new byte[2 * ONE_MB];
byte[] byte6 = new byte[2 * ONE_MB];
byte[] byte7 = new byte[2 * ONE_MB];
byte[] byte8 = new byte[2 * ONE_MB];
byte[] byte9 = new byte[2 * ONE_MB];
```

### 3.2 GC日志：

```
[GC (Allocation Failure) [PSYoungGen: 6653K->879K(9216K)] 6653K->2935K(19456K), 0.0017965 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 7293K->824K(9216K)] 9349K->9024K(19456K), 0.0039197 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 824K->0K(9216K)] [ParOldGen: 8200K->8931K(10240K)] 9024K->8931K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0079013 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
Heap
 PSYoungGen      total 9216K, used 6380K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 77% used [0x00000000ff600000,0x00000000ffc3b0d8,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 8931K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 87% used [0x00000000fec00000,0x00000000ff4b8d78,0x00000000ff600000)
 Metaspace       used 3150K, capacity 4568K, committed 4864K, reserved 1056768K
  class space    used 336K, capacity 392K, committed 512K, reserved 1048576K
```

### 3.3 GC日志分析：

第一次younggc：
在byte[] byte4 = new byte[2 * ONE_MB] 执行原因：应为此时新生代的eden区内存已经占用了6M以上，无法再放入2M
[GC (Allocation Failure) [PSYoungGen: 6653K->879K(9216K)] 6653K->2935K(19456K), 0.0017965 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]  

第一次gc完的结果为：新生代内存使用了879K，老生代使用了：2056K    **可以看到此处已经回收掉了byte3所占用的2M内存，说明对象只有WeakReference时，在进行GC回收时，就会被GC回收掉。**
gc完后，需要执行byte4的初始化操作，执行后内存布局，约等于：新生代内存使用了2991K，老生代使用了：4104K

byte[] byte5 = new byte[2 * ONE_MB];  此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了5039K，老生代使用了：4104K
byte[] byte6 = new byte[2 * ONE_MB];  此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了7087K，老生代使用了：4104K

byte[] byte7 = new byte[2 * ONE_MB];  此时新生代的eden区内存占用了超过7M，无法再放入2M
[GC (Allocation Failure) --[PSYoungGen: 7357K->7357K(9216K)] 11461K->15557K(19456K), 0.0043992 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 

此处为什么是yonggc，是因为在老生代可用内存 > 历次从新生代晋升到老生代的内存时，则冒险一次，进行一次yonggc。本次yonggc没有实质性的内容改变，所以还是要执行full gc。

[Full GC (Ergonomics) [PSYoungGen: 7357K->2787K(9216K)] [ParOldGen: 8200K->8192K(10240K)] 15557K->10979K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0080787 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

第三次gc（full gc）完的结果为:新生代内存使用了2787K，老生代使用了：8192K。
gc完后，需要执行byte7的初始化操作，执行后内存布局，约等于：新生代内存使用了4835K，老生代使用了：8192K。
byte[] byte8 = new byte[2 * ONE_MB]; 此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了6883K，老生代使用了：8192K

byte[] byte9 = new byte[2 * ONE_MB]; 此时eden区内存不足，需要执行full  gc
[Full GC (Ergonomics) [PSYoungGen: 7037K->6883K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15229K->15075K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0137220 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 

第四次执行之后: 发现内存没有什么改变，新生代内存使用了6883K，老生代使用了：8192K。

马上就要出现oom了，此时作为softReference的对象byte3，需要回收掉，以释放其占用的内存。
[Full GC (Allocation Failure)[PSYoungGen: 6883K->4310K(9216K)] [ParOldGen: 8192K->8690K(10240K)] 15075K->13000K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0107278 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
第五次，执行之后: 新生代内存使用了4310K，老生代使用了：8690K。
需要执行byte4的初始化操作，执行后内存布局，约等于：新生代内存使用了6440K，老生代使用了：8690K

### 3.4 总结

在据有弱引用的情况下，只要发生GC，垃圾收集器就会进行回收。

## 4：虚引用GC回收分析

### 4.1 代码:

```java
byte[] byte1 = new byte[ONE_MB];
byte[] byte2 = new byte[ONE_MB];
byte[] byte3 = new byte[2 * ONE_MB];
ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<byte[]>();
PhantomReference<byte[]> byte3Ref = new PhantomReference<byte[]>(byte3, referenceQueue);
byte3 = null;
byte[] byte4 = new byte[2 * ONE_MB];
byte[] byte5 = new byte[2 * ONE_MB];
byte[] byte6 = new byte[2 * ONE_MB];
byte[] byte7 = new byte[2 * ONE_MB];
byte[] byte8 = new byte[2 * ONE_MB];
byte[] byte9 = new byte[2 * ONE_MB];
```

### 4.2 GC日志：

```
[GC (Allocation Failure) [PSYoungGen: 6653K->943K(9216K)] 6653K->5047K(19456K), 0.0052216 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) --[PSYoungGen: 7357K->7357K(9216K)] 11461K->15557K(19456K), 0.0072333 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 7357K->2787K(9216K)] [ParOldGen: 8200K->8192K(10240K)] 15557K->10979K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0123303 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 7037K->6883K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15229K->15075K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0108626 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 6883K->6856K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15075K->15048K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0085088 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
java.lang.OutOfMemoryError: Java heap space
Dumping heap to d:/tmp/\java_pid3856.hprof ...
Heap dump file created [16209560 bytes in 0.026 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at PhantomReferenceDemo.main(PhantomReferenceDemo.java:29)
Heap
 PSYoungGen      total 9216K, used 6984K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 85% used [0x00000000ff600000,0x00000000ffcd23a8,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 8192K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 80% used [0x00000000fec00000,0x00000000ff400080,0x00000000ff600000)
 Metaspace       used 3176K, capacity 4568K, committed 4864K, reserved 1056768K
  class space    used 339K, capacity 392K, committed 512K, reserved 1048576K

```

### 4.3 GC日志分析

第一次younggc：
在byte[] byte4 = new byte[2 * ONE_MB] 执行原因：应为此时新生代的eden区内存已经占用了6M以上，无法再放入2M
[GC (Allocation Failure) [PSYoungGen: 6653K->943K(9216K)] 6653K->5047K(19456K), 0.0052216 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

第一次gc完的结果为：新生代内存使用了943K，老生代使用了：4104K
gc完后，需要执行byte4的初始化操作，执行后内存布局，约等于：新生代内存使用了2991K，老生代使用了：4104K

byte[] byte5 = new byte[2 * ONE_MB];  此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了5039K，老生代使用了：4104K
byte[] byte6 = new byte[2 * ONE_MB];  此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了7087K，老生代使用了：4104K

byte[] byte7 = new byte[2 * ONE_MB];  此时新生代的eden区内存占用了超过7M，无法再放入2M
[GC (Allocation Failure) --[PSYoungGen: 7357K->7357K(9216K)] 11461K->15557K(19456K), 0.0072333 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

**此处为什么是yonggc，是因为在老生代可用内存 > 历次从新生代晋升到老生代的内存时，则冒险一次，进行一次yonggc。本次yonggc没有实质性的内容改变，所以还是要执行full gc**。

[Full GC (Ergonomics) [PSYoungGen: 7357K->2787K(9216K)] [ParOldGen: 8200K->8192K(10240K)] 15557K->10979K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0123303 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]

第三次gc（full gc）完的结果为:新生代内存使用了2787K，老生代使用了：8192K。
gc完后，需要执行byte7的初始化操作，执行后内存布局，约等于：新生代内存使用了4835K，老生代使用了：8192K。
byte[] byte8 = new byte[2 * ONE_MB]; 此时eden区内存足够，不会触发GC，执行后内存布局，约等于：新生代内存使用了6883K，老生代使用了：8192K

byte[] byte9 = new byte[2 * ONE_MB]; 此时eden区内存不足，需要执行full  gc
[Full GC (Ergonomics) [PSYoungGen: 7037K->6883K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15229K->15075K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0108626 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]

第四次执行之后: 发现内存没有什么改变，新生代内存使用了6882K，老生代使用了：8192K。

[Full GC (Allocation Failure) [PSYoungGen: 6883K->6856K(9216K)] [ParOldGen: 8192K->8192K(10240K)] 15075K->15048K(19456K), [Metaspace: 3143K->3143K(1056768K)], 0.0085088 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 

第五次执行GC后，发现并不能空余出足够的空间，用于存放2M的数据，于是出现了OOM。

### 4.4 总结

可以看出需引用，并不像软引用或弱引用，能够直接由虚拟机操作释放掉内存，需要手动的执行PhantomReference的clear操作来进行释放。此功能大多数用于堆外内存释放掉冰山对象。

## 5：引用总结

具有强引用的对象，无论虚拟机内存处于什么情况，均不能去回收它，即使出现了OOM；只具有软引用的对象，当虚拟机内存在将要出现OOM时，会被垃圾收集器回收掉；只具有弱引用的对象，当虚拟机发生垃圾收集时，就会被回收掉；虚拟机是如何回收掉弱引用和软引用的呢，我们需要从该类的结构上去分析。

软引用和弱引用，还有虚引用，都继承了Reference类，我们看一下构造方法。从构造方法中可以看出，传递值，被赋值到了对象的referent字段，还有一个ReferenceQueue的构造方法。

```java
Reference(T referent) {
    this(referent, null);
}

Reference(T referent, ReferenceQueue<? super T> queue) {
    this.referent = referent;
    this.queue = (queue == null) ? ReferenceQueue.NULL : queue;
}

public void clear() {
        this.referent = null;
}
```

**当弱引用和软引用在被垃圾收集器回收时**，会先把referent字段直接设置为空，并不会调用clear方法；并判断当前的queue，是否可以入队，能入队则将对应的软引用或弱引用入队。从2.1和3.1可以看出，确实这样子的。

当虚引用在被垃圾收集器回收时，虚拟机并不会将referent字段设置为空，而是将虚引用加入到queue中，清除referent字段的逻辑，交给了程序员去处理。从4.1可以看出，在没有手动执行清除动作时，即使是虚引用也同样出现了OOM。