// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BigHand.java

package hand;

import handChecker.PokerCard;
import java.util.*;

// Referenced classes of package hand:
//            StraightFlush, Straight, Flush, FourOfAKind, 
//            FullHouse, ThreeOfAKind, TwoPairs, OnePair, 
//            HighestCard, SmallHand

public class BigHand
{

    public BigHand(List cardList)
    {
        this.cardList = cardList;
        sortedList = insertionSort(cardList);
    }

    private List insertionSort(List cardList)
    {
        List sortedList = new ArrayList();
        sortedList.addAll(cardList);
        for(int i = 1; i < sortedList.size(); i++)
        {
            int j = i;
            PokerCard actCard;
            for(actCard = (PokerCard)sortedList.get(j); j > 0 && actCard.getValue().getInt() > ((PokerCard)sortedList.get(j - 1)).getValue().getInt(); j--)
                sortedList.set(j, sortedList.get(j - 1));

            sortedList.set(j, actCard);
        }

        return sortedList;
    }

    private boolean isInsideList(int value, int color)
    {
        PokerCard card = getInsideCard(value, color);
        return card != null;
    }

    private boolean isInsideList(int value)
    {
        PokerCard card = getInsideCard(value);
        return card != null;
    }

    private PokerCard getInsideCard(int value, int color)
    {
        if(value == 1)
            value = handChecker.PokerCard.Value.ASS.getInt();
        for(Iterator iterator = cardList.iterator(); iterator.hasNext();)
        {
            PokerCard card = (PokerCard)iterator.next();
            if(card.getValue().getInt() == value && card.getColor().getInt() == color)
                return card;
        }

        return null;
    }

    private PokerCard getInsideCard(int value)
    {
        if(value == 1)
            value = handChecker.PokerCard.Value.ASS.getInt();
        for(Iterator iterator = cardList.iterator(); iterator.hasNext();)
        {
            PokerCard card = (PokerCard)iterator.next();
            if(card.getValue().getInt() == value)
                return card;
        }

        return null;
    }

    public StraightFlush getStraightFlush()
    {
        for(Iterator iterator = sortedList.iterator(); iterator.hasNext();)
        {
            PokerCard firstCard = (PokerCard)iterator.next();
            handChecker.PokerCard.Color nessesaryColor = firstCard.getColor();
            List cards = new ArrayList();
            cards.add(firstCard);
            int i;
            for(i = 1; isInsideList(firstCard.getValue().getInt() - i, nessesaryColor.getInt()) && i < 5; i++)
            {
                PokerCard card = getInsideCard(firstCard.getValue().getInt() - i, nessesaryColor.getInt());
                cards.add(card);
            }

            if(i == 5)
            {
                StraightFlush hand = new StraightFlush(cards);
                return hand;
            }
        }

        return null;
    }

    public Straight getStraight()
    {
        for(Iterator iterator = sortedList.iterator(); iterator.hasNext();)
        {
            PokerCard firstCard = (PokerCard)iterator.next();
            List cards = new ArrayList();
            cards.add(firstCard);
            int i;
            for(i = 1; isInsideList(firstCard.getValue().getInt() - i) && i < 5; i++)
            {
                PokerCard card = getInsideCard(firstCard.getValue().getInt() - i);
                cards.add(card);
            }

            if(i == 5)
            {
                Straight hand = new Straight(cards);
                return hand;
            }
        }

        return null;
    }

    public Flush getFlush()
    {
        for(Iterator iterator = sortedList.iterator(); iterator.hasNext();)
        {
            PokerCard firstCard = (PokerCard)iterator.next();
            List cards = new ArrayList();
            cards.add(firstCard);
            int value = firstCard.getValue().getInt();
            int color = firstCard.getColor().getInt();
            int i = value - 1;
            while(i > 1) 
            {
                if(isInsideList(i, color))
                    cards.add(getInsideCard(i, color));
                if(cards.size() == 5)
                    return new Flush(cards);
                i--;
            }
        }

        return null;
    }

    private int[] getSameOfAKindCounters()
    {
        int list[] = new int[14];
        for(Iterator iterator = sortedList.iterator(); iterator.hasNext();)
        {
            PokerCard card = (PokerCard)iterator.next();
            int value = card.getValue().getInt();
            list[value - 1]++;
        }

        return list;
    }

    public SmallHand getSameOfAKind()
    {
        int list[] = getSameOfAKindCounters();
        int highestCount = 0;
        int secondHighestCount = 0;
        for(int i = list.length - 1; i >= 0; i--)
        {
            int count = list[i];
            if(count > highestCount)
            {
                secondHighestCount = highestCount;
                highestCount = count;
                continue;
            }
            if(count > secondHighestCount)
                secondHighestCount = count;
        }

        if(highestCount == 4)
            return getFourOfAKind();
        if(highestCount == 3 && secondHighestCount >= 2)
            return getFullHouse();
        if(highestCount == 3 && secondHighestCount == 1)
            return getThreeOfAKind();
        if(highestCount == 2 && secondHighestCount == 2)
            return getTwoPairs();
        if(highestCount == 2 && secondHighestCount == 1)
            return getOnePair();
        else
            return null;
    }

    private FourOfAKind getFourOfAKind()
    {
        int list[] = getSameOfAKindCounters();
        int kicker = 0;
        List cardList = new ArrayList();
        FourOfAKind hand = sortedList.iterator();
        do
        {
            if(!hand.hasNext())
                break;
            PokerCard card = (PokerCard)hand.next();
            int value = card.getValue().getInt();
            if(list[value - 1] == 4)
                cardList.add(0, card);
            else
            if(kicker < 1)
            {
                cardList.add(cardList.size(), card);
                kicker++;
            }
        } while(true);
        hand = new FourOfAKind(cardList);
        return hand;
    }

    private FullHouse getFullHouse()
    {
        int list[] = getSameOfAKindCounters();
        int bigPart = 0;
        int smallPart = 0;
        List cardList = new ArrayList();
        FullHouse hand = sortedList.iterator();
        do
        {
            if(!hand.hasNext())
                break;
            PokerCard card = (PokerCard)hand.next();
            int value = card.getValue().getInt();
            if(list[value - 1] == 3 && bigPart < 3)
            {
                cardList.add(0, card);
                bigPart++;
            } else
            if(list[value - 1] >= 2 && smallPart < 2)
            {
                cardList.add(cardList.size(), card);
                smallPart++;
            }
        } while(true);
        hand = new FullHouse(cardList);
        return hand;
    }

    private ThreeOfAKind getThreeOfAKind()
    {
        int list[] = getSameOfAKindCounters();
        int kicker = 0;
        List cardList = new ArrayList();
        ThreeOfAKind hand = sortedList.iterator();
        do
        {
            if(!hand.hasNext())
                break;
            PokerCard card = (PokerCard)hand.next();
            int value = card.getValue().getInt();
            if(list[value - 1] == 3)
                cardList.add(0, card);
            else
            if(kicker < 2)
            {
                cardList.add(cardList.size(), card);
                kicker++;
            }
        } while(true);
        hand = new ThreeOfAKind(cardList);
        return hand;
    }

    private TwoPairs getTwoPairs()
    {
        int list[] = getSameOfAKindCounters();
        int firstPair = 0;
        int secondPair = 0;
        int kicker = 0;
        List cardList = new ArrayList();
        TwoPairs hand = sortedList.iterator();
        do
        {
            if(!hand.hasNext())
                break;
            PokerCard card = (PokerCard)hand.next();
            int value = card.getValue().getInt();
            if(list[value - 1] == 2 && firstPair < 2)
            {
                cardList.add(0, card);
                firstPair++;
            } else
            if(list[value - 1] == 2 && secondPair < 2)
            {
                cardList.add(2, card);
                secondPair++;
            } else
            if(kicker < 1)
            {
                cardList.add(cardList.size(), card);
                kicker++;
            }
        } while(true);
        hand = new TwoPairs(cardList);
        return hand;
    }

    private OnePair getOnePair()
    {
        int list[] = getSameOfAKindCounters();
        int kicker = 0;
        List cardList = new ArrayList();
        OnePair hand = sortedList.iterator();
        do
        {
            if(!hand.hasNext())
                break;
            PokerCard card = (PokerCard)hand.next();
            int value = card.getValue().getInt();
            if(list[value - 1] == 2)
                cardList.add(0, card);
            else
            if(kicker < 3)
            {
                cardList.add(cardList.size(), card);
                kicker++;
            }
        } while(true);
        hand = new OnePair(cardList);
        return hand;
    }

    public HighestCard getHighestCard()
    {
        List cardList = new ArrayList();
        HighestCard hand = sortedList.iterator();
        do
        {
            if(!hand.hasNext())
                break;
            PokerCard card = (PokerCard)hand.next();
            if(cardList.size() < 5)
                cardList.add(cardList.size(), card);
        } while(true);
        hand = new HighestCard(cardList);
        return hand;
    }

    private List cardList;
    private List sortedList;
}
