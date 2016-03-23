import java.lang.reflect.*;
import java.util.*;

/**
 * @author ktirumala940
 */
/**
 * Represents a stock exchange. A <code>StockExchange</code> keeps a
 * <code>HashMap</code> of stocks, keyed by a stock symbol. It has methods to
 * list a new stock, request a quote for a given stock symbol, and to place a
 * specified trade order.
 */
public class StockExchange
{
    /**
     * a map (implemented as a HashMap of Stocks mapped by their symbols as) of
     * all the stocks
     */
    private Map<String, Stock> listedStocks;


    /**
     * initializes the map of all listed stocks (implements it as a HashMap of
     * Stocks)
     */
    public StockExchange()
    {
        listedStocks = new HashMap<String, Stock>();
    }


    /**
     * 
     * Adds a new stock into the map of listedstocks (maps it into the hashmap
     * by the symbol of the stock)
     * 
     * @param symbol
     *            symbol of the stock
     * @param name
     *            name of the stock
     * @param price
     *            price of the stock
     */
    public void listStock( String symbol, String name, double price )
    {
        listedStocks.put( symbol, new Stock( symbol, name, price ) );
    }

/**
 * 
 * Gets the quote for the stock with the given symbol
 * @param symbol symbol of the stock
 * @return
 */
    public String getQuote( String symbol )
    {
        if (!listedStocks.containsKey( symbol )){
            return null;
        }
        return listedStocks.get( symbol ).getQuote();
    }


    public void placeOrder( TradeOrder order )
    {
        String symbol = order.getSymbol();
        if (!listedStocks.containsKey( symbol )){
            return;
        }
        Stock retrievedStock = listedStocks.get( symbol );
        retrievedStock.placeOrder( order );
    }


    //
    // The following are for test purposes only
    //
    protected Map<String, Stock> getListedStocks()
    {
        return listedStocks;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this StockExchange.
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
                str += separator + field.getType().getName() + " " + field.getName() + ":" + field.get( this );
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
