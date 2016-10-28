// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PokerCard.java

package handChecker;


public interface PokerCard
{
    public static final class Value extends Enum
    {

        public static Value[] values()
        {
            return (Value[])$VALUES.clone();
        }

        public static Value valueOf(String name)
        {
            return (Value)Enum.valueOf(handChecker/PokerCard$Value, name);
        }

        public final int getInt()
        {
            return value;
        }

        public static final Value ASS;
        public static final Value TWO;
        public static final Value THREE;
        public static final Value FOUR;
        public static final Value FIVE;
        public static final Value SIX;
        public static final Value SEVEN;
        public static final Value EIGHT;
        public static final Value NINE;
        public static final Value TEN;
        public static final Value JACK;
        public static final Value QUEEN;
        public static final Value KING;
        private int value;
        private static final Value $VALUES[];

        static 
        {
            ASS = new Value("ASS", 0, 14);
            TWO = new Value("TWO", 1, 2);
            THREE = new Value("THREE", 2, 3);
            FOUR = new Value("FOUR", 3, 4);
            FIVE = new Value("FIVE", 4, 5);
            SIX = new Value("SIX", 5, 6);
            SEVEN = new Value("SEVEN", 6, 7);
            EIGHT = new Value("EIGHT", 7, 8);
            NINE = new Value("NINE", 8, 9);
            TEN = new Value("TEN", 9, 10);
            JACK = new Value("JACK", 10, 11);
            QUEEN = new Value("QUEEN", 11, 12);
            KING = new Value("KING", 12, 13);
            $VALUES = (new Value[] {
                ASS, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, 
                JACK, QUEEN, KING
            });
        }

        private Value(String s, int i, int value)
        {
            super(s, i);
            this.value = value;
        }
    }

    public static final class Color extends Enum
    {

        public static Color[] values()
        {
            return (Color[])$VALUES.clone();
        }

        public static Color valueOf(String name)
        {
            return (Color)Enum.valueOf(handChecker/PokerCard$Color, name);
        }

        public final int getInt()
        {
            return color;
        }

        public static final Color HEARTS;
        public static final Color DIAMONDS;
        public static final Color SPADES;
        public static final Color CLUBS;
        private int color;
        private static final Color $VALUES[];

        static 
        {
            HEARTS = new Color("HEARTS", 0, 1);
            DIAMONDS = new Color("DIAMONDS", 1, 2);
            SPADES = new Color("SPADES", 2, 3);
            CLUBS = new Color("CLUBS", 3, 4);
            $VALUES = (new Color[] {
                HEARTS, DIAMONDS, SPADES, CLUBS
            });
        }

        private Color(String s, int i, int color)
        {
            super(s, i);
            this.color = color;
        }
    }


    public abstract Color getColor();

    public abstract Value getValue();
}
