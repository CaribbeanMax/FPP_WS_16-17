// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StraightFlush.java

package hand;

import handChecker.HandValue;
import handChecker.PokerCard;
import java.util.List;

// Referenced classes of package hand:
//            SmallHand

public class StraightFlush
    implements SmallHand
{

    public StraightFlush(List cardList)
    {
        this.cardList = cardList;
    }

    public HandValue getHandValue()
    {
        long valence = (long)(900000000000D + 1000000000D * (double)((PokerCard)cardList.get(0)).getValue().getInt());
        return new HandValue(valence);
    }

    private List cardList;
}
