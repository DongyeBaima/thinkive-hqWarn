package com.thinkive.hqwarn.bean;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrentBlockingQueue<E> implements BlockingQueue<E>
{
    private final BlockingQueue<E> queue;
    
    public ConcurrentBlockingQueue(BlockingQueue<E> queue)
    {
        // TODO Auto-generated constructor stub
        this.queue = queue;
    }
    
    @Override
    public synchronized E remove()
    {
        // TODO Auto-generated method stub
        return queue.remove();
    }
    
    @Override
    public synchronized E poll()
    {
        // TODO Auto-generated method stub
        return queue.poll();
    }
    
    @Override
    public synchronized E element()
    {
        // TODO Auto-generated method stub
        return queue.element();
    }
    
    @Override
    public synchronized E peek()
    {
        // TODO Auto-generated method stub
        return queue.peek();
    }
    
    @Override
    public synchronized int size()
    {
        // TODO Auto-generated method stub
        return queue.size();
    }
    
    @Override
    public synchronized boolean isEmpty()
    {
        // TODO Auto-generated method stub
        return queue.isEmpty();
    }
    
    @Override
    public synchronized Iterator<E> iterator()
    {
        // TODO Auto-generated method stub
        return queue.iterator();
    }
    
    @Override
    public synchronized Object[] toArray()
    {
        // TODO Auto-generated method stub
        return queue.toArray();
    }
    
    @Override
    public synchronized <T> T[] toArray(T[] a)
    {
        // TODO Auto-generated method stub
        return queue.toArray(a);
    }
    
    @Override
    public synchronized boolean containsAll(Collection<?> c)
    {
        // TODO Auto-generated method stub
        return queue.containsAll(c);
    }
    
    @Override
    public synchronized boolean addAll(Collection<? extends E> c)
    {
        // TODO Auto-generated method stub
        return queue.addAll(c);
    }
    
    @Override
    public synchronized boolean removeAll(Collection<?> c)
    {
        // TODO Auto-generated method stub
        return queue.removeAll(c);
    }
    
    @Override
    public synchronized boolean retainAll(Collection<?> c)
    {
        // TODO Auto-generated method stub
        return queue.retainAll(c);
    }
    
    @Override
    public synchronized void clear()
    {
        // TODO Auto-generated method stub
        queue.clear();
    }
    
    @Override
    public synchronized boolean add(E e)
    {
        // TODO Auto-generated method stub
        return queue.add(e);
    }
    
    @Override
    public synchronized boolean offer(E e)
    {
        // TODO Auto-generated method stub
        return queue.offer(e);
    }
    
    @Override
    public synchronized void put(E e) throws InterruptedException
    {
        // TODO Auto-generated method stub
        queue.put(e);
    }
    
    @Override
    public synchronized boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException
    {
        // TODO Auto-generated method stub
        return queue.offer(e, timeout, unit);
    }
    
    @Override
    public synchronized E take() throws InterruptedException
    {
        // TODO Auto-generated method stub
        return queue.take();
    }
    
    @Override
    public synchronized E poll(long timeout, TimeUnit unit) throws InterruptedException
    {
        // TODO Auto-generated method stub
        return queue.poll(timeout, unit);
    }
    
    @Override
    public synchronized int remainingCapacity()
    {
        // TODO Auto-generated method stub
        return queue.remainingCapacity();
    }
    
    @Override
    public synchronized boolean remove(Object o)
    {
        // TODO Auto-generated method stub
        return queue.remove(o);
    }
    
    @Override
    public synchronized boolean contains(Object o)
    {
        // TODO Auto-generated method stub
        return queue.contains(o);
    }
    
    @Override
    public synchronized int drainTo(Collection<? super E> c)
    {
        // TODO Auto-generated method stub
        return queue.drainTo(c);
    }
    
    @Override
    public synchronized int drainTo(Collection<? super E> c, int maxElements)
    {
        // TODO Auto-generated method stub
        return queue.drainTo(c, maxElements);
    }
    
}
