///**
// * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
// * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
// * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
// * License. You may obtain a copy of the License at
// * <p>
// * http://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
// * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
// * specific language governing permissions and limitations under the License.
// */
//package org.shadow.kafka.client;
//
//import org.apache.kafka.common.network.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.nio.channels.*;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//public class Selector {
//
//    private static final Logger log = LoggerFactory.getLogger(org.apache.kafka.common.network.Selector.class);
//
//    private final java.nio.channels.Selector nioSelector;
//
//    public Selector() throws IOException {
//        this.nioSelector = java.nio.channels.Selector.open();
//    }
//
//
//    public void connect(String id, InetSocketAddress address, int sendBufferSize, int receiveBufferSize) throws IOException {
//
//        SocketChannel socketChannel = SocketChannel.open();
//        socketChannel.configureBlocking(false);
//        Socket socket = socketChannel.socket();
//        socket.setKeepAlive(true);
//        if (sendBufferSize != Selectable.USE_DEFAULT_BUFFER_SIZE)
//            socket.setSendBufferSize(sendBufferSize);
//        if (receiveBufferSize != Selectable.USE_DEFAULT_BUFFER_SIZE)
//            socket.setReceiveBufferSize(receiveBufferSize);
//        socket.setTcpNoDelay(true);
//        boolean connected;
//        try {
//            connected = socketChannel.connect(address);
//        } catch (UnresolvedAddressException e) {
//            socketChannel.close();
//            throw new IOException("Can't resolve address: " + address, e);
//        } catch (IOException e) {
//            socketChannel.close();
//            throw e;
//        }
//        SelectionKey key = socketChannel.register(nioSelector, SelectionKey.OP_CONNECT);
//
//    }
//
//
//    public void register(String id, SocketChannel socketChannel) throws ClosedChannelException {
//        SelectionKey key = socketChannel.register(nioSelector, SelectionKey.OP_READ);
//
//    }
//
//
//    public void wakeup() {
//        this.nioSelector.wakeup();
//    }
//
//
//    public void poll(long timeout) throws IOException {
//        if (timeout < 0)
//            throw new IllegalArgumentException("timeout should be >= 0");
//
//
//        pollSelectionKeys(this.nioSelector.selectedKeys(), false);
//        //     pollSelectionKeys(immediatelyConnectedKeys, true);
//
//
//    }
//
//    private void pollSelectionKeys(Iterable<SelectionKey> selectionKeys, boolean isImmediatelyConnected) throws IOException {
//        Iterator<SelectionKey> iterator = selectionKeys.iterator();
//        while (iterator.hasNext()) {
//            SelectionKey key = iterator.next();
//            iterator.remove();
//            KafkaChannel channel = channel(key);
//
//
//                /* complete any connections that have finished their handshake (either normally or immediately) */
//            if (isImmediatelyConnected || key.isConnectable()) {
//                if (channel.finishConnect()) {
//                } else
//                    continue;
//            }
//
//                /* if channel is not ready finish prepare */
//            if (channel.isConnected() && !channel.ready())
//                channel.prepare();
//
//                /* if channel is ready read from any connections that have readable data */
//            if (channel.ready() && key.isReadable()) {
//                NetworkReceive networkReceive;
//                while ((networkReceive = channel.read()) != null) {
//                }
//
//                /* if channel is ready write to any sockets that have space in their buffer and for which we have data */
//                if (channel.ready() && key.isWritable()) {
//                    Send send = channel.write();
//                    if (send != null) {
//
//                    }
//                }
//
//                /* cancel any defunct sockets */
//                if (!key.isValid()) {
//                    close(channel);
//
//                }
//
//            }
//        }
//
//    }
//
//
//    private int select(long ms) throws IOException {
//        if (ms < 0L)
//            throw new IllegalArgumentException("timeout should be >= 0");
//
//        if (ms == 0L)
//            return this.nioSelector.selectNow();
//        else
//            return this.nioSelector.select(ms);
//    }
//
//    public void close(String id) {
//    }
//
//    private void close(KafkaChannel channel) {
//        try {
//            channel.close();
//        } catch (IOException e) {
//            log.error("Exception closing connection to node {}:", channel.id(), e);
//        }
//
//    }
//
//
//}
