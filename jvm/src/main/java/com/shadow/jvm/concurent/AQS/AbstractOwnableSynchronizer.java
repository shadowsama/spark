package com.shadow.jvm.concurent.AQS;

public abstract class AbstractOwnableSynchronizer
        implements java.io.Serializable {

    // 版本序列号
    private static final long serialVersionUID = 3737899427754241961L;
    // 构造函数
    protected AbstractOwnableSynchronizer() { }
    // 独占模式下的线程
    private transient Thread exclusiveOwnerThread;

    // 设置独占线程
    protected final void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    // 获取独占线程
    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }
}