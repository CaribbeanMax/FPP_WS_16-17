// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HighestCard.java

package hand;

import handChecker.HandValue;
import handChecker.PokerCard;
import java.util.List;

// Referenced classes of package hand:
//            SmallHand

public class HighestCard
    implements SmallHand
{

    public HighestCard(List cardList)
    {
        this.cardList = cardList;
    }

    public HandValue getHandValue()
    {
        long valence = (long)(100000000000D + (double)((PokerCard)cardList.get(0)).getValue().getInt() * 1000000000D + (double)((PokerCard)cardList.get(1)).getValue().getInt() * 10000000D + (double)((PokerCard)cardList.get(2)).getValue().getInt() * 100000D + (double)((PokerCard)cardList.get(3)).getValue().getInt() * 1000D + (double)((PokerCard)cardList.get(4)).getValue().getInt() * 10D);
        return new HandValue(valence);
    }

    private List cardList;
}
