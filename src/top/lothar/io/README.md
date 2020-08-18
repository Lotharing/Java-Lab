## NIO 学习

### 区别:

 IO是面向流的处理，NIO是面向块(缓冲区)的处理
 面向流的I/O 系统一次一个字节地处理数据。  流是单向的
 一个面向块(缓冲区)的I/O系统以块的形式处理数据。  缓冲区是双向的，管道的概念

### NIO主要有三个核心部分组成：
 
  buffer缓冲区,Channel管道,Selector选择器
  (Channel管道比作成铁路，buffer缓冲区比作成火车(运载着货物))
  
  其中ByteBuffer是用得最多的实现类(在管道中读写字节数据)。还有IntBuffer , FloatBuffer ....
  
  缓冲区核心：读取缓冲区的数据get()/写数据到缓冲区中put() : 实现类Buffer接口下的实现类，例如 ByteBuffer.put()
  
### Buffer类维护了4个核心变量属性来提供关于其所包含的数组的信息

  * 容量Capacity:
    缓冲区能够容纳的数据元素的最大数量。容量在缓冲区创建时被设定，并且永远不能被改变。(底层是数组)
    
    上界Limit:
    缓冲区里的数据的总数，代表了当前缓冲区中一共有多少数据。
    
    位置Position:
    下一个要被读或写的元素的位置。Position会自动由相应的 get( )和 put( )函数更新。
    
    标记Mark:
    一个备忘位置。用于记录上一次读写的位置。
    
### Channel通道核心要点
    
   * Channel通道**只负责传输数据**、不直接操作数据的。操作数据都是通过Buffer缓冲区来进行操作！
    
   * 原节点与目标节点的连接,负责传输Buffer中的数据
   
   * 主要的实现类：FileChannel SocketChannel ServerSocketChannel DatagramChannel
   
   * 获取通道： 阵对支持通道类提供 getChannel() 方法
        1.本地IO : FileInputStream , FileOutputStream ,RandomAccessFile
        2.网络IO : Socket , ServerSocket , DatagramChannel
   * 获取通道示例：
        //1.通过本地IO的方式来获取通道
        FileInputStream fileInputStream = new FileInputStream("F:\\IO.md");
        // 得到文件的输入通道
        FileChannel inchannel = fileInputStream.getChannel();
        //2. jdk1.7后通过静态方法.open()获取通道
        FileChannel.open(Paths.get("F:\\IO.md"), StandardOpenOption.WRITE);
        
### 直接与非直接缓冲区

   * 非直接缓冲区是需要经过一个：copy的阶段的(从内核空间copy到用户空间)
   * 直接缓冲区不需要经过copy阶段，也可以理解成--->内存映射文件，(上面的图片也有过例子)。
   
   * 使用直接缓冲区有两种方式：
        缓冲区创建的时候分配的是直接缓冲区
        在FileChannel上调用map()方法，将文件直接映射到内存中创建

   

    
   
   
    