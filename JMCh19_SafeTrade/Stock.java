import java.util.*;
import java.lang.reflect.*;
import java.text.DecimalFormat;
/**
 * @author Jessica Zheng
 */

/**
 * Represents a stock in the SafeTrade project
 */
public class Stock
{
    public static DecimalFormat money = new DecimalFormat( "0.00" );

    private String stockSymbol;

    private String companyName;

    private double loPrice, hiPrice, lastPrice;

    private int volume;

    private PriorityQueue<TradeOrder> buyOrders, sellOrders;


    public Stock( String symbol, String name, double price )
    {
        stockSymbol = symbol;
        companyName = name;
        loPrice = hiPrice = lastPrice = price;
        volume = 0;
        sellOrders = new PriorityQueue<TradeOrder>( 11, new PriceComparator() );
        buyOrders = new PriorityQueue<TradeOrder>( 11,
            new PriceComparator( false ) );

    }


    protected void executeOrders()
    {
        while ( !buyOrders.isEmpty() && !sellOrders.isEmpty() )
        {
            TradeOrder buy = buyOrders.peek();
            TradeOrder sell = sellOrders.peek();

            if ( buy.isLimit() && sell.isLimit()
                && sell.getPrice() <= buy.getPrice() )
            {
                execute( buy, sell, sell.getPrice() );
            }
            else if ( buy.isLimit() && sell.isMarket() )
            {
                execute( buy, sell, buy.getPrice() );
            }
            else if ( buy.isMarket() && sell.isLimit() )
            {
                execute( buy, sell, sell.getPrice() );
            }
            else if ( buy.isMarket() && sell.isMarket() )
            {
                execute( buy, sell, lastPrice );
            }
            else
            {
                break;
            }
        }

    }


    private void execute( TradeOrder buy, TradeOrder sell, double price )
    {
        int size = min( buy.getShares(), sell.getShares() );
        sell.subtractShares( size );
        buy.subtractShares( size );
        if ( sell.getShares() == 0 )
        {
            sellOrders.remove();
        }
        if ( buy.getShares() == 0 )
        {
            buyOrders.remove();
        }
        if ( price < loPrice )
        {
            loPrice = price;
        }
        if ( price > hiPrice )
        {
            hiPrice = price;
        }
        lastPrice = price;
        volume += size;

        buy.getTrader().mailbox().add( "You bought: " + size + " "
            + stockSymbol + " at " + price + " amt " + price * size );
        sell.getTrader().mailbox().add( "You sold: " + size + " " + stockSymbol
            + " at " + price + " amt " + price * size );

    }


    private int min( int shares, int shares2 )
    {
        if ( shares < shares2 )
        {
            return shares;
        }
        else
        {
            return shares2;
        }
    }


    public String getQuote()
    {
        String ans = companyName + " (" + stockSymbol + " \nPrice: " + lastPrice
            + "  hi: " + hiPrice + "  lo: " + loPrice + "  vol: " + volume
            + "\nAsk: ";

        if ( sellOrders.isEmpty() )
        {
            ans += "none";
        }
        else if ( sellOrders.peek().isMarket() )
        {
            ans += "market" + " size: " + sellOrders.peek().getShares();
        }
        else
        {
            ans += sellOrders.peek().getPrice() + " size: "
                + sellOrders.peek().getShares();
        }

        ans += "  Bid: ";

        if ( buyOrders.isEmpty() )
        {
            ans += "none";
        }
        else if ( buyOrders.peek().isMarket() )
        {
            ans += "market" + " size: " + buyOrders.peek().getShares();
        }
        else
        {
            ans += buyOrders.peek().getPrice() + " size: "
                + buyOrders.peek().getShares();
        }

        return ans;
    }


    public void placeOrder( TradeOrder order )
    {
        String msg = "New order:  ";
        if ( order.isBuy() )
        {
            msg+="Buy ";
            buyOrders.add( order );
        }
        else
        {
            msg+="Sell ";
            sellOrders.add( order );
        }
        msg+=stockSymbol + " (" + companyName
            + ")\n" + order.getShares() + " shares at ";
        if ( order.isMarket() )
        {
            msg += "market";
        }
        else
        {
            msg += "$" + order.getPrice();
        }
        order.getTrader().mailbox().add( msg );
        executeOrders();
    }


    //
    // The following are for test purposes only
    //

    protected String getStockSymbol()
    {
        return stockSymbol;
    }


    protected String getCompanyName()
    {
        return companyName;
    }


    protected double getLoPrice()
    {
        return loPrice;
    }


    protected double getHiPrice()
    {
        return hiPrice;
    }


    protected double getLastPrice()
    {
        return lastPrice;
    }


    protected int getVolume()
    {
        return volume;
    }


    protected PriorityQueue<TradeOrder> getBuyOrders()
    {
        return buyOrders;
    }


    protected PriorityQueue<TradeOrder> getSellOrders()
    {
        return sellOrders;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Stock.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for ( Field field : fields )
        {
            try
            {
                str += separator + field.getType().getName() + " "
                    + field.getName() + ":" + field.get( this );
            }
            catch ( IllegalAccessException ex )
            {
                System.out.println( ex );
            }

            separator = ", ";
        }

        return str + "]";
    }
}