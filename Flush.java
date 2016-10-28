// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Flush.java

package hand;

import handChecker.HandValue;
import handChecker.PokerCard;
import java.util.List;

// Referenced classes of package hand:
//            SmallHand

public class Flush
    implements SmallHand
{

    public Flush(List cardList)
    {
        this.cardList = cardList;
    }

    public HandValue getHandValue()
    {
        int cards[] = new int[cardList.size()];
        for(int i = 0; i < cardList.size(); i++)
            cards[i] = ((PokerCard)cardList.get(i)).getValue().getInt();

        long valence = (long)(600000000000D + (double)cards[0] * 1000000000D + (double)cards[1] * 10000000D + (double)cards[2] * 100000D + (double)cards[3] * 1000D + (double)cards[4] * 10D);
        return new HandValue(valence);
    }

    private List cardList;
}
