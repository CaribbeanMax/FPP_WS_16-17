// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandChecker.java

package handChecker;

import hand.BigHand;
import hand.SmallHand;
import java.util.List;

// Referenced classes of package handChecker:
//            HandValue

public class HandChecker
{

    public HandChecker()
    {
    }

    public HandValue check(List cards)
    {
        if(cards.size() != 7)
        {
            throw new IllegalArgumentException("The list must have a size of 7");
        } else
        {
            SmallHand bestHand = null;
            BigHand bigHand = new BigHand(cards);
            hand.StraightFlush straightFlush = bigHand.getStraightFlush();
            bestHand = compareSmallHands(bestHand, straightFlush);
            hand.Straight straight = bigHand.getStraight();
            bestHand = compareSmallHands(bestHand, straight);
            hand.Flush flush = bigHand.getFlush();
            bestHand = compareSmallHands(bestHand, flush);
            SmallHand sameOfAKind = bigHand.getSameOfAKind();
            bestHand = compareSmallHands(bestHand, sameOfAKind);
            hand.HighestCard highestCard = bigHand.getHighestCard();
            bestHand = compareSmallHands(bestHand, highestCard);
            return bestHand.getHandValue();
        }
    }

    private SmallHand compareSmallHands(SmallHand hand1, SmallHand hand2)
    {
        if(hand1 == null)
            return hand2;
        if(hand2 == null)
            return hand1;
        if(hand1.getHandValue().compareTo(hand2.getHandValue()) < 0)
            return hand2;
        else
            return hand1;
    }
}
