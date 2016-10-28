// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandValue.java

package handChecker;


public class HandValue
    implements Comparable
{

    public HandValue(long handValue)
    {
        this.handValue = handValue;
    }

    private long getHandValue()
    {
        return handValue;
    }

    public int compareTo(HandValue o)
    {
        if(getHandValue() > o.getHandValue())
            return 1;
        return getHandValue() != o.getHandValue() ? -1 : 0;
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((HandValue)obj);
    }

    private long handValue;
}
